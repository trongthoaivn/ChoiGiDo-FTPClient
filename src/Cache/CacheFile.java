package Cache;

import MODEL.Protocol;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;
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
            System.out.println(json.toJson(protocols));
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

        }
    }
}
