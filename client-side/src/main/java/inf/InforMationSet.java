package inf;

import java.io.Serializable;

public class InforMationSet implements Serializable {
    private String function;

    private String userid;

    private String userpass;

    private String username;

    private String telephone;

    private String userpm;

    private int userjf;

    private Object tabinf[][];

    private int win;

    private int lose;

    private int hqjl[];

    private int qzx;

    private int qzy;

    private int kind;

    private int winjudg;

    private String czstate;

    private String mailbox;

    private String code;

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUserpass() {
        return userpass;
    }

    public void setUserpass(String userpass) {
        this.userpass = userpass;
    }

    public int getQzx() {
        return qzx;
    }

    public void setQzx(int qzx) {
        this.qzx = qzx;
    }

    public int getQzy() {
        return qzy;
    }

    public void setQzy(int qzy) {
        this.qzy = qzy;
    }

    public int getKind() {
        return kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }

    public int getWinjudg() {
        return winjudg;
    }

    public void setWinjudg(int winjudg) {
        this.winjudg = winjudg;
    }

    public String getCzstate() {
        return czstate;
    }

    public void setCzstate(String czstate) {
        this.czstate = czstate;
    }

    public String getMailbox() {
        return mailbox;
    }

    public void setMailbox(String mailbox) {
        this.mailbox = mailbox;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getUserpm() {
        return userpm;
    }

    public void setUserpm(String userpm) {
        this.userpm = userpm;
    }

    public int getUserjf() {
        return userjf;
    }

    public void setUserjf(int userjf) {
        this.userjf = userjf;
    }

    public Object[][] getTabinf() {
        return tabinf;
    }

    public void setTabinf(Object[][] tabinf) {
        this.tabinf = tabinf;
    }

    public int getWin() {
        return win;
    }

    public void setWin(int win) {
        this.win = win;
    }

    public int getLose() {
        return lose;
    }

    public void setLose(int lose) {
        this.lose = lose;
    }

    public int[] getHqjl() {
        return hqjl;
    }

    public void setHqjl(int[] hqjl) {
        this.hqjl = hqjl;
    }
}