package com.micronaut.controllers;

import com.micronaut.services.MapOperations;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;

import java.util.Map;

@Controller("/mapops")
public class MapOpsController {
    @Inject
    MapOperations mapService;

    @Get("/add")
    public String add(@QueryValue int key) {
        return mapService.addMap(key);
    }

    @Get("/get/{key}")
    public Map<String, Integer> getMapRec(@PathVariable int key) {
        return mapService.getMapRec(key);
    }

    @Get("/insert/{key}")
    public String insert(@PathVariable int key, @QueryValue Integer value, @QueryValue String stringKey) {
        return mapService.insertIntoMap(stringKey, value, key);
    }

    @Get("/remove/{number}/{key}")
    public String removeNumber(@PathVariable String number,@PathVariable int key) {
        return mapService.removeNumber(number, key);
    }

    @Delete("/deletemap/{key}")
    public String deleteMap(@PathVariable int key) {
        return mapService.deleteMap(key);
    }
}
