package net.maploop.dnum.util.hologram;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.gmail.filoghost.holographicdisplays.api.VisibilityManager;
import net.maploop.dnum.Dnum;
import net.maploop.dnum.util.Util;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.*;

public final class DHologram {
	private final String text;
	private final Location location;

	public DHologram(String text, Location location) {
		this.text = text;
		this.location = location;
	}

	public void send(Player player) {
		Hologram hologram = HologramsAPI.createHologram(Dnum.getInstance(), location);
		VisibilityManager visibilityManager = hologram.getVisibilityManager();

		hologram.appendTextLine(Util.colorize(text));

		visibilityManager.showTo(player);
		visibilityManager.setVisibleByDefault(false);
	}
}
