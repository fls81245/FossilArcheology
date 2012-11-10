package net.minecraft.src;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Properties;
import net.minecraft.client.Minecraft;

public class TimeMachineCfgLoader{
	private static final File cfgdir;
    private static final File cfgfile;
	public static final Properties props = new Properties();
	static 
    {
        cfgdir = new File(Minecraft.getMinecraftDir(), "/config/");
        cfgfile = new File(cfgdir, "AddonTimeMachine.cfg");
    }
	public void CfgLoader(){
	}
	public static Properties loadConfig()
        throws IOException
    {
        cfgdir.mkdir();
        if(!cfgfile.exists() && !cfgfile.createNewFile())
        {
            return null;
        }
        if(cfgfile.canRead())
        {
            FileInputStream fileinputstream = new FileInputStream(cfgfile);
            props.load(fileinputstream);
            fileinputstream.close();
			return props;
        }else return null;
    }
	public static void saveConfig(Properties props)
        throws IOException
    {
        cfgdir.mkdir();
        if(!cfgfile.exists() && !cfgfile.createNewFile())
        {
            return;
        }
        if(cfgfile.canWrite())
        {
            FileOutputStream fileoutputstream = new FileOutputStream(cfgfile);
            props.store(fileoutputstream, "AddonTimeMachine Config");
            fileoutputstream.close();
        }
    }
}