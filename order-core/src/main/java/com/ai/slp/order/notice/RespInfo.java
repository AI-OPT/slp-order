package com.ai.slp.order.notice;

public class RespInfo {
	private GrpHdr header;
	private GrpBody body;

	public GrpHdr getHeader() {
		return header;
	}

	public void setHeader(GrpHdr header) {
		this.header = header;
	}

	public GrpBody getBody() {
		return body;
	}

	public void setBody(GrpBody body) {
		this.body = body;
	}

}
