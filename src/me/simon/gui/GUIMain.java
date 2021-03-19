package me.simon.gui;

import me.simon.gui.Commands.GUICommands;
import me.simon.gui.Events.BungeeListener;
import me.simon.gui.Events.PreserveInventory;
import me.simon.gui.Files.DataManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class GUIMain extends JavaPlugin {
    private static GUIMain instance;
    public DataManager HubInventory;

    @Override
    public void onEnable(){
        setInstance(this);
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "GUI Plugin works");

        Bukkit.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        Bukkit.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new BungeeListener());

        this.HubInventory = new DataManager(this);
        HubInventory.saveDefaultConfig();

        //Bukkit.getServer().getPluginManager().registerEvents(new PreserveInventory(HubInventory), this);
        this.getCommand("hub").setExecutor(new GUICommands(HubInventory));
    }

    public static GUIMain getInstance(){
        return instance;
    }

    private static void setInstance(GUIMain instance){
        GUIMain.instance = instance;
    }
}
