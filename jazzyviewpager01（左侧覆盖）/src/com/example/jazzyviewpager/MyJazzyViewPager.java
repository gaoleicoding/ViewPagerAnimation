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
	 * 鏈�澶х殑缂╁皬姣斾緥
	 */
	private static final float SCALE_MAX = 0.5f;
	private static final String TAG = "MyJazzyViewPager";
	/**
	 * 淇濆瓨position涓庡浜庣殑View
	 */
	private HashMap<Integer, View> mChildrenViews = new LinkedHashMap<Integer, View>();
	/**
	 * 婊戝姩鏃跺乏杈圭殑鍏冪礌
	 */
	private View mLeft;
	/**
	 * 婊戝姩鏃跺彸杈圭殑鍏冪礌
	 */
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
		
		//婊戝姩鐗瑰埆灏忕殑璺濈鏃讹紝鎴戜滑璁や负娌℃湁鍔紝鍙湁鍙棤鐨勫垽鏂�
		float effectOffset = isSmall(positionOffset) ? 0 : positionOffset;
		
		//鑾峰彇宸﹁竟鐨刅iew
		mLeft = findViewFromObject(position);
		//鑾峰彇鍙宠竟鐨刅iew
		mRight = findViewFromObject(position + 1);
		
		// 娣诲姞鍒囨崲鍔ㄧ敾鏁堟灉
		animateStack(mLeft, mRight, effectOffset, positionOffsetPixels);
		super.onPageScrolled(position, positionOffset, positionOffsetPixels);
	}

	public void setObjectForPosition(View view, int position)
	{
		mChildrenViews.put(position, view);
	}

	/**
	 * 閫氳繃杩囦綅缃幏寰楀搴旂殑View
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
		if (right != null)
		{
			 /** 
             * 缩小比例 如果手指从右到左的滑动（切换到后一个）：0.0~1.0，即从一半到最大 
             * 如果手指从左到右的滑动（切换到前一个）：1.0~0，即从最大到一半 
             */ 
			 /** 
             * x偏移量： 如果手指从右到左的滑动（切换到后一个）：0-720 如果手指从左到右的滑动（切换到前一个）：720-0 
             */ 
			 mTrans = -getWidth() - getPageMargin() + positionOffsetPixels;
			//利用nineoldandroid.jar控制右侧滑动动画
			ViewHelper.setTranslationX(right, mTrans);
		}
		if (left != null)
		{
			left.bringToFront();
		}
	}
}
