package me.mr7fenix.vanillatweaksgui.gui;

import net.minecraft.client.gui.widget.ClickableWidget;


import java.util.*;

public class ListHandler {
    protected final VanillaTweaksGui screen;
    private int actualPage = 1;
    static final int BUTTON_PER_PAGE = 7;
    static List<ClickableWidget> buttons = new ArrayList<>();

    protected ListHandler(VanillaTweaksGui screen) {
        this.screen = screen;
    }

    public void showAll() {
        new HomeButtons(screen).showHome(actualPage);
        new PlayerButtons(screen).showPlayer(1);
    }

    public int turnPage(int i, boolean isHome) {
        int numPag;

        if (isHome) {
            int r = screen.homes.size() - ((screen.homes.size() / BUTTON_PER_PAGE) * BUTTON_PER_PAGE);
            numPag = (screen.homes.size() / BUTTON_PER_PAGE) + r;
        } else {
            int r = getPlayersId().size() - ((getPlayersId().size() / BUTTON_PER_PAGE) * BUTTON_PER_PAGE);
            numPag = (getPlayersId().size() / BUTTON_PER_PAGE) + r;
        }

        if (actualPage + i >= 1 && actualPage + i <= numPag) {
            actualPage += i;
        }

        return actualPage;
    }

    public Map<String, Integer> getPlayersId() {
        Map<String, Integer> playerId = new HashMap<>();

        screen.player.getScoreboard().getAllPlayerScores(screen.player.getScoreboard().getObjective("tpa.pid")).forEach(score -> {
            if (!Objects.equals(score.getPlayerName(), "#last")) {
                playerId.put(score.getPlayerName(), score.getScore());
            }
        });

        playerId.remove(screen.player.getName().getString());
        return playerId;
    }

    static void clearPreviousButtons() {
        buttons.clear();
    }
}
