package net.maploop.dnum.v_1_16_R3_npc;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.libs.jline.internal.Log;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

@UtilityClass
public class NPCUtil {
	public String[] getSkin(String name) {
		String first = "", second = "";

		String uuid = Bukkit.getOfflinePlayer(name).getUniqueId().toString();

		try {
			URL url = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid + "?unsigned=false");
			URLConnection urc = url.openConnection();
			urc.setUseCaches(false);
			urc.setDefaultUseCaches(false);
			urc.addRequestProperty("User-Agent", "Mozilla/5.0");
			urc.addRequestProperty("Cache-Control", "no-cache, no-store, must-revalidate");
			urc.addRequestProperty("Pragma", "no-cache");

			String json = new Scanner(urc.getInputStream(), "UTF-8").useDelimiter("\\A").next();
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(json);
			JSONArray array = (JSONArray) ((JSONObject)obj).get("properties");

			for (Object o : array) {
				try {
					JSONObject property = (JSONObject) o;
					String n = (String) property.get("name");
					String value = (String) property.get("value");
					String signature = property.containsKey("signature") ? (String) property.get("signature") : null;

					first = value;
					second = signature;

				} catch (Exception e) {
					Log.warn("Failed to fetch skin");
				}
			}

		} catch (Exception ex) {
			Log.error("Failed to fetch skin");
		}

		return new String[] {first, second};
	}
}
