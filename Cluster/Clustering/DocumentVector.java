package Clustering;


import java.util.Map;
import java.util.Set;

public class DocumentVector {

    private int id;
    private Map<String, Double> vector;


    public DocumentVector(int docId,  Map<String, Double> docVector){
        this.id = docId;
        this.vector = docVector;
    }

    public int getId(){
        return this.id;
    }

    public Set<String> getKeys(){
        return vector.keySet();
    }

    public Double get(String key){
        return vector.getOrDefault(key, 0.0);
    }

    public void put(String key, double value){
        vector.put(key, value);
    }
}