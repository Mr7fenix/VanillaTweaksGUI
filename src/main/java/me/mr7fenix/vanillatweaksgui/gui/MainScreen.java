package me.mr7fenix.vanillatweaksgui.gui;

import me.mr7fenix.vanillatweaksgui.VanillaTweaksGuiMain;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;

public class MainScreen extends VanillaTweaksGui{
    MinecraftClient client;
    ClientPlayerEntity player;

    public MainScreen(ClientPlayerEntity player, MinecraftClient client) {
        super(player, client);

        this.client = client;
        this.player = player;
    }

    @Override
    protected void init() {
        super.init();
        new ListHandler(this).showAll();
    }

    public void showScreen() {
        addDrawableChild(new ButtonWidget(START_POINT, height - 52, 110, 20, Text.of("Add Home"), (onPress) -> client.setScreen(new AddScreen(player, client))));

        addDrawableChild(new ButtonWidget(START_POINT, height - 30, 110, 20, Text.of("Exit"), (onPress) -> client.setScreen(null)));

        addDrawableChild(new ButtonWidget(300, 10, 110, 20, Text.of("Back"), (onPress) -> {
            player.sendCommand("trigger back");
            client.setScreen(null);
        }));

        addDrawableChild(new ButtonWidget(300, 32, 110, 20, Text.of("Spawn"), (onPress) -> {
            player.sendCommand("trigger spawn");
            client.setScreen(null);
        }));

        if (player.hasPermissionLevel(1)) {
            addDrawableChild(new ButtonWidget(START_POINT, height - 74, 110, 20, Text.of("DataPack Config"), (onPress) -> new ConfigScreen(player, client)));
        }
    }
}
