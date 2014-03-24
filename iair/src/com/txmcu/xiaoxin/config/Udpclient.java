package com.txmcu.xiaoxin.config;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import android.R.integer;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.net.wifi.WifiInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.txmcu.iair.activity.DeviceAddActivity;
import com.txmcu.iair.common.iAirConstants;
import com.txmcu.iair.common.iAirUtil;
import com.txmcu.xiaoxin.config.XinStateManager.State;
import com.txmcu.xiaoxin.config.wifi.WifiHotManager;
//import android.widget.Toast;

public class Udpclient {
	
	public static interface UdpclientOperations {

		/**
		 * @param init callback ,close wait dialog
		 */
		public void setState(boolean result,String exception);
		
		public void logudp(String msg);

		
	}
	Activity activity;
	XinStateManager xinMgr;
	public UdpclientOperations operations;
	//public Context contentView;
	private static String TAG = "Udpclient";
	public byte[] send_msg ;
	// private AsyncTask<Void, Void, Void> async_cient;
    
    public String recvingMsg;
    DatagramSocket ds = null;
    InetAddress receiverAddress = null;
    int stateCode = 0;
    String sn;
    String userid;
    WifiHotManager wifiHotM;
    
    CountDownTimer connectApTimer;
    public Udpclient(XinStateManager xinstateMgr,UdpclientOperations opertion,Activity activity,WifiHotManager wifiM)
    {
    	this.xinMgr = xinstateMgr;
    	this.operations = opertion;
    	this.activity = activity;
    	this.wifiHotM = wifiM;
    	
    }
    public void setSendWifiInfo(String ssid,String pwd,String auth_mode,String encryp_type,
    		String channel,String _sn,String _userid)
    {
    	sn = _sn;
    	userid=_userid;
    	send_msg =  new byte[105];
    	int len=0;

    	byte[] bytes =ssid.getBytes();
    	System.arraycopy(bytes,0,send_msg,len,bytes.length);len+=20;
    	bytes =pwd.getBytes();
    	System.arraycopy(bytes,0,send_msg,len,bytes.length);len+=20;
    	bytes =auth_mode.getBytes();
    	System.arraycopy(bytes,0,send_msg,len,bytes.length);len+=10;
    	bytes =encryp_type.getBytes();
    	System.arraycopy(bytes,0,send_msg,len,bytes.length);len+=10;
    	bytes =channel.getBytes();


    	System.arraycopy(bytes,0,send_msg,len,bytes.length);len+=5;
    	
    	bytes =sn.getBytes();
    	System.arraycopy(bytes,0,send_msg,len,bytes.length);len+=20;
    	
    	bytes =userid.getBytes();
    	System.arraycopy(bytes,0,send_msg,len,bytes.length);len+=20;

    	recvingMsg = "";
    	//stateCode=100;
    	
    	wifiHotM.connectToHotpot(iAirConstants.XIAOXIN_SSID, iAirConstants.XIAOXIN_PWD);
    	
    	
    	
    	postMessage(initApState);
    	//setStopLoop(0,"");
    	
    	
    	
    }
    static final  int initApState = 100;
    static final int sendState = 50;
    static final int queryState = 30;
    static final int endState = 10;
    
    static final int initTime = 18*1000;
    static final int sendTime = 18*1000;
    static final int QueryTime = 84*1000;
//    private void setStopLoop(int errorcode,String excpetion)
//    {
//    	stateCode = errorcode;
//    	String log = "setStopLoop errorcode:"+errorcode+":"+recvingMsg+":"+excpetion;
//    	Log.d(TAG,log);
//    	operations.logudp(log);
//    	if (stateCode == 2) {
//    		operations.setState(true,excpetion);
//		}
//    	else if (stateCode<0) {
//			operations.setState(false,excpetion);
//	    	if(async_cient!=null)
//	    		async_cient.cancel(false);
//		} 
//    	//Toast.makeText(getapp, text, duration)
//    	//Toast.makeText(contentView, log	, Toast.LENGTH_LONG).show();
//    }
    
    void postMessage(int code)
    {
    	if (stateCode == code) {
			return;
		}
    	 Message tempMsg = msghandler.obtainMessage();
	       tempMsg.what = code;
	      // tempMsg.obj = msg;
	       msghandler.sendMessage(tempMsg);
    }
    
    @SuppressLint("HandlerLeak")
	Handler msghandler = new Handler(){   
        @SuppressLint("NewApi")
		public void handleMessage(Message msg) {  
            switch (msg.what)
            { 
            case initApState:
            {
            	stateCode=initApState;
            	connectApTimer = new CountDownTimer(initTime, 3000) {

       		     public void onTick(long millisUntilFinished) {
       		    	 //initscanRetryTimes++;
       		    	 
       		    	 if(stateCode!=initApState)
       		    		 return;
       		    	 
       		    	 operations.logudp(iAirConstants.XIAOXIN_SSID+" dis connected");
       		    	 WifiInfo curWifi =wifiHotM.getConnectWifiInfo();
       		    	
       		    	 if( curWifi!=null && curWifi.getSSID()!=null  
       		    	 && curWifi.getSSID().equals(iAirConstants.XIAOXIN_SSID)
       		    	 && curWifi.getNetworkId()!=-1)
       		    	 {
       		    		 operations.logudp(iAirConstants.XIAOXIN_SSID+"  connected");
       		    		 postMessage(sendState);
       		    		

       		    	 }
       		    	 else {
       		    		 wifiHotM.connectToHotpot(iAirConstants.XIAOXIN_SSID, iAirConstants.XIAOXIN_PWD);
       				}
       		    	
       		     }

       		     public void onFinish() {
       		    	 if(stateCode==initApState)
       		    	 {
       		    		operations.setState(false,"connect ap time out");
       		    	 }
       		    	
       		     }
       		  }.start();
       		  break;
            }
            case sendState:
            {
            	stateCode=sendState;
            	connectApTimer = new CountDownTimer(sendTime, 3000) {

       		     public void onTick(long millisUntilFinished) {
       		    	 //initscanRetryTimes++;
       		    	 
       		    	 if(stateCode!=sendState)
       		    		 return;
       		    	 
       		    	 operations.logudp(iAirConstants.XIAOXIN_SSID+" send data");
       		    	 
       		    	AsyncTask<Void, Void, Void>  async_cient = new AsyncTask<Void, Void, Void>() 
       		             {
       		                 @Override
       		                 protected Void doInBackground(Void... params)
       		                 {  
       		                 	try 
       		     	            {
       		                     	receiverAddress = InetAddress.getByName(iAirConstants.XIAOXIN_IP);
       		                         ds = new DatagramSocket();
       		                         ds.setSoTimeout(3000);
       		                         
       		                         DatagramPacket dp;                          
    		                         dp = new DatagramPacket(send_msg, send_msg.length,
    		                         		receiverAddress, iAirConstants.XIAOXIN_PORT);
    		                         ds.setBroadcast(true);
    		                         ds.send(dp);
    		                         
    		                         byte[] receiveData = new byte[20];
       		     	            	 DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
       		     	            	 ds.receive(receivePacket);
       		     	            	 recvingMsg = new String( receivePacket.getData());
       		     	            	 
       		     	            	if(recvingMsg.startsWith("receive"))
       		                 		{
       		                 			//TODO RESTORE WIFI
       		                 			postMessage(queryState);
       		                 			//xinMgr.restoreCurrentWifiState();
       		                 			//setStopLoop(1,"");
       		                 		}
       		     	            	 
       		     	            }
       		                 	catch (SocketException e) 
       		                     {
       		                 	//	setStopLoop(-1,e.toString());
       		                     } catch (UnknownHostException e) {
       		     					// TODO Auto-generated catch block
       		                    // 	setStopLoop(-1,e.toString());
       		     				} catch (IOException e) {
       		     					// TODO Auto-generated catch block
      		                     	 //setStopLoop(-3,e.toString());
      		     				}
       		                 	
       		                 	
       		                 	
       		                 
       		                    return null;
       		                 }
       		               

       		                 protected void onPostExecute(Void result) 
       		                 {
       		                    super.onPostExecute(result);
       		                 }
       		             };

       		             if (Build.VERSION.SDK_INT >= 11) 
       		             	async_cient.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
       		             else 
       		             	async_cient.execute();
       		    	// iAirUtil.setProgressText(getString(R.string.add_device_cooldown)+(millisUntilFinished / 1000)+getString(R.string.second));
       		         //mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
       		     }

       		     public void onFinish() {
       		    	 if(stateCode==sendState)
       		    	 {
       		    		operations.setState(false,"send message xiaoxin time out");
       		    		
       		    	 }
       		    	
       		     }
       		  }.start();
       		 break; 
            }
                
            case queryState:
            {
            	
            	stateCode=queryState;
            	connectApTimer = new CountDownTimer(QueryTime, 4000) {

       		     public void onTick(long millisUntilFinished) {
       		    	 //initscanRetryTimes++;
       		    	 
       		    	 if(stateCode!=queryState)
       		    		 return;
       		    	 
       		    	xinMgr.restoreCurrentWifiState();
       		    	
       		    		XinServerManager.bind(activity, userid, sn,new XinServerManager.onSuccess() {
						
						@Override
						public void run(String response) {
							// TODO Auto-generated method stub
							if (response.equals("Ok")) {
								stateCode = endState;
								operations.setState(true,"Ok");
							}
							
						}
					});
       		    	
       		     }

       		     public void onFinish() {
       		    	 if(stateCode==queryState)
       		    	 {
       		    		operations.setState(false,"query sn time out");
       		    	 }
       		    	
       		     }
       		  }.start();
       		  break;
            }
            }      
            super.handleMessage(msg);  
        }  
          
    };
    
   
}
