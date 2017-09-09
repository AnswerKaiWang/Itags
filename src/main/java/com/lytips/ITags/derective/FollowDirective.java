package com.lytips.ITags.derective;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.lytips.ITags.model.MessageModel;
import com.lytips.ITags.repository.UserRepo;
import com.lytips.base.BaseDirective;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public class FollowDirective extends BaseDirective {
	@Autowired
	private UserRepo userRepo;
	
	@Override
	public void execute(Environment env, @SuppressWarnings("rawtypes") Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
			throws TemplateException, IOException {
		Integer userId = (Integer) getParameter(params, "userId");
		Integer followId = (Integer) getParameter(params, "followId");
		Integer id = userRepo.isFollow(userId, followId);
		MessageModel messageModel = new MessageModel();
		if(id == null) {
			messageModel.setResultCode(0);
		} else {
		messageModel.setResult(id);
		}
		setVariable(env, body, "result", messageModel);
	}

}
