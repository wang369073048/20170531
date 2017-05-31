package com.asdc.lrm.action;

import java.util.List;
import java.util.Map;

import com.asdc.lrm.entity.SysMenuEntity;
import com.asdc.lrm.entity.UserEntity;
import com.asdc.lrm.service.LoginService;
import com.asdc.lrm.service.SysMenuService;
import com.asdc.lrm.util.UtilException;
import com.asdc.lrm.util.UtilHTTP;
import com.asdc.lrm.util.UtilSession;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("unchecked")
public class LoginAction extends ActionSupport{

	private static final long serialVersionUID = 1L;
	
	private LoginService loginService;
	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}
	
	private SysMenuService sysMenuService;
	public void setSysMenuService(SysMenuService sysMenuService) {
		this.sysMenuService = sysMenuService;
	}

	private String loginName;
	private String password;
	private String message;
	
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	/**
	 * 用户登陆
	 * @return
	 */
	public String login(){
		try {
			loginService.lonig(loginName, password);
			return "admin";
		}catch (UtilException e){
			message = e.getErrorKey();
			return INPUT;
		}
	}
	
	/**
	 * 获取当前登陆用户可访问的一级菜单
	 * @return
	 */
	public String firstMenu(){
		try{
			List<SysMenuEntity> menuList = loginService.firstMenu();
			UtilHTTP.getHttpServletRequest().setAttribute("menuList", menuList);
		}catch (RuntimeException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 获取当前登陆用户可访问的二级菜单
	 * @return
	 */
	public String secondMenu(){
		try{
			if(UtilSession.getUserId()==0)
				return INPUT;
				
			String parentId = UtilHTTP.getHttpServletRequest().getParameter("parentId");
			List<SysMenuEntity> menuList = loginService.secondMenu(Long.valueOf(parentId));
			UtilHTTP.getHttpServletRequest().setAttribute("menuList", menuList);
			
			SysMenuEntity parentMenu = sysMenuService.getById(Long.valueOf(parentId));
			UtilHTTP.getHttpServletRequest().setAttribute("parentMenu", parentMenu.getMenuName());
			
			return SUCCESS;
		}catch (RuntimeException e) {
			e.printStackTrace();
			return INPUT;
		}
	}
	
	/**
	 * 用户退出登陆
	 * @return
	 */
	public String logout(){
		try{
			ActionContext actionContext = ActionContext.getContext();
			Map session = actionContext.getSession();
			UserEntity user = (UserEntity)session.get("userEntity");
			if(user != null){
				session.remove("userEntity");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
}