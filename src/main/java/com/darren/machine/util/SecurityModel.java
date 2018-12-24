package com.darren.machine.util;


import java.security.InvalidKeyException;


public class SecurityModel {
    public static int DEFAULT_KEYSIZE = 32;
    public static int DEFAULT_BLOCKSIZE = 16;

    //takes in an array of ints and returns a String of chars (ASCII)

    public static String intArrayToString(int[] t) {
        StringBuffer sb = new StringBuffer();
        try {
            for (int i = 0; i < t.length; i++) {
                sb.append((char)t[i]);
            }
            return sb.toString();
        } finally {

        }


    }
    //converts a string of chars to an arrayof int values

    public static int[] stringToIntArray(String s) {
        int[] temp = new int[s.length()];
        for (int i = 0; i < s.length(); i++) {
            temp[i] = s.charAt(i);
        }
        return temp;
    }
    //takes in a string of hex (2 digit) values and returns an int array

    public static int[] hexStringToIntArray(String s) {
        int[] temp = new int[s.length() / 2];
        for (int i = 0; i < s.length(); i = i + 2) {
            temp[i / 2] = 
                    Integer.valueOf(s.substring(i, i + 2), 16).intValue();
        }
        return temp;
    }

    //takes in 2 strings of any size representing the text and the key
    //returns a string representing the ciphertext in hex values(2 digits)

    public static String encrypt(String t, String k) {
        return encrypt(t, k, DEFAULT_KEYSIZE, DEFAULT_BLOCKSIZE);
    }

    public static String encrypt(String t, String k, int keySize, 
                                 int blockSize) {
        String text = cleanText(t, blockSize);
        byte[] keyArray = setKeyLength(k, keySize);
        byte[] textBytes = text.getBytes();
        int cipherLen = textBytes.length;
        StringBuffer cipher = new StringBuffer();
        try {
            Object key = RijndaelAlgorithm.makeKey(keyArray, keySize);
            for (int offset = 0; offset < cipherLen; offset += blockSize) {
                byte[] cipherBytes = 
                    RijndaelAlgorithm.blockEncrypt(textBytes, offset, key, 
                                                   blockSize);
                cipher.append(RijndaelAlgorithm.toString(cipherBytes));
            }

            return cipher.toString();
        } catch (InvalidKeyException e) {
            
            return t;
        } finally {

        }
    }
    //takes in 2 strings, one representing the ciphertext as a string of hex values
    //the other, the key as a normal string.
    //returns a string containing the plain text. works with any size of key and text

    public static String decrypt(String t, String k) {
        return decrypt(t, k, DEFAULT_KEYSIZE, DEFAULT_BLOCKSIZE);
    }

    public static String decrypt(String t, String k, int keySize, 
                                 int blockSize) {
        String text = t;
        StringBuffer plain = new StringBuffer();
        byte[] keyArray = setKeyLength(k, keySize);
        byte[] textBytes = RijndaelAlgorithm.getBytes(t);
        int cipherLen = textBytes.length;
        try {
            Object key = RijndaelAlgorithm.makeKey(keyArray, keySize);
            for (int offset = 0; offset < cipherLen; offset += blockSize) {
                byte[] plainBytes = 
                    RijndaelAlgorithm.blockDecrypt(textBytes, offset, key, 
                                                   blockSize);
                plain.append(new String(plainBytes));
            }

            return plain.toString();
        } catch (InvalidKeyException e) {
            
            return t;
        } finally {

        }
    }

    //sets the keyArray to the correct size and fills it with the correct ints

    public static byte[] setKeyLength(String k, int keySize) {
        byte[] keyArray = new byte[keySize];
        StringBuffer key = new StringBuffer();
        try {
            if (k.length() > keySize) {
                key.append(k.substring(0, keySize));
            } else {
                key.append(k);
            }
            while (key.length() < keySize) {
                key.append(" ");
            }

            return key.toString().getBytes();
        } finally {

        }
    }

    //sets the text to the correct length by adding on spaces if required

    public static String cleanText(String t, int blockSize) {
        StringBuffer text = new StringBuffer();
        try {
            text.append(t);
            while (text.length() < blockSize) {
                text.append(" ");
            }
            while (text.length() % blockSize != 0) {
                text.append(" ");
            }
            return text.toString();
        } finally {

        }
    }

}


