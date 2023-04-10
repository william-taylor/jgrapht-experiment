package com.rate.graph.app.graph;

import com.rate.graph.app.graph.Edge;
import com.rate.graph.app.graph.Node;
import com.rate.graph.app.graph.Path;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.jgrapht.Graph;
import org.jgrapht.nio.json.JSONExporter;
import org.jgrapht.traverse.BreadthFirstIterator;

import java.io.StringWriter;
import java.util.*;
import java.util.stream.Collectors;

@UtilityClass
public class Exporter {

    public String toJson(Graph<Node, Edge> graph) {
        final var exporter = new JSONExporter<Node, Edge>();
        exporter.setVertexAttributeProvider(Node::getAttributes);
        exporter.setEdgeAttributeProvider(Edge::getAttributes);

        final var writer = new StringWriter();
        exporter.exportGraph(graph, writer);
        return writer.toString();
    }

    public List<Path> toPaths(Graph<Node, Edge> graph) {
        final var iterator = new BreadthFirstIterator<>(graph);
        final var parents = new LinkedHashSet<Node>();
        final var paths = new LinkedList<Path>();

        while (iterator.hasNext()) {
            final var node = iterator.next();
            parents.add(iterator.getParent(node));

            final var visitedPath = parents.stream()
                    .filter(Objects::nonNull)
                    .map(Node::getName)
                    .reduce(StringUtils.EMPTY, (a, b) -> a + Path.SEPARATOR + b);

            final var currentPath = new StringBuilder()
                    .append(visitedPath)
                    .append(Path.SEPARATOR)
                    .append(node.getName())
                    .toString();

            final var customAttributePaths = node.getCustomAttributes()
                    .entrySet()
                    .stream()
                    .map(entry -> {
                        final var name = entry.getKey();
                        final var value = entry.getValue();
                        return new Path(currentPath + Path.SEPARATOR + name, value);
                    })
                    .collect(Collectors.toList());

            paths.add(new Path(currentPath, node.getValue()));
            paths.addAll(customAttributePaths);
        }

        return paths;
    }
}
