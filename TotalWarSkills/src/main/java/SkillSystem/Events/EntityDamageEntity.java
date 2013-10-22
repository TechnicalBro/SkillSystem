package main.java.SkillSystem.Events;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import main.java.SkillSystem.Skills;
import main.java.SkillSystem.Handlers.SkillHandlers;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class EntityDamageEntity implements Listener {
	
	private Skills Plugin;
	
	private List<Material> Weapons = Arrays.asList( new Material[] {Material.WOOD_SWORD,Material.WOOD_AXE,Material.STONE_SWORD,Material.STONE_AXE,
			Material.IRON_SWORD,Material.IRON_AXE,Material.GOLD_SWORD,Material.GOLD_AXE,Material.DIAMOND_AXE,Material.DIAMOND_SWORD});
	
	public EntityDamageEntity(Skills Skills)
	{
		this.Plugin = Skills;
	}
	
	private boolean PercentCheck(double Chances)
	{
		if (new Random().nextInt(100) <= Chances)
		{
			return true;
		}
		return false;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void HandleEntityDamage(EntityDamageByEntityEvent Event)
	{
		if (Event.getDamager() instanceof Player && Event.getEntity() instanceof LivingEntity)
		{
			Player Player = (Player)Event.getDamager();
			if (Player.getItemInHand().getType() == Material.AIR || Player.getItemInHand().equals(null))
			{
				Event.setDamage((Event.getDamage() + (SkillHandlers.HandleUnarmedDamage(Player))));
			}
			/*
			else if (Weapons.contains(Player.getItemInHand().getType()))
			{
			
				if (PercentCheck(SkillHandlers.HandleMeleeDamage(Player)) == true)
				{
					int Switch = new Random().nextInt(2);
					LivingEntity Damaged = (LivingEntity)Event.getEntity();
					if (Switch == 1)
					{
						Damaged.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,20 + new Random().nextInt(90),1));
					}
					else
					{
						Damaged.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 20 + new Random().nextInt(90),1));
					}
					Player.playSound(Player.getLocation(), Sound.ANVIL_LAND, 1.0f, 1.0f);
				
			}
			*/
		}
		
	    if ((Event.getDamager() instanceof Arrow))
	    {
	        Arrow a = (Arrow)Event.getDamager();
	        if (((a.getShooter() instanceof Player)) && ((Event.getEntity() instanceof LivingEntity)))
	        {
	        	LivingEntity le = (LivingEntity)Event.getEntity();
	        	Player player = (Player)a.getShooter();
	        	Location loc = le.getEyeLocation();
		        World world = loc.getWorld();
		        Effect e = Effect.getById(2001);
		        if ((a.getLocation().getY() >= le.getEyeLocation().getY()) && (a.getVelocity().lengthSquared() > 7.0D))
		        {
		        	world.playEffect(loc, e, Material.REDSTONE_BLOCK.getId());
		        	Event.setDamage(Event.getDamage() + SkillHandlers.HandleArcheryHeadshotDamage(player));
		        	player.playSound(player.getLocation(), Sound.ARROW_HIT, 1.0f, 1.0f);
		        }
	        }
	    }
	}
}
