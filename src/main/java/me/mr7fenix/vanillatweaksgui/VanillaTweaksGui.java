package me.mr7fenix.vanillatweaksgui;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.*;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.*;

public class VanillaTweaksGui extends Screen {

    Map<String, List<ClickableWidget>> widgets;
    Map<Integer, String> homes;
    Map<String, Integer> playerId;
    private ClientPlayerEntity player;
    private MinecraftClient client;
    private int actualPage = 1;
    final int START_POINT = 10;
    final int BUTTON_PER_PAGE = 7;

    protected VanillaTweaksGui(ClientPlayerEntity player, MinecraftClient client) {
        super(Text.of("Mi piace la banana"));
        this.player = player;
        this.client = client;
        playerId = getPlayersId();
    }


    @Override
    protected void init() {
        client.keyboard.setRepeatEvents(true);
        homes = ExampleMod.CONFIG.getHomes();
        widgets = new HashMap<>();

        mainPage();
        setHomeScreen();

        show("main");
        showHome(actualPage);
        showPlayer(1);
    }

    @Override
    public void tick() {
        for (List<ClickableWidget> list : widgets.values()) {
            for (ClickableWidget widget : list) {
                if (widget instanceof TextFieldWidget t) {
                    t.tick();
                }
            }
        }
    }

    private void goHome(int i) {
        player.sendCommand("trigger home set " + i);
    }

    private void goPlayer(int i) {
        player.sendCommand("trigger tpa set " + i);
    }

    private void setFocusedElement(String group) {
        for (ClickableWidget widget : widgets.get(group)) {
            if (widget instanceof TextFieldWidget t) {
                focusOn(t);
                t.changeFocus(false);
                t.setTextFieldFocused(true);
                return;
            }
        }

    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (super.keyPressed(keyCode, scanCode, modifiers)) return true;
        return getFocused() != null && getFocused().keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean charTyped(char chr, int modifiers) {
        return getFocused() != null && getFocused().charTyped(chr, modifiers);
    }

    private void mainPage() {
        widgets.put("main", new ArrayList<>(homes.size()));
        ButtonWidget addButton = addDrawableChild(new ButtonWidget(START_POINT, height - 52, 110, 20, Text.of("Aggiungi"), (onPress) -> {
            show("setHome");
            setFocusedElement("setHome");
        }));

        ButtonWidget exitButton = addDrawableChild(new ButtonWidget(START_POINT, height - 30, 110, 20, Text.of("Esci"), (onPress) -> {
            client.setScreen(null);
        }));

        ButtonWidget backButton = addDrawableChild(new ButtonWidget(300, 10, 110, 20, Text.of("Back"), (onPress) -> {
            player.sendCommand("trigger back");
            client.setScreen(null);
        }));

        ButtonWidget spawnButton = addDrawableChild(new ButtonWidget(300, 32, 110, 20, Text.of("Spawn"), (onPress) -> {
            player.sendCommand("trigger spawn");
            client.setScreen(null);
        }));

        ButtonWidget previousButton = addDrawableChild(new PageTurnWidget(START_POINT, 162, false, button -> changePage(-1), true));
        ButtonWidget nextButton = addDrawableChild(new PageTurnWidget(START_POINT + 86, 162, true, button -> changePage(1), true));

        widgets.get("main").add(addButton);
        widgets.get("main").add(exitButton);
        widgets.get("main").add(nextButton);
        widgets.get("main").add(previousButton);
        widgets.get("main").add(backButton);
        widgets.get("main").add(spawnButton);

    }

    private void changePage(int i) {
        int r = homes.size() - ((homes.size() / BUTTON_PER_PAGE) * BUTTON_PER_PAGE);
        int numPag = (homes.size() / BUTTON_PER_PAGE) + r;


        if (actualPage + i >= 1 && actualPage + i <= numPag) actualPage += i;
        showHome(actualPage);
    }

    private void show(String group) {
        hideAll();

        for (ClickableWidget widget : widgets.get(group)) {
            widget.visible = true;
        }
    }

    private void showHome(int page) {
        clearPreviousButtons("homes");
        int distance = 10;

        int i = 0;
        int j = BUTTON_PER_PAGE;
        for (Map.Entry<Integer, String> entry : homes.entrySet()) {
            if (i >= BUTTON_PER_PAGE) {
                break;
            }

            if (j == BUTTON_PER_PAGE * page) {


                ButtonWidget homeButton = addDrawableChild(new ButtonWidget(START_POINT, distance, 110, 20, Text.of(entry.getValue()), (onPress) -> {
                    goHome(entry.getKey());
                    client.setScreen(null);
                }));

                ButtonWidget deleteButton = addDrawableChild(new TexturedButtonWidget(START_POINT + 120, distance, 20, 20, 0, 0, 20, new Identifier("vanilla-tweaks-gui:textures/gui/bin.png"), 20, 20, (onPress) -> {
                    ExampleMod.CONFIG.removeHome(entry.getKey());
                    client.setScreen(this);
                    //TODO: Add a confirmation dialog
                }));

               // ButtonWidget modifyButton = addDrawableChild(new TexturedButtonWidget(START_POINT + 120, distance, 20, 20, 0, 0, 20, new Identifier("vanilla-tweaks-gui:textures/gui/modify.png"), 20, 20, (onPress) -> {
               //     TODO: Add modify home screen
               // }));
                i++;

                distance += 22;

                widgets.get("homes").add(homeButton);
                widgets.get("homes").add(deleteButton);
                //widgets.get("homes").add(modifyButton);
            } else j++;
        }
    }

    private void clearPreviousButtons(String group) {
        List<ClickableWidget> previousButtons = widgets.computeIfAbsent(group, k -> new ArrayList<>(BUTTON_PER_PAGE * 3));
        for (ClickableWidget previous : previousButtons) {
            remove(previous);
        }
    }

    private void showPlayer(int page) {
        clearPreviousButtons("player");
        int distance = 10;

        int i = 0;
        int j = BUTTON_PER_PAGE;
        for (Map.Entry<String, Integer> entry : playerId.entrySet()) {
            if (i >= BUTTON_PER_PAGE) {
                break;
            }

            if (j == BUTTON_PER_PAGE * page) {


                ButtonWidget homeButton = addDrawableChild(new ButtonWidget(START_POINT + 170, distance, 110, 20, Text.of(entry.getKey()), (onPress) -> {
                    goPlayer(entry.getValue());
                    client.setScreen(null);
                }));
                i++;

                distance += 22;

                widgets.get("player").add(homeButton);
            } else j++;
        }
    }


    private void hideAll() {
        widgets.values().stream().flatMap(List::stream).forEach(widget -> widget.visible = false);
    }

    private void setHomeScreen() {
        String nextID = getFirstFreeId().toString();
        TextFieldWidget name = addDrawableChild(new TextFieldWidget(textRenderer, START_POINT, 10, 110, 20, Text.of("Nome")));
        TextFieldWidget id = addDrawableChild(new TextFieldWidget(textRenderer, START_POINT, 42, 110, 20, Text.of(nextID)));
        name.setText("Home " + nextID);
        id.setText(nextID);

        id.setTextPredicate((s) -> {
            try {
                return Integer.parseInt(s) > 0;
            } catch (NumberFormatException nfe) {
                return s.trim().length() == 0;
            }
        });

        ButtonWidget buttonAdd = addDrawableChild(new ButtonWidget(START_POINT - 2, 70, 114, 20, Text.of("Aggiungi"), (onPress) -> {
            int finalId = Integer.parseInt(id.getText().trim().length() == 0 ? nextID : id.getText());
            ExampleMod.CONFIG.setHomes(name.getText(), finalId);
            player.sendCommand("trigger sethome set " + finalId);
            client.setScreen(this);
        }));
        ButtonWidget buttonBack = addDrawableChild(new ButtonWidget(START_POINT - 2, 92, 114, 20, Text.of("Indietro"), (onPress) -> {
            client.setScreen(this);
        }));


        widgets.put("setHome", Arrays.asList(name, id, buttonAdd, buttonBack));
    }

    private Integer getFirstFreeId() {
        int i = 0;
        for (int key : homes.keySet()) {
            if (i + 1 != key) {
                return i + 1;
            }
            i++;
        }
        return homes.size() + 1;
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    public Map<String, Integer> getPlayersId() {
        Map<String, Integer> playerId = new HashMap<>();

        player.getScoreboard().getAllPlayerScores(player.getScoreboard().getObjective("tpa.pid")).forEach(score -> {
            if (!Objects.equals(score.getPlayerName(), "#last")) {
                playerId.put(score.getPlayerName(), score.getScore());
            }
        });

        playerId.remove(player.getName().getString());
        return playerId;
    }
}
