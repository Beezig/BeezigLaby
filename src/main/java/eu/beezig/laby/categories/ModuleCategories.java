package eu.beezig.laby.categories;

import net.labymod.ingamegui.ModuleCategory;
import net.labymod.ingamegui.ModuleCategoryRegistry;
import net.labymod.settings.elements.ControlElement;
import net.labymod.utils.Material;

public enum ModuleCategories {

    GLOBAL(new ModuleCategory("The Hive", true, new ControlElement.IconData(Material.GLOWSTONE_DUST)), "global"),
    BED(new ModuleCategory("Hive Bedwars", true, new ControlElement.IconData(Material.BED)), "bed"),
    TIMV(new ModuleCategory("Hive Trouble in Mineville", true, new ControlElement.IconData(Material.STICK)), "timv"),
    MIMV(new ModuleCategory("Hive Murder in Mineville", true, new ControlElement.IconData(Material.REDSTONE)), "mimv"),
    SKY(new ModuleCategory("Hive Skywars", true, new ControlElement.IconData(Material.DIAMOND)), "sky"),
    HIDE(new ModuleCategory("Hive Hide and Seek", true, new ControlElement.IconData(Material.WORKBENCH)), "hide"),
    CAI(new ModuleCategory("Hive Cowboys and Indians", true, new ControlElement.IconData(Material.FEATHER)), "cai"),
    LAB(new ModuleCategory("Hive TheLab", true, new ControlElement.IconData(Material.GLASS_BOTTLE)), "lab"),
    BP(new ModuleCategory("Hive BlockParty", true, new ControlElement.IconData(Material.RECORD_3)), "bp"),
    DR(new ModuleCategory("Hive DeathRun", true, new ControlElement.IconData(Material.LAVA_BUCKET)), "dr"),
    ARCADE(new ModuleCategory("Hive Arcade", true, new ControlElement.IconData(Material.DIAMOND_BLOCK)), "arcade"),
    GNT(new ModuleCategory("Hive SkyGiants", true, new ControlElement.IconData(Material.SKULL_ITEM)), "gnt"),
    GRAV(new ModuleCategory("Hive Gravity", true, new ControlElement.IconData(Material.GOLD_BOOTS)), "grav"),
    SGN(new ModuleCategory("Hive Survival Games 2", true, new ControlElement.IconData(Material.IRON_SWORD)), "sgn");

    private ModuleCategory category;
    private String trigger;

    ModuleCategories(ModuleCategory category, String trigger) {
        this.category = category;
        this.trigger = trigger;
    }

    public ModuleCategory getCategory() {
        return category;
    }

    public static ModuleCategories get(String in) {
        for(ModuleCategories test : values()) {
            if(in.startsWith(test.trigger)) return test;
        }
        return GLOBAL;
    }

    public static void init() {
        for(ModuleCategories cat : values()) {
            ModuleCategoryRegistry.loadCategory(cat.getCategory());
        }
    }
}
