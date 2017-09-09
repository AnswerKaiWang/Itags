package com.lytips.ITags.derective;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.lytips.ITags.repository.ChatRepo;
import com.lytips.base.BaseDirective;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public class ChatCountDirective extends BaseDirective {
	@Autowired
	private ChatRepo chatRepo;
	
	@Override
	public void execute(Environment env, @SuppressWarnings("rawtypes") Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
			throws TemplateException, IOException {
		Integer userId = (Integer) getParameter(params, "userId");
		Integer personUserId = (Integer) getParameter(params, "personUserId");
		Integer count = chatRepo.queryUnReadCount(userId, personUserId);
		if(null == count) {
			count = 0;
		}
		setVariable(env, body, "unReadCount", count);
	}

}
