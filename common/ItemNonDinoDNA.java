package net.minecraft.src;

import java.util.List;


public class ItemNonDinoDNA extends Item {
	public ItemNonDinoDNA(int i){
		super(i);
		setHasSubtypes(true);
		setMaxDamage(0);
		maxStackSize=64;
	}
	public int getIconFromDamage(int i)
    {
		if (i<TypeCount) return 70+i;
        else return 0;
    }
    public String getTextureFile()
    {
       return "/skull/Fos_items.png";
    }
	public String getItemNameIS(ItemStack itemstack)
    {
		switch(GetTypeFromInt(itemstack.getItemDamage())){
			case Pig:
				return "DNAPig";
			case Sheep:
				return "DNASheep";
			case Cow:
				return "DNATCow";
			case Chicken:
				return "DNAChicken";
			case SaberCat:
				return "DNASaberCat";
			default:
				return "AnimalDNA";
		}
    }
	private EnumAnimalType GetTypeFromInt(int data){
		EnumAnimalType[] resultArray=EnumAnimalType.values();
		return resultArray[data];
	}
	public static final int TypeCount=EnumAnimalType.values().length;
    public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        for (int var4 = 0; var4 < TypeCount; ++var4)
        {
            par3List.add(new ItemStack(par1, 1, var4));
        }
    }
}