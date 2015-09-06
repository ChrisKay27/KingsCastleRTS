package com.kingscastle.ui;

import android.graphics.Paint;

import com.kingscastle.framework.Image;
import com.kingscastle.gameUtils.vector;


public class ImageLabel {

	private Image img;private vector loc; private Paint paint;

	public ImageLabel(Image img,vector loc,Paint paint){
		this.img=img;this.loc=loc;this.paint=paint;
	}

	public Image getImage() {
		return img;
	}


	public void setImage(Image img) {
		this.img = img;
	}


	public vector getLoc() {
		return loc;
	}


	public void setLoc(vector loc) {
		this.loc = loc;
	}


	public Paint getPaint() {
		return paint;
	}


	public void setPaint(Paint paint) {
		this.paint = paint;
	}




}
