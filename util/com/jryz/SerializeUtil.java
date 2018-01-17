package com.jryz;


import java.io.*;

/**
 * 序列化
 */
public class SerializeUtil {

    /**
     * 序列化
     * @param object
     * @return
     */
    public static byte[] serialize(Object object) throws IOException {
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        baos = new ByteArrayOutputStream();
        oos = new ObjectOutputStream(baos);
        oos.writeObject(object);
        byte[] bytes = baos.toByteArray();
        return bytes;
    }

    /**
     * 反序列化
     * @param bytes
     * @return
     */
    public static Object unserialize(byte[] bytes) {
        ByteArrayInputStream bais = null;
        try {
            bais = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            return ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws IOException {

    }
}