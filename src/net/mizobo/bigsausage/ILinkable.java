package net.mizobo.bigsausage;

import java.io.File;
import java.util.List;

public interface ILinkable {
	
	public File getFile();
	public boolean getIsHidden();
	public List<String> getDefaultTriggers();
	
}
