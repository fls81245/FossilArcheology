package net.minecraft.src;

public class FPZAIOwnerHurtTarget extends EntityAITarget
{
	EntityFriendlyPigZombie fpz;
    EntityLiving field_48391_b;

    public FPZAIOwnerHurtTarget(EntityFriendlyPigZombie par1FPZ)
    {
        super(par1FPZ, 32.0F, false);
        this.fpz = par1FPZ;
        this.setMutexBits(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        if (!this.fpz.isTamed())
        {
            return false;
        }
        else
        {
            EntityLiving var1 = this.fpz.getOwner();

            if (var1 == null)
            {
                return false;
            }
            else
            {
                this.field_48391_b = var1.getLastAttackingEntity();
                return this.isSuitableTarget(this.field_48391_b, false);
            }
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        this.taskOwner.setAttackTarget(this.field_48391_b);
        super.startExecuting();
    }
}
