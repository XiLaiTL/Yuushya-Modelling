package com.yuushya.modelling.forge.item;

import com.mojang.blaze3d.vertex.PoseStack;
import com.yuushya.modelling.item.showblocktool.GetBlockStateItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class GetBlockStateItemForge{

    public static Callable getRenderCall(){
        if(Dist.CLIENT.isClient()) return ()->getRender();
        else return ()->null;
    }

    @OnlyIn(Dist.CLIENT)
    public static BlockEntityWithoutLevelRenderer getRender() {
        return new BlockEntityWithoutLevelRenderer() {
            @Override
            public void renderByItem(@NotNull ItemStack stack, ItemTransforms.@NotNull TransformType mode, @NotNull PoseStack matrices, @NotNull MultiBufferSource vertexConsumers, int light, int overlay) {
                GetBlockStateItem.renderByItem(stack, mode, matrices, vertexConsumers, light, overlay);
            }
        };
    }
}
