package com.txmcu.iair.adapter;

import java.io.Serializable;

public class Device  implements Serializable {

	private static final long	serialVersionUID	= 7973999356736512441L;

	private Integer id;
	
	private String sn="";
	private String title="";
	
	private String name="";
	
	private int bitmapId;
	
	
	

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	public String getSn() {
		return sn;
	}

	/**
	 * @param id the id to set
	 */
	public void setSn(String sn) {
		this.sn = sn;
	}
	
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the bitmapId
	 */
	public int getBitmapId() {
		return bitmapId;
	}

	/**
	 * @param bitmapId the bitmapId to set
	 */
	public void setBitmapId(int bitmapId) {
		this.bitmapId = bitmapId;
	}
	
	
}
