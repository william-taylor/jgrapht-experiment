package com.rate.graph.app.graph;

import lombok.*;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.nio.Attribute;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@ToString
public class Edge extends DefaultEdge {
    public static final String HIGHLIGHT_ATTRIBUTE = "highlight";

    @Getter
    private final Map<String, Attribute> attributes;

    @Builder
    public Edge(Attribute highlight, @Singular Map<String, Attribute> attributes) {
        this.attributes = attributes
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        if (Objects.nonNull(highlight)) {
            this.attributes.put(HIGHLIGHT_ATTRIBUTE, highlight);
        }

        if (!this.attributes.containsKey(HIGHLIGHT_ATTRIBUTE)) {
            throw new RuntimeException("No highlight attribute invalid edge");
        }
    }
}
