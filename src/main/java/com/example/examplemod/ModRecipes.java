package com.example.examplemod;

import java.util.function.Supplier;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModRecipes {

    public void register(IEventBus bus) {
        RECIPE_SERIALIZERS.register(bus);
        RECIPE_TYPES.register(bus);
    }

    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister
            .create(Registries.RECIPE_SERIALIZER, ExampleMod.MOD_ID);

    public static final Supplier<RecipeSerializer<RightClickBlockRecipe>> RIGHT_CLICK_BLOCK_SERIAL = RECIPE_SERIALIZERS
            .register("right_click_block", RightClickBlockRecipeSerializer::new);

    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Registries.RECIPE_TYPE,
            ExampleMod.MOD_ID);

    public static final Supplier<RecipeType<RightClickBlockRecipe>> RIGHT_CLICK_BLOCK_TYPE = RECIPE_TYPES.register(
            "right_click_block",
            // We need the qualifying generic here due to generics being generics.
            () -> RecipeType.<RightClickBlockRecipe>simple(
                    ResourceLocation.fromNamespaceAndPath(ExampleMod.MOD_ID, "right_click_block")));

}
