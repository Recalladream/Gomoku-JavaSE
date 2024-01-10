package mainprogram;

import conrtroller.InfClassification;
import utils.InforMationSet;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

public class ServerThread extends Thread{
    ArrayList<String> yhztlist;
    HashMap<String,Socket>map;
    HashMap<Socket,ObjectOutputStream>putmap;
    HashMap<String,String>matchmap;
    Connection con;
    Socket KH;
    ObjectInputStream obgetkh;
    ObjectOutputStream obputkh;

    public void initSocket(){
        try {
            obgetkh = new ObjectInputStream(KH.getInputStream());
            obputkh = new ObjectOutputStream(KH.getOutputStream());
            System.out.println("正常连接");
        }catch (Exception e){e.printStackTrace();}
    }

    public ServerThread(Socket KH,ArrayList<String> yhztlist,HashMap<String,Socket> map, HashMap<Socket,ObjectOutputStream>putmap,HashMap<String,String>matchmap,Connection con){
        try {
            this.KH=KH;
            this.yhztlist=yhztlist;
            this.map=map;
            this.putmap=putmap;
            this.matchmap=matchmap;
            this.con=con;
            initSocket();
        }catch (Exception e){e.printStackTrace();}
    }

    public void run(){
        InforMationSet bdjs=new InforMationSet();
        bdjs.setFunction("false");

        while (true){
            try {
                if (KH.isClosed()){
                    System.out.println("正常断开");
                    break;
                }
                else {
                    InforMationSet getinfjh = (InforMationSet) obgetkh.readObject();
                    InfClassification inffl = new InfClassification(getinfjh,KH,obputkh,yhztlist,map,putmap,matchmap,bdjs,con);
                    inffl.functionalresolution();
                }
            }catch (Exception e){e.printStackTrace();}
        }
    }
}