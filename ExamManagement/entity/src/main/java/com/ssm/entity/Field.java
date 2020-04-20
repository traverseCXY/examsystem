package com.ssm.entity;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Field {

	private int fieldId;
	private String fieldName;
	private String memo;
	private boolean state;
	private boolean removeable;
	public boolean isRemoveable() {
		return removeable;
	}
	public void setRemoveable(boolean removeable) {
		this.removeable = removeable;
	}
	public int getFieldId() {
		return fieldId;
	}
	public void setFieldId(int fieldId) {
		this.fieldId = fieldId;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public boolean isState() {
		return state;
	}
	public void setState(boolean state) {
		this.state = state;
	}
	public String toString(){
		System.out.println("fieldId = " + fieldId);
		return (this.fieldId+"-"+this.fieldName+"-"+this.memo+"-"+this.removeable).toString();
	}
}
