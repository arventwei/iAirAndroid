package com.txmcu.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ImageView;

public class LoadingImgView extends ImageView
  implements Runnable
{
  private int[] imageIds;
  private int index = 0;
  private boolean isStop = false;
  private int length = 1;

  public LoadingImgView(Context paramContext)
  {
    this(paramContext, null);
  }

  public LoadingImgView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  protected void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    if ((this.imageIds != null) && (this.imageIds.length > 0))
      setImageResource(this.imageIds[this.index]);
  }

  public void run()
  {
    while (!this.isStop)
    {
      int i = 1 + this.index;
      this.index = i;
      this.index = (i % this.length);
      postInvalidate();
      try
      {
        Thread.sleep(180L);
      }
      catch (InterruptedException localInterruptedException)
      {
        localInterruptedException.printStackTrace();
      }
    }
  }

  public void setImageIds(int[] paramArrayOfInt)
  {
    this.imageIds = paramArrayOfInt;
    if ((this.imageIds != null) && (this.imageIds.length > 0))
      this.length = this.imageIds.length;
  }

  public void startAnim()
  {
    this.isStop = false;
    new Thread(this).start();
  }

  public void stopAnim()
  {
    this.isStop = true;
  }
}
