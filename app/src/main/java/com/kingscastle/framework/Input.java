package com.kingscastle.framework;

import android.support.annotation.NonNull;

import java.util.List;

public interface Input {


	class TouchEvent {
		public static final int TOUCH_DOWN = 0;
		public static final int TOUCH_UP = 1;
		public static final int TOUCH_DRAGGED = 2;
		public static final int TOUCH_HOLD = 3;

		public int type;
		public int x, y;
		public int pointer;

		@NonNull
        @Override
		public String toString()
		{
			switch( type )
			{
			case 0: return "TouchEvent TOUCH_DOWN [ " + x + " , " + y + "]";
			case 1: return "TouchEvent TOUCH_UP [ " + x + " , " + y + "]";
			case 2: return "TouchEvent TOUCH_DRAGGED [ " + x + " , " + y + "]";
			case 3: return "TouchEvent TOUCH_HOLD [ " + x + " , " + y + "]";
			}

			return "TouchEvent UnknownTypeWtf [ " + x + " , " + y + "]";
		}
	}

	boolean isTouchDown(int pointer);

	int getTouchX(int pointer);

	int getTouchY(int pointer);

	List<TouchEvent> getTouchEvents();

}