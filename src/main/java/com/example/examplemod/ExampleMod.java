package com.example.examplemod;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(ExampleMod.MOD_ID)
public class ExampleMod {
    public static final String MOD_ID = "examplemod";

    public ExampleMod(IEventBus modEventBus, ModContainer modContainer) {
        ModRecipes.RECIPE_SERIALIZERS.register(modEventBus);
        NeoForge.EVENT_BUS.register(new RecipeEvent());
    }

}
