package main.java.SkillSystem.Events;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import main.java.SkillSystem.Skills;
import main.java.SkillSystem.Handlers.SkillHandlers;
import main.java.SkillSystem.Handlers.Utilities.FarmingUtilities;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class InteractEvent implements Listener
{
	
	private FarmingUtilities FarmingUtils = new FarmingUtilities();
	
	public InteractEvent(Skills Plugin)
	{
		Plugin.getServer().getPluginManager().registerEvents(this, Plugin);
	}
	
	@EventHandler
	public void InteractedEvent(PlayerInteractEvent Event)
	{
		if (Event.isCancelled())
		{
			return;
		}
		
		if (Event.getAction() == Action.RIGHT_CLICK_BLOCK && FarmingUtils.isFarmingBlock(Event.getClickedBlock().getType()) && FarmingUtils.isUsingBoneMeal(Event.getPlayer()))
		{
			if (FarmingUtils.canInstaGrow(Event.getPlayer().getName(), Event.getClickedBlock()))
			{
				Event.getClickedBlock().setData(FarmingUtils.getGrowthData(Event.getClickedBlock().getType()));
				Event.getPlayer().setItemInHand(FarmingUtils.RemoveFromStack(Event.getPlayer().getItemInHand(), 1));
				Skills.getPlayerHandler(Event.getPlayer().getName()).getSkill("Farming").addExp(1 + new Random().nextInt(5));
				SkillHandlers.HandleFarming(Event.getPlayer().getName(),1 + new Random().nextInt(5));
				return;
				//Event.getPlayer().sendMessage(ChatColor.RED + "Your talent as a farmer makes the crop grow instantly!");
			}
			return;
		}
		/*
		if (Event.getAction() == Action.RIGHT_CLICK_AIR || Event.getAction() == Action.RIGHT_CLICK_BLOCK)
		{
			if (Event.getPlayer().getItemInHand() != null && Event.getPlayer().getItemInHand().getType() == Material.MILK_BUCKET)
			{
				boolean consumed = false;
				ItemStack Item = Event.getPlayer().getItemInHand();
				if ((Event.getPlayer().getLevel() + 10) < (10 + ((int)Skills.getPlayerHandler(Event.getPlayer().getName()).getSkill("Magic").getLevel() * 5)))
				{
					Event.getPlayer().setLevel(Event.getPlayer().getLevel() + 10);
					consumed = true;
				}
				else
				{
					Event.getPlayer().setLevel(10 + ((int)Skills.getPlayerHandler(Event.getPlayer().getName()).getSkill("Magic").getLevel() * 5));
					consumed = true;
				}
				if (consumed == true)
				{
					Event.getPlayer().setItemInHand(FarmingUtils.RemoveFromStack(Event.getPlayer().getItemInHand(), 1));
					if (Event.getPlayer().getInventory().firstEmpty() != -1)
					{
						Event.getPlayer().getInventory().addItem(new ItemStack(Material.BUCKET));
					}
					else
					{
						Event.getPlayer().getWorld().dropItemNaturally(Event.getPlayer().getLocation(), new ItemStack(Material.BUCKET));
					}
					Event.getPlayer().updateInventory();
				}
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
			*/
		}
}
