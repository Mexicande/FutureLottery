package cn.com.futurelottery.presenter;

/**
 *
 * @author apple
 * @date 2018/4/22
 * 更新选中数量
 */

public class FooterOneEvent {
    private int mOne;
    private int type;
    public FooterOneEvent(int messge,int type) {
        this.mOne=messge;
        this.type=type;
    }

    public int getmMeeage() {
        return mOne;
    }

    public int getType() {
        return type;
    }

}
