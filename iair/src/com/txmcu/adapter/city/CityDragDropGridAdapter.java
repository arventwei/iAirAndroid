package com.txmcu.adapter.city;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import ca.laplanete.mobile.pageddragdropgrid.PagedDragDropGrid;
import ca.laplanete.mobile.pageddragdropgrid.PagedDragDropGridAdapter;

import com.txmcu.iair.R;

public class CityDragDropGridAdapter implements PagedDragDropGridAdapter {

	private Context context;
	private PagedDragDropGrid gridview;
	
	List<GridViewPage> pages = new ArrayList<GridViewPage>();
	
	public CityDragDropGridAdapter(Context context, PagedDragDropGrid gridview) {
		super();
		this.context = context;
		this.gridview = gridview;
		
		GridViewPage page1 = new GridViewPage();
//		List<Item> items = new ArrayList<Item>();
//		items.add(new Item(1, "Item 1", R.drawable.ic_launcher));
//		items.add(new Item(2, "Item 2", R.drawable.ic_launcher));
//		items.add(new Item(3, "Item 3", R.drawable.ic_launcher));
//		page1.setItems(items);
		pages.add(page1);
		
		
	}

	@Override
	public int pageCount() {
		return pages.size();
	}

	private List<GridViewItem> itemsInPage(int page) {
		if (pages.size() > page) {
			return pages.get(page).getItems();
		}	
		return Collections.emptyList();
	}

    @Override
	public View view(int page, int index) {
		
		LinearLayout layout = new LinearLayout(context);
		layout.setOrientation(LinearLayout.VERTICAL);
		
		ImageView icon = new ImageView(context);
		GridViewItem item = getItem(page, index);
		icon.setImageResource(item.getDrawable());
		icon.setPadding(15, 15, 15, 15);
		
		layout.addView(icon);
		
		TextView label = new TextView(context);
		label.setTag("text");
		label.setText(item.getName());	
		label.setTextColor(Color.BLACK);
		label.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL);
	
		label.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT));

		layout.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		
		// only set selector on every other page for demo purposes
		// if you do not wish to use the selector functionality, simply disregard this code
		if(page % 2 == 0) {
    		setViewBackground(layout);
    		layout.setClickable(true);
    		layout.setOnLongClickListener(new OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return gridview.onLongClick(v);
                }
    		});
		}

		layout.addView(label);
		return layout;
	}

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setViewBackground(LinearLayout layout) {
        if (android.os.Build.VERSION.SDK_INT >= 16) {
      //      layout.setBackground(context.getResources().getDrawable(R.drawable.list_selector_holo_light));
        }
    }

	private GridViewItem getItem(int page, int index) {
		List<GridViewItem> items = itemsInPage(page);
		return items.get(index);
	}

	@Override
	public int rowCount() {
		return AUTOMATIC;
	}

	@Override
	public int columnCount() {
		return AUTOMATIC;
	}

	@Override
	public int itemCountInPage(int page) {
		return itemsInPage(page).size();
	}

	public void printLayout() {
		int i=0;
		for (GridViewPage page : pages) {
			Log.d("Page", Integer.toString(i++));
			
			for (GridViewItem item : page.getItems()) {
				Log.d("Item", Long.toString(item.getId()));
			}
		}
	}

	private GridViewPage getPage(int pageIndex) {
		return pages.get(pageIndex);
	}

	@Override
	public void swapItems(int pageIndex, int itemIndexA, int itemIndexB) {
		getPage(pageIndex).swapItems(itemIndexA, itemIndexB);
	}

	@Override
	public void moveItemToPreviousPage(int pageIndex, int itemIndex) {
		int leftPageIndex = pageIndex-1;
		if (leftPageIndex >= 0) {
			GridViewPage startpage = getPage(pageIndex);
			GridViewPage landingPage = getPage(leftPageIndex);
			
			GridViewItem item = startpage.removeItem(itemIndex);
			landingPage.addItem(item);	
		}	
	}

	@Override
	public void moveItemToNextPage(int pageIndex, int itemIndex) {
		int rightPageIndex = pageIndex+1;
		if (rightPageIndex < pageCount()) {
			GridViewPage startpage = getPage(pageIndex);
			GridViewPage landingPage = getPage(rightPageIndex);
			
			GridViewItem item = startpage.removeItem(itemIndex);
			landingPage.addItem(item);			
		}	
	}

	@Override
	public void deleteItem(int pageIndex, int itemIndex) {
		getPage(pageIndex).deleteItem(itemIndex);
	}

    @Override
    public int deleteDropZoneLocation() {        
        return BOTTOM;
    }

    @Override
    public boolean showRemoveDropZone() {
        return true;
    }

	@Override
	public int getPageWidth(int page) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getItemAt(int page, int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean disableZoomAnimationsOnChangePage() {
		// TODO Auto-generated method stub
		return false;
	}
	
}
