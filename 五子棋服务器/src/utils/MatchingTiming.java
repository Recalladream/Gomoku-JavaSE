package utils;

import dao.DataBase;

import javax.swing.*;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.util.HashMap;

public class MatchingTiming extends Thread{
    String userid;

    HashMap<String,String> matchmap;
    ObjectOutputStream obputkh;
    InforMationSet bdjs;

    Connection con;

    public MatchingTiming(String userid, HashMap<String,String> matchmap, ObjectOutputStream obputkh, InforMationSet bdjs, Connection con){
        this.userid=userid;
        this.matchmap=matchmap;
        this.obputkh=obputkh;
        this.bdjs=bdjs;
        this.con=con;
    }

    @Override
    public void run() {
        try {
            int a=0;

            while (true){
                if (bdjs.getFunction().equals("true"))break;

                a++;

                if (!(matchmap.get(userid)==null)){
                    String bzid=matchmap.get(userid);

                    DataBase dataBase=new DataBase(con);

                    InforMationSet putfor=new InforMationSet();
                    putfor.setFunction("Normal_Successful");
                    putfor.setKind(-1);
                    putfor.setUsername(dataBase.getName(bzid));

                    obputkh.writeObject((Object) putfor);

                    break;
                }

                if (a>=60){
                    InforMationSet putfor=new InforMationSet();
                    putfor.setFunction("Normal_lose");

                    obputkh.writeObject((Object) putfor);

                    break;
                }

                MatchingTiming.sleep(1000);
            }

        }catch (Exception e){e.printStackTrace();}
    }
}
