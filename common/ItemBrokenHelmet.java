package net.minecraft.src;



public class ItemBrokenHelmet extends Item {
	public ItemBrokenHelmet(int i){
		super(i);
		maxStackSize=1;
	}
    public String getTextureFile()
    {
       return "/skull/Fos_items.png";
    }
}