package cn.com.futurelottery.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tantan on 2018/5/16.
 */

public class Chipped implements Serializable{

    /**
     * code : 200
     * message : OK
     * data : {"info":[{"id":"1","together_id":"152635522643212971","lotid":"ssq","strands":"0","play_type":"103","pay_money_total":"22.00","baseline_money":"1.00","multiple":"1","notes":"6","data":"[\"01,02,03,04$07,08,09#01,02\"]","cut":"1","phase":"2018055","percentage":"3.00","type":"1","declaration":"跟我买中大奖","created_at":"2018-05-15 03:33:46","updated_at":"2018-05-15 03:33:46","number":1,"name":"双色球","user_name":"幸运彩60571189","remaining_money":19,"progress":13.64,"probability":0,"time":-83074},{"id":"5","together_id":"152644678299381722","lotid":"ssq","strands":"0","play_type":"103","pay_money_total":"22.00","baseline_money":"1.00","multiple":"1","notes":"6","data":"[\"01,02,03,04$07,08,09#01,02\"]","cut":"1","phase":"2018056","percentage":"3.00","type":"1","declaration":"跟我买中大奖","created_at":"2018-05-16 04:59:42","updated_at":"2018-05-16 04:59:42","number":1,"name":"双色球","user_name":"幸运彩60571189","remaining_money":19,"progress":13.64,"probability":0,"time":89726},{"id":"9","together_id":"152644678332604886","lotid":"ssq","strands":"0","play_type":"103","pay_money_total":"22.00","baseline_money":"1.00","multiple":"1","notes":"6","data":"[\"01,02,03,04$07,08,09#01,02\"]","cut":"1","phase":"2018056","percentage":"3.00","type":"1","declaration":"跟我买中大奖","created_at":"2018-05-16 04:59:43","updated_at":"2018-05-16 04:59:43","number":1,"name":"双色球","user_name":"幸运彩60571189","remaining_money":19,"progress":13.64,"probability":0,"time":89726},{"id":"13","together_id":"152644678493850830","lotid":"ssq","strands":"0","play_type":"103","pay_money_total":"22.00","baseline_money":"1.00","multiple":"1","notes":"6","data":"[\"01,02,03,04$07,08,09#01,02\"]","cut":"1","phase":"2018056","percentage":"3.00","type":"1","declaration":"跟我买中大奖","created_at":"2018-05-16 04:59:44","updated_at":"2018-05-16 04:59:44","number":1,"name":"双色球","user_name":"幸运彩60571189","remaining_money":19,"progress":13.64,"probability":0,"time":89726},{"id":"17","together_id":"152644678430374698","lotid":"ssq","strands":"0","play_type":"103","pay_money_total":"22.00","baseline_money":"1.00","multiple":"1","notes":"6","data":"[\"01,02,03,04$07,08,09#01,02\"]","cut":"1","phase":"2018056","percentage":"3.00","type":"1","declaration":"跟我买中大奖","created_at":"2018-05-16 04:59:44","updated_at":"2018-05-16 04:59:44","number":1,"name":"双色球","user_name":"幸运彩60571189","remaining_money":19,"progress":13.64,"probability":0,"time":89726},{"id":"21","together_id":"152644678546472899","lotid":"ssq","strands":"0","play_type":"103","pay_money_total":"22.00","baseline_money":"1.00","multiple":"1","notes":"6","data":"[\"01,02,03,04$07,08,09#01,02\"]","cut":"1","phase":"2018056","percentage":"3.00","type":"1","declaration":"跟我买中大奖","created_at":"2018-05-16 04:59:45","updated_at":"2018-05-16 04:59:45","number":1,"name":"双色球","user_name":"幸运彩60571189","remaining_money":19,"progress":13.64,"probability":0,"time":89726},{"id":"25","together_id":"152644678517601926","lotid":"ssq","strands":"0","play_type":"103","pay_money_total":"22.00","baseline_money":"1.00","multiple":"1","notes":"6","data":"[\"01,02,03,04$07,08,09#01,02\"]","cut":"1","phase":"2018056","percentage":"3.00","type":"1","declaration":"跟我买中大奖","created_at":"2018-05-16 04:59:46","updated_at":"2018-05-16 04:59:46","number":1,"name":"双色球","user_name":"幸运彩60571189","remaining_money":19,"progress":13.64,"probability":0,"time":89726},{"id":"29","together_id":"152644678660852451","lotid":"ssq","strands":"0","play_type":"103","pay_money_total":"22.00","baseline_money":"1.00","multiple":"1","notes":"6","data":"[\"01,02,03,04$07,08,09#01,02\"]","cut":"1","phase":"2018056","percentage":"3.00","type":"1","declaration":"跟我买中大奖","created_at":"2018-05-16 04:59:46","updated_at":"2018-05-16 04:59:46","number":1,"name":"双色球","user_name":"幸运彩60571189","remaining_money":19,"progress":13.64,"probability":0,"time":89726},{"id":"33","together_id":"152644678715544108","lotid":"ssq","strands":"0","play_type":"103","pay_money_total":"22.00","baseline_money":"1.00","multiple":"1","notes":"6","data":"[\"01,02,03,04$07,08,09#01,02\"]","cut":"1","phase":"2018056","percentage":"3.00","type":"1","declaration":"跟我买中大奖","created_at":"2018-05-16 04:59:47","updated_at":"2018-05-16 04:59:47","number":1,"name":"双色球","user_name":"幸运彩60571189","remaining_money":19,"progress":13.64,"probability":0,"time":89726},{"id":"37","together_id":"152644678729863493","lotid":"ssq","strands":"0","play_type":"103","pay_money_total":"22.00","baseline_money":"1.00","multiple":"1","notes":"6","data":"[\"01,02,03,04$07,08,09#01,02\"]","cut":"1","phase":"2018056","percentage":"3.00","type":"1","declaration":"跟我买中大奖","created_at":"2018-05-16 04:59:47","updated_at":"2018-05-16 04:59:47","number":1,"name":"双色球","user_name":"幸运彩60571189","remaining_money":19,"progress":13.64,"probability":0,"time":89726},{"id":"41","together_id":"152644678856758677","lotid":"ssq","strands":"0","play_type":"103","pay_money_total":"22.00","baseline_money":"1.00","multiple":"1","notes":"6","data":"[\"01,02,03,04$07,08,09#01,02\"]","cut":"1","phase":"2018056","percentage":"3.00","type":"1","declaration":"跟我买中大奖","created_at":"2018-05-16 04:59:48","updated_at":"2018-05-16 04:59:48","number":1,"name":"双色球","user_name":"幸运彩60571189","remaining_money":19,"progress":13.64,"probability":0,"time":89726},{"id":"45","together_id":"152644678824531291","lotid":"ssq","strands":"0","play_type":"103","pay_money_total":"22.00","baseline_money":"1.00","multiple":"1","notes":"6","data":"[\"01,02,03,04$07,08,09#01,02\"]","cut":"1","phase":"2018056","percentage":"3.00","type":"1","declaration":"跟我买中大奖","created_at":"2018-05-16 04:59:48","updated_at":"2018-05-16 04:59:48","number":1,"name":"双色球","user_name":"幸运彩60571189","remaining_money":19,"progress":13.64,"probability":0,"time":89726},{"id":"49","together_id":"152644678974298130","lotid":"ssq","strands":"0","play_type":"103","pay_money_total":"22.00","baseline_money":"1.00","multiple":"1","notes":"6","data":"[\"01,02,03,04$07,08,09#01,02\"]","cut":"1","phase":"2018056","percentage":"3.00","type":"1","declaration":"跟我买中大奖","created_at":"2018-05-16 04:59:49","updated_at":"2018-05-16 04:59:49","number":1,"name":"双色球","user_name":"幸运彩60571189","remaining_money":19,"progress":13.64,"probability":0,"time":89726},{"id":"53","together_id":"152644678970018284","lotid":"ssq","strands":"0","play_type":"103","pay_money_total":"22.00","baseline_money":"1.00","multiple":"1","notes":"6","data":"[\"01,02,03,04$07,08,09#01,02\"]","cut":"1","phase":"2018056","percentage":"3.00","type":"1","declaration":"跟我买中大奖","created_at":"2018-05-16 04:59:49","updated_at":"2018-05-16 04:59:49","number":1,"name":"双色球","user_name":"幸运彩60571189","remaining_money":19,"progress":13.64,"probability":0,"time":89726},{"id":"57","together_id":"152644679023921300","lotid":"ssq","strands":"0","play_type":"103","pay_money_total":"22.00","baseline_money":"1.00","multiple":"1","notes":"6","data":"[\"01,02,03,04$07,08,09#01,02\"]","cut":"1","phase":"2018056","percentage":"3.00","type":"1","declaration":"跟我买中大奖","created_at":"2018-05-16 04:59:50","updated_at":"2018-05-16 04:59:50","number":1,"name":"双色球","user_name":"幸运彩60571189","remaining_money":19,"progress":13.64,"probability":0,"time":89726},{"id":"61","together_id":"152644679039407318","lotid":"ssq","strands":"0","play_type":"103","pay_money_total":"22.00","baseline_money":"1.00","multiple":"1","notes":"6","data":"[\"01,02,03,04$07,08,09#01,02\"]","cut":"1","phase":"2018056","percentage":"3.00","type":"1","declaration":"跟我买中大奖","created_at":"2018-05-16 04:59:50","updated_at":"2018-05-16 04:59:50","number":1,"name":"双色球","user_name":"幸运彩60571189","remaining_money":19,"progress":13.64,"probability":0,"time":89726},{"id":"65","together_id":"152644679141345355","lotid":"ssq","strands":"0","play_type":"103","pay_money_total":"22.00","baseline_money":"1.00","multiple":"1","notes":"6","data":"[\"01,02,03,04$07,08,09#01,02\"]","cut":"1","phase":"2018056","percentage":"3.00","type":"1","declaration":"跟我买中大奖","created_at":"2018-05-16 04:59:51","updated_at":"2018-05-16 04:59:51","number":1,"name":"双色球","user_name":"幸运彩60571189","remaining_money":19,"progress":13.64,"probability":0,"time":89726},{"id":"69","together_id":"152644679191969396","lotid":"ssq","strands":"0","play_type":"103","pay_money_total":"22.00","baseline_money":"1.00","multiple":"1","notes":"6","data":"[\"01,02,03,04$07,08,09#01,02\"]","cut":"1","phase":"2018056","percentage":"3.00","type":"1","declaration":"跟我买中大奖","created_at":"2018-05-16 04:59:51","updated_at":"2018-05-16 04:59:51","number":1,"name":"双色球","user_name":"幸运彩60571189","remaining_money":19,"progress":13.64,"probability":0,"time":89726},{"id":"73","together_id":"152644679268348255","lotid":"ssq","strands":"0","play_type":"103","pay_money_total":"22.00","baseline_money":"1.00","multiple":"1","notes":"6","data":"[\"01,02,03,04$07,08,09#01,02\"]","cut":"1","phase":"2018056","percentage":"3.00","type":"1","declaration":"跟我买中大奖","created_at":"2018-05-16 04:59:52","updated_at":"2018-05-16 04:59:52","number":1,"name":"双色球","user_name":"幸运彩60571189","remaining_money":19,"progress":13.64,"probability":0,"time":89726},{"id":"77","together_id":"152644679236514804","lotid":"ssq","strands":"0","play_type":"103","pay_money_total":"22.00","baseline_money":"1.00","multiple":"1","notes":"6","data":"[\"01,02,03,04$07,08,09#01,02\"]","cut":"1","phase":"2018056","percentage":"3.00","type":"1","declaration":"跟我买中大奖","created_at":"2018-05-16 04:59:52","updated_at":"2018-05-16 04:59:52","number":1,"name":"双色球","user_name":"幸运彩60571189","remaining_money":19,"progress":13.64,"probability":0,"time":89726}],"limit_end":20}
     * error_code : 0
     * error_message :
     * time : 2018-05-16 18:55:02
     */

    private int code;
    private String message;
    private DataProduct data;
    private int error_code;
    private String error_message;
    private String time;

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

    public DataProduct getData() {
        return data;
    }

    public void setData(DataProduct data) {
        this.data = data;
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

    public static class DataProduct {
        /**
         * info : [{"id":"1","together_id":"152635522643212971","lotid":"ssq","strands":"0","play_type":"103","pay_money_total":"22.00","baseline_money":"1.00","multiple":"1","notes":"6","data":"[\"01,02,03,04$07,08,09#01,02\"]","cut":"1","phase":"2018055","percentage":"3.00","type":"1","declaration":"跟我买中大奖","created_at":"2018-05-15 03:33:46","updated_at":"2018-05-15 03:33:46","number":1,"name":"双色球","user_name":"幸运彩60571189","remaining_money":19,"progress":13.64,"probability":0,"time":-83074},{"id":"5","together_id":"152644678299381722","lotid":"ssq","strands":"0","play_type":"103","pay_money_total":"22.00","baseline_money":"1.00","multiple":"1","notes":"6","data":"[\"01,02,03,04$07,08,09#01,02\"]","cut":"1","phase":"2018056","percentage":"3.00","type":"1","declaration":"跟我买中大奖","created_at":"2018-05-16 04:59:42","updated_at":"2018-05-16 04:59:42","number":1,"name":"双色球","user_name":"幸运彩60571189","remaining_money":19,"progress":13.64,"probability":0,"time":89726},{"id":"9","together_id":"152644678332604886","lotid":"ssq","strands":"0","play_type":"103","pay_money_total":"22.00","baseline_money":"1.00","multiple":"1","notes":"6","data":"[\"01,02,03,04$07,08,09#01,02\"]","cut":"1","phase":"2018056","percentage":"3.00","type":"1","declaration":"跟我买中大奖","created_at":"2018-05-16 04:59:43","updated_at":"2018-05-16 04:59:43","number":1,"name":"双色球","user_name":"幸运彩60571189","remaining_money":19,"progress":13.64,"probability":0,"time":89726},{"id":"13","together_id":"152644678493850830","lotid":"ssq","strands":"0","play_type":"103","pay_money_total":"22.00","baseline_money":"1.00","multiple":"1","notes":"6","data":"[\"01,02,03,04$07,08,09#01,02\"]","cut":"1","phase":"2018056","percentage":"3.00","type":"1","declaration":"跟我买中大奖","created_at":"2018-05-16 04:59:44","updated_at":"2018-05-16 04:59:44","number":1,"name":"双色球","user_name":"幸运彩60571189","remaining_money":19,"progress":13.64,"probability":0,"time":89726},{"id":"17","together_id":"152644678430374698","lotid":"ssq","strands":"0","play_type":"103","pay_money_total":"22.00","baseline_money":"1.00","multiple":"1","notes":"6","data":"[\"01,02,03,04$07,08,09#01,02\"]","cut":"1","phase":"2018056","percentage":"3.00","type":"1","declaration":"跟我买中大奖","created_at":"2018-05-16 04:59:44","updated_at":"2018-05-16 04:59:44","number":1,"name":"双色球","user_name":"幸运彩60571189","remaining_money":19,"progress":13.64,"probability":0,"time":89726},{"id":"21","together_id":"152644678546472899","lotid":"ssq","strands":"0","play_type":"103","pay_money_total":"22.00","baseline_money":"1.00","multiple":"1","notes":"6","data":"[\"01,02,03,04$07,08,09#01,02\"]","cut":"1","phase":"2018056","percentage":"3.00","type":"1","declaration":"跟我买中大奖","created_at":"2018-05-16 04:59:45","updated_at":"2018-05-16 04:59:45","number":1,"name":"双色球","user_name":"幸运彩60571189","remaining_money":19,"progress":13.64,"probability":0,"time":89726},{"id":"25","together_id":"152644678517601926","lotid":"ssq","strands":"0","play_type":"103","pay_money_total":"22.00","baseline_money":"1.00","multiple":"1","notes":"6","data":"[\"01,02,03,04$07,08,09#01,02\"]","cut":"1","phase":"2018056","percentage":"3.00","type":"1","declaration":"跟我买中大奖","created_at":"2018-05-16 04:59:46","updated_at":"2018-05-16 04:59:46","number":1,"name":"双色球","user_name":"幸运彩60571189","remaining_money":19,"progress":13.64,"probability":0,"time":89726},{"id":"29","together_id":"152644678660852451","lotid":"ssq","strands":"0","play_type":"103","pay_money_total":"22.00","baseline_money":"1.00","multiple":"1","notes":"6","data":"[\"01,02,03,04$07,08,09#01,02\"]","cut":"1","phase":"2018056","percentage":"3.00","type":"1","declaration":"跟我买中大奖","created_at":"2018-05-16 04:59:46","updated_at":"2018-05-16 04:59:46","number":1,"name":"双色球","user_name":"幸运彩60571189","remaining_money":19,"progress":13.64,"probability":0,"time":89726},{"id":"33","together_id":"152644678715544108","lotid":"ssq","strands":"0","play_type":"103","pay_money_total":"22.00","baseline_money":"1.00","multiple":"1","notes":"6","data":"[\"01,02,03,04$07,08,09#01,02\"]","cut":"1","phase":"2018056","percentage":"3.00","type":"1","declaration":"跟我买中大奖","created_at":"2018-05-16 04:59:47","updated_at":"2018-05-16 04:59:47","number":1,"name":"双色球","user_name":"幸运彩60571189","remaining_money":19,"progress":13.64,"probability":0,"time":89726},{"id":"37","together_id":"152644678729863493","lotid":"ssq","strands":"0","play_type":"103","pay_money_total":"22.00","baseline_money":"1.00","multiple":"1","notes":"6","data":"[\"01,02,03,04$07,08,09#01,02\"]","cut":"1","phase":"2018056","percentage":"3.00","type":"1","declaration":"跟我买中大奖","created_at":"2018-05-16 04:59:47","updated_at":"2018-05-16 04:59:47","number":1,"name":"双色球","user_name":"幸运彩60571189","remaining_money":19,"progress":13.64,"probability":0,"time":89726},{"id":"41","together_id":"152644678856758677","lotid":"ssq","strands":"0","play_type":"103","pay_money_total":"22.00","baseline_money":"1.00","multiple":"1","notes":"6","data":"[\"01,02,03,04$07,08,09#01,02\"]","cut":"1","phase":"2018056","percentage":"3.00","type":"1","declaration":"跟我买中大奖","created_at":"2018-05-16 04:59:48","updated_at":"2018-05-16 04:59:48","number":1,"name":"双色球","user_name":"幸运彩60571189","remaining_money":19,"progress":13.64,"probability":0,"time":89726},{"id":"45","together_id":"152644678824531291","lotid":"ssq","strands":"0","play_type":"103","pay_money_total":"22.00","baseline_money":"1.00","multiple":"1","notes":"6","data":"[\"01,02,03,04$07,08,09#01,02\"]","cut":"1","phase":"2018056","percentage":"3.00","type":"1","declaration":"跟我买中大奖","created_at":"2018-05-16 04:59:48","updated_at":"2018-05-16 04:59:48","number":1,"name":"双色球","user_name":"幸运彩60571189","remaining_money":19,"progress":13.64,"probability":0,"time":89726},{"id":"49","together_id":"152644678974298130","lotid":"ssq","strands":"0","play_type":"103","pay_money_total":"22.00","baseline_money":"1.00","multiple":"1","notes":"6","data":"[\"01,02,03,04$07,08,09#01,02\"]","cut":"1","phase":"2018056","percentage":"3.00","type":"1","declaration":"跟我买中大奖","created_at":"2018-05-16 04:59:49","updated_at":"2018-05-16 04:59:49","number":1,"name":"双色球","user_name":"幸运彩60571189","remaining_money":19,"progress":13.64,"probability":0,"time":89726},{"id":"53","together_id":"152644678970018284","lotid":"ssq","strands":"0","play_type":"103","pay_money_total":"22.00","baseline_money":"1.00","multiple":"1","notes":"6","data":"[\"01,02,03,04$07,08,09#01,02\"]","cut":"1","phase":"2018056","percentage":"3.00","type":"1","declaration":"跟我买中大奖","created_at":"2018-05-16 04:59:49","updated_at":"2018-05-16 04:59:49","number":1,"name":"双色球","user_name":"幸运彩60571189","remaining_money":19,"progress":13.64,"probability":0,"time":89726},{"id":"57","together_id":"152644679023921300","lotid":"ssq","strands":"0","play_type":"103","pay_money_total":"22.00","baseline_money":"1.00","multiple":"1","notes":"6","data":"[\"01,02,03,04$07,08,09#01,02\"]","cut":"1","phase":"2018056","percentage":"3.00","type":"1","declaration":"跟我买中大奖","created_at":"2018-05-16 04:59:50","updated_at":"2018-05-16 04:59:50","number":1,"name":"双色球","user_name":"幸运彩60571189","remaining_money":19,"progress":13.64,"probability":0,"time":89726},{"id":"61","together_id":"152644679039407318","lotid":"ssq","strands":"0","play_type":"103","pay_money_total":"22.00","baseline_money":"1.00","multiple":"1","notes":"6","data":"[\"01,02,03,04$07,08,09#01,02\"]","cut":"1","phase":"2018056","percentage":"3.00","type":"1","declaration":"跟我买中大奖","created_at":"2018-05-16 04:59:50","updated_at":"2018-05-16 04:59:50","number":1,"name":"双色球","user_name":"幸运彩60571189","remaining_money":19,"progress":13.64,"probability":0,"time":89726},{"id":"65","together_id":"152644679141345355","lotid":"ssq","strands":"0","play_type":"103","pay_money_total":"22.00","baseline_money":"1.00","multiple":"1","notes":"6","data":"[\"01,02,03,04$07,08,09#01,02\"]","cut":"1","phase":"2018056","percentage":"3.00","type":"1","declaration":"跟我买中大奖","created_at":"2018-05-16 04:59:51","updated_at":"2018-05-16 04:59:51","number":1,"name":"双色球","user_name":"幸运彩60571189","remaining_money":19,"progress":13.64,"probability":0,"time":89726},{"id":"69","together_id":"152644679191969396","lotid":"ssq","strands":"0","play_type":"103","pay_money_total":"22.00","baseline_money":"1.00","multiple":"1","notes":"6","data":"[\"01,02,03,04$07,08,09#01,02\"]","cut":"1","phase":"2018056","percentage":"3.00","type":"1","declaration":"跟我买中大奖","created_at":"2018-05-16 04:59:51","updated_at":"2018-05-16 04:59:51","number":1,"name":"双色球","user_name":"幸运彩60571189","remaining_money":19,"progress":13.64,"probability":0,"time":89726},{"id":"73","together_id":"152644679268348255","lotid":"ssq","strands":"0","play_type":"103","pay_money_total":"22.00","baseline_money":"1.00","multiple":"1","notes":"6","data":"[\"01,02,03,04$07,08,09#01,02\"]","cut":"1","phase":"2018056","percentage":"3.00","type":"1","declaration":"跟我买中大奖","created_at":"2018-05-16 04:59:52","updated_at":"2018-05-16 04:59:52","number":1,"name":"双色球","user_name":"幸运彩60571189","remaining_money":19,"progress":13.64,"probability":0,"time":89726},{"id":"77","together_id":"152644679236514804","lotid":"ssq","strands":"0","play_type":"103","pay_money_total":"22.00","baseline_money":"1.00","multiple":"1","notes":"6","data":"[\"01,02,03,04$07,08,09#01,02\"]","cut":"1","phase":"2018056","percentage":"3.00","type":"1","declaration":"跟我买中大奖","created_at":"2018-05-16 04:59:52","updated_at":"2018-05-16 04:59:52","number":1,"name":"双色球","user_name":"幸运彩60571189","remaining_money":19,"progress":13.64,"probability":0,"time":89726}]
         * limit_end : 20
         */

        private int limit_end;
        private ArrayList<InfoProduct> info;

        public int getLimit_end() {
            return limit_end;
        }

        public void setLimit_end(int limit_end) {
            this.limit_end = limit_end;
        }

        public ArrayList<InfoProduct> getInfo() {
            return info;
        }

        public void setInfo(ArrayList<InfoProduct> info) {
            this.info = info;
        }

        public static class InfoProduct {
            /**
             * id : 1
             * together_id : 152635522643212971
             * lotid : ssq
             * strands : 0
             * play_type : 103
             * pay_money_total : 22.00
             * baseline_money : 1.00
             * multiple : 1
             * notes : 6
             * data : ["01,02,03,04$07,08,09#01,02"]
             * cut : 1
             * phase : 2018055
             * percentage : 3.00
             * type : 1
             * declaration : 跟我买中大奖
             * created_at : 2018-05-15 03:33:46
             * updated_at : 2018-05-15 03:33:46
             * number : 1
             * name : 双色球
             * user_name : 幸运彩60571189
             * remaining_money : 19
             * progress : 13.64
             * probability : 0
             * time : -83074
             */

            private String id;
            private String together_id;
            private String lotid;
            private String strands;
            private String play_type;
            private String pay_money_total;
            private String baseline_money;
            private String multiple;
            private String notes;
            private String data;
            private String cut;
            private String phase;
            private String percentage;
            private String type;
            private String declaration;
            private String created_at;
            private String updated_at;
            private int number;
            private String name;
            private String user_name;
            private int remaining_money;
            private double progress;
            private int probability;
            private int time;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTogether_id() {
                return together_id;
            }

            public void setTogether_id(String together_id) {
                this.together_id = together_id;
            }

            public String getLotid() {
                return lotid;
            }

            public void setLotid(String lotid) {
                this.lotid = lotid;
            }

            public String getStrands() {
                return strands;
            }

            public void setStrands(String strands) {
                this.strands = strands;
            }

            public String getPlay_type() {
                return play_type;
            }

            public void setPlay_type(String play_type) {
                this.play_type = play_type;
            }

            public String getPay_money_total() {
                return pay_money_total;
            }

            public void setPay_money_total(String pay_money_total) {
                this.pay_money_total = pay_money_total;
            }

            public String getBaseline_money() {
                return baseline_money;
            }

            public void setBaseline_money(String baseline_money) {
                this.baseline_money = baseline_money;
            }

            public String getMultiple() {
                return multiple;
            }

            public void setMultiple(String multiple) {
                this.multiple = multiple;
            }

            public String getNotes() {
                return notes;
            }

            public void setNotes(String notes) {
                this.notes = notes;
            }

            public String getData() {
                return data;
            }

            public void setData(String data) {
                this.data = data;
            }

            public String getCut() {
                return cut;
            }

            public void setCut(String cut) {
                this.cut = cut;
            }

            public String getPhase() {
                return phase;
            }

            public void setPhase(String phase) {
                this.phase = phase;
            }

            public String getPercentage() {
                return percentage;
            }

            public void setPercentage(String percentage) {
                this.percentage = percentage;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getDeclaration() {
                return declaration;
            }

            public void setDeclaration(String declaration) {
                this.declaration = declaration;
            }

            public String getCreated_at() {
                return created_at;
            }

            public void setCreated_at(String created_at) {
                this.created_at = created_at;
            }

            public String getUpdated_at() {
                return updated_at;
            }

            public void setUpdated_at(String updated_at) {
                this.updated_at = updated_at;
            }

            public int getNumber() {
                return number;
            }

            public void setNumber(int number) {
                this.number = number;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getUser_name() {
                return user_name;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
            }

            public int getRemaining_money() {
                return remaining_money;
            }

            public void setRemaining_money(int remaining_money) {
                this.remaining_money = remaining_money;
            }

            public double getProgress() {
                return progress;
            }

            public void setProgress(double progress) {
                this.progress = progress;
            }

            public int getProbability() {
                return probability;
            }

            public void setProbability(int probability) {
                this.probability = probability;
            }

            public int getTime() {
                return time;
            }

            public void setTime(int time) {
                this.time = time;
            }
        }
    }
}
