package com.mod.loan.util;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * DES加解密工具类
 */
public class DesUtil {
    private static final String DES_ALGORITHM = "DES";
    private static final String DEFAULT_KEY= "1234abcd";

    /**
     * DES加密
     *
     * @param plainData 原始字符串
     * @param secretKey 加密密钥
     * @return 加密后的字符串
     */
    public static String encryption(String plainData, String secretKey) throws Exception {

        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance(DES_ALGORITHM);
            if (secretKey==null){
                secretKey = DEFAULT_KEY;
            }
            cipher.init(Cipher.ENCRYPT_MODE, generateKey(secretKey));

        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException ignored) {

        }

        try {
            // 为了防止解密时报javax.crypto.IllegalBlockSizeException: Input length must
            // be multiple of 8 when decrypting with padded cipher异常，
            // 不能把加密后的字节数组直接转换成字符串
            byte[] buf = cipher.doFinal(plainData.getBytes());

            return Base64Utils.encode(buf);

        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
            throw new Exception("IllegalBlockSizeException", e);
        } catch (BadPaddingException e) {
            e.printStackTrace();
            throw new Exception("BadPaddingException", e);
        }

    }

    public static String encryption(String plainData) throws Exception {
        return encryption(plainData, null);
    }

    public static String decryption(String plainData) throws Exception {
        return decryption(plainData, null);
    }

    /**
     * DES解密
     * @param secretData 密码字符串
     * @param secretKey 解密密钥
     * @return 原始字符串
     */
    public static String decryption(String secretData, String secretKey) throws Exception {

        Cipher cipher;
        try {
            cipher = Cipher.getInstance(DES_ALGORITHM);
            if (secretKey==null){
                secretKey = DEFAULT_KEY;
            }
            cipher.init(Cipher.DECRYPT_MODE, generateKey(secretKey));

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new Exception("NoSuchAlgorithmException", e);
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            throw new Exception("NoSuchPaddingException", e);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
            throw new Exception("InvalidKeyException", e);
        }

        try {

            byte[] buf = cipher.doFinal(Base64Utils.decode(secretData.toCharArray()));

            return new String(buf);

        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
            throw new Exception("IllegalBlockSizeException", e);
        } catch (BadPaddingException e) {
            e.printStackTrace();
            throw new Exception("BadPaddingException", e);
        }
    }

    private static SecretKey generateKey(String secretKey)
            throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException {
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES_ALGORITHM);
        DESKeySpec keySpec = new DESKeySpec(secretKey.getBytes());
        keyFactory.generateSecret(keySpec);
        return keyFactory.generateSecret(keySpec);
    }


    static private class Base64Utils {

        static private char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/="
                .toCharArray();
        static private byte[] codes = new byte[256];

        static {
            for (int i = 0; i < 256; i++)
                codes[i] = -1;
            for (int i = 'A'; i <= 'Z'; i++)
                codes[i] = (byte) (i - 'A');
            for (int i = 'a'; i <= 'z'; i++)
                codes[i] = (byte) (26 + i - 'a');
            for (int i = '0'; i <= '9'; i++)
                codes[i] = (byte) (52 + i - '0');
            codes['+'] = 62;
            codes['/'] = 63;
        }

        /**
         * 将原始数据编码为base64编码
         */
        static private String encode(byte[] data) {
            char[] out = new char[((data.length + 2) / 3) * 4];
            for (int i = 0, index = 0; i < data.length; i += 3, index += 4) {
                boolean quad = false;
                boolean trip = false;
                int val = (0xFF & (int) data[i]);
                val <<= 8;
                if ((i + 1) < data.length) {
                    val |= (0xFF & (int) data[i + 1]);
                    trip = true;
                }
                val <<= 8;
                if ((i + 2) < data.length) {
                    val |= (0xFF & (int) data[i + 2]);
                    quad = true;
                }
                out[index + 3] = alphabet[(quad ? (val & 0x3F) : 64)];
                val >>= 6;
                out[index + 2] = alphabet[(trip ? (val & 0x3F) : 64)];
                val >>= 6;
                out[index + 1] = alphabet[val & 0x3F];
                val >>= 6;
                out[index] = alphabet[val & 0x3F];
            }

            return new String(out);
        }

        /**
         * 将base64编码的数据解码成原始数据
         */
        static private byte[] decode(char[] data) {
            int len = ((data.length + 3) / 4) * 3;
            if (data.length > 0 && data[data.length - 1] == '=')
                --len;
            if (data.length > 1 && data[data.length - 2] == '=')
                --len;
            byte[] out = new byte[len];
            int shift = 0;
            int accum = 0;
            int index = 0;
            for (char aData : data) {
                int value = codes[aData & 0xFF];
                if (value >= 0) {
                    accum <<= 6;
                    shift += 6;
                    accum |= value;
                    if (shift >= 8) {
                        shift -= 8;
                        out[index++] = (byte) ((accum >> shift) & 0xff);
                    }
                }
            }
            if (index != out.length)
                throw new Error("miscalculated data length!");
            return out;
        }
    }

    public static void main(String[] args) {
        try {
            String decryption = DesUtil.decryption("h24WgHz0Bxg=", "1234abcd");
            System.out.println(decryption);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


