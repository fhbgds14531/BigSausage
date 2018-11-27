package net.mizobogames.fhbgds.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import net.mizobogames.fhbgds.BigSausage;
import net.mizobogames.fhbgds.Command;
import net.mizobogames.fhbgds.SettingsManager;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;

public class CommandTts extends Command {

	private List<String> ttsStrings;

	public CommandTts(String commandString, String helpString) {
		super(commandString, helpString);
		ttsStrings = new ArrayList<String>();
	}

	@Override
	public void execute(IChannel channel, IUser commandAuthor, IGuild guild, List<String> command, IMessage message) {
		if ((boolean) SettingsManager.getSettingForGuild(guild, "tts-enabled")) {
			SecureRandom rand = new SecureRandom();
			File ttsFile = new File("guilds/" + guild.getStringID() + "/tts.txt");
			if (ttsStrings.isEmpty()) {
				try {
					ttsStrings = Files.readAllLines(ttsFile.toPath());
				} catch (IOException e) {
					e.printStackTrace();
					ttsStrings = new ArrayList<String>();
				}
			}
			if (!ttsStrings.isEmpty()) {
				String send = ttsStrings.get(rand.nextInt(ttsStrings.size()));
				channel.sendMessage(send, true);
				ttsStrings.remove(send);
			} else {
				channel.sendMessage("There are currently no tts strings in the list, try adding some with `" + BigSausage.PREFIX + " add-tts <tts string>`");
			}
		} else {
			channel.sendMessage("Tts is disabled.");
		}
	}

}
