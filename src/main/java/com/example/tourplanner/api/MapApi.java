package com.example.tourplanner.api;

import org.attoparser.dom.Text;

import java.util.List;

public interface MapApi {

    String searchAddress(String text);

    Long[] searchDirection(String start, String end, String transport);

    String getMap(String start, String end, String transport);

}
