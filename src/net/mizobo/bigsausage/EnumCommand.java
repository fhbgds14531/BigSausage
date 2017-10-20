package net.mizobo.bigsausage;

import java.util.ArrayList;
import java.util.List;

public enum EnumCommand {
	enable(false, "Enables my functionality, stupid. Usage: !bs enable"), 
	disable(false, "Disables  my functionality, stupid. Usage: !bs disable"), 
	clips(false, "Lists the names of the available audio clips. Usage: !bs clips"), 
	status(false, "Checks the status of the bot in this server. Usage: !bs status"), 
	target(false, "Targets a user with a coordinated audio strike in the voice channel they're in. Usage: !bs target \\@<username> <list of clips>"), 
	trust(false, "Add a user to the trusted users list. Usage: [!bs trust \\@<username>] to add a user or [!bs trust] to list trusted users."), 
	remove_trust(false, "Remove a user from the trusted users list. Usage: !bs removetrust \\@<username>"), 
	setup_defaults(false, "Reset all trigger lists to their default settings, can only be used by an Admin. Usage: !bs setupdefaults"), 
	add_trigger(false, "Add a trigger word for an audio clip or image. Usage: !bs add-trigger <clip or image name> <trigger word>"), 
	remove_trigger(false, "Remove a trigger word for an audio clip or image. Usage: !bs remove-trigger <clip or image name> <trigger word>"), 
	remove_all_triggers(false, "Remove all trigger words for an audio clip or image, Can only be used by an Admin. Usage: !bs remove-all-triggers <clip or image name>"), 
	reset_triggers(false, "Reset all trigger words for an audio clip or image back to their default state, Can only be used by an Admin. Usage: !bs reset <clip or image name>"), 
	list_triggers(false, "List all trigger words for an audio clip or image. Usage: !bs list-triggers <clip or image name>"), 
	version(false, "List the current version of BigSausage. Usage: !bs version"),
	tts(false, "Repeat a random tts string from the tts list. Usage: !bs tts"),
	remove_tts(false, "Remove a tts string from the tts list. Usage: !bs remove-tts <exact tts string>"),
	add_tts(false, "Add a tts string to the tts list. Usage: !bs add-tts <exact tts string>"),
	images(false, "List all the image names BigSausage can link. Usage: !bs images"),
	changelog(false, "List the notable changes since the last version. Usage: !bs changelog"),
	get_fucked(false, "Move a user to a specific voice channel, then play the specified clip, can only be used by an Admin. Usage: !bs get-fucked \\@<username> <voice-channel-name> <clips to play>"),
	max_clips(false, "List or edit the maximum number of audio clips that BigSausage will queue per message. Usage: \"!bs max-clips\" (to list the current setting) or \"!bs max-clips <non-zero, positive integer>\" to set a new maximum."),
	thomas(false, "Hold on, what did you say?"),
	tts_info(false, "List information regarding the tts file for this server, Trusted users only. Usage: \"!bs tts-info\""),
	roll(false, "Roll some dice. Usage: \"!bs roll XdY\" where x is the number of dice and y is the number of sides per die."),
	commands(false, "Show all commands. Usage: \"!bs commands\""),
	help(false, "For specific help use \"!bs help <command name>\". for a list of commands, use \"!bs commands\""), 
	save_all(true, ""), 
	update(true, ""),
	force_update(true, ""),
	update_to(true, ""),
	play_clip(true, ""),
	shutdown(true, "");

	private boolean isSecret;
	String helpText;

	private EnumCommand(boolean secret, String helpText) {
		this.isSecret = secret;
		this.helpText = helpText;
	}

	public boolean getIsSecret() {
		return this.isSecret;
	}
	
	public void setHelpString(String s){
		this.helpText = s;
	}

	public static EnumCommand getFromString(String s) {
		for(EnumCommand c : EnumCommand.values()){
			if(c.toString().contentEquals(s)) return c;
		}
		return help;
	}
	
	public String getHelpText(){
		return this.helpText;
	}

	@Override
	public String toString() {
		return this.name().replace("_", "-");
	}
	
	public static String getCommaSeparatedList() {
		String out = "";
		for (EnumCommand com : EnumCommand.values()) {
			if(!com.isSecret){
				out += com.toString() + ", ";
			}
		}
		return out.substring(0, out.lastIndexOf(", "));
	}
	
	public static String getCommaSeparatedFormattedList() {
		List<String> names = new ArrayList<String>();
		for(EnumCommand command : EnumCommand.values()){
			names.add(command.toString());
		}
		String[] nameArray = new String[names.size()];
		names.toArray(nameArray);
		int maxLength = Util.getMaxLength(nameArray);
		String out = "";
		int index = 0;
		for (EnumCommand com : EnumCommand.values()) {
			if(!com.isSecret){
				out += com.toString() + " ";
				for(int i = com.toString().length(); i < maxLength; i++){
					out += " ";
				}
				index++;
				if(index == 4){
					out+= "\n";
					index = 0;
				}
			}
		}
		return out.trim();
	}
}
