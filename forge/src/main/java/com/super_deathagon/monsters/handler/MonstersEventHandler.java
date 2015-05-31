package com.super_deathagon.monsters.handler;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.super_deathagon.util.EntityAttributeModifier;

public class MonstersEventHandler {
	private double percentHPPB = 0;
	private double percentDPPB = 0;
	
	public MonstersEventHandler(double i, double j) {
		percentHPPB= i;
		percentDPPB = j;
	}

	/**
	 * Handle to capture entity spawn events. 
	 * We only print information at the moment.
	 * @param event
	 */
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onEvent(EntityJoinWorldEvent event){
		Entity e = event.entity;
		if(e != null && (e instanceof EntityLivingBase) && !(e instanceof EntityPlayer)){
			modifyMonsterAttributes((EntityLivingBase)e);
			//printEntityLivingInfo((EntityLivingBase)e);
		}
	}
	
	public void modifyMonsterAttributes(EntityLivingBase e){
		double distanceToSpawn = e.getPositionVector().distanceTo(new Vec3(e.worldObj.getSpawnPoint().getX(), 
																			e.worldObj.getSpawnPoint().getY(), 
																			e.worldObj.getSpawnPoint().getZ()));
		double addHP = e.getHealth() * distanceToSpawn * percentHPPB;
		EntityAttributeModifier.modifyMaxHealth(e, addHP);

		IAttributeInstance attackDamage = e.getEntityAttribute(SharedMonsterAttributes.attackDamage);
		if(attackDamage != null){
			double addDP = attackDamage.getAttributeValue() * distanceToSpawn * percentDPPB;
			EntityAttributeModifier.modifyBaseDamage(e, addDP);
		}
	}
	
	/**
	 * print out some information we might need.
	 * @param e the entity we are interested in
	 */
	public void printEntityLivingInfo(EntityLivingBase e){
		IAttributeInstance ada = e.getEntityAttribute(SharedMonsterAttributes.attackDamage);
		double ad = 0;
		if(ada != null)
			ad = ada.getAttributeValue();
		System.out.println("Name:" + e.getName() 
						+ " HP:" + e.getHealth() 
						+ " DP:" + ad);
		System.out.println("Distance from spawn area:" + e.getPositionVector().distanceTo(new Vec3(e.worldObj.getSpawnPoint().getX(), 
																									e.worldObj.getSpawnPoint().getY(), 
																									e.worldObj.getSpawnPoint().getZ())));
	}
}
