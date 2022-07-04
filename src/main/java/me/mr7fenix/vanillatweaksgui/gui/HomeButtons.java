package me.mr7fenix.vanillatweaksgui.gui;

import me.mr7fenix.vanillatweaksgui.VanillaTweaksGuiMain;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.PageTurnWidget;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.Map;

public class HomeButtons extends ListHandler {

    protected HomeButtons(VanillaTweaksGui screen) {
        super(screen);
    }

    public void showHome(int page) {
        clearPreviousButtons();
        int distance = 10;
        Map<Integer, String> homes =  VanillaTweaksGuiMain.CONFIG.getHomes();

        int i = 0;
        int j = BUTTON_PER_PAGE;
        for (Map.Entry<Integer, String> entry : homes.entrySet()) {
            if (i >= BUTTON_PER_PAGE) {
                break;
            }

            if (j == BUTTON_PER_PAGE * page) {
                ClickableWidget homeButton = screen.addDrawableChild(new ButtonWidget(VanillaTweaksGui.START_POINT, distance, 110, 20, Text.of(entry.getValue()), (onPress) -> {
                    screen.player.sendCommand("trigger home set " + entry.getKey());
                    screen.client.setScreen(null);
                }));

                ClickableWidget dellButton = screen.addDrawableChild(new TexturedButtonWidget(VanillaTweaksGui.START_POINT + 142, distance, 20, 20, 0, 0, 20, new Identifier("vanilla-tweaks-gui:textures/gui/bin.png"), 20, 20, (onPress) -> screen.client.setScreen(new ConfirmDeleteScreen(screen.player, screen.client, entry.getKey()))));

                ClickableWidget modButton = screen.addDrawableChild(new TexturedButtonWidget(VanillaTweaksGui.START_POINT + 120, distance, 20, 20, 0, 0, 20, new Identifier("vanilla-tweaks-gui:textures/gui/modify.png"), 20, 20, (onPress) -> screen.client.setScreen(new ModifyScreen(screen.player, screen.client, entry.getKey()))));
                i++;

                distance += 22;

                buttons.addAll(Arrays.asList(homeButton, dellButton, modButton));
            } else j++;
        }

        //Turn page Back
        screen.addDrawableChild(new PageTurnWidget(VanillaTweaksGui.START_POINT, 162, false, button -> showHome(turnPage(-1, homes.size())), true));

        //Turn page Forward
        screen.addDrawableChild(new PageTurnWidget(VanillaTweaksGui.START_POINT + 86, 162, true, button -> showHome(turnPage(1, homes.size())), true));
    }
}
