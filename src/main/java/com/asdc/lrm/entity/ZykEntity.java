package com.asdc.lrm.entity;

import java.util.Date;

public class ZykEntity{

	private String id;									//主键Id
	private String zykId;								//资源库Id
	private String cityId;								//省市Id
	private String fullname;							//专业名称
	private String specialtyCategory;					//所属专业大类名称
	private String specialtyName;						//所属专业名称
	private String moreSpecialty;						//适用的专业目录名称
	private String website;								//资源库访问地址
	private String instituteInCharge;					//项目主持单位
	private String personInCharge;						//项目主持人
	private String cooperationInstitute;				//联合建设单位
	private Date modifiedDate;							//申请日期
	private Date declarationDate;						//申报日期
	private Date projectDate;							//立项时间
	private Date accepDate;								//验收时间
	private String status;								//资源库状态（1：申报中、2：待验收、3：已验收）
	private String province;							//临时存储 省 
	private String city;								//临时存储 市
	private int sendStatus;								//下发文件状态 1：成功 ，2：失败
	private Date sendLastTime;							//下发文件时间
	private String zykNum;								//资源库编号
	
	private String sendColor;
	private int sendFlag;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getZykId() {
		return zykId;
	}
	public void setZykId(String zykId) {
		this.zykId = zykId;
	}
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getSpecialtyCategory() {
		return specialtyCategory;
	}
	public void setSpecialtyCategory(String specialtyCategory) {
		this.specialtyCategory = specialtyCategory;
	}
	public String getSpecialtyName() {
		return specialtyName;
	}
	public void setSpecialtyName(String specialtyName) {
		this.specialtyName = specialtyName;
	}
	public String getMoreSpecialty() {
		return moreSpecialty;
	}
	public void setMoreSpecialty(String moreSpecialty) {
		this.moreSpecialty = moreSpecialty;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public String getInstituteInCharge() {
		return instituteInCharge;
	}
	public void setInstituteInCharge(String instituteInCharge) {
		this.instituteInCharge = instituteInCharge;
	}
	public String getPersonInCharge() {
		return personInCharge;
	}
	public void setPersonInCharge(String personInCharge) {
		this.personInCharge = personInCharge;
	}
	public String getCooperationInstitute() {
		return cooperationInstitute;
	}
	public void setCooperationInstitute(String cooperationInstitute) {
		this.cooperationInstitute = cooperationInstitute;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public Date getDeclarationDate() {
		return declarationDate;
	}
	public void setDeclarationDate(Date declarationDate) {
		this.declarationDate = declarationDate;
	}
	public Date getProjectDate() {
		return projectDate;
	}
	public void setProjectDate(Date projectDate) {
		this.projectDate = projectDate;
	}
	public Date getAccepDate() {
		return accepDate;
	}
	public void setAccepDate(Date accepDate) {
		this.accepDate = accepDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public int getSendStatus() {
		return sendStatus;
	}
	public void setSendStatus(int sendStatus) {
		this.sendStatus = sendStatus;
	}
	public Date getSendLastTime() {
		return sendLastTime;
	}
	public void setSendLastTime(Date sendLastTime) {
		this.sendLastTime = sendLastTime;
	}
	public String getSendColor() {
		return sendColor;
	}
	public void setSendColor(String sendColor) {
		this.sendColor = sendColor;
	}
	public int getSendFlag() {
		return sendFlag;
	}
	public void setSendFlag(int sendFlag) {
		this.sendFlag = sendFlag;
	}
	public String getZykNum() {
		return zykNum;
	}
	public void setZykNum(String zykNum) {
		this.zykNum = zykNum;
	}
}