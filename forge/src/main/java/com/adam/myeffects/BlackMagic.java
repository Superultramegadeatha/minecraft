package com.adam.myeffects;

import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class BlackMagic {
	/*
    public void meteorShower(World world, EntityPlayer player){
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
    
    public void explode(World world, EntityPlayer player){
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
    
    public static void explode2(World world, EntityPlayer player){
    	//distance from player
    	int distance = 10;
    	float explosionPower = 5f;
    	double x = player.posX + player.getLookVec().xCoord*distance;
    	double y = player.posY + player.getLookVec().yCoord*distance;
    	double z = player.posZ + player.getLookVec().zCoord*distance;
    	MovingObjectPosition p = com.adam.supermod.seras.BlackMagic.getMouseOverAll(player,500);
    	if(p != null){
	    	x = p.hitVec.xCoord;
	    	y = p.hitVec.yCoord;
	    	z = p.hitVec.zCoord;
	        world.newExplosion((Entity)null, 
	        					x,y,z, 
	        					explosionPower, true, true);
    	}
    }*/
    
    public static void ignite(World world, EntityPlayer player){
    	//distance from player
    	int distance = 10;
    	float explosionPower = 5f;
    	double x = player.posX + player.getLookVec().xCoord*distance;
    	double y = player.posY + player.getLookVec().yCoord*distance;
    	double z = player.posZ + player.getLookVec().zCoord*distance;
    	Entity e = com.adam.supermod.seras.BlackMagic.getMouseOverEntityLiving(player,500);
    	System.out.println("Ignite world obj: " + world.isRemote);
    	System.out.println("Ignite player.worldObj: " + player.worldObj.isRemote);
    	System.out.println("Objects are the same: " + world.equals(player.worldObj));
    	
    	if(e != null)
    		e.setFire(100);
    }
    
    /*
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
    }*/
}
