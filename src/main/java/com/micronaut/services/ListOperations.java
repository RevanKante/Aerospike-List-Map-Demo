package com.micronaut.services;

import com.aerospike.client.*;
import com.aerospike.client.cdt.ListOperation;
import com.aerospike.client.cdt.ListReturnType;
import com.aerospike.client.cdt.ListSortFlags;
import com.aerospike.client.policy.Policy;
import com.aerospike.client.policy.WritePolicy;
import com.aerospike.client.query.Filter;
import com.aerospike.client.query.RecordSet;
import com.aerospike.client.query.Statement;
import com.aerospike.client.Record;
import com.github.javafaker.Faker;
import com.micronaut.configuration.AeroMapperConfiguration;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.*;

@Singleton
public class ListOperations {

    @Inject
    AeroMapperConfiguration configuration;

    public String addList(int listKey) {
        String message = "";
        try {
            AerospikeClient client = configuration.getClient();

            Faker faker = new Faker();
            List<String> listStr = new ArrayList<String>();

            listStr.add(faker.food().fruit());
            listStr.add(faker.food().fruit());
            listStr.add(faker.food().fruit());
            listStr.add(faker.food().fruit());
            listStr.add(faker.food().fruit());
            listStr.add(faker.food().fruit());

            Key key = new Key("test","listset",listKey);

            Bin bin1 = new Bin("index", listKey);
            Bin bin2 = new Bin("list", listStr);

            WritePolicy policy = new WritePolicy();
            policy.sendKey = true;

            client.put(policy, key, bin1, bin2);
            message = "List added successfully, index : "+listKey;
        }
        catch (Exception e) {
            message = e.getMessage();
        }
        return message;
    }

    public List<String> getList(int key) {
        Key newKey = new Key("test", "listset",key);
        Record record = configuration.getClient().get(null, newKey);
        return (List<String>)record.getValue("list");
    }

    public Record getRecordByKey(int key) {
        try {
//            AerospikeClient client = configuration.getClient();
            List<String> records = new ArrayList<>();

            Policy policy = new Policy();
            policy.sendKey=true;

            Statement statement = new Statement();
            statement.setNamespace("test");
            statement.setSetName("listset");

            statement.setFilter(Filter.equal("index", key));

            RecordSet record = configuration.getClient().query(null,statement);

            if (record.next()) {
                System.out.println(record.getKey());
                System.out.println(record.getRecord());
                return record.getRecord();
            }
            else {
                return  record.getRecord();
            }
        }
        catch (Exception e) {
            return null;
        }
    }

    public String insertIntoList(String fruit, int key1) {
        String result = "";
        try {
            AerospikeClient client = configuration.getClient();
            Key key = new Key("test","listset",key1);

            Record origRecord = client.get(null, key);

            client.operate(client.writePolicyDefault, key,
                    ListOperation.append("list", Value.get(fruit))
            );
            result = "Inserted successfully";
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            result = "Insertion failed";
        }
        return result;
    }

    public String removeFruit(String fruit, int key1) {
        String result = "";
        try {
            AerospikeClient client = configuration.getClient();
            Key key = new Key("test","listset",key1);

            List<Value> fruits = Arrays.asList(Value.get(fruit));
            client.operate(client.writePolicyDefault, key,
                    ListOperation.removeByValueList("list", fruits, ListReturnType.NONE)
            );
            result = "Removed successfully";
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            result = "Failed to remove";
        }
        return result;
    }

    public Map<String,List<String>> sortList(int key1) {
        Map<String,List<String>> result = new HashMap<>();
        try {
            Key key = new Key("test","listset",key1);

            configuration.getClient().operate(null, key,
                    ListOperation.sort("list", ListSortFlags.DEFAULT)
            );
            Record record = configuration.getClient().get(null, key);
            result.put("Sorted List : ", (List<String>) record.getValue("list"));
        }

        catch (Exception e) {
            result.put("Failed to get list", null);
        }
        return result;
    }

    public String deleteList(int key1) {
        String result = "";
        try {
            Key key = new Key("test", "listset", key1);
            configuration.getClient().delete(null, key);
            result = "Successfully deleted list";
        }
        catch (Exception e) {
            result = "Failed to delete list";
        }
        return  result;
    }
}
