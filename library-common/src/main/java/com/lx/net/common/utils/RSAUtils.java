package com.lx.net.common.utils;

import android.util.Log;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;

import javax.crypto.Cipher;

/**
 * @author :pl
 * @version ：
 * @package_name ：com.lx.net.mine.fragment
 * @org ： 深圳赛为安全技术服务有限公司
 * @date : 2022/11/30 19:19
 * @description ：
 */
public class RSAUtils {
    private static final String KEY_ALGORITHM = "RSA";
    private static final int KEY_SIZE = 2048;//设置长度
    public static final String SIGNATURE_ALGORITHM = "SHA256withRSA";
    public static final String RSA_TYPE = "RSA/ECB/PKCS1Padding";


    private final static String PUBLIC_KEY_NAME = "";
    private final static String PRIVATE_KEY_NAME = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDA+JBLaviBNeXEihGBz9FmZXyF86REYDHa9dttSGOWMVg3zINAVPk2R/Fr5ecobeGyRzb6NiK32UDIq2qtu+8NooNURLZ/GlHTcM0NWeO6hLPTfSAwBviMXvzx8W5ps73g/mKs/tQLyEbYTFwMT0buBNr+zl4XSyqXOv4kT1ZiWf+rAjJdqUbW5LPh+syiQ7vGcn0miw8dJL/MwfOC28HToknyvTf598CNf2Z3wO9cpGiEbslGVDNTBCz9TI85+y+68i8D+l1DS7GeBa9wL3pcb7GsYtht87lOD2soWrHogWwH4uIlDFuwvIL48A3co/mQ/K7/Zksg0KQmk2Q6+8x/AgMBAAECggEASNfLiFyZ8BSLZnSEBU+QiSm4JsyA7rqtNy1lpkwUI6b+2RlfVlJ9PItNy+AeMTnBX83YJ/11f5cICHgErg5qP+hf0NibV0F1L+69yNNszoS3aRcrplWLT1mv/BiaCFasT5lXYFxVaRkx/QZeHNt0N/cnP9Zg2EQskKfZNZWpUzhkHwJn4JFjXMplTl0M03Y8CtI79kbPwOZMWsLdb2KfU4RZtFFEoZZeTI37BUY4Ga1FlQaTFmhHl+xi0d++GGh3lX35fsGjsZ7zRssy4ywtKJBYM5PWOUwJkJgKgovq7nVzTKdzjU1s3R3E+29cW3/FaqTSx9SsCllZemqzd5iOMQKBgQDi4qebYml1c1UFfQJ+J97Unwq4A/2eDLu++F/xaUjbTxHrWMiMCzt1utg8nAdygxxxYKm5T3mjAiPwoaTTe6MjlWlAInwgn1jvIAfQ143RvvQ52muDC4qByB7ZoTLgpbs4/bxMzdbYGGn44yYtpMtVDaudyLL6qylx5u5ocQ/uGQKBgQDZu8vW/qFem08BSN8Q1F6jHXKbUDM03LtHH4nQp4cOEQq2SKF+oV+lwx4g/2w8WfzdTDAEufua4RDNP53XSdcHO1Jza9WnA/irZeLdTIK8PNuFWzDm1cIBI0PT4kFUqqd33QPiX7xuE2qDx/J2hlhZPLBY+HPMpWe+cjlK8rkyVwKBgQDKn8MyCSzHwM2AjklIo5Dk8HihKKc6nIKbpbQBpJZ1jeMh/PN15p4eoVR2pJ4eDau8MajCH5ExHDs+rw3F9VgX8lrB9UpH1CeXv1Jbl/ZHCLOSa1ey+/6hsziiAc2vRTO0TR76tKYX7Y3OwgYZo2AYQJIw4sm9BlmIKMZoLfkdGQKBgFti8ix71VkzEJb2cgHLUSlfa8H6iHOQjd5NQ3fbSNlDl95oX7gZnV6ipZut+UzfbD2qA6GIOi2Id5feMq6w5Fq5sGKXiSY/iXjPG8hMm7bMSEsRulW4tGPr3EUresZBlma90iqKijkdVyCWpJLQ0nYp/c5qAZiMeB28LYXimI6jAoGBAKq1ZnNhxdtoftmId66ZRNBbZLXlCK2mOj3E2/HNs8E6sx1lZa8lRXeIv7hVDzW1uauOQiOc+casaTChnCXthgCuGsxn4FstTG+E+inaxEBb+XsfOwZq+XGyw4d5DgabsOZEP5IIaxrK/O7GV1mBWgKKb37ColCbXusJ3uJ7OVnO";
    private final static String SERVICE_PUBLIC_KEY_NAME = "";


    /**
     * 生成公、私钥
     * 根据需要返回String或byte[]类型
     * @return
     */
    public static ArrayList<String> createRSAKeys(){
        ArrayList<String> array = new ArrayList<>();
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
            keyPairGenerator.initialize(KEY_SIZE, new SecureRandom());
            KeyPair keyPair = keyPairGenerator.generateKeyPair();

            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();

            //获取公、私钥值
            String publicKeyValue = Base64.encode(publicKey.getEncoded());
            String privateKeyValue = Base64.encode(privateKey.getEncoded());

            //存入
            array.add(publicKeyValue);
            array.add(privateKeyValue);

            Log.e(" >>> ",publicKeyValue);
            Log.e(" >>> ",privateKeyValue);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return array;
    }

    //获取服务器RSA公钥
    public static PublicKey getServicePublicKey() {
        try {
            return  getPublicKey(SERVICE_PUBLIC_KEY_NAME);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    //获取RSA公钥 根据钥匙字段
    public static PublicKey getPublicKey(String key) {
        try {
            byte[] byteKey = Base64.decode(key);
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(byteKey);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            return keyFactory.generatePublic(x509EncodedKeySpec);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //获取RSA私钥   根据钥匙字段
    private static PrivateKey getPrivateKey(String key) {
        try {
            byte[] byteKey = Base64.decode(key);
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(byteKey);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

            return keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }




    //本地RSA私钥 签名
    public static String sign(String requestData){
        String signature = null;
        byte[] signed = null;
        try {
//            Log.e("=0== 签名前 >>>",requestData);
            PrivateKey privateKey = getPrivateKey(PRIVATE_KEY_NAME);
            Signature Sign = Signature.getInstance(SIGNATURE_ALGORITHM);
            Sign.initSign(privateKey);
            Sign.update(requestData.getBytes());
            signed = Sign.sign();
            signature = Base64.encode(signed);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return signature;
    }


    //公钥验证签名   base64签名 signature   签名内容requestData
    public static boolean verifySign(String requestData, String signature){
        boolean verifySignSuccess = false;
        try {
            PublicKey publicKey = getServicePublicKey();
            Signature verifySign = Signature.getInstance(SIGNATURE_ALGORITHM);
            verifySign.initVerify(publicKey);
            verifySign.update(requestData.getBytes());

            verifySignSuccess = verifySign.verify(Base64.decode(signature));
            System.out.println(" >>> "+verifySignSuccess);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return verifySignSuccess;
    }


    public static String encrypt(String clearText) {
        String encryptedBase64 = "";
        try {
            Key key = getServicePublicKey();
            final Cipher cipher = Cipher.getInstance(RSA_TYPE);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            //
            byte[] encryptedBytes = cipher.doFinal(clearText.getBytes("UTF-8"));
            encryptedBase64 = Base64.encode(encryptedBytes);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return encryptedBase64;
    }

    public static String encryptSafe(String clearText,String servicePublicKey) {
        String encryptedBase64 = "";
        try {
            Key key = getPublicKey(servicePublicKey);
            final Cipher cipher = Cipher.getInstance(RSA_TYPE);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            //
            byte[] encryptedBytes = cipher.doFinal(clearText.getBytes("UTF-8"));
            encryptedBase64 = Base64.encode(encryptedBytes);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return encryptedBase64;
    }

    public static String decrypt(String encryptedBase64) {
        String decryptedString = "";
        try {
            Key key =  getPrivateKey(PRIVATE_KEY_NAME);
            final Cipher cipher = Cipher.getInstance(RSA_TYPE);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] encryptedBytes = Base64.decode(encryptedBase64);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            decryptedString = new String(decryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return decryptedString;
    }
}
