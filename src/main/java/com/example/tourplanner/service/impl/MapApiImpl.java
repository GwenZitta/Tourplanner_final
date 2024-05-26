package com.example.tourplanner.service.impl;

import com.example.tourplanner.api.MapApi;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class MapApiImpl implements MapApi {
    private static final String API_KEY = "5b3ce3597851110001cf62480695dcc17acc407994dbf802b2a54b67";

    @Override
    public String searchAddress(String text) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(
                "https://api.openrouteservice.org/geocode/search?boundary.country=AT&api_key=" + API_KEY + "&text=" + text,
                String.class);

        String coordinate = response.getBody().substring(response.getBody().indexOf("coordinates") + 14, response.getBody().indexOf("properties") - 4);
        return coordinate;
    }

    @Override
    public Long[] searchDirection(String start, String end, String transport) {
        String[] profiles = {"driving-car", "cycling-regular", "foot-walking"};
        String profile = profiles[2];
        if(Objects.equals(transport, "car")){
            profile = profiles[0];}
        else if(Objects.equals(transport, "bike")){
            profile = profiles[1];}
        else if(Objects.equals(transport, "foot")){
            profile = profiles[2];}
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(
                "https://api.openrouteservice.org/v2/directions/" + profile + "?api_key=" + API_KEY + "&start=" + start + "&end=" + end,
                String.class);

        Long[] routeCoordinates;
        if (response.getStatusCode() == HttpStatus.OK) {
            JSONObject jsonResponse = new JSONObject(response.getBody());
            System.out.println(jsonResponse);

            JSONArray features = jsonResponse.getJSONArray("features");
            if (features.length() < 1) {
                return null;
            }

            JSONObject properties = features.getJSONObject(0).getJSONObject("properties");
            JSONObject summary = properties.getJSONObject("summary");


            Long distance = summary.getLong("distance");
            Long duration = summary.getLong("duration");
            routeCoordinates = (new Long[]{distance, duration});

            return routeCoordinates;
        }
        return null;
    }

    @Override
    public void getMap() {
        // TODO
    }
}
