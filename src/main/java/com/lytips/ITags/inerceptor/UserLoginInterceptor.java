package com.lytips.ITags.inerceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import com.lytips.ITags.constant.ItagsConstant;
import com.lytips.ITags.controller.UserController;
import com.lytips.ITags.entity.User;
import com.lytips.ITags.model.MessageModel;
import com.lytips.ITags.utils.AssertUtil;
import com.lytips.ITags.utils.CookieUtil;
import com.lytips.ITags.utils.UserBase64;

public class UserLoginInterceptor implements HandlerInterceptor{
	
	@Autowired
	private UserController userController;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if(null == request.getSession().getAttribute("userId")) {
			String userName = CookieUtil.getCookieValue(request, "userName");
			String userPwd = CookieUtil.getCookieValue(request, "userPwd");
			userName = UserBase64.decode(userName);
			userPwd = UserBase64.decode(userPwd);
			AssertUtil.isTrue(StringUtils.isEmpty(userPwd) || 
					StringUtils.isEmpty(userPwd), ItagsConstant.LOGIN_FIRST);
			MessageModel messageModel = userController.userLogin(new User(userName, userPwd), request);
			if(messageModel.getResultCode() == 1) {
				request.getRequestDispatcher("index").forward(request, response);
			}

		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

}
