package com.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class CheckLoginInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		// 1.获取session对象
		HttpSession session = request.getSession();
		// 2.判断session域中是否有登录标记
		Object obj = session.getAttribute("userinfo");
		if (obj != null) {
			return true;
		}
		// 3.前往指定的结果视图登录
		// request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request,
		// response);
		response.sendRedirect(request.getContextPath() + "/login");
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
