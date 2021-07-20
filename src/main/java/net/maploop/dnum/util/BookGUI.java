package net.maploop.dnum.util;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.SneakyThrows;
import net.md_5.bungee.api.chat.*;
import net.md_5.bungee.chat.ComponentSerializer;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftMetaBook;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.List;

public class BookGUI {
    public static void openBook(ItemStack book, Player p) {
        int slot = p.getInventory().getHeldItemSlot();
        ItemStack old = p.getInventory().getItem(slot);
        p.getInventory().setItemInMainHand(book);

        ByteBuf buf = Unpooled.buffer(256);
        buf.setByte(0, slot);
        buf.writerIndex(1);
        PacketPlayOutOpenBook packet = new PacketPlayOutOpenBook(EnumHand.MAIN_HAND);
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
        System.out.println("sent");
        p.getInventory().setItem(slot, old);
    }

    @SneakyThrows
    public static ItemStack makeBook(String title, String author, String[] text) {
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta meta = (BookMeta) book.getItemMeta();

        for (String page : text) {
            meta.addPage(page);
        }

        meta.setTitle("Example");
        meta.setAuthor("Server");
        book.setItemMeta(meta);

        return book;
    }

    @SuppressWarnings("unchecked")
    @SneakyThrows
    public static ItemStack getExampleBook() {
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta meta = (BookMeta) book.getItemMeta();

        meta.addPage("Page 1 \nmade by Swofty", "Page 2", "§cPage 3");

        meta.setTitle("Example");
        meta.setAuthor("Server");
        book.setItemMeta(meta);

        return book;
    }

}
