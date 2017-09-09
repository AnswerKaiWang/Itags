package com.lytips.base.exception;

import java.io.IOException;
import java.io.Writer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import freemarker.core.Environment;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

public class FreemarkerExceptionHandler implements TemplateExceptionHandler {
	
	private static final Logger log = LoggerFactory  
            .getLogger(FreemarkerExceptionHandler.class);  
	
	@Override
	public void handleTemplateException(TemplateException te, Environment env, Writer out) throws TemplateException {
		log.warn("[Freemarker Error: " + te.getMessage() + "]");  
        try {  
            out.write("[服务器资源出现问题]");  
            log.error("[出错了，请联系网站管理员]", te);  
        } catch (IOException e) {  
            throw new TemplateException(  
                    "Failed to print error message. Cause: " + e, env);  
        }  
	}

	
	
	
}
