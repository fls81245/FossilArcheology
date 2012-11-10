// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft.src;

import org.lwjgl.opengl.GL11;

// Referenced classes of package net.minecraft.src:
//            RenderLiving, EntityLiving, ModelBiped, ModelRenderer, 
//            ItemStack, Block, RenderBlocks, Item, 
//            RenderManager, ItemRenderer

public class RenderPigBoss extends RenderBiped
{

    public RenderPigBoss(ModelBiped modelbiped, float f)
    {
        super(modelbiped, f);
        setRenderPassModel(new ModelPigBoss());
    }
    public void doRenderLiving(EntityLiving entityliving, double d, double d1, double d2, 
            float f, float f1)
    {
    	((ModelPigBoss)(this.mainModel)).RangedAttack=(((EntityPigBoss)entityliving).getAttackMode()==1);
    	super.doRenderLiving(entityliving, d, d1, d2, f, f1);
    	
    }
    protected int shouldRenderPass(EntityLiving entityliving, int i, float f)
    {
        return setChargeLineBrightness((EntityPigBoss)entityliving, i, f);
    }
    public int setChargeLineBrightness(EntityPigBoss entitypigboss, int i, float f){
        if(i != 0)
        {
            return -1;
        }
    	if (entitypigboss.FireballCount>=50){
    		loadTexture("/skull/PigBossCharged_r.png");
    	}else{
    		loadTexture("/skull/PigBoss_r.png");
    	}
    	float f1 = (1.0F - entitypigboss.getBrightness(1.0F)) * 0.5F;
        GL11.glEnable(3042 /*GL_BLEND*/);
        GL11.glDisable(3008 /*GL_ALPHA_TEST*/);
        GL11.glBlendFunc(770, 771);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, f1*100);
        return 1;
    }
}
