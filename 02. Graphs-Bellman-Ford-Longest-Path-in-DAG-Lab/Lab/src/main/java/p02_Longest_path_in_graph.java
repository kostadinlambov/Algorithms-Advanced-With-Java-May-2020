import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class p02_Longest_path_in_graph {
    public static int[][] graph;
    public static int[] distance;
    public static boolean[] visited;

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int nodes = Integer.parseInt(reader.readLine());
        int edges = Integer.parseInt(reader.readLine());

        graph = new int[nodes + 1][nodes + 1];

        for (int i = 0; i < edges; i++) {
            int[] tokens = Arrays.stream(reader.readLine()
                    .split("\\s+"))
                    .mapToInt(Integer::parseInt)
                    .toArray();

            int source = tokens[0];
            int destination = tokens[1];
            int weight = tokens[2];

            graph[source][destination] = weight;
        }

        int source = Integer.parseInt(reader.readLine());
        int destination = Integer.parseInt(reader.readLine());

        distance = new int[graph.length];
        visited = new boolean[graph.length];

        Arrays.fill(distance, Integer.MIN_VALUE);
        distance[source] = 0;

        Deque<Integer> sorted = new ArrayDeque<>();

        for (int i = 1; i < graph.length; i++) {
            topologicalSort(i, sorted);
        }

        while (!sorted.isEmpty()) {
            int node = sorted.pop();

            for (int i = 0; i < graph[node].length; i++) {
                int weight = graph[node][i];
                if (weight != 0) {
                    if (distance[node] + weight > distance[i]) {
                        distance[i] = distance[node] + weight;
                    }
                }
            }
        }

        System.out.println(distance[destination]);
    }

    private static void topologicalSort(int node, Deque<Integer> sorted) {
        if (visited[node]) {
            return;
        }

        visited[node] = true;

        for (int i = 1; i < graph[node].length; i++) {
            if (graph[node][i] != 0) {
                topologicalSort(i, sorted);
            }
        }

        sorted.push(node);
    }
}
