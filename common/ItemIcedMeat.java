package net.minecraft.src;

public class ItemIcedMeat extends ForgeItemSword {

	public ItemIcedMeat(int i, EnumToolMaterial enumtoolmaterial) {
		super(i, enumtoolmaterial);
		this.setMaxDamage(500);
		this.setMaxStackSize(64);
	}
    public boolean hitEntity(ItemStack itemstack, EntityLiving entityliving, EntityLiving entityliving1)
    {
        itemstack.damageItem(1000, entityliving1);
        return true;
    }
    public boolean onBlockDestroyed(ItemStack itemstack, int i, int j, int k, int l, EntityLiving entityliving)
    {
        itemstack.damageItem(1000, entityliving);
        return true;
    }
}
