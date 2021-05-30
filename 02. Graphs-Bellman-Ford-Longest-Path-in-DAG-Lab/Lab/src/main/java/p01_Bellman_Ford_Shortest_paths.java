import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class p01_Bellman_Ford_Shortest_paths {
    public static int[][] graph;
    public static int[] distance;
    public static int[] prev;

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));


        int nodes = Integer.parseInt(reader.readLine());
        int edges = Integer.parseInt(reader.readLine());


        graph = new int[nodes +1 ][nodes + 1];

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

        try {
            bellmanFord(source);
        }catch (IllegalStateException ise){
            System.out.println(ise.getMessage());
            return;
        }

        List<Integer> path = new ArrayList<>();

        path.add(destination);

        int node = prev[destination];

        while (node != -1){
            path.add(node);
            node = prev[node];
        }

        Collections.reverse(path);

        for (Integer currentNode : path) {
            System.out.print(currentNode + " ");
        }
        System.out.println();

        System.out.println(distance[destination]);
    }

    private static void bellmanFord(int sourceNode) {
        //Generics shortest path Algorithm
            // Init dist and prev
            // Set values to dist and prev


        distance = new int[graph.length];
        Arrays.fill(distance, Integer.MAX_VALUE);
        prev = new int[graph.length];
        Arrays.fill(prev, -1);

        distance[sourceNode] = 0;

        for (int i = 1; i < graph.length - 1; i++) {
            for (int r = 1; r < graph.length; r++) {
                for (int c = 1; c < graph[r].length ; c++) {
                    int weight = graph[r][c];

                    if(weight != 0){
                        int source = r;
                        int dest = c;

                        if(distance[source] != Integer.MAX_VALUE){
                            int newValue = distance[source] + weight;

                            if(newValue < distance[dest]){
                                distance[dest] = newValue;
                                prev[dest] = source;
                            }
                        }
                    }
                }
            }
        }

        for (int i = 1; i < graph.length - 1; i++) {
            for (int r = 1; r < graph.length; r++) {
                for (int c = 1; c < graph[r].length ; c++) {
                    int weight = graph[r][c];

                    if(weight != 0){
                        int source = r;
                        int dest = c;

                        if(distance[source] != Integer.MAX_VALUE){
                            int newValue = distance[source] + weight;

                            if(newValue < distance[dest]){
                               throw new IllegalStateException("Negative Cycle Detected");
                            }
                        }
                    }
                }
            }
        }
    }
}
