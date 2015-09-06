package com.kingscastle.ui;

import android.graphics.Paint;
import android.support.annotation.NonNull;

import com.kingscastle.gameUtils.vector;

import org.jetbrains.annotations.NotNull;


public class TextLabel
{
    @NotNull
	private String msg;
    @NotNull
	private vector loc;
    @NotNull
	private Paint paint;
	private boolean visible = true;


	public TextLabel( @NotNull String msg, @NotNull vector loc, @NotNull Paint paint)
	{
		this.msg=msg;
        this.loc=loc;
        this.paint=paint;
	}

	@NonNull
    public String getMsg() {
		return msg;
	}


	public void setMsg(@NotNull String msg) {
		this.msg = msg;
	}


	@NonNull
    public vector getLoc() {
		return loc;
	}


	public void setLoc(@NotNull vector loc) {
		this.loc = loc;
	}


	@NonNull
    public Paint getPaint() {
		return paint;
	}


	public void setPaint(@NotNull Paint paint) {
		this.paint = paint;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}



}
