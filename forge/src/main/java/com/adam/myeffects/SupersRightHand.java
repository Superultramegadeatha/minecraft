package com.adam.myeffects;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.adam.supermod.seras.BlackMagic;
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
        explode2(worldIn, playerIn);
        return itemStackIn;
    }
    
    private void meteorShower(World world, EntityPlayer player){
    	//only squares for amount pls.
    	int amount = 100;
    	int square = (int) Math.sqrt(amount);
    	EntityLargeFireball[] meteorShower = new EntityLargeFireball[amount];
    	double x = player.posX + player.getLookVec().xCoord*amount - square;
    	double y = player.posY + player.getLookVec().yCoord*amount;
    	double z = player.posZ + player.getLookVec().zCoord*amount - square;
    	for(int i = 0; i < square; i++){	
    		for(int j = 0; j < square; j++){
    			for(int k = 0; k < square; k++){
		    		meteorShower[i] = new EntityLargeFireball(world,
		    							x+i*2,
		    							y+k*2,
		    							z+j*2,
		    							0,-1,0);
		    		world.spawnEntityInWorld(meteorShower[i]);
    			}
    		}
    	}
    }
    
    private void explode(World world, EntityPlayer player){
    	//distance from player
    	int distance = 10;
    	EntityLargeFireball[] meteorShower = new EntityLargeFireball[6];
    	double x = player.posX + player.getLookVec().xCoord*distance;
    	double y = player.posY + player.getLookVec().yCoord*distance;
    	double z = player.posZ + player.getLookVec().zCoord*distance;
    	
    	meteorShower[0] = new EntityLargeFireball(world,x,y+10,z,0,-1,0);
    	meteorShower[1] = new EntityLargeFireball(world,x,y-10,z,0,1,0);
    	meteorShower[2] = new EntityLargeFireball(world,x+10,y,z,-1,0,0);
    	meteorShower[3] = new EntityLargeFireball(world,x-10,y,z,1,0,0);
    	meteorShower[4] = new EntityLargeFireball(world,x,y,z+10,0,0,-1);
    	meteorShower[5] = new EntityLargeFireball(world,x,y,z-10,0,0,1);

    	for(EntityLargeFireball fireball:meteorShower){
        	world.spawnEntityInWorld(fireball);
    	}
    }

    private void explode2(World world, EntityPlayer player){
    	BlackMagic magic = new BlackMagic();
    	//distance from player
    	int distance = 10;
    	float explosionPower = 5f;
    	double x = player.posX + player.getLookVec().xCoord*distance;
    	double y = player.posY + player.getLookVec().yCoord*distance;
    	double z = player.posZ + player.getLookVec().zCoord*distance;
    	BlockPos p = magic.getMouseOverAll(player,500).getBlockPos();
    	x = p.getX();
    	y = p.getY();
    	z = p.getZ();
        world.newExplosion((Entity)null, 
        					x,y,z, 
        					explosionPower, true, true);
    }
    
    private void ignite(World world, EntityPlayer player){
    	BlackMagic magic = new BlackMagic();
    	//distance from player
    	int distance = 10;
    	float explosionPower = 5f;
    	double x = player.posX + player.getLookVec().xCoord*distance;
    	double y = player.posY + player.getLookVec().yCoord*distance;
    	double z = player.posZ + player.getLookVec().zCoord*distance;
    	BlockPos p = player.rayTrace(500, player.getEyeHeight()).getBlockPos();
    	Entity e = magic.getMouseOverEntityLiving(player,500);
    	if(e != null)
    		e.setFire(100);
        /*world.newExplosion((Entity)null, 
        					p.getX(), 
        					p.getY(), 
        					p.getZ(), 
        					explosionPower, true, true);*/
    }
    
    //bug where static bats are left at spawn point
    //static bats are removed after leaving and entering the game
    //this is probably due to asynchronous call of spawnEntityInWorld
    private void bats(World world, EntityPlayer player){
 
    }
    
    private void dragon(World world, EntityPlayer player){
       	int amount = 1;    	
    	EntityDragon bat = new EntityDragon(world);
    	bat.setPosition(player.posX, player.posY+.64, player.posZ);
        for(int i = 0; i < amount; i++){
        	//colony[i].setIsBatHanging(false);
        	world.spawnEntityInWorld(bat);
        	//colony[i].spawnExplosionParticle();
        }
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