package Enchanter;

import org.powerbot.script.rt4.Magic;

import static java.lang.StrictMath.round;
import static org.powerbot.script.rt4.Magic.Spell.*;

public class Properties {

    final public static String[] NAMES = {"Amulet", "Necklace", "Ring", "Bracelet"};

    //All arrays are always sorted by level of material (and enchantment): Sapphire < Emerald < Ruby < Diamond

    private final static int[] AMULET_ITEM_IDS = {1694, 1658, 1698, 1662};
    private final static int[] NECKLACE_ITEM_IDS = {1656, 1696, 1660, 1662};
    private final static int[] RING_ITEM_IDS = {1637, 1639, 1641, 1643};
    private final static int[] BRACELET_ITEM_IDS = {11071, 11076, 1105, 11092};

    private final static Magic.MagicSpell[] SPELLS = {
        ENCHANT_LEVEL_1_JEWELLERY,
        ENCHANT_LEVEL_2_JEWELLERY,
        ENCHANT_LEVEL_3_JEWELLERY,
        ENCHANT_LEVEL_4_JEWELLERY,
        ENCHANT_LEVEL_5_JEWELLERY,
    };

    private final static double AVERAGE_XP[] = {17.5, 37, 49, 57};
    public final static int[] STAFF_IDS = {1383, 1381, 1387, 1385};

    public enum SelectedItem {
        SELECTED_ITEM();

        private int id;
        public int necStaff;
        public Magic.MagicSpell spell;
        double xp;

        SelectedItem(){}

        public void setSelectedItem(String itemType, int level) {
            for(Properties.ItemType ty : Properties.ItemType.values()) {
                if(ty.equals(itemType)) {
                    Properties.SelectedItem.SELECTED_ITEM.setId(ty.typeIds[level - 1]);
                    Properties.SelectedItem.SELECTED_ITEM.setMagicSpell(level);
                    xp = AVERAGE_XP[level - 1];
                    necStaff = STAFF_IDS[level - 1];
                }
            }
        }

        private void setId(int id) {
            this.id = id;
        }

        private void setMagicSpell(int level) {
            this.spell = SPELLS[level - 1];
        }

        public int id() {
            return SelectedItem.SELECTED_ITEM.id;
        }
    }

    public enum ItemType {
        AMULET("Amulet", AMULET_ITEM_IDS),
        NECKLACE("Necklace", NECKLACE_ITEM_IDS),
        RING("Ring", RING_ITEM_IDS),
        BRACELET("Bracelet", BRACELET_ITEM_IDS);

        String name;
        int[] typeIds;

        ItemType(String name, int[] typeIds) {
            this.name = name;
            this.typeIds = typeIds;
        }

        public boolean equals(String s) {
            return this.name.equals(s);
        }
    }

    public final static int cosmicRuneId = 564;
    static boolean window = false;
    static boolean running = false;
    private static int enchantedItemsCounter = 0;
    public static boolean rightStaff = false;

    public static void setWindow(boolean b){
        window = b;
    }

    public static void setRunning(boolean b) {
        running = b;
    }

    public static int calcEnchantedItemCount(int gainedExp) {
        return (int) round(gainedExp / SelectedItem.SELECTED_ITEM.xp);
    }

}
