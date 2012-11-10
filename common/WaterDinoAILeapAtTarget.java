package net.minecraft.src;

public class WaterDinoAILeapAtTarget extends EntityAILeapAtTarget {

	public WaterDinoAILeapAtTarget(EntityLiving par1EntityLiving, float par2) {
		super(par1EntityLiving, par2);
		// TODO Auto-generated constructor stub
	}
	@Override
	public boolean shouldExecute()
	{
		this.leapTarget = this.leaper.getAttackTarget();

		if (this.leapTarget == null)
		{
			return false;
		}
		else
		{
			double var1 = this.leaper.getDistanceSqToEntity(this.leapTarget);
			return var1 >= 1.0D && var1 <= 32.0D ? (this.leaper.getRNG().nextInt(5) == 0) : false;
		}
	}
	@Override
	public boolean continueExecuting()
	{
		return false;
	}
	@Override
	public void startExecuting()
	{
		double var1 = this.leapTarget.posX - this.leaper.posX;
		double var3 = this.leapTarget.posZ - this.leaper.posZ;
		float var5 = MathHelper.sqrt_double(var1 * var1 + var3 * var3);
		this.leaper.motionX += var1 / var5 * 0.5D * 0.800000011920929D + this.leaper.motionX * 0.20000000298023224D;
		this.leaper.motionZ += var3 / var5 * 0.5D * 0.800000011920929D + this.leaper.motionZ * 0.20000000298023224D;
		this.leaper.motionY = (double)this.leapMotionY;
	}
}
