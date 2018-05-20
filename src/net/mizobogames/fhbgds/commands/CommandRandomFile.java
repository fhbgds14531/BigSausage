package net.mizobogames.fhbgds.commands;

import java.io.File;
import java.io.FileNotFoundException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import net.mizobogames.fhbgds.BigSausage;
import net.mizobogames.fhbgds.Command;
import net.mizobogames.fhbgds.SettingsManager;
import net.mizobogames.fhbgds.Util;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.IVoiceChannel;

public class CommandRandomFile extends Command {

	boolean image = false;

	public CommandRandomFile(String commandString, String helpString) {
		super(commandString, helpString);
		if (commandString.contentEquals("image")) image = true;
	}

	@Override
	public void execute(IChannel channel, IUser commandAuthor, IGuild guild, List<String> command, IMessage message) {
		SecureRandom rand = new SecureRandom();
		File indexDir = new File("guilds/" + guild.getStringID() + "/files/indices");
		if (indexDir.exists()) {
			if (!image) {
				if (!(boolean) SettingsManager.getSettingForGuild(guild, "audio-enabled")) {
					channel.sendMessage("Audio clips are currently disabled.");
					return;
				}
				String arg3 = command.get(2);
				if(arg3 == null || arg3.isEmpty()){
					arg3 = "1";
				}
				int numToLink = Integer.valueOf(arg3);
				long maxLinkable = (long) SettingsManager.getSettingForGuild(guild, "max_clips_per_message");
				if (numToLink > maxLinkable){
					numToLink = Integer.valueOf(String.valueOf(maxLinkable));
					channel.sendMessage("The maximum allowed number of audio clips to be queued at once is " + maxLinkable + ", so I'll queue that many instead.");
				}
				File audioFileIndex = Util.getAudioIndexFile(guild);
				if (audioFileIndex.exists() && ((boolean) SettingsManager.getSettingForGuild(guild, "audio-enabled"))) {
					JSONObject audioIndex = Util.getJsonObjectFromFile(guild, audioFileIndex);
					JSONArray ja = (JSONArray) audioIndex.get("index");
					if (ja == null) ja = new JSONArray();
					List<String> indexStrings = new ArrayList<String>();
					ja.forEach(s -> indexStrings.add(String.valueOf(s)));
					if(indexStrings.size() == 0){
						channel.sendMessage("Couldn't find any files to play... Try adding some!");
						return;
					}
					for(int i = 0; i < numToLink; i++){
						String clipName = indexStrings.get(rand.nextInt(indexStrings.size()));
						for (IVoiceChannel vChannel : guild.getVoiceChannels()) {
							if (vChannel.getConnectedUsers().contains(commandAuthor)) {
								String filename = (String) audioIndex.get(clipName + "_name");
								File file = new File("guilds/" + guild.getStringID() + "/files/" + filename);
								BigSausage.queueFile(file, guild, vChannel, commandAuthor, false);
							}
						}
					}
				}
			} else {
				if (!(boolean) SettingsManager.getSettingForGuild(guild, "images-enabled")) {
					channel.sendMessage("Images are currently disabled.");
					return;
				}
				File imageFileIndex = new File("guilds/" + guild.getStringID() + "/files/indices/imageIndex.json");
				if (imageFileIndex.exists()) {
					JSONObject imageIndex = Util.getJsonObjectFromFile(guild, imageFileIndex);
					JSONArray ja = (JSONArray) imageIndex.get("index");
					if (ja == null) ja = new JSONArray();
					List<String> indexStrings = new ArrayList<String>();
					ja.forEach(s -> indexStrings.add(String.valueOf(s)));
					if(indexStrings.size() == 0){
						channel.sendMessage("Couldn't find any files to upload... Try adding some!");
						return;
					}
					String imageName = indexStrings.get(rand.nextInt(indexStrings.size()));
					String filename = (String) imageIndex.get(imageName + "_name");
					File file = new File("guilds/" + guild.getStringID() + "/files/" + filename);
					System.out.println("Sending file \"" + filename + "\" to guild \"" + guild.getName() + "\"...");
					try {
						channel.setTypingStatus(true);
						channel.sendFile(file);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
						channel.sendMessage("Error uploading image `" + imageName + "`.");
					}
					channel.setTypingStatus(false);
				}
			}
		}
	}

}
