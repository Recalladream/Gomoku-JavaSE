import config.DataBaseConifg;
import dao.DataBase;
import dao.ThirdPartyLibrary;
import org.junit.Test;
import server_utils.SnowflakeAlgorithm;

import java.util.Random;

public class DataTest {
    DataBase dataBase=new DataBase(DataBaseConifg.getConnection());
    ThirdPartyLibrary thirdPartyLibrary=new ThirdPartyLibrary();

    String[] name1= {"王", "张", "许", "彭", "田", "周", "陈", "李", "姜", "杨", "邓", "刘","黄","郭","唐","石","吕","毛"};
    String[] name2 = {"伟","芳", "秀英","文迪","静","洋","杰","艳","涛","强","磊","军","刚","敏","超","丽","娜","雅琪","娟","子乔"};

    private String getID(String function){
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

    private String getNickName(){
        Random random = new Random();
        Random random1 = new Random();
        String name= name1[random.nextInt(18)] + name2[random.nextInt(20)];
        return name;
    }

    @Test
    public void join_Excel(){
        try {
            thirdPartyLibrary.into_excel(DataBaseConifg.getConnection(),"select * from user");
        }catch (Exception e){e.printStackTrace();}
    }

    @Test
    public void join_User(){
        for (int i=1;i<=300;i++){
        String state=dataBase.userRegister(getID("1"),getNickName(),getID("1"),getID("1"),getID("1")+"@qq.com");
        System.out.println(state);
        }
    }

}
