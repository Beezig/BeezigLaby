/*
 * Copyright (C) 2019 Beezig (RoccoDev, ItsNiklass)
 *
 * This file is part of BeezigLaby.
 *
 * BeezigLaby is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * BeezigLaby is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with BeezigLaby.  If not, see <http://www.gnu.org/licenses/>.
 */

package eu.beezig.laby.gui;

import net.labymod.gui.elements.ModTextField;
import net.labymod.main.lang.LanguageManager;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;
import eu.beezig.core.CommandManager;
import eu.beezig.laby.LabyMain;

import java.io.IOException;

public class ReportReasonScreen extends GuiScreen {

    private ModTextField fReason;
    private GuiButton btnDone, btnCancel;
    private GuiScreen lastScreen;
    private String player;

    public ReportReasonScreen(GuiScreen last, String player) {
        this.lastScreen = last;
        this.player = player;
    }

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        fReason = new ModTextField(-1,
                LabyMain.LABY.getDrawUtils().fontRenderer,
                this.width / 2 - 100, this.height / 2 - 20, 200, 20);
        fReason.setText("Cheating");
        if (player != null && !player.isEmpty()) {
            fReason.setCursorPositionEnd();
            fReason.setSelectionPos(0);
        }


        buttonList.add(btnDone = new GuiButton(1, this.width / 2 + 3, this.height / 2 + 5, 98, 20,
                LanguageManager.translate("button_send")));

        buttonList.add(btnCancel = new GuiButton(0, this.width / 2 - 101, this.height / 2 + 5, 98, 20,
                LanguageManager.translate("button_cancel")));
        super.initGui();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        LabyMain.LABY.getDrawUtils().drawString("Report " + player, width / 2 - 100, height / 2 - 33);
        fReason.drawTextBox();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        fReason.mouseClicked(mouseX, mouseY, mouseButton);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        switch(button.id) {
            case 0:
                this.mc.displayGuiScreen(this.lastScreen);
                break;
            case 1:
                CommandManager.dispatchCommand("/report " + player + " " + fReason.getText());
                this.mc.displayGuiScreen(this.lastScreen);
                break;
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        fReason.textboxKeyTyped(typedChar, keyCode);
        if (this.btnDone.enabled && (keyCode == 28 || keyCode == 156)) {
            this.actionPerformed(this.btnDone);
        }
        if (keyCode == 1) {
            this.actionPerformed(this.btnCancel);
            return;
        }
        super.keyTyped(typedChar, keyCode);
    }
}
