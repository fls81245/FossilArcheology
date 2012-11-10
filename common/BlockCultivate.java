package net.minecraft.src;
import java.util.Random;
public class BlockCultivate extends BlockContainer 
{
	private final String VAT="Vat.";
	private final String ERR_OUTBREAK="Err.OutBreak";

    private Random furnaceRand;
    private final boolean isActive;
    private static boolean keepFurnaceInventory = false;
	protected BlockCultivate(int i, boolean flag)
    {
        super(i, Material.glass);
        furnaceRand = new Random();
        isActive = flag;
		blockIndexInTexture = mod_Fossil.cultivate_side_off;
    }

 
	public int idDropped(int i, Random random,int unusedj)
    {
        return mod_Fossil.blockcultivateIdle.blockID;
    }
	public void onBlockAdded(World world, int i, int j, int k)
    {
        super.onBlockAdded(world, i, j, k);
        setDefaultDirection(world, i, j, k);
    }
    /*public void updateTick(World world, int i, int j, int k, Random random)
    {
    	mod_Fossil.cultivate_side_on++;
    	if (mod_Fossil.cultivate_side_on>=44)mod_Fossil.cultivate_side_on=38;
    }*/
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
            return mod_Fossil.cultivate_top;
        }
        if(l == 0)
        {
            return mod_Fossil.cultivate_top;
        }
        int i1 = iblockaccess.getBlockMetadata(i, j, k);
        if(l != i1)
        {
            if(isActive)
			{
				return mod_Fossil.cultivate_side_on;
			} else
			{
				return mod_Fossil.cultivate_side_off;
			}
        }
        if(isActive)
        {
            return mod_Fossil.cultivate_side_on;
        } else
        {
            return mod_Fossil.cultivate_side_off;
        }
    }
	public void randomDisplayTick(World world, int i, int j, int k, Random random)
    {
        //if(!isActive)
        //{
            return;
        //}
		/*
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
        }
		*/
    }
	public int getBlockTextureFromSide(int i)
    {
        if(i == 1)
        {
            return mod_Fossil.cultivate_top;
        }
        if(i == 0)
        {
            return mod_Fossil.cultivate_top;
        }
        if(i == 3)
        {
            return mod_Fossil.cultivate_side_off;
        } else
        {
            return mod_Fossil.cultivate_side_off;
        }
    }
	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
        if(par1World.isRemote)
        {
            return true;
        } else
        {
        	mod_Fossil.callGUI(par5EntityPlayer,FossilGuiHandler.CULTIVE_GUI_ID,par1World, par2, par3, par4);
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
			world.setBlockWithNotify(i, j, k, mod_Fossil.blockcultivateActive.blockID);
        } else
        {
			world.setBlockWithNotify(i, j, k, mod_Fossil.blockcultivateIdle.blockID);
        }
        keepFurnaceInventory = false;
        world.setBlockMetadataWithNotify(i, j, k, l);
		tileentity.validate();
        world.setBlockTileEntity(i, j, k, tileentity);
    }
	public TileEntity createNewTileEntity(World par1World)
    {
        return new TileEntityCultivate();

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
	private void ReturnIron(World world,int i, int j, int k ){
        ItemStack itemstack = new ItemStack(Item.ingotIron,3);
        float f = furnaceRand.nextFloat() * 0.8F + 0.1F;
        float f1 = furnaceRand.nextFloat() * 0.8F + 0.1F;
        float f2 = furnaceRand.nextFloat() * 0.8F + 0.1F;
        do
        {
            if(itemstack.stackSize <= 0)
            {
                break;
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
	public void onBlockRemovalLost( World world, int i, int j, int k ,boolean flag)
    {
		keepFurnaceInventory=false;
		//onBlockRemoval(world,i,j,k);
		String MsgTmp=mod_Fossil.GetLangTextByKey(VAT+ERR_OUTBREAK);
		for (int i1=0;i1<world.playerEntities.size();i1++){
			mod_Fossil.ShowMessage(MsgTmp,(EntityPlayer) world.playerEntities.get(i1));
		}

		ReturnIron(world,i,j,k);
		world.setBlockWithNotify(i, j, k, 0);
		world.removeBlockTileEntity(i, j, k);
		if (flag){
			//if (isActive) {
				EntityLiving entityliving=null;
				world.playAuxSFX(2001, i, j, k, Block.glass.blockID);
				world.setBlockWithNotify(i, j, k, Block.waterMoving.blockID);
				if (world.isRemote) return;
				
				int randChance=world.rand.nextInt(100);
				if (randChance<=5) {
					entityliving = (EntityLiving)new EntityCreeper(world);
				}
				if (randChance>5 && randChance<10){
					entityliving = (EntityLiving)new EntityPigZombie(world);				
				}
				if (randChance>=10){
					entityliving=(EntityLiving)new EntityFailuresaurus(world);
				}
				entityliving.setLocationAndAngles(i, j, k, world.rand.nextFloat() * 360F, 0.0F);
				world.spawnEntityInWorld(entityliving);
			//}
		}
	}
	public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
        if(!keepFurnaceInventory)
        {
            TileEntityCultivate tileentityfurnace = (TileEntityCultivate)par1World.getBlockTileEntity(par2, par3, par4);

label0:
            for(int l = 0; l < tileentityfurnace.getSizeInventory(); l++)

            {
                ItemStack var9 = tileentityfurnace.getStackInSlot(l);

                if(var9 == null)
                {
                    continue;
                }
                float var10 = furnaceRand.nextFloat() * 0.8F + 0.1F;
                float var11 = furnaceRand.nextFloat() * 0.8F + 0.1F;
                float var12 = furnaceRand.nextFloat() * 0.8F + 0.1F;
                do
                {
                    if(var9.stackSize <= 0)
                    {
                        continue label0;
                    }
                    int var13 = furnaceRand.nextInt(21) + 10;
                    if(var13 > var9.stackSize)
                    {
                        var13 = var9.stackSize;
                    }
                    var9.stackSize -= var13;
                    EntityItem entityitem = new EntityItem(par1World, (double)((float)par2 + var10), (double)((float)par3 + var11), (double)((float)par4 + var12), new ItemStack(var9.itemID, var13, var9.getItemDamage()));

                    float f3 = 0.05F;
                    entityitem.motionX = (float)furnaceRand.nextGaussian() * f3;
                    entityitem.motionY = (float)furnaceRand.nextGaussian() * f3 + 0.2F;
                    entityitem.motionZ = (float)furnaceRand.nextGaussian() * f3;
                    par1World.spawnEntityInWorld(entityitem);
                } while(true);
            }

        }
        super.breakBlock(par1World, par2, par3, par4, par5, par6);

    }
    public String getTextureFile()
    {
       return "/skull/Fos_terrian.png";
    }



}