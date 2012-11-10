package net.minecraft.src;

public class PigmenSpeaker {
		EntityFriendlyPigZombie speaker=null;
	public PigmenSpeaker(EntityFriendlyPigZombie speaker) {
		super();
		this.speaker = speaker;
	}
	public void SendSpeech(EnumPigmenSpeaks speak){
		SendSpeech(speak,"Notch");
	}
	public void SendSpeech(EnumPigmenSpeaks speak,String Args1){
		/*if (speaker!=null && speaker.maskSpeech) return;
		String result="";
		final String SPEAK="FPZSpeaker.";
		String Self=mod_Fossil.GetLangTextByKey(SPEAK+"self");
		if (speak==EnumPigmenSpeaks.LifeFor){
			if (mod_Fossil.ReverseLang){
				result=mod_Fossil.GetLangTextByKey(SPEAK+speak.toString()+".tail")+Args1+mod_Fossil.GetLangTextByKey(SPEAK+speak.toString()+".head");
			}else{
				result=mod_Fossil.GetLangTextByKey(SPEAK+speak.toString()+".head")+Args1+mod_Fossil.GetLangTextByKey(SPEAK+speak.toString()+".tail");
			}
		}else{
			result=mod_Fossil.GetLangTextByKey(SPEAK+speak.toString());
		}
		if (mod_Fossil.ReverseLang){
			mod_Fossil.ShowMessage(new StringBuilder().append(result).append(Self).toString());			
		}else{
			mod_Fossil.ShowMessage(new StringBuilder().append(Self).append(result).toString());
		}*/

	}
}
