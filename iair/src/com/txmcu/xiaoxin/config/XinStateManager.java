package com.txmcu.xiaoxin.config;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.util.Log;

import com.txmcu.iair.common.Util;
import com.txmcu.iair.common.iAirApplication;
import com.txmcu.iair.common.iAirConstants;
import com.txmcu.xiaoxin.config.wifi.WifiHotManager;
import com.txmcu.xiaoxin.config.wifi.WifiHotManager.OpretionsType;
import com.txmcu.xiaoxin.config.wifi.WifiHotManager.WifiBroadCastOperations;

public class XinStateManager 
implements WifiBroadCastOperations , Udpclient.UdpclientOperations{
	
	private Context context;
	private XinOperations operations;
	private WifiHotManager wifiHotM;
	private static XinStateManager instance = null;
	private Udpclient udpclient = null;
	
	static String TAG = "XinStateManager";
	
	private iAirApplication application;
	
	public enum ConfigType {
		Succeed,
		Failed,
		//Failed_Connect_XiaoXin,
	//	Failed_TimeOut,
		//Failed_XiaoXinConfig
	}
	public enum State
	{
		Init,
		Config,
	}
	public State mCurState = State.Init;
	public static int TimeOutSecond = 120;
	
	public static interface XinOperations {

		/**
		 * @param init callback ,close wait dialog
		 */
		public void initResult(boolean result,String SSID);

		/**
		 * @param invoke callback
		 */
		public void configResult(ConfigType type );
	}
	public static void destroy() {
		instance = null;
	}
	public static XinStateManager getInstance(Activity context,XinOperations operations) {

		if (instance == null) {
			instance = new XinStateManager(context,operations);

		}
		return instance;
	}

	private XinStateManager(Activity context,XinOperations operations) {
		this.context = context;
		this.operations = operations;
		this.application = ((iAirApplication)context.getApplication());
		wifiHotM = WifiHotManager.getInstance(this.context, XinStateManager.this);
		WifiInfo curWifiInfo = wifiHotM.getConnectWifiInfo();
		if(curWifiInfo!=null&&curWifiInfo.getSSID().endsWith(iAirConstants.XIAOXIN_SSID))
		{
			wifiHotM.removeWifiInfo(curWifiInfo.getNetworkId());
		}
		wifiHotM.scanWifiHot();
	}
	
	public void Init()
	{
		mCurState = State.Init;
		wifiHotM.scanWifiHot();
		udpclient = new Udpclient(this,this);
		//udpclient.operations= this;
		//backupCurrentWifiState();
	}
	public void Config(String SSID,String Pwd,String _userid,String _sn)
	{
		mCurState = State.Config;
		application.setWifibackupPwd(Pwd);
		//wifibackupPwd = Pwd;
		userid=_userid;
		sn=_sn;
		wifiHotM.connectToHotpot(iAirConstants.XIAOXIN_SSID, iAirConstants.XIAOXIN_PWD);
	}
	public void Destroy()
	{
		restoreCurrentWifiState();
		wifiHotM.unRegisterWifiConnectBroadCast();
		wifiHotM.unRegisterWifiScanBroadCast();
		wifiHotM.unRegisterWifiStateBroadCast();
		WifiHotManager.destroy();
		XinStateManager.destroy();
		
	}
//	int wifibackupNetId=-1;
//	String wifibackupSSID;
//	String wifibackupChannel;
//	String wifibackupPwd;
//	String wifibackupAuthMode;
//	String wifibackupEncrypType;
	
	String userid;
	String sn;
	private void backupCurrentWifiState(WifiInfo info,List<ScanResult> scannlist ) 
	{
		//wifibackupNetId=-1;
		application.setWifibackupSSID("");
		if (info!=null) {
			application.setWifibackupNetId(info.getNetworkId());
			//wifibackupNetId = ;
			application.setWifibackupSSID(info.getSSID());
			//wifibackupSSID = info.getSSID();
			
			application.setWifibackupChannel("6");
			//wifibackupChannel = "6";
			for (ScanResult sr : scannlist) 
			{
				if (sr.SSID.equalsIgnoreCase(info.getSSID()))
				{
					int  channel =  Util.getChannel(sr.frequency);
					application.setWifibackupChannel(String.valueOf(channel));
					break;
				}
			}
			
			List<String> authInfo = wifiHotM.getAuthMode(info.getSSID());
			application.setWifibackupAuthMode(authInfo.get(0));
			application.setWifibackupEncrypType(authInfo.get(1));
			
		}
	}
	public void restoreCurrentWifiState() {
		if(application.getWifibackupNetId()!=-1)
			wifiHotM.enableNetWorkById(application.getWifibackupNetId());
	}
	// wifi scan callback
	@Override
	public void disPlayWifiScanResult(List<ScanResult> wifiList) {

		wifiHotM.unRegisterWifiScanBroadCast();
		Log.i(TAG, " scan: = " + wifiList);
		if(mCurState == State.Init)
		{
			backupCurrentWifiState(wifiHotM.getConnectWifiInfo(),wifiList);
			if (application.getWifibackupSSID().length()==0) {
				operations.initResult(false,"");
			}
			else {
				operations.initResult(true,application.getWifibackupSSID());
			}
		}
		
		
		
		
		//wifiHotM.enableNetwork(SSID, password)
	}
	
	// wifi connect callback
	@Override
	public boolean disPlayWifiConResult(boolean result, WifiInfo wifiInfo) {

		Log.i(TAG, "disPlayWifiConResult");
		wifiHotM.unRegisterWifiConnectBroadCast();


		if(mCurState == State.Config)
		{
			udpclient.setSendWifiInfo(application.getWifibackupSSID(), application.getWifibackupPwd(),
					application.getWifibackupAuthMode(), application.getWifibackupEncrypType(),
					application.getWifibackupChannel(),sn,userid);
			
			udpclient.Looper();
		}

		return false;
	}

	// wifi connect & scan ,when wifi enable
	@Override
	public void operationByType(OpretionsType type, String SSID,String pwd) {
		Log.i(TAG, "operationByType！type = " + type);

		if (type == OpretionsType.SCAN) {
			wifiHotM.scanWifiHot();
		}
		else {
			wifiHotM.connectToHotpot(SSID, pwd);
		}
	}
	@Override
	public void setState(boolean result, String exception) {
		// TODO Auto-generated method stub
		if (result && exception.startsWith("Ok")) {
			operations.configResult(ConfigType.Succeed);
		}
		else {
			operations.configResult(ConfigType.Failed);
		}
	}
	
}
