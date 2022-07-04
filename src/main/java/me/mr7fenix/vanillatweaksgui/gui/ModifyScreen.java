package me.mr7fenix.vanillatweaksgui.gui;

import me.mr7fenix.vanillatweaksgui.VanillaTweaksGuiMain;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;

public class ModifyScreen extends VanillaTweaksGui {

    private final int id;

    protected ModifyScreen(ClientPlayerEntity player, MinecraftClient client, int id) {
        super(player, client);
        this.id = id;
    }

    @Override
    public void showScreen() {
        TextFieldWidget newName = addDrawableChild(new TextFieldWidget(textRenderer, START_POINT, 10, 110, 20, Text.of(homes.get(id))));
        //TextFieldWidget newId = addDrawableChild(new TextFieldWidget(textRenderer, START_POINT, 42, 110, 20, Text.of(i.toString())));
        newName.setText(homes.get(id));


        addDrawableChild(new ButtonWidget(START_POINT - 2, 70, 114, 20, Text.of("Modify"), (onPress) -> {
            VanillaTweaksGuiMain.CONFIG.modifyHomes(newName.getText(), /*Integer.parseInt(newId.getText())*/ id);
            client.setScreen(this);
        }));

        addDrawableChild(new ButtonWidget(START_POINT - 2, 92, 114, 20, Text.of("Back"), (onPress) -> client.setScreen(this)));
    }
}
