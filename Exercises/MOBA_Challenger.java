import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class MOBA_Challenger {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Map<String, Map<String, Integer>> seasonRankings = new HashMap<>();
        String input;

        while (!"Season end".equals(input = scanner.nextLine())) {

            String[] inputCommand = input.split(" -> | vs ");
            String player = inputCommand[0];
            String player2 = "";
            boolean fightOrUpdateRanks = inputCommand.length == 3;

            if (fightOrUpdateRanks) {

                String position = inputCommand[1];
                int skillPoints = Integer.parseInt(inputCommand[2]);

                seasonRankings.putIfAbsent(position, new HashMap<>());
                seasonRankings.get(position).putIfAbsent(player, -1);
                if (seasonRankings.get(position).get(player) < skillPoints) {
                    seasonRankings.get(position).put(player, skillPoints);
                }
            }
            boolean fightRequirments = false;
            if (!fightOrUpdateRanks) {
                player2 = inputCommand[1];

                for(Map.Entry<String, Map<String, Integer>> entry : seasonRankings.entrySet()) {
                    if (entry.getValue().containsKey(player) && entry.getValue().containsKey(player2)) {
                        fightRequirments = true;
                        break;
                    }
                }
            }
            if (fightRequirments) {
                player2 = inputCommand[1];
                String winner = totalSkillPointsCalc(player, player2, seasonRankings);
                switch (winner) {
                    case "Player 1":
                        removePlayer(player2, seasonRankings);
                        break;
                    case "Player 2":
                        removePlayer(player, seasonRankings);
                        break;
                }
            }
        }
        Map<String, Map<String, Integer>> printing = new LinkedHashMap<>();

        for(Map.Entry<String, Map<String, Integer>> entry : seasonRankings.entrySet()) {
            for(Map.Entry<String, Integer> subEntry : entry.getValue().entrySet()) {
                printing.putIfAbsent(subEntry.getKey(), new LinkedHashMap<>());
                printing.get(subEntry.getKey()).put(entry.getKey(), subEntry.getValue());
            }
        }
        AtomicInteger e1Sum = new AtomicInteger();
        AtomicInteger e2Sum = new AtomicInteger();

        printing.entrySet().stream().sorted((e2, e1) -> {
            e1Sum.set(0);
            e2Sum.set(0);
            e1.getValue().entrySet().stream().forEach(entry -> {
                e1Sum.addAndGet(entry.getValue());
            });
            e2.getValue().entrySet().stream().forEach(entry -> {
                e2Sum.addAndGet(entry.getValue());
            });
            int sort = Integer.compare(e1Sum.get(), e2Sum.get());
            if (sort == 0) {
                sort = e2.getKey().compareTo(e1.getKey());
            }
            return sort;
        }).forEach(key -> {
            e1Sum.set(0);

            key.getValue().entrySet().stream().forEach(entry -> {
                e1Sum.addAndGet(entry.getValue());
            });
            System.out.println(key.getKey() + ": " + e1Sum + " skill");
            key.getValue().entrySet().stream().sorted((e2, e1) -> {
                int sort = Integer.compare(e1.getValue(), e2.getValue());
                if (sort == 0) {
                    sort = e2.getKey().compareTo(e1.getKey());
                }
                return sort;
            }).forEach(entry -> {
                System.out.printf("- %s <::> %d\n", entry.getKey(), entry.getValue());
            });
        });



    }
    public static String totalSkillPointsCalc (String player1, String player2, Map<String, Map<String, Integer>> rankings) {
        String winner = "";
        int player1SkillPoints = 0;
        int player2SkillPoints = 0;

        for(Map.Entry<String, Map<String, Integer>> entry : rankings.entrySet()) {
            for(Map.Entry<String, Integer> subEntry : entry.getValue().entrySet()) {
                if (subEntry.getKey().equals(player1)) {
                    player1SkillPoints += subEntry.getValue();
                }else if (subEntry.getKey().equals(player2)) {
                    player2SkillPoints += subEntry.getValue();
                }
            }
        }
        if (player1SkillPoints > player2SkillPoints) {
            winner = "Player 1";
        }else if (player1SkillPoints < player2SkillPoints) {
            winner = "Player 2";
        }
        return winner;
    }
    public static void removePlayer (String player, Map<String, Map<String, Integer>> rankings) {
        for(Map.Entry<String, Map<String, Integer>> entry : rankings.entrySet()) {
            entry.getValue().remove(player);
        }
    }
}