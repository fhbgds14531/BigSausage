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

	@SuppressWarnings("unused")
	private static Map<String, String> defaultSettings = new HashMap<String, String>();
	private static Map<String, State> guildSpecificStates;
	
	public IO(){
//		defaultSettings.put("", value)
	}

	static void loadSettingsForGuild(IGuild guild) {
		if (guildSpecificStates == null) guildSpecificStates = new HashMap<String, State>();
		File settingsDir = new File("settings/" + guild.getStringID());
		File f = new File("settings/" + guild.getStringID() + "/settings.bs");
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(f));
			State s = (State) in.readObject();
			in.close();
			guildSpecificStates.put(guild.getStringID(), s);
		} catch (FileNotFoundException e) {
			try {
				settingsDir.mkdirs();
				f.createNewFile();
				State s = State.Disabled;
				guildSpecificStates.put(guild.getStringID(), s);
				saveSettingsForGuild(guild);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
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
			State s = (State) guildSpecificStates.get(guild.getStringID());
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(f));
			out.writeObject(s);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void setMaxFilesPerMessage(IGuild guild, int i) throws Exception {
		File f = new File("settings/" + guild.getStringID() + "/maxClips.bs");
		if (f.exists()) f.delete();
		f.createNewFile();
		List<String> lines = new ArrayList<String>();
		lines.add(String.valueOf(i));
		Files.write(f.toPath(), lines, StandardOpenOption.WRITE);
		System.out.println("Changed max clips per message for \"" + guild.getName() + "\" to " + i);
	}

	public static int getMaxFilesPerMessage(IGuild guild) throws Exception {
		return Integer.valueOf(Files.readAllLines(new File("settings/" + guild.getStringID() + "/maxClips.bs").toPath()).get(0));
	}

	public static void setStateForGuild(IGuild guild, State state) {
		System.out.println(state);
		guildSpecificStates.put(guild.getStringID(), state);
		saveSettingsForGuild(guild);
	}

	public static State getStateForGuild(IGuild guild) {
		State s = guildSpecificStates.get(guild.getStringID());
		return s;
	}

	public static List<String> getTriggersForGuild(IGuild guild, ILinkable linkable) {
		if (linkable == null) return new ArrayList<String>();
		if (!linkable.getIsHidden() && linkable.getDefaultTriggers().size() > 0) {
			List<String> list = null;
			try {
				list = Files.readAllLines(new File("settings/" + guild.getStringID() + "/triggers/" + linkable.toString() + ".txt").toPath());
			} catch (Exception e) {
				e.printStackTrace();
			}
			return (list == null ? new ArrayList<String>() : list);
		} else {
			return new ArrayList<String>();
		}
	}

	public static void saveTriggersForGuild(IGuild guild, List<String> triggers, ILinkable linkable) {
		if (linkable == null) return;
		if (!linkable.getIsHidden() && linkable.getDefaultTriggers().size() > 0) {
			File triggerDir = new File("settings/" + guild.getStringID() + "/triggers/");
			if (!triggerDir.exists()) triggerDir.mkdirs();
			try {
				File f = new File("settings/" + guild.getStringID() + "/triggers/" + linkable.toString() + ".txt");
				if (f.exists()) f.delete();
				f.createNewFile();
				Files.write(f.toPath(), triggers, StandardOpenOption.WRITE);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void addTriggerWord(String word, ILinkable linkable, IGuild guild) {
		if (linkable == null) return;
		if (!linkable.getIsHidden() && linkable.getDefaultTriggers().size() > 0) {
			List<String> triggers = getTriggersForGuild(guild, linkable);
			triggers.add(word);
			saveTriggersForGuild(guild, triggers, linkable);
		}
	}

	public static void removeTriggerWord(String word, ILinkable linkable, IGuild guild) {
		if (linkable == null) return;
		if (!linkable.getIsHidden() && linkable.getDefaultTriggers().size() > 0) {
			List<String> triggers = getTriggersForGuild(guild, linkable);
			triggers.remove(word);
			saveTriggersForGuild(guild, triggers, linkable);
		}
	}
}
