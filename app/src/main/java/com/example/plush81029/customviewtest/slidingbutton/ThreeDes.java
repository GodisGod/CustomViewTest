//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.example.plush81029.customviewtest.slidingbutton;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class ThreeDes {
    private static final String Algorithm = "DESede";

    public ThreeDes() {
    }

    public static byte[] encryptMode(byte[] keybyte, byte[] src) throws Exception {
        return encryptMode(keybyte, src, 0);
    }

    public static byte[] encryptMode(byte[] keybyte, byte[] src, int k) throws Exception {
        SecretKeySpec deskey = new SecretKeySpec(keybyte, "DESede");
        Cipher c1 = Cipher.getInstance("DESede");
        if(k == 1) {
            c1 = Cipher.getInstance("DESede/ECB/NoPadding");
        }

        c1.init(1, deskey);
        return c1.doFinal(src);
    }

    public static byte[] encryptModedes(byte[] keybyte, byte[] src) throws Exception {
        SecretKeySpec deskey = new SecretKeySpec(keybyte, "DES");
        Cipher c1 = Cipher.getInstance("DESede");
        c1.init(1, deskey);
        return c1.doFinal(src);
    }

    public static byte[] decryptMode(byte[] keybyte, byte[] src) throws Exception {
        return decryptMode(keybyte, src, 0);
    }

    public static byte[] decryptMode(byte[] keybyte, byte[] src, int k) throws Exception {
        SecretKeySpec deskey = new SecretKeySpec(keybyte, "DESede");
        Cipher c1 = Cipher.getInstance("DESede");
        if(k == 1) {
            c1 = Cipher.getInstance("DESede/ECB/NoPadding");
        }

        c1.init(2, deskey);
        return c1.doFinal(src);
    }

    public static String byte2Hex(byte[] b) {
        String hs = "";
        String stmp = "";

        for(int n = 0; n < b.length; ++n) {
            stmp = Integer.toHexString(b[n] & 255);
            if(stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }

            if(n < b.length - 1) {
                hs = hs + ":";
            }
        }

        return hs.toUpperCase();
    }

    public static String parseByte2HexStr(byte[] buf) {
        StringBuffer sb = new StringBuffer();

        for(int i = 0; i < buf.length; ++i) {
            String hex = Integer.toHexString(buf[i] & 255);
            if(hex.length() == 1) {
                hex = '0' + hex;
            }

            sb.append(hex.toUpperCase());
        }

        return sb.toString();
    }

    public static byte[] parseHexStr2Byte(String hexStr) {
        if(hexStr.length() < 1) {
            return null;
        } else {
            byte[] result = new byte[hexStr.length() / 2];

            for(int i = 0; i < hexStr.length() / 2; ++i) {
                int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
                int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
                result[i] = (byte)(high * 16 + low);
            }

            return result;
        }
    }

    public static String encrypt(String key, String src) throws Exception {
        return parseByte2HexStr(encryptMode(key.getBytes(), src.getBytes()));
    }

    public static String encrypt(String key, String src, int k) throws Exception {
        return parseByte2HexStr(encryptMode(key.getBytes(), src.getBytes(), k));
    }

    public static byte[] decrypt(String key, String src) throws Exception {
        return decryptMode(key.getBytes(), parseHexStr2Byte(src));
    }

    public static byte[] decrypt(String key, String src, int k) throws Exception {
        return decryptMode(key.getBytes(), parseHexStr2Byte(src), k);
    }
}
