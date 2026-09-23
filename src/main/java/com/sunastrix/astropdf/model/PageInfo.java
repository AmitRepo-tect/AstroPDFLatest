package com.sunastrix.astropdf.model;

import org.apache.pdfbox.pdmodel.PDPage;

public class PageInfo {
	String pageTitle;
	PDPage page;
	int startPageNo;

	public String getPageTitle() {
		return pageTitle;
	}

	public void setPageTitle(String pageTitle) {
		this.pageTitle = pageTitle;
	}

	public PDPage getPage() {
		return page;
	}

	public void setPage(PDPage page) {
		this.page = page;
	}

	public int getStartPageNo() {
		return startPageNo;
	}

	public void setStartPageNo(int startPageNo) {
		this.startPageNo = startPageNo;
	}

}
