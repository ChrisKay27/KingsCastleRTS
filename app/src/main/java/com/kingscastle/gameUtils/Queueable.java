package com.kingscastle.gameUtils;


import com.kingscastle.gameElements.Cost;

public interface Queueable {
	String getName();
	int getBuildTime();
	void setBuildTime(int bt);
	void queueableComplete();
	Cost getCosts();
}
