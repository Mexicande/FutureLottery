package cn.com.futurelottery.presenter;

/**
 *
 * @author apple
 * @date 2018/5/2
 * 筛选
 */

public class CompetitionSelectType {
    private int mSelect;
    private String  mLeague;
    public CompetitionSelectType(int type,String league) {
        this.mSelect=type;
        this.mLeague=league;
    }

    public int getmSelect() {
        return mSelect;
    }

    public void setmSelect(int mSelect) {
        this.mSelect = mSelect;
    }

    public String getmLeague() {
        return mLeague;
    }

    public void setmLeague(String mLeague) {
        this.mLeague = mLeague;
    }
}
