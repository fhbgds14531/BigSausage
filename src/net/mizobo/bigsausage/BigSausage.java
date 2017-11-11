package net.mizobo.bigsausage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import net.mizobo.bigsausage.Util.RunningAverage;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.impl.events.guild.GuildCreateEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.handle.obj.IVoiceChannel;
import sx.blah.discord.handle.obj.Permissions;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;
import sx.blah.discord.util.audio.AudioPlayer;
import sx.blah.discord.util.audio.AudioPlayer.Track;
import sx.blah.discord.util.audio.events.TrackFinishEvent;
import sx.blah.discord.util.audio.events.TrackStartEvent;

public class BigSausage {
	private static final String VERSION = "0.1.9";
	private static final String CHANGELOG = "Added value.";

	private static String TOKEN;
	private static final String PREFIX = "!bs";
	private static IDiscordClient client;
	static Map<String, List<String>> trustedUsersPerGuild = new HashMap<String, List<String>>();
	static final File sausage = new File("files/Thomas_Sausage.wav");
	static final File ugly = new File("files/Ugly.wav");
	static final File fire = new File("files/Fire.wav");
	static final File enemy = new File("files/enemy_spotted.wav");
	static final File linked = new File("files/linked.wav");
	static final File miceway = new File("files/miceway.wav");
	static final File sceptre = new File("files/sceptre.wav");
	static final File hatemyself = new File("files/hatemyself.wav");
	static final File hcw = new File("files/hcw.wav");
	static final File korean = new File("files/korean.wav");
	static final File bursela = new File("files/bursela.wav");
	static final File burse = new File("files/burse.wav");
	static final File choice = new File("files/choice.wav");
	static final File grunch = new File("files/grunch.wav");
	static final File sainte = new File("files/sainte.wav");
	static final File egg = new File("files/egg.wav");
	static final File thomas = new File("files/thomas.wav");
	static final File whiskey = new File("files/whiskey.wav");
	static final File corndog = new File("files/corndog.png");
	static final File lego = new File("files/lego.png");
	static final File horse = new File("files/horse.jpg");
	static final File grout = new File("files/grout.jpg");
	static final File seeded = new File("files/seeded.jpg");
	static final File science = new File("files/science.png");
	static final File never = new File("files/never.wav");
	static final File silence = new File("files/silence.wav");
	static final File nightmare = new File("files/nightmare.wav");
	static final File tube = new File("files/tube.wav");
	static final File boilegg = new File("files/boilegg.wav");
	static final File bush = new File("files/bush.wav");
	static final File datstick = new File("files/datstick.wav");
	static final File shorts = new File("files/shorts.wav");
	static final File seth = new File("files/suckmycaw.wav");
	static final File chaos = new File("files/chaos.wav");
	static final File value = new File("files/2pt4Mil.png");
	static final File ipa = new File("files/ipa.wav");
	static final File fuck_you_luigi = new File("files/fuck_you_luigi.wav");
	private static final String myUserID = "198575970624471040";
	static final List<String> sausageList = Arrays.asList(new String[] { "sausage", "thomas", "daddy" });
	static final List<String> uglyList = Arrays.asList(new String[] { "ugly", "motherfucker", "jame" });
	static final List<String> enemyList = Arrays.asList(new String[] { "enemy", "spotted", "pubg" });
	static final List<String> fireList = Arrays.asList(new String[] { "hole", "fire", "grenade", "bomb", "molotov" });
	static final List<String> linkedList = Arrays.asList(new String[] { "link", "daughter" });
	static final List<String> hateMyselfList = Arrays.asList(new String[] { "hate", "myself" });
	static final List<String> miceWayList = Arrays.asList(new String[] { "blind" });
	static final List<String> sceptreList = Arrays.asList(new String[] { "sceptre", "boost" });
	static final List<String> hcwList = Arrays.asList(new String[] { "hcw", "buns" });
	static final List<String> koreanList = Arrays.asList(new String[] { "policewoman", "korea" });
	static final List<String> burselaList = Arrays.asList(new String[] { "ursula" });
	static final List<String> burseList = Arrays.asList(new String[] { "burse", "limit" });
	static final List<String> choiceList = Arrays.asList(new String[] { "choice", "claus" });
	static final List<String> grunchList = Arrays.asList(new String[] { "grinch", "grunch" });
	static final List<String> sainteList = Arrays.asList(new String[] { "saint" });
	static final List<String> succList = Arrays.asList(new String[] { "succ" });
	static final List<String> horseList = Arrays.asList(new String[] { "horse" });
	static final List<String> groutList = Arrays.asList(new String[] { "grout", "groot", "vin" });
	static final List<String> legoList = Arrays.asList(new String[] { "lego" });
	static final List<String> seededList = Arrays.asList(new String[] { "seeded" });
	static final List<String> scienceList = Arrays.asList(new String[] { "science" });
	static final List<String> nightmareList = Arrays.asList(new String[] { "nightmare" });
	static final List<String> tubeList = Arrays.asList(new String[] { "tube" });
	static final List<String> boileggList = Arrays.asList(new String[] { "boil" });
	static final List<String> bushList = Arrays.asList(new String[] { "bush" });
	static final List<String> datstickList = Arrays.asList(new String[] { "stick", "dat" });
	static final List<String> shortsList = Arrays.asList(new String[] { "shorts", "justice" });
	static final List<String> sethList = Arrays.asList(new String[] { "seth" });
	static final List<String> chaosList = Arrays.asList(new String[] { "chaos", "damn" });
	static final List<String> fylList = Arrays.asList(new String[] { "luigi", "neat" });
	static final List<String> ipaList = Arrays.asList(new String[] { "christian", "chin", "ipa" });
	static final List<String> valueList = Arrays.asList(new String[] { "value", "million" });
	static final List<String> emptyList = Arrays.asList(new String[] {});
	static final List<String> whiskeyList = Arrays
			.asList(new String[] { "beer", "wine", "whiskey", "rum", "vodka", "gin", "scotch", "bourbon", "moonshine", "everclear", "tequila", "brandy" });
	static final List<String> eggList = Arrays.asList(new String[] { "audio-easter-egg" });

	private static final File serverSettings = new File("settings");
	static String lastTts = "";

	List<Fucker> fucked = new ArrayList<Fucker>();

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws DiscordException, RateLimitException, FileNotFoundException, IOException, ClassNotFoundException {
		for (EnumClips clip : EnumClips.values()) {
			if (!clip.getFile().exists()) {
				String filename = clip.getFile().getName().replace("files/", "");
				System.out.println("File \"" + filename + "\" not detected... Attempting to download it.");
				URL url = new URL("https://github.com/fhbgds14531/BigSausage/blob/master/files/" + filename + "?raw=true");
				download(url, clip.getFile());
				System.out.println("Success!");
			}
		}
		for (EnumImage image : EnumImage.values()) {
			if (!image.getFile().exists()) {
				String filename = image.getFile().getName().replace("files/", "");
				System.out.println("File \"" + filename + "\" not detected... Attempting to download it.");
				URL url = new URL("https://github.com/fhbgds14531/BigSausage/blob/master/files/" + filename + "?raw=true");
				download(url, image.getFile());
				System.out.println("Success!");
			}
		}
		TOKEN = Files.readAllLines(new File("BigSausage.token").toPath()).get(0);
		if (!serverSettings.exists()) serverSettings.mkdirs();
		Map<String, List<String>> tempMap1 = new HashMap<String, List<String>>();
		File trustedUsersFile = new File("trusted.s");
		if (trustedUsersFile.exists()) {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(trustedUsersFile));
			tempMap1 = (Map<String, List<String>>) in.readObject();
			in.close();
		}
		trustedUsersPerGuild = tempMap1;
		System.out.println("Logging bot in...");
		client = new ClientBuilder().withToken(TOKEN).withRecommendedShardCount().build();
		client.getDispatcher().registerListener(new BigSausage());
		client.login();
	}

	@EventSubscriber
	public void onReady(ReadyEvent event) {
		if(new File("DEBUG.token").exists()){
			client.changeUsername("BigSausage - Beta");
			client.changePlayingText("under maintinence");
		}else{
			client.changeUsername("BigSausage");
		}
		System.out.println("BigSausage ready for mouths.");
	}

	@EventSubscriber
	public void onJoin(GuildCreateEvent event) throws IOException {
		IGuild guild = event.getGuild();
		IO.loadSettingsForGuild(guild);
		File maxClips = new File("settings/" + guild.getStringID() + "/maxClips.bs");
		if (!maxClips.exists()) {
			maxClips.createNewFile();
			Files.write(maxClips.toPath(), Arrays.asList(new String[] { "4" }), StandardOpenOption.WRITE);
			System.out.println("Created missing file \"maxClips.bs\" for guild \"" + guild.getName() + "\"");
		}
		if (!trustedUsersPerGuild.containsKey(guild.getStringID())) {
			trustedUsersPerGuild.put(guild.getStringID(), new ArrayList<String>());
			List<String> trusted = trustedUsersPerGuild.get(guild.getStringID());
			trusted.add(myUserID);
			System.out.println("Added guild \"" + guild.getName() + "\" to the trusted users registry.");
		}
		File triggersDir = new File("settings/" + guild.getStringID() + "/triggers");
		if (!triggersDir.exists()) {
			this.setupDefaults(guild);
			System.out.println("Setup default triggers for \"" + guild.getName() + "\"");
		}
		for (EnumClips clip : EnumClips.values()) {
			File triggersFile = new File("settings/" + guild.getStringID() + "/triggers/" + clip.toString() + ".txt");
			if (!triggersFile.exists() && clip.getDefaultTriggers().size() != 0) {
				triggersFile.createNewFile();
				Files.write(triggersFile.toPath(), clip.getDefaultTriggers(), StandardOpenOption.WRITE);
				System.out.println("Recreated missing triggers file for \"" + clip.toString() + "\" in guild \"" + guild.getName() + "\"");
			}
		}
		for (EnumImage image : EnumImage.values()) {
			File triggersFile = new File("settings/" + guild.getStringID() + "/triggers/" + image.toString() + ".txt");
			if (!triggersFile.exists()) {
				triggersFile.createNewFile();
				Files.write(triggersFile.toPath(), image.getDefaultTriggers(), StandardOpenOption.WRITE);
				System.out.println("Recreated missing triggers file for \"" + image.toString() + "\" in guild \"" + guild.getName() + "\"");
			}
		}
		File ttsFile = new File("settings/" + guild.getStringID() + "/tts.txt");
		if (!ttsFile.exists()) {
			ttsFile.createNewFile();
			System.out.println("Created tts file for guild \"" + guild.getName() + "\"");
		}
		File serverInfo = new File("settings/" + guild.getStringID() + "/Server_Info.txt");
		if (serverInfo.exists()) serverInfo.delete();
		serverInfo.createNewFile();
		List<String> info = new ArrayList<String>();
		info.add("Server ID: " + guild.getStringID());
		info.add("Server Name: \"" + guild.getName() + "\"");
		info.add("Server Owner: " + guild.getOwner().getName() + " (" + guild.getOwnerLongID() + ")");
		info.add("Number of Users: " + guild.getTotalMemberCount());
		String userList = "";
		for (IUser user : guild.getUsers()) {
			userList += user.getStringID() + "[" + user.getName() + ", " + user.getNicknameForGuild(guild) + "], ";
		}
		info.add("Users: " + userList.substring(0, userList.lastIndexOf(", ")));
		Files.write(serverInfo.toPath(), info, StandardOpenOption.WRITE);
		// guild.getDefaultChannel().sendMessage("BigSausage is online! Current Version: " + VERSION + ", Use \"!bs help\" for a list of commands!");
	}

	@EventSubscriber
	public void onMessage(MessageReceivedEvent event) throws Exception {
		SecureRandom rand = new SecureRandom();
		rand.setSeed(System.nanoTime());
		IMessage message = event.getMessage();
		IUser user = message.getAuthor();
		String[] words = message.getContent().split(" ");

		IChannel channel = message.getChannel();
		IGuild guild = message.getGuild();
		List<String> wordList = Arrays.asList(words);

		if(message.getContent().contentEquals("<@361063755045404673>")){
			channel.sendMessage(EnumCommand.help.getHelpText());
			return;
		}
		if (user.getName().contentEquals("BigSausage")) return;
		if (words[0].contentEquals(PREFIX) && words.length > 1) {
			EnumCommand c = EnumCommand.getFromString(words[1]);
			final File ttsFile = new File("settings/" + guild.getStringID() + "/tts.txt");
			List<String> tts = Files.readAllLines(ttsFile.toPath());
			boolean setToStatus = false;
			if(IO.getStateForGuild(guild) != State.Enabled){
				switch(c){
					case add_trigger:
					case add_tts:
					case changelog:
					case clips:
					case commands:
					case disable:
					case enable:
					case force_update:
					case help:
					case images:
					case list_triggers:
					case max_clips:
					case remove_all_triggers:
					case remove_trigger:
					case remove_trust:
					case remove_tts:
					case reset_triggers:
					case save_all:
					case setup_defaults:
					case shutdown:
					case status:
					case trust:
					case tts_info:
					case update:
					case update_to:
					case version:
					break;
					default:
						setToStatus = true;
						return;
				}
			}
			if(setToStatus) c = EnumCommand.status;
			switch (c) {
				case commands:
					String commands = EnumCommand.getCommaSeparatedFormattedList();
					
					commands = commands.replace(", ", ",\t");
					channel.sendMessage("```" + commands + "```");
					break;
				case roll:
					if(wordList.size() < 3){
						channel.sendMessage("Incorrect number of arguments. Use \"!bs help roll\" for usage");
						break;
					}
					Dice d = Dice.getDice(wordList.get(2));
					List<Integer> roll = d.rollAndReturnListOfResults();
					int result = 0;
					for(int i : roll){
						result += i;
					}
					String rolls = Util.getCommaSeparatedString(roll);
					if(rolls.length() > 30){
						rolls = rolls.substring(0, 30);
						rolls = rolls.substring(0, rolls.lastIndexOf(", "));
						rolls += "...";
					}
					channel.sendMessage(wordList.get(2) + "=" + result + " (" + rolls + ")");
					break;
				case tts_info:
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
					break;
				case thomas:
					if (getHasPermission(user, guild, TrustLevel.Trusted) || user.getStringID().contentEquals("158744331178475520")) {
						if (user.getVoiceStateForGuild(guild) != null) {
							if (user.getVoiceStateForGuild(guild).getChannel() != null) {
								this.queueFile(thomas, guild, user.getVoiceStateForGuild(guild).getChannel(), user, true);
							}
						}
					} else {
						if (rand.nextFloat() < 0.02F) {
							channel.sendMessage("Hey " + guild.getOwner().mention() + ", " + user.mention() + " is trying to use a restricted command!");
						} else {
							channel.sendMessage("You don't have permission to use this command!");
						}
					}
					break;
				case get_fucked:
					if (getHasPermission(user, guild, TrustLevel.Admin_Only)) {
						if (wordList.size() < 5) return;
						List<String> remainder = new ArrayList<String>();
						for (String s : wordList) {
							if (wordList.indexOf(s) > 3) {
								remainder.add(s);
							}
						}
						if (guild.getUserByID(361063755045404673L).getPermissionsForGuild(guild).contains(Permissions.VOICE_MOVE_MEMBERS)) {
							IUser target = guild.getUserByID(Long.valueOf(wordList.get(2).replace("<@", "").replace(">", "").replace("!", "")));
							List<IVoiceChannel> vch = guild.getVoiceChannelsByName(wordList.get(3).replace("-", " ").trim());
							if (vch.size() < 1) {
								channel.sendMessage("If that channel actually exists, I can't access it.");
								return;
							}
							IVoiceChannel chan = vch.get(0);
							if (target.getVoiceStateForGuild(guild) != null) {
								if (target.getVoiceStateForGuild(guild).getChannel() != null) {
									if (chan.getModifiedPermissions(target).contains(Permissions.VOICE_CONNECT)) {
										fucked.add(new Fucker(target, target.getVoiceStateForGuild(guild).getChannel(), chan));
										target.moveToVoiceChannel(chan);
										Thread.sleep(200L);
										this.checkListAndQueueFile(message.getContent(), remainder, guild, target, channel, true);
									} else {
										channel.sendMessage("I can't move them there.");
									}
								}
							}
						} else {
							channel.sendMessage("I don't have permission to move members. That command is useless right now");
							return;
						}
					}
					break;
				case play_clip:
					if (wordList.size() < 4) return;
					if (getHasPermission(user, guild, TrustLevel.Admin_Only)) {
						EnumClips clip = EnumClips.getFromString(wordList.get(2));
						String channelName = "";
						for (String word : wordList) {
							if (wordList.indexOf(word) > 2) {
								channelName += word + " ";
							}
						}
						List<IVoiceChannel> vch = guild.getVoiceChannelsByName(channelName.trim());
						if (vch.size() < 1) {
							channel.sendMessage("If that channel actually exists, I can't access it.");
							return;
						}
						IVoiceChannel chan = vch.get(0);
						this.queueFile(clip.getFile(), guild, (IVoiceChannel) chan, user, true);
					} else {
						if (rand.nextFloat() < 0.02F) {
							channel.sendMessage("Hey " + guild.getOwner().mention() + ", " + user.mention() + " is trying to use a restricted command!");
						} else {
							channel.sendMessage("You don't have permission to use this command!");
						}
					}
					break;
				case max_clips:
					if (wordList.size() == 2) {
						channel.sendMessage("Max audio files per message: " + IO.getMaxFilesPerMessage(guild));
					} else {
						if (getHasPermission(user, guild, TrustLevel.Admin_Only)) {
							int i = Integer.valueOf(wordList.get(2));
							if (i < 1) i = 1;
							IO.setMaxFilesPerMessage(guild, i);
							channel.sendMessage("Changed max clips per message to " + i);
						} else {
							if (rand.nextFloat() < 0.02F) {
								channel.sendMessage("Hey " + guild.getOwner().mention() + ", " + user.mention() + " is trying to use a restricted command!");
							} else {
								channel.sendMessage("You don't have permission to use this command!");
							}
						}
					}
					break;
				case changelog:
					channel.sendMessage("Version: " + VERSION + "\n Notable changes: " + CHANGELOG);
					break;
				case update_to:
					if (user.getStringID().equals(myUserID)) {
						if (wordList.size() < 3) {
							channel.sendMessage("Invalid number of arguments.");
							return;
						} else {
							String newVersionString = wordList.get(2);
							List<String> content = new ArrayList<String>();
							URL update = new URL("https://github.com/fhbgds14531/BigSausage/blob/master/jar/" + newVersionString + "/BigSausage.jar");
							InputStream in = update.openStream(); // throws an IOException
							BufferedReader reader = new BufferedReader(new InputStreamReader(in));
							String line;
							while ((line = reader.readLine()) != null) {
								content.add(line);
							}
							if (!content.contains("    <title>Page not found &middot; GitHub</title>")) {
								System.out.println("Successfull update command given. Updating from " + VERSION + " to " + newVersionString);
								System.out.println("Downloading jar");
								update = new URL("https://github.com/fhbgds14531/BigSausage/blob/master/jar/" + newVersionString + "/BigSausage.jar?raw=true");
								download(update, new File("BigSausage_1.jar"));
								System.out.println("Done! Attempting to restart...");
								channel.sendMessage("Restarting...");
								Runtime.getRuntime().exec("cmd /c start \"\" rename.bat");
								client.logout();
								System.exit(0);
							} else {
								channel.sendMessage("Invalid target version.");
							}
						}
						break;
					}
				case force_update:
				case update:
					if (user.getStringID().equals(myUserID)) {
						URL url = new URL("https://github.com/fhbgds14531/BigSausage/blob/master/newVersion.txt?raw=true");
						File version = new File("newVersion.txt");
						download(url, version);
						String newVersionString = Files.readAllLines(version.toPath()).get(0);
						if (!newVersionString.contentEquals(VERSION) || c == EnumCommand.force_update) {
							System.out.println("Successfull update command given. Updating from " + VERSION + " to " + newVersionString);
							System.out.println("Downloading jar");
							URL update = new URL("https://github.com/fhbgds14531/BigSausage/blob/master/jar/" + newVersionString + "/BigSausage.jar?raw=true");
							download(update, new File("BigSausage_1.jar"));
							System.out.println("Done! Attempting to restart...");
							channel.sendMessage("Restarting...");
							Runtime.getRuntime().exec("cmd /c start \"\" rename.bat");
							client.logout();
							version.deleteOnExit();
							System.exit(0);
						} else {
							channel.sendMessage("I am already the latest version, silly! (" + VERSION + ", " + newVersionString + ")");
						}
						version.delete();
					}
					break;
				case images:
					String[] imageNames = new String[EnumImage.values().length];
					for(int i = 0; i < imageNames.length; i++){
						imageNames[i] = EnumImage.values()[i].toString();
					}
					channel.sendMessage("Available images are:\n```" + Util.getCommaSeparatedFormattedList((String[]) imageNames) + "```");
					break;
				case tts:
					if (tts.size() > 0) {
						String ttsString = "";
						int count = 4;
						ttsString = tts.get(rand.nextInt(tts.size()));
						while (count > 0 && ttsString.contentEquals(lastTts)) {
							ttsString = tts.get(rand.nextInt(tts.size()));
							count--;
						}
						if (count == 0) {
							System.out.println("Gave up trying to find a unique tts in guild " + guild.getName());
						}
						if (ttsString.isEmpty()) {
							System.err.println("Somehow failed to get a tts string from a list with content in guild \"" + guild.getName() + "\"");
							channel.sendMessage("Error: could not find a tts string. This shouldn't happen.");
							return;
						}
						channel.sendMessage(ttsString, true);
						lastTts = ttsString;
					} else {
						channel.sendMessage("There are currently no tts strings in the registry for this server. try adding some!");
					}
					break;
				case add_tts:
					if (getHasPermission(user, guild, TrustLevel.Trusted)) {
						String ttsAddString = "";
						for (String s : wordList) {
							if (wordList.indexOf(s) > 1) {
								ttsAddString += s + " ";
							}
						}
						if (!ttsAddString.isEmpty()) {
							tts.add(ttsAddString.trim());
							ttsFile.delete();
							ttsFile.createNewFile();
							Files.write(ttsFile.toPath(), tts, StandardOpenOption.WRITE);
							channel.sendMessage(ttsAddString.trim(), true);
						}
					} else {
						if (rand.nextFloat() < 0.02F) {
							channel.sendMessage("Hey " + guild.getOwner().mention() + ", " + user.mention() + " is trying to use a restricted command!");
						} else {
							channel.sendMessage("You don't have permission to use this command!");
						}
					}
					break;
				case remove_tts:
					if (getHasPermission(user, guild, TrustLevel.Trusted)) {
						String ttsRemoveString = "";
						for (String s : wordList) {
							if (wordList.indexOf(s) > 1) {
								ttsRemoveString += s + " ";
							}
						}
						boolean removed = tts.remove(ttsRemoveString.trim());
						String reply = "";
						if (removed) {
							reply = "Successfully removed string \"" + ttsRemoveString + "\" from the tts list.";
						} else {
							reply = "Failed to remove string \"" + ttsRemoveString + "\" from the tts list. Is it exactly the same as an existing string? If not, this command won't work.";
						}
						ttsFile.delete();
						ttsFile.createNewFile();
						Files.write(ttsFile.toPath(), tts, StandardOpenOption.WRITE);
						channel.sendMessage(reply);
					} else {
						if (rand.nextFloat() < 0.02F) {
							channel.sendMessage("Hey " + guild.getOwner().mention() + ", " + user.mention() + " is trying to use a restricted command!");
						} else {
							channel.sendMessage("You don't have permission to use this command!");
						}
					}
					break;
				case add_trigger:
				case list_triggers:
				case remove_trigger:
				case remove_all_triggers:
				case reset_triggers:
					Commands.editTriggers(c, wordList, guild, user, channel);
					break;
				case clips:
					List<String> nameList = EnumClips.getNonSecretNames();
					String[] clipNames = new String[nameList.size()];
					for(int i = 0; i < clipNames.length; i++){
						clipNames[i] = nameList.get(i);
					}
					channel.sendMessage("Available clips are:\n```" + Util.getCommaSeparatedFormattedList((String[]) clipNames) + "```");
					break;
				case disable:
					if (getHasPermission(user, guild, TrustLevel.Trusted)) {
						channel.sendMessage("BigSausage disabled. Type \"!bs enable\" to reenable me!");
						IO.setStateForGuild(guild, State.Disabled);
					} else {
						channel.sendMessage("You can't tell me what to do, ask an administrator.");
					}
					break;
				case enable:
					if (getHasPermission(user, guild, TrustLevel.Trusted)) {
						channel.sendMessage("BigSausage enabled. Type \"!bs disable\" to disable me!");
						IO.setStateForGuild(guild, State.Enabled);
					} else {
						channel.sendMessage("You can't tell me what to do, ask an administrator.");
					}
					break;
				case remove_trust:
					if (getHasPermission(user, guild, TrustLevel.Admin_Only)) {
						List<String> trusted = trustedUsersPerGuild.get(guild.getStringID());
						if ((wordList.size() > 2)) {
							String mention = wordList.get(2);
							mention = mention.replace("<@", "").replace(">", "");
							if (trusted.contains(mention)) {
								trusted.remove(mention);
								channel.sendMessage("Removed <@" + mention + "> from the trusted users list.");
							}
						}
					} else {
						if (rand.nextFloat() < 0.02F) {
							channel.sendMessage("Hey " + guild.getOwner().mention() + ", " + user.mention() + " is trying to use a restricted command!");
						} else {
							channel.sendMessage("You don't have permission to use this command!");
						}
					}
					break;
				case save_all:
					if (user.getStringID().contentEquals(myUserID)) {
						save();
						channel.sendMessage("Successfully saved all settings globally");
					}
					break;
				case setup_defaults:
					if (getHasPermission(user, guild, TrustLevel.Admin_Only)) {
						setupDefaults(guild);
						channel.sendMessage("Reset settings to default.");
					}
					break;
				case shutdown:
					if (user.getStringID().contentEquals(myUserID)) {
						save();
						channel.sendMessage("Yes, master...");
						client.logout();
						if (!client.isLoggedIn()) {
							System.out.println("Successfully logged out.");
						}
					}
					break;
				case status:
					channel.sendMessage("BigSausage is " + (IO.getStateForGuild(guild).toString()));
					break;
				case target:
					if (getHasPermission(user, guild, TrustLevel.Trusted)) {
						String username = wordList.get(2);
						IUser targetedUser = message.getGuild().getUserByID(Long.valueOf(username.replace("@", "").replace("<", "").replace(">", "").replace("!", "")));
						if (IO.getStateForGuild(guild) == State.Enabled) {
							List<String> newList = new ArrayList<String>();
							for (String s : wordList) {
								if (!s.contains(PREFIX)) {
									newList.add(s);
								}
							}
							this.checkListAndQueueFile(message.getContent(), newList, guild, targetedUser, channel, true);
						}
					} else {
						channel.sendMessage("Who do you think you are, " + user.mention() + "?");
					}
					break;
				case trust:
					if (getHasPermission(user, guild, TrustLevel.Admin_Only)) {
						List<String> trusted = trustedUsersPerGuild.get(guild.getStringID());
						if (words.length == 2) {
							String out = "";
							List<String> formattedTrusted = new ArrayList<String>();
							trusted.forEach(s -> formattedTrusted.add("<@" + s + ">"));
							out = Util.getCommaSeparatedFormattedList(formattedTrusted);
							channel.sendMessage("Trusted users: \n" + out + "");
						} else {
							String mention = wordList.get(2);
							mention = mention.replace("<@", "").replace(">", "");
							trusted.add(mention);
							trustedUsersPerGuild.put(guild.getStringID(), trusted);
							save();
							channel.sendMessage("Added <@" + mention + "> to the trusted users list.");
						}
					} else {
						if (rand.nextFloat() < 0.02F) {
							channel.sendMessage("Hey " + guild.getOwner().mention() + ", " + user.mention() + " is trying to use a restricted command!");
						} else {
							channel.sendMessage("You don't have permission to use this command!");
						}
					}
					break;
				case version:
					channel.sendMessage("I am BigSausage version " + VERSION);
					break;
				default:
				case help:
					if (wordList.size() == 2) {
						channel.sendMessage(EnumCommand.help.getHelpText());
					} else {
						EnumCommand com = EnumCommand.getFromString(wordList.get(2));
						channel.sendMessage(com.getHelpText());
					}
					break;
			}
		} else if (words[0].contentEquals(PREFIX) && words.length == 1) {
			channel.sendMessage("For help please use !bs help");
		} else {
			if (IO.getStateForGuild(guild) == State.Enabled) {
				this.checkListAndQueueFile(message.getContent(), wordList, guild, user, channel, false);
			}
		}
	}

	public void setupDefaults(IGuild guild) {
		for (EnumClips clip : EnumClips.values()) {
			if (clip.getDefaultTriggers().size() > 0) {
				IO.saveTriggersForGuild(guild, clip.getDefaultTriggers(), clip);
			}
		}
		for (EnumImage image : EnumImage.values()) {
			if (image.getDefaultTriggers().size() > 0) {
				IO.saveTriggersForGuild(guild, image.getDefaultTriggers(), image);
			}
		}
	}

	public void save() {
		try {
			File serverStates = new File("states.s");
			File trustedUsers = new File("trusted.s");
			if (serverStates.exists()) {
				serverStates.delete();
			}
			if (trustedUsers.exists()) {
				trustedUsers.delete();
			}
			serverStates.createNewFile();
			trustedUsers.createNewFile();
			ObjectOutputStream trustedOut = new ObjectOutputStream(new FileOutputStream(trustedUsers));
			trustedOut.writeObject(trustedUsersPerGuild);
			trustedOut.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void checkListAndQueueFile(String command, List<String> commandText, IGuild guild, IUser triggerUser, IChannel triggerChannel, boolean wasCommanded) throws FileNotFoundException {
		SecureRandom rand = new SecureRandom();
		if (commandText.size() == 0 || commandText.get(0).contentEquals(PREFIX)) return;
		for (EnumImage image : EnumImage.values()) {
			boolean found = false;
			for (String word : commandText) {
				List<String> list = IO.getTriggersForGuild(guild, image);
				for (String trigger : list) {
					if (word.toLowerCase().contains(trigger)) {
						triggerChannel.sendFile(image.getFile());
						found = true;
						break;
					}
				}
				if (found) break;
			}
		}
		boolean linkedFile = false;
		for (String word : commandText) {
			for (EnumClips clip : EnumClips.values()) {
				List<String> list = IO.getTriggersForGuild(guild, clip);
				for (String s : list) {
					if (word.toLowerCase().contains(s)) {
						for (IVoiceChannel vChannel : guild.getVoiceChannels()) {
							if (vChannel.getConnectedUsers().contains(triggerUser)) {
								if (rand.nextFloat() < 0.06F && !wasCommanded) {
									this.queueFile(silence, guild, vChannel, triggerUser, wasCommanded);
								}
								if (rand.nextFloat() > 0.01 || wasCommanded) {
									this.queueFile(clip.getFile(), guild, vChannel, triggerUser, wasCommanded);
								} else {
									this.queueFile(never, guild, vChannel, triggerUser, wasCommanded);
								}
								linkedFile = true;
							}
						}
					}
				}
			}
		}
		if (linkedFile == false && rand.nextFloat() < 0.20F) {
			for (EnumClips clip : EnumClips.values()) {
				List<String> list = IO.getTriggersForGuild(guild, clip);
				for (String s : list) {
					if (command.replace(" ", "").contains(s)) {
						for (IVoiceChannel vChannel : guild.getVoiceChannels()) {
							if (vChannel.getConnectedUsers().contains(triggerUser)) {
								if (rand.nextFloat() < 0.06F && !wasCommanded) {
									this.queueFile(silence, guild, vChannel, triggerUser, wasCommanded);
								}
								if (rand.nextFloat() > 0.01 || wasCommanded) {
									this.queueFile(clip.getFile(), guild, vChannel, triggerUser, wasCommanded);
								} else {
									this.queueFile(never, guild, vChannel, triggerUser, wasCommanded);
								}
							}
						}
					}
				}
			}
		}
	}

	private void queueFile(File f, IGuild guild, IVoiceChannel channelToJoin, IUser triggerUser, boolean commanded) {
		int queueLength = 0;
		try {
			AudioPlayer player = getPlayer(guild);
			queueLength = player.getPlaylist().size();
			if (queueLength < IO.getMaxFilesPerMessage(guild)) {
				player.setLoop(false);
				player.setPaused(false);
				join(channelToJoin, triggerUser, commanded);
				player.queue(f);
				if (f == nightmare) {
					player.setVolume(0.09f);
				} else {
					player.setVolume(1);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@EventSubscriber
	public void onTrackStart(TrackStartEvent event) throws RateLimitException, DiscordException, MissingPermissionsException {

	}

	@EventSubscriber
	public void onTrackFinish(TrackFinishEvent event) throws RateLimitException, DiscordException, MissingPermissionsException {
		Optional<Track> newT = event.getNewTrack();
		if (!newT.isPresent()) {
			List<Fucker> removalList = new ArrayList<Fucker>();
			for (Fucker f : fucked) {
				if (f.originChannel.getGuild() == event.getPlayer().getGuild()) {
					f.fuckTarget.moveToVoiceChannel(f.originChannel);
					removalList.add(f);
				}
			}
			for (Fucker f : removalList) {
				fucked.remove(f);
			}
			event.getPlayer().getGuild().getConnectedVoiceChannel().leave();
		} else {
		}
	}

	private void join(IVoiceChannel channel, IUser user, boolean commanded) throws RateLimitException, DiscordException, MissingPermissionsException {
		if (channel == null || user == null) {
			return;
		} else if (user.getVoiceStateForGuild(channel.getGuild()) == null) {
			return;
		} else if (user.getVoiceStateForGuild(channel.getGuild()).getChannel() == null) {
			channel.join();
		} else if (user.getVoiceStateForGuild(channel.getGuild()).getChannel().getConnectedUsers().contains(user) || commanded) {
			IVoiceChannel voice;
			if (!commanded) {
				voice = user.getVoiceStateForGuild(channel.getGuild()).getChannel();
			} else {
				voice = channel;
			}
			if (!voice.getModifiedPermissions(client.getOurUser()).contains(Permissions.VOICE_CONNECT)) {
			} else {
				voice.join();
			}
		}
	}

	private AudioPlayer getPlayer(IGuild guild) {
		return AudioPlayer.getAudioPlayerForGuild(guild);
	}

	static boolean getHasPermission(IUser user, IGuild guild, TrustLevel level) {
		List<String> trustedUsers = trustedUsersPerGuild.get(guild.getStringID());
		switch (level) {
			case Admin_Only:
				return (user.getPermissionsForGuild(guild).contains(Permissions.ADMINISTRATOR) || user.getStringID().contentEquals(myUserID));
			case Trusted:
				return (trustedUsers.contains(user.getStringID()) || user.getPermissionsForGuild(guild).contains(Permissions.ADMINISTRATOR) || user.getStringID().contentEquals(myUserID));
			default:
				return false;
		}
	}

	static enum TrustLevel {
		Admin_Only, Trusted;
	}

	static enum State {
		Enabled, Disabled;

		public static State getFromString(String s) {
			switch (s) {
				case "enabled":
					return Enabled;
				case "disabled":
					return Disabled;
				default:
					return Disabled;
			}
		}
	}

	public static void download(URL url, File location) throws IOException {
		URLConnection c = url.openConnection();
		InputStream in = c.getInputStream();

		FileOutputStream out = new FileOutputStream(location);
		int n = -1;
		byte[] buffer = new byte[2048];
		while ((n = in.read(buffer)) != -1) {
			if (n > 0) {
				out.write(buffer, 0, n);
			}
		}
		in.close();
		out.close();
	}

	public void startRenameBat() throws Exception {
		ArrayList<String> command = new ArrayList<String>();
		command.add("rename.bat");

		final ProcessBuilder builder = new ProcessBuilder(command);
		builder.start();
		System.exit(0);
	}
}
