package com.txmcu.iair.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

import com.txmcu.iair.R;
import com.txmcu.iair.chat.MessageAdapter;
import com.txmcu.iair.chat.MessageVo;
import com.txmcu.iair.common.iAirApplication;

public class MainDownChatActivity extends Activity implements OnClickListener {

	private static final String TAG = "MainDownChatActivity";


	iAirApplication application;
	
	//down chat
	public ListView chatlistView;
    private List<MessageVo> meList = new ArrayList<MessageVo>();
    private MessageAdapter messageAdapter;
	//

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_down_chat);

		application = (iAirApplication)getApplication();
		messageAdapter = new MessageAdapter(this, meList);;
		chatlistView = (ListView) this.findViewById(R.id.chat_listView);// 实例化ListView
		chatlistView.setAdapter(messageAdapter);//
		
		//test data
		SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        String time = df.format(new Date()).toString();
        String sendContenta= "阿~阿~… -宝宝";
        String sendContentb= "刚带宝宝晒完太阳.-爷爷";
        meList.add(new MessageVo(MessageVo.MESSAGE_FROM, sendContenta, time));
        meList.add(new MessageVo(MessageVo.MESSAGE_FROM, sendContentb, time));
        meList.add(new MessageVo(MessageVo.MESSAGE_FROM, sendContenta, time));
        meList.add(new MessageVo(MessageVo.MESSAGE_FROM, sendContentb, time));
        meList.add(new MessageVo(MessageVo.MESSAGE_FROM, sendContenta, time));

		//findViewById(R.id.return_btn).setOnClickListener(this);

	}



	public void onClick(View view) {
	
//		if (view.getId()==R.id.return_btn) {
//			finish();
//		}

	}

}
