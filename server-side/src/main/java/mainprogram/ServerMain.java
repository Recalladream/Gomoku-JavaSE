package mainprogram;

import config.DataBaseConifg;

import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.HashMap;

public class ServerMain {
    public static void main(String[]args){
        try {
            ThreadPoolManagement pool=new ThreadPoolManagement();

            Connection con= DataBaseConifg.getConnection();

            ArrayList<String> yhztlist=new ArrayList<String>();
            HashMap<String,Socket> map=new HashMap<String,Socket>();
            HashMap<Socket, ObjectOutputStream>putmap=new HashMap<Socket,ObjectOutputStream>();
            HashMap<String,String>matchmap=new HashMap<String,String>();

            ServerSocket server=new ServerSocket(3998);
            while(true) {
                Runnable creat=new ServerThread(server.accept(),yhztlist,map,putmap,matchmap,con);
                pool.submit(creat);
            }
        }catch (Exception e){e.printStackTrace();}
    }
}