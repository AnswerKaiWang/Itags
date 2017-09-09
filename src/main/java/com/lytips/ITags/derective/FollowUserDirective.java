package com.lytips.ITags.derective;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lytips.ITags.entity.UserRelation;
import com.lytips.ITags.repository.UserRepo;
import com.lytips.base.BaseDirective;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public class FollowUserDirective extends BaseDirective {
	@Autowired
	private UserRepo userRepo;
	
	@Override
	public void execute(Environment env, @SuppressWarnings("rawtypes") Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
			throws TemplateException, IOException {
		Integer userId = (Integer) getParameter(params, "userId");
		String type = (String) getParameter(params, "type");
		Integer page = (Integer) getParameter(params, "page");
		PageHelper.startPage(page, 3);
		List<UserRelation> list;
		if(type.equals("follow")) {
			list = userRepo.queryFollowUsers(userId);
		} else {
			list = userRepo.queryFollowedUsers(userId);
		}
		PageInfo<UserRelation> pageInfo = new PageInfo<>(list);
		setVariable(env, body, "followPageInfo", pageInfo);
	}

}
