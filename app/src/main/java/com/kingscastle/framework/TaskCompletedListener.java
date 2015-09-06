package com.kingscastle.framework;

public interface TaskCompletedListener<T> {
	
	/**
	 * @param t null if task did not complete successfully
	 */
	void onTaskCompleted(T t);

}
