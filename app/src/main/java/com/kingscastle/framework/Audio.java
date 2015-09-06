package com.kingscastle.framework;


import com.kingscastle.framework.implementation.AndroidSound;

public interface Audio {
    public Music createMusic(String file);

    public AndroidSound createSound(String file);
}