package com.super_deathagon.monsters.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.pathfinding.PathNavigateGround;

public class EntityAIFollowTarget extends EntityAINearestAttackableTarget{

	
	public EntityAIFollowTarget(EntityCreature p_i45878_1_, Class p_i45878_2_, boolean p_i45878_3_) {
		super(p_i45878_1_, p_i45878_2_, p_i45878_3_);
	}

	public void updateTask(){
		super.updateTask();
		double distance = taskOwner.getDistanceToEntity(targetEntity);
		if((distance > 10.0 || distance < 5.0) && taskOwner.getNavigator().noPath())
			moveBehindTarget();
	}
	
	private boolean moveBehindTarget(){
    	double yaw = targetEntity.rotationYawHead + taskOwner.getRNG().nextGaussian()*45.0;
    	double x = targetEntity.posX - Math.sin( (yaw*Math.PI)/180.0 )*10.0;
    	double z = targetEntity.posZ + Math.cos( (yaw*Math.PI)/180.0 )*10.0;
		return taskOwner.getNavigator().tryMoveToXYZ(x, taskOwner.posY, z, 1.0f);
	}
	
	private boolean canTargetSeeMe(){
		return false;
	}
}
