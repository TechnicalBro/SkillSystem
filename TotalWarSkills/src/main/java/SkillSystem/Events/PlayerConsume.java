package main.java.SkillSystem.Events;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import main.java.SkillSystem.Skills;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PlayerConsume implements Listener
{
	
	public PlayerConsume(Skills Plugin)
	{
		Plugin.getServer().getPluginManager().registerEvents(this, Plugin);
	}
	
	@EventHandler
	public void ItemConsumed(PlayerItemConsumeEvent Event)
	{
		ItemStack Item = Event.getItem();
		if (Item.getType() == Material.MILK_BUCKET)
		{
			if (Item.hasItemMeta())
			{
				ItemMeta Meta = Item.getItemMeta();
				if (Meta.hasLore())
				{
					for(String S : Meta.getLore())
					{
						if (S.toLowerCase().contains("best before"))
						{
							try
							{
								Date Expiration = new SimpleDateFormat("MM - dd").parse(StringUtils.substringAfter(S, ": "));
								Calendar Today = Calendar.getInstance();
								if (Today.get(Calendar.MONTH) >= Expiration.getMonth() && Today.get(Calendar.DATE) > Expiration.getDate())
								{
									Event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION,200,4));
									//Event.getPlayer().sendMessage("Maybe you shouldn't have drank that expired milk?");
								}
							}
							catch (ParseException e)
							{
								e.printStackTrace();
							}
							break;
						}
					}
				}
			}
		}
		
		if (Item.getType() == Material.MILK_BUCKET)
		{
			if ((Event.getPlayer().getLevel() + 10) < (10 + ((int)Skills.getPlayerHandler(Event.getPlayer().getName()).getSkill("Magic").getLevel() * 5)))
			{
				Event.getPlayer().setLevel(Event.getPlayer().getLevel() + 10);
			}
			else
			{
				Event.getPlayer().setLevel(10 + ((int)Skills.getPlayerHandler(Event.getPlayer().getName()).getSkill("Magic").getLevel() * 5));
			}
		}
		else if (Item.getType() == Material.COOKIE)
		{
			if ((Event.getPlayer().getLevel() + 5) < (10 + ((int)Skills.getPlayerHandler(Event.getPlayer().getName()).getSkill("Magic").getLevel() * 5)))
			{
				Event.getPlayer().setLevel(Event.getPlayer().getLevel() + 5);
			}
			else
			{
				Event.getPlayer().setLevel(10 + ((int)Skills.getPlayerHandler(Event.getPlayer().getName()).getSkill("Magic").getLevel() * 5));
			}
		}
		else if (Item.getType() == Material.POTION)
		{
			if (Item.getDurability() == 0)
			{
				if ((Event.getPlayer().getLevel() + 10) < (10 + ((int)Skills.getPlayerHandler(Event.getPlayer().getName()).getSkill("Magic").getLevel() * 5)))
				{
					Event.getPlayer().setLevel(Event.getPlayer().getLevel() + 10);
				}
				else
				{
					Event.getPlayer().setLevel(10 + ((int)Skills.getPlayerHandler(Event.getPlayer().getName()).getSkill("Magic").getLevel() * 5));
				}
			}
		}
	}

}
