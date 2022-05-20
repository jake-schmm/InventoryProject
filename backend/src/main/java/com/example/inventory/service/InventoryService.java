package com.example.inventory.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.example.inventory.model.Inventory;
import com.example.inventory.model.Location;
import com.fasterxml.jackson.core.type.TypeReference; 

import org.json.JSONArray;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonArray;
import com.google.gson.Gson;
import java.io.*;
import org.json.JSONObject;
import com.google.gson.Gson; 

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;  
import java.nio.file.Paths; 
import com.google.gson.reflect.TypeToken;
import java.util.Arrays;
import java.util.stream.*;  
import org.json.*;
import java.util.*;
import javax.json.*;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
@Service
public class InventoryService {

         
        public Location createLocation(Location loc) throws IOException {
            String jsonStr = new String(Files.readAllBytes(Paths.get("src/main/java/com/example/inventory/service/data.json")));  
            Gson gson = new Gson();
            
            JsonObject inputObj = gson.fromJson(jsonStr, JsonObject.class);
            JsonObject newObject = new JsonObject();
            
            newObject.addProperty("locationId", loc.getLocationId().toString());
            newObject.addProperty("locationName", loc.getLocationName());
            inputObj.get("locations").getAsJsonArray().add(newObject);
            
            BufferedWriter bw = new BufferedWriter(new FileWriter("src/main/java/com/example/inventory/service/data.json"));
            bw.write(gson.toJson(inputObj)); 
            bw.close();
            
            return loc;
        }
        
        public List<Location> getAllLocations() throws IOException {
            String jsonStr = new String(Files.readAllBytes(Paths.get("src/main/java/com/example/inventory/service/data.json")));  
          
            JSONObject inputObj = new org.json.JSONObject(jsonStr);
            JSONArray arr = (JSONArray)inputObj.get("locations");
            // Convert JSONArray back to a string so you can use objectMapper to convert from the isolated string (containing just recipes array) to a list of Recipes
            String s = arr.toString();
            ObjectMapper objectMapper = new ObjectMapper();
            List<Location> finalList = objectMapper.readValue(s, new TypeReference<List<Location>>() {});

            return finalList;
        }
        
        public Location updateLocation(String locationId, Location loc) throws IOException {
        	List<Location> list = getAllLocations(); 
            int indexInList = -1;
            for(int i = 0; i < list.size(); i++) {
                if(list.get(i).getLocationId().equals(locationId)) {
                    indexInList = i;
                }
            }
            // check if exists 
            if(indexInList == -1) {
                return null;
            }
            else {
         
                String jsonStr = new String(Files.readAllBytes(Paths.get("src/main/java/com/example/inventory/service/data.json")));  
                Gson gson = new Gson();
            
                JsonObject inputObj = gson.fromJson(jsonStr, JsonObject.class);
                JsonObject newObject = new JsonObject();
                newObject.addProperty("locationId", locationId.toString());
                
                
                newObject.addProperty("locationName", loc.getLocationName());
                
          

                // update JSON and write to file 
                inputObj.get("locations").getAsJsonArray().set(indexInList, newObject);
                
                BufferedWriter bw = new BufferedWriter(new FileWriter("src/main/java/com/example/inventory/service/data.json"));
                bw.write(gson.toJson(inputObj)); 
                bw.close();
                
                return loc;
            
            }
        }
        
        public void deleteLocation(String locationId) throws IOException {
            
            String jsonStr = new String(Files.readAllBytes(Paths.get("src/main/java/com/example/inventory/service/data.json")));  
            Gson gson = new Gson();
        
            JsonObject inputObj = gson.fromJson(jsonStr, JsonObject.class);

            List<Location> list = getAllLocations(); 
           int indexInList = -1;
           for(int i = 0; i < list.size(); i++) {
               if(list.get(i).getLocationId().equals(locationId)) {
                   indexInList = i;
               }
           }
           // check if exists 
           if(indexInList != -1) {
               inputObj.get("locations").getAsJsonArray().remove(indexInList);
           }
           BufferedWriter bw = new BufferedWriter(new FileWriter("src/main/java/com/example/inventory/service/data.json"));
           bw.write(gson.toJson(inputObj)); 
           bw.close();
               
       
            
       }

        public Inventory createInventory(Inventory inv) throws IOException {
       
            String jsonStr = new String(Files.readAllBytes(Paths.get("src/main/java/com/example/inventory/service/data.json")));  
            Gson gson = new Gson();
        
            JsonObject inputObj = gson.fromJson(jsonStr, JsonObject.class);
            JsonObject newObject = new JsonObject();
            
     
            newObject.addProperty("id", inv.getId().toString());
         
           
            newObject.addProperty("name", inv.getName());
            newObject.addProperty("description", inv.getDescription());
            newObject.addProperty("price", inv.getPrice());
            newObject.addProperty("quantity", inv.getQuantity());
            newObject.addProperty("reorderDays", inv.getReorderDays());
            newObject.addProperty("location", inv.getLocation());
   
            // add new inventory item to array
            inputObj.get("inventory").getAsJsonArray().add(newObject);
            
            BufferedWriter bw = new BufferedWriter(new FileWriter("src/main/java/com/example/inventory/service/data.json"));
            bw.write(gson.toJson(inputObj)); 
            bw.close();
            
            return new Inventory();
        }

        public List<Inventory> getAllInventory() throws IOException {
       
          
            String jsonStr = new String(Files.readAllBytes(Paths.get("src/main/java/com/example/inventory/service/data.json")));  
          
            JSONObject inputObj = new org.json.JSONObject(jsonStr);
            JSONArray arr = (JSONArray)inputObj.get("inventory");
            // Convert JSONArray back to a string so you can use objectMapper to convert from the isolated string (containing just recipes array) to a list of Recipes
            String s = arr.toString();
            ObjectMapper objectMapper = new ObjectMapper();
            List<Inventory> finalList = objectMapper.readValue(s, new TypeReference<List<Inventory>>() {});

            return finalList; 
        }
       

        public void deleteInventory(String id) throws IOException {
        
             String jsonStr = new String(Files.readAllBytes(Paths.get("src/main/java/com/example/inventory/service/data.json")));  
             Gson gson = new Gson();
         
             JsonObject inputObj = gson.fromJson(jsonStr, JsonObject.class);

             List<Inventory> list = getAllInventory(); 
            int indexInList = -1;
            for(int i = 0; i < list.size(); i++) {
                if(list.get(i).getId().equals(id)) {
                    indexInList = i;
                }
            }
            // check if exists 
            if(indexInList != -1) {
                inputObj.get("inventory").getAsJsonArray().remove(indexInList);
            }
            BufferedWriter bw = new BufferedWriter(new FileWriter("src/main/java/com/example/inventory/service/data.json"));
            bw.write(gson.toJson(inputObj)); 
            bw.close();
                
        
             
        }

       
        
        /* Returns null if not found */
        public Inventory findInventoryById(String id) throws IOException {
            List<Inventory> invList = getAllInventory();
            for(int i = 0; i < invList.size(); i++) {
                if(invList.get(i).getId().equals(id)) {
                    return invList.get(i);
                }
            }
           return null;
        }

        public Inventory updateInventory(String id, Inventory inv) throws IOException {
          
            
            List<Inventory> list = getAllInventory(); 
            int indexInList = -1;
            for(int i = 0; i < list.size(); i++) {
                if(list.get(i).getId().equals(id)) {
                    indexInList = i;
                }
            }
            // check if exists 
            if(indexInList == -1) {
                return null;
            }
            else {
         
                String jsonStr = new String(Files.readAllBytes(Paths.get("src/main/java/com/example/inventory/service/data.json")));  
                Gson gson = new Gson();
            
                JsonObject inputObj = gson.fromJson(jsonStr, JsonObject.class);
                JsonObject newObject = new JsonObject();
                newObject.addProperty("id", id.toString());
                
                
                newObject.addProperty("name", inv.getName());
                newObject.addProperty("description", inv.getDescription());
                newObject.addProperty("price", inv.getPrice());
                newObject.addProperty("quantity", inv.getQuantity());
                newObject.addProperty("reorderDays", inv.getReorderDays());
                newObject.addProperty("location", inv.getLocation());
       
          

                // update JSON and write to file 
                inputObj.get("inventory").getAsJsonArray().set(indexInList, newObject);
                
                BufferedWriter bw = new BufferedWriter(new FileWriter("src/main/java/com/example/inventory/service/data.json"));
                bw.write(gson.toJson(inputObj)); 
                bw.close();
                
                return inv;
            
            
    }
}
}

 