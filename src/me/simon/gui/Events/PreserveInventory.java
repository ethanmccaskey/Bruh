package me.simon.gui.Events;

import me.simon.gui.Files.DataManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class PreserveInventory implements Listener {
    private BungeeListener bungeeListener = new BungeeListener();
    public static DataManager hubInventory;

    public PreserveInventory(DataManager hubInventory){
        this.hubInventory = hubInventory;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player p = event.getPlayer();

        p.getInventory().clear();
        p.getInventory().setItem(0, hubInventory.getConfig().getItemStack("Inventory.Player.SelectorStick"));
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        Inventory playerInventory = player.getInventory();

        /*
        check weather any of the hub items are moved
        keep a static inventory for player
            when player changes value of inventory, reset
         */
        if (!(player.getInventory().contains(hubInventory.getConfig().getItemStack("Inventory.Player.SelectorStick")))){ //check whether player's inventory contains lobbySelector
            event.getItemDrop().remove();
            player.getInventory().setItem(0, hubInventory.getConfig().getItemStack("Inventory.Player.SelectorStick"));
        }
    }

    @EventHandler
    public void onSelectorClick(PlayerInteractEvent event){
        Player player = event.getPlayer();
        Action action = event.getAction();
        ItemStack heldItem = player.getInventory().getItemInMainHand();
        Inventory selectorInventory = Bukkit.createInventory(player, 9, ChatColor.BOLD + "Lobby Selector");

        if(action == Action.LEFT_CLICK_AIR || action == Action.RIGHT_CLICK_AIR){
            if(heldItem.equals(hubInventory.getConfig().getItemStack("Inventory.Player.SelectorStick"))){ //player clicks lobbySelector Item
                List<ItemStack> items = (List<ItemStack>) hubInventory.getConfig().getList("Inventory.LobbySelector");
                selectorInventory.setContents(items.toArray(new ItemStack[items.size()]));
                player.openInventory(selectorInventory);
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        try {
            Player player = (Player) event.getWhoClicked();
            ItemStack clickedItem = event.getCurrentItem();
            String clickedItemName = ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName().toLowerCase());

            if(player.getInventory().getItem(0).equals(hubInventory.getConfig().getItemStack("Inventory.Player.SelectorStick")))
                event.setCancelled(true);
            else
                event.setCancelled(false);

            if (clickedItemName.equals("lobby")){
                player.sendMessage("Sending you to Lobby...");
                bungeeListener.connectToServer(player, "lobby");
            } else if (clickedItemName.equals("main")) {
                player.sendMessage("Sending you to Main...");
                bungeeListener.connectToServer(player, "main");
            } else if (clickedItemName.equals("blank")) {
                player.sendMessage("Sending you to Blank...");
            }

        }
        catch (Exception e){
            return;
        }
    }
}
