package com.micronaut.controllers;

import com.micronaut.services.MapOperations;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;

import java.util.Map;

@Controller("/mapops")
public class MapOpsController {
    @Inject
    MapOperations mapService;

    @Post("/add")
    public String add(@QueryValue int key, @Body Map<String, Integer> number) {
        return mapService.addMap(key, number);
    }

    @Get("/get/{key}")
    public Map<String, Integer> getMapRec(@PathVariable int key) {
        return mapService.getMapRec(key);
    }

    @Put("/update/{key}")
    public String insert(@PathVariable int key, @QueryValue Integer value, @QueryValue String stringKey) {
        return mapService.insertIntoMap(stringKey, value, key);
    }

    @Put("/remove/{key}")
    public String removeNumber(@QueryValue String number,@PathVariable int key) {
        return mapService.removeNumber(number, key);
    }

    @Delete("/deletemap/{key}")
    public String deleteMap(@PathVariable int key) {
        return mapService.deleteMap(key);
    }
}
