package com.adam.supermod.seras;

import java.util.List;
import java.util.Random;

import com.adam.supermod.networking.AbilityMessage;
import com.adam.supermod.proxy.CommonProxy;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


//not always facing an entity after teleporting to it
/**
 * 
 * @author Super
 *
 * <br>This is a static "helper" class to hold some functions.
 */
public class BlackMagic{	
	

    /***************************************************/
    /**Methods that may be used Client or Server side.**/
    /***************************************************/	
	
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
	 * Code from {@link net.minecraft.client.renderer.EntityRenderer#getMouseOver()} was modified
	 * to return the block or entity the player is looking at for any given distance.
	 * @see net.minecraft.client.renderer.EntityRenderer#getMouseOver(float)
	 * @param player The player for whom we are seeing through.
	 * @param reach The maximum distance to trace.
	 * @return the MovingObjectPosition representing the block or entity.
	 */
    public static MovingObjectPosition getMouseOverAll(EntityPlayer player, double reach){
        if (player != null){
            if (player.worldObj != null){
                Entity pointedEntity = null;
                MovingObjectPosition mouseOver = getMouseOverBlock(player, reach);
                //d1 is the distance from the mouseover to the player
                //but is initialized as the maximum distance to trace over
                double d1 = reach;
                Vec3 vec3 = player.getPositionVector();
                Vec3 playerPosVec = new Vec3(vec3.xCoord, vec3.yCoord + player.eyeHeight, vec3.zCoord);
                Vec3 playerLookVec = player.getLook(1.0f);
                Vec3 vec33 = null;

                if (mouseOver != null){
                    d1 = mouseOver.hitVec.distanceTo(playerPosVec);
                }

                Vec3 mouseOverPosVec = playerPosVec.addVector(playerLookVec.xCoord * d1, 
                									playerLookVec.yCoord * d1, 
                									playerLookVec.zCoord * d1);
                float f1 = 1.0F;
                List list = player.worldObj.getEntitiesWithinAABBExcludingEntity(player, 
							player.getEntityBoundingBox().addCoord(playerLookVec.xCoord * d1, 
																   playerLookVec.yCoord * d1, 
																   playerLookVec.zCoord * d1).expand((double)f1, 
									 																 (double)f1, 
									 															 	 (double)f1));
                double d2 = d1;
                for (int i = 0; i < list.size(); ++i){
                    Entity entity1 = (Entity)list.get(i);
                    
                    if (entity1.canBeCollidedWith()){
                    	
                    	//i guess the collision box is different than the bounding box
                        float f2 = entity1.getCollisionBorderSize();
                        AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().expand((double)f2, 
                        																	(double)f2, 
                        																	(double)f2);
                        MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(playerPosVec, mouseOverPosVec);

                        if (axisalignedbb.isVecInside(playerPosVec)){
                            if (0.0D < d2 || d2 == 0.0D){
                                pointedEntity = entity1;
                                vec33 = movingobjectposition == null ? playerPosVec : movingobjectposition.hitVec;
                                d2 = 0.0D;
                            }
                        }
                        else if (movingobjectposition != null){
                            double d3 = playerPosVec.distanceTo(movingobjectposition.hitVec);

                            if (d3 < d2 || d2 == 0.0D){
                                if (entity1 == player.ridingEntity && !player.canRiderInteract()){
                                    if (d2 == 0.0D){
                                        pointedEntity = entity1;
                                        vec33 = movingobjectposition.hitVec;
                                    }
                                }else{
                                    pointedEntity = entity1;
                                    vec33 = movingobjectposition.hitVec;
                                    d2 = d3;
                                }
                            }
                        }
                    }
                }

                if (pointedEntity != null && (d2 < d1 || mouseOver == null)){
                    mouseOver = new MovingObjectPosition(pointedEntity, vec33);
                }
                return mouseOver;
            }
        }
		return null;
    }
    
    /**
     * A helper method to return only a living entity or null.
     * @see com.adam.supermod.seras.BlackMagic#getMouseOverAll(EntityPlayer, double)
	 * @param player The player for whom we are seeing through.
	 * @param reach The maximum distance to trace.
	 * @return The EntityLivingBase representing the living entity.
     */
    public static EntityLivingBase getMouseOverEntityLiving(EntityPlayer player, double reach){
    	MovingObjectPosition p = getMouseOverAll(player, reach);
    	if(p != null && p.entityHit != null && p.entityHit instanceof EntityLivingBase){
    		return (EntityLivingBase) p.entityHit;
    	}else
    		return null;
    }

	/**
	 * Spawns particles to give the teleport ability some visual effects.
	 * This method should only ever be used by the Client or Integrated Server as
	 * particles should only ever be spawned Client side.
	 * 
	 * @param player The player to spawn particles around.
	 * @see net.minecraft.world.World#spawnParticle(EnumParticleTypes, double, double, double, double, double, double, int)
	 */
    public static void spawnTeleportParticles(EntityPlayer player){
		for(int i = 0; i < 32; i++)
			player.worldObj.spawnParticle(EnumParticleTypes.SPELL_WITCH, 
											player.posX + player.worldObj.rand.nextDouble() * 2.0 - 1.0, 
											player.posY + player.worldObj.rand.nextDouble() * 2.0, 
											player.posZ+ player.worldObj.rand.nextDouble() * 2.0 - 1.0,
											player.worldObj.rand.nextGaussian()/2.0, 
											player.worldObj.rand.nextGaussian(), 
											player.worldObj.rand.nextGaussian()/2.0, 
											new int[0]);
    }
	
    /**
     * Causes a player to teleport to where they are looking, limited by a given maximum distance.
     * If they are looking at a living entity, this causes them to teleport behind that entity 
     * (working on turning the player to face the entity).
     * @param player The player to teleport.
     * @param reach The maximum distance to teleport.
     * @see com.adam.supermod.seras.BlackMagic#teleportToEntity(EntityPlayer, Entity)
     * @see com.adam.supermod.seras.BlackMagic#teleportToBlock(EntityPlayer, MovingObjectPosition)
     */
	public static void teleportToLook(EntityPlayer player, double reach){
		MovingObjectPosition p = getMouseOverAll(player, reach);
		if(p != null){
	    	if(p.entityHit != null && p.entityHit instanceof EntityLivingBase)
	    		teleportToEntity(player, p.entityHit);
			else
				teleportToBlock(player, p);
		}
	}
	
	/**
	 * Causes a player to teleport to the other side of a contiguous mass,
	 * or not at all if there are no voids within the trace to teleport to.
	 * @param player The player to teleport.
	 * @param reach The maximum distance to teleport.
	 * @see com.adam.supermod.seras.BlackMagic#getMouseOverBlock(EntityPlayer, double)
	 * @see com.adam.supermod.seras.BlackMagic#isTeleportSafe(World, BlockPos)
	 * @see com.adam.supermod.seras.BlackMagic#teleportToBlock(EntityPlayer, MovingObjectPosition)
	 */
	public static void teleportThroughBlock(EntityPlayer player, double reach){
		Vec3 lookVec = player.getLookVec();
		Vec3 playerVec = player.getPositionVector();
		Vec3 blockVec = getMouseOverBlock(player, reach).hitVec;
		BlockPos block = new BlockPos(blockVec);
		
		do{
			blockVec = blockVec.add(lookVec);
			block = new BlockPos(blockVec);
		}while(!isTeleportSafe(player.worldObj, block) && blockVec.distanceTo(playerVec) < reach);

		if(blockVec.distanceTo(playerVec) < reach)
			teleportToBlock(player, new MovingObjectPosition(blockVec, EnumFacing.UP, block));
	}
	
	/**
	 * 
	 * @param player
	 * @param e
	 */
	private static void teleportToEntity(EntityPlayer player, Entity e){
		double r = e.rotationYaw*Math.PI/180.0;
		double x = e.posX + Math.sin(r);
		double y = e.posY;
		double z = e.posZ - Math.cos(r);
		
		if(isTeleportSafe(player.worldObj, x, y, z)){
			player.setRotationYawHead(e.rotationYaw);
			AbilityMessage amts = new AbilityMessage(player.getUniqueID(), 
														AbilityMessage.Ability.TELEPORT,
														x,y,z);
			CommonProxy.simpleNetworkWrapper.sendToServer(amts);
		}
	}
	
	private static void teleportToBlock(EntityPlayer player, MovingObjectPosition p){
		BlockPos look = p.getBlockPos();
		double x = look.getX() + 0.5;
		double y = look.getY();
		double z = look.getZ() + 0.5;
		EnumFacing e = p.sideHit;
		
		if(e == EnumFacing.UP){
			if(player.worldObj.getBlockState(look).getBlock().getMaterial().blocksMovement())
				y += player.worldObj.getBlockState(look).getBlock().getBlockBoundsMaxY();
		}else if(e == EnumFacing.DOWN){
			y -= 2.0;
		}else if(e == EnumFacing.NORTH){
			z--;
		}else if(e == EnumFacing.SOUTH){
			z++;
		}else if(e == EnumFacing.EAST){
			x++;
		}else if(e == EnumFacing.WEST){
			x--;
		}else{
			return;
		}
		
		if(isTeleportSafe(player.worldObj, x, y, z)){
			AbilityMessage amts = new AbilityMessage(player.getUniqueID(), 
														AbilityMessage.Ability.TELEPORT,
														x,y,z);
			CommonProxy.simpleNetworkWrapper.sendToServer(amts);
			//instantTransmission(player, x, y, z);
		}
	}
	
	public static boolean isTeleportSafe(World world, double x, double y, double z){
		BlockPos block = new BlockPos(x, y, z);
		
		return !world.getBlockState(block).getBlock().getMaterial().blocksMovement()
			&& !world.getBlockState(block.up()).getBlock().getMaterial().blocksMovement()
			&& world.getChunkFromBlockCoords(block).isLoaded()
			&& y > 0;
	}
	
	public static boolean isTeleportSafe(World world, BlockPos bp){
		return !world.getBlockState(bp).getBlock().getMaterial().blocksMovement()
			&& !world.getBlockState(bp.up()).getBlock().getMaterial().blocksMovement()
			&& world.getChunkFromBlockCoords(bp).isLoaded()
			&& bp.getY() > 0;
	}
    
	
    /***************************************************/
    /**Methods that should only be used by the server.**/
    /***************************************************/
	
    @SideOnly(Side.SERVER)
    public static void instantTransmission(EntityPlayer player, Vec3 posVec){
    	int sendRadius = 100;
		AbilityMessage amts = new AbilityMessage(player.getUniqueID(), 
													AbilityMessage.Ability.TELEPORT);
		
    	player.worldObj.playSoundToNearExcept(player, "supermod:teleport", 1, 1);
		CommonProxy.simpleNetworkWrapper.sendToAllAround(amts, new TargetPoint(player.dimension, 
															player.posX, player.posY, player.posZ, sendRadius));
		player.setPositionAndUpdate(posVec.xCoord, posVec.yCoord, posVec.zCoord);
		player.fallDistance = 0;
		player.worldObj.playSoundAtEntity(player, "supermod:teleport", 1, 1);
		CommonProxy.simpleNetworkWrapper.sendToAllAround(amts, new TargetPoint(player.dimension, 
															player.posX, player.posY, player.posZ, sendRadius));
    }
    
    @SideOnly(Side.SERVER)
    public static void instantTransmission(EntityPlayer player,double x, double y, double z){
    	int sendRadius = 100;
		AbilityMessage amts = new AbilityMessage(player.getUniqueID(), 
													AbilityMessage.Ability.TELEPORT);
		
    	player.worldObj.playSoundToNearExcept(player, "supermod:teleport", 1, 1);
    	System.out.println("Server: Sending ability message to client.");
		CommonProxy.simpleNetworkWrapper.sendToAllAround(amts, new TargetPoint(player.dimension, 
															player.posX, player.posY, player.posZ, sendRadius));
		player.setPositionAndUpdate(x, y, z);
		player.fallDistance = 0;
		player.worldObj.playSoundAtEntity(player, "supermod:teleport", 1, 1);
		CommonProxy.simpleNetworkWrapper.sendToAllAround(amts, new TargetPoint(player.dimension, 
															player.posX, player.posY, player.posZ, sendRadius));
	
    }
    
    @SideOnly(Side.SERVER)
    public static void fireArrow(EntityPlayer player){
    	if(Seras.isMaster(player))
    		fireArrow(player, Seras.teleportDistance);
    }
    
    @SideOnly(Side.SERVER)
	public static void fireArrow(EntityPlayer player, double reach){
		if(player.worldObj.isRemote)
			return;
		
		float speed = 3f;
		float inaccuracy = 16f;
		Entity e = getMouseOverEntityLiving(player, reach);
		if(e != null){
			double randmult = player.getDistanceToEntity(e);
			double randx = player.worldObj.rand.nextDouble() * randmult - randmult/2.0;
			double randy = player.worldObj.rand.nextDouble() * randmult - randmult/2.0;
			double randz = player.worldObj.rand.nextDouble() * randmult - randmult/2.0;
			EntityArrow arrow = new EntityArrow(player.worldObj, 
												e.posX + randx, 
												e.posY  + randy, 
												e.posZ + randz);
			arrow.setThrowableHeading(-randx, -randy, -randz, speed, 0.0f);
			arrow.canBePickedUp = 0;
			arrow.setFire(5);
	        player.worldObj.playSoundAtEntity(player, "random.bow", 2.0F, (new Random()).nextFloat() * 0.4f + 1.2F);
			player.worldObj.spawnEntityInWorld(arrow);
		}
	}
	
	//currently being used for testing
	public static void familiars(EntityPlayer player){
		System.out.println(Math.sin(player.getRotationYawHead()*Math.PI/180.0) + " " + Math.cos(player.getRotationYawHead()*Math.PI/180.0));
    }
	
	public static void moveFast(EntityPlayer player, AttributeModifier sprintingSpeedBoostModifier){
		IAttributeInstance playerSpeedAttribute = player.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
		if(!playerSpeedAttribute.func_180374_a(sprintingSpeedBoostModifier)){
			playerSpeedAttribute.applyModifier(sprintingSpeedBoostModifier);
		}else if(playerSpeedAttribute.func_180374_a(sprintingSpeedBoostModifier)){
			playerSpeedAttribute.removeModifier(sprintingSpeedBoostModifier);
		}
	}
	
	public static void makeufat(EntityPlayer player){
		EntityLivingBase e = getMouseOverEntityLiving(player, Seras.teleportDistance);
		if(e != null){
			AttributeModifier addMaxHealth = new AttributeModifier("generic.maxHealth", 10, 0);
			e.getEntityAttribute(SharedMonsterAttributes.maxHealth).applyModifier(addMaxHealth);
			System.out.println("Max health is now " + e.getMaxHealth() + ".");
		}
	}
}
