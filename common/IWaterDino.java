package net.minecraft.src;

public interface IWaterDino {
	public  final float IDLE_SINK_SPEED=-0.00375f;
	public final float SINK_SPEED=IDLE_SINK_SPEED*5.0F;
	public final float FLOAT_SPEED=SINK_SPEED*-5.0f;
	public abstract boolean isOnSurface();
	//public abstract float getSurfaceDistance();
}
