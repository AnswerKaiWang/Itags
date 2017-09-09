package com.lytips.ITags.derective;

import java.io.IOException;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.pagehelper.PageInfo;
import com.lytips.ITags.business.MsgBiz;
import com.lytips.ITags.entity.MsgRelation;
import com.lytips.ITags.query.ExtraMsgQuery;
import com.lytips.base.BaseDirective;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public class ExtraMsgDerective extends BaseDirective {
	@Autowired
	private MsgBiz msgBiz;
	
	
	@Override
	public void execute(Environment env, @SuppressWarnings("rawtypes") Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
			throws TemplateException, IOException {
		String msgId = (String) getParameter(params, "msgId");
		ExtraMsgQuery query = new ExtraMsgQuery();
		query.setMsgId(Integer.parseInt(msgId));
		query.setRows(5);
		PageInfo<MsgRelation> pageInfo = msgBiz.queryExtraMsg(query);
		setVariable(env, body, "extraMsgPageInfo", pageInfo);
	}

}
