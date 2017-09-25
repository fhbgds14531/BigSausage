package net.mizobo.bigsausage;

import java.io.File;

public enum EnumClips {
	sausage(BigSausage.sausage), ugly(BigSausage.ugly), enemy(BigSausage.enemy), fire(BigSausage.fire), 
	linked(BigSausage.linked), miceway(BigSausage.miceway), sceptre(BigSausage.sceptre), hatemyself(BigSausage.hatemyself), 
	hcw(BigSausage.hcw), korean(BigSausage.korean), bursela(BigSausage.burse), burse(BigSausage.burse), whiskey(BigSausage.whiskey), egg(BigSausage.egg),
	choice(BigSausage.choice), grunch(BigSausage.grunch), sainte(BigSausage.sainte);

	private File file;

	private EnumClips(File fileToPlay) {
		this.file = fileToPlay;
	}

	public static EnumClips getFromString(String s) {
		switch (s) {
			case "sausage":
				return sausage;
			case "ugly":
				return ugly;
			case "enemy":
				return enemy;
			case "fire":
				return fire;
			case "linked":
				return linked;
			case "miceway":
				return miceway;
			case "sceptre":
				return sceptre;
			case "hatemyself":
				return hatemyself;
			case "hcw":
				return hcw;
			case "korean":
				return korean;
			case "bursela":
				return bursela;
			case "burse":
				return burse;
			case "whiskey":
				return whiskey;
			case "choice":
				return choice;
			case "grunch":
				return grunch;
			case "sainte":
				return sainte;
			case "egg":
				return egg;
			default:
				return null;
		}
	}
	
	public File getFile(){
		return this.file;
	}

	public static String getCommaSeparatedList() {
		String out = "";
		for (EnumClips clip : EnumClips.values()) {
			out += clip.toString() + ", ";
		}
		return out.substring(0, out.lastIndexOf(", "));
	}

}
