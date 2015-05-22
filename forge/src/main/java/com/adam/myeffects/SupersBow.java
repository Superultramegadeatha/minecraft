package com.adam.myeffects;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

public class SupersBow extends Item
{
    public static final String[] bowPullIconNameArray = new String[] {"pulling_0", "pulling_1", "pulling_2"};
    private static final String __OBFID = "CL_00001777";
    public static final String name = "Super's Right Hand";
    public SupersBow()
    {
        this.maxStackSize = 1;
        this.setMaxDamage(384);
        this.setCreativeTab(CreativeTabs.tabCombat);
    }

    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityPlayer playerIn, int timeLeft){
        //System.out.println("Player stopped using item.");
    }

    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityPlayer playerIn){
    	//System.out.println("Player finished using item.");
        return stack;
    }

    public EnumAction getItemUseAction(ItemStack stack){
        return EnumAction.BOW;
    }
    
    public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn){
        playerIn.setItemInUse(itemStackIn, this.getMaxItemUseDuration(itemStackIn));
        return itemStackIn;
    }
    public int getMaxItemUseDuration(ItemStack stack){
        return 72000;
    }
    public int getItemEnchantability(){
        return 0;
    }
}