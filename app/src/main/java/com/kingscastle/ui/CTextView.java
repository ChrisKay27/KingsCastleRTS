package com.kingscastle.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

public class CTextView extends View{

	private final Paint mPaint = new Paint();{
		mPaint.setTextSize(30);
		mPaint.setAntiAlias( true );
		//mPaint.setTextAlign(Paint.Align.CENTER);
		UIUtil.applyCooperBlack(mPaint);
	}
	private final ArrayList<String> text = new ArrayList<String>();
	@Nullable
    private String text2;

	private float x;
	private float y;

	public CTextView(Context context) {
		super(context);
	}
	public CTextView(@NonNull Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	public CTextView(@NonNull Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onDraw( @NonNull Canvas c ){
		float yOffs = 0f;
		synchronized( text ){
			for( String t : text ){
				mPaint.setColor( Color.BLACK );
				c.drawText(t , x -2 , y-mPaint.ascent()-2 + yOffs, mPaint );
				c.drawText(t , x +2 , y-mPaint.ascent()+2 + yOffs , mPaint );
				mPaint.setColor( Color.WHITE );

				c.drawText(t , x , y-mPaint.ascent() + yOffs , mPaint );
				yOffs += mPaint.getFontSpacing();
			}
		}
	}
	@Nullable
    public String getText() {
		return text2;
	}
	@NonNull
    public CTextView setText(@Nullable String text_ ) {
		this.text2 = text_;

		if( text_ != null ){
			synchronized( text ){
				text.clear();
				for( String t : text_.split("\n"))
					text.add(t);
			}
		}
		return this;
	}
	@Override
	public float getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	@Override
	public float getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	@NonNull
    public Paint getPaint() {
		return mPaint;
	}
	public void setTextSize( float size ){
		mPaint.setTextSize(size);
	}




}
