package net.mizobogames.fhbgds.commands;

import java.util.List;

import net.mizobogames.fhbgds.Command;
import net.mizobogames.fhbgds.PermissionLevels;
import net.mizobogames.fhbgds.SettingsManager;
import net.mizobogames.fhbgds.Util;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;

public class CommandEnable extends Command {

	private boolean stateToSet;

	public CommandEnable(String commandString, String helpString, boolean stateToSet) {
		super(commandString, helpString);
		this.stateToSet = stateToSet;
	}

	@Override
	public void execute(IChannel channel, IUser commandAuthor, IGuild guild, List<String> command, IMessage message) {
		if (Util.hasPermission(PermissionLevels.PERMISSION_LEVEL_TRUSTED, guild, commandAuthor)) {
			String thingToChange = "";
			if (command.size() > 2) {
				thingToChange = command.get(2).toLowerCase();
			} else {
				thingToChange = "all";
			}
			switch (thingToChange) {
				case "images":
					SettingsManager.setSettingForGuild(guild, "images-enabled", stateToSet);
					channel.sendMessage("Images are now " + (stateToSet ? "enabled." : " disabled."));
					break;
				case "ian-mode":
					SettingsManager.setSettingForGuild(guild, "ian_mode", stateToSet);
					channel.sendMessage("Ian mode is now " + (stateToSet ? "enabled." : " disabled."));
					break;
				case "audio":
					SettingsManager.setSettingForGuild(guild, "audio-enabled", stateToSet);
					channel.sendMessage("Audio is now " + (stateToSet ? "enabled." : " disabled."));
					break;
				case "tts":
					SettingsManager.setSettingForGuild(guild, "tts-enabled", stateToSet);
					channel.sendMessage("Tts is now " + (stateToSet ? "enabled." : " disabled."));
					break;
				case "multi-link":
					SettingsManager.setSettingForGuild(guild, "multi-link-enabled", stateToSet);
					channel.sendMessage("Multiple of the same clip/image per message are now " + (stateToSet ? "enabled." : " disabled."));
					break;
				case "all":
					SettingsManager.setSettingForGuild(guild, "audio-enabled", stateToSet);
					SettingsManager.setSettingForGuild(guild, "images-enabled", stateToSet);
					SettingsManager.setSettingForGuild(guild, "tts-enabled", stateToSet);
					SettingsManager.setSettingForGuild(guild, "multi-link-enabled", stateToSet);
					channel.sendMessage("Everything is now " + (stateToSet ? "enabled." : " disabled."));
					break;
			}
		}
	}

}
