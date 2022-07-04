package me.mr7fenix.vanillatweaksgui.gui;


import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.PageTurnWidget;
import net.minecraft.text.Text;

import java.util.Map;

public class PlayerButtons extends ListHandler {

    protected PlayerButtons(VanillaTweaksGui screen) {
        super(screen);
    }

    public void showPlayer(int page) {
        clearPreviousButtons();
        int distance = 10;

        int i = 0;
        int j = BUTTON_PER_PAGE;
        for (Map.Entry<String, Integer> entry : getPlayersId().entrySet()) {
            if (i >= BUTTON_PER_PAGE) {
                break;
            }

            if (j == BUTTON_PER_PAGE * page) {


                int finalI = i;
                screen.addDrawableChild(new ButtonWidget(VanillaTweaksGui.START_POINT + 170, distance, 110, 20, Text.of(entry.getKey()), (onPress) -> {
                    screen.player.sendCommand("trigger tpa set " + finalI);
                    screen.client.setScreen(null);
                }));
                i++;

                distance += 22;
            } else j++;
        }

        //Turn page Back
        screen.addDrawableChild(new PageTurnWidget(VanillaTweaksGui.START_POINT + 170, 162, false, button -> showPlayer(turnPage(-1, true)), true));

        //Turn page Forward
        screen.addDrawableChild(new PageTurnWidget(VanillaTweaksGui.START_POINT + 86 + 170, 162, true, button -> showPlayer(turnPage(-1, true)), true));

    }
}
