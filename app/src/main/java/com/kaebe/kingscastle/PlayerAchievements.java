package com.kaebe.kingscastle;

import android.content.SharedPreferences;

/**
 * Created by Chris on 8/1/2015 for Tower Defence
 */
public class PlayerAchievements {

    private SharedPreferences sp;
    private UnlockAchievementListener ual;

    private int monstersKilled;


    public void load(UnlockAchievementListener ual ,SharedPreferences sp){
        this.ual = ual;
        this.sp = sp;
        monstersKilled = sp.getInt("MonstersKilled",0);
    }


    public void save(){
        sp.edit().putInt("MonstersKilled", monstersKilled).apply();
    }

    public void incMonstersKilled(){
        monstersKilled++;
        if( monstersKilled == 50 )
            ual.unlockAchievement(R.string.achievement_kill_50_monsters);
    }


    public interface UnlockAchievementListener{
        void unlockAchievement(int achieveID);
    }
}
