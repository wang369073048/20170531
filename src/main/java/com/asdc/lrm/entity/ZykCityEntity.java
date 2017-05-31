package com.asdc.lrm.entity;


public class ZykCityEntity{

	private String cityId;								//省市Id
	private String name;								//名称
	private int type;									//类型(1.省或直辖市；2.市)
	private String parentId;							//父级Id
	
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
}