package com.lytips.ITags.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lytips.ITags.crawler.ZakerCrawler;
import com.lytips.ITags.entity.News;
import com.lytips.ITags.model.MessageModel;
import com.lytips.ITags.repository.NewsRepo;
import com.lytips.ITags.utils.HttpUtils;
import com.lytips.base.BaseController;

@Controller
@RequestMapping("news")
public class NewsController extends BaseController {
	@Autowired
	private ZakerCrawler zakerCrawler;
	@Autowired
	private NewsRepo newsRepo;
	
	@RequestMapping(value = "newsDetail")
	public String newsDetail(String url, Model model, HttpServletRequest request) {
		model.addAttribute("newsUrl", url);
		model.addAttribute("newsChange", "newsDetail.ftl");
		request.getSession().setAttribute("location", "top");
		return "newsIndex";
	}
	
	public String index(Model model, String type, HttpServletRequest request) {
		String host = "http://toutiao-ali.juheapi.com";
	    String path = "/toutiao/index";
	    String method = "GET";
	    String appcode = "164b9ec267cf41b5b3bc5e770ceb999e";
	    Map<String, String> headers = new HashMap<String, String>();
	    //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
	    headers.put("Authorization", "APPCODE " + appcode);
	    Map<String, String> querys = new HashMap<String, String>();
	    if(StringUtils.isEmpty(type)) {
	    	type = "top";
	    }
	    querys.put("type", type);
	    HttpResponse response = null;
	    Object obj = null;
	    try {
	    	response = HttpUtils.doGet(host, path, method, headers, querys);
	    	obj = JSON.toJSON(EntityUtils.toString(response.getEntity()).replaceAll("\\\\", ""));
	    } catch (Exception e) {
	    	e.printStackTrace();
	    } finally {
		}
	    model.addAttribute("news", obj);
	    model.addAttribute("type", type);
		model.addAttribute("newsChange", "news.ftl");
		request.getSession().setAttribute("location", "top");
		return "newsIndex";
	}
	
	@RequestMapping("zaker")
	@ResponseBody
	public MessageModel zaker() {
		zakerCrawler.getNews(true);
		return new MessageModel();
	}
	
	@RequestMapping("getNew")
	@ResponseBody
	public MessageModel addNew() {
		zakerCrawler.addNew();
		return new MessageModel();
	}
	
	@RequestMapping(value = "index/{type}")
	public String zakerIndex(Model model, @PathVariable(value = "type") Integer type, HttpServletRequest request) {
		if(type == null) {
			type = 660;
		}
		PageHelper.startPage(1, 30);
		List<News> list = newsRepo.queryNewsByType(type);
		PageInfo<News> pageInfo = new PageInfo<>(list);
	    model.addAttribute("news", pageInfo);
	    model.addAttribute("type", type);
		model.addAttribute("newsChange", "news.ftl");
		request.getSession().setAttribute("location", "top");
		return "newsIndex";
	}
	
	@RequestMapping(value = "loadMore/{type}/{page}")
	@ResponseBody
	public PageInfo<News> zakerIndex(@PathVariable(value = "type") Integer type,@PathVariable(value = "page") Integer page) {
		PageHelper.startPage(page, 30);
		List<News> list = newsRepo.queryNewsByType(type);
		PageInfo<News> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}
	
	@RequestMapping("index")
	public String index() {
		return "redirect:/news/index/660";
	}
	
}
