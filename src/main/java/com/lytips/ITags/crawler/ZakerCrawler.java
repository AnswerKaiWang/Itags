package com.lytips.ITags.crawler;

import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.lytips.ITags.constant.ItagsConstant;
import com.lytips.ITags.constant.NewsConstant;
import com.lytips.ITags.entity.News;
import com.lytips.ITags.repository.NewsRepo;
import com.lytips.ITags.utils.ITagsUtils;
@Component
public class ZakerCrawler implements Runnable {
	private static final Logger LOG = Logger.getLogger(ZakerCrawler.class.getName());
	private String url;
	private Integer type;
	private Integer page;
	
	@Autowired
	private NewsRepo newsRepo;
	@Autowired
	private TaskExecutor taskExecutor;
	
	/**
	 * 爬取新闻
	 * @param url
	 * @param type
	 * @param flag
	 * @param page 
	 */
	public void crawlerNews(String url, Integer type, Boolean flag, Integer page) {
		List<News> list = crawlerDetail(url, type, flag, page);
		if(ITagsUtils.isNotEmptyList(list)) {
			if(flag) {
				if(url.contains("http://www.myzaker.com/channel/")) {
					newsRepo.insertNewsIndexBatch(list);
				}
				newsRepo.insertBatch(list);
				System.err.println(type + "插入成功--------" + page);
			} else {
				List<News> newNews = new ArrayList<>();
				List<String> newsIndexes = newsRepo.queryNewsIndex(type);
				for(News news:list) {
					if(!newsIndexes.contains(news.getTitle())) {
						newNews.add(news);
					} 
				}
				if(ITagsUtils.isNotEmptyList(newNews)) {
					newsRepo.insertNewsIndexBatch(newNews);
					newsRepo.insertBatch(newNews);
				}
			}
		}
	}


	@Override
	public void run() {
		crawlerNews(url, type, true, page);
		LOG.error(Thread.currentThread().getName());
	}

	public void getNews(Boolean flag) {
		try {
			if(flag) {
				newsRepo.clearAllNews();
				newsRepo.clearAllNewsIndex();
				Thread.sleep(2000);
				page = 1;
			} else {
				page = 0;
			}
			newsRepo.clearHotNews();
			@SuppressWarnings("unchecked")
			Class<NewsConstant> clazz = (Class<NewsConstant>) Class.forName("com.lytips.ITags.constant.NewsConstant");
			Field[] fields = clazz.getDeclaredFields();
			for(Field field:fields) {
				String channel = field.get(null).toString();
				crawlerNews("http://www.myzaker.com/channel/" + channel, Integer.parseInt(channel), flag, page);
				System.err.println(channel + "频道执行结束");
			}
			//crawlerNews("http://www.myzaker.com/channel/660", 660, flag, page);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 定时任务每半小时抓取第一页更新的数据
	 */
	@Scheduled(cron="0 0/30 6-23 * * ?")
	public void addNew() {
		getNews(false);
	}
	
	//定时每天中午12点删除前天增量索引库 保证查询速率
	@Scheduled(cron="0 0 12 * * ?")
	public void deleteIndex() {
		Long yesterday = new Date().getTime() - ItagsConstant.DAY_TIME;
		String newsIndex = new SimpleDateFormat("MM-dd").format(new Date(yesterday));
		newsRepo.deleteYesterdayNewsIndex(newsIndex);
		LOG.info(new Date().toString() + "--删除前天增量索引库");
	}
	
	
	
	/**
	 * 爬取页面新闻信息
	 * @param url
	 * @param type
	 * @param flag
	 * @return
	 */
	public List<News> crawlerDetail(String url, Integer type, Boolean flag, Integer page) {
		List<News> list = new ArrayList<>();
		Document document = null;
		try {
			document = Jsoup.connect(url).get();
			Elements links = document.select("div.figure");
			Elements next = document.select("a.next_page");
			if(flag) {
				if(next != null) {
					String nextUrl = next.first().attr("href");
					if(StringUtils.isNoneEmpty(nextUrl)) {
						ZakerCrawler crawler = new ZakerCrawler("http:" + nextUrl, type, newsRepo, taskExecutor, page + 1);
						this.taskExecutor.execute(crawler);
					}
				}
			}
			loadHotNews(document, list, type);
			News news = null;
			for(Element link:links) {
				news = new News();
				news.setType(type);
				news.setPage(page);
				Elements title = link.select("h2.figcaption");
				Element info = link.select("div.subtitle").first();
				news.setAuthor(info.select("span:eq(0)").text());
				news.setPublishTime(processDate(info.select("span:eq(1)").text()));
				news.setTitle(title.first().text());
				news.setComment(info.select("span:eq(2)").text());
				String ul = title.first().select(">a").first().attr("href");
				ul = "http://app.myzaker.com/news/article.php?pk=" + ul.substring(26, ul.length()-1);
				news.setUrl(ul);
				Element img = link.select(">a").first();
				if(img != null) {
					String style = img.attr("style");
					style = "http://" + style.substring(23, style.indexOf(")"));
					news.setPic(style);
				}
				list.add(news);
			}
		} catch (IOException e) {
			ZakerCrawler crawler = new ZakerCrawler(url, type, newsRepo, taskExecutor, page);
			this.taskExecutor.execute(crawler);
			 LOG.error("读取超时，正在重新读取" + crawler);
		}
		return list;
	}
	
	
	
	
	
	
	
	private void loadHotNews(Document document, List<News> list, Integer type) {
		Elements elements = document.select("div.channel");
		News news;
		Integer page = null;
		for(Element ex:elements) {
			String title = ex.select("h3.channel_title").text();
			if(title.equals("24小时热榜"))
				page = -24;
			if(title.equals("最新资讯"))
				page = -1;
			Elements newses = ex.select("ul.channel_list_show");
			if(null != newses) {
				for(Element temp: newses) {
					Elements tempNews = temp.select("li");
					String url;
					String subTitle;
					Element newsDetail ;
					for(Element temp2: tempNews) {
						news = new News();
						news.setPage(page);
						news.setType(type);
						url = temp2.select("a").attr("href");
						url = "http://app.myzaker.com/news/article.php?pk=" + url.substring(26, url.length()-1);
						news.setUrl(url);
						subTitle =  temp2.select("a").text();
						news.setTitle(subTitle);
						newsDetail = temp2.select("div.channel_subtitle").first();
						news.setAuthor(newsDetail.select("span:eq(0)").text());
						news.setPublishTime(processDate(newsDetail.select("span:eq(1)").text()));
						list.add(news);
					}
				}
			}
		}
	}


	public ZakerCrawler(String url, Integer type, NewsRepo newsRepo, TaskExecutor taskExecutor, Integer page) {
		this.url = url;
		this.type = type;
		this.newsRepo = newsRepo;
		this.taskExecutor = taskExecutor;
		this.page = page;
	}


	public ZakerCrawler() {
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	public String processDate(String time) {
		Long time1 = null;
		SimpleDateFormat date = new SimpleDateFormat("M-d");
		SimpleDateFormat dateTime = new SimpleDateFormat("M-d HH:mm");
		if(time.contains("分钟")) {
			int minute = Integer.parseInt(time.substring(0, time.indexOf("分")));
			time1 = new Date().getTime() - minute * ItagsConstant.MINUTE_TIME;
			return dateTime.format(new Date(time1));
		} else if (time.contains("小时")) {
			int hour = Integer.parseInt(time.substring(0, time.indexOf("小")));
			time1 = new Date().getTime() - hour * ItagsConstant.HOUR_TIME + ((int)(Math.random() * 10)) * ItagsConstant.MINUTE_TIME;
			return dateTime.format(new Date(time1));
		} else if(time.equals("昨天")) {
			time1 = new Date().getTime() - 1 * ItagsConstant.DAY_TIME;
			return date.format(new Date(time1));
		} else if(time.equals("前天")){
			time1 = new Date().getTime() - 2 * ItagsConstant.DAY_TIME;
			return date.format(new Date(time1));
		} else if(time.equals("刚刚")){
			time1 = new Date().getTime();
			return dateTime.format(time1);
		}
		return time;
	}
	
}
