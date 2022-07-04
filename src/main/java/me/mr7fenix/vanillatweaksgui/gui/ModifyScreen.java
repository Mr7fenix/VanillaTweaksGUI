package me.mr7fenix.vanillatweaksgui.gui;

import me.mr7fenix.vanillatweaksgui.VanillaTweaksGuiMain;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import java.util.Map;

public class ModifyScreen extends VanillaTweaksGui {

    private final Integer id;

    protected ModifyScreen(ClientPlayerEntity player, MinecraftClient client, int id) {
        super(player, client);
        this.id = id;
    }

    @Override
    public void showScreen() {
        Map<Integer, String> homes = VanillaTweaksGuiMain.CONFIG.getHomes();

        TextFieldWidget newName = addDrawableChild(new TextFieldWidget(textRenderer, START_POINT, 10, 110, 20, Text.of(homes.get(id))));
        newName.setText(homes.get(id));


        addDrawableChild(new ButtonWidget(START_POINT - 2, 70, 114, 20, Text.of("Modify"), (onPress) -> {
            VanillaTweaksGuiMain.CONFIG.modifyHomes(newName.getText(), id);
            client.setScreen(new MainScreen(player, client));
        }));

        addDrawableChild(new ButtonWidget(START_POINT - 2, 92, 114, 20, Text.of("Back"), (onPress) -> client.setScreen(new MainScreen(player, client))));
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        super.render(matrices, mouseX, mouseY, delta);
        String homeId = "home id: " + id.toString();
        drawCenteredText(matrices, textRenderer, homeId, START_POINT + textRenderer.getWidth(homeId) / 2, 42, 0xffffff);
    }
}
