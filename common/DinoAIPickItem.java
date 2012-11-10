package net.minecraft.src;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class DinoAIPickItem extends EntityAIBase
{
    private EntityDinosaurce entityVar;
    private double destX;
    private double destY;
    private double destZ;
    private float field_48317_e;
    private final int SEARCH_RANGE;
    private final float HUNT_LIMIT;
    private final int USE_RANGE=3;
    private EntityItem targetItem=null;
    private DinoAINearestAttackableTargetSorter targetSorter;
    private final Item WANTED_ITEM;
    protected boolean shouldCheckHunt=false;
    

    public DinoAIPickItem(EntityDinosaurce par1EntityCreature, Item wanted,float par2,int rangePar,float HuntLimitPar)
    {
    	this(par1EntityCreature, wanted, par2, rangePar, HuntLimitPar, false);
    }
    public DinoAIPickItem(EntityDinosaurce par1EntityCreature, Item wanted,float par2,int rangePar,float HuntLimitPar,boolean checkHunt)
    {
        this.entityVar = par1EntityCreature;
        this.field_48317_e = par2;
        this.setMutexBits(1);
        this.SEARCH_RANGE=rangePar;
        HUNT_LIMIT=HuntLimitPar;
        targetSorter=new DinoAINearestAttackableTargetSorter(this,entityVar);
        WANTED_ITEM=wanted;
        shouldCheckHunt=checkHunt;
    }
    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
    		if (entityVar.getHunger()>HUNT_LIMIT) return false;
            Vec3 var1 = getNearestItem();

            if (var1 == null)
            {
                return false;
            }
            else
            {
                this.destX = var1.xCoord;
                this.destY = var1.yCoord;
                this.destZ = var1.zCoord;
                return true;
            }
    }
    
    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting()
    {
        return !this.entityVar.getNavigator().noPath() && targetItem.isEntityAlive();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
    	if ((Math.pow((entityVar.posX-destX),2)+Math.pow((entityVar.posZ-destZ), 2))<Math.pow(this.USE_RANGE,2)){
    		this.entityVar.PickUpItem(targetItem.item.getItem());
    		targetItem.setDead();
    		this.entityVar.getNavigator().clearPathEntity();
    	}
    	else this.entityVar.getNavigator().tryMoveToXYZ(this.destX, this.destY, this.destZ, this.field_48317_e);
    }
    private Vec3 getNearestItem(){
    	List var5 = this.entityVar.worldObj.getEntitiesWithinAABB(EntityItem.class, this.entityVar.boundingBox.expand((double)this.SEARCH_RANGE, 4.0D, (double)this.SEARCH_RANGE));
        Collections.sort(var5, this.targetSorter);
        Iterator var2 = var5.iterator();
        Vec3 result=null;
        while (var2.hasNext())
        {
            EntityItem var3 = (EntityItem)var2.next();
            if (WANTED_ITEM==null || var3.item.itemID==WANTED_ITEM.shiftedIndex){
            		targetItem=var3;
            	   result=Vec3.createVectorHelper(var3.posX, var3.posY, var3.posZ);
            	   break;
            }           
        }
        return result;
    }
    protected boolean checkTargetReachable(Entity par1Entity, boolean par2)
    {
        if (par1Entity == null)
        {
            return false;
        }
        else if (!par1Entity.isEntityAlive())
        {
            return false;
        }
        else if (par1Entity.boundingBox.maxY > this.entityVar.boundingBox.minY && par1Entity.boundingBox.minY < this.entityVar.boundingBox.maxY)
        {
            /*if (!this.entityVar.func_48100_a(par1Entity.getClass()))
            {
                return false;
            }
            else
            {*/
                if (this.entityVar instanceof EntityTameable && ((EntityTameable)this.entityVar).isTamed())
                {
                    if (par1Entity instanceof EntityTameable && ((EntityTameable)par1Entity).isTamed())
                    {
                        return false;
                    }

                    if (par1Entity == ((EntityTameable)this.entityVar).getOwner())
                    {
                        return false;
                    }
                }
                else if (par1Entity instanceof EntityPlayer && !par2 && ((EntityPlayer)par1Entity).capabilities.disableDamage)
                {
                    return false;
                }

                if (!this.entityVar.isWithinHomeDistance(MathHelper.floor_double(par1Entity.posX), MathHelper.floor_double(par1Entity.posY), MathHelper.floor_double(par1Entity.posZ)))
                {
                    return false;
                }
                else
                {

                    return true;
                }
            //}
        }
        else
        {
            return false;
        }
    }
}
