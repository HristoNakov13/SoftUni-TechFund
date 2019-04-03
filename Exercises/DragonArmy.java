import java.util.*;


public class DragonArmy {

    public static class Dragon {
        private String name;
        private Integer damage;
        private Integer health;
        private Integer armor;

        public Dragon(Integer damage, Integer health, Integer armor) {
            this.damage = damage;
            this.health = health;
            this.armor = armor;
        }

        public Dragon() {
        }

        public Integer getDamage() {
            return damage;
        }

        public void setDamage(Integer damage) {
            this.damage = damage;
        }

        public Integer getHealth() {
            return health;
        }

        public void setHealth(Integer health) {
            this.health = health;
        }

        public Integer getArmor() {
            return armor;
        }

        public void setArmor(Integer armor) {
            this.armor = armor;
        }

    }
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        Map<String, Map<String, Dragon>> dragons = new LinkedHashMap<>();
        int countDragons = Integer.parseInt(scanner.nextLine());

        for (int i = 0; i < countDragons; i++) {

            String[] data = scanner.nextLine().split(" ");

            String type = data[0];
            String name = data[1];
            boolean validDragon = type.charAt(0) > 90 || name.charAt(0) > 90
                    || (type.contains(" ") || name.contains(" "));

            if (validDragon) {
                continue;
            }

            int damage = dragonStats(data[2], "damage");
            int health = dragonStats(data[3], "health");
            int armor = dragonStats(data[4], "armor");

            dragons.putIfAbsent(type, new HashMap<>());

            dragons.get(type).putIfAbsent(name, new Dragon());

            dragons.get(type).get(name).setDamage(damage);
            dragons.get(type).get(name).setHealth(health);
            dragons.get(type).get(name).setArmor(armor);

        }

        for (Map.Entry<String, Map<String, Dragon>> dragon : dragons.entrySet()) {

            double averageDmg = 0;
            double averageHP = 0;
            double averageArmor = 0;

            for (Map.Entry<String, Dragon> name : dragon.getValue().entrySet()) {
                averageDmg += name.getValue().getDamage();
                averageHP += name.getValue().getHealth();
                averageArmor += name.getValue().getArmor();

            }
            averageDmg /= dragon.getValue().size();
            averageHP /= dragon.getValue().size();
            averageArmor /= dragon.getValue().size();

            System.out.println(String.format("%s::(%.2f/%.2f/%.2f)",
                    dragon.getKey(), averageDmg, averageHP, averageArmor));

            dragon.getValue().entrySet().stream()
                    .sorted(Comparator.comparing(Map.Entry::getKey))
                    .forEach(pair ->
                            System.out.println(String.format("-%s -> damage: %d, health: %d, armor: %d"
                                    , pair.getKey(), pair.getValue().getDamage()
                                    , pair.getValue().getHealth(), pair.getValue().getArmor())));
        }
    }
    public static int dragonStats (String stats, String statFor) {

        int stat;
        if ("null".equals(stats)) {
            switch (statFor) {
                case "health":
                    stat = 250;
                    break;
                case "damage":
                    stat = 45;
                    break;
                case "armor":
                    stat = 10;
                    break;
                default:
                    stat = 0;
                    break;
            }
        }else {
            stat = Integer.parseInt(stats);
        }
        return stat;
    }
}
