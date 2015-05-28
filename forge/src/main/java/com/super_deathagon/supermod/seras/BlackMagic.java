package com.super_deathagon.supermod.seras;

import java.util.List;
import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.particle.EntitySpellParticleFX;
import net.minecraft.client.particle.EntitySpellParticleFX.WitchFactory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.passive.EntityBat;
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

import com.super_deathagon.supermod.network.AbilityMessage;
import com.super_deathagon.supermod.proxy.CommonProxy;
import com.super_deathagon.util.EntityAttributeModifier;
import com.super_deathagon.util.MouseOverHelper;


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
    
    public static void fireArrow(EntityPlayer player){
    	if(Seras.isMaster(player))
    		fireArrow(player, Seras.teleportDistance);
    }
    
	public static void fireArrow(EntityPlayer player, double reach){
		if(player.worldObj.isRemote)
			return;
		
		float speed = 3f;
		float inaccuracy = 16f;
		Entity e = MouseOverHelper.getMouseOverEntityLiving(player, reach);
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
	
	/*public static void moveFast(EntityPlayer player, AttributeModifier sprintingSpeedBoostModifier){
		IAttributeInstance playerSpeedAttribute = player.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
		if(!playerSpeedAttribute.func_180374_a(sprintingSpeedBoostModifier)){
			playerSpeedAttribute.applyModifier(sprintingSpeedBoostModifier);
		}else if(playerSpeedAttribute.func_180374_a(sprintingSpeedBoostModifier)){
			playerSpeedAttribute.removeModifier(sprintingSpeedBoostModifier);
		}
	}*/
	
	public static void makeufat(EntityPlayer player){
		EntityLivingBase e = MouseOverHelper.getMouseOverEntityLiving(player, Seras.teleportDistance);
		if(e != null){
			EntityAttributeModifier.modifyMaxHealth(e, 10.0);
		}
	}
}
