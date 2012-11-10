package net.minecraft.src;

public class ItemChickenSoup extends ForgeItem {
	public static final int BASEICONLOC=58;
	public static final Item CONTAINER=Item.bucketEmpty;
	protected ItemChickenSoup(int i) {
		super(i);
		setHasSubtypes(true);
		setMaxDamage(0);
		maxStackSize=1;
	}
	public int getIconFromDamage(int i)
    {
		return BASEICONLOC+((i==0)?0:2);
    }
	public String getItemNameIS(ItemStack itemstack)
    {
		if (itemstack.getItemDamage()==1) return "CookedChickenSoup";
		else return "RawChickenSoup";
    }    

}
