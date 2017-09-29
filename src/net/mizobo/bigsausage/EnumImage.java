package net.mizobo.bigsausage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public enum EnumImage {
	succ(BigSausage.corndog, BigSausage.succList),
	horse(BigSausage.horse, BigSausage.horseList),
	lego(BigSausage.lego, BigSausage.legoList);
	
	File fileToLink;
	List<String> defaultTriggers;
	
	public File getFile(){
		return this.fileToLink;
	}
	
	public static EnumImage getFromString(String s) {
		switch (s) {
			case "succ":
				return succ;
			case "horse":
				return horse;
			default:
				return succ;
		}
	}
	
	private EnumImage(File file, List<String> defaultTriggers){
		this.fileToLink = file;
		this.defaultTriggers = defaultTriggers;
	}
	
	public List<String> getDefaultTriggers(){
		List<String> list = new ArrayList<String>();
		list.addAll(defaultTriggers);
		return list;
	}
	
	public static String getCommaSeparatedList() {
		String out = "";
		for (EnumImage image : EnumImage.values()) {
			out += image.toString() + ", ";
		}
		return out.substring(0, out.lastIndexOf(", "));
	}
}
