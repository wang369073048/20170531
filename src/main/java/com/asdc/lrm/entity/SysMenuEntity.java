package com.asdc.lrm.entity;

import com.asdc.lrm.entity.common.BaseEntity;

public class SysMenuEntity extends BaseEntity{
	
	private long parentId;					// 父级菜单ID
	private String menuName;				// 中文名称
	private String menuEnName;				// 英文名称
	private String menuUrl;					// 连接地址
	private int sortNumber;					// 排序号
	private String menuImg;					// 图片路径
	
	public long getParentId() {
		return parentId;
	}
	public void setParentId(long parentId) {
		this.parentId = parentId;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public String getMenuUrl() {
		return menuUrl;
	}
	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}
	public int getSortNumber() {
		return sortNumber;
	}
	public void setSortNumber(int sortNumber) {
		this.sortNumber = sortNumber;
	}
	public String getMenuImg() {
		return menuImg;
	}
	public void setMenuImg(String menuImg) {
		this.menuImg = menuImg;
	}
	public String getMenuEnName() {
		return menuEnName;
	}
	public void setMenuEnName(String menuEnName) {
		this.menuEnName = menuEnName;
	}
}