package com.ai.slp.order.search.bo.prod;

import com.google.gson.annotations.Expose;

/**
 * Created by xin on 16-5-17.
 */
public class ProdAudiencesSes {
	/**
	 * 用户权限
	 */
    @Expose
    private String audiencecode;
    /**
     * 用户ID
     */
    @Expose
    private String userid;

    public ProdAudiencesSes() {
        super();
    }


    public ProdAudiencesSes(String audiencecode, String userid) {
        this.audiencecode = audiencecode;
        this.userid = userid;
    }


    public String getAudiencecode() {
        return audiencecode;
    }


    public void setAudiencecode(String audiencecode) {
        this.audiencecode = audiencecode;
    }


    public String getUserid() {
        return userid;
    }


    public void setUserid(String userid) {
        this.userid = userid;
    }
    
}
