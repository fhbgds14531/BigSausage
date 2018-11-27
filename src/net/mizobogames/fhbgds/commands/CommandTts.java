package net.mizobogames.fhbgds.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.mizobogames.fhbgds.BigSausage;
import net.mizobogames.fhbgds.Command;
import net.mizobogames.fhbgds.SettingsManager;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;

public class CommandTts extends Command {

	private static Map<IGuild, List<String>> ttsStrings;

	public CommandTts(String commandString, String helpString) {
		super(commandString, helpString);
	}

	public static void setupGuilds() {
		ttsStrings = new HashMap<IGuild, List<String>>();
		for (IGuild guild : BigSausage.client.getGuilds()) {
			ttsStrings.put(guild, new ArrayList<String>());
		}
	}

	@Override
	public void execute(IChannel channel, IUser commandAuthor, IGuild guild, List<String> command, IMessage message) {
		try {
			if ((boolean) SettingsManager.getSettingForGuild(guild, "tts-enabled")) {
				SecureRandom rand = new SecureRandom();
				File ttsFile = new File("guilds/" + guild.getStringID() + "/tts.txt");
				if (ttsStrings.get(guild).isEmpty()) {
					try {
						ttsStrings.put(guild, Files.readAllLines(ttsFile.toPath()));
					} catch (IOException e) {
						e.printStackTrace();
						ttsStrings.put(guild, new ArrayList<String>());
					}
				}
				if (!ttsStrings.get(guild).isEmpty()) {
					String send = ttsStrings.get(guild).get(rand.nextInt(ttsStrings.size()-1));
					channel.sendMessage(send, true);
					ttsStrings.get(guild).remove(send);
				} else {
					channel.sendMessage("There are currently no tts strings in the list, try adding some with `" + BigSausage.PREFIX + " add-tts <tts string>`");
				}
			} else {
				channel.sendMessage("Tts is disabled.");
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
			channel.sendMessage("Something went wrong, please use `bs bugreport` to inform my creator what you were doing when it happened.");
		}
	}

}
