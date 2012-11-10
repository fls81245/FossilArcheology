package net.minecraft.src;
import java.util.Random;
public class BlockFossilSkull extends BlockDirectional{
    private boolean blockType;
	public BlockFossilSkull(int i, int j, boolean flag){
		super(i, Material.pumpkin);
        blockIndexInTexture = j;
        setTickRandomly(true);
        blockType = flag;
	}
    public String getTextureFile()
    {
       return "/skull/Fos_terrian.png";
    }
	public int getBlockTextureFromSideAndMetadata(int i, int j)
    {
        if(i == 1)
        {
            return mod_Fossil.skull_side;
        }
        if(i == 0)
        {
            return mod_Fossil.skull_side;
        }
        int k = mod_Fossil.skull_face_off;
        if(blockType)
        {
            k=mod_Fossil.skull_face_on;
        }
        if(j == 2 && i == 2)
        {
            return k;
        }
        if(j == 3 && i == 5)
        {
            return k;
        }
        if(j == 0 && i == 3)
        {
            return k;
        }
        if(j == 1 && i == 4)
        {
            return k;
        } else
        {
            return mod_Fossil.skull_top;
        }
    }
	public int getBlockTextureFromSide(int i)
    {
        if(i == 1)
        {
            return mod_Fossil.skull_side;
        }
        if(i == 0)
        {
            return mod_Fossil.skull_side;
        }
        if(i == 3)
        {
            return mod_Fossil.skull_face_off;
        } else
        {
            return mod_Fossil.skull_top;
        }
    }
	public void onBlockAdded(World world, int i, int j, int k)
    {
        super.onBlockAdded(world, i, j, k);
    }

    public void onBlockPlacedBy(World world, int i, int j, int k, EntityLiving entityliving)
    {
        int l = MathHelper.floor_double((double)((entityliving.rotationYaw * 4F) / 360F) + 2.5D) & 3;
        world.setBlockMetadataWithNotify(i, j, k, l);
    }
	/*    public int idDropped(int i, Random random)
    {
        if (!blockType) return mod_Fossil.blockSkull.blockID;
		else return mod_Fossil.SkullLantern.blockID;
    }*/
}