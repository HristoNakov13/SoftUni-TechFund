import java.io.IOException;
import java.util.*;

public class VaporWinterSale_exam {

    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);

        Map<String, Double> gamesNoDLC = new HashMap<>();
        Map<String, Map<String, Double>> gamesWithDLC = new HashMap<>();

        String[] catalogue = scanner.nextLine().split(", ");

        for(String game : catalogue) {

            if (game.contains(":")) {
                String[] gameDLC = game.split(":");
                if (gamesNoDLC.containsKey(gameDLC[0])) {

                    double newPrice = gamesNoDLC.get(gameDLC[0]) + gamesNoDLC.get(gameDLC[0]) * 0.2;
                    gamesWithDLC.put(gameDLC[0], new HashMap<>());
                    gamesWithDLC.get(gameDLC[0]).put(gameDLC[1], newPrice);

                    gamesNoDLC.remove(gameDLC[0]);
                }
            }else if (game.contains("-")) {
                String[] gameAndPrice = game.split("-");
                gamesNoDLC.put(gameAndPrice[0], Double.parseDouble(gameAndPrice[1]));
            }
        }
        gamesWithDLC.entrySet().stream().sorted((pair1, pair2) -> {

            Map.Entry<String, Double> subPair1 = pair1.getValue().entrySet().iterator().next();
            Map.Entry<String, Double> subPair2 = pair2.getValue().entrySet().iterator().next();
            return Double.compare(subPair1.getValue(), subPair2.getValue());

        }).forEach(kvp -> {

            Map.Entry<String, Double> subPair = kvp.getValue().entrySet().iterator().next();
            System.out.println(String.format("%s - %s - %.2f", kvp.getKey(), subPair.getKey(), subPair.getValue() - subPair.getValue() * 0.5));
        });

        gamesNoDLC.entrySet().stream().sorted((pair1, pair2) ->
                Double.compare(pair2.getValue(), pair1.getValue()))
                .forEach(kvp -> {
                    System.out.println(String.format("%s - %.2f", kvp.getKey(), kvp.getValue() - kvp.getValue() * 0.2));
                });
    }
}