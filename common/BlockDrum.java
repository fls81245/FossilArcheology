// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft.src;

// Referenced classes of package net.minecraft.src:
//            BlockContainer, Material, Block, World, 
//            TileEntityNote, EntityPlayer, TileEntity

public class BlockDrum extends BlockContainer
{
    public BlockDrum(int i)
    {
        super(i, 74, Material.wood);
		//world.setBlockMetadataWithNotify(i, j, k, OrderStay);
    }
    public String getTextureFile()
    {
       return "/skull/Fos_terrian.png";
    }
	 public int getBlockTextureFromSideAndMetadata(int sidePar,int metaPar)
    {
        if(sidePar == 1 || sidePar == 0)
        {
        	switch(metaPar){
        	case 1:
        		return mod_Fossil.dump_top_follow;
        	case 2:
        		return mod_Fossil.dump_top_freemove;
        	case 0:
        	default:
                return mod_Fossil.dump_top_stay;
        	}
        }
        return mod_Fossil.dump_side;
    }

    public void onNeighborBlockChange(World world, int i, int j, int k, int l)
    {
        if(l > 0 && Block.blocksList[l].canProvidePower())
        {
            boolean flag = world.isBlockGettingPowered(i, j, k);
            TileEntityDrum tileentityDump = (TileEntityDrum)world.getBlockTileEntity(i, j, k);
            if(tileentityDump.previousRedstoneState != flag)
            {
                if(flag)
                {
                    tileentityDump.triggerNote(world, i, j, k);
                }
                tileentityDump.previousRedstoneState = flag;
            }
        }
    }

    public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
        if(par1World.isRemote)
        {
            return true;
        } else
        {
            TileEntityDrum tileentityDump = (TileEntityDrum)par1World.getBlockTileEntity(par2, par3, par4);
			tileentityDump.TriggerOrder(par5EntityPlayer);
			int orderMeta=tileentityDump.Order.ordinal();
			par1World.setBlockMetadataWithNotify(par2, par3, par4,orderMeta);
            return true;
        }
    }

    public void onBlockClicked(World world, int i, int j, int k, EntityPlayer entityplayer)
    {
        if(world.isRemote)
        {
            return;
        } else
        {
            TileEntityDrum tileentityDump = (TileEntityDrum)world.getBlockTileEntity(i, j, k);
            //tileentityDump.triggerNote(world, i, j, k);
			if (entityplayer.inventory.getCurrentItem()!=null)tileentityDump.SendOrder(entityplayer.inventory.getCurrentItem().itemID,entityplayer);
            return;
        }
    }

    public TileEntity createNewTileEntity(World par1World)
    {
        return new TileEntityDrum();
    }

    /*public void playBlock(World world, int i, int j, int k, int l, int i1)
    {
        float f = (float)Math.pow(2D, (double)(i1 - 12) / 12D);
        String s = "harp";
        if(l == 1)
        {
            s = "bd";
        }
        if(l == 2)
        {
            s = "snare";
        }
        if(l == 3)
        {
            s = "hat";
        }
        if(l == 4)
        {
            s = "bassattack";
        }
        world.playSoundEffect((double)i + 0.5D, (double)j + 0.5D, (double)k + 0.5D, (new StringBuilder()).append("note.").append(s).toString(), 3F, f);
        world.spawnParticle("note", (double)i + 0.5D, (double)j + 1.2D, (double)k + 0.5D, (double)i1 / 24D, 0.0D, 0.0D);
    }*/
}
