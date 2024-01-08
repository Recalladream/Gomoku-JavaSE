package dao;

import java.sql.*;

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
            smg="select * from user where account=?";
            pcz=con.prepareStatement(smg);
            pcz.setString(1,account);
            find=pcz.executeQuery();
            find.next();
            name=find.getString("name");
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

            while (find.next()&&pm<=100){
                String account=find.getString("account");
                String usernaem=find.getString("username");
                int points=find.getInt("points");
                int win=find.getInt("win");
                int lose=find.getInt("lose");

                tabinf[pm-1][0]=pm;
                tabinf[pm-1][1]=usernaem;
                tabinf[pm-1][2]=points;
                tabinf[pm-1][3]=win;
                tabinf[pm-1][4]=lose;

                if (account.equals(userid)){
                    infset[0]=pm;
                    infset[1]=points;
                    infset[2]=win;
                    infset[3]=lose;
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

    private void joinIntegral(String userid,String username){
        try {
            smg="insert into integral(account,username,points) values(?,?,?)";
            pcz=con.prepareStatement(smg);
            pcz.setString(1,userid);
            pcz.setString(2,username);
            pcz.setInt(3,0);
            pcz.executeUpdate();
        }catch (Exception e){e.printStackTrace();}
    }

    private void joinUser(String account,String nickname,String password,String telephone,String mail){
        try {
            smg="insert into user(account,password,name,telephone,email) values(?,?,?,?,?)";
            pcz=con.prepareStatement(smg);
            pcz.setString(1,account);
            pcz.setString(2,password);
            pcz.setString(3,nickname);
            pcz.setString(4,telephone);
            pcz.setString(5,mail);
            pcz.executeUpdate();

        }catch (Exception e){e.printStackTrace();}
    }
    public String userRegister(String account,String nickname,String password,String telephone,String mail){
        try {
            smg="select * from user where telephone = ? or email = ?";
            pcz=con.prepareStatement(smg);
            pcz.setString(1,telephone);
            pcz.setString(2,mail);
            find=pcz.executeQuery();

            while (find.next()){
                String user_tele_phone=find.getString("telephone");
                String user_mail_box=find.getString("email");

                if (user_tele_phone.equals(telephone))
                    return "1";
                if (user_mail_box.equals(mail))
                    return "2";
            }

        }catch (Exception e){e.printStackTrace();}
        joinUser(account,nickname,password,telephone,mail);
        joinIntegral(account,nickname);
        return "3";
    }

    public void online(String account){
        try {
            smg="update user set off_line = ? where account = ?";
            pcz=con.prepareStatement(smg);
            pcz.setInt(1,1);
            pcz.setString(2,account);
            pcz.executeUpdate();
        }catch (Exception e){e.printStackTrace();}
    }

    public void offLine(String account){
        try {
            smg="update user set off_line = ? where account = ?";
            pcz=con.prepareStatement(smg);
            pcz.setInt(1,0);
            pcz.setString(2,account);
            pcz.executeUpdate();
        }catch (Exception e){e.printStackTrace();}
    }

    public String loginCheck(String account,String pass){
        try {
            smg="select * from user where account = ?";
            pcz=con.prepareStatement(smg);
            pcz.setString(1,account);
            find=pcz.executeQuery();

            while (find.next()){
                String user_nick_name=find.getString("name");
                String user_account=find.getString("account");
                String user_pass=find.getString("password");
                boolean user_off_line=find.getBoolean("off_line");

                if (user_account.equals(account))
                    if (user_pass.equals(pass)){
                        if (user_off_line)
                            return "4";

                        return user_nick_name;
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

            while (find.next()){
                String user_account=find.getString("account");
                String user_pass_word=find.getString("password");

                if (user_account.equals(account))
                    if (!user_pass_word.equals(xpass))
                        return "1";
                    else
                        return "2";
            }
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

            while (find.next()){
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
            }
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
