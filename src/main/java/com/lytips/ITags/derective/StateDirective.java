package com.lytips.ITags.derective;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.lytips.ITags.query.StateQuery;
import com.lytips.ITags.repository.MsgRepo;
import com.lytips.base.BaseDirective;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public class StateDirective extends BaseDirective {
	
	@Autowired
	private MsgRepo msgRepo;
	
	@Override
	public void execute(Environment env, @SuppressWarnings("rawtypes") Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
			throws TemplateException, IOException {
		String userId = (String) getParameter(params, "userId");
		Integer msgId = (Integer) getParameter(params, "msgId");
		BigDecimal type =  (BigDecimal) getParameter(params, "type");
		StateQuery stateQuery = new StateQuery(Integer.parseInt(userId), 
				msgId, type.intValue());
		Integer id = msgRepo.queryIsGoodOrStore(stateQuery);
		stateQuery.setType(1);
		Integer storeId = msgRepo.queryIsGoodOrStore(stateQuery);
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("goodId", id);
		map.put("storeId", storeId);
		setVariable(env, body, "stateIds", map);
	}

}
