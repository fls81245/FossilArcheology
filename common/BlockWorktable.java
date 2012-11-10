



package net.minecraft.src;

import java.util.Random;








public class BlockWorktable extends BlockContainer
{
	private Random furnaceRand;
    private final boolean isActive;
    private static boolean keepFurnaceInventory = false;

	protected BlockWorktable(int i, boolean flag)
    {
        super(i, Material.rock);
        furnaceRand = new Random();
        isActive = flag;
        blockIndexInTexture = 45;
    }

	public int idDropped(int i, Random random,int unusedj)
    {
        return mod_Fossil.blockworktableIdle.blockID;

    }
    public String getTextureFile()
    {
       return "/skull/Fos_terrian.png";
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
			if (isActive) return mod_Fossil.worktable_top_on;
			else return mod_Fossil.worktable_top_off;

        }
        if(l == 0)
        {
            return 68;

        }
        int i1 = iblockaccess.getBlockMetadata(i, j, k);
        if(l != i1)
        {
            return mod_Fossil.worktable_side1;

        }
            return mod_Fossil.worktable_side2;







    }

	public void randomDisplayTick(World world, int i, int j, int k, Random random)
    {
        /*if(!isActive)
        {
            return;
        }
        int l = world.getBlockMetadata(i, j, k);
        float f = (float)i + 0.5F;
        float f1 = (float)j + 0.0F + (random.nextFloat() * 6F) / 16F;
        float f2 = (float)k + 0.5F;
        float f3 = 0.52F;
        float f4 = random.nextFloat() * 0.6F - 0.3F;
        if(l == 4)
        {
            world.spawnParticle("smoke", f - f3, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
            world.spawnParticle("flame", f - f3, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
        } else
        if(l == 5)
        {
            world.spawnParticle("smoke", f + f3, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
            world.spawnParticle("flame", f + f3, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
        } else
        if(l == 2)
        {
            world.spawnParticle("smoke", f + f4, f1, f2 - f3, 0.0D, 0.0D, 0.0D);
            world.spawnParticle("flame", f + f4, f1, f2 - f3, 0.0D, 0.0D, 0.0D);
        } else
        if(l == 3)
        {
            world.spawnParticle("smoke", f + f4, f1, f2 + f3, 0.0D, 0.0D, 0.0D);
            world.spawnParticle("flame", f + f4, f1, f2 + f3, 0.0D, 0.0D, 0.0D);
        }*/
    }


	public int getBlockTextureFromSide(int i)
    {
        if(i == 1)
        {
            if (isActive) return mod_Fossil.worktable_top_on;
			else return mod_Fossil.worktable_top_off;

        }
        if(i == 0)
        {
            return 68;

        }
        if(i == 3 || i == 2)

        {
            return mod_Fossil.worktable_side2;

        } else
        {
            return mod_Fossil.worktable_side1;

        }
    }

	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
        if(par1World.isRemote)
        {
            return true;
        } else
        {
        	mod_Fossil.callGUI(par5EntityPlayer,FossilGuiHandler.WORKTABLE_GUI_ID,par1World, par2, par3, par4);

            return true;
        }
    }

	public static void updateFurnaceBlockState(boolean flag, World world, int i, int j, int k)
    {
        int l = world.getBlockMetadata(i, j, k);
        TileEntity tileentity = world.getBlockTileEntity(i, j, k);
        keepFurnaceInventory = true;

        if(flag)
        {
            world.setBlockWithNotify(i, j, k, mod_Fossil.blockworktableActive.blockID);
        } else
        {
            world.setBlockWithNotify(i, j, k, mod_Fossil.blockworktableIdle.blockID);
        }
        keepFurnaceInventory = false;

        world.setBlockMetadataWithNotify(i, j, k, l);
        tileentity.validate();
        world.setBlockTileEntity(i, j, k, tileentity);
    }

	public TileEntity createNewTileEntity(World par1World)
    {
        return new TileEntityWorktable();

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

	public void breakBlock(World world, int i, int j, int k,int par5, int par6)
    {
        if(!keepFurnaceInventory)

        {
            TileEntityWorktable tileentityfurnace = (TileEntityWorktable)world.getBlockTileEntity(i, j, k);
label0:
            for(int l = 0; l < tileentityfurnace.getSizeInventory(); l++)
            {
                ItemStack itemstack = tileentityfurnace.getStackInSlot(l);
                if(itemstack == null)
                {
                    continue;
                }
                float f = furnaceRand.nextFloat() * 0.8F + 0.1F;
                float f1 = furnaceRand.nextFloat() * 0.8F + 0.1F;
                float f2 = furnaceRand.nextFloat() * 0.8F + 0.1F;
                do
                {
                    if(itemstack.stackSize <= 0)
                    {
                        continue label0;
                    }
                    int i1 = furnaceRand.nextInt(21) + 10;
                    if(i1 > itemstack.stackSize)
                    {
                        i1 = itemstack.stackSize;
                    }
                    itemstack.stackSize -= i1;
                    EntityItem entityitem = new EntityItem(world, (float)i + f, (float)j + f1, (float)k + f2, new ItemStack(itemstack.itemID, i1, itemstack.getItemDamage()));
                    float f3 = 0.05F;
                    entityitem.motionX = (float)furnaceRand.nextGaussian() * f3;
                    entityitem.motionY = (float)furnaceRand.nextGaussian() * f3 + 0.2F;
                    entityitem.motionZ = (float)furnaceRand.nextGaussian() * f3;
                    world.spawnEntityInWorld(entityitem);
                } while(true);
            }

        }
        super.breakBlock(world, i, j, k,par5, par6);
    }
	




}
