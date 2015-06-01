package com.super_deathagon.monsters.entity.ai;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.player.EntityPlayer;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

public class EntityAIHiveNearestAttackableTarget extends EntityAIHiveTarget{
	protected final Class targetClass;
    private final int targetChance;
    /** Instance of EntityAINearestAttackableTargetSorter. */
    protected final EntityAINearestAttackableTarget.Sorter theNearestAttackableTargetSorter;
    /**
     * This filter is applied to the Entity search.  Only matching entities will be targetted.  (null -> no
     * restrictions)
     */
    protected Predicate targetEntitySelector;
    protected EntityLivingBase targetEntity;
    private static final String __OBFID = "CL_00001620";

    /**
     * 
     * @param creature the owner of this AI
     * @param area the area to search for others of the same type of creature
     * @param target the type of entity to target
     * @param checkSight target only what this creature can see
     */
    public EntityAIHiveNearestAttackableTarget(EntityCreature creature, double area, Class target, boolean checkSight)
    {
        this(creature, area, target, checkSight, false);
    }

    /**
     * 
     * @param creature the owner of this AI
     * @param area the area to search for others of the same type of creature
     * @param target the type of entity to target
     * @param checkSight target only what this creature can see
     * @param nearOnly target only what is near this creature
     */
    public EntityAIHiveNearestAttackableTarget(EntityCreature creature, double area, Class target, boolean checkSight, boolean nearOnly)
    {
        this(creature, area, target, 10, checkSight, nearOnly, (Predicate)null);
    }

    /**
     * 
     * @param creature the owner of this AI
     * @param area the area to search for others of the same type of creature
     * @param target the type of entity to target
     * @param checkSight target only what this creature can see
     * @param nearOnly target only what is near this creature
     * @param p_i45880_6_ the predicate used to specifically define what to target
     */
    public EntityAIHiveNearestAttackableTarget(EntityCreature creature, double area, Class target, int p_i45880_3_, boolean checkSight, boolean nearOnly, final Predicate p_i45880_6_)
    {
        super(creature, area, checkSight, nearOnly);
        this.targetClass = target;
        this.targetChance = p_i45880_3_;
        this.theNearestAttackableTargetSorter = new EntityAINearestAttackableTarget.Sorter(creature);
        this.setMutexBits(1);
        this.targetEntitySelector = new Predicate()
        {
            public boolean func_179878_a(EntityLivingBase p_179878_1_)
            {
                if (p_i45880_6_ != null && !p_i45880_6_.apply(p_179878_1_))
                {
                    return false;
                }
                else
                {
                    if (p_179878_1_ instanceof EntityPlayer)
                    {
                        double d0 = EntityAIHiveNearestAttackableTarget.this.getTargetDistance();

                        if (p_179878_1_.isSneaking())
                        {
                            d0 *= 0.800000011920929D;
                        }

                        if (p_179878_1_.isInvisible())
                        {
                            float f = ((EntityPlayer)p_179878_1_).getArmorVisibility();

                            if (f < 0.1F)
                            {
                                f = 0.1F;
                            }

                            d0 *= (double)(0.7F * f);
                        }

                        if ((double)p_179878_1_.getDistanceToEntity(EntityAIHiveNearestAttackableTarget.this.taskOwner) > d0)
                        {
                            return false;
                        }
                    }

                    return EntityAIHiveNearestAttackableTarget.this.isSuitableTarget(p_179878_1_, false);
                }
            }
            public boolean apply(Object p_apply_1_)
            {
                return this.func_179878_a((EntityLivingBase)p_apply_1_);
            }
        };
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        if (this.targetChance > 0 && this.taskOwner.getRNG().nextInt(this.targetChance) != 0)
        {
            return false;
        }
        else
        {
            double d0 = this.getTargetDistance();
            List list = this.taskOwner.worldObj.getEntitiesWithinAABB(this.targetClass, this.taskOwner.getEntityBoundingBox().expand(d0, 4.0D, d0), Predicates.and(this.targetEntitySelector, IEntitySelector.NOT_SPECTATING));
            Collections.sort(list, this.theNearestAttackableTargetSorter);

            if (list.isEmpty())
            {
                return false;
            }
            else
            {
                this.targetEntity = (EntityLivingBase)list.get(0);
                return true;
            }
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        this.taskOwner.setAttackTarget(this.targetEntity);
        super.startExecuting();
    }

    public static class Sorter implements Comparator
    {
        private final Entity theEntity;
        private static final String __OBFID = "CL_00001622";

        public Sorter(Entity p_i1662_1_)
        {
            this.theEntity = p_i1662_1_;
        }

        public int compare(Entity p_compare_1_, Entity p_compare_2_)
        {
            double d0 = this.theEntity.getDistanceSqToEntity(p_compare_1_);
            double d1 = this.theEntity.getDistanceSqToEntity(p_compare_2_);
            return d0 < d1 ? -1 : (d0 > d1 ? 1 : 0);
        }

        public int compare(Object p_compare_1_, Object p_compare_2_)
        {
            return this.compare((Entity)p_compare_1_, (Entity)p_compare_2_);
        }
    }
}
