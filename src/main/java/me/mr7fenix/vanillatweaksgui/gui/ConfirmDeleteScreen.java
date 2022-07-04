package me.mr7fenix.vanillatweaksgui.gui;

import me.mr7fenix.vanillatweaksgui.VanillaTweaksGuiMain;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;

public class ConfirmDeleteScreen extends VanillaTweaksGui {
    int key;
    protected ConfirmDeleteScreen(ClientPlayerEntity player, MinecraftClient client, int key) {
        super(player, client);
        this.key = key;
    }
    @Override
    public void showScreen() {
        TextFieldWidget title = addDrawableChild(new TextFieldWidget(textRenderer, START_POINT + 25, 10, 110, 20, Text.of("Your sure?")));
        title.setDrawsBackground(false);
        title.setText("Are you sure?");
        title.setEditable(false);
        title.setTextFieldFocused(false);
        title.setFocusUnlocked(false);
        title.setCursor(0);
        title.setUneditableColor(0xFFFFFF);

        addDrawableChild(new ButtonWidget(START_POINT, 32, 110, 20, Text.of("Yes"), (onPress) -> {
            VanillaTweaksGuiMain.CONFIG.removeHome(key);
            client.setScreen(new MainScreen(player, client));
        }));

        addDrawableChild(new ButtonWidget(START_POINT, 54, 110, 20, Text.of("No"), (onPress) -> client.setScreen(this)));
    }
}
