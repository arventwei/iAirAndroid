package com.txmcu.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import cn.classd.dragablegrid.widget.DragableGridview;
import cn.classd.dragablegrid.widget.DragableGridview.OnItemClickListener;
import cn.classd.dragablegrid.widget.DragableGridview.OnSwappingListener;

import com.txmcu.iair.R;

public class CityManageActivity extends Activity 
implements OnClickListener{

	public static final String TAG = "CityManageActivity";
	
	private List<City> books;
	
	private DragableGridview mGridview;
	
	private  CityAdapter adapter;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.city_manage_activity);
		findViewById(R.id.back_img).setOnClickListener(this);
		mGridview= ((DragableGridview)findViewById(R.id.gridview));
		
		
		initTestData();
		// mGridview = (DragableGridview) findViewById(R.id.dragableGridview1);
	        
	        adapter = new CityAdapter();
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
					City book = books.get(oldIndex);
					books.remove(oldIndex);
					books.add(newIndex, book);
					
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
	
	
	  private class CityAdapter extends BaseAdapter {

			@Override
			public int getCount() {
				//return 5;
				return books.size();
			}

			@Override
			public Object getItem(int position) {
				return books.get(position);
			}

			@Override
			public long getItemId(int position) {
				return books.get(position).getId();
			}
			
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				if (null == convertView) {
					convertView = View.inflate(CityManageActivity.this, R.layout.change_city_gridview_item, null);
				}
				
				City b = books.get(position);
				
				//((ImageView) convertView.findViewById(R.id.imageView1)).setImageResource(b.getBitmapId());
				
				((TextView)convertView.findViewById(R.id.city_name)).setText(b.getName());
				
				return convertView;
			}
	    	
	    }
	  private void initTestData() {
	    	books = new ArrayList<City>();
	    	
			//for (int i = 0; i < 1; i++) {
				setBooks();
			//}
	    }
	    
	    private void setBooks() {
	    	
	    	//String[] bookNames = getResources().getStringArray(R.array.books);
	    	
	    	City book = new City();
	    	book.setId(1);
	    	book.setName("北京");
	    	//book.setBitmapId(R.drawable.b001);
	    	books.add(book);
	    	

	    	City book1 = new City();
	    	book1.setId(2);
	    	book1.setName("南京");
	    	//book.setBitmapId(R.drawable.b001);
	    	books.add(book1);
	    	
	    	
	    }
}
