package com.txmcu.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import ca.laplanete.mobile.pageddragdropgrid.OnPageChangedListener;
import ca.laplanete.mobile.pageddragdropgrid.PagedDragDropGrid;

import com.txmcu.adapter.city.CityDragDropGridAdapter;
import com.txmcu.iair.R;

public class CityManageActivity extends Activity 
implements OnClickListener{

	private String CURRENT_PAGE_KEY = "CURRENT_PAGE_KEY";
	 private PagedDragDropGrid gridview;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.city_manage_activity);
		findViewById(R.id.back_img).setOnClickListener(this);
		
		gridview = (PagedDragDropGrid) findViewById(R.id.gridview);	
		
		CityDragDropGridAdapter adapter = new CityDragDropGridAdapter(this, gridview);
		
        gridview.setAdapter(adapter);
		gridview.setClickListener(this);

		gridview.setBackgroundColor(Color.LTGRAY);	
		
		
		gridview.setOnPageChangedListener(new OnPageChangedListener() {            
            @Override
            public void onPageChanged(PagedDragDropGrid sender, int newPageNumber) {
                Toast.makeText(CityManageActivity.this, "Page changed to page " + newPageNumber, Toast.LENGTH_SHORT).show();                
            }
        });
		
	}
	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
	  super.onRestoreInstanceState(savedInstanceState);
      int savedPage = savedInstanceState.getInt(CURRENT_PAGE_KEY);
      gridview.restoreCurrentPage(savedPage);
	}
	
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {

	    outState.putInt(CURRENT_PAGE_KEY, gridview.currentPage());
	    super.onSaveInstanceState(outState);
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
	
	
}
