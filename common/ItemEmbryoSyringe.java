package net.minecraft.src;

import java.util.List;



public class ItemEmbryoSyringe extends ForgeItem {
	private String[] ItemNames={
			"EmbyoPig","EmbyoSheep","EmbyoCow","EmbyoSaberCat","EmbyoMammoth"
	};
	protected ItemEmbryoSyringe(int i) {
		super(i);
		setHasSubtypes(true);
		setMaxDamage(0);
		maxStackSize=64;
		initItemNameVector();
	}
    private void initItemNameVector() {
		
		
	}
	public String getTextureFile()
    {
       return "/skull/needle.png";
    }
	public int getIconFromDamage(int i)
    {
		return i;
    }
	public String getItemNameIS(ItemStack itemstack)
    {
			int tmp=itemstack.getItemDamage();
			if (tmp<this.ItemNames.length) return this.ItemNames[tmp];
			return "EmbyoSyring";
    }
	public static EnumEmbyos GetEmbyo(int index){
		return EnumEmbyos.values()[index];
	}
    public boolean itemInteractionForEntity(ItemStack itemstack, EntityLiving entityliving)
    {
        if(entityliving instanceof EntityAnimal && ((EntityAnimal)entityliving).getGrowingAge()==0)
        {
        	IViviparous entityanimal1=null;
        	if (entityliving instanceof EntityPig) entityanimal1=new EntityPregnantPig(entityliving.worldObj);
        	if (entityliving instanceof EntityCow) entityanimal1=new EntityPregnantCow(entityliving.worldObj);
        	if (entityliving instanceof EntitySheep){
        		entityanimal1=new EntityPregnantSheep(entityliving.worldObj);
        		((EntitySheep)entityanimal1).setFleeceColor(((EntitySheep)entityliving).getFleeceColor());
        		((EntitySheep)entityanimal1).setSheared(((EntitySheep)entityliving).getSheared());
        	}
        	if (entityanimal1!=null){
        		entityanimal1.SetEmbyo(GetEmbyo(itemstack.getItemDamage()));
        		((EntityAnimal)entityanimal1).setLocationAndAngles(entityliving.posX, entityliving.posY, entityliving.posZ, entityliving.rotationYaw, entityliving.rotationPitch);
        		entityliving.setDead();
        		if (!entityliving.worldObj.isRemote)entityliving.worldObj.spawnEntityInWorld(((EntityAnimal)entityanimal1));
        		itemstack.stackSize--;
        	}
        	return true;
        }else return false;
    }
    public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        for (int var4 = 0; var4 < EnumEmbyos.values().length; ++var4)
        {
            par3List.add(new ItemStack(par1, 1, var4));
        }
    }
}
