package net.minecraft.src;

import java.util.Random;



public class BlockTimeMachine extends BlockContainer{

	protected BlockTimeMachine(int i, int j, Material material) {
        super(i, 1, material);
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.75F, 1.0F);
        setLightOpacity(0);
		
	}

	@Override
	public String getTextureFile() {
		return "/TimeMachine/T_mac_o.png";
	}
	public int getBlockTexture(IBlockAccess iblockaccess, int i, int j, int k, int l)
    {
        if(l == 1)
        {
            return 0;
        }
        if(l == 0)
        {
            return 1;
        }
        return 16+(l<=3?0:1);
    }

	@Override
	public TileEntity createNewTileEntity(World par1World) {
		return new TileEntityTimeMachine();
	}
	public void randomDisplayTick(World world, int i, int j, int k, Random random)
    {
        super.randomDisplayTick(world, i, j, k, random);
        for(int l = i - 2; l <= i + 2; l++)
        {
            for(int i1 = k - 2; i1 <= k + 2; i1++)
            {
                if(l > i - 2 && l < i + 2 && i1 == k - 1)
                {
                    i1 = k + 2;
                }
                if(random.nextInt(16) != 0)
                {
                    continue;
                }
                for(int j1 = j; j1 <= j + 1; j1++)
                {
                    if(world.getBlockId(l, j1, i1) != Block.bookShelf.blockID)
                    {
                        continue;
                    }
                    if(!world.isAirBlock((l - i) / 2 + i, j1, (i1 - k) / 2 + k))
                    {
                        break;
                    }
                    world.spawnParticle("enchantmenttable", (double)i + 0.5D, (double)j + 2D, (double)k + 0.5D, (double)((float)(l - i) + random.nextFloat()) - 0.5D, (float)(j1 - j) - random.nextFloat() - 1.0F, (double)((float)(i1 - k) + random.nextFloat()) - 0.5D);
                }

            }

        }

    }

    public boolean isOpaqueCube()
    {
        return false;
    }

    public int getBlockTextureFromSideAndMetadata(int i, int j)
    {
        return getBlockTextureFromSide(i);
    }

    public int getBlockTextureFromSide(int i)
    {
        if(i == 0)
        {
            return blockIndexInTexture;
        }
        if(i == 1)
        {
            return blockIndexInTexture;
        } else
        {
            return blockIndexInTexture + 16;
        }
    }

    public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
    	//Right click
        if(par1World.isRemote)
        {
            return true;
        } else
        {
           TileEntityTimeMachine ControllingObject=(TileEntityTimeMachine)par1World.getBlockTileEntity(par2,par3,par4);
           if (ControllingObject!=null){
        	 
        	   ModLoader.getMinecraftInstance().displayGuiScreen(new GuiTimeMachine(par5EntityPlayer.inventory, ControllingObject));
           }
           return true;
        }
    }
}
