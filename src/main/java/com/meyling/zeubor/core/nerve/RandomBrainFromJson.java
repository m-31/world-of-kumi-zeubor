package com.meyling.zeubor.core.nerve;

import com.meyling.zeubor.core.io.IoUtility;
import com.meyling.zeubor.core.log.Log;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Scanner;

/**
 * Encapsulates a neural net and manages interplay with sensors and muscles. 
 */
public class RandomBrainFromJson extends BrainFromJson {

    public static void main(final String[] args) {
        RandomBrainFromJson bi = new RandomBrainFromJson(1, 100, "newInga");
        bi.grow(IoUtility.getStringData("inga.json").data);
        bi.getInputNeurons().get(0).setFire(true);
        bi.getInputNeurons().get(1).setFire(true);
//        bi.printOutputNeurons();
//        bi.iterate();
//        bi.printOutputNeurons();
        System.exit(0);
    }


    private double changeProbability;
    private double range;
    private boolean change;
    private String newName;
    
    public RandomBrainFromJson(final double changeProbability, final double range, final String newName) {
        super();
        this.changeProbability = changeProbability;
        this.range = range;
        this.newName = newName;
    }

    public void grow(final String json) {
        Log.debug("grow neuronal net");
        Scanner scanner = new Scanner(json);
        long i = 0;
        while (scanner.hasNextLine()) {
            Log.debug(++i + scanner.nextLine());
        }
        
        while (!change) {
            JSONObject obj = new JSONObject(json);
            grow(obj);
            if (!change) {
                Log.error("no change!");
            }
        }    
    }
    

    protected void grow(final JSONObject obj) {
        obj.put("name", newName);
        super.grow(obj); 
    }
    
            
    protected void addGroupConnections(String area, JSONObject group) {
        if (!group.has("connections")) {
            return;
        }
        final JSONArray connections = group.getJSONArray("connections");
        for (int j = 0; j < connections.length(); j++) {
            final JSONObject connection = connections.getJSONObject(j);
            int weight = connection.getInt("weight");
            if (Math.random() < changeProbability) {
                change = true;
                Log.debug("change " + weight);
                weight = (int) (weight + (Math.random() * range - range / 2));
                Log.debug(" into " + weight);
            }    
            connection.put("weight", weight);
            final JSONArray tos = connection.getJSONArray("to");
            Integer from = null;
            if (connection.has("from")) {
                from = connection.getInt("from");
            }
            for (int k = 0; k < tos.length(); k++) {
                addConnection(area, from, tos.getString(k), weight);
            }
        }    
    }
        

}
