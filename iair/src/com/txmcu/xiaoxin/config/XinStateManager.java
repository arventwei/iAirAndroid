package com.txmcu.xiaoxin.config;

import java.util.List;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.util.Log;

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
	
	public enum ConfigType {
		Succeed,
		Failed,
		//Failed_Connect_XiaoXin,
	//	Failed_TimeOut,
		//Failed_XiaoXinConfig
	}
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
	public static XinStateManager getInstance(Context context,XinOperations operations) {

		if (instance == null) {
			instance = new XinStateManager(context,operations);

		}
		return instance;
	}

	private XinStateManager(Context context,XinOperations operations) {
		this.context = context;
		this.operations = operations;
		wifiHotM = WifiHotManager.getInstance(this.context, XinStateManager.this);

		wifiHotM.scanWifiHot();
	}
	
	public void Init()
	{
		wifiHotM.scanWifiHot();
		udpclient = new Udpclient();
		udpclient.operations= this;
		//backupCurrentWifiState();
	}
	public void Config(String SSID,String Pwd)
	{
		wifibackupPwd = Pwd;
		wifiHotM.connectToHotpot("xiaoxin_AP", "xiaoxinap");
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
	int wifibackupNetId=-1;
	String wifibackupSSID;
	String wifibackupChannel;
	String wifibackupPwd;
	String wifibackupAuthMode;
	String wifibackupEncrypType;
	private void backupCurrentWifiState(WifiInfo info,List<ScanResult> scannlist ) {
		wifibackupNetId=-1;
		wifibackupSSID="";
		if (info!=null) {
			wifibackupNetId = info.getNetworkId();
			wifibackupSSID = info.getSSID();
			
			
			wifibackupChannel = "6";
			for (ScanResult sr : scannlist) 
			{
				if (sr.SSID.equalsIgnoreCase(wifibackupSSID))
				{
					int  channel =  getChannel(sr.frequency);
					wifibackupChannel= String.valueOf(channel);
					break;
				}
			}
			
			List<String> authInfo = wifiHotM.getAuthMode(wifibackupSSID);
			wifibackupAuthMode=authInfo.get(0);
			wifibackupEncrypType=authInfo.get(1);
			
		}
	}
	private void restoreCurrentWifiState() {
		if(wifibackupNetId!=-1)
			wifiHotM.enableNetWorkById(wifibackupNetId);
	}
	// wifi 热点扫描回调
	@Override
	public void disPlayWifiScanResult(List<ScanResult> wifiList) {

		wifiHotM.unRegisterWifiScanBroadCast();
		Log.i(TAG, " 热点扫描结果 �?= " + wifiList);
		backupCurrentWifiState(wifiHotM.getConnectWifiInfo(),wifiList);
		if (wifibackupSSID.length()==0) {
			operations.initResult(false,"");
		}
		else {
			operations.initResult(true,wifibackupSSID);
		}
		
		
		
		//wifiHotM.enableNetwork(SSID, password)
	}
	
	// wifi 
	@Override
	public boolean disPlayWifiConResult(boolean result, WifiInfo wifiInfo) {

		Log.i(TAG, "热点连接回调函数");
		wifiHotM.unRegisterWifiConnectBroadCast();
		udpclient.setSendWifiInfo(wifibackupSSID, wifibackupPwd,
				wifibackupAuthMode, wifibackupEncrypType, wifibackupChannel);
		
		udpclient.Looper();
		return false;
	}

	// wifi 
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
	
	public static int getChannel(int frequency)
    {
            int channel = 0;
            switch(frequency){
            case 2412:
                    channel = 1;
                    break;
            case 2417:
                    channel = 2;
                    break;
            case 2422:
                    channel = 3;
                    break;
            case 2427:
                    channel = 4;
                    break;
            case 2432:
                    channel = 5;
                    break;
            case 2437:
                    channel = 6;
                    break;
            case 2442:
                    channel = 7;
                    break;
            case 2447:
                    channel = 8;
                    break;
            case 2452:
                    channel = 9;
                    break;
            case 2457:
                    channel = 10;
                    break;
            case 2462:
                    channel = 11;
                    break;
            case 2467:
                    channel = 12;
                    break;
            case 2472:
                    channel = 13;
                    break;
            case 2484:
                    channel = 14;
                    break;
            }
            return channel;
    }

}
