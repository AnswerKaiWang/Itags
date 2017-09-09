package com.lytips.ITags.derective;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.lytips.ITags.entity.News;
import com.lytips.ITags.repository.NewsRepo;
import com.lytips.base.BaseDirective;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public class HotNewsDirective extends BaseDirective {
	@Autowired
	private NewsRepo newsRepo;
	
	@Override
	public void execute(Environment env, @SuppressWarnings("rawtypes") Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
			throws TemplateException, IOException {
		Integer type = (Integer) getParameter(params, "type");
		List<News> newsest = newsRepo.queryHotNews(type, -1);
		List<News> hotNews = newsRepo.queryHotNews(type, -24);
		Map<String, List<News>> map = new HashMap<>();
		map.put("newsest", newsest);
		map.put("hotNews", hotNews);
		setVariable(env, body, "newses", map);
	}

}
