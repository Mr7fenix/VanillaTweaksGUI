package me.mr7fenix.vanillatweaksgui;

import com.mojang.brigadier.LiteralMessage;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.gui.screen.ingame.ScreenHandlerProvider;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.LiteralTextContent;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class VanillaTweakGUIClient implements ClientModInitializer {
    private static KeyBinding keyBindings;
    @Override
    public void onInitializeClient() {
        keyBindings = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "j.vanillatweaksgui.openGUI",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_J,
                "key.category.vanillatweaksgui"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (keyBindings.wasPressed()) {
                client.setScreen(new VanillaTweaksGui(client.player, client));
            }
        });
    }
}
