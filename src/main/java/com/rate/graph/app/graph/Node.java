package com.rate.graph.app.graph;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;
import org.jgrapht.nio.Attribute;

import java.util.*;
import java.util.stream.Collectors;

import static org.jgrapht.nio.DefaultAttribute.NULL;

@ToString
public class Node {
    public static final String NAME_ATTRIBUTE = "name";
    public static final String VALUE_ATTRIBUTE = "value";

    private static final Set<String> NON_CUSTOM_ATTRIBUTES = new HashSet<>(Arrays.asList(NAME_ATTRIBUTE, VALUE_ATTRIBUTE));

    @Getter
    private final Map<String, Attribute> attributes;

    @Builder
    public Node(Attribute name, Attribute value, @Singular Map<String, Attribute> attributes) {
        this.attributes = attributes
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        if (Objects.nonNull(name)) {
            this.attributes.put(NAME_ATTRIBUTE, name);
        }

        if (!this.attributes.containsKey(NAME_ATTRIBUTE)) {
            throw new RuntimeException("No name attribute invalid node");
        }

        if (Objects.nonNull(value)) {
            this.attributes.put(VALUE_ATTRIBUTE, value);
        }

        if (!this.attributes.containsKey(VALUE_ATTRIBUTE)) {
            throw new RuntimeException("No value attribute invalid node");
        }
    }

    public String getName() {
        return attributes.get(NAME_ATTRIBUTE).getValue();
    }

    public String getValue() {
        return Optional.ofNullable(attributes.get(VALUE_ATTRIBUTE))
                .map(Attribute::getValue)
                .filter(value -> !StringUtils.equals(value, NULL.getValue()))
                .orElse(null);
    }

    public Map<String, String> getCustomAttributes() {
        return attributes.entrySet()
                .stream()
                .filter(attribute -> !NON_CUSTOM_ATTRIBUTES.contains(attribute.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().getValue()));
    }
}
