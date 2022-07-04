package me.mr7fenix.vanillatweaksgui.gui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.jetbrains.annotations.MustBeInvokedByOverriders;

import java.util.*;

public abstract class VanillaTweaksGui extends Screen {
    public ClientPlayerEntity player;
    public MinecraftClient client;

    static final int START_POINT = 10;


    public VanillaTweaksGui(ClientPlayerEntity player, MinecraftClient client) {
        super(Text.of("VanillaTweaksGui"));
        this.player = player;
        this.client = client;
    }

    public abstract void showScreen();

    @Override
    protected void init() {
        client.keyboard.setRepeatEvents(true);
        showScreen();
    }

    @Override
    public void tick() {
        for (Element child : children()) {
            if (child instanceof TextFieldWidget t) {
                t.tick();
            }
        }
    }

    @Override
    @MustBeInvokedByOverriders
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        String version = "Version: 1.0.1";
        drawCenteredText(matrices, textRenderer, version, width - textRenderer.getWidth(version) / 2 - 6, height - 16, 0xffffff);
        super.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    @Override
    public <T extends Element & Drawable & Selectable> T addDrawableChild(T drawableElement) {
        return super.addDrawableChild(drawableElement);
    }
}
