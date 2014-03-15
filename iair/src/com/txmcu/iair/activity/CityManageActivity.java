package com.txmcu.iair.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import cn.classd.dragablegrid.widget.DragableGridview;
import cn.classd.dragablegrid.widget.DragableGridview.OnItemClickListener;
import cn.classd.dragablegrid.widget.DragableGridview.OnSwappingListener;

import com.txmcu.iair.R;
import com.txmcu.iair.adapter.CityAdapter;

public class CityManageActivity extends Activity 
implements OnClickListener{

	public static final String TAG = "CityManageActivity";
	
	
	
	private DragableGridview mGridview;
	
	private  CityAdapter adapter;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_city_manage);
		findViewById(R.id.back_img).setOnClickListener(this);
		mGridview= ((DragableGridview)findViewById(R.id.gridview));
		
		
		
		// mGridview = (DragableGridview) findViewById(R.id.dragableGridview1);
	        
        adapter = new CityAdapter(this);
        initTestData();
        addGridViewListener();
		
	}

	protected void addGridViewListener() {
		mGridview.setAdapter(adapter);
        
        mGridview.setOnItemClick(new OnItemClickListener() {
			
			@Override
			public void click(int index) {
				Log.d(TAG, "item : " + index + " -- clicked!");
			}
		});
        
        mGridview.setOnSwappingListener(new OnSwappingListener() {
			
			@Override
			public void waspping(int oldIndex, int newIndex) {
				adapter.waspping(oldIndex,newIndex);
				adapter.notifyDataSetChanged();
			}
		});
	}
	
	@Override
	public void onClick(View paramView)
	{
	    switch (paramView.getId())
	    {
	    	case R.id.back_img:
	    		finish();
	    		break;
	    	
	    }
	}
	
	
	  
	  private void initTestData() {
	    	//books = new ArrayList<City>();
	    	adapter.addCity(1, "北京");
	    	adapter.addCity(2, "南京");
			//for (int i = 0; i < 1; i++) {
				//setBooks();
			//}
	    }
	    
	   
}
