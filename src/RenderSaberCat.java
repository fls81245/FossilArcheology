package net.minecraft.src;

public class RenderSaberCat extends RenderLiving
{
    public RenderSaberCat(ModelBase modelbase, float f)
    {
        super(modelbase, f);
    }

    public void renderSaberCat(EntitySaberCat EntitySaberCat, double d, double d1, double d2,
            float f, float f1)
    {
        super.doRenderLiving(EntitySaberCat, d, d1, d2, f, f1);
    }

    protected float func_25004_a(EntitySaberCat EntitySaberCat, float f)
    {
        return EntitySaberCat.setTailRotation();
    }

    protected void func_25006_b(EntitySaberCat EntitySaberCat, float f)
    {
    }

    protected void preRenderCallback(EntityLiving entityliving, float f)
    {
        func_25006_b((EntitySaberCat)entityliving, f);
    }

    protected float handleRotationFloat(EntityLiving entityliving, float f)
    {
        return func_25004_a((EntitySaberCat)entityliving, f);
    }

    public void doRenderLiving(EntityLiving entityliving, double d, double d1, double d2,
            float f, float f1)
    {
        renderSaberCat((EntitySaberCat)entityliving, d, d1, d2, f, f1);
    }

    public void doRender(Entity entity, double d, double d1, double d2,
            float f, float f1)
    {
        renderSaberCat((EntitySaberCat)entity, d, d1, d2, f, f1);
    }
}
