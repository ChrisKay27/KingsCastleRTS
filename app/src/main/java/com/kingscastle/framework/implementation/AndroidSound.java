package com.kingscastle.framework.implementation;


import android.media.SoundPool;

import com.kingscastle.framework.Sound;


public class AndroidSound implements Sound {
    private final int soundId;
    private final SoundPool soundPool;

    public AndroidSound(SoundPool soundPool, int soundId) {
        this.soundId = soundId;
        this.soundPool = soundPool;
    }
    
    @Override
    public void play(float volume,float rate) {
        soundPool.play(soundId, volume, volume, 0, 0, rate>2?2:rate);
    }

    @Override
    public void play(float volume) {
    	play(volume,1);
    }

    @Override
    public void dispose() {
        soundPool.unload(soundId);
    }

}
