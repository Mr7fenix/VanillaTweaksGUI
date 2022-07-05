package me.mr7fenix.vanillatweaksgui.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.CheckboxWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardPlayerScore;
import net.minecraft.text.Text;

import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class ConfigScreen extends VanillaTweaksGui {

    private final String DELAY_CONFIG = "#delay";
    private final String COOLDOWN_CONFIG = "#cooldown";
    private TextFieldWidget homeLimit;
    private TextFieldWidget homeDelay;
    private TextFieldWidget homeCooldown;
    private TextFieldWidget backDelay;
    private TextFieldWidget backCooldown;
    private CheckboxWidget backDeath;
    private TextFieldWidget spawnDelay;
    private TextFieldWidget spawnCooldown;
    private TextFieldWidget tpaCooldown;

    protected ConfigScreen(ClientPlayerEntity player, MinecraftClient client) {
        super(player, client);
    }

    @Override
    public void showScreen() {
        showHomeConfigs();
        showBackConfigs();
        showSpawnConfigs();
        showTpaConfigs();


        addDrawableChild(new ButtonWidget(START_POINT + 120, height - 25, 110, 20, Text.of("Back"), (onPress) -> client.setScreen(new MainScreen(player, client))));

        addDrawableChild(new ButtonWidget(START_POINT, height - 25, 110, 20, Text.of("Save"), (onPress) -> {



            //Send command to modify home homeLimit
            player.sendCommand("scoreboard players set #limit homes.config " + homeLimit.getText());
            player.sendCommand("scoreboard players set #delay homes.config " + homeDelay.getText());
            player.sendCommand("scoreboard players set #cooldown homes.config " + homeCooldown.getText());
            player.sendCommand("scoreboard players set #delay back.config " + backDelay.getText());
            player.sendCommand("scoreboard players set #cooldown back.config " + backCooldown.getText());
            player.sendCommand("scoreboard players set #death back.config " + (backDeath.isChecked() ? 1 : 0));
            player.sendCommand("scoreboard players set #cooldown tpa.config " + tpaCooldown.getText());
            player.sendCommand("scoreboard players set #delay spawn.config " + spawnDelay.getText());
            player.sendCommand("scoreboard players set #cooldown spawn.config " + spawnCooldown.getText());
            client.setScreen(new MainScreen(player, client));
        }));
    }

    private void showHomeConfigs () {
        String HOME_CONFIG = "homes.config";
        homeLimit = addDrawableChild(new TextFieldWidget(textRenderer, START_POINT, 20, 110, 20, Text.of(getScore(HOME_CONFIG, "#limit").toString())));
        homeLimit.setText(getScore(HOME_CONFIG, "#limit").toString());

        homeDelay = addDrawableChild(new TextFieldWidget(textRenderer, START_POINT, 60, 110, 20, Text.of(getScore(HOME_CONFIG, DELAY_CONFIG).toString())));
        homeDelay.setText(getScore(HOME_CONFIG, DELAY_CONFIG).toString());

        homeCooldown = addDrawableChild(new TextFieldWidget(textRenderer, START_POINT, 100, 110, 20, Text.of(getScore(HOME_CONFIG, COOLDOWN_CONFIG).toString())));
        homeCooldown.setText(getScore(HOME_CONFIG, COOLDOWN_CONFIG).toString());

    }

    private void showBackConfigs () {
        String BACK_CONFIG = "back.config";
        backDelay = addDrawableChild(new TextFieldWidget(textRenderer, START_POINT + 122, 20, 110, 20, Text.of(getScore(BACK_CONFIG, DELAY_CONFIG).toString())));
        backDelay.setText(getScore(BACK_CONFIG, DELAY_CONFIG).toString());

        backCooldown = addDrawableChild(new TextFieldWidget(textRenderer, START_POINT + 122, 60, 110, 20, Text.of(getScore(BACK_CONFIG, COOLDOWN_CONFIG).toString())));
        backCooldown.setText(getScore(BACK_CONFIG, COOLDOWN_CONFIG).toString());

        backDeath = addDrawableChild(new CheckboxWidget(START_POINT + 122, 100, 20, 20, Text.of("Back tp to death"), getScore(BACK_CONFIG, "#death").equals(1)));

    }

    private void showTpaConfigs () {
        String TPA_CONFIG = "tpa.config";
        tpaCooldown = addDrawableChild(new TextFieldWidget(textRenderer, START_POINT + 244, 20, 110, 20, Text.of(getScore(TPA_CONFIG, COOLDOWN_CONFIG).toString())));
        tpaCooldown.setText(getScore(TPA_CONFIG, COOLDOWN_CONFIG).toString());

    }

    private void showSpawnConfigs () {
        String SPAWN_CONFIG = "spawn.config";
        spawnDelay = addDrawableChild(new TextFieldWidget(textRenderer, START_POINT + 244, 60, 110, 20, Text.of(getScore(SPAWN_CONFIG, DELAY_CONFIG).toString())));
        spawnDelay.setText(getScore(SPAWN_CONFIG, DELAY_CONFIG).toString());

        spawnCooldown = addDrawableChild(new TextFieldWidget(textRenderer, START_POINT + 244, 100, 110, 20, Text.of(getScore(SPAWN_CONFIG, COOLDOWN_CONFIG).toString())));
        spawnCooldown.setText(getScore(SPAWN_CONFIG, COOLDOWN_CONFIG).toString());

    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        super.render(matrices, mouseX, mouseY, delta);

        //Home config text
        drawTextWithShadow(matrices, textRenderer, Text.of("Max home limit: "), START_POINT, 10, 0xFFFFFF);
        drawTextWithShadow(matrices, textRenderer, Text.of("Home delay: "), START_POINT, 50, 0xFFFFFF);
        drawTextWithShadow(matrices, textRenderer, Text.of("Home Cooldown: "), START_POINT, 90, 0xFFFFFF);

        //Back config text
        drawTextWithShadow(matrices, textRenderer, Text.of("Back delay: "), START_POINT + 122, 10, 0xFFFFFF);
        drawTextWithShadow(matrices, textRenderer, Text.of("Back Cooldown: "), START_POINT + 122, 50, 0xFFFFFF);

        //Tpa config text
        drawTextWithShadow(matrices, textRenderer, Text.of("Tpa Cooldown: "), START_POINT + 244, 10, 0xFFFFFF);

        //Spawn config text
        drawTextWithShadow(matrices, textRenderer, Text.of("Spawn delay: "), START_POINT + 244, 50, 0xFFFFFF);
        drawTextWithShadow(matrices, textRenderer, Text.of("Spawn Cooldown: "), START_POINT + 244, 90, 0xFFFFFF);
    }

    private Integer getScore(String scoreBoard, String config) {
        for (ScoreboardPlayerScore player2 : Objects.requireNonNull(client.getServer()).getScoreboard().getAllPlayerScores(Objects.requireNonNull(client.getServer()).getScoreboard().getObjective(scoreBoard))) {
            if (player2.getPlayerName().equals(config)) {
                return (player2.getScore());
            }
        }
        return 0;
    }
}
