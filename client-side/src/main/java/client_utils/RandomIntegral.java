package client_utils;

import java.util.Random;

public class RandomIntegral {
    public static int getIntegral(Boolean win){
        int points=0;

        Random random=new Random();

        if (win){
            points=random.nextInt(12)+20;
        }else {
            points=(-20)-random.nextInt(12);
        }

        return points;
    }
}
