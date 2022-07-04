package me.mr7fenix.vanillatweaksgui.util;

import me.lortseam.completeconfig.api.ConfigEntry;
import me.lortseam.completeconfig.data.Config;
import me.lortseam.completeconfig.data.ConfigOptions;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class ModConfig extends Config {
    @ConfigEntry(comment = "Numero case")
    private Map<Integer, String> homes = new HashMap<>();

    public ModConfig() {
        super(ConfigOptions.mod("vanilla-tweaks-gui"));
    }

    public Map<Integer, String> getHomes() {
        return new TreeMap<>(homes);
    }

    public void setHomes(String name, int i) {
        homes.put(i, name);
        save();
    }

    public void modifyHomes(String name, int i) {
        homes.put(i, name);
        save();
    }

    public void removeHome(Integer key) {
        homes.remove(key);
        save();
    }
}
