package net.mizobogames.fhbgds.commands;

import net.mizobogames.fhbgds.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import sx.blah.discord.handle.obj.*;
import sx.blah.discord.util.MissingPermissionsException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CommandFuck extends Command {

    public static final String fuckChannelName = "BigSausage Channel";

    public CommandFuck(String commandString, String helpString) {
        super(commandString, helpString);
    }

    @Override
    public void execute(IChannel channel, IUser commandAuthor, IGuild guild, List<String> command, IMessage message) {
        if(Util.hasPermission(PermissionLevels.PERMISSION_LEVEL_ADMIN, guild, commandAuthor)){
            boolean fuckChannelExists = false;
            for(IVoiceChannel vCh : guild.getVoiceChannels()){
                if(vCh.getName().contentEquals(fuckChannelName)) fuckChannelExists = true;
            }
            if(!fuckChannelExists){
                try {
                    guild.createVoiceChannel(fuckChannelName);
                }catch (MissingPermissionsException e){
                    e.printStackTrace();
                    channel.sendMessage("I don't have the required permissions to execute this command! Code:`CF1`");
                    return;
                }
                for(IVoiceChannel vCh : guild.getVoiceChannels()){
                    if(vCh.getName().contentEquals(fuckChannelName)) fuckChannelExists = true;
                }
                if(!fuckChannelExists){
                    channel.sendMessage("Something went wrong! Send a bugreport if this continues to happen!");
                    try {
                        Util.sendAutomaticBugReport("Creating fuckChannel (probably a permissions issue)", CommandFuck.class, 39, CommandFuck.class.getMethod("execute", IChannel.class, IUser.class, IGuild.class, List.class, IMessage.class), guild, commandAuthor);
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
            }
            if(fuckChannelExists){
                ArrayList<String> words = new ArrayList<>();
                if(command.size() < 4){
                    channel.sendMessage(this.getHelpString());
                    return;
                }
                words.add(command.get(2));
                words.add(command.get(3));
                long targetID = Long.valueOf(command.get(2).replace("<", "").replace(">", "").replace("@", "").replace("!", ""));
                IUser target = guild.getUserByID(targetID);
                boolean targetIsInVoice = false;
                IVoiceChannel origin = null;
                for(IVoiceChannel vCh : guild.getVoiceChannels()){
                    if(vCh.getConnectedUsers().contains(target)){
                        targetIsInVoice = true;
                        origin = vCh;
                    }
                }
                if(!targetIsInVoice){
                    channel.sendMessage("I can't move someone who isn't connected... sorry!");
                    return;
                }else{
                    IVoiceChannel sausageChannel = guild.getVoiceChannelsByName(fuckChannelName).get(0);
                    if (!(boolean) SettingsManager.getSettingForGuild(guild, "audio-enabled")) {
                        channel.sendMessage("Audio clips are currently disabled.");
                        return;
                    }
                    File audioFileIndex = Util.getAudioIndexFile(guild);
                    if (audioFileIndex.exists()) {
                        JSONObject audioIndex = Util.getJsonObjectFromFile(guild, audioFileIndex);
                        JSONArray ja = (JSONArray) audioIndex.get("index");
                        if (ja == null) ja = new JSONArray();
                        List<String> indexStrings = new ArrayList<>();
                        ja.forEach(s -> indexStrings.add(String.valueOf(s)));
                        for (String clipName : indexStrings) {
                            JSONArray triggers = (JSONArray) audioIndex.get(clipName);
                            List<String> triggerStrings = new ArrayList<String>();
                            triggers.forEach(object -> triggerStrings.add(String.valueOf(object)));
                            for (String trigger : triggerStrings) {
                                if (command.get(3).toLowerCase().contains(trigger)) {
                                    String filename = (String) audioIndex.get(clipName + "_name");
                                    File file = new File("guilds/" + guild.getStringID() + "/files/" + filename);
                                    target.moveToVoiceChannel(sausageChannel);
                                    BigSausage.queueFile(file, guild, sausageChannel, target, true);
                                    BigSausage.getBot().queueFuckReturn(guild.getLongID(), target, origin);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
