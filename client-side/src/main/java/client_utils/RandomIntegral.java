package client_utils;

import java.util.Random;

/**
 * 随机生成积分类
 */
public class RandomIntegral {
    /**
     * 生成积分方法
     * @param win
     * @return
     */
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
