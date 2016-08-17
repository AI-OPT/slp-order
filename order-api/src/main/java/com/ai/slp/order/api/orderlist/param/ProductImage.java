package com.ai.slp.order.api.orderlist.param;

import java.io.Serializable;

public class ProductImage implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
     * 上传后的图片ID
     */
    private String vfsId;

    /**
     * 图片扩展名
     */
    private String picType;

	public String getVfsId() {
		return vfsId;
	}

	public void setVfsId(String vfsId) {
		this.vfsId = vfsId;
	}

	public String getPicType() {
		return picType;
	}

	public void setPicType(String picType) {
		this.picType = picType;
	}

}
