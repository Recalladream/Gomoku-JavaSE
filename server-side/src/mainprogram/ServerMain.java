package mainprogram;

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
            Class.forName("com.mysql.jdbc.Driver");
            Connection con= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/gomoku","root","478788223");

            ArrayList<String> yhztlist=new ArrayList<String>();
            HashMap<String,Socket> map=new HashMap<String,Socket>();
            HashMap<Socket, ObjectOutputStream>putmap=new HashMap<Socket,ObjectOutputStream>();
            HashMap<String,String>matchmap=new HashMap<String,String>();

            ServerSocket server=new ServerSocket(3998);
            while(true) {
                ServerThread creat=new ServerThread(server.accept(),yhztlist,map,putmap,matchmap,con);
                creat.start();
            }
        }catch (Exception e){e.printStackTrace();}
    }
}