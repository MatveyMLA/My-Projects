package com.mati.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.mati.util.Constans;
import com.mati.util.HttpUtils;
@Component
public class LoginFilter implements Filter {

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		Cookie[] cookies = httpRequest.getCookies();
		if(HttpUtils.isLoginCookieExist(cookies, Constans.COOKIE_KEY)){
			chain.doFilter(request, response);
		}
		else{
			HttpServletResponse httpResponse = (HttpServletResponse)response;
			httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
		}
		
	}

	public void init(FilterConfig fConfig) throws ServletException {}
	public void destroy() {}
}
