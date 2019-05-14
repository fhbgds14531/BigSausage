package net.mizobogames.fhbgds.commands;

import java.util.List;

import net.mizobogames.fhbgds.Command;
import net.mizobogames.fhbgds.SettingsManager;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;

public class CommandStatus extends Command{

	public CommandStatus(String commandString, String helpString) {
		super(commandString, helpString);
	}

	@Override
	public void execute(IChannel channel, IUser commandAuthor, IGuild guild, List<String> command, IMessage message) {
		String audio = (boolean) SettingsManager.getSettingForGuild(guild, "audio-enabled") ? "Enabled" : "Disabled";
		String images = (boolean) SettingsManager.getSettingForGuild(guild, "images-enabled") ? "Enabled" : "Disabled";
		String tts = (boolean) SettingsManager.getSettingForGuild(guild, "tts-enabled") ? "Enabled" : "Disabled";
		String multi_link = (boolean) SettingsManager.getSettingForGuild(guild, "multi-link-enabled") ? "Enabled" : "Disabled";
		String max_clips = String.valueOf((long) SettingsManager.getSettingForGuild(guild, "max_clips_per_message"));
		channel.sendMessage("Here is the current status of BigSausage's services:\n```Audio Clip Playback: " + audio + "\nImage Linking:   \t" 
				+ images + "\nText To Speech:  \t" + tts + "\nMulti-Link:  \t\t" + multi_link + 
				"\nAudio Clips/Message: " + max_clips + "```");
	}
	

}
