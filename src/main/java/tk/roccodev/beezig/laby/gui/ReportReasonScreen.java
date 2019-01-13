package tk.roccodev.beezig.laby.gui;

import net.labymod.gui.elements.ModTextField;
import net.labymod.main.lang.LanguageManager;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;
import tk.roccodev.beezig.CommandManager;
import tk.roccodev.beezig.laby.LabyMain;

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
