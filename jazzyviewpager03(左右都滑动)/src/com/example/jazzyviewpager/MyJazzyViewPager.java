package com.example.jazzyviewpager;

import java.util.HashMap;
import java.util.LinkedHashMap;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.nineoldandroids.view.ViewHelper;

public class MyJazzyViewPager extends ViewPager
{
	private float mTrans;
	private float mScale;
	/**
	 * 最大的缩小比例 
	 */
	private static final float SCALE_MAX = 0.5f;
	private static final String TAG = "MyJazzyViewPager";
	/**
	 *保存position与对于的View 
	 */
	private HashMap<Integer, View> mChildrenViews = new LinkedHashMap<Integer, View>();
	private View mLeft;
	private View mRight;

	public MyJazzyViewPager(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	@Override
	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels)
	{

//		Log.e(TAG, "position=" + position+", positionOffset = "+positionOffset+" ,positionOffsetPixels =  " + positionOffsetPixels+" , currentPos = " + getCurrentItem());
		
		//滑动特别小的距离时，我们认为没有动，可有可无的判断  
		float effectOffset = isSmall(positionOffset) ? 0 : positionOffset;
		
		//获取左边的View 
		mLeft = findViewFromObject(position);
		//获取右边的View  
		mRight = findViewFromObject(position + 1);
		
		// 添加切换动画效果 
		animateStack(mLeft, mRight, effectOffset, positionOffsetPixels);
		super.onPageScrolled(position, positionOffset, positionOffsetPixels);
	}

	public void setObjectForPosition(View view, int position)
	{
		mChildrenViews.put(position, view);
	}

	/**
	 * 通过过位置获得对应的View 
	 * 
	 * @param position
	 * @return
	 */
	public View findViewFromObject(int position)
	{
		return mChildrenViews.get(position);
	}

	private boolean isSmall(float positionOffset)
	{
		return Math.abs(positionOffset) < 0.0001;
	}

	protected void animateStack(View left, View right, float effectOffset,
			int positionOffsetPixels)
	{
		Log.d("gaolei", "effectOffset----------------/"+effectOffset);
		Log.d("gaolei", "positionOffsetPixels----------------/"+positionOffsetPixels);
		if (right != null)
		{
			 /** 
             * 缩小比例 如果手指从右到左的滑动（切换到后一个）：0.0~1.0，即从一半到最大 
             * 如果手指从左到右的滑动（切换到前一个）：1.0~0，即从最大到一半 
			 /** 
             * x偏移量： 如果手指从右到左的滑动（切换到后一个）：0-720 如果手指从左到右的滑动（切换到前一个）：720-0 
             */ 
			right.bringToFront();
		}
		if (left != null)
		{
			mTrans =positionOffsetPixels;
		//利用nineoldandroid.jar控制左侧滑动动画，修改200可以尝试改变移动距离
			ViewHelper.setTranslationX(left, positionOffsetPixels-200*effectOffset);
		}
	}
}
