package com.kingscastle.framework;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

import com.kingscastle.gameUtils.vector;
import com.kingscastle.ui.ImageLabel;
import com.kingscastle.ui.TextLabel;


public interface Graphics {

	enum ImageFormat {
		ARGB8888, ARGB4444, RGB565
	}

	Image newImage(String fileName, ImageFormat format);

	void clearScreen(int color);

	void drawPaint(Paint p);

	void drawCircle(vector v, float radius);

    void drawCircle(float x, float y, float radius);

    void drawLine(float x, float y, float x2, float y2, int color);




	void drawRect(float x, float y, float width, float height, int color);

	void drawRect(RectF area, vector offset, int color);

	void drawRect(float x, float y, float width, float height, Paint paint);

	void drawRect(RectF rectF, Paint paint);

	void drawRect(Rect rect, Paint paint);

	void drawRect(Rect miniMapArea, int black);

	void drawRect(Rect rect, vector v, int color);

	void drawRect(Rect rect, vector v, Paint paint);

	void drawRect(RectF rectF, vector v, Paint paint);


	void drawRectBorder(RectF r, vector offset, int color, float borderWidth);

	void drawRectBorder(float x, float y, float width, float height, int color,
                        float borderWidth);

	void drawRectBorder(Rect rect, int color, float borderWidth);




	void drawRoundRect(float left, float top, float right, float bottom, Paint paint);

	void drawRoundRect(RectF rect, int color);

	void drawRoundRect(float left, float top, float right, float bottom, int color);



	void drawRectBorder(RectF selectionArea, int color, float borderWidth);

	void drawRoundRectBorder(RectF rect, int color, float borderWidth);

	void drawRoundRectBorder(float left, float top, float right, float bottom,
                             int color, float borderWidth);






	void drawImage(Image Image, int srcLeft, int srcTop, int srcRight,
                   int srcBottom, int dstLeft, int dstTop, int dstRight,
                   int dstBottom);


	void drawImage(Image image, int x, int y, int srcX, int srcY,
                   int srcWidth, int srcHeight);

	void drawImage(Image Image, int x, int y);

	void drawImage(Image fogtile, float xOffs, float yOffs);

	void drawImage(Image image, float left, float top, Paint paint);

	void drawImage(Image image, float left, float top, vector offset);

	void drawImage(Image image, Rect src, Rect dst);
	void drawImage(Image image, Rect srcRect, Rect dst, Paint paint);

	void drawImage(Image img, float x, float y, float width, float height);


	void drawImageOnWholeScreen(Image loadingScreen);

	void drawString(String text, float x, float y, Paint paint);

	int getWidth();

	int getHeight();

	void drawARGB(int i, int j, int k, int l);




	Bitmap getBitmap();


	Image newImage(int id, ImageFormat rgb565);



	int getWidthDiv2();

	int getHeightDiv2();

	void drawLine(float x, float y, float x2, float y2, int color, float width);

	void drawTextLabel(TextLabel tl);

	void drawTextLabel(TextLabel tl, float xoffs, float yoffs);

	void drawImageLabel(ImageLabel il);

	void drawImageLabel(ImageLabel il, float xoffs, float yoffs);



	void setCanvas(Canvas c);

	Canvas getCanvas();



	void drawString(String string, float centerX, float top, vector offset,
                    Paint paint);

	void drawImage(Image currentImage, float f, float g, vector offset,
                   Paint dstoverpaint);

	void setBitmap(Bitmap framebuff);

    void save();

    void clipRect(Rect area);












}
