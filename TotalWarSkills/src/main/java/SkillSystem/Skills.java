package main.java.SkillSystem;

import java.util.HashMap;

import main.java.SkillSystem.Events.*;
import main.java.SkillSystem.Events.EntityDeath;
import main.java.SkillSystem.Handlers.EXPChart;
import main.java.SkillSystem.Handlers.PlayerHandler;
import main.java.SkillSystem.Handlers.SkillHandlers;
import main.java.SkillSystem.Handlers.SkillsMenu;

import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

public class Skills extends JavaPlugin {
	
	public static EXPChart ExpChart = new EXPChart();
	public static SkillHandlers SkillsHandler;
	public static SkillsMenu SkillsMenu = new SkillsMenu();
	
	private static HashMap<String,PlayerHandler> PlayerHandlers = new HashMap<String,PlayerHandler>();
	
	@Override
	public void onEnable() {
		new PlayerJoin(this);
		new EntityDeath(this);
		new CraftItem(this);
		new PlayerInteractEntity(this);
		new BlockBreak(this);
		new InteractEvent(this);
		new PlayerConsume(this);
		new PlayerShear(this);
	}
	
	@Override
	public void onDisable() {
		HandlerList.unregisterAll(this);
	}
	
	public static PlayerHandler getPlayerHandler(String Playername)
	{
		if (PlayerHandlers.containsKey(Playername))
		{
			return PlayerHandlers.get(Playername);
		}
		return null;
	}
	
	public static boolean hasHandler(String Name)
	{
		return PlayerHandlers.containsKey(Name);
	}
	
	public static void addPlayerHandler(String Name, PlayerHandler PlayerHandler)
	{
		PlayerHandlers.put(Name, PlayerHandler);
	}
	
	public static SkillHandlers getSkillsHandler()
	{
		return SkillsHandler;
	}
}
