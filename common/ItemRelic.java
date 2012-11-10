package net.minecraft.src;



public class ItemRelic extends Item{
	public ItemRelic(int i){
		super(i);
		maxStackSize=64;
	}
    public String getTextureFile()
    {
       return "/skull/Fos_items.png";
    }
}