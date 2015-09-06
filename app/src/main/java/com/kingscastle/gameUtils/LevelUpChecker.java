package com.kingscastle.gameUtils;

/**
 * Created by Chris on 9/4/2015 for KingsCastle-Heroes
 */
public class LevelUpChecker {

    private static final long[] exps = new long[]{0, 100, 200, 400, 800, 1500, 2600, 4200, 6400, 9300, 13000, 17600, 23200, 29900, 37800, 47000, 57600, 69700, 83400, 98800};

    public static int getLevelForExp(int exp){

        for (int i = 0; i < exps.length; i++) {
            if( exps[i] > exp )
                return i;
        }
        return 20;
    }

}
