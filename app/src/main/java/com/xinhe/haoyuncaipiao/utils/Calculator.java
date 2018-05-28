package com.xinhe.haoyuncaipiao.utils;

public class Calculator {
    public final static int RED = 6;

    public final static int BLUE = 1;

    public final static int MAX_BLUE = 16;

    public final static int MAX_RED = 16;

    /**
   * @Description: 计算双色球中奖情况
   * @param red 选中的红球个数
   * @param guessRed 猜中的红球个数
   * @param blue 选中的篮球个数
   * @param guessBlue   猜中的篮球个数
     */
    public  long []  calculateRewardLevels(int red,int guessRed,int blue,int guessBlue){
        long rewardSum[] = new long[6];
        //参数有效性检查
        if(red>MAX_RED||red<RED||guessRed>RED||guessRed<0||blue<BLUE||blue>MAX_BLUE||guessBlue>BLUE||guessBlue<0){
            throw new IllegalArgumentException("参数不合法");
        }

        int notGuessBule = blue - guessBlue;

        for(int i=guessRed; i>=0;i--){
            if(red-guessRed+i<6)//确保组合时，Cmn，m>=n
                break;
            int recoreds = combination(guessRed,i) * combination(red-guessRed,RED-i);

            if(recoreds*guessBlue!=0){//蓝球中1个
                if(i == 6 && guessBlue == 1){//6+1	一等奖
                    rewardSum[0] = recoreds*guessBlue;
                }
                if(i == 5 && guessBlue == 1) {//5+1	三等奖
                    rewardSum[2] = recoreds*guessBlue;
                }
                if(i == 4 && guessBlue == 1) {//4+1	四等奖
                    rewardSum[3] += recoreds*guessBlue;
                }
                if(i == 3 && guessBlue == 1) {//3+1	五等奖
                    rewardSum[4] += recoreds*guessBlue;
                }
                if((i == 2 || i == 1 || i == 0) && guessBlue == 1) {//2+1 1+1 1+0	六等奖
                    rewardSum[5] += recoreds*guessBlue;
                }
            }
            if(recoreds*notGuessBule!=0){//蓝球中0个
                if(i == 6) {//6+0	二等奖
                    rewardSum[1] = recoreds*notGuessBule;
                }
                if(i == 5) {//5+0	四等奖
                    rewardSum[3] += recoreds*notGuessBule;
                }
                if(i == 4) {//4+0	五等奖
                    rewardSum[4] += recoreds*notGuessBule;
                }
            }
        }
        return rewardSum;
    }

    /**
     * Cmn，组合算法
     */
    public static int combination(int m,int n){
        int k = 1;
        int j = 1;
        for(int i = n; i>=1;i--){
            k = k*m;
            j = j*n;
            m--;
            n--;
        }
        return k/j;
    }

    /**
     * 双色球普通方式算法
     * 根据红篮球数，算出投注注数
     */

    public static long calculateBetNum(int redBallNum, int blueBallNum) {
        return combination(redBallNum, 6) * blueBallNum;
    }

    /**
     * 双色球胆拖方式算法
     * 根据红篮球数，算出投注注数
     */

    public static long calculateDanTuoNum(int redDanBallNum, int redTuoBallNum,int blueBallNum) {
        long number;
        if (redDanBallNum+redTuoBallNum<6||blueBallNum==0||redDanBallNum==0||redTuoBallNum==0||redDanBallNum+redTuoBallNum<7){
            number=0;
        }else {
            number=combination(redTuoBallNum, 6-redDanBallNum) * blueBallNum;

        }
        return number;
    }


    /**
     * 大乐透普通方式算法
     * 根据红篮球数，算出投注注数
     */

    public static long calculateSLNum(int redBallNum, int blueBallNum) {
        return combination(redBallNum, 5) * combination(blueBallNum, 2);
    }


    /**
     * 大乐透胆拖方式算法
     * 根据红篮球数，算出投注注数
     */

    public static long calculateSLDanTuoNum(int redDanBallNum, int redTuoBallNum,int blueDanBallNum,int blueTuoBallNum) {
        long number;
        if (redDanBallNum+redTuoBallNum<6||redDanBallNum==0||redTuoBallNum==0||blueTuoBallNum<2||blueDanBallNum+blueTuoBallNum<2||redDanBallNum+redTuoBallNum+blueDanBallNum+blueTuoBallNum<8){
            number=0;
        }else {
            number=combination(redTuoBallNum, 5-redDanBallNum) * combination(blueTuoBallNum, 2-blueDanBallNum);

        }
        return number;
    }

    /**
     * 排三组三复式算法
     *
     */

    public static long arrange3ThreeCompound(int count) {
        long number;
        if (count<1){
            number=0;
        }else {
            number=count*(count-1);

        }
        return number;
    }
    /**
     * 排三组六算法
     *
     */

    public static long arrange3Six(int count) {
        long number;
        if (count<2){
            number=0;
        }else {
            number=count*(count-1)*(count-2)/6;

        }
        return number;
    }

}