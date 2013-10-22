package main.java.SkillSystem.Events;

import main.java.SkillSystem.Skills;
import main.java.SkillSystem.Handlers.PlayerHandler;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;

public class PlayerJoin implements Listener {
	
	public PlayerJoin(Skills Plugin)
	{
		Plugin.getServer().getPluginManager().registerEvents(this, Plugin);
	}
	
	@EventHandler
	public void PlayerJoins(PlayerLoginEvent Event)
	{
		if (!Skills.hasHandler(Event.getPlayer().getName()))
		{
			Skills.addPlayerHandler(Event.getPlayer().getName(), new PlayerHandler(Event.getPlayer().getName()));
		}
	}

}
