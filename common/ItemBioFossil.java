package net.minecraft.src;

import java.lang.reflect.InvocationTargetException;
import java.util.Random;


public class ItemBioFossil extends Item {
	public ItemBioFossil(int i){
		super(i);
		maxStackSize=64;
	}
    public String getTextureFile()
    {
       return "/skull/Fos_items.png";
    }
    /*public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
    {
			Class choose=getRandomModel().getDinoClass();
			EntityDinosaurce entityModel;
			try {
				entityModel = (EntityDinosaurce)choose.getConstructor(new Class[] {World.class}).newInstance(new Object[] {world});
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			} 
			entityModel.setModelized(true);
			entityModel.setLocationAndAngles(i, j + 1, k, world.rand.nextFloat() * 360F, 0.0F);
			entityModel.faceEntity(entityplayer, 360F, 360F);
			if (world.checkIfAABBIsClear(entityModel.boundingBox) && world.getCollidingBoundingBoxes(entityModel, entityModel.boundingBox).size() == 0 ) {
				world.spawnEntityInWorld(entityModel);
				itemstack.stackSize--;
				return true;
			}
			return false;
	}*/
    public boolean tryPlaceIntoWorld(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        if (par3World.isRemote)
        {
            return true;
        }
        Class choose=getRandomModel().getDinoClass();
		EntityDinosaurce entityModel;
		try {
			entityModel = (EntityDinosaurce)choose.getConstructor(new Class[] {World.class}).newInstance(new Object[] {par3World});
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} 
		entityModel.setModelized(true);
		entityModel.setLocationAndAngles(par4, par5 + 1, par6, par3World.rand.nextFloat() * 360F, 0.0F);
		entityModel.faceEntity(par2EntityPlayer, 360F, 360F);
		if (par3World.checkIfAABBIsClear(entityModel.boundingBox) && par3World.getCollidingBoundingBoxes(entityModel, entityModel.boundingBox).size() == 0 ) {
			par3World.spawnEntityInWorld(entityModel);
			par1ItemStack.stackSize--;
			return true;
		}
		return false;
    }

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
    private EnumDinoType getRandomModel(){
    	EnumDinoType[] list=EnumDinoType.values();
    	int totalCount=list.length;
    	EnumDinoType result;
    	Random rand=new Random();
    	do{
    		result=list[rand.nextInt(totalCount)];
    	}while(!result.isModelable());
    	return result;
    }
}