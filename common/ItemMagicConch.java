// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft.src;

import java.util.Iterator;
import java.util.List;




// Referenced classes of package net.minecraft.src:
//            Item, World, Material, EntityPlayer, 
//            Block, MathHelper, ItemStack, TileEntitySign

public class ItemMagicConch extends Item
{

    public ItemMagicConch(int i)
    {
        super(i);
		setHasSubtypes(true);
		setMaxDamage(0);
        maxStackSize = 1;
    }
    public String getTextureFile()
    {
       return "/skull/Fos_items.png";
    }
	public int getIconFromDamage(int i)
    {
		return this.iconIndex;
    }
	public String getItemNameIS(ItemStack itemstack)
    {
		final String NameHead="MagicConch";
		String SecondName=EnumOrderType.values()[itemstack.getItemDamage()].toString();
		return NameHead+SecondName;
    }
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
    {
    	final String DRUM="Drum.";
    	final String MSG="Msg.";
    	final String HEAD="Head";
    	final String MIDDLE="Middle";
    	final String TAIL="Tail";
    	final String DINO=EntityDinosaurce.GetNameByEnum(EnumDinoType.Plesiosaur, true);
		final String MSGHEAD=mod_Fossil.GetLangTextByKey(DRUM+MSG+HEAD);
		final String MSGMIDDLE=mod_Fossil.GetLangTextByKey(DRUM+MSG+MIDDLE);
		final String MSGTAIL=mod_Fossil.GetLangTextByKey(DRUM+MSG+TAIL);
    	String OrderString="";
		List list = world.getEntitiesWithinAABB(net.minecraft.src.EntityPlesiosaur.class, AxisAlignedBB.getAABBPool().addOrModifyAABBInPool(entityplayer.posX, entityplayer.posY, entityplayer.posZ, entityplayer.posX + 1.0D, entityplayer.posY + 1.0D, entityplayer.posZ + 1.0D).expand(30D, 4D, 30D));
		Iterator iterator = list.iterator();
        do
        {
            if(!iterator.hasNext())
            {
                 break;
            }
            Entity entity1 = (Entity)iterator.next();
            EntityDinosaurce entityDino = (EntityDinosaurce)entity1;
            if (entityDino.isTamed()){
            	entityDino.SetOrder(EnumOrderType.values()[itemstack.getItemDamage()]);
            	world.spawnParticle("note", entity1.posX, entity1.posY + 1.2D, entity1.posZ, 0.0D, 0.0D, 0.0D);            	
            }

        } while(true);
        OrderString=EnumOrderType.values()[itemstack.getItemDamage()].GetOrderString();
        
        mod_Fossil.ShowMessage(MSGHEAD+DINO+MSGMIDDLE+" "+OrderString+MSGTAIL,entityplayer);
        return itemstack;
    }
}
