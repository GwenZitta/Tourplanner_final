package com.example.tourplanner.service.impl;

import com.example.tourplanner.api.MapApi;
import org.attoparser.dom.Text;
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

        Long[] routeInfos;
        if (response.getStatusCode() == HttpStatus.OK) {
            JSONObject jsonResponse = new JSONObject(response.getBody());

            JSONArray features = jsonResponse.getJSONArray("features");
            if (features.length() < 1) {
                return null;
            }

            JSONObject properties = features.getJSONObject(0).getJSONObject("properties");
            JSONObject summary = properties.getJSONObject("summary");


            Long distance = summary.getLong("distance");
            Long duration = summary.getLong("duration");
            routeInfos = (new Long[]{distance, duration});

            return routeInfos;
        }
        return null;
    }

    @Override
    public String getMap(String start, String end, String transport) {
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

        List<double[]> routeCoordinates = new ArrayList<>();
        if (response.getStatusCode() == HttpStatus.OK) {
            JSONObject jsonResponse = new JSONObject(response.getBody());

            JSONArray features = jsonResponse.getJSONArray("features");
            if (features.length() < 1) {
                return null;
            }
            JSONObject geometry = features.getJSONObject(0).getJSONObject("geometry");
            JSONArray coordinates = geometry.getJSONArray("coordinates");

            // Swapping longitude and latitude for each coordinate
            for (int i = 0; i < coordinates.length(); i++) {
                JSONArray coord = coordinates.getJSONArray(i);
                double longitude = coord.getDouble(0);
                double latitude = coord.getDouble(1);
                routeCoordinates.add(new double[]{latitude, longitude});
            }
        }
        String coordinates = "";
        for(int i = 0; i < routeCoordinates.size(); i++){
            coordinates = coordinates + "[" + routeCoordinates.get(i)[0] + ", " + routeCoordinates.get(i)[1] + "]";
            if(i < (routeCoordinates.size() - 1)){
                coordinates = coordinates + ",";
            }
        }
        System.out.println(coordinates);

        String map = "<!DOCTYPE html><html><head>    <title>Simple Map</title>    <meta charset='utf-8' />    <meta name='viewport' content='width=device-width, initial-scale=1.0'>    <link rel='stylesheet' href='https://unpkg.com/leaflet/dist/leaflet.css' />    <style>        #mapid { width: 600px; height: 400px; }        .start-icon {            background-color: green;            border-radius: 50%;            width: 12px;            height: 12px;        }        .end-icon {            background-color: red;            border-radius: 50%;            width: 12px;            height: 12px;        }    </style></head><body><div id='mapid'></div><script src='https://unpkg.com/leaflet/dist/leaflet.js'></script><script>    var map = L.map('mapid').setView(["
                + start.substring(10) + "," + start.substring(0, 8)
                + "], 14);    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {        maxZoom: 19,        attribution: '© OpenStreetMap'    }).addTo(map);        var startPoint = ["
                + start.substring(10) + "," + start.substring(0, 8)
                + "];    var endPoint = ["
                + end.substring(10) + "," + end.substring(0, 8)
                + "];    var route = ["
                + coordinates
                + "];    var polyline = L.polyline(route, {color: 'blue'}).addTo(map);    map.fitBounds(polyline.getBounds());        var startIcon = L.divIcon({className: 'start-icon'});    var endIcon = L.divIcon({className: 'end-icon'});       var startMarker = L.marker(startPoint, {icon: startIcon}).addTo(map).bindPopup('Start Point');    var endMarker = L.marker(endPoint, {icon: endIcon}).addTo(map).bindPopup('End Point');</script></body></html>";
        return map;
    }
}
