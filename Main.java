package com.freshworks;

import java.io.*;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.*;

import org.json.JSONException;
import org.json.JSONObject;


public class Main{
    private File dir;
    public Main(String path){
        dir = new File(path);
        Scanner scan = new Scanner(System.in);
        if(!dir.mkdir()){                       //if mkdir() returns false display error and call main function
            System.out.println("Error:datastore cannot be created");
        }
        else System.out.println("DataStore is created successfully at "+path);
    }

    public void create(String path){
        String key;
        String res="";
        FileWriter file;
        String[] name_val;
        JSONObject ob = new JSONObject();
        System.out.print("Enter the key : ");
        key = scan.next();
        System.out.println("Please enter the name-values to be loaded into the Json file in the format of name,value. Press done to end : ");
        while(true){
            res = scan.next();
            if(res.equals('done'))
            break;
            else {
                name_val = res.split(",", 2);
                try {
                    ob.put(name_val[0], name_val[1]);
                    System.out.println(name_val[0] + "-" + name_val[1]);
                } catch(ArrayIndexOutOfBoundsException e){
                  break;
                } catch (JSONException e) {
                    System.out.print("Unable to load json");
                    e.printStackTrace();
                }
            }
        }
        try{
            file = new FileWriter(path+"/"+key+".txt");
            file.write(obj.toString());
            System.out.println("Json object : \n"+ obj);
            file.flush();
            file.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void read(String dir_path, String key){
        String path = dir_path+"/"+key+".txt";
        String line = null;
        FileReader file;
        try{
            file = new FileReader(path);
            BufferedReader bufferedReader = new BufferedReader(file);
            while((line = bufferedReader.readLine())!=null)
                System.out.println(line);
            bufferedReader.close();
            file.close();
        }catch(FileNotFoundException fe){
            System.out.print("File with key" +key+" doesn't exists");
            fe.printStackTrace();
        }catch(IOException e){
            System.out.print("Error reading file"+path);
            e.printStackTrace();
        }

    }

    public void delete(String dir_path, String key){
        String path = dir_path + "/" + key + ".txt";

        try {
            Files.deleteIfExists(Paths.get(path));
            System.out.println("File with key " +key+" deleted successfully");
        } catch(NoSuchFileException e) {
            System.out.println("No such file/directory exists");
        } catch(DirectoryNotEmptyException e) {
            System.out.println("Directory is not empty.");
        } catch(IOException e) {
            System.out.println("Invalid permissions.");
        }
    }

    public static void main(String[] args) throws IOException{
        String def_path = "C:/Data Store";       //default path for file storage
        String path = "";
        int option = 0;                            //option for create or read or delete
        String key;
        JSONObject obj = new JSONObject();
        Scanner scan = new Scanner(System.in);

        System.out.println("Specify the path of data store. For default path (C:/Data Store) give def as input :");
        path = scan.next();
        if(path.equals("def"))
        path = def_path;

        Main ds = new Main(path);

        while(option!=4){
            System.out.println("Select the option for \n1.Create \n2.Read \n3.Delete \n4.Quit");
            option = scan.nextInt();
            switch (option) {
                case 1:
                    ds.create(path);
                    break;
                case 2:
                    System.out.print("Enter the key to read : ");
                    key = scan.next();
                    ds.read(path, key);
                    break;
                case 3:
                    System.out.print("Enter the key to delete file: ");
                    key = scan.next();
                    ds.delete(path, key);
                    break;
                default:
                    System.out.print("Incorrect Option");
                    break;
            }
        }

    }
}


