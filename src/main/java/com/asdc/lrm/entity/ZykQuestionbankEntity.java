package com.asdc.lrm.entity;


public class ZykQuestionbankEntity{

	private String qbId;								//专业Id
	private String zykId;								//资源库id
	private String title;								//专业名称
	private int questionNum;							//题库题目数量
	private int objQuesNum;								//客观题数量
	private int subQuesNum;								//主观题数量
	private int citedQuesNum;							//被测试、练习引用题目数量
	private int quesUsingNum;							//题目使用总次数
	
	public String getQbId() {
		return qbId;
	}
	public void setQbId(String qbId) {
		this.qbId = qbId;
	}
	public String getZykId() {
		return zykId;
	}
	public void setZykId(String zykId) {
		this.zykId = zykId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getQuestionNum() {
		return questionNum;
	}
	public void setQuestionNum(int questionNum) {
		this.questionNum = questionNum;
	}
	public int getObjQuesNum() {
		return objQuesNum;
	}
	public void setObjQuesNum(int objQuesNum) {
		this.objQuesNum = objQuesNum;
	}
	public int getSubQuesNum() {
		return subQuesNum;
	}
	public void setSubQuesNum(int subQuesNum) {
		this.subQuesNum = subQuesNum;
	}
	public int getCitedQuesNum() {
		return citedQuesNum;
	}
	public void setCitedQuesNum(int citedQuesNum) {
		this.citedQuesNum = citedQuesNum;
	}
	public int getQuesUsingNum() {
		return quesUsingNum;
	}
	public void setQuesUsingNum(int quesUsingNum) {
		this.quesUsingNum = quesUsingNum;
	}
	
}