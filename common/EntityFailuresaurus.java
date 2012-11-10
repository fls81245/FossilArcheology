package net.minecraft.src;

public class EntityFailuresaurus extends EntityZombie{
	public EntityFailuresaurus(World world){
		super(world);
		texture = "/skull/Failuresaurus.png";
	}
    protected int getDropItemId()
    {
        return mod_Fossil.biofossil.shiftedIndex;
    }
    protected void jump()
    {
        return;
    }
    
}
