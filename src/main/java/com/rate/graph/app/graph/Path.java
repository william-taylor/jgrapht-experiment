package com.rate.graph.app.graph;

import lombok.Value;

@Value
public class Path {
    public static final String SEPARATOR = "/";
    private String route;
    private String value;
}
