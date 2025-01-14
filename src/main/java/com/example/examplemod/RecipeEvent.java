package com.example.examplemod;

import java.util.Optional;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.UseItemOnBlockEvent;

public class RecipeEvent {
    @SubscribeEvent
    public static void useItemOnBlock(UseItemOnBlockEvent event) {
        // Skip if we are not in the block-dictated phase of the event. See the event's javadocs for details.
        if (event.getUsePhase() != UseItemOnBlockEvent.UsePhase.BLOCK) return;
        // Get the parameters we need.
        UseOnContext context = event.getUseOnContext();
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState blockState = level.getBlockState(pos);
        ItemStack itemStack = context.getItemInHand();
        RecipeManager recipes = level.getRecipeManager();
        // Create an input and query the recipe.
        RightClickBlockInput input = new RightClickBlockInput(blockState, itemStack);
        Optional<RecipeHolder<? extends Recipe<CraftingInput>>> optional = recipes.getRecipeFor(
                
                ModRecipes.RIGHT_CLICK_BLOCK_TYPE.get(),
                input,
                level
        );
        ItemStack result = optional
                .map(RecipeHolder::value)
                .map(e -> e.assemble(input, level.registryAccess()))
                .orElse(ItemStack.EMPTY);
        // If there is a result, break the block and drop the result in the world.
        if (!result.isEmpty()) {
            level.removeBlock(pos, false);
            // If the level is not a server level, don't spawn the entity.
            if (!level.isClientSide()) {
                ItemEntity entity = new ItemEntity(level,
                        // Center of pos.
                        pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                        result);
                level.addFreshEntity(entity);
            }
            // Cancel the event to stop the interaction pipeline.
            event.cancelWithResult(ItemInteractionResult.sidedSuccess(level.isClientSide));
        }
    }
}
