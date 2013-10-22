package main.java.SkillSystem.Events;

import java.util.Random;

import main.java.SkillSystem.Skills;
import main.java.SkillSystem.Handlers.SkillHandlers;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerShearEntityEvent;

public class PlayerShear implements Listener
{

	public PlayerShear(Skills Plugin)
	{
		Plugin.getServer().getPluginManager().registerEvents(this, Plugin);
	}
	
	@EventHandler
	public void Sheared(PlayerShearEntityEvent Event)
	{
		SkillHandlers.HandleFarming(Event.getPlayer().getName(),1 + new Random().nextInt(3));
	}
	
}
