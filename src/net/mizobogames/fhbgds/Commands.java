package net.mizobogames.fhbgds;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.mizobogames.fhbgds.commands.*;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;

public class Commands {

	public static final Command help = new CommandHelp("help", "For specific help use `%p% %n% <command name>`. for a list of commands, use `%p% commands`");
	static List<Command> commands = new ArrayList<Command>();
	
	public Commands(){
		commands.add(help);
		commands.add(new CommandEnable("enable", "Enables specific functionalities of the bot. Usage: `%p% %n%` [images|audio|tts|multi-link|all]", true));
		commands.add(new CommandEnable("disable", "Disables the functionalies of the bot. Usage: `%p% %n%` [images|audio|tts|multi-link|all]", false));
		commands.add(new CommandStatus("status", "Checks the status of the bot. Usage: `%p% %n%`"));
		commands.add(new CommandCommands("commands", "Lists all the commands in the bot. Usage: `%p% %n%`"));
		commands.add(new CommandTts("tts", "Repeat a random tts string from the tts list. Usage: `%p% %n%`"));
		commands.add(new CommandAddTts("add-tts", "Add a tts string to the tts list. Usage: `%p% %n% <tts string>`"));
		commands.add(new CommandRemoveTts("remove-tts", "Remove a tts string from the tts list. Usage: `%p% %n% <tts string>`"));
		commands.add(new CommandTrust("trust", "Add a user to the trusted users list. Usage: `%p% %n% \\@<username>` to add a user or `%p% %n%` to list trusted users."));
		commands.add(new CommandRemoveTrust("remove-trust", "Remove a user from the trusted users list. Usage: `%p% %n% \\@<username>`"));
		commands.add(new CommandRemoveFile("remove-file", "Remove a file from the pool. Usage: `%p% %n% <file_name>`."));
		commands.add(new CommandListTriggers("list-triggers", "List the triggers for a clip or image. Usage: `%p% %n%`"));
		commands.add(new CommandAddTrigger("add-trigger", "Add a trigger for a clip or image. Usage: `%p% %n% <name> <word>`"));
		commands.add(new CommandRemoveTrigger("remove-trigger", "Remove a trigger from a clip or image. Usage: `%p% %n% <name> <word>`"));
		commands.add(new CommandMaxClips("max-clips", "List or edit the maximum number of audio clips that BigSausage will queue per message. Usage: `%p% %n%` to list the current setting or `%p% %n% <number>` to set a new maximum."));
		commands.add(new CommandRoll("roll", "Roll some dice. Usage: `%p% %n% <X>d<Y>` where X is the number of dice and Y is the number of sides per die."));
		commands.add(new CommandUpload("upload", "Adds a file to be linked or played. Audio files must be of the `.wav` format. Usage: `%p% %n% <a name for the file> <default trigger list>`"));
		commands.add(new CommandListFiles("list", "Lists files that have been uploaded. If the third parameter is left blank, will list all files. Usage: `%p% %n% <images|audio>`"));
		commands.add(new CommandBugreport("bugreport", "Sends a bug report to the official channel, the report includes your user ID and name in case you need to be contacted. " +
				"Usage: `%p% %n% <description of what went wrong>`"));
		commands.add(new CommandTtsInfo("tts-info", "Lists off statistics about the tts file for this server."));
		commands.add(new CommandChangelog("changelog", "Show the changelog for the bot. Usage: `%p% %n%`"));
		commands.add(new CommandChangelog("version", "Show the current version for the bot. Usage: `%p% %n%`"));
		commands.add(new CommandRandomFile("voice", "Play a random voice clip or several. Usage: `%p% %n% [number of clips to play (optional)]`"));
		commands.add(new CommandRandomFile("image", "Upload a random image. Usage: `%p% %n%`"));
		commands.add(new CommandDeleteLast("clear", "Delete up to the last 15 sequential BigSausage related messages. Usage: `%p% %n%`"));
		commands.add(new CommandFuck("fuck", "Moves the specified user to the BigSausage channel and plays the specified audio clip. Usage: `%p% %n% @User <Voice Clip>`"));
		commands.add(new CommandUpdate("update", ""));
		commands.add(new CommandShutdown("shutdown", ""));
		commands.add(new CommandRestart("restart", ""));
	}
	
	public Command getFromString(String command){
		for(Command c : commands){
			if(c.getCommandString().contentEquals(command)){
				return c;
			}
		}
		return help;
	}
	
	public boolean findAndExecuteCommand(List<String> messageContent, IChannel channel, IUser author, IGuild guild, IMessage message){
		try{
			if(messageContent.get(0).contentEquals(BigSausage.PREFIX) || messageContent.get(0).replace("!", "").startsWith(BigSausage.client.getOurUser().mention().replace("!", ""))){
				if(messageContent.size() > 1){
					this.getFromString(messageContent.get(1)).execute(channel, author, guild, messageContent, message);
					return true;
				}else{
					help.execute(channel, author, guild, Arrays.asList(new String[] {BigSausage.PREFIX, "help"}), message);
					return true;
				}
			}else{
				return false;
			}
		}catch (Exception e){
			e.printStackTrace();
			channel.sendMessage("Are you trying to break me? If not, please send the command you just typed using `!bs bugreport` to the developer. If so, please don't.");
			return false;
		}
	}
	
	public class CommandCommands extends Command{

		public CommandCommands(String commandString, String helpString) {
			super(commandString, helpString);
		}

		@Override
		public void execute(IChannel channel, IUser commandAuthor, IGuild guild, List<String> command, IMessage message) {
			List<Command> commands = Commands.commands;
			List<String> names = new ArrayList<String>();
			for(Command c : commands){
				if(!c.getIsHidden()) names.add(c.getCommandString());
			}
			channel.sendMessage("```" + Util.getCommaSeparatedFormattedList(names) + "```");
		}
	}
}
