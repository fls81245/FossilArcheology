package net.minecraft.src;
import java.util.Iterator;
import java.util.List;
import java.util.Random;


public class ItemAncientEgg extends Item{

/*private final int Triceratops=0;
private final int Raptor=1;
public final int TRex=2;
public final int Pterosaur=3;
public final int Nautilus=4;
public final int Plesiosaur=5;
public final int Mosasaurus=6;
public int[] Animaltype={Triceratops,Raptor,TRex,Pterosaur,Nautilus,Plesiosaur,Mosasaurus};
*/
	public ItemAncientEgg(int i){
		super(i);
		setHasSubtypes(true);
		setMaxDamage(0);
		maxStackSize=1;
	}
    public String getTextureFile()
    {
       return "/skull/Fos_items.png";
    }
	public int getIconFromDamage(int i)
    {
        if (i<TypeCount) return 22+i;
        else return 0;
    }
	public String getItemNameIS(ItemStack itemstack)
    {
		switch(GetTypeFromInt(itemstack.getItemDamage())){
			case Triceratops:
				return "Eggtriceratops";
			case Raptor:
				return "EggRaptor";
			case TRex:
				return "EggTRex";
			case Pterosaur:
				return "EggPterosaur";
			case Nautilus:
				return "ShellNautilus";
			case Plesiosaur:
				return "EggPlesiosaur";
			case Mosasaurus:
				return "EggMosasaurus";
			case Stegosaurus:
				return "EggStegosaurus";
			case dilphosaur:
				return "EggUtahraptor";
			case Brachiosaurus:
				return "EggBrachiosaurus";
			default:
				return "Ancient egg";
		}
    }
	public ItemStack onItemRightClick(ItemStack itemstack, World world,EntityPlayer entityplayer )
    {
        float var4 = 1.0F;
        float var5 = entityplayer.prevRotationPitch + (entityplayer.rotationPitch - entityplayer.prevRotationPitch) * var4;
        float var6 = entityplayer.prevRotationYaw + (entityplayer.rotationYaw - entityplayer.prevRotationYaw) * var4;
        double var7 = entityplayer.prevPosX + (entityplayer.posX - entityplayer.prevPosX) * (double)var4;
        double var9 = entityplayer.prevPosY + (entityplayer.posY - entityplayer.prevPosY) * (double)var4 + 1.62D - (double)entityplayer.yOffset;
        double var11 = entityplayer.prevPosZ + (entityplayer.posZ - entityplayer.prevPosZ) * (double)var4;
        Vec3 var13 = world.func_82732_R().getVecFromPool(var7, var9, var11);
        float var14 = MathHelper.cos(-var6 * 0.017453292F - (float)Math.PI);
        float var15 = MathHelper.sin(-var6 * 0.017453292F - (float)Math.PI);
        float var16 = -MathHelper.cos(-var5 * 0.017453292F);
        float var17 = MathHelper.sin(-var5 * 0.017453292F);
        float var18 = var15 * var16;
        float var20 = var14 * var16;
        double var21 = 5.0D;
        Vec3 var23 = var13.addVector((double)var18 * var21, (double)var17 * var21, (double)var20 * var21);
        MovingObjectPosition var24 = world.rayTraceBlocks_do(var13, var23, true);

        if (var24 == null)
        {
            return itemstack;
        }
        else
        {
            Vec3 var25 = entityplayer.getLook(var4);
            boolean var26 = false;
            float var27 = 1.0F;
            List var28 = world.getEntitiesWithinAABBExcludingEntity(entityplayer, entityplayer.boundingBox.addCoord(var25.xCoord * var21, var25.yCoord * var21, var25.zCoord * var21).expand((double)var27, (double)var27, (double)var27));
            Iterator var29 = var28.iterator();

            while (var29.hasNext())
            {
                Entity var30 = (Entity)var29.next();

                if (var30.canBeCollidedWith())
                {
                    float var31 = var30.getCollisionBorderSize();
                    AxisAlignedBB var32 = var30.boundingBox.expand((double)var31, (double)var31, (double)var31);

                    if (var32.isVecInside(var13))
                    {
                        var26 = true;
                    }
                }
            }

            if (var26)
            {
                return itemstack;
            }
            else
            {
                if (var24.typeOfHit == EnumMovingObjectType.TILE)
                {
                    int var35 = var24.blockX;
                    int var33 = var24.blockY;
                    int var34 = var24.blockZ;

                    if (!world.isRemote)
                    {
                        if (world.getBlockId(var35, var33, var34) == Block.snow.blockID)
                        {
                            --var33;
                        }

                        if (!spawnCreature(world, this.GetTypeFromInt(itemstack.getItemDamage()), (double)((float)var35 + 0.5F), (double)((float)var33 + 1.0F), (double)((float)var34 + 0.5F))) return itemstack;
                    }

                    if (!entityplayer.capabilities.isCreativeMode)
                    {
                        --itemstack.stackSize;
                    }
                }

                return itemstack;
            }
        }


	}
	/*public boolean tryPlaceIntoWorld(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
	    {
	        if (par3World.isRemote)
	        {
	            return true;
	        }
	        else
	        {
	        	
	            int var11 = par3World.getBlockId(par4, par5, par6);
	            par4 += Facing.offsetsXForSide[par7];
	            par5 += Facing.offsetsYForSide[par7];
	            par6 += Facing.offsetsZForSide[par7];
	            double var12 = 0.0D;

	            if (par7 == 1 && var11 == Block.fence.blockID || var11 == Block.netherFence.blockID)
	            {
	                var12 = 0.5D;
	            }

	            if (spawnCreature(par3World, GetTypeFromInt(par1ItemStack.getItemDamage()), (double)par4 + 0.5D, (double)par5 + var12, (double)par6 + 0.5D) && !par2EntityPlayer.capabilities.isCreativeMode)
	            {
	                --par1ItemStack.stackSize;
	            }

	            return true;
	        }
	    }*/

	    /**
	     * Spawns the creature specified by the egg's type in the location specified by the last three parameters.
	     * Parameters: world, entityID, x, y, z.
	     */
	    public static boolean spawnCreature(World par0World, EnumDinoType enumDinoType, double par2, double par4, double par6)
	    {
	            Entity var8;
	            if (enumDinoType==EnumDinoType.Nautilus) var8=new EntityNautilus(par0World);
	            else var8 = new EntityDinoEgg(par0World,enumDinoType);
	            
	            if (var8 != null)
	            {
	                var8.setLocationAndAngles(par2, par4, par6, par0World.rand.nextFloat() * 360.0F, 0.0F);

	                par0World.spawnEntityInWorld(var8);
	            }

	            return var8 != null;
	        
	    }

	private EnumDinoType GetTypeFromInt(int data){
		EnumDinoType[] resultArray=EnumDinoType.values();
		return resultArray[data];
	}
	public static final int TypeCount=EnumDinoType.values().length;
    public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        for (int var4 = 0; var4 < EnumDinoType.values().length; ++var4)
        {
            par3List.add(new ItemStack(par1, 1, var4));
        }
    }
}