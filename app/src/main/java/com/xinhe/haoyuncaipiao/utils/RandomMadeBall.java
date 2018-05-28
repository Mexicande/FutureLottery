package com.xinhe.haoyuncaipiao.utils;

import java.util.ArrayList;
import java.util.Collections;

/**
 * 随机球
 */

public class RandomMadeBall {
    /**
     * 生成随机验证码的的方法
     *
     * @return 装载随机验证码的的数组randomword
     */
    public static ArrayList<String> getManyBall(int count,int chooseCount) {
        ArrayList<Integer> randomRed = new ArrayList<Integer>();
        for (int i = 0; i < chooseCount; i++) {
            int hong = (int) (Math.random() * count);
            if (i == 0) {
                randomRed.add(hong);
            } else {
                if (randomRed.indexOf(hong) != -1) {
                    i--;
                } else {
                    randomRed.add(hong);
                }
            }
        }

        Collections.sort(randomRed);
        ArrayList<String> redBall = new ArrayList<String>();
        for (int k = 0; k < randomRed.size(); k++) {
            redBall.add(randomRed.get(k).toString());
        }
        return redBall;
    }

    /**
     * 生成随机验证码的的方法
     *
     * @return 装载随机验证码的的数组randomword
     */
    public static ArrayList<String> getOneBall(int count) {

        ArrayList<String> blueBall = new ArrayList<String>();
        for (int i = 0; i < 1; i++) {
            int blue = (int) (Math.random() * count);
            if (i == 0) {
                blueBall.add(blue + "");
            } else {
                if (blueBall.get(i - 1) == blue + "") {
                    i--;
                } else {
                    blueBall.add(blue + "");
                }
            }
        }
        return blueBall;
    }
}
