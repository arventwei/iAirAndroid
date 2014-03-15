package com.txmcu.iair.adapter;

import java.io.Serializable;

public class City  implements Serializable {

	private static final long	serialVersionUID	= 7973994356736512440L;

	private Integer id;
	
	private String title;
	
	private String name;
	
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
