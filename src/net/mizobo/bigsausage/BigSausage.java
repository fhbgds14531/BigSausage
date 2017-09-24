package net.mizobo.bigsausage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.sound.sampled.UnsupportedAudioFileException;

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

	private static String TOKEN = "";
	private static final String PREFIX = "!bs";
	private static IDiscordClient client;
	static Map<String, State> statePerGuild = new HashMap<>();
	static Map<String, List<String>> trustedUsersPerGuild = new HashMap<String, List<String>>();
	private static final File sausage = new File("Thomas_Sausage.wav");
	private static final File ugly = new File("Ugly.wav");
	private static final File fire = new File("Fire.wav");
	private static final File enemy = new File("enemy_spotted.wav");
	private static final File linked = new File("linked.wav");
	private static final File miceway = new File("miceway.wav");
	private static final File sceptre = new File("sceptre.wav");
	private static final File hatemyself = new File("hatemyself.wav");
	private static final File hcw = new File("hcw.wav");
	private static final File korean = new File("korean.wav");
	private static final File bursela = new File("bursela.wav");
	private static final File burse = new File("burse.wav");
	private static final File egg = new File("egg.wav");
	private static final File whiskey = new File("whiskey.wav");
	private static final File corndog = new File("corndog.png");
	private static final int maxQueueSize = 4;
	private static final String myUserID = "198575970624471040";
	private static final List<String> sausageList = Arrays.asList(new String[] { "sausage", "thomas", "daddy" });
	private static final List<String> uglyList = Arrays.asList(new String[] { "ugly", "motherfucker", "jame" });
	private static final List<String> enemyList = Arrays.asList(new String[] { "enemy", "spotted", "pubg" });
	private static final List<String> fireList = Arrays.asList(new String[] { "hole", "fire", "grenade", "bomb", "molotov" });
	private static final List<String> linkedList = Arrays.asList(new String[] { "link", "daughter" });
	private static final List<String> hateMyselfList = Arrays.asList(new String[] { "hate", "myself" });
	private static final List<String> miceWayList = Arrays.asList(new String[] { "blind" });
	private static final List<String> sceptreList = Arrays.asList(new String[] { "sceptre", "boost" });
	private static final List<String> hcwList = Arrays.asList(new String[] { "hcw", "buns" });
	private static final List<String> koreanList = Arrays.asList(new String[] { "policewoman", "korea" });
	private static final List<String> burselaList = Arrays.asList(new String[] { "ursela" });
	private static final List<String> burseList = Arrays.asList(new String[] { "burse", "limit" });
	private static final List<String> whiskeyList = Arrays.asList(new String[] { 
			"beer", "wine", "whiskey", "rum", "vodka", "gin", "scotch", "bourbon", "moonshine", "everclear", "tequila", "brandy" });
	private static final List<String> eggList = Arrays.asList(new String[] { "audio-easter-egg" });

	private static final String VERSION = "0.1.2";

	private static final File serverSettings = new File("settings");

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws DiscordException, RateLimitException, FileNotFoundException, IOException, ClassNotFoundException {
		TOKEN = Files.readAllLines(new File("BigSausage.token").toPath()).get(0);
		if (!serverSettings.exists()) serverSettings.mkdirs();
		Map<String, State> tempMap = new HashMap<String, State>();
		File statesFile = new File("states.s");
		if (statesFile.exists()) {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(statesFile));
			tempMap = (HashMap<String, State>) in.readObject();
			in.close();
		}
		statePerGuild = tempMap;
		Map<String, List<String>> tempMap1 = new HashMap<String, List<String>>();
		File trustedUsersFile = new File("trusted.s");
		if (trustedUsersFile.exists()) {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(trustedUsersFile));
			tempMap1 = (Map<String, List<String>>) in.readObject();
			in.close();
		}
		trustedUsersPerGuild = tempMap1;
		System.out.println("Logging bot in...");
		client = new ClientBuilder().withToken(TOKEN).build();
		client.getDispatcher().registerListener(new BigSausage());
		client.login();
	}

	@EventSubscriber
	public void onReady(ReadyEvent event) {
		System.out.println("BigSausage ready for mouths.");
	}

	@EventSubscriber
	public void onJoin(GuildCreateEvent event) {
		IO.loadSettingsForGuild(event.getGuild());
		if (!statePerGuild.containsKey(event.getGuild().getStringID())) {
			statePerGuild.put(event.getGuild().getStringID(), State.Disabled);
			System.out.println("Added guild \"" + event.getGuild().getName() + "\" to the states registry.");
		}
		if (!trustedUsersPerGuild.containsKey(event.getGuild().getStringID())) {
			trustedUsersPerGuild.put(event.getGuild().getStringID(), new ArrayList<String>());
			List<String> trusted = trustedUsersPerGuild.get(event.getGuild().getStringID());
			trusted.add(myUserID);
			System.out.println("Added guild \"" + event.getGuild().getName() + "\" to the trusted users registry.");
		}
		File triggersDir = new File("settings/" + event.getGuild().getStringID() + "/triggers");
		if (!triggersDir.exists()) {
			this.setupDefaults(event.getGuild());
			System.out.println("Setup default triggers for \"" + event.getGuild().getName() + "\"");
		}
	}

	@EventSubscriber
	public void onMessage(MessageReceivedEvent event) throws RateLimitException, DiscordException, MissingPermissionsException, UnsupportedAudioFileException, IOException {
		boolean sharedSucc = false;
		IMessage message = event.getMessage();
		IUser user = message.getAuthor();
		if (user.isBot()) return;

		String[] words = message.getContent().split(" ");

		IChannel channel = message.getChannel();
		IGuild guild = message.getGuild();
		for (String word : words) {
			List<String> wordList = Arrays.asList(words);
			// TODO Commands go here
			if (words[0].contentEquals(PREFIX)) {
				if (word.toLowerCase().contentEquals("target")) {
					if (!wordList.contains("help")) {
						if (getHasPermission(user, guild, TrustLevel.Trusted)) {
							String username = wordList.get(wordList.indexOf(word) + 1);
							String word1 = wordList.get(wordList.indexOf(username) + 1);
							System.out.println(username + ", " + word1);
							IUser u = message.getGuild().getUserByID(Long.valueOf(username.replace("@", "").replace("<", "").replace(">", "")));
							if (statePerGuild.get(guild.getStringID()) == State.Enabled) {
								this.checkListAndQueueFile(word1, "sausage", sausage, guild, u, channel);
								this.checkListAndQueueFile(word1, "ugly", ugly, guild, u, channel);
								this.checkListAndQueueFile(word1, "enemy", enemy, guild, u, channel);
								this.checkListAndQueueFile(word1, "fire", fire, guild, u, channel);
								this.checkListAndQueueFile(word1, "linked", linked, guild, u, channel);
								this.checkListAndQueueFile(word1, "sceptre", sceptre, guild, u, channel);
								this.checkListAndQueueFile(word1, "hatemyself", hatemyself, guild, u, channel);
								this.checkListAndQueueFile(word1, "miceway", miceway, guild, u, channel);
								this.checkListAndQueueFile(word1, "hcw", hcw, guild, u, channel);
								this.checkListAndQueueFile(word1, "korean", korean, guild, u, channel);
								this.checkListAndQueueFile(word1, "burse", burse, guild, u, channel);
								this.checkListAndQueueFile(word1, "bursela", bursela, guild, u, channel);
								this.checkListAndQueueFile(word1, "whiskey", whiskey, guild, u, channel);
								this.checkListAndQueueFile(word1, "egg", egg, guild, u, channel);
								if (word.toLowerCase().contains("succ") && !sharedSucc) {
									channel.sendFile(corndog);
									sharedSucc = true;
								}
							}
						} else {
							channel.sendMessage("Who do you think you are, " + user.mention() + "?");
						}
					}
				}
				if (word.toLowerCase().contentEquals("removetrust")) {
					if (!wordList.contains("help")) {
						if (getHasPermission(user, guild, TrustLevel.Admin_Only)) {
							List<String> trusted = trustedUsersPerGuild.get(guild.getStringID());
							if ((wordList.indexOf(word) != wordList.size() - 1)) {
								String mention = wordList.get(wordList.indexOf(word) + 1);
								mention = mention.replace("<@", "").replace(">", "");
								if (trusted.contains(mention)) {
									trusted.remove(mention);
									channel.sendMessage("Removed <@" + mention + "> from the trusted users list.");
								}
							}
						}
					}
				}
				if (word.toLowerCase().contentEquals("trust")) {
					if (!wordList.contains("help")) {
						if (getHasPermission(user, guild, TrustLevel.Admin_Only)) {
							List<String> trusted = trustedUsersPerGuild.get(guild.getStringID());
							if (words.length == 2) {
								String out = "";
								for (String username : trusted) {
									// if(!username.contentEquals(myUserID)){
									out += "<@" + username + ">, ";
									// }
								}
								channel.sendMessage("Trusted users: " + out.substring(0, out.lastIndexOf(", ")));
							} else {
								if ((wordList.indexOf(word) != wordList.size() - 1)) {
									String mention = wordList.get(wordList.indexOf(word) + 1);
									mention = mention.replace("<@", "").replace(">", "");
									trusted.add(mention);
									trustedUsersPerGuild.put(guild.getStringID(), trusted);
									channel.sendMessage("Added <@" + mention + "> to the trusted users list.");
								}
							}
						}
					}
				}
				if (word.toLowerCase().contentEquals("shutdown")) {
					if (!wordList.contains("help")) {
						if (message.getAuthor().getStringID().contentEquals(myUserID)) {
							channel.sendMessage("Yes master...");
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
								ObjectOutputStream statesOut = new ObjectOutputStream(new FileOutputStream(serverStates));
								statesOut.writeObject(statePerGuild);
								statesOut.close();
								ObjectOutputStream trustedOut = new ObjectOutputStream(new FileOutputStream(trustedUsers));
								trustedOut.writeObject(trustedUsersPerGuild);
								trustedOut.close();
							} catch (Exception e) {
								e.printStackTrace();
							}
							client.logout();
							if (!client.isLoggedIn()) {
								System.out.println("Successfully logged out.");
							}
						} else {
							channel.sendMessage("You're not my master! You can't tell me what to do!");
						}
					}
				}
				if (word.toLowerCase().contentEquals("save-all")) {
					if (!wordList.contains("help")) {
						if (message.getAuthor().getStringID().contentEquals(myUserID)) {
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
								ObjectOutputStream statesOut = new ObjectOutputStream(new FileOutputStream(serverStates));
								statesOut.writeObject(statePerGuild);
								statesOut.close();
								ObjectOutputStream trustedOut = new ObjectOutputStream(new FileOutputStream(trustedUsers));
								trustedOut.writeObject(trustedUsersPerGuild);
								trustedOut.close();
								channel.sendMessage("Successfully saved all settings globally.");
							} catch (Exception e) {
								e.printStackTrace();
							}
						} else {
							channel.sendMessage("You're not my master! You can't tell me what to do!");
						}
					}
				}
				if (word.toLowerCase().contentEquals("help")) {
					if (words.length == 2) {
						channel.sendMessage("!bs <enable, disable, clips, status, target, trust, removetrust, setupdefaults, addtriggerfor, removetriggerfor, listtriggersfor, help>");
					} else {
						String nextWord = "";
						if (wordList.indexOf(word) != wordList.size() - 1) {
							nextWord = wordList.get(wordList.indexOf(word) + 1);
						}
						switch (nextWord) {
							case "enable":
								channel.sendMessage("Enables the bot's functionality, stupid.");
								break;
							case "disable":
								channel.sendMessage("Disables the bot's functionality, stupid.");
								break;
							case "status":
								channel.sendMessage("Checks the status of the bot's funcionality (Enabled/Disabled)");
								break;
							case "target":
								channel.sendMessage("Usage: !bs target \\@username <list of things to say, seperated by spaces>");
								break;
							case "trust":
								channel.sendMessage("Adds a trusted user, can only be used by admin. Usage: !bs trust \\@username");
								break;
							case "setupdefaults":
								channel.sendMessage("Resets all trigger words back to the default values, can only be used by an admin. Usage: !bs setupdefaults");
								break;
							case "clips":
								channel.sendMessage("Lists the names of all the available audio clips. Usage: !bs clips");
								break;
							case "removetrust":
								channel.sendMessage("Removes a trusted user, can only be used by admin. Usage: !bs removetrust \\@username");
								break;
							case "addtriggerfor":
								channel.sendMessage(
										"Adds a trigger word for an audio clip, can only be used by an admin. Available clips: use !bs clips Usage: !bs addtriggerfor <clipname> <newTrigger>");
								break;
							case "removetriggerfor":
								channel.sendMessage(
										"Removes a trigger word for an audio clip, can only be used by an admin. Available clips: use !bs clips Usage: !bs removetriggerfor <clipname> <oldTrigger>");
								break;
							case "listtriggersfor":
								channel.sendMessage(
										"Lists all the trigger words for an audio clip, can only be used by a trusted user. Available clips: use !bs clips Usage: !bs listtriggersfor <clipname>");
								break;
							default:
								channel.sendMessage("Usage: !bs help <command name>");
								break;
						}
					}
				}
				if (word.toLowerCase().contentEquals("clips")) {
					if (!wordList.contains("help")) {
						if (getHasPermission(user, guild, TrustLevel.Trusted)) {
							channel.sendMessage("sausage, ugly, enemy, fire, linked, linked, hatemyself, miceway, sceptre, hcw, korean, bursela, burse, whiskey");
						}
					}

				}
				if (word.toLowerCase().contentEquals("disable")) {
					if (!wordList.contains("help")) {
						if (getHasPermission(user, guild, TrustLevel.Trusted)) {
							channel.sendMessage("BigSausage disabled. Type \"!bs enable\" to reenable me!");
							statePerGuild.put(guild.getStringID(), State.Disabled);
						} else {
							channel.sendMessage("You can't tell me what to do, ask an administrator.");
						}
					}
				}
				if (word.toLowerCase().contentEquals("enable")) {
					if (!wordList.contains("help")) {
						if (getHasPermission(user, guild, TrustLevel.Trusted)) {
							channel.sendMessage("BigSausage enabled. Type \"!bs disable\" to disable me!");
							statePerGuild.put(guild.getStringID(), State.Enabled);
						} else {
							channel.sendMessage("You can't tell me what to do, ask an administrator.");
						}
					}
				}
				if (word.toLowerCase().contentEquals("status")) {
					if (!wordList.contains("help")) {
						State state = statePerGuild.get(message.getGuild().getStringID());
						if (state != null) {
							channel.sendMessage("BigSausage is " + state.toString().toLowerCase());
						} else {
							channel.sendMessage("BigSausage is " + State.Disabled.toString().toLowerCase());
						}
					}
				}
				if (word.toLowerCase().contentEquals("setupdefaults")) {
					if (!wordList.contains("help")) {
						if (getHasPermission(user, guild, TrustLevel.Admin_Only)) {
							this.setupDefaults(guild);
							channel.sendMessage("Reset all triggers to their default state.");
						}
					}
				}
				if (word.toLowerCase().contentEquals("addtriggerfor")) {
					if (wordList.size() != 4) {
						channel.sendMessage("Invalid number of arguments. Use \"!bs help addtriggerfor\" to see the correct usage.");
					} else if (!wordList.contains("help")) {
						if (getHasPermission(user, guild, TrustLevel.Admin_Only)) {
							String nextWord = wordList.get(wordList.indexOf(word) + 1).toLowerCase();
							String trigger = wordList.get(wordList.indexOf(nextWord) + 1).toLowerCase();
							switch (nextWord) {
								case "sausage":
								case "ugly":
								case "enemy":
								case "fire":
								case "linked":
								case "miceway":
								case "sceptre":
								case "hatemyself":
								case "hcw":
								case "korean":
								case "bursela":
								case "burse":
								case "whiskey":
								case "egg":
									IO.addTriggerWord(trigger, nextWord, guild);
									break;
								default:
									channel.sendMessage("Unknown type: \"" + nextWord + "\" Use \"!bs help addtriggerfor\" to see the correct usage.");
									break;
							}
						}
					}
				}
				if (word.toLowerCase().contentEquals("removetriggerfor")) {
					if (wordList.size() != 4) {
						channel.sendMessage("Invalid number of arguments. Use \"!bs help removetriggerfor\" to see the correct usage.");
					} else if (!wordList.contains("help")) {
						if (getHasPermission(user, guild, TrustLevel.Admin_Only)) {
							String nextWord = wordList.get(wordList.indexOf(word) + 1).toLowerCase();
							String trigger = wordList.get(wordList.indexOf(nextWord) + 1).toLowerCase();
							switch (nextWord) {
								case "sausage":
								case "ugly":
								case "enemy":
								case "fire":
								case "linked":
								case "miceway":
								case "sceptre":
								case "hatemyself":
								case "hcw":
								case "korean":
								case "bursela":
								case "burse":
								case "whiskey":
								case "egg":
									IO.removeTriggerWord(trigger, nextWord, guild);
									break;
								default:
									channel.sendMessage("Unknown type: \"" + nextWord + "\" Use \"!bs help removetriggerfor\" to see the correct usage.");
									break;
							}
						}
					}
				}
				if (word.toLowerCase().contentEquals("listtriggersfor")) {
					if (wordList.size() != 3) {
						channel.sendMessage("Invalid number of arguments. Use \"!bs help listtriggersfor\" to see the correct usage.");
					} else if (!wordList.contains("help")) {
						if (getHasPermission(user, guild, TrustLevel.Trusted)) {
							String nextWord = wordList.get(wordList.indexOf(word) + 1).toLowerCase();
							switch (nextWord) {
								case "sausage":
								case "ugly":
								case "enemy":
								case "fire":
								case "linked":
								case "miceway":
								case "sceptre":
								case "hatemyself":
								case "hcw":
								case "korean":
								case "bursela":
								case "burse":
								case "whiskey":
								case "egg":
									List<String> triggers = IO.getTriggersForGuild(guild, nextWord);
									String out = "";
									for (String s : triggers) {
										out += s + ", ";
									}
									out = out.substring(0, out.lastIndexOf(", "));
									channel.sendMessage("Triggers for \"" + nextWord + "\" are as follows: " + out);
									break;
								default:
									channel.sendMessage("Unknown type: \"" + nextWord + "\" Use \"!bs help listtriggersfor\" to see the correct usage.");
									break;
							}
						}
					}
				}
				if (word.toLowerCase().contentEquals("version")) {
					channel.sendMessage("I am BigSausage! Version " + VERSION + ", fear me!");
				}
			} else { // TODO trigger words
				if (statePerGuild.get(guild.getStringID()) == State.Enabled) {
					this.checkListAndQueueFile(word, "sausage", sausage, guild, user, channel);
					this.checkListAndQueueFile(word, "ugly", ugly, guild, user, channel);
					this.checkListAndQueueFile(word, "enemy", enemy, guild, user, channel);
					this.checkListAndQueueFile(word, "fire", fire, guild, user, channel);
					this.checkListAndQueueFile(word, "linked", linked, guild, user, channel);
					this.checkListAndQueueFile(word, "sceptre", sceptre, guild, user, channel);
					this.checkListAndQueueFile(word, "hatemyself", hatemyself, guild, user, channel);
					this.checkListAndQueueFile(word, "miceway", miceway, guild, user, channel);
					this.checkListAndQueueFile(word, "hcw", hcw, guild, user, channel);
					this.checkListAndQueueFile(word, "korean", korean, guild, user, channel);
					this.checkListAndQueueFile(word, "burse", burse, guild, user, channel);
					this.checkListAndQueueFile(word, "bursela", bursela, guild, user, channel);
					this.checkListAndQueueFile(word, "whiskey", whiskey, guild, user, channel);
					this.checkListAndQueueFile(word, "egg", egg, guild, user, channel);
					if (word.toLowerCase().contains("succ") && !sharedSucc) {
						channel.sendFile(corndog);
						sharedSucc = true;
					}
				}
			}
		}
	}

	public void setupDefaults(IGuild guild) {
		IO.saveTriggersForGuild(guild, sausageList, "sausage");
		IO.saveTriggersForGuild(guild, uglyList, "ugly");
		IO.saveTriggersForGuild(guild, enemyList, "enemy");
		IO.saveTriggersForGuild(guild, fireList, "fire");
		IO.saveTriggersForGuild(guild, linkedList, "linked");
		IO.saveTriggersForGuild(guild, sceptreList, "sceptre");
		IO.saveTriggersForGuild(guild, miceWayList, "miceway");
		IO.saveTriggersForGuild(guild, hateMyselfList, "hatemyself");
		IO.saveTriggersForGuild(guild, hcwList, "hcw");
		IO.saveTriggersForGuild(guild, koreanList, "korean");
		IO.saveTriggersForGuild(guild, burselaList, "bursela");
		IO.saveTriggersForGuild(guild, burseList, "burse");
		IO.saveTriggersForGuild(guild, eggList, "egg");
		IO.saveTriggersForGuild(guild, whiskeyList, "whiskey");
	}

	public void checkListAndQueueFile(String word, String listName, File filetoQueue, IGuild guild, IUser triggerUser, IChannel triggerChannel) {
		List<String> list = IO.getTriggersForGuild(guild, listName);
		for (String s : list) {
			if (word.toLowerCase().contains(s)) {
				for (IVoiceChannel vChannel : guild.getVoiceChannels()) {
					if (vChannel.getConnectedUsers().contains(triggerUser)) {
						this.queueFile(filetoQueue, guild, vChannel, triggerUser, triggerChannel);
					}
				}
			}
		}
	}

	private void queueFile(File f, IGuild guild, IVoiceChannel channelToJoin, IUser triggerUser, IChannel triggerChannel) {
		int queueLength = 0;
		try {
			AudioPlayer player = getPlayer(guild);
			queueLength = player.getPlaylist().size();
			if (queueLength < maxQueueSize) {
				player.setVolume(1);
				player.setLoop(false);
				player.setPaused(false);
				join(channelToJoin, triggerUser, triggerChannel);
				player.queue(f);
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
			event.getPlayer().getGuild().getConnectedVoiceChannel().leave();
		} else {
		}
	}

	private void join(IChannel channel, IUser user, IChannel general) throws RateLimitException, DiscordException, MissingPermissionsException {
		if (!user.getVoiceStateForGuild(channel.getGuild()).getChannel().getConnectedUsers().contains(user)) {
			// general.sendMessage("You're not in a voice channel, " + user.mention());
			return;
		} else {
			IVoiceChannel voice = user.getVoiceStateForGuild(channel.getGuild()).getChannel();
			if (!voice.getModifiedPermissions(client.getOurUser()).contains(Permissions.VOICE_CONNECT)) {
				// general.sendMessage("I can't join that voice channel!");
			} else if (voice.getUserLimit() != 0 && voice.getConnectedUsers().size() >= voice.getUserLimit()) {
				// general.sendMessage("That room is full!");
			} else {
				voice.join();
				// general.sendMessage("Connected to **" + voice.getName() + "**.");
			}
		}
	}

	private AudioPlayer getPlayer(IGuild guild) {
		return AudioPlayer.getAudioPlayerForGuild(guild);
	}

	private boolean getHasPermission(IUser user, IGuild guild, TrustLevel level) {
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

	private static enum TrustLevel {
		Admin_Only, Trusted;
	}

	static enum State {
		Enabled, Disabled;
	}
}
