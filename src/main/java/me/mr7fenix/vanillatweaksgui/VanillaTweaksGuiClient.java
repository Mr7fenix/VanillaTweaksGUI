package me.mr7fenix.vanillatweaksgui;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class VanillaTweaksGuiClient implements ClientModInitializer {
    private static KeyBinding keyBindings;

    @Override
    public void onInitializeClient() {
        keyBindings = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "Open GUI",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_J,
                "VanillaTweaksGui"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (keyBindings.wasPressed()) {
                client.setScreen(new VanillaTweaksGui(client.player, client));
            }
        });
    }
}
