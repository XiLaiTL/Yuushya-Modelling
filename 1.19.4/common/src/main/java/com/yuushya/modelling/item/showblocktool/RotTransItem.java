package com.yuushya.modelling.item.showblocktool;

import org.joml.Vector3f;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;

public class RotTransItem extends PosTransItem{
    public RotTransItem(Properties properties, Integer tipLines) {
        super(properties, tipLines);
    }
    public float getDegreeAfterRotation(float degree,int mode){
        if(mode>0) return degree+22.5f<360f?degree+22.5f:0f;
        else return degree-22.5f>0f?degree-22.5f:360f;
    }
    @Override
    public InteractionResult inMainHandRightClickOnBlock(Player player, BlockState blockState, Level level, BlockPos blockPos, ItemStack handItemStack){
        //右手右键 向后位移
        getTag(handItemStack);
        return translateData(player,blockState,level,blockPos,handItemStack,(transformData)->{
            Vector3f rot=transformData.rot;
            switch (getForm()){
                case 0-> rot.set(getDegreeAfterRotation(rot.x(),1),rot.y(),rot.z());
                case 1-> rot.set(rot.x(),getDegreeAfterRotation(rot.y(),1),rot.z());
                case 2-> rot.set(rot.x(),rot.y(),getDegreeAfterRotation(rot.z(),1));
            }
            player.displayClientMessage(Component.translatable(this.getDescriptionId()+".switch",rot.x(),rot.y(),rot.z()),true);
        });
    }
    @Override
    public InteractionResult inMainHandLeftClickOnBlock(Player player, BlockState blockState, Level level, BlockPos blockPos, ItemStack handItemStack){
        //右手左键 向前位移
        getTag(handItemStack);
        return translateData(player,blockState,level,blockPos,handItemStack,(transformData)->{
            Vector3f rot=transformData.rot;
            switch (getForm()){
                case 0-> rot.set(getDegreeAfterRotation(rot.x(),-1),rot.y(),rot.z());
                case 1-> rot.set(rot.x(),getDegreeAfterRotation(rot.y(),-1),rot.z());
                case 2-> rot.set(rot.x(),rot.y(),getDegreeAfterRotation(rot.z(),-1));
            }
            player.displayClientMessage(Component.translatable(this.getDescriptionId()+".switch",rot.x(),rot.y(),rot.z()),true);
        });
    }

}
