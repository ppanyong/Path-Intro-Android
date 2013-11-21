package com.pageIndicator;

import com.example.myfirstapp.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;

/**
 * Draws circles (one for each view). The current view position is filled and
 * others are only stroked.
 */
public class CirclePageIndicator extends View {
	// 标示点半径
	private float mRadius;
	private final Paint mPaintPageFill = new Paint(ANTI_ALIAS_FLAG);
	private final Paint mPaintStroke = new Paint(ANTI_ALIAS_FLAG);
	private final Paint mPaintFill = new Paint(ANTI_ALIAS_FLAG);
	private int mCurrentPage = 0;
	private int mSnapPage;
	private float mPageOffset;
	private int mScrollState;
	private boolean mCentered = true;
	private boolean mSnap;
	private int pagesCount = 2;

	public CirclePageIndicator(Context context) {
		this(context, null);
	}

	public CirclePageIndicator(Context context, AttributeSet attrs) {
		this(context, attrs, R.attr.vpiCirclePageIndicatorStyle);
	}

	public CirclePageIndicator(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		if (isInEditMode())
			return;

		// Retrieve styles attributes
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.CirclePageIndicator, defStyle, 0);

		// 未选择中背景
		mPaintPageFill.setStyle(Style.FILL);
		mPaintPageFill.setColor(Color.WHITE);
		mPaintPageFill.setAlpha(80);
		// 边框
		/*
		 * mPaintStroke.setStyle(Style.STROKE);
		 * mPaintStroke.setColor(Color.RED); mPaintStroke.setStrokeWidth(0.1f);
		 */
		// 选中背景
		mPaintFill.setStyle(Style.FILL);
		mPaintFill.setColor(Color.WHITE);
		// 半径
		mRadius = 4;
		a.recycle();
	}

	public void setCentered(boolean centered) {
		mCentered = centered;
		invalidate();
	}

	public boolean isCentered() {
		return mCentered;
	}

	public void setPageColor(int pageColor) {
		mPaintPageFill.setColor(pageColor);
		invalidate();
	}

	public int getPageColor() {
		return mPaintPageFill.getColor();
	}

	public void setFillColor(int fillColor) {
		mPaintFill.setColor(fillColor);
		invalidate();
	}

	public int getFillColor() {
		return mPaintFill.getColor();
	}

	public void setStrokeColor(int strokeColor) {
		mPaintStroke.setColor(strokeColor);
		invalidate();
	}

	public int getStrokeColor() {
		return mPaintStroke.getColor();
	}

	public void setStrokeWidth(float strokeWidth) {
		mPaintStroke.setStrokeWidth(strokeWidth);
		invalidate();
	}

	public float getStrokeWidth() {
		return mPaintStroke.getStrokeWidth();
	}

	public void setRadius(float radius) {
		mRadius = radius;
		invalidate();
	}

	public float getRadius() {
		return mRadius;
	}

	public void setSnap(boolean snap) {
		mSnap = snap;
		invalidate();
	}

	public boolean isSnap() {
		return mSnap;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// final int count = mViewPager.getAdapter().getCount();
		final int count = this.pagesCount;
		if (mCurrentPage >= count) {
			setCurrentItem(count - 1);
			return;
		}
		int longSize;
		int longPaddingBefore;
		int longPaddingAfter;
		int shortPaddingBefore;
		longSize = getWidth();
		longPaddingBefore = getPaddingLeft();
		longPaddingAfter = getPaddingRight();
		shortPaddingBefore = getPaddingTop();

		final float threeRadius = mRadius * 3;
		final float shortOffset = shortPaddingBefore + mRadius;
		float longOffset = longPaddingBefore + mRadius;
		if (mCentered) {
			longOffset += ((longSize - longPaddingBefore - longPaddingAfter) / 2.0f)
					- ((count * threeRadius) / 2.0f);
		}

		float dX;
		float dY;

		float pageFillRadius = mRadius;
		if (mPaintStroke.getStrokeWidth() > 0) {
			pageFillRadius -= mPaintStroke.getStrokeWidth() / 2.0f;
		}

		// Draw stroked circles
		for (int iLoop = 0; iLoop < count; iLoop++) {
			float drawLong = longOffset + (iLoop * threeRadius);
			dX = drawLong;
			dY = shortOffset;
			// Only paint fill if not completely transparent
			if (mPaintPageFill.getAlpha() > 0) {
				canvas.drawCircle(dX, dY, pageFillRadius, mPaintPageFill);
			}

			// Only paint stroke if a stroke width was non-zero
			if (pageFillRadius != mRadius) {
				canvas.drawCircle(dX, dY, mRadius, mPaintStroke);
			}
		}

		// Draw the filled circle according to the current scroll
		float cx = (mSnap ? mSnapPage : mCurrentPage) * threeRadius;
		if (!mSnap) {
			cx += mPageOffset * threeRadius;
		}
		dX = longOffset + cx;
		dY = shortOffset;
		canvas.drawCircle(dX, dY, mRadius, mPaintFill);
	}

	public boolean onTouchEvent(android.view.MotionEvent ev) {
		if (super.onTouchEvent(ev)) {
			return true;
		}
		return true;
	}

	public void setViewPager(ViewPager view) {
		invalidate();
	}

	public void setViewPager(int count) {
		this.pagesCount = count;
		invalidate();
	}

	public void setViewPager(ViewPager view, int initialPosition) {
		setViewPager(view);
		setCurrentItem(initialPosition);
	}

	public void setCurrentItem(int item) {
		mCurrentPage = item;
		invalidate();
	}

	public void notifyDataSetChanged() {
		invalidate();
	}

	public void onPageScrollStateChanged(int state) {
		mScrollState = state;
	}

	public void onPageScrolled(int position, float positionOffset,
			int positionOffsetPixels) {
		mCurrentPage = position;
		mPageOffset = positionOffset;
		invalidate();
	}

	public void onPageSelected(int position) {
		if (mSnap || mScrollState == ViewPager.SCROLL_STATE_IDLE) {
			mCurrentPage = position;
			mSnapPage = position;
			invalidate();
		}
	}

	public void setOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
		// mListener = listener;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View#onMeasure(int, int)
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(measureLong(widthMeasureSpec),
				measureShort(heightMeasureSpec));
	}

	/**
	 * Determines the width of this view
	 * 
	 * @param measureSpec
	 *            A measureSpec packed into an int
	 * @return The width of the view, honoring constraints from measureSpec
	 */
	private int measureLong(int measureSpec) {
		int result;
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);

		// Calculate the width according the views count
		final int count = 3;
		result = (int) (getPaddingLeft() + getPaddingRight()
				+ (count * 2 * mRadius) + (count - 1) * mRadius + 1);
		// Respect AT_MOST value if that was what is called for by measureSpec
		if (specMode == MeasureSpec.AT_MOST) {
			result = Math.min(result, specSize);
		}
		return result;
	}

	/**
	 * Determines the height of this view
	 * 
	 * @param measureSpec
	 *            A measureSpec packed into an int
	 * @return The height of the view, honoring constraints from measureSpec
	 */
	private int measureShort(int measureSpec) {
		int result;
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);

		if (specMode == MeasureSpec.EXACTLY) {
			// We were told how big to be
			result = specSize;
		} else {
			// Measure the height
			result = (int) (2 * mRadius + getPaddingTop() + getPaddingBottom() + 1);
			// Respect AT_MOST value if that was what is called for by
			// measureSpec
			if (specMode == MeasureSpec.AT_MOST) {
				result = Math.min(result, specSize);
			}
		}
		return result;
	}
}
