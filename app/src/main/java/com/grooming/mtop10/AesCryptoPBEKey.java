package com.grooming.mtop10;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;

public final class AesCryptoPBEKey {

    public static final int SALT_LENGTH_BYTES = 20;


    private byte[] iv = null;
    private byte[] salt = null;

    public byte[] getIV() {
        return iv;
    }

    public byte[] getSalt() {
        return salt;
    }

    AesCryptoPBEKey(final byte[] iv, final byte[] salt) {
        this.iv = iv;
        this.salt = salt;
    }

    AesCryptoPBEKey() {
        iv = null;
        initSalt();
    }

    private void initSalt() {
        salt = new byte[SALT_LENGTH_BYTES];
        SecureRandom sr = new SecureRandom();
        sr.nextBytes(salt);
    }

    public final byte[] encrypt(final byte[] plain, final char[] password) {
        byte[] encrypted = null;

        try {
            //выбираем алгоритм, режим и дополнение
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");

            //используем соль
            SecretKey secretKey = generateKey(password, salt);

            //указать режим (шифрование/расшифрование) и ключ
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);



            iv = cipher.getIV();

            encrypted = cipher.doFinal(plain);



        } catch (NoSuchAlgorithmException e) {
        } catch (NoSuchPaddingException e) {
        } catch (InvalidKeyException e) {
        } catch (IllegalBlockSizeException e) {
        } catch (BadPaddingException e) {
        } finally {
        }

        return encrypted;
    }

    public final byte[] decrypt(final byte[] encrypted, final char[] password) {
        byte[] plain = null;

        try {
            // выбираем алгоритм, режим и дополнение:
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");

            // используем соль (пароль пользователя может быть тривиальным)
            SecretKey secretKey = generateKey(password, salt);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
            plain = cipher.doFinal(encrypted);

        } catch (NoSuchAlgorithmException e) {
        } catch (NoSuchPaddingException e) {
        } catch (InvalidKeyException e) {
        } catch (InvalidAlgorithmParameterException e) {
        } catch (IllegalBlockSizeException e) {
        } catch (BadPaddingException e) {
        } finally {
        }

        return plain;
    }

    private static SecretKey generateKey(final char[] password,
                                               final byte[] salt) {
        SecretKey secretKey = null;
        PBEKeySpec keySpec = null;

        try {

            // создаем фабрику указывая PasswrodBaseEncryption а также алгоритм хеширвоания и режим блоков
            SecretKeyFactory secretKeyFactory =
                    SecretKeyFactory.getInstance("PBEWITHSHA256AND128BITAES-CBC-BC");
//                    SecretKeyFactory.getInstance("PBEWITHSHA256AND128BITAES-CBC-BC");


            // настраиваем генерацию ключа:
            // доабвляем соль, большое количество итераций хеширования и размер ключа:
            keySpec = new PBEKeySpec(password,
                    salt,
                    1024,
                    128);
            // не забудем очистить массив с паролем!
            Arrays.fill(password, '?');
            // генерирему ключ...
            secretKey = secretKeyFactory.generateSecret(keySpec);
        } catch (NoSuchAlgorithmException e) {
        } catch (InvalidKeySpecException e) {
        } finally {
            keySpec.clearPassword();
        }

        return secretKey;
    }
}