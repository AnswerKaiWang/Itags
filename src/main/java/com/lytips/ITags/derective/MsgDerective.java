package com.lytips.ITags.derective;

import java.io.IOException;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.lytips.ITags.business.MsgBiz;
import com.lytips.ITags.query.MsgQuery;
import com.lytips.base.BaseDirective;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

@Component
public class MsgDerective extends BaseDirective {
	@Autowired
	private MsgBiz msgBiz;
	
	@Override
	public void execute(Environment env, @SuppressWarnings("rawtypes") Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
			throws TemplateException, IOException {
		String userId = (String) getParameter(params, "userId");
		String personUserId = (String) getParameter(params, "personUserId");
		String page = (String) getParameter(params, "page");
		String type = (String) getParameter(params, "type");
		MsgQuery msgQuery = new MsgQuery(null, Integer.parseInt(userId), type, Integer.parseInt(personUserId));
		msgQuery.setPage(Integer.parseInt(page));
		if(type.equals("self")) {
			setVariable(env, body, "pageInfo", msgBiz.queryMsgByParams(msgQuery));
		} else {
			setVariable(env, body, "pageInfo", msgBiz.personMsgList(msgQuery));
		}
	}

}
