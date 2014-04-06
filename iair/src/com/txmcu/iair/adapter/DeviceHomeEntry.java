package com.txmcu.iair.adapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DeviceHomeEntry  implements Serializable {

	private static final long	serialVersionUID	= 7679994356736512440L;
//	private static int imaxid = 0;
	
	public DeviceHomeEntry() {
	//	this.id = imaxid++;
		
	}
	public DeviceHomeEntry(int type) {
		this.type = type;
			
		}
	
	public Home home;
	public int type=0;	//0-old home,1-new home,2-buy device
	

	
}
