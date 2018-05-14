package cn.com.futurelottery.model;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;
import java.util.List;

import cn.com.futurelottery.ui.adapter.football.ScoreListAdapter;
import cn.com.futurelottery.ui.adapter.football.WinAndLoseAdapter;

/**
 * Created by apple on 2018/4/17.
 */

public class ScoreList implements Serializable, MultiItemEntity {


    private int code;
    private String message;
    private int error_code;
    private String error_message;
    private String time;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getError_message() {
        return error_message;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    @Override
    public int getItemType() {
        return 0;
    }

    public static class DataBean extends AbstractExpandableItem<ScoreList.DataBean.MatchBean>
            implements MultiItemEntity,Serializable {


        private String date;
        private String week;
        private int count;
        private List<MatchBean> match;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getWeek() {
            return week;
        }

        public void setWeek(String week) {
            this.week = week;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public List<MatchBean> getMatch() {
            return match;
        }

        public void setMatch(List<MatchBean> match) {
            this.match = match;
        }

        @Override
        public int getLevel() {
            return 0;
        }

        @Override
        public int getItemType() {
            return ScoreListAdapter.TYPE_LEVEL_0;
        }

        public static class MatchBean implements MultiItemEntity,Serializable{
            /**
             * id : 560
             * match_id : 20180419*4*001
             * daytime : 20180419
             * weekid : 4
             * teamid : 001
             * league : 荷甲
             * team : 乌德勒支:格罗宁根
             * endtime : 23:56
             * matchtime : 2018-04-20 00:30:00
             * flage : 0
             * wtype : 1
             * champion :
             * unsupport_type : FT006,0&FT001,0
             * created_at : 2018-04-17 21:22:47
             * homeTeam : 乌德勒支
             * awayTeam : 格罗宁根
             * week : 星期四
             * odds : [[{"odds":"9.00","name":"1:0","describe":"1:0奖金(赔率)"},{"odds":"8.25","name":"2:0","describe":"2:0奖金(赔率)"},{"odds":"7.00","name":"2:1","describe":"2:1奖金(赔率)"},{"odds":"11.00","name":"3:0","describe":"3:0奖金(赔率)"},{"odds":"10.00","name":"3:1","describe":"3:1奖金(赔率)"},{"odds":"16.00","name":"3:2","describe":"3:2奖金(赔率)"},{"odds":"19.00","name":"4:0","describe":"4:0奖金(赔率)"},{"odds":"18.00","name":"4:1","describe":"4:1奖金(赔率)"},{"odds":"30.00","name":"4:2","describe":"4:2奖金(赔率)"},{"odds":"40.00","name":"5:0","describe":"5:0奖金(赔率)"},{"odds":"35.00","name":"5:1","describe":"5:1奖金(赔率)"},{"odds":"60.00","name":"5:2","describe":"5:2奖金(赔率)"},{"odds":"19.00","name":"胜其它","describe":"胜其它奖金(赔率)"}],[{"odds":"18.00","name":"0:0","describe":"0:0奖金(赔率)"},{"odds":"8.25","name":"1:1","describe":"1:1奖金(赔率)"},{"odds":"12.50","name":"2:2","describe":"2:2奖金(赔率)"},{"odds":"40.00","name":"3:3","describe":"3:3奖金(赔率)"},{"odds":"200.00","name":"平其它","describe":"平其它奖金(赔率)"}],[{"odds":"18.00","name":"0:1","describe":"0:1奖金(赔率)"},{"odds":"30.00","name":"0:2","describe":"0:2奖金(赔率)"},{"odds":"14.00","name":"1:2","describe":"1:2奖金(赔率)"},{"odds":"70.00","name":"0:3","describe":"0:3奖金(赔率)"},{"odds":"40.00","name":"1:3","describe":"1:3奖金(赔率)"},{"odds":"30.00","name":"2:3","describe":"2:3奖金(赔率)"},{"odds":"250.00","name":"0:4","describe":"0:4奖金(赔率)"},{"odds":"100.00","name":"1:4","describe":"1:4奖金(赔率)"},{"odds":"100.00","name":"2:4","describe":"2:4奖金(赔率)"},{"odds":"600.00","name":"0:5","describe":"0:5奖金(赔率)"},{"odds":"400.00","name":"1:5","describe":"1:5奖金(赔率)"},{"odds":"300.00","name":"2:5","describe":"2:5奖金(赔率)"},{"odds":"80.00","name":"负其它","describe":"负其它奖金(赔率)"}]]
             */

            private int homeType1;
            private int vsType1;
            private int awayType1;
            private int homeType2;
            private int vsType2;
            private int awayType2;

            private String select;
            private String id;
            private String match_id;
            private String daytime;
            private String weekid;
            private String teamid;
            private String league;
            private String team;
            private String endtime;
            private String matchtime;
            private String flage;
            private String wtype;
            private String champion;
            private String unsupport_type;
            private String created_at;
            private String homeTeam;
            private String awayTeam;
            private String week;
            private List<List<OddsBean>> odds;


            public int getHomeType1() {
                return homeType1;
            }

            public void setHomeType1(int homeType1) {
                this.homeType1 = homeType1;
            }

            public int getVsType1() {
                return vsType1;
            }

            public void setVsType1(int vsType1) {
                this.vsType1 = vsType1;
            }

            public int getAwayType1() {
                return awayType1;
            }

            public void setAwayType1(int awayType1) {
                this.awayType1 = awayType1;
            }

            public int getHomeType2() {
                return homeType2;
            }

            public void setHomeType2(int homeType2) {
                this.homeType2 = homeType2;
            }

            public int getVsType2() {
                return vsType2;
            }

            public void setVsType2(int vsType2) {
                this.vsType2 = vsType2;
            }

            public int getAwayType2() {
                return awayType2;
            }

            public void setAwayType2(int awayType2) {
                this.awayType2 = awayType2;
            }

            public String getSelect() {
                return select;
            }

            public void setSelect(String select) {
                this.select = select;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getMatch_id() {
                return match_id;
            }

            public void setMatch_id(String match_id) {
                this.match_id = match_id;
            }

            public String getDaytime() {
                return daytime;
            }

            public void setDaytime(String daytime) {
                this.daytime = daytime;
            }

            public String getWeekid() {
                return weekid;
            }

            public void setWeekid(String weekid) {
                this.weekid = weekid;
            }

            public String getTeamid() {
                return teamid;
            }

            public void setTeamid(String teamid) {
                this.teamid = teamid;
            }

            public String getLeague() {
                return league;
            }

            public void setLeague(String league) {
                this.league = league;
            }

            public String getTeam() {
                return team;
            }

            public void setTeam(String team) {
                this.team = team;
            }

            public String getEndtime() {
                return endtime;
            }

            public void setEndtime(String endtime) {
                this.endtime = endtime;
            }

            public String getMatchtime() {
                return matchtime;
            }

            public void setMatchtime(String matchtime) {
                this.matchtime = matchtime;
            }

            public String getFlage() {
                return flage;
            }

            public void setFlage(String flage) {
                this.flage = flage;
            }

            public String getWtype() {
                return wtype;
            }

            public void setWtype(String wtype) {
                this.wtype = wtype;
            }

            public String getChampion() {
                return champion;
            }

            public void setChampion(String champion) {
                this.champion = champion;
            }

            public String getUnsupport_type() {
                return unsupport_type;
            }

            public void setUnsupport_type(String unsupport_type) {
                this.unsupport_type = unsupport_type;
            }

            public String getCreated_at() {
                return created_at;
            }

            public void setCreated_at(String created_at) {
                this.created_at = created_at;
            }

            public String getHomeTeam() {
                return homeTeam;
            }

            public void setHomeTeam(String homeTeam) {
                this.homeTeam = homeTeam;
            }

            public String getAwayTeam() {
                return awayTeam;
            }

            public void setAwayTeam(String awayTeam) {
                this.awayTeam = awayTeam;
            }

            public String getWeek() {
                return week;
            }

            public void setWeek(String week) {
                this.week = week;
            }

            public List<List<OddsBean>> getOdds() {
                return odds;
            }

            public void setOdds(List<List<OddsBean>> odds) {
                this.odds = odds;
            }

            @Override
            public int getItemType() {
                return ScoreListAdapter.TYPE_LEVEL_1;
            }

            public static class OddsBean implements Serializable{
                /**
                 * odds : 9.00
                 * name : 1:0
                 * describe : 1:0奖金(赔率)
                 */
                private int type;
                private String odds;
                private String name;
                private String describe;

                public int getType() {
                    return type;
                }

                public void setType(int type) {
                    this.type = type;
                }

                public String getOdds() {
                    return odds;
                }

                public void setOdds(String odds) {
                    this.odds = odds;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getDescribe() {
                    return describe;
                }

                public void setDescribe(String describe) {
                    this.describe = describe;
                }

                @Override
                public String toString() {
                    return "OddsBean{" +
                            "type=" + type +
                            ", odds='" + odds + '\'' +
                            ", name='" + name + '\'' +
                            ", describe='" + describe + '\'' +
                            '}';
                }

            }

            @Override
            public String toString() {
                return "MatchBean{" +
                        "homeType1=" + homeType1 +
                        ", vsType1=" + vsType1 +
                        ", awayType1=" + awayType1 +
                        ", homeType2=" + homeType2 +
                        ", vsType2=" + vsType2 +
                        ", awayType2=" + awayType2 +
                        ", select='" + select + '\'' +
                        ", id='" + id + '\'' +
                        ", match_id='" + match_id + '\'' +
                        ", daytime='" + daytime + '\'' +
                        ", weekid='" + weekid + '\'' +
                        ", teamid='" + teamid + '\'' +
                        ", league='" + league + '\'' +
                        ", team='" + team + '\'' +
                        ", endtime='" + endtime + '\'' +
                        ", matchtime='" + matchtime + '\'' +
                        ", flage='" + flage + '\'' +
                        ", wtype='" + wtype + '\'' +
                        ", champion='" + champion + '\'' +
                        ", unsupport_type='" + unsupport_type + '\'' +
                        ", created_at='" + created_at + '\'' +
                        ", homeTeam='" + homeTeam + '\'' +
                        ", awayTeam='" + awayTeam + '\'' +
                        ", week='" + week + '\'' +
                        ", odds=" + odds +
                        '}';
            }

        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "date='" + date + '\'' +
                    ", week='" + week + '\'' +
                    ", count=" + count +
                    ", match=" + match +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ScoreList{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", error_code=" + error_code +
                ", error_message='" + error_message + '\'' +
                ", time='" + time + '\'' +
                ", data=" + data +
                '}';
    }
}
