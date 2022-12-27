package com.micronaut.services;

import com.aerospike.client.*;
import com.aerospike.client.cdt.ListOperation;
import com.aerospike.client.cdt.ListReturnType;
import com.aerospike.client.cdt.MapOperation;
import com.aerospike.client.cdt.MapPolicy;
import com.aerospike.client.policy.WritePolicy;
import com.github.javafaker.Faker;
import com.micronaut.configuration.AeroMapperConfiguration;
import jakarta.inject.Inject;
import com.aerospike.client.Record;
import java.util.HashMap;
import java.util.Map;

public class MapOperations {
    @Inject
    AeroMapperConfiguration configuration;

    public String addMap(int mapKey, Map<String, Integer> number) {
        String message = "";
        try {
            Key key = new Key("test","mapset",mapKey);

            Bin bin1 = new Bin("index", mapKey);
            Bin bin2 = new Bin("map", number);

            WritePolicy policy = new WritePolicy();
            policy.sendKey = true;

            configuration.getClient().put(policy, key, bin1, bin2);
            message = "Map added successfully, index : "+mapKey;
        }
        catch (Exception e) {
            message = e.getMessage();
        }
        return message;
    }

    public Map<String, Integer> getMapRec(int key) {
        Key newKey = new Key("test", "mapset",key);

        Record record = configuration.getClient().get(null, newKey);
        return (Map<String, Integer>) record.getMap("map");
    }

    public String insertIntoMap(String stringKey, Integer value, int key1) {
        String result = "";
        try {
            Key key = new Key("test","mapset",key1);

            configuration.getClient().operate(null,key,
                                MapOperation.put(new MapPolicy(),"map", Value.get(stringKey), Value.get(value)
            ));

            result = "Updated successfully";
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            result = "Failed to update";
        }
        return result;
    }

    public String removeNumber(String mapkey, int index) {
        String result = "";
        try {
            Key key = new Key("test","mapset", index);

            configuration.getClient().operate(null, key,
                    MapOperation.removeByKey("map", Value.get(mapkey), ListReturnType.NONE)
            );
            result = "Removed successfully";
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            result = "Failed to remove";
        }
        return result;
    }

    public String deleteMap(int key) {
        String result;
        try {
            Key key1 = new Key("test", "mapset", key);
            configuration.getClient().delete(null, key1);
            result = "Successfully deleted record with key : "+ key;
        }
        catch (Exception e) {
            result = "Failed to delete record";
        }
        return result;
    }
}
