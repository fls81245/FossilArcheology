package net.minecraft.src;

import java.util.Random;

public class DinoAIFishing extends EntityAIBase
{
    private EntityDinosaurce theEntity;
    private final float huntLimit;
    private final int percentage;
    public DinoAIFishing(EntityDinosaurce par1EntityDinosaurce,float parHuntLimit,int parPercentage)
    {
        this.theEntity = par1EntityDinosaurce;
        this.setMutexBits(4);
        par1EntityDinosaurce.getNavigator().setCanSwim(true);
        percentage=(parPercentage>100)?100:(parPercentage<0)?0:parPercentage;
        huntLimit=parHuntLimit;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
    	if (!nearbyGotWater()) return false;
    	if (this.theEntity.getHunger()<huntLimit && this.theEntity.rand.nextInt(100)<percentage) return true;
        return false;
    }

    /**
     * Updates the task
     */
    public void updateTask()
    {
    		Random randSeed=this.theEntity.rand;    	
    	for (int i=0;i<=randSeed.nextInt(3);i++){
            EntityItem var13 = new EntityItem(this.theEntity.worldObj, this.theEntity.posX, this.theEntity.posY, this.theEntity.posZ, new ItemStack(Item.fishRaw));
            double var3 = (randSeed.nextInt(5)-2);
            double var5 = (randSeed.nextInt(5)-2);
            double var7 = (randSeed.nextInt(5)-2);
            double var9 = (double)MathHelper.sqrt_double(var3 * var3 + var5 * var5 + var7 * var7);
            double var11 = 0.1D;
            var13.motionX = var3 * var11;
            var13.motionY = var5 * var11 + (double)MathHelper.sqrt_double(var9) * 0.08D;
            var13.motionZ = var7 * var11;
            this.theEntity.worldObj.spawnEntityInWorld(var13);
    	}

    }
    private boolean nearbyGotWater(){
    	if (this.theEntity.isInWater()) return true;
    	AxisAlignedBB boxTmp=this.theEntity.boundingBox.expand(2.0, 2.0, 2.0);
    	if (this.theEntity.worldObj.isAnyLiquid(boxTmp)) return true;
    	return false;
    }
}
