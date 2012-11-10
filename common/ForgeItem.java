// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft.src;

import java.io.PrintStream;
import java.util.Random;



// Referenced classes of package net.minecraft.src:
//            ItemStack, StatCollector, EnumAction, ItemSpade, 
//            EnumToolMaterial, ItemPickaxe, ItemAxe, ItemFlintAndSteel, 
//            ItemFood, ItemBow, ItemCoal, ItemSword, 
//            ItemSoup, ItemHoe, ItemSeeds, Block, 
//            ItemArmor, ItemPainting, Potion, ItemSign, 
//            ItemDoor, Material, ItemBucket, ItemMinecart, 
//            ItemSaddle, ItemRedstone, ItemSnowball, ItemBoat, 
//            ItemReed, ItemEgg, ItemFishingRod, ItemDye, 
//            ItemBed, ItemMap, ItemShears, ItemRecord, 
//            StatList, EntityPlayer, World, EntityLiving, 
//            Entity

public class ForgeItem extends Item 
{

    protected ForgeItem(int i)
    {
    	super(i);
    }
    public String getTextureFile()
    {
       return "/skull/Fos_items.png";
    }
}
