package com.lytips.base;

import java.io.IOException;
import java.util.Map;

import freemarker.core.Environment;
import freemarker.ext.beans.BeansWrapper;
import freemarker.ext.beans.BeansWrapperBuilder;
import freemarker.template.Configuration;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.utility.DeepUnwrap;

public abstract class BaseDirective implements TemplateDirectiveModel {
	
	@SuppressWarnings("rawtypes")
	protected Object getParameter(Map params, String paramName) 
			throws TemplateModelException {
		TemplateModel model = (TemplateModel) params.get(paramName);
		Object position = DeepUnwrap.unwrap(model);
		return position;
	}
	
	protected void setVariable(Environment env, TemplateDirectiveBody body, 
			String name, Object value) 
	throws TemplateException, IOException {
		// 构建一个TemplateModel
		BeansWrapper beansWrapper = new BeansWrapperBuilder(Configuration.VERSION_2_3_21).build();
		// 获取数据
		TemplateModel templateModel = beansWrapper.wrap(value);
		// 设置输出的内容
		env.setVariable(name, templateModel);
		// 输出
		body.render(env.getOut());
	}
}
