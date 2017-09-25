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
	add_trigger(false, "Add a trigger word for an audio clip. Usage: !bs add-trigger <clip name> <trigger word>"), 
	remove_trigger(false, "Remove a trigger word for an audio clip. Usage: !bs remove-trigger <clip name> <trigger word>"), 
	list_triggers(false, "List all trigger words for an audio clip. Usage: !bs list-triggers <clip name>"), 
	version(false, "List the current version of BigSausage. Usage: !bs version"),
	help(false, "What"), 
	save_all(true, ""), 
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
