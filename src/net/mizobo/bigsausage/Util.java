package net.mizobo.bigsausage;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

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
	
	public static String humanReadableByteCount(long bytes, boolean si) {
	    int unit = si ? 1000 : 1024;
	    if (bytes < unit) return bytes + " B";
	    int exp = (int) (Math.log(bytes) / Math.log(unit));
	    String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp-1) + (si ? "" : "i");
	    return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
	}
	
	public static class RunningAverage {

	    private final Queue<BigDecimal> queue = new ArrayDeque<BigDecimal>();
	    private final int maxSize;
	    private BigDecimal sum = BigDecimal.ZERO;

	    public RunningAverage(int maxSize) {
	        this.maxSize = maxSize;
	    }

	    public void add(BigDecimal num) {
	        sum = sum.add(num);
	        queue.add(num);
	        if (queue.size() > maxSize) {
	            sum = sum.subtract(queue.remove());
	        }
	    }

	    public BigDecimal getAverage() {
	        if (queue.isEmpty()) return BigDecimal.ZERO;
	        BigDecimal divisor = BigDecimal.valueOf(queue.size());
	        return sum.divide(divisor, 2, RoundingMode.HALF_UP);
	    }
	}
	
	public static String getCommaSeparatedString(List list) {
		String out = "";
		for (Object o : list) {
			out += String.valueOf(o) + ", ";
		}
		return out.substring(0, out.lastIndexOf(", "));
	}
}
