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
	// static Map<String, State> statePerGuild = new HashMap<>();
	static Map<String, List<String>> trustedUsersPerGuild = new HashMap<String, List<String>>();
	static final File sausage = new File("Thomas_Sausage.wav");
	static final File ugly = new File("Ugly.wav");
	static final File fire = new File("Fire.wav");
	static final File enemy = new File("enemy_spotted.wav");
	static final File linked = new File("linked.wav");
	static final File miceway = new File("miceway.wav");
	static final File sceptre = new File("sceptre.wav");
	static final File hatemyself = new File("hatemyself.wav");
	static final File hcw = new File("hcw.wav");
	static final File korean = new File("korean.wav");
	static final File bursela = new File("bursela.wav");
	static final File burse = new File("burse.wav");
	static final File choice = new File("choice.wav");
	static final File grunch = new File("grunch.wav");
	static final File sainte = new File("sainte.wav");
	static final File egg = new File("egg.wav");
	static final File whiskey = new File("whiskey.wav");
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
	private static final List<String> choiceList = Arrays.asList(new String[] { "choice", "santa" });
	private static final List<String> grunchList = Arrays.asList(new String[] { "grinch", "grunch" });
	private static final List<String> sainteList = Arrays.asList(new String[] { "saint" });
	private static final List<String> whiskeyList = Arrays
			.asList(new String[] { "beer", "wine", "whiskey", "rum", "vodka", "gin", "scotch", "bourbon", "moonshine", "everclear", "tequila", "brandy" });
	private static final List<String> eggList = Arrays.asList(new String[] { "audio-easter-egg" });

	private static final String VERSION = "0.1.2";

	private static final File serverSettings = new File("settings");

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws DiscordException, RateLimitException, FileNotFoundException, IOException, ClassNotFoundException {
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
		// if (!statePerGuild.containsKey(event.getGuild().getStringID())) {
		// statePerGuild.put(event.getGuild().getStringID(), State.Disabled);
		// System.out.println("Added guild \"" + event.getGuild().getName() + "\" to the states registry.");
		// }
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
		List<String> wordList = Arrays.asList(words);

		if (words[0].contentEquals(PREFIX) && words.length > 1) {
			EnumCommand c = EnumCommand.getFromString(words[1]);
			switch (c) {
				case add_trigger:
				case list_triggers:
				case remove_trigger:
					Commands.editTriggers(c, wordList, guild, user, channel);
					break;
				case clips:
					channel.sendMessage("Available clips are: " + EnumClips.getCommaSeparatedList());
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
						channel.sendMessage("BigSausage enabled. Type \"!bs enable\" to disable me!");
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
						IUser targetedUser = message.getGuild().getUserByID(Long.valueOf(username.replace("@", "").replace("<", "").replace(">", "")));
						if (IO.getStateForGuild(guild) == State.Enabled) {
							this.checkListAndQueueFile(wordList, guild, targetedUser, channel);
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
							for (String username : trusted) {
								// if(!username.contentEquals(myUserID)){
								out += "<@" + username + ">, ";
								// }
							}
							channel.sendMessage("Trusted users: " + out.substring(0, out.lastIndexOf(", ")));
						} else {
							String mention = wordList.get(2);
							mention = mention.replace("<@", "").replace(">", "");
							trusted.add(mention);
							trustedUsersPerGuild.put(guild.getStringID(), trusted);
							channel.sendMessage("Added <@" + mention + "> to the trusted users list.");
						}
					}
					break;
				case version:
					channel.sendMessage("I am BigSausage version " + VERSION);
					break;
				default:
				case help:
					if (wordList.size() == 2) {
						String commandList = EnumCommand.getCommaSeparatedList();
						channel.sendMessage("For specific help use !bs help <" + commandList + ">");
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
				for (String word : wordList) {
					this.checkListAndQueueFile(wordList, guild, user, channel);
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
		IO.saveTriggersForGuild(guild, choiceList, "choice");
		IO.saveTriggersForGuild(guild, grunchList, "grunch");
		IO.saveTriggersForGuild(guild, sainteList, "sainte");
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
			// ObjectOutputStream statesOut = new ObjectOutputStream(new FileOutputStream(serverStates));
			// statesOut.writeObject(statePerGuild);
			// statesOut.close();
			ObjectOutputStream trustedOut = new ObjectOutputStream(new FileOutputStream(trustedUsers));
			trustedOut.writeObject(trustedUsersPerGuild);
			trustedOut.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void checkListAndQueueFile(List<String> commandText, IGuild guild, IUser triggerUser, IChannel triggerChannel) {
		if (commandText.size() == 0 || commandText.get(0).contentEquals(PREFIX)) return;
		for (EnumClips clip : EnumClips.values()) {
			List<String> list = IO.getTriggersForGuild(guild, clip.toString());
			for (String s : list) {
				for (String word : commandText) {
					if (word.toLowerCase().contains(s)) {
						for (IVoiceChannel vChannel : guild.getVoiceChannels()) {
							if (vChannel.getConnectedUsers().contains(triggerUser)) {
								this.queueFile(clip.getFile(), guild, vChannel, triggerUser, triggerChannel);
							}
						}
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
}
