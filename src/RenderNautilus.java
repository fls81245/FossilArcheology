// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft.src;

import org.lwjgl.opengl.GL11;

// Referenced classes of package net.minecraft.src:
//            RenderLiving, EntitySquid, ModelBase, EntityLiving, 
//            Entity

public class RenderNautilus extends RenderLiving
{

    public RenderNautilus(ModelBase modelbase, float f)
    {
        super(modelbase, f);
    }

    public void func_77123_a(EntityNautilus entityNautilus, double d, double d1, double d2, 
            float f, float f1)
    {
        super.doRenderLiving(entityNautilus, d, d1, d2, f, f1);
    }

    protected void func_77122_a(EntityNautilus entityNautilus, float f, float f1, float par4)
    {
        //float var5 = entityNautilus.field_70862_e + (entityNautilus.field_70861_d - entityNautilus.field_70862_e) * par4;
        float var6 = entityNautilus.field_70860_g + (entityNautilus.field_70859_f - entityNautilus.field_70860_g) * par4;
        GL11.glTranslatef(0.0F, 0.5F, 0.0F);
        GL11.glRotatef(180F - f1, 0.0F, 1.0F, 0.0F);
        //GL11.glRotatef(f3, 1.0F, 0.0F, 0.0F);
        GL11.glRotatef(var6, 0.0F, 1.0F, 0.0F);
        GL11.glTranslatef(0.0F, -1.2F, 0.0F);
    }


    protected float handleRotationFloat(EntityNautilus entitysquid, float f)
    {
        float f1 = entitysquid.lastTentacleAngle + (entitysquid.tentacleAngle - entitysquid.lastTentacleAngle) * f;
        return f1;
    }


    protected float handleRotationFloat(EntityLiving entityliving, float f)
    {
        return handleRotationFloat((EntityNautilus)entityliving, f);
    }

    protected void rotateCorpse(EntityLiving entityliving, float f, float f1, float f2)
    {
    	func_77122_a((EntityNautilus)entityliving, f, f1, f2);
    }

    public void doRenderLiving(EntityLiving entityliving, double d, double d1, double d2, 
            float f, float f1)
    {
    	func_77123_a((EntityNautilus)entityliving, d, d1, d2, f, f1);
    }

    public void doRender(Entity entity, double d, double d1, double d2, 
            float f, float f1)
    {
    	func_77123_a((EntityNautilus)entity, d, d1, d2, f, f1);
    }
}
