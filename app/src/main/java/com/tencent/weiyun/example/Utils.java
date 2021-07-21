package com.tencent.weiyun.example;

import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utils {

    public static byte[] md5(String str)
    {
        byte[] md5 = null;

        try
        {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            md5 = md.digest();
        }
        catch (NoSuchAlgorithmException e)
        {
            md5 = str.getBytes();
        }

        return md5;
    }

    public static void copyStream(InputStream is, OutputStream os)
    {
        final int buffer_size = 1024;
        try
        {
            byte[] bytes = new byte[buffer_size];
            for (;;)
            {
                int count = is.read(bytes, 0, buffer_size);
                if (count == -1)
                    break;
                os.write(bytes, 0, count);
            };
        }
        catch(Exception ex)
        {

        }
    }

    final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
    public static String hexToString(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static byte[] stringToHex(String hex) {
        int len = hex.length();
        if ((len & 0x01) != 0) {
            throw new RuntimeException("Odd number of characters.");
        }
        byte[] out = new byte[len >> 1];
        for (int i = 0, j = 0; j < len; i++) {
            int f = Character.digit(hex.charAt(j), 16) << 4;
            j++;
            f = f | Character.digit(hex.charAt(j), 16);
            j++;
            out[i] = (byte) (f & 0xFF);
        }
        return out;
    }
}
