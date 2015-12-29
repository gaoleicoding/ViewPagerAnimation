package com.example.jazzyviewpager;

import com.example.zhy_jazzyviewpager.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class MainActivity extends Activity
{
	protected static final String TAG = "MainActivity";
	private int[] mImgIds;
	private MyJazzyViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mImgIds = new int[] { R.drawable.a, R.drawable.b, R.drawable.c,
				R.drawable.d };
		mViewPager = (MyJazzyViewPager) findViewById(R.id.id_viewPager);
		mViewPager.setAdapter(new PagerAdapter()
		{

			@Override
			public boolean isViewFromObject(View arg0, Object arg1)
			{
				return arg0 == arg1;
			}

			@Override
			public void destroyItem(ViewGroup container, int position,
					Object object)
			{
				container.removeView((View) object);
			}

			@Override
			public Object instantiateItem(ViewGroup container, int position)
			{
				ImageView imageView = new ImageView(MainActivity.this);
				imageView.setImageResource(mImgIds[position]);
				imageView.setScaleType(ScaleType.CENTER_CROP);
				container.addView(imageView);
				mViewPager.setObjectForPosition(imageView, position);
				return imageView;
			}

			@Override
			public int getCount()
			{
				return mImgIds.length;
			}
		});

	}

}
