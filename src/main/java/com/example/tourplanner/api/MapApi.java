package com.example.tourplanner.api;

import java.util.List;

public interface MapApi {

    String searchAddress(String text);

    Long[] searchDirection(String start, String end, String transport);

    void getMap();

}
