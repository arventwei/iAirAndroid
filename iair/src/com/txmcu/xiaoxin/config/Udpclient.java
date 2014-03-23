package com.txmcu.xiaoxin.config;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import com.txmcu.iair.common.iAirConstants;
//import android.widget.Toast;

public class Udpclient {
	
	public static interface UdpclientOperations {

		/**
		 * @param init callback ,close wait dialog
		 */
		public void setState(boolean result,String exception);

		
	}
	Activity activity;
	XinStateManager xinMgr;
	public UdpclientOperations operations;
	//public Context contentView;
	private static String TAG = "Udpclient";
	public byte[] send_msg ;
	 private AsyncTask<Void, Void, Void> async_cient;
    
    public String recvingMsg;
    DatagramSocket ds = null;
    InetAddress receiverAddress = null;
    int stateCode = 0;
    String sn;
    String userid;
    
    public Udpclient(XinStateManager xinstateMgr,UdpclientOperations opertion,Activity activity)
    {
    	this.xinMgr = xinstateMgr;
    	this.operations = opertion;
    	this.activity = activity;
    	
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
    	setStopLoop(0,"");
    	
    	
    	
    }
    private void setStopLoop(int errorcode,String excpetion)
    {
    	stateCode = errorcode;
    	String log = "errorcode:"+errorcode+":"+recvingMsg+":"+excpetion;
    	Log.d(TAG,log);
    	if (stateCode == 2) {
    		operations.setState(true,excpetion);
		}
    	else if (stateCode<0) {
			operations.setState(false,excpetion);
	    	if(async_cient!=null)
	    		async_cient.cancel(false);
		} 
    	//Toast.makeText(getapp, text, duration)
    	//Toast.makeText(contentView, log	, Toast.LENGTH_LONG).show();
    }
    int icount =0;
    @SuppressLint("NewApi")
    public void Looper()
    {
    	if(async_cient!=null)
    		async_cient.cancel(false);

    	Log.d(TAG,"loopcount:"+icount++);
        async_cient = new AsyncTask<Void, Void, Void>() 
        {
            @Override
            protected Void doInBackground(Void... params)
            {  
            	try 
	            {
                	receiverAddress = InetAddress.getByName(iAirConstants.XIAOXIN_IP);
                    ds = new DatagramSocket();
	            }
            	catch (SocketException e) 
                {
            		setStopLoop(-1,e.toString());
                } catch (UnknownHostException e) {
					// TODO Auto-generated catch block
                	setStopLoop(-1,e.toString());
				}
            	while(stateCode==0)
            	{
            		sendMsg();

            		receMsg();
            		if(recvingMsg.startsWith("receive"))
            		{
            			//TODO RESTORE WIFI
            			
            			xinMgr.restoreCurrentWifiState();
            			setStopLoop(1,"");
            		}

            		try {
						Thread.sleep(8000);
						Log.d(TAG,"sleep2000 counter:"+icount);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return null;
						//setStopLoop(-2,e.toString());
					}
            	}
            	while(stateCode==1)
            	{
            		
            		XinServerManager.bind(activity, userid, sn,new XinServerManager.onSuccess() {
						
						@Override
						public void run(String response) {
							// TODO Auto-generated method stub
							if (response.equals("Ok")) {
								setStopLoop(2,response);
							}
							
						}
					});
            		

            		 
            		try {
						Thread.sleep(4000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return null;
					}
            	}
            	
            
               return null;
            }
            private void receMsg(){
            	 try 
	            {
	            	byte[] receiveData = new byte[20];
	            	 DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
	            	 ds.receive(receivePacket);
	            	 recvingMsg = new String( receivePacket.getData());
	            } 
                catch (SocketException e) 
                {
                    e.printStackTrace();
                    setStopLoop(-3,e.toString());
                } catch (IOException e) {
					// TODO Auto-generated catch block
                	 setStopLoop(-3,e.toString());
				}
            }

			private void sendMsg()  {

                try 
                {
                    DatagramPacket dp;                          
                    dp = new DatagramPacket(send_msg, send_msg.length,
                    		receiverAddress, iAirConstants.XIAOXIN_PORT);
                    ds.setBroadcast(true);
                    ds.send(dp);
                } 
                catch (SocketException e) 
                {
                    //e.printStackTrace();
                	//TODO...
                    setStopLoop(-4,e.toString());
                }
                catch (IOException e) {
                	//TODO...
                	setStopLoop(-4,e.toString());
				}
               
                
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
    }
}
