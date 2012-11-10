package net.minecraft.src;

import java.util.Random;

public class EntityBones extends EntityMob
{
    private static final ItemStack defaultHeldItem;

    public EntityBones(World world)
    {
        super(world);
        texture = "/mob/skeleton.png";
        this.tasks.addTask(1, new EntityAIDeadBones(this));
    }

    public int getMaxHealth()
    {
        return 20;
    }

    public boolean isAIEnabled()
    {
        return true;
    }
    protected String getHurtSound()
    {
        return "mob.skeletonhurt";
    }

    protected String getDeathSound()
    {
        return "mob.skeletonhurt";
    }

    public boolean attackEntityFrom(DamageSource damagesource, int i)
    {
        boolean result=super.attackEntityFrom(damagesource, i);
        entityToAttack=null;
        return result;
    }

    public boolean canBreatheUnderwater()
    {
        return true;
    }
    public void onLivingUpdate()
    {
        if (worldObj.isDaytime() && !worldObj.isRemote)
        {
            float f = getBrightness(1.0F);
            if (f > 0.5F && worldObj.canBlockSeeTheSky(MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ)) && rand.nextFloat() * 30F < (f - 0.4F) * 2.0F)
            {
                setFire(8);
            }
        }
        super.onLivingUpdate();
    }
    protected void updateEntityActionState()
    {
    	return;
    }
    protected boolean canDespawn()
    {
        return false;
    }
    protected void jump()
    {
    	return;
    }
    protected Entity findPlayerToAttack()
    {
    	return null;
    }
    protected void attackEntity(Entity entity, float f)
    {
    	return;
    }

    public void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        super.writeEntityToNBT(nbttagcompound);
    }

    public void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        super.readEntityFromNBT(nbttagcompound);
    }

    protected int getDropItemId()
    {
        return mod_Fossil.blockSkull.blockID;
    }

    protected void dropFewItems(boolean flag, int i)
    {
        int j = 1;
        for (int k = 0; k < j; k++)
        {
            dropItem(mod_Fossil.blockSkull.blockID, 1);
        }

        j = rand.nextInt(3 + i);
        for (int l = 0; l < j; l++)
        {
            dropItem(Item.bone.shiftedIndex, 1);
        }
    }

    public ItemStack getHeldItem()
    {
        return defaultHeldItem;
    }

    public EnumCreatureAttribute getCreatureAttribute()
    {
        return EnumCreatureAttribute.UNDEAD;
    }

    static
    {
        defaultHeldItem = null;
    }
}
