package net.mizobo.bigsausage;

public enum EnumCommand {
	enable(false, "Enables my functionality, stupid. Usage: !bs enable"), 
	disable(false, "Disables  my functionality, stupid. Usage: !bs disable"), 
	clips(false, "Lists the names of the available audio clips. Usage: !bs clips"), 
	status(false, "Checks the status of the bot in this server. Usage: !bs status"), 
	target(false, "Targets a user with a coordinated audio strike in the voice channel they're in. Usage: !bs target \\@<username> <list of clips>"), 
	trust(false, "Add a user to the trusted users list. Usage: [!bs trust \\@<username>] to add a user or [!bs trust] to list trusted users."), 
	remove_trust(false, "Remove a user from the trusted users list. Usage: !bs removetrust \\@<username>"), 
	setup_defaults(false, "Reset all trigger lists to their default settings, can only be used by an admin. Usage: !bs setupdefaults"), 
	add_trigger(false, "Add a trigger word for an audio clip or image. Usage: !bs add-trigger <clip or image name> <trigger word>"), 
	remove_trigger(false, "Remove a trigger word for an audio clip or image. Usage: !bs remove-trigger <clip or image name> <trigger word>"), 
	list_triggers(false, "List all trigger words for an audio clip or image. Usage: !bs list-triggers <clip or image name>"), 
	version(false, "List the current version of BigSausage. Usage: !bs version"),
	tts(false, "Repeat a random tts string from the tts list. Usage: !bs tts"),
	remove_tts(false, "Remove a tts string from the tts list. Usage: !bs remove-tts <exact tts string>"),
	add_tts(false, "Add a tts string to the tts list. Usage: !bs add-tts <exact tts string>"),
	images(false, "List all the image names BigSausage can link. Usage: !bs images"),
	help(false, "What"), 
	save_all(true, ""), 
	update(true, ""),
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

	public static EnumCommand getFromString(String s) {
		switch (s) {
			case "enable":
				return enable;
			case "disable":
				return disable;
			case "clips":
				return clips;
			case "status":
				return status;
			case "target":
				return target;
			case "trust":
				return trust;
			case "removetrust":
				return remove_trust;
			case "setup-defaults":
				return setup_defaults;
			case "add-trigger":
				return add_trigger;
			case "remove-trigger":
				return remove_trigger;
			case "list-triggers":
				return list_triggers;
			case "help":
				return help;
			case "save-all":
				return save_all;
			case "shutdown":
				return shutdown;
			case "version":
				return version;
			case "tts":
				return tts;
			case "remove-tts":
				return remove_tts;
			case "add-tts":
				return add_tts;
			case "images":
				return images;
			default:
				return help;
		}
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
			out += com.toString() + ", ";
		}
		return out.substring(0, out.lastIndexOf(", "));
	}
}
