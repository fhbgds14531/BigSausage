package net.mizobo.bigsausage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public enum EnumClips {
	sausage(BigSausage.sausage, BigSausage.sausageList), ugly(BigSausage.ugly, BigSausage.uglyList), enemy(BigSausage.enemy, BigSausage.enemyList), 
	fire(BigSausage.fire, BigSausage.fireList), linked(BigSausage.linked, BigSausage.linkedList), miceway(BigSausage.miceway, BigSausage.miceWayList), 
	sceptre(BigSausage.sceptre, BigSausage.sceptreList), hatemyself(BigSausage.hatemyself, BigSausage.hateMyselfList), hcw(BigSausage.hcw, BigSausage.hcwList), 
	korean(BigSausage.korean, BigSausage.koreanList), bursela(BigSausage.bursela, BigSausage.burselaList), burse(BigSausage.burse, BigSausage.burseList), 
	whiskey(BigSausage.whiskey, BigSausage.whiskeyList), egg(BigSausage.egg, BigSausage.eggList), choice(BigSausage.choice, BigSausage.choiceList), 
	grunch(BigSausage.grunch, BigSausage.grunchList), sainte(BigSausage.sainte, BigSausage.sainteList);

	private File file;
	private List<String> defaultTriggers;

	private EnumClips(File fileToPlay, List<String> defaultTriggers) {
		this.file = fileToPlay;
		this.defaultTriggers = defaultTriggers;
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
	
	public List<String> getDefaultTriggers(){
		List<String> list = new ArrayList<String>();
		list.addAll(defaultTriggers);
		return list;
	}

	public static String getCommaSeparatedList() {
		String out = "";
		for (EnumClips clip : EnumClips.values()) {
			out += clip.toString() + ", ";
		}
		return out.substring(0, out.lastIndexOf(", "));
	}

}
