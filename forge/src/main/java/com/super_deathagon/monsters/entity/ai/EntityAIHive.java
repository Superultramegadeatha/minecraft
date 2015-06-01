package com.super_deathagon.monsters.entity.ai;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

import com.google.common.base.Predicate;
import com.super_deathagon.monsters.entity.EntityHiveSpider;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.world.World;

public class EntityAIHive extends EntityAIBase{
	protected HashSet<EntityCreature> hive;
	protected double searchArea;
	protected final EntityCreature taskOwner;
	
	public EntityAIHive(EntityCreature creature, double area){
		taskOwner = creature;
		hive = new HashSet<EntityCreature>();
		searchArea = area;
	}
	
	@Override
	public boolean shouldExecute() {
		if(hive != null)
			return true;
		return false;
	}
	
	@Override
	public void updateTask(){
		updateHive();
	}
	
    protected void updateHive(){
        List<Entity> list = taskOwner.worldObj.getEntitiesWithinAABBExcludingEntity(taskOwner, taskOwner.getEntityBoundingBox().expand(searchArea, searchArea, searchArea));
        HashSet<EntityCreature> tempHive = new HashSet<EntityCreature>();
        EntityCreature entity;
        
        for(Entity e : list){
        	if(e.getClass().equals(taskOwner.getClass())){
        		if(e != null){
            		tempHive.add((EntityCreature) e);
        		}
        	}
        }
        
        hive = tempHive;
    }
}
