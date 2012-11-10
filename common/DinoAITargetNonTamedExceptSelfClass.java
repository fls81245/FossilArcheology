package net.minecraft.src;

public class DinoAITargetNonTamedExceptSelfClass extends EntityAITargetNonTamed {

	public DinoAITargetNonTamedExceptSelfClass(
			EntityTameable par1EntityTameable, Class par2Class, float par3,
			int par4, boolean par5) {
		super(par1EntityTameable, par2Class, par3, par4, par5);
		// TODO Auto-generated constructor stub
	}
    public boolean shouldExecute()
    {
    	boolean result=super.shouldExecute();
    	if (result && this.targetEntity instanceof EntityDinosaurce && ((EntityDinosaurce)this.targetEntity).SelfType==((EntityDinosaurce)this.taskOwner).SelfType){
    		return false;
    	}
    	return result;
    }
}
