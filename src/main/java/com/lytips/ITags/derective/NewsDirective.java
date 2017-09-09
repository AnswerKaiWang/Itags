package com.lytips.ITags.derective;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.lytips.ITags.utils.HttpUtils;
import com.lytips.base.BaseDirective;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public class NewsDirective extends BaseDirective {

	@Override
	public void execute(Environment env, @SuppressWarnings("rawtypes") Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
			throws TemplateException, IOException {
		String type = (String) getParameter(params, "type");
		String host = "http://toutiao-ali.juheapi.com";
	    String path = "/toutiao/index";
	    String method = "GET";
	    String appcode = "164b9ec267cf41b5b3bc5e770ceb999e";
	    Map<String, String> headers = new HashMap<String, String>();
	    //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
	    headers.put("Authorization", "APPCODE " + appcode);
	    Map<String, String> querys = new HashMap<String, String>();
	    querys.put("type", type);
	    HttpResponse response = null;
	    Object obj = null;
	    try {
	    	response = HttpUtils.doGet(host, path, method, headers, querys);
	    	obj = JSON.toJSON(EntityUtils.toString(response.getEntity()));
	    	System.err.println(EntityUtils.toString(response.getEntity()));
	    	
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	    setVariable(env, body, "news", obj);
	}

}
