package net.mizobo.bigsausage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public enum EnumClips implements ILinkable{
	
	sausage(BigSausage.sausage, BigSausage.sausageList, false), ugly(BigSausage.ugly, BigSausage.uglyList, false), enemy(BigSausage.enemy, BigSausage.enemyList, false), 
	fire(BigSausage.fire, BigSausage.fireList, false), linked(BigSausage.linked, BigSausage.linkedList, false), miceway(BigSausage.miceway, BigSausage.miceWayList, false), 
	sceptre(BigSausage.sceptre, BigSausage.sceptreList, false), hatemyself(BigSausage.hatemyself, BigSausage.hateMyselfList, false), hcw(BigSausage.hcw, BigSausage.hcwList, false), 
	korean(BigSausage.korean, BigSausage.koreanList, false), bursela(BigSausage.bursela, BigSausage.burselaList, false), burse(BigSausage.burse, BigSausage.burseList, false), 
	whiskey(BigSausage.whiskey, BigSausage.whiskeyList, false), egg(BigSausage.egg, BigSausage.eggList, false), choice(BigSausage.choice, BigSausage.choiceList, false), 
	grunch(BigSausage.grunch, BigSausage.grunchList, false), sainte(BigSausage.sainte, BigSausage.sainteList, false), tube(BigSausage.tube, BigSausage.tubeList, false),
	silence(BigSausage.silence, BigSausage.emptyList, true), never(BigSausage.never, BigSausage.emptyList, true), nightmare(BigSausage.nightmare, BigSausage.nightmareList, true),
	thomas(BigSausage.thomas, BigSausage.emptyList, true), boilegg(BigSausage.boilegg, BigSausage.boileggList, false), bush(BigSausage.bush, BigSausage.bushList, false), 
	datstick(BigSausage.datstick, BigSausage.datstickList, false), shorts(BigSausage.shorts, BigSausage.shortsList, false), seth(BigSausage.seth, BigSausage.sethList, false),
	chaos(BigSausage.chaos, BigSausage.chaosList, false), ipa(BigSausage.ipa, BigSausage.ipaList, false), fyl(BigSausage.fuck_you_luigi, BigSausage.fylList, false);

	private File file;
	private List<String> defaultTriggers;
	private boolean hidden;

	private EnumClips(File fileToPlay, List<String> defaultTriggers, boolean hidden) {
		this.file = fileToPlay;
		this.defaultTriggers = defaultTriggers;
		this.hidden = hidden;
	}
	
	public static EnumClips getFromString(String s) {
		for(EnumClips c : EnumClips.values()){
			if(c.toString().contentEquals(s)) return c;
		}
		return hcw;
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
	
	public static List<String> getNonSecretNames(){
		List<String> list = new ArrayList<String>();
		for(EnumClips clip : EnumClips.values()){
			if(!clip.getIsHidden()){
				list.add(clip.toString());
			}
		}
		return list;
	}

	@Override
	public boolean getIsHidden() {
		return this.hidden;
	}

}
