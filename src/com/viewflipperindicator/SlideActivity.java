package com.viewflipperindicator;

import com.example.myfirstapp.R;
import com.pageIndicator.CirclePageIndicator;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class SlideActivity extends Activity implements
		GestureDetector.OnGestureListener, OnTouchListener,
		Animation.AnimationListener {
	// 背景flipper
	private ViewFlipper flipper;
	// 标题flipper
	private ViewFlipper textFlipper;
	// 最小响应滑动距离
	private static final int FLING_MIN_DISTANCE = 100;
	private static final int FLING_MIN_VELOCITY = 200;
	private static final int FLING_DURATION = 250;
	private static float ACCVAL = 0.4f;
	private CirclePageIndicator indic;
	private GestureDetector mGestureDetector;

	//需要显示的介绍背景图片
	private int[] photosList = new int[] { R.drawable.bg1, R.drawable.bg2,
			R.drawable.bg3, R.drawable.bg2 };
	//需要显示的介绍文案，与背景图片配对
	String[] titleText = {
			"分享生活<br><small><font size=\"1\">记录和分享每一个时刻</font></small>",
			"记录生活", "点滴", "记录生活" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_slide);
		textFlipper = (ViewFlipper) findViewById(R.id.titles);
		for (int i = 0; i < photosList.length; i++) {
			TextView tv = new TextView(this);
			tv.setTextSize(25);
			tv.setShadowLayer(1, 1, 1, Color.BLACK);
			String message = titleText[i];
			tv.setText(Html.fromHtml(message));
			tv.setTextColor(android.graphics.Color.WHITE);
			tv.setGravity(Gravity.CENTER);
			textFlipper.addView(tv);
		}
		flipper = (ViewFlipper) findViewById(R.id.photos);
		for (int i = 0; i < photosList.length; i++) {
			ImageView iv = new ImageView(this);
			iv.setBackgroundResource(photosList[i]);
			flipper.addView(iv);
		}
		// 注册一个用于手势识别的类
		mGestureDetector = new GestureDetector(this, this);
		mGestureDetector.setIsLongpressEnabled(true);
		// 给mFlipper设置一个listener
		flipper.setOnTouchListener(this);
		// flipper.getAnimation().setAnimationListener(this);
		textFlipper.setOnTouchListener(this);
		// 允许长按住ViewFlipper,这样才能识别拖动等手势
		flipper.setLongClickable(true);
		textFlipper.setLongClickable(true);
		indic = (CirclePageIndicator) findViewById(R.id.viewflowindic);
		indic.setViewPager(photosList.length);
		indic.setCurrentItem(0);
	}

	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		if (e1.getX() - e2.getX() > FLING_MIN_DISTANCE
				&& Math.abs(velocityX) > FLING_MIN_VELOCITY) {
			// 当像左侧滑动的时候
			// 设置View进入屏幕时候使用的动画
			flipper.setInAnimation(inAlphaAnimation());
			// 设置View退出屏幕时候使用的动画
			flipper.setOutAnimation(outAlphaAnimation());
			flipper.showNext();
			textFlipper.setInAnimation(inFromRightAnimation());
			textFlipper.setOutAnimation(outToLeftAnimation());
			textFlipper.showNext();
		} else if (e2.getX() - e1.getX() > FLING_MIN_DISTANCE
				&& Math.abs(velocityX) > FLING_MIN_VELOCITY) {
			// 当像右侧滑动的时候
			flipper.setInAnimation(inAlphaAnimation());
			flipper.setOutAnimation(outAlphaAnimation());
			flipper.showPrevious();
			textFlipper.setInAnimation(inFromLeftAnimation());
			textFlipper.setOutAnimation(outToRightAnimation());
			textFlipper.showPrevious();
		}
		return false;
	}

	/**
	 * 定义从右侧进入的动画效果
	 * 
	 * @return
	 */
	protected Animation inFromRightAnimation() {
		Animation inFromRight = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, +1.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f);
		inFromRight.setDuration(FLING_DURATION);
		inFromRight.setInterpolator(new AccelerateInterpolator(ACCVAL));
		return inFromRight;
	}

	/**
	 * 定义透明
	 * 
	 * @return
	 */
	protected Animation inAlphaAnimation() {
		Animation inAlphaAnim = new AlphaAnimation(0.1f, 1.0f);
		inAlphaAnim.setDuration(FLING_DURATION);
		inAlphaAnim.setInterpolator(new AccelerateInterpolator(ACCVAL));
		inAlphaAnim.setAnimationListener(this);
		return inAlphaAnim;
	}

	protected Animation outAlphaAnimation() {
		Animation outAlphaAnim = new AlphaAnimation(1.0f, 0.1f);
		outAlphaAnim.setDuration(FLING_DURATION);
		outAlphaAnim.setAnimationListener(this);
		outAlphaAnim.setInterpolator(new AccelerateInterpolator(0.4f));
		return outAlphaAnim;
	}

	/**
	 * 定义从左侧退出的动画效果
	 * 
	 * @return
	 */
	protected Animation outToLeftAnimation() {
		Animation outtoLeft = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, -1.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f);
		outtoLeft.setDuration(FLING_DURATION);
		outtoLeft.setInterpolator(new AccelerateInterpolator(ACCVAL));
		return outtoLeft;
	}

	/**
	 * 定义从左侧进入的动画效果
	 * 
	 * @return
	 */
	protected Animation inFromLeftAnimation() {
		Animation inFromLeft = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, -1.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f);
		inFromLeft.setDuration(FLING_DURATION);
		inFromLeft.setInterpolator(new AccelerateInterpolator(ACCVAL));
		return inFromLeft;
	}

	/**
	 * 定义从右侧退出时的动画效果
	 * 
	 * @return
	 */
	protected Animation outToRightAnimation() {
		Animation outtoRight = new TranslateAnimation(
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, +1.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f,
				Animation.RELATIVE_TO_PARENT, 0.0f);
		outtoRight.setDuration(FLING_DURATION);
		outtoRight.setInterpolator(new AccelerateInterpolator(ACCVAL));
		return outtoRight;
	}

	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		return mGestureDetector.onTouchEvent(arg1);
	}

	public void onShowPress(MotionEvent e) {
	}

	public boolean onSingleTapUp(MotionEvent e) {
		return true;
	}

	// 用户按下触摸屏，并拖动，由1个MotionEvent ACTION_DOWN, 多个ACTION_MOVE触发
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		return true;
	}

	// 用户长按触摸屏，由多个MotionEvent ACTION_DOWN触发
	public void onLongPress(MotionEvent e) {
	}

	@Override
	// 用户轻触触摸屏，由1个MotionEvent ACTION_DOWN触发
	public boolean onDown(MotionEvent arg0) {
		return true;
	}

	@Override
	public void onAnimationEnd(Animation arg0) {
		indic.setCurrentItem(flipper.getDisplayedChild());
	}

	@Override
	public void onAnimationRepeat(Animation arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onAnimationStart(Animation arg0) {
		// TODO Auto-generated method stub

	}

}
