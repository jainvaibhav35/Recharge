package base.rechargeapp.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.ImageView;

import base.rechargeapp.R;

/**
 * This is CustomImageView used to make image rounded 
 * @author mukesh
 *
 */

public class RoundedImageViewWithBorder extends ImageView 
{
	private int mBorderWidth =0;//in dp
	private int mViewWidth;
	private int mViewHeight;
	private Bitmap mImage;
	private Paint mImagePaint;
	private Paint mPaintBorder;
	private Paint mPaintWhite;
	private BitmapShader mShader;
	private Paint mPaintBackground;
	private String mDrawString;
	private int mCircleCenter;

	public RoundedImageViewWithBorder(Context context) {
		super(context);
		setup();
	}

	public RoundedImageViewWithBorder(Context context, AttributeSet attrs) {
		super(context, attrs);
		setup();
	}

	public RoundedImageViewWithBorder(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setup();
	}

	private void setup()
	{
		// init paint
		mImagePaint = new Paint();
		mImagePaint.setAntiAlias(true);

		mPaintBorder = new Paint();
		setBorderColor(getContext().getResources().getColor(R.color.white));
		mPaintBorder.setAntiAlias(true);

		mPaintWhite = new Paint();
		mPaintWhite.setColor(Color.WHITE);
		mPaintWhite.setTextAlign(Align.CENTER);
		mPaintWhite.setStyle(Paint.Style.FILL);
		mPaintWhite.setAntiAlias(true);
		//		mPaintWhite.setTypeface(AppTypeFace.getAppTypeface(getContext()).getTypeFaceHelveticaNeueBold());

		this.mBorderWidth = dpToPx(getContext(),mBorderWidth);
		mPaintBackground = new Paint();
		mPaintBackground.setColor(Color.BLACK);
		mPaintBackground.setAntiAlias(true);
	}

	public void setBorderWidth(int borderWidth)
	{
		this.mBorderWidth = dpToPx(getContext(),borderWidth);
		this.invalidate();
	}

	public void setBorderColor(int borderColor)
	{		
		if(mPaintBorder != null)
			mPaintBorder.setColor(borderColor);

		this.invalidate();
	}

	private void loadBitmap()
	{
		BitmapDrawable bitmapDrawable = (BitmapDrawable) this.getDrawable();

		if(bitmapDrawable != null)
			mImage = bitmapDrawable.getBitmap();
	}

	@SuppressLint("DrawAllocation")
	@Override
	public void onDraw(Canvas canvas)
	{
		//load the bitmap
		loadBitmap();

		//		mCircleCenter = (mViewWidth)/ 2;
		canvas.drawCircle(mCircleCenter , mCircleCenter, mCircleCenter , mPaintBorder);
		// init shader
		//		Log.e("mImage =>"+mImage, "On Draw RIV ");
		if(mImage !=null)
		{			
			canvas.drawCircle(mCircleCenter , mCircleCenter , mCircleCenter-mBorderWidth, mPaintWhite);
			mShader = new BitmapShader(Bitmap.createScaledBitmap(mImage, mViewWidth, mViewHeight, false), Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
			mImagePaint.setShader(mShader);
			// circleCenter is the x or y of the view's center
			// radius is the radius in pixels of the cirle to be drawn
			// paint contains the shader that will texture the shape
			canvas.drawCircle(mCircleCenter , mCircleCenter , mCircleCenter-mBorderWidth, mImagePaint);
		}
		else
		{
			if(mDrawString!=null)
			{
				canvas.drawCircle(mCircleCenter , mCircleCenter , mCircleCenter-mBorderWidth, mPaintBackground);
				Rect bounds = new Rect();
				mPaintWhite.getTextBounds(mDrawString, 0, mDrawString.length(), bounds); 
				//				canvas.drawText(mDrawString,mCircleCenter-bounds.width()/2,mCircleCenter+ bounds.height()/2,mPaintWhite);
				canvas.drawText(mDrawString,mCircleCenter,mCircleCenter+ bounds.height()/2,mPaintWhite);
			}
			else
			{
				canvas.drawCircle(mCircleCenter , mCircleCenter , mCircleCenter-mBorderWidth, mPaintWhite);
			}
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		int width = measureWidth(widthMeasureSpec);
		int height = measureHeight(heightMeasureSpec, widthMeasureSpec);    	

		mViewWidth = width;
		mViewHeight = height;
		mCircleCenter = (mViewWidth)/ 2;
		setMeasuredDimension(width, height);
	}

	private int measureWidth(int measureSpec)
	{
		int result = 0;
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);

		if (specMode == MeasureSpec.EXACTLY) {
			// We were told how big to be
			result = specSize;
		} else {
			// Measure the text
			result = mViewWidth;

		}

		return result;
	}

	private int measureHeight(int measureSpecHeight, int measureSpecWidth) {
		int result = 0;
		int specMode = MeasureSpec.getMode(measureSpecHeight);
		int specSize = MeasureSpec.getSize(measureSpecHeight);

		if (specMode == MeasureSpec.EXACTLY) 
		{
			// We were told how big to be
			result = specSize;
		} 
		else 
		{
			// Measure the text (beware: ascent is a negative number)
			result = mViewHeight;           
		}
		return result;
	}

	/**
	 * This method is use to set drawing parameter
	 * @param background
	 * @param character
	 */
	public void setDrawParameter(int background, String character,float textSize)
	{
		mPaintBackground.setColor(background);
		//		mPaintWhite.setTextSize(getPixels(TypedValue.COMPLEX_UNIT_SP, textSize));
		mPaintWhite.setTextSize(textSize);
		//		TypedValue.COMPLEX_UNIT_SP,

		mDrawString=character;
	}

	public void setPresence(int presence) 
	{
		switch (presence)
		{
		case 1:
			setBorderColor(getContext().getResources().getColor(R.color.white));
			break;
		case 0:
			setBorderColor(getContext().getResources().getColor(R.color.white));
			break;
		default:
			break;
		}
	}

	/**
	 * This method is use to convert dp into pixel value
	 * @param context
	 * @param dp
	 * @return int
	 */
	public int dpToPx(Context context,int dp) 
	{
		DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
		int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));       
		return px;
	}
}
//	/**
//	 * This method is use to change sp to pixel
//	 * @param background
//	 * @param character
//	 */
//	 private int getPixels(int unit, float size) 
//	 {
//	    DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
//	    return (int)TypedValue.applyDimension(unit, size, metrics);
//	    
////	    DisplayMetrics dm = new DisplayMetrics();
////	    getWindowManager().getDefaultDisplay().getMetrics(dm);
////	    pixelSize = (int)scaledPixelSize * dm.scaledDensity; 
//	}
