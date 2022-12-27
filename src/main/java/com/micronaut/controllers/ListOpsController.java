package com.micronaut.controllers;

import com.micronaut.services.ListOperations;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Map;

@Controller("/listops")
public class ListOpsController {
    @Inject
    ListOperations listService;

    @Post("/add")
    public String add(@QueryValue int key) {
        return listService.addList(key);
    }

    @Get("/get/{key}")
    public List<String> get(@PathVariable int key) {
        return listService.getList(key);
    }

    @Put("/updatelist/{key}")
    public String updateList(@QueryValue String fruit,@PathVariable int key) {
        return listService.updateList(fruit, key);
    }

    @Put("/remove/{key}")
    public String removeFruit(@QueryValue String fruit,@PathVariable int key) {
        return listService.removeFruit(fruit, key);
    }

    @Get("/sortlist/{key}")
    public Map<String,List<String>> sortList(@PathVariable int key) {
        return listService.sortList(key);
    }

    @Delete("/deletelist/{key}")
    public String deleteList(@PathVariable int key) {
        return listService.deleteList(key);
    }
}
