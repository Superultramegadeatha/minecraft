package com.adam.myeffects;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.google.common.collect.Multimap;

public class SupersRightHand extends Item{
	
    private float attackDamage;
    private static final String __OBFID = "CL_00000072";
    public static final String name = "Super's Right Hand";

    public SupersRightHand()
    {
        this.maxStackSize = 1;
        this.setMaxDamage(384);
        this.setCreativeTab(CreativeTabs.tabCombat);
        this.attackDamage = 5.0F;
    }

    public float getDamageVsEntity(){
        return 1.0f;
    }

    public float getStrVsBlock(ItemStack stack, Block block){
    	return 30.0F;
    }

    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker){
    	if(attacker.getHealth() == attacker.getMaxHealth()){
	    	AttributeModifier modify = new AttributeModifier("generic.maxHealth", target.getHealth(), 0);
	    	attacker.getEntityAttribute(SharedMonsterAttributes.maxHealth).applyModifier(modify);
    	}
	    attacker.setHealth(attacker.getHealth() + target.getHealth());
    	target.setHealth(0);;
        return true;
    }

    public boolean onBlockDestroyed(ItemStack stack, World worldIn, Block blockIn, BlockPos pos, EntityLivingBase playerIn){
        return true;
    }

    @SideOnly(Side.CLIENT)
    public boolean isFull3D(){
        return true;
    }


    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityPlayer playerIn, int timeLeft){
        //System.out.println("Player stopped using item.");
    }

    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityPlayer playerIn){
    	//System.out.println("Player finished using item.");
        return stack;
    }

    public EnumAction getItemUseAction(ItemStack stack){
        return EnumAction.BLOCK;
    }
    
    public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn){
        playerIn.setItemInUse(itemStackIn, this.getMaxItemUseDuration(itemStackIn));
        //autoarrow = new AutoArrow(worldIn, playerIn, 3f, 8f);
        //bats(worldIn, playerIn);
    	System.out.println("onItemRightClick world obj: " + worldIn.isRemote);
    	System.out.println("onItemRightClick player.worldObj: " + playerIn.worldObj.isRemote);
    	System.out.println("Objects are the same: " + worldIn.equals(playerIn.worldObj));
        com.adam.myeffects.BlackMagic.ignite(worldIn, playerIn);
        return itemStackIn;
    }
    
    public boolean canHarvestBlock(Block blockIn){
        return true;
    }

    public int getItemEnchantability(){
        return 0;
    }

    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair){
        return false;
    }
    public int getMaxItemUseDuration(ItemStack stack){
        return 72000;
    }
    public Multimap getItemAttributeModifiers(){
        Multimap multimap = super.getItemAttributeModifiers();
        multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(itemModifierUUID, "Weapon modifier", (double)this.attackDamage, 0));
        return multimap;
    }
}