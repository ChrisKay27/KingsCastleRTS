package com.kingscastle.framework;

public interface Sound {
    public void play(float volume);

    public void dispose();

	void play(float volume, float rate);
}
 