package com.lytips.ITags.derective;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.lytips.ITags.entity.Remark;
import com.lytips.ITags.repository.UserRepo;
import com.lytips.base.BaseDirective;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public class RemarkDirective extends BaseDirective {
	@Autowired
	private UserRepo userRepo;
	
	@Override
	public void execute(Environment env, @SuppressWarnings("rawtypes") Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
			throws TemplateException, IOException {
		Integer userId = (Integer) getParameter(params, "userId");
		Integer personUserId = (Integer) getParameter(params, "personUserId");
		Remark remark = userRepo.queryRemarkById(userId, personUserId);
		setVariable(env, body, "remark", remark);
	}

}
