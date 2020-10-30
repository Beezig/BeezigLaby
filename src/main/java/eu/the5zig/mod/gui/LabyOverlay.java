package eu.the5zig.mod.gui;

import net.labymod.main.LabyMod;

public class LabyOverlay implements IOverlay {
    @Override
    public void displayMessage(String title, String subtitle) {
        displayMessage(title, subtitle, null);
    }

    @Override
    public void displayMessage(String title, String subtitle, Object uniqueReference) {
        LabyMod.getInstance().getGuiCustomAchievement().displayAchievement(title, subtitle);
    }

    @Override
    public void displayMessage(String message) {
        displayMessage("Beezig", message);
    }

    @Override
    public void displayMessage(String message, Object uniqueReference) {
        displayMessage("Beezig", message);
    }

    @Override
    public void displayMessageAndSplit(String message) {
        displayMessage(message);
    }

    @Override
    public void displayMessageAndSplit(String message, Object uniqueReference) {

    }
}
