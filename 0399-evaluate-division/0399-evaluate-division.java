import java.util.*;

class Solution {
    public double[] calcEquation(List<List<String>> equations,
                                 double[] values,
                                 List<List<String>> queries) {

        // Graph: variable -> (neighbor, value)
        Map<String, Map<String, Double>> graph = new HashMap<>();

        // Build graph
        for (int i = 0; i < equations.size(); i++) {
            String a = equations.get(i).get(0);
            String b = equations.get(i).get(1);
            double value = values[i];

            graph.putIfAbsent(a, new HashMap<>());
            graph.putIfAbsent(b, new HashMap<>());

            graph.get(a).put(b, value);
            graph.get(b).put(a, 1.0 / value);
        }

        double[] result = new double[queries.size()];

        // Process queries
        for (int i = 0; i < queries.size(); i++) {
            String start = queries.get(i).get(0);
            String end = queries.get(i).get(1);

            if (!graph.containsKey(start) || !graph.containsKey(end)) {
                result[i] = -1.0;
            } else {
                Set<String> visited = new HashSet<>();
                result[i] = dfs(graph, start, end, 1.0, visited);
            }
        }

        return result;
    }

    private double dfs(Map<String, Map<String, Double>> graph,
                       String current,
                       String target,
                       double product,
                       Set<String> visited) {

        if (current.equals(target)) {
            return product;
        }

        visited.add(current);

        for (Map.Entry<String, Double> entry : graph.get(current).entrySet()) {

            String next = entry.getKey();
            double value = entry.getValue();

            if (visited.contains(next)) {
                continue;
            }

            double result = dfs(
                graph,
                next,
                target,
                product * value,
                visited
            );

            if (result != -1.0) {
                return result;
            }
        }

        return -1.0;
    }
}