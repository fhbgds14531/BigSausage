package net.mizobogames.fhbgds.commands;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.List;

import net.mizobogames.fhbgds.BigSausage;
import net.mizobogames.fhbgds.Command;
import net.mizobogames.fhbgds.PermissionLevels;
import net.mizobogames.fhbgds.Util;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;

public class CommandAddTts extends Command {
	
	public CommandAddTts(String commandString, String helpString) {
		super(commandString, helpString);
	}

	@Override
	public void execute(IChannel channel, IUser commandAuthor, IGuild guild, List<String> command, IMessage message) {
		if(Util.hasPermission(PermissionLevels.PERMISSION_LEVEL_TRUSTED, guild, commandAuthor)){
			String string = "";
			for(String s : command){
				string += s + " ";
			}
			String commandString = BigSausage.PREFIX + " " + getCommandString();
			string = string.trim();
			string = string.replace(commandString, "").trim();
			if(string.isEmpty()){
				channel.sendMessage(getHelpString());
				return;
			}
			File ttsFile = new File("guilds/" + guild.getStringID() + "/tts.txt");
			List<String> ttses;
			try {
				ttses = Files.readAllLines(ttsFile.toPath());
				ttses.add(string);
				ttsFile.delete();
				ttsFile.createNewFile();
				Files.write(ttsFile.toPath(), ttses, StandardOpenOption.WRITE);
			}catch (Exception e){
				e.printStackTrace();
				channel.sendMessage("Encountered an error while trying to add that tts string, if this problem persists, use `!bs bugreport <description>` to report the issue.");
				return;
			}
			channel.sendMessage(string, true);
		}
	}

}
