package com.example.operationschedularproject;

import java.io.*;

public class Saver {
    public void save(String filepath, Object object) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(filepath);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(object);
        objectOutputStream.close();
        fileOutputStream.close();
    }

    /** Usage
     * User user = new User();
     *         user = (User)saver.load("D:\\OperationSchedularProject\\Info.ser", user.getClass());
     * @param filepath
     * @param classType
     * @return
     * @param <T>
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public <T> T load(String filepath, Class<T> classType) throws IOException, ClassNotFoundException {
        try (FileInputStream fileInputStream = new FileInputStream(filepath);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            Object object = objectInputStream.readObject();

            if (classType.isInstance(object)) {
                return classType.cast(object);
            } else {
                throw new IllegalArgumentException("The deserialized object is not of the specified class type.");
            }
        }
    }
}
