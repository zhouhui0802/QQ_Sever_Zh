package com.zh.common;

import java.io.File;

public class Message implements java.io.Serializable{
	
	//�û������ķ�ʽ
	private String mesType;
	
	//�����û������Ƿ�����
	private String sender;
	
	//��ȡ�ߣ�������
	private String getter;
	
	//���͵�����
	private String con;
	
	//���͵�ʱ��
	private String sendTime;
	
	//Ⱥ����Ϣ
	private String MultiChat;
	
	public String getMultiChat() {
		return MultiChat;
	}

	public void setMultiChat(String multiChat) {
		MultiChat = multiChat;
	}



	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	//�ļ��Ĵ���
	private File file;
	
	public String getMesType() {
		return mesType;
	}

	public void setMesType(String mesType) {
		this.mesType = mesType;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getGetter() {
		return getter;
	}

	public void setGetter(String getter) {
		this.getter = getter;
	}

	public String getCon() {
		return con;
	}

	public void setCon(String con) {
		this.con = con;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}
	
	
}
