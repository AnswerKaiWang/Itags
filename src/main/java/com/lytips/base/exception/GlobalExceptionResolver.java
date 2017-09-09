package com.lytips.base.exception;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.alibaba.fastjson.JSON;
import com.lytips.ITags.constant.ItagsConstant;
import com.lytips.ITags.model.MessageModel;

/**
 * 全局异常实现
 * @author lp
 *
 */
public class GlobalExceptionResolver extends SimpleMappingExceptionResolver {

	@Override
	protected ModelAndView doResolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		
		//  实现全局异常统一处理  普通请求  ajax 请求
		String accept=request.getHeader("Accept");
		//String contentType=request.getHeader("Content-Type");
		String xRequestedWith=request.getHeader("X-Requested-With");
		String viewName=determineViewName(ex, request);//获取默认视图
		ModelAndView mv=new ModelAndView(viewName);
		if(null!=accept&&accept.contains("application/json")&&
		  null!=xRequestedWith&&xRequestedWith.contains("XMLHttpRequest")){
			// ajax 请求  json 形式返回异常信息
			MessageModel messageModel=new MessageModel();
			
			messageModel.setResultCode(ItagsConstant.OPTIONS_FAILURE_CODE);
			messageModel.setMsg(ex.getMessage());//获取异常信息
			
			PrintWriter pw=null;
			try {
				//设置编码
				response.setCharacterEncoding("utf-8");
				pw=response.getWriter();
				pw.print(JSON.toJSON(messageModel));//
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				pw.flush();
				if(null!=pw){
					pw.close();
				}
			}
			return null;
		}else{
			mv.addObject("ex", ex);
			//  判断异常类型
			if(ex instanceof ParamsException){
				mv.addObject("errorMsg", ex.getMessage());
				mv.addObject("errorCode", ItagsConstant.OPTIONS_FAILURE_CODE);
				mv.addObject("ctx", request.getContextPath());
				mv.addObject("uri", request.getRequestURI());//  "/main"  /user/addUser
				mv.setViewName("error");
				return mv;
			}
			
			
			return mv;
		}
		
		
		
		
		
	}
	
	
	

}
