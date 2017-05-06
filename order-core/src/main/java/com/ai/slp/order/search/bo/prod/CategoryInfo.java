package com.ai.slp.order.search.bo.prod;

import com.google.gson.annotations.Expose;

/**
 * Created by xin on 16-5-13.
 */
public class CategoryInfo {
	/**
	 * 类目ID
	 */
    @Expose
    private String categorid;
    /**
	 * 类目名称
	 */
    private String categoryname;


    public CategoryInfo() {
        super();
    }


    public CategoryInfo(String categorid, String categoryname) {
        this.categorid = categorid;
        this.categoryname = categoryname;
    }


	public String getCategorid() {
		return categorid;
	}


	public void setCategorid(String categorid) {
		this.categorid = categorid;
	}


	public String getCategoryname() {
		return categoryname;
	}


	public void setCategoryname(String categoryname) {
		this.categoryname = categoryname;
	}
}
