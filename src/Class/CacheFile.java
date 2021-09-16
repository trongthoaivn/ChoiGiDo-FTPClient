package Class;

import MODEL.Protocol;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class CacheFile {
    public Boolean CreateCache(){
        try{
            File file = new File("cache.json");
            if(file.createNewFile())
                return true;
            else return false;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }
    public Boolean WriteCache(ArrayList<Protocol> protocols){
        try {
            Gson json = new Gson();
            FileWriter writer = new FileWriter("cache.json");

            writer.write(json.toJson(protocols));
            writer.close();
            return  true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
    public ArrayList ReadCache(){
        ArrayList arr = new ArrayList();
        try {
            Gson gson = new Gson();
            Reader reader = Files.newBufferedReader(Paths.get("cache.json"));
            ArrayList<Protocol> protocols = gson.fromJson(reader, new TypeToken<ArrayList<Protocol>>() {}.getType());
            arr = protocols;
            return arr;
        } catch (IOException e) {
            e.printStackTrace();
            return arr;
        }
    }
}
