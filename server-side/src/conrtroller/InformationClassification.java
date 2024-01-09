package conrtroller;

import dao.DataBase;
import utils.Email;
import utils.InforMationSet;
import utils.MatchingTiming;
import utils.SnowflakeAlgorithm;

import javax.swing.*;
import javax.swing.text.html.HTMLDocument;
import java.io.*;
import java.net.Socket;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class InformationClassification {
    HashMap<String,Socket>map;
    HashMap<Socket,ObjectOutputStream>putmap;
    HashMap<String,String>matchmap;
    Connection con;
    Socket KH;
    InforMationSet getinfjh;
    ObjectOutputStream obputkh;
    InforMationSet bdjs;

    public InformationClassification(InforMationSet getinfjh, Socket KH, ObjectOutputStream obputkh, HashMap<String,Socket>map, HashMap<Socket,ObjectOutputStream>putmap, HashMap<String,String>matchmap, InforMationSet bdjs,Connection con){
        this.KH=KH;
        this.getinfjh=getinfjh;
        this.obputkh=obputkh;
        this.map=map;
        this.putmap=putmap;
        this.matchmap=matchmap;
        this.bdjs=bdjs;
        this.con=con;
    }

    public void initmap(String userid){
        map.put(userid,KH);
        putmap.put(KH,obputkh);
    }

    public void losedeletmap(String userid){
        Socket desok=map.get(userid);
        map.remove(userid,desok);
        ObjectOutputStream deob=putmap.get(desok);
        putmap.remove(desok,deob);
        matchmap.remove(userid);
    }

    public void sucdeletmap(int kind,String userid){
        Socket desok=map.get(userid);
        map.remove(userid,desok);
        ObjectOutputStream deob=putmap.get(desok);
        putmap.remove(desok,deob);

        if (kind==-1){
            String bzid=matchmap.get(userid);
            matchmap.remove(userid,bzid);
        }
    }

    private void closeSocket(){
        try {
            KH.close();
        }catch (Exception e){e.printStackTrace();}
    }

    private String getID(String function){
        DataBase dataBase=new DataBase(con);
        String state;
        Long id;
        String ID="";
        try {
            while (true) {
                id=new SnowflakeAlgorithm().nextId();
                ID=new StringBuffer(id.toString()).reverse().toString().substring(0,10);

                state=dataBase.snowflakeJudg(ID,function);

                if (state.equals("2")&&!ID.substring(0,0).equals("0")){
                    break;
                }
            }
        }catch (Exception e){e.printStackTrace();}
        return ID;
    }

    public void onLine(String userid){
        try {
            String account=userid;

            DataBase dataBase=new DataBase(con);

            dataBase.online(account);
        }catch (Exception e){e.printStackTrace();}
    }

    public void offLine(String userid){
        try {
            String account=userid;

            DataBase dataBase=new DataBase(con);

            dataBase.offLine(account);

            map.remove(account,KH);
        }catch (Exception e){e.printStackTrace();}
    }

    public void functionalresolution(){
        String function=getinfjh.getFunction();

        if (function.equals("Transfer_piece")){
            try {
                String userid=getinfjh.getUserid();
                int kind=getinfjh.getKind();
                int qzx=getinfjh.getQzx();
                int qzy=getinfjh.getQzy();

                if (kind==-1){
                    String bzid=matchmap.get(userid);
                    if (bzid!=null){
                        Socket bzser=map.get(bzid);
                        if (bzser!=null){
                            ObjectOutputStream obputKH=putmap.get(bzser);

                            InforMationSet putinf=new InforMationSet();
                            putinf.setKind(kind);
                            putinf.setQzx(qzx);
                            putinf.setQzy(qzy);

                            obputKH.writeObject(putinf);
                        }
                    }
                }
                if (kind==1) {
                    String hzid=null;
                    for (Map.Entry<String,String> entry : matchmap.entrySet()){
                        if (userid.equals(entry.getValue())){
                            hzid=entry.getKey();
                            break;
                        }
                    }
                    if (hzid!=null){
                        Socket hzser=map.get(hzid);
                        if (hzser!=null){
                            ObjectOutputStream obputKH=putmap.get(hzser);

                            InforMationSet putinf=new InforMationSet();
                            putinf.setKind(kind);
                            putinf.setQzx(qzx);
                            putinf.setQzy(qzy);

                            obputKH.writeObject(putinf);
                        }
                    }
                }
            }catch (Exception e){e.printStackTrace();}
        }

        if (function.equals("leaderboard")){
            try {
                String userid=getinfjh.getUserid();

                Object infset[]=new Object[5];

                DataBase dataBase=new DataBase(con);
                dataBase.leaderBoard(infset,userid);

                InforMationSet putinf=new InforMationSet();
                putinf.setUserpm(Integer.toString((int)infset[0]));
                putinf.setUserjf((int)infset[1]);
                putinf.setWin((int)infset[2]);
                putinf.setLose((int)infset[3]);
                putinf.setTabinf((Object[][])infset[4]);

                obputkh.writeObject(putinf);
            }catch (Exception e){e.printStackTrace();}
        }

        if (function.equals("penitence")){
            try {
                String userid=getinfjh.getUserid();
                int kind=getinfjh.getKind();
                int hqjl[]=getinfjh.getHqjl();

                if (kind==-1){
                    String bzid=matchmap.get(userid);
                    if (bzid!=null){
                        Socket bzser=map.get(bzid);
                        if (bzser!=null){
                            ObjectOutputStream obputKH=putmap.get(bzser);

                            InforMationSet putinf=new InforMationSet();
                            putinf.setFunction("penitence");
                            putinf.setHqjl(hqjl);

                            obputKH.writeObject(putinf);
                        }
                    }
                }
                if (kind==1) {
                    String hzid=null;
                    for (Map.Entry<String,String> entry : matchmap.entrySet()){
                        if (userid.equals(entry.getValue())){
                            hzid=entry.getKey();
                            break;
                        }
                    }
                    if (hzid!=null){
                        Socket hzser=map.get(hzid);
                        if (hzser!=null){
                            ObjectOutputStream obputKH=putmap.get(hzser);

                            InforMationSet putinf=new InforMationSet();
                            putinf.setFunction("penitence");
                            putinf.setHqjl(hqjl);

                            obputKH.writeObject(putinf);
                        }
                    }
                }
            }catch (Exception e){e.printStackTrace();}
        }

        if (function.equals("computedintegral")){
            try {
                String userid=getinfjh.getUserid();
                int userjf=getinfjh.getUserjf();

                DataBase dataBase=new DataBase(con);

                dataBase.computedIntegral(userid,userjf);
            }catch (Exception e){e.printStackTrace();}
        }

        if (function.equals("off_line")){
            String account=getinfjh.getUserid();
            offLine(account);
            closeSocket();
        }

        if (function.equals("loseClose")){
            try {
                String userid=getinfjh.getUserid();

                InforMationSet putfor=new InforMationSet();
                putfor.setFunction("Close");

                obputkh.writeObject((Object) putfor);

                bdjs.setFunction("true");

                losedeletmap(userid);

                closeSocket();
            }catch (Exception e){e.printStackTrace();}
        }

        if (function.equals("succlose")){
            try {
                String userid=getinfjh.getUserid();
                int winjudg=getinfjh.getWinjudg();
                int kind=getinfjh.getKind();
                int points=getinfjh.getUserjf();

                if (winjudg==0){
                    if (kind==-1){
                        String bzid=matchmap.get(userid);
                        Socket bzser=map.get(bzid);
                        ObjectOutputStream obputKH=putmap.get(bzser);

                        InforMationSet putinf=new InforMationSet();
                        putinf.setFunction("runaway");

                        obputKH.writeObject(putinf);
                    }else if (kind==1){
                        String hzid=null;
                        for (Map.Entry<String,String> entry : matchmap.entrySet()){
                            if (entry.getValue().equals(userid)){
                                hzid=entry.getKey();
                                break;
                            }
                        }
                        if (hzid!=null){
                            Socket hzser=map.get(hzid);
                            ObjectOutputStream obputKH=putmap.get(hzser);

                            InforMationSet putinf=new InforMationSet();
                            putinf.setFunction("runaway");

                            obputKH.writeObject(putinf);
                        }
                    }

                    DataBase dataBase=new DataBase(con);
                    dataBase.computedIntegral(userid,points);
                }

                InforMationSet putinf=new InforMationSet();
                putinf.setFunction("close");

                obputkh.writeObject(putinf);

                sucdeletmap(kind,userid);

                closeSocket();
            }catch (Exception e){e.printStackTrace();}
        }

        if (function.equals("Close")){
            closeSocket();
        }

        if (function.equals("Normal")){
            try {
                String hzid=null;

                boolean bool=false;
                String userid=getinfjh.getUserid();

                initmap(userid);

                map.put(userid,KH);
                putmap.put(KH,obputkh);

                for (String key:matchmap.keySet()){
                    if (matchmap.get(key)==null){
                        bool=true;
                        hzid=key;
                        matchmap.put(key,userid);
                        break;
                    }
                }

                if (bool){
                    DataBase dataBase=new DataBase(con);

                    InforMationSet putfor=new InforMationSet();
                    putfor.setFunction("Normal_Successful");
                    putfor.setKind(1);
                    putfor.setUsername(dataBase.getName(hzid));

                    obputkh.writeObject((Object) putfor);
                }else {
                    matchmap.put(userid,null);

                    MatchingTiming matchingTiming=new MatchingTiming(userid,matchmap,obputkh,bdjs,con);
                    matchingTiming.start();
                }

            }catch (Exception e){e.printStackTrace();}
        }

        if (function.equals("Get_mail")){
            try {
                String smg=getinfjh.getCzstate();
                String mail=getinfjh.getMailbox();

                String code= Email.getCode();
                String state=Email.sendEmail(mail,code,smg);

                InforMationSet putinfjh=new InforMationSet();
                putinfjh.setCzstate(state);
                putinfjh.setCode(code);

                obputkh.writeObject(putinfjh);
                obputkh.flush();
            }catch (Exception e){e.printStackTrace();}
        }

        if (function.equals("User_register")){
            try {
                String account=getID("1");
                String nickname=getinfjh.getUsername();
                String password=getinfjh.getUserpass();
                String telephone=getinfjh.getTelephone();
                String mail=getinfjh.getMailbox();

                DataBase dataBase=new DataBase(con);
                String state=dataBase.userRegister(account,nickname,password,telephone,mail);

                InforMationSet putinfjh=new InforMationSet();
                putinfjh.setCzstate(state);
                putinfjh.setUserid(account);

                obputkh.writeObject(putinfjh);
                obputkh.flush();
            }catch (Exception e){e.printStackTrace();}
        }

        if (function.equals("Login_check")){
            try {
                String account=getinfjh.getUserid();
                String pass=getinfjh.getUserpass();

                DataBase dataBase=new DataBase(con);
                String state=dataBase.loginCheck(account,pass);

                InforMationSet putinfjh=new InforMationSet();
                putinfjh.setCzstate(state);

                obputkh.writeObject(putinfjh);
                obputkh.flush();

                if (!state.equals("2")&&!state.equals("3")&&!state.equals("4")){
                    onLine(account);
                    closeSocket();
                }
            }catch (Exception e){e.printStackTrace();}
        }

        if (function.equals("Account_retrieval")){
            try {
                String account=getinfjh.getUserid();
                String xpass=getinfjh.getUserpass();

                DataBase dataBase=new DataBase(con);
                String state=dataBase.accountRetrieval(account,xpass);

                InforMationSet putinfjh=new InforMationSet();
                putinfjh.setCzstate(state);

                obputkh.writeObject(putinfjh);
                obputkh.flush();
            }catch (Exception e){e.printStackTrace();}
        }

        if (function.equals("Change_password")){
            try {
                String account=getinfjh.getUserid();
                String ypass=getinfjh.getUserpass();
                String xpass=getinfjh.getCzstate();

                DataBase dataBase=new DataBase(con);
                String state=dataBase.changePassword(account,ypass,xpass);

                InforMationSet putinfjh=new InforMationSet();
                putinfjh.setCzstate(state);

                obputkh.writeObject(putinfjh);
                obputkh.flush();
            }catch (Exception e){e.printStackTrace();}
        }
    }
}