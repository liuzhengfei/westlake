package com.zg.westlake.model;

import android.graphics.Bitmap;

public class CenterStoryModel {

	private Bitmap userImg;
	private Bitmap contentImg;
	private String contentImgUrl;
	private String author;
	private String createDate;
	private String content;
	private String Id;
	private boolean isOpening; // 记录当前item的 展开状态

	public Bitmap getUserImg() {
		return userImg;
	}

	public void setUserImg(Bitmap userImg) {
		this.userImg = userImg;
	}

	public Bitmap getContentImg() {
		return contentImg;
	}

	public void setContentImg(Bitmap contentImg) {
		this.contentImg = contentImg;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean isOpening() {
		return isOpening;
	}

	public void setOpening(boolean isOpening) {
		this.isOpening = isOpening;
	}

	public String getContentImgUrl() {
		return contentImgUrl;
	}

	public void setContentImgUrl(String contentImgUrl) {
		this.contentImgUrl = contentImgUrl;
	}

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

}
