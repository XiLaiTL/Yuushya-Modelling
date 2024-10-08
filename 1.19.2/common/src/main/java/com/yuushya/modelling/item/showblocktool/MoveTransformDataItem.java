package com.yuushya.modelling.item.showblocktool;

import com.yuushya.modelling.blockentity.TransformData;
import com.yuushya.modelling.blockentity.showblock.ShowBlock;
import com.yuushya.modelling.blockentity.showblock.ShowBlockEntity;
import com.yuushya.modelling.item.AbstractToolItem;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.AirBlock;
import net.minecraft.world.level.block.state.BlockState;

public class MoveTransformDataItem extends AbstractToolItem {
    private final TransformData transformData=new TransformData();
    public MoveTransformDataItem(Properties properties, Integer tipLines) {
        super(properties, tipLines);
    }

    @Override
    public InteractionResult inMainHandRightClickOnBlock(Player player, BlockState blockStateTarget, Level level, BlockPos blockPos, ItemStack handItemStack){
        //右手右键复制内容，以及清空展示方块内的东西//with main hand right-click can read
        getTag(handItemStack);
        if(blockStateTarget.getBlock() instanceof ShowBlock){
            ShowBlockEntity showBlockEntity = (ShowBlockEntity) level.getBlockEntity(blockPos);
            BlockState blockStateShowBlock =showBlockEntity.getTransFormDataNow().blockState;
            if (!(blockStateShowBlock.getBlock() instanceof AirBlock)){
                transformData.set(showBlockEntity.getTransFormDataNow());
                showBlockEntity.removeTransFormDataNow();
                showBlockEntity.saveChanged();
            }
            else {
                player.displayClientMessage(Component.translatable(this.getDescriptionId()+".mainhand.pass"), true);
                return InteractionResult.PASS;
            }
        }
        else {
            transformData.set();
            transformData.blockState= blockStateTarget;
        }
        setTag(handItemStack);
        player.displayClientMessage(Component.translatable(this.getDescriptionId()+".mainhand.success"),true);
        return InteractionResult.SUCCESS;
    }
    @Override
    public InteractionResult inOffHandRightClickOnBlock(Player player, BlockState blockStateTarget, Level level, BlockPos blockPos, ItemStack handItemStack){
        //左手右键放置状态到展示方块里//with off hand right-click can put all state to showblock
        getTag(handItemStack);
        if(transformData.blockState.getBlock() instanceof AirBlock){
            player.displayClientMessage(Component.translatable(this.getDescriptionId()+".offhand.fail"), true);
            return InteractionResult.SUCCESS;
        }
        if(blockStateTarget.getBlock() instanceof ShowBlock){
            ShowBlockEntity showBlockEntity = (ShowBlockEntity) level.getBlockEntity(blockPos);
            showBlockEntity.getTransFormDataNow().set(transformData);
            showBlockEntity.saveChanged();
            player.displayClientMessage(Component.translatable(this.getDescriptionId()+".offhand.success"), true);
            return InteractionResult.SUCCESS;
        } else {return InteractionResult.PASS;}
    }

    //method for readNbt and writeNbt
    public void getTag(ItemStack itemStack){
        CompoundTag compoundTag = itemStack.getOrCreateTag();
        transformData.load(compoundTag.getCompound("TransformData"));
    }
    public void setTag(ItemStack itemStack){
        CompoundTag transformDataTag=new CompoundTag();
        transformData.saveAdditional(transformDataTag);

        CompoundTag compoundTag = itemStack.getOrCreateTag();
        compoundTag.put("TransformData",transformDataTag);
        itemStack.setTag(compoundTag);
    }

}