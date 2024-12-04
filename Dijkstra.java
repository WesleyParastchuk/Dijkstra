import java.util.*;

public class Dijkstra {
    public static void main(String[] args) {
        Map<String, Map<String, Integer>> grafo = new HashMap<>();
        grafo.put("A", Map.of("B", 4, "C", 2, "D", 7));
        grafo.put("B", Map.of("A", 4, "C", 1, "E", 1));
        grafo.put("C", Map.of("A", 2, "B", 1, "D", 3, "E", 3));
        grafo.put("D", Map.of("A", 7, "C", 3, "E", 2));
        grafo.put("E", Map.of("B", 1, "C", 3, "D", 2));

        Map<String, Integer> distancias = new HashMap<>();
        Map<String, String> predecessores = new HashMap<>();

        dijkstra(grafo, "A", distancias, predecessores);

        System.out.println("Menor tempo para chegar de A até E: " + distancias.get("E") + " horas");
        System.out.println("Caminho de A até E: " + construirCaminho("E", predecessores));

        System.out.println("Menor tempo para chegar de A até D: " + distancias.get("D") + " horas");
        System.out.println("Caminho de A até D: " + construirCaminho("D", predecessores));
    }

    public static void dijkstra(Map<String, Map<String, Integer>> grafo, String origem,
                                 Map<String, Integer> distancias, Map<String, String> predecessores) {
        PriorityQueue<String> fila = new PriorityQueue<>(Comparator.comparingInt(distancias::get));
        Set<String> visitados = new HashSet<>();

        for (String vertice : grafo.keySet()) {
            distancias.put(vertice, Integer.MAX_VALUE);
            predecessores.put(vertice, null);
        }
        distancias.put(origem, 0);
        fila.add(origem);

        while (!fila.isEmpty()) {
            String atual = fila.poll();
            if (visitados.contains(atual)) continue;
            visitados.add(atual);

            for (Map.Entry<String, Integer> vizinho : grafo.get(atual).entrySet()) {
                String adjacente = vizinho.getKey();
                int peso = vizinho.getValue();

                if (!visitados.contains(adjacente)) {
                    int novaDistancia = distancias.get(atual) + peso;
                    if (novaDistancia < distancias.get(adjacente)) {
                        distancias.put(adjacente, novaDistancia);
                        predecessores.put(adjacente, atual);
                        fila.add(adjacente);
                    }
                }
            }
        }
    }

    public static List<String> construirCaminho(String destino, Map<String, String> predecessores) {
        LinkedList<String> caminho = new LinkedList<>();
        String atual = destino;

        while (atual != null) {
            caminho.addFirst(atual);
            atual = predecessores.get(atual);
        }
        return caminho;
    }
}
