package com.txmcu.iair.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

import com.txmcu.iair.R;
import com.txmcu.iair.adapter.Home;
import com.txmcu.iair.adapter.MessageAdapter;
import com.txmcu.iair.adapter.MessageVo;
import com.txmcu.iair.common.iAirApplication;
import com.txmcu.xiaoxin.config.XinServerManager;
/**
 * 主界面下半部分的聊天界面
 * @author Administrator
 *
 */
public class MainDownChatActivity extends Activity implements OnClickListener {

	private static final String TAG = "MainDownChatActivity";


	iAirApplication application;
	
	//down chat
	public ListView chatlistView;
    private List<MessageVo> meList = new ArrayList<MessageVo>();
    private MessageAdapter messageAdapter;
	//

    Home home;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_down_chat);
		application = (iAirApplication)getApplication();
		Intent intent = getIntent();
		String homeidString = intent.getStringExtra("homeid");
		home = application.getHome(homeidString);
		
		messageAdapter = new MessageAdapter(this);
		chatlistView = (ListView) this.findViewById(R.id.chat_listView);// 实例化ListView
		chatlistView.setAdapter(messageAdapter);//
		
		messageAdapter.syncMessage(home);
		
		//test data
		//SimpleDateFormat df = new SimpleDateFormat("HH:mm");
       // String time = df.format(new Date()).toString();
       //// String sendContenta= "阿~阿~… -宝宝";
      //  String sendContentb= "刚带宝宝晒完太阳.-爷爷";
       // meList.add(new MessageVo(MessageVo.MESSAGE_FROM, sendContenta, time));
      //  meList.add(new MessageVo(MessageVo.MESSAGE_FROM, sendContentb, time));
      //  meList.add(new MessageVo(MessageVo.MESSAGE_FROM, sendContenta, time));
      //  meList.add(new MessageVo(MessageVo.MESSAGE_FROM, sendContentb, time));
       // meList.add(new MessageVo(MessageVo.MESSAGE_FROM, sendContenta, time));

		//findViewById(R.id.return_btn).setOnClickListener(this);

	}
	
	public void requestlist()
	{
		XinServerManager.gethomenotice_bypage(this,application.getUserid(),
				home.homeid,"0","100",new XinServerManager.onSuccess() {
					
					@Override
					public void run(JSONObject response) throws JSONException {
						// TODO Auto-generated method stub
						home.notices = XinServerManager.getNoticeFromJson(application,response.getJSONArray("notice"));
						messageAdapter.syncMessage(home);
						messageAdapter.notifyDataSetChanged();
					}
				});
	}



	public void onClick(View view) {
	
//		if (view.getId()==R.id.return_btn) {
//			finish();
//		}

	}

}
