package net.mizobo.bigsausage;

import java.util.List;

import net.mizobo.bigsausage.BigSausage.TrustLevel;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IUser;

public class Commands {

	public static void editTriggers(EnumCommand command, List<String> fullCommandMessage, IGuild guild, IUser commandSender, IChannel channel) {
			if (BigSausage.getHasPermission(commandSender, guild, TrustLevel.Admin_Only)) {
				String clipName = fullCommandMessage.get(2).toLowerCase();
				String trigger = "";
				switch (command) {
					case add_trigger:
						if (fullCommandMessage.size() != 4) {
							channel.sendMessage("Invalid number of arguments. Use \"!bs help\" for help.");
							break;
						}
						trigger = fullCommandMessage.get(3);
						IO.addTriggerWord(trigger, clipName, guild);
						channel.sendMessage("Added \"" + trigger + "\" to the trigger list for \"" + clipName + "\"");
						break;
					case list_triggers:
						String out = "";
						for(String s : IO.getTriggersForGuild(guild, clipName)){
							out += s + ", ";
						}
						channel.sendMessage("Triggers for \"" + clipName + "\" are as follows: " + out.substring(0, out.lastIndexOf(", ")));
						break;
					case remove_trigger:
						if (fullCommandMessage.size() != 4) {
							channel.sendMessage("Invalid number of arguments. Use \"!bs help\" for help.");
							break;
						}
						trigger = fullCommandMessage.get(3);
						IO.removeTriggerWord(trigger, clipName, guild);
						channel.sendMessage("Removed \"" + trigger + "\" from the trigger list for \"" + clipName + "\"");
						break;
					default:
						channel.sendMessage("Error. Use \"!bs help\" for help.");
						break;

				}
			}
	}

}
