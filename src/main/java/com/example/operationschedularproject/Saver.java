package com.example.operationschedularproject;


import com.example.operationschedularproject.LinkedList.LinkedList;

import java.io.*;


public class Saver {
    public static void save(String filepath, LinkedList object)  {
        try {
            FileOutputStream fileOutputStream  = new FileOutputStream(filepath);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(object);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

//    public static LinkedList load(String filepath)  {
//        LinkedList<HealthProfessional> output;
//        try {
//            FileInputStream fileInputStream = new FileInputStream(filepath);
//             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream); {
//            output = (LinkedList<HealthProfessional>) objectInputStream.readObject();
//                System.out.println("Object successfully loaded!");
//        }
//    } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//      return output;
//
//    }
}
