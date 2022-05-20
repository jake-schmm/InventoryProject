package com.example.inventory.controller;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.*;

import com.example.inventory.model.Inventory;
import com.example.inventory.model.Location;
import com.example.inventory.service.InventoryService;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.io.IOException;
import java.util.ArrayList;
import javax.json.JsonObject;


@RestController
@RequestMapping("/")
public class InventoryController {
        @Autowired
        InventoryService inventoryService;

        @CrossOrigin(origins="http://localhost:3001")
        @RequestMapping(value="/inventory", method=RequestMethod.POST)
        public ResponseEntity<Object> createInventoryItem(@RequestBody Inventory inv) throws IOException {
            inventoryService.createInventory(inv);
            return ResponseEntity.status(HttpStatus.CREATED).body(""); 
        }

        @CrossOrigin(origins="http://localhost:3001")
        @RequestMapping(value="/locations", method=RequestMethod.POST)
        public ResponseEntity<Object> createLocation(@RequestBody Location loc) throws IOException {
            inventoryService.createLocation(loc);
            return ResponseEntity.status(HttpStatus.CREATED).body(""); 
        }

        @CrossOrigin(origins="http://localhost:3001")
        @RequestMapping(value="/inventory", method=RequestMethod.GET)
        public ResponseEntity<Object> readInventory() throws IOException {
            List<Inventory> list = inventoryService.getAllInventory();
           
            return ResponseEntity.status(HttpStatus.OK).body(list);
        }
        
        @CrossOrigin(origins="http://localhost:3001")
        @RequestMapping(value="/locations", method=RequestMethod.GET)
        public ResponseEntity<Object> readLocations() throws IOException {
            List<Location> list = inventoryService.getAllLocations();
           
            return ResponseEntity.status(HttpStatus.OK).body(list);
        }

        @CrossOrigin(origins="http://localhost:3001")
        @RequestMapping(value="/inventory/{id}", method=RequestMethod.PUT)
        public ResponseEntity<Object> updateInventoryItem(@PathVariable(value= "id") String id, @RequestBody Inventory inventoryDetails) throws IOException {
        
        	Inventory r = inventoryService.updateInventory(id, inventoryDetails);
            
            String label = "\"error\" : \"Inventory item does not exist\"";
            
            if(r != null) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(""); 
            }
            else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(label);
            }
        }

        @CrossOrigin(origins="http://localhost:3001")
        @RequestMapping(value="/locations/{id}", method=RequestMethod.PUT)
        public ResponseEntity<Object> updateLocation(@PathVariable(value= "id") String id, @RequestBody Location locationDetails) throws IOException {
        
        	Location l = inventoryService.updateLocation(id, locationDetails);
            
            String label = "\"error\" : \"Location does not exist\"";
            
            if(l != null) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(""); 
            }
            else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(label);
            }
        }


        @CrossOrigin(origins="http://localhost:3001")
        @RequestMapping(value="/inventory/{id}", method=RequestMethod.DELETE)
        public void deleteInventory(@PathVariable(value = "id") String id) throws IOException {
            inventoryService.deleteInventory(id);
        }
        
        @CrossOrigin(origins="http://localhost:3001")
        @RequestMapping(value="/locations/{id}", method=RequestMethod.DELETE)
        public void deleteLocation(@PathVariable(value = "id") String id) throws IOException {
            inventoryService.deleteLocation(id);
        }
}