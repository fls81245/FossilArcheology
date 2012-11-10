



package net.minecraft.src;

import java.util.Random;

import net.minecraft.client.Minecraft;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.FMLNetworkHandler;
import cpw.mods.fml.common.network.NetworkRegistry;






public class BlockAnalyzer extends BlockContainer{
    private Random furnaceRand;
    private final boolean isActive;
    private static boolean keepFurnaceInventory = false;


	public BlockAnalyzer(int i,boolean flag){

		super(i, Material.iron);
        furnaceRand = new Random();
        isActive = flag;
        blockIndexInTexture = 45;
	}
    public String getTextureFile()
    {
       return "/skull/Fos_terrian.png";
    }
	public int idDropped(int i, Random random, int j)
    {
        return mod_Fossil.blockanalyzerIdle.blockID;

    }

	public void onBlockAdded(World world, int i, int j, int k)
    {
        super.onBlockAdded(world, i, j, k);
        setDefaultDirection(world, i, j, k);
    }

	private void setDefaultDirection(World world, int i, int j, int k)
    {
        if(world.isRemote)
        {
            return;
        }
        int l = world.getBlockId(i, j, k - 1);
        int i1 = world.getBlockId(i, j, k + 1);
        int j1 = world.getBlockId(i - 1, j, k);
        int k1 = world.getBlockId(i + 1, j, k);
        byte byte0 = 3;
        if(Block.opaqueCubeLookup[l] && !Block.opaqueCubeLookup[i1])
        {
            byte0 = 3;
        }
        if(Block.opaqueCubeLookup[i1] && !Block.opaqueCubeLookup[l])
        {
            byte0 = 2;
        }
        if(Block.opaqueCubeLookup[j1] && !Block.opaqueCubeLookup[k1])
        {
            byte0 = 5;
        }
        if(Block.opaqueCubeLookup[k1] && !Block.opaqueCubeLookup[j1])
        {
            byte0 = 4;
        }
        world.setBlockMetadataWithNotify(i, j, k, byte0);
    }

    public int getBlockTexture(IBlockAccess iblockaccess, int i, int j, int k, int l)
    {
        if(l == 1)
        {
            return mod_Fossil.analyzer_top;

        }
        if(l == 0)
        {
            return mod_Fossil.analyzer_top;

        }
        int i1 = iblockaccess.getBlockMetadata(i, j, k);
        if(l != i1)
        {
            return mod_Fossil.analyzer_side;

        }
        if(isActive)
        {
            return mod_Fossil.analyzer_on;

        } else
        {
            return mod_Fossil.analyzer_off;

        }
    }

	public void randomDisplayTick(World world, int i, int j, int k, Random random)
    {
       //special effects for operationing


	   return;
    }




























	public int getBlockTextureFromSide(int i)
    {
        if(i == 1)
        {
            return mod_Fossil.analyzer_top;

        }
        if(i == 0)
        {
            return mod_Fossil.analyzer_top;

        }
        if(i == 3)
        {
            return mod_Fossil.analyzer_off;

        } else
        {
            return mod_Fossil.analyzer_side;

        }
    }

	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
        if(par1World.isRemote)
        {
            return true;
        } else
        {        	


					mod_Fossil.callGUI(par5EntityPlayer,FossilGuiHandler.ANALYZER_GUI_ID,par1World, par2, par3, par4);




            return true;
        }
    }

	public static void updateFurnaceBlockState(boolean flag, World world, int i, int j, int k)
    {
			int l = world.getBlockMetadata(i, j, k);
			TileEntity tileentity = world.getBlockTileEntity(i, j, k);
			if (tileentity!=null){
			keepFurnaceInventory = true;
				if(flag)
				{
					world.setBlockWithNotify(i, j, k, mod_Fossil.blockanalyzerActive.blockID);
				} else
				{
					world.setBlockWithNotify(i, j, k, mod_Fossil.blockanalyzerIdle.blockID);
				}
				keepFurnaceInventory = false;
				world.setBlockMetadataWithNotify(i, j, k, l);
				tileentity.validate();
				world.setBlockTileEntity(i, j, k, tileentity);
			}
    }



	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLiving entityliving)
    {
        int l = MathHelper.floor_double((double)((entityliving.rotationYaw * 4F) / 360F) + 0.5D) & 3;
        if(l == 0)
        {
            world.setBlockMetadataWithNotify(i, j, k, 2);
        }
        if(l == 1)
        {
            world.setBlockMetadataWithNotify(i, j, k, 5);
        }
        if(l == 2)
        {
            world.setBlockMetadataWithNotify(i, j, k, 3);
        }
        if(l == 3)
        {
            world.setBlockMetadataWithNotify(i, j, k, 4);
        }
    }

	public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
		if (!keepFurnaceInventory)
        {
            TileEntityAnalyzer var7 = (TileEntityAnalyzer)par1World.getBlockTileEntity(par2, par3, par4);

            if (var7 != null)
            {
                for (int var8 = 0; var8 < var7.getSizeInventory(); ++var8)
                {
                    ItemStack var9 = var7.getStackInSlot(var8);

                    if (var9 != null)
                    {
                        float var10 = this.furnaceRand.nextFloat() * 0.8F + 0.1F;
                        float var11 = this.furnaceRand.nextFloat() * 0.8F + 0.1F;
                        float var12 = this.furnaceRand.nextFloat() * 0.8F + 0.1F;

                        while (var9.stackSize > 0)
                        {
                            int var13 = this.furnaceRand.nextInt(21) + 10;

                            if (var13 > var9.stackSize)
                            {
                                var13 = var9.stackSize;
                            }

                            var9.stackSize -= var13;
                            EntityItem var14 = new EntityItem(par1World, (double)((float)par2 + var10), (double)((float)par3 + var11), (double)((float)par4 + var12), new ItemStack(var9.itemID, var13, var9.getItemDamage()));

                            if (var9.hasTagCompound())
                            {
                                var14.item.setTagCompound((NBTTagCompound)var9.getTagCompound().copy());
                            }

                            float var15 = 0.05F;
                            var14.motionX = (double)((float)this.furnaceRand.nextGaussian() * var15);
                            var14.motionY = (double)((float)this.furnaceRand.nextGaussian() * var15 + 0.2F);
                            var14.motionZ = (double)((float)this.furnaceRand.nextGaussian() * var15);
                            par1World.spawnEntityInWorld(var14);
                        }
                    }
                }
            }
        }
        super.breakBlock(par1World, par2, par3, par4, par5, par6);
    }
    /**
     * each class overrdies this to return a new <className>
     */
    public TileEntity createNewTileEntity(World par1World)
    {
        return new TileEntityAnalyzer();
    }





}
