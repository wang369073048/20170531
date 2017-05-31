package com.asdc.lrm.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.asdc.lrm.entity.UserEntity;

public class LoginFilter extends HttpServlet implements Filter {

	private static final long serialVersionUID = 1L;
	private String value;

    public void init(FilterConfig filterConfig) throws ServletException {
    	String parameter = filterConfig.getInitParameter("except");
    	if(parameter!=null && !parameter.isEmpty()){
    		value = parameter;
    	}
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
    	HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        String url = request.getServletPath();
        String contextPath = request.getContextPath();
        
        if(url.equals(""))
        	url+="/";
        
        if(url.startsWith("/")&&!url.startsWith("/login")){//若访问后台资源 过滤到login
        	if(value != null && url.contains(value)){
        		filterChain.doFilter(servletRequest, servletResponse);
        		return ;
        	}else{
        		UserEntity user= (UserEntity) session.getAttribute("userEntity");
        		if(user==null){//转入管理员登陆页面
        			java.io.PrintWriter out = response.getWriter();
        			out.println("<html>");
        			out.println("<script>");
        			out.println("window.top.open ('"+contextPath+"/login.jsp','_top')");
        			out.println("</script>");
        			out.println("</html>");
        			return;
        		}
        	}
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
