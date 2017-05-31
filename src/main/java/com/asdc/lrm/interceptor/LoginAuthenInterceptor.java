package com.asdc.lrm.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class LoginAuthenInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 1L;

	public String intercept(ActionInvocation invocation) throws Exception {
//		ActionContext context = ActionContext.getContext();
//		Map session = context.getSession();
//		Object user = session.get("userEntity");
//		if (user == null) {
//			return "login";
//		}
		return invocation.invoke();
	}

}
