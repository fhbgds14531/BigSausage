package net.mizobo.bigsausage;

public class Util {

	public static ILinkable getLinkableFromString(String s){
		s = s.toLowerCase().trim();
		ILinkable link;
		link = EnumClips.getFromString(s);
		if(link == null){
			link = EnumImage.getFromString(s);
		}
		return link;
	}
	
}
