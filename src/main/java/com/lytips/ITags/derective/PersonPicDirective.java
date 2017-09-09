package com.lytips.ITags.derective;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.lytips.ITags.repository.UserRepo;
import com.lytips.base.BaseDirective;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public class PersonPicDirective extends BaseDirective {
	@Autowired
	private UserRepo userRepo;
	
	@Override
	public void execute(Environment env, @SuppressWarnings("rawtypes") Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
			throws TemplateException, IOException {
		Integer userId = (Integer) getParameter(params, "userId");
		String type = (String) getParameter(params, "type");
		List<String> list = userRepo.queryUserPics(userId, type);
		setVariable(env, body, "picList", list);
	}

}
