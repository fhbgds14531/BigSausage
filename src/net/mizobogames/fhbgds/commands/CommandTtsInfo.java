package net.mizobogames.fhbgds.commands;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.mizobogames.fhbgds.Command;
import net.mizobogames.fhbgds.Util;
import net.mizobogames.fhbgds.Util.RunningAverage;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;

public class CommandTtsInfo extends Command {

	public CommandTtsInfo(String commandString, String helpString) {
		super(commandString, helpString);
	}

	@Override
	public void execute(IChannel channel, IUser commandAuthor, IGuild guild, List<String> command, IMessage message) {
		final File ttsFile = new File("guilds/" + guild.getStringID() + "/tts.txt");
		List<String> tts;
		try {
			tts = Files.readAllLines(ttsFile.toPath());
		} catch (IOException e) {
			e.printStackTrace();
			tts = new ArrayList<String>();
		}
		int numberOfLines = tts.size();
		int wordCount = 0; 
		int avgWordsPerEntry;
		String fileSize = Util.humanReadableByteCount(ttsFile.length(), false);
		
		RunningAverage avg = new RunningAverage(tts.size());
		for(String s: tts){
			List<String> sWords = Arrays.asList(s.split(" "));
			avg.add(new BigDecimal(sWords.size()));
			wordCount += sWords.size();
		}
		avgWordsPerEntry = avg.getAverage().intValue();
		channel.sendMessage(String.format("File size: %s, Number of Entries: %s, Average Words per Entry: %s, Total Word Count: %s", fileSize, numberOfLines, avgWordsPerEntry, wordCount));
	}

}
