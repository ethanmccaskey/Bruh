package me.simon.gui.Commands;

import me.simon.gui.Files.DataManager;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import java.util.List;

public class GUICommands implements CommandExecutor {
    public static DataManager hubInventory;

    public GUICommands(DataManager hubInventory){
        this.hubInventory = hubInventory;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        World world = player.getWorld();

        switch(label){
            case "hub":
                player.getInventory().setItem(0, hubInventory.getConfig().getItemStack("Inventory.Player.SelectorStick"));
                break;
        }

        return true;
    }
}
