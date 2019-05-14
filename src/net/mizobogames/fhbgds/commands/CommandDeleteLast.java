package net.mizobogames.fhbgds.commands;

import java.util.ArrayList;
import java.util.List;

import net.mizobogames.fhbgds.BigSausage;
import net.mizobogames.fhbgds.Command;
import net.mizobogames.fhbgds.PermissionLevels;
import net.mizobogames.fhbgds.Util;
import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IUser;
import sx.blah.discord.util.MessageHistory;

public class CommandDeleteLast extends Command {

	public CommandDeleteLast(String commandString, boolean hiddenFromHelp, String helpString) {
		super(commandString, hiddenFromHelp, helpString);
	}

	public CommandDeleteLast(String commandString, String helpString) {
		super(commandString, helpString);
	}

	@Override
	public void execute(IChannel channel, IUser commandAuthor, IGuild guild, List<String> command, IMessage message) {
		if(Util.hasPermission(PermissionLevels.PERMISSION_LEVEL_TRUSTED, guild, commandAuthor)){
			MessageHistory history = channel.getMessageHistory(16);
			int i;
			List<IMessage> messages = new ArrayList<IMessage>();
			messages.add(history.getLatestMessage());
			for(i = 1; i < 16; i++){
				IMessage m = history.get(i);
				if((m.getAuthor().getLongID() == BigSausage.client.getOurUser().getLongID()) || 
						m.toString().startsWith(BigSausage.PREFIX) || 
						message.getContent().replace("!", "").trim().startsWith(BigSausage.client.getOurUser().mention().replace("!", ""))){
					messages.add(m);
				}else{
					break;
				}
			}
			if(!messages.isEmpty()){
				channel.bulkDelete(messages);
			}
		}else{
			channel.sendMessage("You don't have permission to use that command!");
		}
	}

}
