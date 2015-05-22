package com.adam.myeffects;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class AutoArrow extends Item implements Runnable{

	private World world;
	private EntityPlayer player;
	private float velocity;
	private float inaccuracy;
	private boolean running;
	
	public AutoArrow(World worldIn, EntityPlayer playerIn, float f, float g) {
		world = worldIn;
		player = playerIn;
		velocity = f;
		inaccuracy = g;
	}

	@Override
	public void run() {
		fireArrow(world,player,velocity,inaccuracy);
	}
	
	public void fireArrow(World worldIn, EntityPlayer playerIn, float speedIn, float inaccuracyIn) {
        EntityArrow entityarrow = new EntityArrow(worldIn, playerIn, speedIn);
        entityarrow.setThrowableHeading(entityarrow.motionX, entityarrow.motionY, entityarrow.motionZ, speedIn, inaccuracyIn);
        entityarrow.setDamage(10.0);
        entityarrow.canBePickedUp = 0;
        worldIn.playSoundAtEntity(playerIn, "random.bow", 2.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F));
        if (!worldIn.isRemote){
            worldIn.spawnEntityInWorld(entityarrow);
        }
	}
}
