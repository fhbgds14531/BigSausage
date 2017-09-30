package net.mizobo.bigsausage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public enum EnumClips {
	sausage(BigSausage.sausage, BigSausage.sausageList, false), ugly(BigSausage.ugly, BigSausage.uglyList, false), enemy(BigSausage.enemy, BigSausage.enemyList, false), 
	fire(BigSausage.fire, BigSausage.fireList, false), linked(BigSausage.linked, BigSausage.linkedList, false), miceway(BigSausage.miceway, BigSausage.miceWayList, false), 
	sceptre(BigSausage.sceptre, BigSausage.sceptreList, false), hatemyself(BigSausage.hatemyself, BigSausage.hateMyselfList, false), hcw(BigSausage.hcw, BigSausage.hcwList, false), 
	korean(BigSausage.korean, BigSausage.koreanList, false), bursela(BigSausage.bursela, BigSausage.burselaList, false), burse(BigSausage.burse, BigSausage.burseList, false), 
	whiskey(BigSausage.whiskey, BigSausage.whiskeyList, false), egg(BigSausage.egg, BigSausage.eggList, false), choice(BigSausage.choice, BigSausage.choiceList, false), 
	grunch(BigSausage.grunch, BigSausage.grunchList, false), sainte(BigSausage.sainte, BigSausage.sainteList, false), silence(BigSausage.silence, BigSausage.emptyList, true), 
	never(BigSausage.never, BigSausage.emptyList, true);

	private File file;
	private List<String> defaultTriggers;
	private boolean hidden;

	private EnumClips(File fileToPlay, List<String> defaultTriggers, boolean hidden) {
		this.file = fileToPlay;
		this.defaultTriggers = defaultTriggers;
		this.hidden = hidden;
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
			case "silence":
				return silence;
			case "never":
				return never;
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
			if(!clip.hidden){
				out += clip.toString() + ", ";
			}
		}
		return out.substring(0, out.lastIndexOf(", "));
	}

}
