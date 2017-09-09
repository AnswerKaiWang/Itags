package com.lytips.ITags.repository;

import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.context.annotation.Scope;

import com.lytips.ITags.entity.News;
import com.lytips.ITags.entity.NewsIndex;
@Scope("prototype")
public interface NewsRepo {
	void insertBatch(List<News> list);
	
	@Delete("delete from t_news where type = #{type}")
	void clearNewsByType(@Param(value = "type") Integer type);
	
	@Select("select title, author, pic, url, publish_time as publishTime, type, "
			+ "comment from t_news where type = #{type} and page >=0 order by publish_time desc")
	List<News> queryNewsByType(@Param(value = "type") Integer type);
	
	@Delete("delete from t_news")
	void clearAllNews();
	
	@Delete("delete from t_news_index")
	void clearAllNewsIndex();
	
	@Insert("insert into t_news_index(title, type, create_time, state) values(#{title},"
			+ "#{type}, #{createTime}, 1)")
	void insertIndex(NewsIndex newsIndex);
	
	
	@Select("select title from t_news_index where type = #{type} and state = 1")
	List<String> queryNewsIndex(@Param(value = "type") Integer type);
	
	@Update("update t_news_index set state = 0 where create_time = #{createTime}")
	void deleteYesterdayNewsIndex(@Param(value = "createTime") String createTime);

	void insertNewsIndexBatch(List<News> news);
	
	@Select("select title, author, url, publish_time as publishTime, type, "
			+ "comment from t_news where type = #{type} and page = #{page} "
			+ "order by publish_time desc limit 0, 20")
	List<News> queryHotNews(@Param(value = "type") Integer type, @Param(value = "page") Integer page);
	
	@Delete("delete from t_news where page < 0")
	void clearHotNews();
	
}
