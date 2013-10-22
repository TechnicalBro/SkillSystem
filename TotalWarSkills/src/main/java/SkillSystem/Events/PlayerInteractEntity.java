package main.java.SkillSystem.Events;

import java.util.List;
import java.util.Arrays;
import java.util.Random;

import main.java.SkillSystem.*;
import main.java.SkillSystem.Handlers.SkillHandlers;
import main.java.SkillSystem.Handlers.Utilities.FarmingUtilities;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerInteractEntity implements Listener{
	
	private FarmingUtilities FarmingUtils = new FarmingUtilities();
	
	public PlayerInteractEntity(Skills Plugin)
	{
		Plugin.getServer().getPluginManager().registerEvents(this, Plugin);
	}
	
	@EventHandler
	public void PlayerInteractedEntity(PlayerInteractEntityEvent Event)
	{
		Entity Entity = Event.getRightClicked();
		Player Player = Event.getPlayer();
		if (Entity instanceof LivingEntity && !Entity.hasMetadata("NPC"))
		{
			LivingEntity Mob = (LivingEntity)Entity;
			if (FarmingUtils.isFarmAnimal(Mob))
			{
				switch (Mob.getType())
				{
					case COW:
						if (Player.getItemInHand().getType() == Material.BUCKET)
						{
							if (FarmingUtils.HandleCowMilk(Mob, Player) == true)
							{
								SkillHandlers.HandleFarming(Player.getName(),5);
							}
							Event.setCancelled(true);
						}
						break;
					case SHEEP:
						break;
					case CHICKEN:
						if (Player.getItemInHand().getType() == Material.SHEARS)
						{
							if (FarmingUtils.HandleChicken(Mob, Player) == true)
							{
								SkillHandlers.HandleFarming(Player.getName(),3);
							}
							Event.setCancelled(true);
						}
						break;
					case PIG:
						break;
					default:
						break;
				}
			}
			
		}
	}

}
