package me.simon.gui.Events;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import me.simon.gui.GUIMain;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class BungeeListener implements PluginMessageListener {
    private GUIMain plugin = GUIMain.getInstance();

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {

        if(!channel.equals("BungeeCord")) return;

        ByteArrayDataInput input = ByteStreams.newDataInput(message);
        String subchannel = input.readUTF();

        if(subchannel.equals("Connect")) return;
    }

    public void connectToServer(Player player, String server){
        ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeUTF("Connect");
        output.writeUTF(server);

        player.sendPluginMessage(plugin, "BungeeCord", output.toByteArray());
    }
}
