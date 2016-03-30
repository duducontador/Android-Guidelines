package com.ciandt.sample.referenceapplication.infrastructure;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.security.KeyPairGeneratorSpec;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.Calendar;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.security.auth.x500.X500Principal;

@SuppressWarnings("WeakerAccess")
public class SecurityPreferences {
    private KeyStore keyStore;
    private final SharedPreferences preferences;
    private final Context context;

    private Cipher writer;
    private Cipher reader;
    private Cipher keyWriter;

    public SecurityPreferences(Context context) {
        this.context = context;
        this.preferences = context.getSharedPreferences(Constants.SecurityKeys.PREFERENCE_NAME, Context.MODE_PRIVATE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            try {
                keyStore = KeyStore.getInstance(Constants.SecurityKeys.ANDROID_KEY_STORE);
                keyStore.load(null);
            } catch (Exception e) {
                throw new SecurePreferencesException(e);
            }

            createNewKeys();
        } else {
            try {
                this.writer = Cipher.getInstance(Constants.SecurityKeys.TRANSFORMATION);
                this.reader = Cipher.getInstance(Constants.SecurityKeys.TRANSFORMATION);
                this.keyWriter = Cipher.getInstance(Constants.SecurityKeys.KEY_TRANSFORMATION);

                initCiphers();

            } catch (Exception e) {
                throw new SecurePreferencesException(e);
            }
        }
    }

    @SuppressWarnings("SameParameterValue")
    public void removeStoredString(String key) {
        preferences.edit().remove(toKey(key)).apply();
    }

    public void storeString(String key, String value) {
        preferences.edit().putString(toKey(key), encryptString(value)).apply();
    }

    public String getStoredString(String key) {
        if (hasStoredKey(key)) {
            String securedEncodedValue = preferences.getString(toKey(key), "");
            return decryptString(securedEncodedValue);
        }
        return "";
    }

    protected SecretKeySpec getSecretKey() {
        byte[] keyBytes;
        try {
            keyBytes = createKeyBytes();
        } catch (Exception e) {
            throw new SecurePreferencesException(e);
        }
        return new SecretKeySpec(keyBytes, Constants.SecurityKeys.TRANSFORMATION);
    }

    protected byte[] createKeyBytes()
            throws UnsupportedEncodingException, NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(Constants.SecurityKeys.SECRET_KEY_HASH_TRANSFORMATION);
        md.reset();
        return md.digest(Constants.SecurityKeys.HDR2.getBytes(Constants.SecurityKeys.CHARSET));
    }

    private byte[] convert(Cipher cipher, byte[] bs) {
        try {
            return cipher.doFinal(bs);
        } catch (Exception e) {
            throw new SecurePreferencesException(e);
        }
    }

    protected IvParameterSpec getIv() {
        byte[] iv;
        Cipher writer;
        PublicKey publicKey;
        try {
            writer = Cipher.getInstance(Constants.SecurityKeys.TRANSFORMATION);
        } catch (Exception e) {
            throw new SecurePreferencesException(e);
        }

        iv = new byte[writer.getBlockSize()];

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            iv = new byte[writer.getBlockSize()];

            try {
                publicKey = keyStore.getCertificate(Constants.SecurityKeys.ALIAS).getPublicKey();
            } catch (KeyStoreException e) {
                throw new SecurePreferencesException(e);
            }

            String s;

            if (publicKey.toString().contains("@")) {
                s = publicKey.toString().split("@")[1];
                while (s.length() <= 17) {
                    s += "w";
                }
            } else if (publicKey.toString().length() > 16) {
                s = publicKey.toString().substring(0, 16);
            } else {
                s = publicKey.toString();
                while (s.length() <= 17) {
                    s += "w";
                }
            }

            System.arraycopy(s.getBytes(), 0, iv, 0, writer.getBlockSize());
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            KeyStore.PrivateKeyEntry privateKeyEntry;
            try {
                privateKeyEntry = (KeyStore.PrivateKeyEntry) keyStore.getEntry(Constants.SecurityKeys.ALIAS, null);
            } catch (Exception e) {
                throw new SecurePreferencesException(e);
            }
            publicKey = privateKeyEntry.getCertificate().getPublicKey();

            String s = publicKey.toString().split(new String(new char[]{','}))[0].split(new String(new char[]{'m', 'o', 'd', 'u', 'l', 'u', 's', '='}))[1];
            System.arraycopy(s.getBytes(), 0, iv, 0, writer.getBlockSize());
        } else {
            iv = new byte[writer.getBlockSize()];
            System.arraycopy(Constants.SecurityKeys.GENERIC_KEY.getBytes(), 0, iv, 0, writer.getBlockSize());
        }
        return new IvParameterSpec(iv);
    }

    private String toKey(String key) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
            return encryptString(key);
        } else {
            Cipher keyWriter;
            try {
                keyWriter = Cipher.getInstance(Constants.SecurityKeys.TRANSFORMATION);
            } catch (Exception e) {
                throw new SecurePreferencesException(e);
            }
            IvParameterSpec ivSpec = getIv();
            SecretKeySpec secretKey = getSecretKey();

            try {
                keyWriter.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
            } catch (Exception e) {
                e.printStackTrace();
                throw new SecurePreferencesException(e);
            }

            byte[] secureValue = null;
            try {
                secureValue = convert(keyWriter, key.getBytes(Constants.SecurityKeys.CHARSET));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return Base64.encodeToString(secureValue, Base64.NO_WRAP);
        }
    }

    public boolean hasStoredKey(String key) {
        return preferences.contains(toKey(key));
    }

    public void createNewKeys() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
            return;
        }

        try {
            // Create new key if needed
            if (!keyStore.containsAlias(Constants.SecurityKeys.ALIAS)) {
                Calendar start = Calendar.getInstance();
                Calendar end = Calendar.getInstance();
                end.add(Calendar.YEAR, 100);
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                    @SuppressWarnings("deprecation")
                    KeyPairGeneratorSpec spec = new KeyPairGeneratorSpec.Builder(context)
                            .setAlias(Constants.SecurityKeys.ALIAS)
                            .setSubject(new X500Principal(Constants.SecurityKeys.SECURITY_KEY_NAME))
                            .setSerialNumber(BigInteger.ONE)
                            .setStartDate(start.getTime())
                            .setEndDate(end.getTime())
                            .build();
                    KeyPairGenerator generator = KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_RSA, Constants.SecurityKeys.ANDROID_KEY_STORE);
                    generator.initialize(spec);

                    generator.generateKeyPair();
                } else {
                    KeyGenParameterSpec spec = new KeyGenParameterSpec.Builder(
                            Constants.SecurityKeys.ALIAS,
                            KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                            .setKeyValidityStart(start.getTime())
                            .setKeyValidityEnd(end.getTime())
                            .setCertificateSubject(new X500Principal(Constants.SecurityKeys.SECURITY_KEY_NAME))
                            .setCertificateSerialNumber(BigInteger.ONE)
                            .setDigests(KeyProperties.DIGEST_SHA256,
                                    KeyProperties.DIGEST_SHA384,
                                    KeyProperties.DIGEST_SHA512)
                            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1)
                            .build();
                    KeyPairGenerator generator = KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_RSA, Constants.SecurityKeys.ANDROID_KEY_STORE);
                    generator.initialize(spec);
                    generator.generateKeyPair();
                }
            }
        } catch (Exception e) {
            throw new SecurePreferencesException(e);
        }
    }

    public String encryptString(String value) {

        if (value == null || value.isEmpty()) {
            throw new SecurePreferencesException(new Exception("Parameter cannot be null or empty"));
        }

        String encryptedText;
        try {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
                byte[] secureValue;
                try {
                    secureValue = convert(writer, value.getBytes(Constants.SecurityKeys.CHARSET));
                } catch (UnsupportedEncodingException e) {
                    throw new SecurePreferencesException(e);
                }
                encryptedText = Base64.encodeToString(secureValue, Base64.NO_WRAP);
            } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry) keyStore.getEntry(Constants.SecurityKeys.ALIAS, null);
                RSAPublicKey publicKey = (RSAPublicKey) privateKeyEntry.getCertificate().getPublicKey();

                Cipher inCipher = Cipher.getInstance(Constants.SecurityKeys.CIPHER_TYPE, Constants.SecurityKeys.CIPHER_PROVIDER);
                inCipher.init(Cipher.ENCRYPT_MODE, publicKey);

                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                CipherOutputStream cipherOutputStream = new CipherOutputStream(outputStream, inCipher);
                cipherOutputStream.write(value.getBytes(Constants.SecurityKeys.CHARSET));
                cipherOutputStream.close();

                byte[] val = outputStream.toByteArray();
                encryptedText = Base64.encodeToString(val, Base64.DEFAULT);
            } else {
                Cipher cipher = Cipher.getInstance(Constants.SecurityKeys.CIPHER_TYPE);
                PublicKey publicKey = keyStore.getCertificate(Constants.SecurityKeys.ALIAS).getPublicKey();
                cipher.init(Cipher.ENCRYPT_MODE, publicKey);
                byte[] cypherEncrypted= cipher.doFinal(value.getBytes(Constants.SecurityKeys.CHARSET));
                encryptedText = Base64.encodeToString(cypherEncrypted, Base64.DEFAULT);
            }
        } catch (Exception e) {
            throw new SecurePreferencesException(e);
        }

        return encryptedText;
    }

    public String decryptString(String cipherText) {
        String decryptString;

        try {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
                byte[] securedValue = Base64.decode(cipherText, Base64.NO_WRAP);
                byte[] value = convert(reader, securedValue);
                try {
                    decryptString =  new String(value, Constants.SecurityKeys.CHARSET);
                } catch (UnsupportedEncodingException e) {
                    throw new SecurePreferencesException(e);
                }
            } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry) keyStore.getEntry(Constants.SecurityKeys.ALIAS, null);

                Cipher output = Cipher.getInstance(Constants.SecurityKeys.CIPHER_TYPE, Constants.SecurityKeys.CIPHER_PROVIDER);

                RSAPrivateKey privateKey = (RSAPrivateKey) privateKeyEntry.getPrivateKey();
                output.init(Cipher.DECRYPT_MODE, privateKey);

                CipherInputStream cipherInputStream = new CipherInputStream(new ByteArrayInputStream(Base64.decode(cipherText, Base64.DEFAULT)), output);
                ArrayList<Byte> values = new ArrayList<>();
                int nextByte;
                while ((nextByte = cipherInputStream.read()) != -1) {
                    values.add((byte) nextByte);
                }

                byte[] bytes = new byte[values.size()];
                for (int i = 0; i < bytes.length; i++) {
                    bytes[i] = values.get(i);
                }

                decryptString = new String(bytes, 0, bytes.length, Constants.SecurityKeys.CHARSET);
            } else {
                Cipher cipher = Cipher.getInstance(Constants.SecurityKeys.CIPHER_TYPE);
                PrivateKey privateKey = (PrivateKey) keyStore.getKey(Constants.SecurityKeys.ALIAS, null);
                cipher.init(Cipher.DECRYPT_MODE, privateKey);
                byte[] cypherDecrypted= cipher.doFinal(Base64.decode(cipherText, Base64.DEFAULT));
                decryptString = new String(cypherDecrypted, Constants.SecurityKeys.CHARSET);
            }
        } catch (Exception e) {
            throw new SecurePreferencesException(e);
        }

        return decryptString;
    }

    protected void initCiphers() throws
            InvalidKeyException,
            InvalidAlgorithmParameterException {
        IvParameterSpec ivSpec = getIv();
        SecretKeySpec secretKey = getSecretKey();

        writer.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
        reader.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
        keyWriter.init(Cipher.ENCRYPT_MODE, secretKey);
    }

    public static class SecurePreferencesException extends RuntimeException {

        public SecurePreferencesException(Throwable e) {
            super(e);
        }

    }
}
