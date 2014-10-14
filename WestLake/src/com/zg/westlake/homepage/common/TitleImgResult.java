package com.zg.westlake.homepage.common;

import java.io.Serializable;

import android.graphics.Bitmap;

public class TitleImgResult implements Serializable {
	private static final long serialVersionUID = 1L;

	private String imgId;
	private String imgName;
	private Bitmap bitmap;
	private String imgDate;

	public String getImgId() {
		return imgId;
	}

	public void setImgId(String imgId) {
		this.imgId = imgId;
	}

	public String getImgName() {
		return imgName;
	}

	public void setImgName(String imgName) {
		this.imgName = imgName;
	}

	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	public String getImgDate() {
		return imgDate;
	}

	public void setImgDate(String imgDate) {
		this.imgDate = imgDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
