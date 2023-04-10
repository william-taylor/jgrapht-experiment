package com.rate.graph.app;

import com.rate.graph.app.graph.Edge;
import com.rate.graph.app.graph.Exporter;
import com.rate.graph.app.graph.Importer;
import com.rate.graph.app.graph.Node;
import org.jgrapht.Graph;
import org.jgrapht.graph.DirectedMultigraph;
import org.jgrapht.nio.DefaultAttribute;

public class App {

    private static final String VENUE_CUSTOM_ATTRIBUTE = "venue";

    public static Node createRootNode() {
        return Node.builder()
                .name(DefaultAttribute.createAttribute("Custom Rate"))
                .value(DefaultAttribute.createAttribute(1.0))
                .build();
    }

    public static Node createSecurityRateNode() {
        return Node.builder()
                .name(DefaultAttribute.createAttribute("Security Rate"))
                .value(DefaultAttribute.createAttribute(1.0))
                .build();
    }

    public static Node createBtecRateNode() {
        return Node.builder()
                .name(DefaultAttribute.createAttribute("Venue Rate"))
                .value(DefaultAttribute.createAttribute(1.0))
                .attribute(VENUE_CUSTOM_ATTRIBUTE, DefaultAttribute.createAttribute("Venue"))
                .build();
    }

    public static Node createInternalRateNode() {
        return Node.builder()
                .name(DefaultAttribute.createAttribute("Internal Rate"))
                .value(DefaultAttribute.NULL)
                .build();
    }

    public static Edge createHighlightedEdge(boolean highlight) {
        return Edge.builder()
                .highlight(DefaultAttribute.createAttribute(highlight))
                .build();
    }

    public static void main(String[] args) {
        final var graph = createGraph();
        final var json = Exporter.toJson(graph);
        System.out.println("JSON: " + json);

        final var graphFromJson = Importer.fromJson(json);
        System.out.println("Graph: " + graphFromJson);

        final var paths = Exporter.toPaths(graphFromJson);
        System.out.println("Paths: " + paths);
    }

    public static Graph<Node, Edge> createGraph() {
        final var graph = new DirectedMultigraph<Node, Edge>(Edge.class);
        final var vertex1 = createRootNode();
        final var vertex2 = createSecurityRateNode();
        final var vertex3 = createInternalRateNode();
        final var vertex4 = createBtecRateNode();

        graph.addVertex(vertex1);
        graph.addVertex(vertex2);
        graph.addVertex(vertex3);
        graph.addVertex(vertex4);

        graph.addEdge(vertex1, vertex2, createHighlightedEdge(true));
        graph.addEdge(vertex2, vertex4, createHighlightedEdge(true));
        graph.addEdge(vertex1, vertex3, createHighlightedEdge(false));
        return graph;
    }
}
