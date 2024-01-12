package client_utils;

import java.security.MessageDigest;

/**
 * MD5加密算法
 */
public class MD5 {
    private static final MD5 md5=new MD5();

    /**
     * 单列模式私有化构造
     */
    private MD5(){
    }

    /**
     * 获得单列类
     * @return
     */
    public static MD5 getMD5(){
        return md5;
    }

    /**
     * 执行加密算法
     * @param source
     * @return
     */
    public String encipher(String source) {

        StringBuffer sb = new StringBuffer(32);

        try {

            MessageDigest md = MessageDigest.getInstance("MD5");

            byte[] array = md.digest(source.getBytes("utf-8"));

            for (int i = 0; i < array.length; i++) {

                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).toUpperCase().substring(1, 3));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
