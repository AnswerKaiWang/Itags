package com.lytips.ITags.derective;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lytips.ITags.entity.MsgRelation;
import com.lytips.ITags.repository.MsgRepo;
import com.lytips.base.BaseDirective;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public class NoticeDirective extends BaseDirective {
	@Autowired
	private MsgRepo msgRepo;
	
	@Override
	public void execute(Environment env, @SuppressWarnings("rawtypes") Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
			throws TemplateException, IOException {
		Integer userId = (Integer) getParameter(params, "userId");
		Integer page = (Integer) getParameter(params, "page");
		PageHelper.startPage(page, 10);
		List<MsgRelation> list = msgRepo.queryNotice(userId);
		PageInfo<MsgRelation> pageInfo = new PageInfo<>(list);
		setVariable(env, body, "noticePageInfo", pageInfo);
	}

}
