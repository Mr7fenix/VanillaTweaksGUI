package me.mr7fenix.vanillatweaksgui.gui;

import me.mr7fenix.vanillatweaksgui.VanillaTweaksGuiMain;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;
import java.util.Map;

public class AddScreen extends VanillaTweaksGui {


    public AddScreen(ClientPlayerEntity player, MinecraftClient client) {
        super(player, client);
    }

    @Override
    public void showScreen() {
        String nextID = getFirstFreeId().toString();
        TextFieldWidget id = addDrawableChild(new TextFieldWidget(textRenderer, START_POINT, 42, 110, 20, Text.of(nextID)));
        TextFieldWidget name = addDrawableChild(new TextFieldWidget(textRenderer, START_POINT, 10, 110, 20, Text.of("Name")));
        name.setText("Home " + nextID);
        id.setText(nextID);
        setInitialFocus(name);

        onlyNumberPredicate(id);

        addDrawableChild(new ButtonWidget(START_POINT - 2, 70, 114, 20, Text.of("Add"), (onPress) -> {
            int finalId = Integer.parseInt(id.getText().trim().length() == 0 ? nextID : id.getText());
            VanillaTweaksGuiMain.CONFIG.setHomes(name.getText(), finalId);
            player.sendCommand("trigger sethome set " + finalId);
            client.setScreen(new MainScreen(player, client));
        }));
        addDrawableChild(new ButtonWidget(START_POINT - 2, 92, 114, 20, Text.of("Back"), (onPress) -> client.setScreen(new MainScreen(player, client))));
    }

    private Integer getFirstFreeId() {
        Map<Integer, String> homes = VanillaTweaksGuiMain.CONFIG.getHomes();


        int i = 0;
        for (int key : homes.keySet()) {
            if (i + 1 != key) {
                return i + 1;
            }
            i++;
        }
        return homes.size() + 1;
    }

    void onlyNumberPredicate(TextFieldWidget id){
        id.setTextPredicate((s) -> {
            try {
                return Integer.parseInt(s) > 0;
            } catch (NumberFormatException nfe) {
                return s.trim().length() == 0;
            }
        });
    }
}
