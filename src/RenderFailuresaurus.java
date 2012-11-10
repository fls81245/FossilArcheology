package net.minecraft.src;

public class RenderFailuresaurus extends RenderLiving{
	public RenderFailuresaurus (ModelBase modelbase, float f){
		super(modelbase,f);
	}
    public void renderCow(EntityFailuresaurus entitytiger, double d, double d1, double d2, 
            float f, float f1)
    {
        super.doRenderLiving(entitytiger, d, d1, d2, f, f1);
    }

    public void doRenderLiving(EntityLiving entityliving, double d, double d1, double d2, 
            float f, float f1)
    {
        renderCow((EntityFailuresaurus)entityliving, d, d1, d2, f, f1);
    }

    public void doRender(Entity entity, double d, double d1, double d2, 
            float f, float f1)
    {
        renderCow((EntityFailuresaurus)entity, d, d1, d2, f, f1);
    }
}

