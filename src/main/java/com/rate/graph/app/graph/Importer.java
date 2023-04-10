package com.rate.graph.app.graph;

import lombok.experimental.UtilityClass;
import org.jgrapht.Graph;
import org.jgrapht.graph.DirectedMultigraph;
import org.jgrapht.nio.Attribute;
import org.jgrapht.nio.json.JSONImporter;

import java.io.StringReader;
import java.util.Map;

@UtilityClass
public class Importer {

    public Graph<Node, Edge> fromJson(final String json) {
        final var importer = new JSONImporter<Node, Edge>();
        importer.setEdgeWithAttributesFactory(Importer::toEdge);
        importer.setVertexWithAttributesFactory(Importer::toNode);

        final var graph = new DirectedMultigraph<Node, Edge>(Edge.class);
        importer.importGraph(graph, new StringReader(json));
        return graph;
    }

    Edge toEdge(Map<String, Attribute> attributes) {
        return Edge.builder()
                .attributes(attributes)
                .build();
    }

    Node toNode(String id, Map<String, Attribute> attributes) {
        return Node.builder()
            .attributes(attributes)
            .build();
    }

}
