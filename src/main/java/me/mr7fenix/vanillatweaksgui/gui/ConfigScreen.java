package me.mr7fenix.vanillatweaksgui.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.scoreboard.ScoreboardPlayerScore;
import net.minecraft.text.Text;

public class ConfigScreen extends VanillaTweaksGui {

    protected ConfigScreen(ClientPlayerEntity player, MinecraftClient client) {
        super(player, client);
    }
    @Override
    public void showScreen(){
        clearChildren();
        TextFieldWidget limit = addDrawableChild(new TextFieldWidget(textRenderer, START_POINT, 10, 110, 20, Text.of(getMaxHome().toString())));
        limit.setText(getMaxHome().toString());


        addDrawableChild(new ButtonWidget(300, 10, 110, 20, Text.of("Back"), (onPress) -> client.setScreen(this)));

        addDrawableChild(new ButtonWidget(300, 32, 110, 20, Text.of("Save"), (onPress) -> {
            player.sendCommand("scoreboard players set #limit homes.config " + limit.getText());
            client.setScreen(new MainScreen(player, client));
        }));
    }

    Integer getMaxHome(){
        for (ScoreboardPlayerScore player2 : player.getScoreboard().getAllPlayerScores(player.getScoreboard().getObjective("homes.config"))) {
            if (player2.getPlayerName().equals("#limit")) {
                return (player2.getScore());
            }
        }
        return 1;
    }
}
