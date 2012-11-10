package net.minecraft.src;

public class DinoAIAvoidEntityWhenYoung extends EntityAIAvoidEntity {
	EntityDinosaurce dinoEntity;
	public DinoAIAvoidEntityWhenYoung(EntityCreature par1EntityCreature,
			Class par2Class, float par3, float par4, float par5) {
		super(par1EntityCreature, par2Class, par3, par4, par5);
		dinoEntity=(EntityDinosaurce)par1EntityCreature;
		// TODO Auto-generated constructor stub
	}
    public boolean shouldExecute()
    {
    	if (!dinoEntity.isYoung()) return false;
    	return super.shouldExecute();
    }
}
