package net.maploop.dnum.listener;

import net.maploop.dnum.Dnum;
import net.maploop.dnum.gui.PlayerMenuUtility;
import net.maploop.dnum.item.CustomItem;
import net.maploop.dnum.item.ItemUtilities;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class ItemManager implements Listener {
    Dnum main;

    public ItemManager(Dnum main) {
        this.main = main;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerUse(org.bukkit.event.player.PlayerInteractEvent event) {
        if (event.getItem() != null) {
            ItemStack item = event.getItem();

            net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
            NBTTagCompound compound = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
            if(!(compound.hasKey("SkyblockItem"))) return;
            if(!(ItemUtilities.getStringFromItem(item, "SkyblockItem").equals("true"))) return;

            if (event.getItem().hasItemMeta()) {
                if (ItemUtilities.isSBItem(event.getPlayer().getInventory().getItemInHand())) {
                    useSBItem(event, event.getPlayer().getInventory().getItemInHand());
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack used = event.getCurrentItem();

        if(used == null) return;
        if(!(used.hasItemMeta())) return;
        if(!(used.getItemMeta().hasDisplayName())) return;
        if(used.getItemMeta().getDisplayName().contains("§aSkyblock GUI")) {
            if(event.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY)) event.setCancelled(true);
            if(event.getAction().equals(InventoryAction.UNKNOWN)  || event.getAction().equals(InventoryAction.HOTBAR_MOVE_AND_READD) || event.getAction().equals(InventoryAction.HOTBAR_SWAP)) event.setCancelled(true);

            event.setCancelled(true);
        }

        net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(used);
        NBTTagCompound compound = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
        if(!(compound.hasKey("SkyblockItem"))) return;
        if(!(ItemUtilities.getStringFromItem(used, "SkyblockItem").equals("true"))) return;

        ItemUtilities.getSBItem(used).clickedInInventoryAction(player, event);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerHit(EntityDamageByEntityEvent event) {
        Player player = (Player) event.getDamager();
        ItemStack used = player.getInventory().getItemInHand();

        if (event.getDamager().getType() != EntityType.PLAYER) return;
        if(used == null) return;
        if(!(used.hasItemMeta())) return;
        if(!(used.getItemMeta().hasDisplayName())) return;
        net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(used);
        NBTTagCompound compound = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
        if(!(compound.hasKey("SkyblockItem"))) return;
        if(!(ItemUtilities.getStringFromItem(used, "SkyblockItem").equals("true"))) return;


        if (used != null) {
            if (used.getType() != Material.AIR) {
                if (ItemUtilities.isSBItem(used))
                    ItemUtilities.getSBItem(used).hitEntityAction(player, event, event.getEntity(), used);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if(player.getItemInHand() == null) return;
        ItemStack item = player.getInventory().getItemInHand();

        if(item == null) return;
        if(!(item.hasItemMeta())) return;
        if(!(item.getItemMeta().hasDisplayName())) return;
        net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
        NBTTagCompound compound = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
        if(!(compound.hasKey("SkyblockItem"))) return;
        if(!(ItemUtilities.getStringFromItem(item, "SkyblockItem").equals("true"))) return;

        if (item == null || item.getType() == Material.AIR) return;
        if (ItemUtilities.isSBItem(item))
            ItemUtilities.getSBItem(item).breakBlockAction(player, event, event.getBlock(), item);
    }

    @EventHandler
    public void onFish(PlayerFishEvent event) {
        Player player = event.getPlayer();
        if(player.getItemInHand() == null) return;
        ItemStack item = player.getInventory().getItemInHand();

        if(item == null) return;
        if(!(item.hasItemMeta())) return;
        if(!(item.getItemMeta().hasDisplayName())) return;
        net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
        NBTTagCompound compound = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
        if(!(compound.hasKey("SkyblockItem"))) return;
        if(!(ItemUtilities.getStringFromItem(item, "SkyblockItem").equals("true"))) return;

        if (item == null || item.getType() == Material.AIR) return;
        if (ItemUtilities.isSBItem(item))
            ItemUtilities.getSBItem(item).playerFishAction(player, event, item);
    }

    @EventHandler
    public void onShoot(EntityShootBowEvent event) {
        if(!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();
        if(player.getItemInHand() == null) return;
        ItemStack item = player.getInventory().getItemInHand();

        if(item == null) return;
        if(!(item.hasItemMeta())) return;
        if(!(item.getItemMeta().hasDisplayName())) return;
        net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
        NBTTagCompound compound = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
        if(!(compound.hasKey("SkyblockItem"))) return;
        if(!(ItemUtilities.getStringFromItem(item, "SkyblockItem").equals("true"))) return;

        if (item == null || item.getType() == Material.AIR) return;
        if (ItemUtilities.isSBItem(item))
            ItemUtilities.getSBItem(item).playerShootAction(player, event, item);
    }

    @EventHandler
    public void onInteractWithEntity(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        if(player.getItemInHand() == null) return;
        ItemStack item = player.getInventory().getItemInHand();

        if(item == null) return;
        if(!(item.hasItemMeta())) return;
        if(!(item.getItemMeta().hasDisplayName())) return;
        net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
        NBTTagCompound compound = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
        if(!(compound.hasKey("SkyblockItem"))) return;
        if(!(ItemUtilities.getStringFromItem(item, "SkyblockItem").equals("true"))) return;

        if (item == null || item.getType() == Material.AIR) return;
        if (ItemUtilities.isSBItem(item))
            ItemUtilities.getSBItem(item).rightClickEntityAction(player, event, item);
    }

/*
    private void undoChanges(Player player, PlayerItemHeldEvent event) {
        SQLGetter getter = new SQLGetter(player, main);
        if(player.getInventory().getItem(event.getPreviousSlot()).getItemMeta().getDisplayName().contains("§6Aspect of the Dragons")) {
            getter.setStrength(getter.getStrength() - 250);
            getter.setCritDamage(getter.getCritDamage() - 150);
        }
    }
 */

/*
    @EventHandler
    public void onSwap(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getPlayer().getInventory().getItem(event.getNewSlot());
        SQLGetter getter = new SQLGetter(player, main);

        if(item == null) {
            undoChanges(player, event);
            return;
        }
        if(!(item.hasItemMeta())) return;
        if(!(item.getItemMeta().hasDisplayName())) return;
        net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
        NBTTagCompound compound = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
        if(!(compound.hasKey("is-SB"))) {
            undoChanges(player, event);
            return;
        }
        if(!(ItemUtilities.getStringFromItem(item, "is-SB").equals("true"))) return;

        if (item == null || item.getType() == Material.AIR) return;
        if (ItemUtilities.isSBItem(item))
            ItemUtilities.getSBItem(item).onSwapAction(player, event, item);
        else {

        }
    }
 */

    private void useSBItem(PlayerInteractEvent event, ItemStack item) {
        Player player = event.getPlayer();
        if(item == null) return;
        if(!(item.hasItemMeta())) return;
        if(!(item.getItemMeta().hasDisplayName())) return;

        net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
        NBTTagCompound compound = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
        if(!(compound.hasKey("SkyblockItem"))) return;
        if(!(ItemUtilities.getStringFromItem(item, "SkyblockItem").equals("true"))) return;

        CustomItem sbitem = ItemUtilities.getSBItem(item);
        if (event.getAction() == Action.LEFT_CLICK_AIR) {
            if (!player.isSneaking()) {
                sbitem.leftClickAirAction(player, item);
            } else {
                sbitem.shiftLeftClickAirAction(player, item);
            }
        } else if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
            if (!player.isSneaking()) {
                sbitem.leftClickBlockAction(player, event, event.getClickedBlock(), item);
            } else {
                sbitem.shiftLeftClickBlockAction(player, event, event.getClickedBlock(), item);
            }
        } else if (event.getAction() == Action.RIGHT_CLICK_AIR) {
            if (!player.isSneaking()) {
                sbitem.rightClickAirAction(player, event, item);
            } else {
                sbitem.shiftRightClickAirAction(player, event, item);
            }
        } else if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (!player.isSneaking()) {
                sbitem.rightClickBlockAction(player, event, event.getClickedBlock(), item);
            } else {
                sbitem.shiftRightClickBlockAction(player, event, event.getClickedBlock(), item);
            }
        }
    }
}
