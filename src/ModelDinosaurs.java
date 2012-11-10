package net.minecraft.src;

public abstract class ModelDinosaurs extends ModelBase {

	public void setRotationAngles(float f, float f1, float f2,
			float f3, float f4, float f5) {
				  this.setRotationAngles(f, f1, f2, f3, f4, f5,false);
			  }

	protected abstract void setRotationAngles(float f, float f1, float f2, float f3,
			float f4, float f5, boolean modelized);
	
}
