package net.mizobogames.fhbgds.commands;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import net.mizobogames.fhbgds.BigSausage;
import net.mizobogames.fhbgds.Command;
import net.mizobogames.fhbgds.Util;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;

public class CommandListFiles extends Command {

	public CommandListFiles(String commandString, String helpString) {
		super(commandString, helpString);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void execute(IChannel channel, IUser commandAuthor, IGuild guild, List<String> command, IMessage message) {
		String output = "Error. If you're seeing this, please report the incident using `" + BigSausage.PREFIX + " bugreport <information about the issue>`";
		boolean doAudio = false;
		boolean doImages = false;
		if (command.size() < 3) {
			doAudio = true;
			doImages = true;
		} else {
			String type = command.get(2);
			if (type.toLowerCase().contentEquals("images")) doImages = true;
			if (type.toLowerCase().contentEquals("audio")) doAudio = true;
		}
		channel.setTypingStatus(true);
		File filesDir = new File("guilds/" + guild.getStringID() + "/files/");
		if (filesDir.exists()) {
			File indexDir = new File("guilds/" + guild.getStringID() + "/files/indices");
			File audioFileIndex = Util.getAudioIndexFile(guild);
			File imageFileIndex = Util.getImageIndexFile(guild);
			List<String> imageIndexStrings = new ArrayList<String>();
			List<String> audioIndexStrings = new ArrayList<String>();
			if (indexDir.exists()) {
				if (audioFileIndex.exists() && doAudio) {
					JSONObject audioIndex = Util.getJsonObjectFromFile(guild, audioFileIndex);
					JSONArray ja = (JSONArray) audioIndex.get("index");
					if (ja == null) ja = new JSONArray();
					ja.forEach(s -> audioIndexStrings.add(String.valueOf(s)));
				}
				if (imageFileIndex.exists() && doImages) {
					JSONObject imageIndex = Util.getJsonObjectFromFile(guild, imageFileIndex);
					JSONArray ja = (JSONArray) imageIndex.get("index");
					if (ja == null) ja = new JSONArray();
					ja.forEach(s -> imageIndexStrings.add(String.valueOf(s)));
				}
				String audioList = "Audio Clips:\n";
				if (!audioIndexStrings.isEmpty()) {
					audioList += "```" + Util.getCommaSeparatedFormattedList(audioIndexStrings) + "```";
				} else {
					audioList = "There are currently no audio clips.";
				}
				String imageList = "Images:\n";
				if (!imageIndexStrings.isEmpty()) {
					imageList += "```" + Util.getCommaSeparatedFormattedList(imageIndexStrings) + "```";
				} else {
					imageList = "There are currently no images.";
				}
				boolean lots = false;
				if (doImages && !doAudio) {
					String finalOut = "Here are all the image names for this server:\n" + imageList.replace("Images:\n", "");
					List<String> imageStrings = new ArrayList<String>();
					while (finalOut.length() > 2000) {
						imageStrings.add(finalOut.substring(0, finalOut.substring(0, 1994).lastIndexOf(" ")) + "```");
						finalOut = "```" + finalOut.substring(finalOut.substring(0, 2000).lastIndexOf(" "));
					}
					imageStrings.add(finalOut);
					int count = 0;
					for (String s : imageStrings) {
						channel.sendMessage(s);
						count++;
						try{
							if(count < 3){
								Thread.sleep(500L);
							}else{
								Thread.sleep(2750L);
								count = 0;
								lots = true;
							}
						}catch(Exception e){
							e.printStackTrace();
							channel.sendMessage("`Sorry, something went wrong.`");
							IGuild supportGuild = BigSausage.client.getGuildByID(382053109788049429L);
							IChannel reportChannel = supportGuild.getChannelByID(382053168042737674L);
							reportChannel.sendMessage("Something went wrong listing files in guild `" + guild.getLongID() + "` owned by <@" + guild.getOwnerLongID() + ">");
							reportChannel.sendMessage(Util.getStacktraceString(this.getClass().getName()) + ": thread sleeping");
						}
					}
					if(lots){
						channel.sendMessage("Wow! That's a lot of things!");
					}
				} else if (!doImages && doAudio) {
					String finalOut = "Here are all the audio clip names for this server:\n" + audioList.replace("Audio Clips:\n", "");
					List<String> audioStrings = new ArrayList<String>();
					while (finalOut.length() > 2000) {
						audioStrings.add(finalOut.substring(0, finalOut.substring(0, 1994).lastIndexOf(" ")) + "```");
						finalOut = finalOut.substring(finalOut.substring(0, 2000).lastIndexOf(" ")).replaceFirst("\n", "");
						if(finalOut.startsWith("   ")) finalOut = finalOut.substring(3);
						finalOut = "```" + finalOut;
					}
					audioStrings.add(finalOut);

					int count = 0;
					for (String s : audioStrings) {
						channel.sendMessage(s);
						count++;
						try{
							if(count < 3){
								Thread.sleep(500L);
							}else{
								Thread.sleep(2750L);
								count = 0;
								lots = true;
							}
						}catch(Exception e){
							e.printStackTrace();
							channel.sendMessage("`Sorry, something went wrong.`");
							IGuild supportGuild = BigSausage.client.getGuildByID(382053109788049429L);
							IChannel reportChannel = supportGuild.getChannelByID(382053168042737674L);
							reportChannel.sendMessage("Something went wrong listing files in guild `" + guild.getLongID() + "` owned by <@" + guild.getOwnerLongID() + ">");
							reportChannel.sendMessage(Util.getStacktraceString(this.getClass().getName()) + ": thread sleeping");
						}
					}
					if(lots){
						channel.sendMessage("Wow! That's a lot of things!");
					}
				} else if (doImages && doAudio) {
					String finalOut = "Here are all the image and audio clip names for this server:\n\n" + imageList + "\n" + audioList;
					List<String> allStrings = new ArrayList<String>();
					while (finalOut.length() > 2000) {
						allStrings.add(finalOut.substring(0, finalOut.substring(0, 1994).lastIndexOf(" ")) + "```");
						finalOut = "```" + finalOut.substring(finalOut.substring(0, 1994).lastIndexOf(" ")).replaceFirst("\n", "").replaceFirst(" ", "");
					}
					allStrings.add(finalOut);
					
					int count = 0;
					for (String s : allStrings) {
						channel.sendMessage(s);
						count++;
						try{
							if(count < 3){
								Thread.sleep(500L);
							}else{
								Thread.sleep(2500L);
								count = 0;
								lots = true;
							}
						}catch(Exception e){
							e.printStackTrace();
							channel.sendMessage("`Sorry, something went wrong.`");
							IGuild supportGuild = BigSausage.client.getGuildByID(382053109788049429L);
							IChannel reportChannel = supportGuild.getChannelByID(382053168042737674L);
							reportChannel.sendMessage("Something went wrong listing files in guild `" + guild.getLongID() + "` owned by <@" + guild.getOwnerLongID() + ">");
							reportChannel.sendMessage(Util.getStacktraceString(this.getClass().getName()) + ": thread sleeping");
						}
					}
					if(lots){
						channel.sendMessage("Wow! That's a lot of things!");
					}
				}
				if (output.contains("no audio") && output.contains("no images")) {
					channel.sendMessage("There are currently no files. Try adding some using `" + BigSausage.PREFIX + " upload`");
				}
			}
		} else {
			channel.sendMessage("The files directory is empty.");
		}
		channel.setTypingStatus(false);
	}

}
