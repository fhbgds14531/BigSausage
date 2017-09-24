package net.mizobo.bigsausage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.mizobo.bigsausage.BigSausage.State;
import sx.blah.discord.handle.obj.IGuild;

public class IO {

	private static Map<String, Map<String, String>> guildSpecificSettings;

	@SuppressWarnings("unchecked")
	static void loadSettingsForGuild(IGuild guild) {
		if(guildSpecificSettings == null) guildSpecificSettings = new HashMap<String, Map<String, String>>();
		File settingsDir = new File("settings/" + guild.getStringID());
		File f = new File("settings/" + guild.getStringID() + "/settings.bs");
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(f));
			Map<String, String> map = (Map<String, String>) in.readObject();
			in.close();
			guildSpecificSettings.put(guild.getStringID(), map);
		}catch(FileNotFoundException e){
			try {
				settingsDir.mkdirs();
				f.createNewFile();
				Map<String, String> map = new HashMap<String, String>();
				guildSpecificSettings.put(guild.getStringID(), map);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			setStateForGuild(guild, BigSausage.statePerGuild.get(guild.getStringID()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void saveSettingsForGuild(IGuild guild) {
		File f = new File("settings/" + guild.getStringID() + "/settings.bs");
		try {
			if (f.exists()) {
				f.delete();
			}
			f.createNewFile();
			Map<String, String> map = guildSpecificSettings.get(guild.getStringID());
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(f));
			out.writeObject(map);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void setStateForGuild(IGuild guild, State state) {
		guildSpecificSettings.get(guild.getStringID()).put("state", State.Enabled.toString());
		saveSettingsForGuild(guild);
	}

	public static List<String> getTriggersForGuild(IGuild guild, String forWhat) {
		List<String> list = null;
		try {
			list = Files.readAllLines(new File("settings/" + guild.getStringID() + "/triggers/" + forWhat + ".txt").toPath());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (list == null ? new ArrayList<String>() : list);
	}

	public static void saveTriggersForGuild(IGuild guild, List<String> triggers, String forWhat) {
		File triggerDir = new File("settings/" + guild.getStringID() + "/triggers/");
		if(!triggerDir.exists()) triggerDir.mkdirs();
		try {
			File f = new File("settings/" + guild.getStringID() + "/triggers/" + forWhat + ".txt");
			if (f.exists()) f.delete();
			f.createNewFile();
			Files.write(f.toPath(), triggers, StandardOpenOption.WRITE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void addTriggerWord(String word, String forWhat, IGuild guild){
		List<String> triggers = getTriggersForGuild(guild, forWhat);
		triggers.add(word);
		saveTriggersForGuild(guild, triggers, forWhat);
	}
	
	public static void removeTriggerWord(String word, String forWhat, IGuild guild){
		List<String> triggers = getTriggersForGuild(guild, forWhat);
		triggers.remove(word);
		saveTriggersForGuild(guild, triggers, forWhat);
	}
}
