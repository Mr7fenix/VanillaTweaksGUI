package me.mr7fenix.vanillatweaksgui;

import me.mr7fenix.vanillatweaksgui.util.ModConfig;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VanillaTweaksGuiMain implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("vanilla-tweaks-gui");
	public static ModConfig CONFIG;


	@Override
	public void onInitialize() {
		LOGGER.info("Hello Fabric world!");
		CONFIG = new ModConfig();
		CONFIG.load();
	}
}
