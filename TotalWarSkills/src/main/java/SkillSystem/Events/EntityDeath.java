package main.java.SkillSystem.Events;

import java.util.ArrayList;
import java.util.Random;

import main.java.SkillSystem.Skills;
import main.java.SkillSystem.Handlers.EXPChart;
import main.java.SkillSystem.Handlers.SkillHandlers;
import main.java.SkillSystem.Handlers.Fireworks.FireworkSettings;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;

import com.caved_in.TotalWar;

public class EntityDeath implements Listener {
	
	private Skills Plugin;
	
	private Material[] Materials = new Material[] {Material.WOOD_SWORD,Material.WOOD_AXE,Material.STONE_SWORD,Material.STONE_AXE,Material.IRON_SWORD,Material.IRON_AXE,Material.GOLD_SWORD,Material.GOLD_AXE,Material.DIAMOND_AXE,Material.DIAMOND_SWORD };
	private ArrayList<Material> Matter = new ArrayList<Material>();
	private FireworkSettings Fireworks = new FireworkSettings();
	public EntityDeath(Skills Plugin)
	{
		Plugin.getServer().getPluginManager().registerEvents(this, Plugin);
		for(Material M : Materials)
		{
			Matter.add(M);
		}
	}
	
	@EventHandler
	public void EntityDeathEvent(EntityDeathEvent Event)
	{
		if (Event.getEntity().getKiller() instanceof Player)
		{
			Damageable Entity = (Damageable)Event.getEntity();
			Player Player = (Player)Event.getEntity().getKiller();
			switch(Event.getEntity().getLastDamageCause().getCause())
			{
				case ENTITY_ATTACK:
					if (Player.getItemInHand().getType() == Material.AIR || Player.getItemInHand().equals(null))
					{
						SkillHandlers.HandleUnarmed(Player.getName(),Entity.getMaxHealth());
					}
					else if (Matter.contains(Player.getItemInHand().getType()))
					{
						SkillHandlers.HandleMelee(Player.getName(),Entity.getMaxHealth());
					}
					break;
				case PROJECTILE:
					SkillHandlers.HandleArchery(Player.getName(),Entity.getMaxHealth());
					//Event.getEntity().get
					break;
				default:
					break;
			}
		}
	}
}
