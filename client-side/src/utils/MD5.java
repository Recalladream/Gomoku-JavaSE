package utils;

import java.security.MessageDigest;

public class MD5 {
    private static MD5 md5=new MD5();

    public static MD5 getMD5(){
        return md5;
    }

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
