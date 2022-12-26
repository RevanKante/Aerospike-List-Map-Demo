package com.micronaut.controllers;

import com.micronaut.services.ListOperations;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.QueryValue;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Map;

@Controller("/listops")
public class ListOpsController {
    @Inject
    ListOperations listService;

    @Get("/add")
    public String add(@QueryValue int key) {
        return listService.addList(key);
    }

    @Get("/get/{key}")
    public List<String> get(@PathVariable int key) {
        return listService.getList(key);
    }

    @Get("/insert/{fruit}/{key}")
    public String insert(@PathVariable String fruit,@PathVariable int key) {
        return listService.insertIntoList(fruit, key);
    }

    @Get("/remove/{fruit}/{key}")
    public String removeFruit(@PathVariable String fruit,@PathVariable int key) {
        return listService.removeFruit(fruit, key);
    }

    @Get("/sortlist/{key}")
    public Map<String,List<String>> sortList(@PathVariable int key) {
        return listService.sortList(key);
    }

    @Get("/deletelist/{key}")
    public String deleteList(@PathVariable int key) {
        return listService.deleteList(key);
    }
}
