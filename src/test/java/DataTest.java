import dao.DataBase;
import dao.ThirdPartyLibrary;
import logininterface.LoginInter;
import org.junit.Test;
import utils.DataBaseConifg;

public class DataTest {
    DataBase dataBase=new DataBase(DataBaseConifg.getConnection());
    ThirdPartyLibrary thirdPartyLibrary=new ThirdPartyLibrary();

    @Test
    public void join_excel(){
        try {
            thirdPartyLibrary.into_excel(DataBaseConifg.getConnection(),"select * from user");
        }catch (Exception e){e.printStackTrace();}
    }
}
