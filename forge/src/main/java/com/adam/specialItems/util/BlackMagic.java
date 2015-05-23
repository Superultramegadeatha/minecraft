package com.adam.specialItems.util;

import java.util.LinkedList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.MovingObjectPosition.MovingObjectType;
import net.minecraft.util.Vec3;
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
	/**
	 * This method traces through the players look vector to a maximum distance and returns 
	 * the block or entity the player was looking at. If the trace does not collide with anything but air or liquid
	 * the last block is returned.
	 * 
	 * @param player The player for whom we are extending their look vector.
	 * @param reach The the maximum distance to trace. 
	 * @return The MovingObjectPosition representing the block or entity.
	 */
	public static MovingObjectPosition getMouseOverBlock(EntityPlayer player, double reach){
        float f = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch);
        float f1 = player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw);
        double d0 = player.prevPosX + (player.posX - player.prevPosX);
        double d1 = player.prevPosY + (player.posY - player.prevPosY) + (double)player.getEyeHeight();
        double d2 = player.prevPosZ + (player.posZ - player.prevPosZ);
        Vec3 vec3 = new Vec3(d0, d1, d2);
        float f2 = MathHelper.cos(-f1 * 0.017453292F - (float)Math.PI);
        float f3 = MathHelper.sin(-f1 * 0.017453292F - (float)Math.PI);
        float f4 = -MathHelper.cos(-f * 0.017453292F);
        float f5 = MathHelper.sin(-f * 0.017453292F);
        float f6 = f3 * f4;
        float f7 = f2 * f4;
        Vec3 vec31 = vec3.addVector((double)f6 * reach, (double)f5 * reach, (double)f7 * reach);
        
        //if no collidable blocks are traced this will return null, and not the last block
        //ex. looking through grass into the sky will return null
        MovingObjectPosition p = player.worldObj.rayTraceBlocks(vec3, vec31, false, true, true);
        //if p is null we return the first block hit that is collideable
		return (p != null)? p:player.worldObj.rayTraceBlocks(vec3, vec31, false, false, true);
	}
	
	/**
	 * Traces through all blocks and entities up to a given distance reach and returns them.
	 * @param player
	 * @param reach
	 * @return
	 */
	public static LinkedList<MovingObjectPosition> getAllPiercedBlocks(EntityPlayer player, double reach){
		LinkedList<MovingObjectPosition> hits = new LinkedList<MovingObjectPosition>();
        MovingObjectPosition mop = null;
		Vec3 lookVec = player.getLookVec();
        float f = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch);
        float f1 = player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw);
        double d0 = player.prevPosX + (player.posX - player.prevPosX);
        double d1 = player.prevPosY + (player.posY - player.prevPosY) + (double)player.getEyeHeight();
        double d2 = player.prevPosZ + (player.posZ - player.prevPosZ);
        Vec3 start = new Vec3(d0, d1, d2);
        Vec3 blockVec = start;
        float f2 = MathHelper.cos(-f1 * 0.017453292F - (float)Math.PI);
        float f3 = MathHelper.sin(-f1 * 0.017453292F - (float)Math.PI);
        float f4 = -MathHelper.cos(-f * 0.017453292F);
        float f5 = MathHelper.sin(-f * 0.017453292F);
        float f6 = f3 * f4;
        float f7 = f2 * f4;
        Vec3 end = start.addVector((double)f6 * reach, (double)f5 * reach, (double)f7 * reach);
		BlockPos block = new BlockPos(start);
		World world = player.worldObj;
		
		for(int i = 0; i < reach; i++){
			blockVec = blockVec.add(lookVec);
			block = new BlockPos(blockVec);
			mop = world.getBlockState(block).getBlock().collisionRayTrace(world, block, start, end);
			if(mop != null)
				hits.add(mop);
		}
 		return hits;
 	}

	public static LinkedList<MovingObjectPosition> getAllPiercedEntities(EntityPlayer player, double reach){
		if (player != null){
            if (player.worldObj != null){
            	LinkedList<MovingObjectPosition> hits = new LinkedList<MovingObjectPosition>();
                Vec3 vec3 = player.getPositionVector();
                Vec3 playerPosVec = new Vec3(vec3.xCoord, vec3.yCoord + player.eyeHeight, vec3.zCoord);
                Vec3 playerLookVec = player.getLook(1.0f);
                Vec3 mouseOverPosVec = playerPosVec.addVector(playerLookVec.xCoord * reach, 
															playerLookVec.yCoord * reach, 
															playerLookVec.zCoord * reach);                
                Entity pointedEntity = null;
                MovingObjectPosition mouseOver = null;
                Vec3 vec33 = null;
                List list = player.worldObj.getEntitiesWithinAABBExcludingEntity(player, 
							player.getEntityBoundingBox().addCoord(playerLookVec.xCoord * reach, 
																   playerLookVec.yCoord * reach, 
																   playerLookVec.zCoord * reach).expand(1,1,1));
                for (int i = 0; i < list.size(); ++i){
                    Entity entity1 = (Entity)list.get(i);
                    System.out.println("Found some shit.");
                    if (entity1.canBeCollidedWith()){
                    	System.out.println("This shit can be collided.");
                        float cbs = entity1.getCollisionBorderSize();
                        AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().expand(cbs, cbs, cbs);
                        MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(playerPosVec, mouseOverPosVec);
                        if (movingobjectposition != null){
                        	System.out.println("Added some shit.");
                        	hits.add(new MovingObjectPosition(entity1, movingobjectposition.hitVec));
                        }
                    }
                }
                return hits;
            }
        }
		return null;
	}
    
    public static void ignite(World world, EntityPlayer player, byte magnitude){
    	LinkedList<MovingObjectPosition> hits = getAllPiercedBlocks(player, magnitude);
    	IBlockState fire = Blocks.fire.getDefaultState();
    	BlockPos pos;
    	Block block;
    	EnumFacing face;
    	int mag = (int) Math.ceil(5.0*magnitude/Byte.MAX_VALUE);
    	int burnTime = 3;
    	System.out.println(magnitude);
    	System.out.println(mag);
    	for(MovingObjectPosition mop: hits){
    		for(int i = 0; i < mag; i++)
		    	world.spawnParticle(EnumParticleTypes.FLAME, 
		    						mop.hitVec.xCoord + world.rand.nextGaussian()*mag/20.0, 
		    						mop.hitVec.yCoord + world.rand.nextGaussian()*mag/20.0,
		    						mop.hitVec.zCoord + world.rand.nextGaussian()*mag/20.0, 
		    						world.rand.nextGaussian()*mag/100.0,  
		    						world.rand.nextGaussian()*mag/100.0, 
		    						world.rand.nextGaussian()*mag/100.0, 0);
    		
			if(mop.typeOfHit == MovingObjectType.BLOCK){
		    	pos = mop.getBlockPos();
				block = world.getBlockState(pos).getBlock();
				face = mop.sideHit;
				switch(face){
				case   UP: 	pos = pos.up();
					break;
				case DOWN:	pos = pos.down();
					break;
				case EAST: 	pos = pos.east();
					break;
				case WEST: 	pos = pos.west();
					break;
				case NORTH: pos = pos.north();
					break;
				case SOUTH: pos = pos.south();
					break;
				default: System.out.println("Unknown EnumFacing.");
					break;
				}
				if(world.isAirBlock(pos) && Blocks.fire.canCatchFire(world, mop.getBlockPos(), face)){
					world.setBlockState(pos, fire);
				}
			}else if(mop.typeOfHit == MovingObjectType.ENTITY){
				mop.entityHit.setFire(mag);
			}
    	}
    	
    	hits = getAllPiercedEntities(player, magnitude);
    	for(MovingObjectPosition mop: hits){
    		if(mop != null && mop.entityHit != null)
    			mop.entityHit.setFire(burnTime * mag);
    	}
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
