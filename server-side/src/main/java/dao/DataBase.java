package dao;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DataBase {
    String smg;
    Connection con;
    Statement scz;
    PreparedStatement pcz;
    ResultSet find;
    //;
    public DataBase(Connection con){
        this.con=con;
    }

    public String getName(String account){
        String name=null;
        try {
            smg="select * from userinf where account=?";
            pcz=con.prepareStatement(smg);
            pcz.setString(1,account);
            find=pcz.executeQuery();
            find.next();
            name=find.getString("nickname");
        }catch (Exception e){e.printStackTrace();}
        return name;
    }

    public String snowflakeJudg(String ID,String function){
        try {
            smg="select * from user where account = ?";

            pcz=con.prepareStatement(smg);
            pcz.setString(1,ID);
            find=pcz.executeQuery();

            while (find.next()){
                if (function.equals("1")){
                    String user_account=find.getString("account");
                    if (user_account.equals(ID)){
                        return "1";
                    }
                }
            }

        }catch (Exception e){e.printStackTrace();}

        return "2";
    }

    public void leaderBoard(Object infset[],String userid){
        try {
            int pm=1;
            Object tabinf[][]=new Object[100][5];

            smg="select * from integral ORDER BY points DESC";
            pcz=con.prepareStatement(smg);
            find=pcz.executeQuery();

            while (find.next()){
                String account=find.getString("account");
                String usernaem=find.getString("nickname");
                int points=find.getInt("points");
                int win=find.getInt("win");
                int lose=find.getInt("lose");

                if (pm<=100) {
                    tabinf[pm - 1][0] = pm;
                    tabinf[pm - 1][1] = usernaem;
                    tabinf[pm - 1][2] = points;
                    tabinf[pm - 1][3] = win;
                    tabinf[pm - 1][4] = lose;
                }

                if (account.equals(userid)){
                    infset[0]=pm;
                    infset[1]=points;
                    infset[2]=win;
                    infset[3]=lose;
                    if (pm>100)break;
                }
                pm++;
            }

            infset[4]=tabinf;
        }catch (Exception e){e.printStackTrace();}
    }

    public void computedIntegral(String account,int points){
        try {
            if (points>=0){
                smg="update integral set points =points + ? , win = win + ? where account = ?";
                pcz=con.prepareStatement(smg);
                pcz.setInt(1,points);
                pcz.setInt(2,1);
                pcz.setString(3,account);
                pcz.executeUpdate();
            }
            if (points<0){
                smg="update integral set points =points + ? where account = ? and points >= ?";
                pcz=con.prepareStatement(smg);
                pcz.setInt(1,points);
                pcz.setString(2,account);
                pcz.setInt(3,Math.abs(points));
                pcz.executeUpdate();

                smg="update integral set lose = lose + ? where account = ?";
                pcz=con.prepareStatement(smg);
                pcz.setInt(1,1);
                pcz.setString(2,account);
                pcz.executeUpdate();
            }
        }catch (Exception e){e.printStackTrace();}
    }

    private void joinIntegral(String userid,String nickname){
        try {
            smg="insert into integral(account,nickname) values(?,?)";
            pcz=con.prepareStatement(smg);
            pcz.setString(1,userid);
            pcz.setString(2,nickname);
            pcz.executeUpdate();
        }catch (Exception e){e.printStackTrace();}
    }

    private void joinUser(String account,String password){
        try {
            smg="insert into user(account,password) values(?,?)";
            pcz=con.prepareStatement(smg);
            pcz.setString(1,account);
            pcz.setString(2,password);
            pcz.executeUpdate();
        }catch (Exception e){e.printStackTrace();}
    }
    private void joinUserInf(String account,String nickname,String telephone,String mail){
        try {
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date=new Date();
            String time=sdf.format(date);

            smg="insert into userinf(account,nickname,telephone,mailbox,joindate) values(?,?,?,?,?)";
            pcz=con.prepareStatement(smg);
            pcz.setString(1,account);
            pcz.setString(2,nickname);
            pcz.setString(3,telephone);
            pcz.setString(4,mail);
            pcz.setString(5,time);
            pcz.executeUpdate();
        }catch (Exception e){e.printStackTrace();}
    }
    public String userRegister(String account,String nickname,String password,String telephone,String mail){
        try {
            smg="select * from userinf where telephone = ? or mailbox = ?";
            pcz=con.prepareStatement(smg);
            pcz.setString(1,telephone);
            pcz.setString(2,mail);
            find=pcz.executeQuery();

            while (find.next()){
                String user_tele_phone=find.getString("telephone");
                String user_mail_box=find.getString("mailbox");

                if (user_tele_phone.equals(telephone))
                    return "1";
                if (user_mail_box.equals(mail))
                    return "2";
            }

        }catch (Exception e){e.printStackTrace();}
        joinUser(account,password);
        joinUserInf(account,nickname,telephone,mail);
        joinIntegral(account,nickname);
        return "3";
    }

    public String loginCheck(String account,String pass){
        try {
            smg="select * from user where account = ?";
            pcz=con.prepareStatement(smg);
            pcz.setString(1,account);

            find=pcz.executeQuery();

            if (find.next()){
                String user_account=find.getString("account");
                String user_pass=find.getString("password");

                if (user_account.equals(account))
                    if (user_pass.equals(pass)){
                        return getName(account);
                    }
                    else{
                        return "2";
                    }
            }
        }catch (Exception e){e.printStackTrace();}
        return "3";
    }

    private String accountRetrieveJudge(String account,String xpass){
        try {
            smg="select * from user where account = ?";
            pcz=con.prepareStatement(smg);
            pcz.setString(1,account);
            find=pcz.executeQuery();
            find.next();
            String user_account=find.getString("account");
            String user_pass_word=find.getString("password");

            if (user_account.equals(account))
                if (!user_pass_word.equals(xpass))
                    return "1";
                else
                    return "2";
        }catch (Exception e){e.printStackTrace();}
        return "3";
    }
    public String accountRetrieval(String account,String xpass){
        String judejg="";
        try {
            judejg=accountRetrieveJudge(account,xpass);
            if (judejg.equals("1")){
                smg="update user set password = ? where account = ?";
                pcz=con.prepareStatement(smg);
                pcz.setString(1,xpass);
                pcz.setString(2,account);
                pcz.executeUpdate();
            }
        }catch (Exception e){e.printStackTrace();}
        return judejg;
    }

    private String changePasswordJudge(String account,String ypass,String xpass){
        try {
            smg="select * from user where account = ?";
            pcz=con.prepareStatement(smg);
            pcz.setString(1,account);
            find=pcz.executeQuery();

            find.next();
            String user_account=find.getString("account");
            String user_pass_word=find.getString("password");


            if (user_account.equals(account))
                if (user_pass_word.equals(ypass)){
                    if (ypass.equals(xpass))
                        return "3";

                    return "4";
                }
                else
                    return "1";
        }catch (Exception e){}
        return "2";
    }
    public String changePassword(String account,String ypass,String xpass){
        String judejg="";
        try {
            judejg=changePasswordJudge(account,ypass,xpass);
            if (judejg.equals("4")){
                smg="update user set password = ? where account = ?";
                pcz=con.prepareStatement(smg);
                pcz.setString(1,xpass);
                pcz.setString(2,account);
                pcz.executeUpdate();
            }
        }catch (Exception e){e.printStackTrace();}
        return judejg;
    }
}
