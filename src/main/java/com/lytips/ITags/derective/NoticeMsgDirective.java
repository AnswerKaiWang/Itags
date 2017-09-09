package com.lytips.ITags.derective;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.lytips.ITags.entity.Msg;
import com.lytips.ITags.repository.MsgRepo;
import com.lytips.base.BaseDirective;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public class NoticeMsgDirective extends BaseDirective {
	@Autowired
	private MsgRepo  msgRepo;
	
	@Override
	public void execute(Environment env, @SuppressWarnings("rawtypes") Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
			throws TemplateException, IOException {
		Integer msgId = (Integer) getParameter(params, "msgId");
		String type = (String) getParameter(params, "type");
		if(type.equals("extra")) {
			msgId = msgRepo.queryRootMsgId(msgId);
		}
		Msg msg = msgRepo.queryById(msgId);
		setVariable(env, body, "msg", msg);
	}

}
