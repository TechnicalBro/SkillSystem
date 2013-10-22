package main.java.SkillSystem.Events;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import main.java.SkillSystem.Skills;
import main.java.SkillSystem.Handlers.EXPChart;
import main.java.SkillSystem.Handlers.PlayerHandler;
import main.java.SkillSystem.Handlers.SkillHandlers;
import main.java.SkillSystem.Handlers.Fireworks.FireworkSettings;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.caved_in.*;
import com.caved_in.TotalWarItems.TotalWarItems;
import com.caved_in.TotalWarItems.Handlers.ItemStackHandler.Tier;

public class CraftItem implements Listener {
	
	private Skills Plugin;
	private String[] HpBonus = new String[] {"Increases Max HP by ","!" };
	private List<Material> Weapons = Arrays.asList( new Material[] {Material.WOOD_SWORD,Material.WOOD_AXE,Material.STONE_SWORD,Material.STONE_AXE,Material.IRON_SWORD,Material.IRON_AXE,Material.GOLD_SWORD,Material.GOLD_AXE,Material.DIAMOND_AXE,Material.DIAMOND_SWORD,Material.LEATHER_BOOTS,Material.LEATHER_CHESTPLATE,Material.LEATHER_HELMET,Material.LEATHER_LEGGINGS,Material.IRON_BOOTS,Material.IRON_LEGGINGS,Material.IRON_CHESTPLATE,Material.IRON_HELMET,Material.GOLD_HELMET,Material.GOLD_BOOTS,Material.GOLD_LEGGINGS,Material.GOLD_CHESTPLATE,
			Material.DIAMOND_BOOTS,Material.DIAMOND_LEGGINGS,Material.DIAMOND_CHESTPLATE,Material.DIAMOND_HELMET,Material.WOOD_SPADE,Material.WOOD_HOE,Material.STONE_SPADE,
			Material.STONE_HOE,Material.IRON_HOE,Material.IRON_SPADE,Material.GOLD_HOE,Material.GOLD_SPADE});
	public CraftItem(Skills Skills)
	{
		this.Plugin = Skills;
		Plugin.getServer().getPluginManager().registerEvents(this, Plugin);
	}
	
	public int Calculate_Health(Tier Tier)
	{
		int Minimum = 0;
		int Maximum = 0;
		if (Tier == com.caved_in.TotalWarItems.Handlers.ItemStackHandler.Tier.Common)
		{
			Minimum = 15;
			Maximum = 45;
		}
		else if (Tier == com.caved_in.TotalWarItems.Handlers.ItemStackHandler.Tier.Rare)
		{
			Minimum = 39;
			Maximum = 83;
		}
		else if (Tier == com.caved_in.TotalWarItems.Handlers.ItemStackHandler.Tier.Epic)
		{
			Minimum = 65;
			Maximum = 127;
		}
		
		int MinD = 1 + (new Random().nextInt(Minimum + 1) + new Random().nextInt(Minimum + 1)) / 2;
	    int MaxD = 1 + (new Random().nextInt(Maximum + 1) + new Random().nextInt(Maximum + 1)) / 2;
	    return (int)(Math.round((MinD + MaxD) / 2));
	}
	
	private int getExpValues(Material Material)
	{
		String MaterialName = Material.name().toLowerCase();
		
		if (MaterialName.contains("wood"))
		{
			return 3;
		}
		else if (MaterialName.contains("stone") || MaterialName.contains("leather"))
		{
			return 9;
		}
		else if (MaterialName.contains("iron"))
		{
			return 12;
		}
		else if (MaterialName.contains("gold"))
		{
			return 9;
		}
		else if (MaterialName.contains("diamond"))
		{
			return 25;
		}
		return 2;
	}
	
	private boolean PercentCheck(double Chances)
	{
		if (new Random().nextInt(100) <= Chances)
		{
			return true;
		}
		return false;
	}
	
	@EventHandler
	public void CraftedItemEvent(CraftItemEvent Event)
	{
		double Rare_Increase = 0;
		try
		{
			if (this.Weapons.contains(Event.getInventory().getResult().getType()))
			{
				Player Player = ((Player)Event.getWhoClicked());
				if (Event.isShiftClick() == true)
				{
					Player.sendMessage(ChatColor.RED + "You can't shiftclick-craft these items, sorry!");
					Event.setCancelled(true);
					return;
				}
				
				if (Event.getCursor().getType() != Material.AIR)
				{
					return;
				}
				
				if (TotalWar.permission.has(Player, TotalWar.Donator_Permission) == true)
				{
					Rare_Increase += TotalWar.Plugin_Config.getDonatorRareCraftIncrease();
				}
				
				Rare_Increase += (Skills.getPlayerHandler(Player.getName()).getSkill("Crafting").getLevel() / 2) + (new Random().nextDouble());
				
				ItemStack Result = Event.getInventory().getResult();
				ItemMeta ResultMeta = (ItemMeta)Result.getItemMeta();
				ArrayList<String> Lore = new ArrayList<String>();
				int[] Damages;
				switch (Event.getInventory().getResult().getType())
				{
					case WOOD_SWORD:
						Damages = TotalWarItems.ItemHandler.Calculate_Damage(TotalWarItems.ItemHandler.getItemDamage("Blade", Tier.Common));
						Lore.add(ChatColor.RED + "Deals " + Damages[0] + " to " + Damages[1] + " damage!");
						break;
					case STONE_SWORD:
						if(PercentCheck(5 + Rare_Increase) == true)
						{
							Damages = TotalWarItems.ItemHandler.Calculate_Damage(TotalWarItems.ItemHandler.getItemDamage("Blade", Tier.Rare));
							Lore.add(ChatColor.RED + "Deals " + Damages[0] + " to " + Damages[1] + " damage!");
						}
						else
						{
							Damages = TotalWarItems.ItemHandler.Calculate_Damage(TotalWarItems.ItemHandler.getItemDamage("Blade", Tier.Common));
							Lore.add(ChatColor.RED + "Deals " + Damages[0] + " to " + Damages[1] + " damage!");
						}
						break;
					case IRON_SWORD:
						if (PercentCheck(2 + Rare_Increase) == true)
						{
							Damages = TotalWarItems.ItemHandler.Calculate_Damage(TotalWarItems.ItemHandler.getItemDamage("Blade", Tier.Epic));
							Lore.add(ChatColor.RED + "Deals " + Damages[0] + " to " + Damages[1] + " damage!");
						}
						else if(PercentCheck(35 + Rare_Increase) == true)
						{
							Damages = TotalWarItems.ItemHandler.Calculate_Damage(TotalWarItems.ItemHandler.getItemDamage("Blade", Tier.Rare));
							Lore.add(ChatColor.RED + "Deals " + Damages[0] + " to " + Damages[1] + " damage!");
						}
						else
						{
							Damages = TotalWarItems.ItemHandler.Calculate_Damage(TotalWarItems.ItemHandler.getItemDamage("Blade", Tier.Common));
							Lore.add(ChatColor.RED + "Deals " + Damages[0] + " to " + Damages[1] + " damage!");
						}
						break;
					case GOLD_SWORD:
						if(PercentCheck(19 + Rare_Increase) == true)
						{
							Damages = TotalWarItems.ItemHandler.Calculate_Damage(TotalWarItems.ItemHandler.getItemDamage("Blade", Tier.Rare));
							Lore.add(ChatColor.RED + "Deals " + Damages[0] + " to " + Damages[1] + " damage!");
						}
						else
						{
							Damages = TotalWarItems.ItemHandler.Calculate_Damage(TotalWarItems.ItemHandler.getItemDamage("Blade", Tier.Common));
							Lore.add(ChatColor.RED + "Deals " + Damages[0] + " to " + Damages[1] + " damage!");
						}
						break;
					case DIAMOND_SWORD:
						if(PercentCheck(40 + Rare_Increase) == true)
						{
							Damages = TotalWarItems.ItemHandler.Calculate_Damage(TotalWarItems.ItemHandler.getItemDamage("Blade", Tier.Epic));
							Lore.add(ChatColor.RED + "Deals " + Damages[0] + " to " + Damages[1] + " damage!");
						}
						else
						{
							Damages = TotalWarItems.ItemHandler.Calculate_Damage(TotalWarItems.ItemHandler.getItemDamage("Blade", Tier.Rare));
							Lore.add(ChatColor.RED + "Deals " + Damages[0] + " to " + Damages[1] + " damage!");
						}
						break;
					case LEATHER_LEGGINGS:
						Lore.add(ChatColor.RED + HpBonus[0] + this.Calculate_Health(Tier.Common) + HpBonus[1]);
						break;
					case LEATHER_HELMET:
						Lore.add(ChatColor.RED + HpBonus[0] + this.Calculate_Health(Tier.Common) + HpBonus[1]);
						break;
					case LEATHER_CHESTPLATE:
						Lore.add(ChatColor.RED + HpBonus[0] + this.Calculate_Health(Tier.Common) + HpBonus[1]);
						break;
					case LEATHER_BOOTS:
						Lore.add(ChatColor.RED + HpBonus[0] + this.Calculate_Health(Tier.Common) + HpBonus[1]);
						break;
					case IRON_LEGGINGS:
						if (PercentCheck(10 + Rare_Increase) == true)
						{
							Lore.add(ChatColor.RED + HpBonus[0] + this.Calculate_Health(Tier.Rare) + HpBonus[1]);
						}
						else
						{
							Lore.add(ChatColor.RED + HpBonus[0] + this.Calculate_Health(Tier.Common) + HpBonus[1]);
						}
						break;
					case IRON_HELMET:
						if (PercentCheck(10 + Rare_Increase) == true)
						{
							Lore.add(ChatColor.RED + HpBonus[0] + this.Calculate_Health(Tier.Rare) + HpBonus[1]);
						}
						else
						{
							Lore.add(ChatColor.RED + HpBonus[0] + this.Calculate_Health(Tier.Common) + HpBonus[1]);
						}
						break;
					case IRON_CHESTPLATE:
						if (PercentCheck(10 + Rare_Increase) == true)
						{
							Lore.add(ChatColor.RED + HpBonus[0] + this.Calculate_Health(Tier.Rare) + HpBonus[1]);
						}
						else
						{
							Lore.add(ChatColor.RED + HpBonus[0] + this.Calculate_Health(Tier.Common) + HpBonus[1]);
						}
						break;
					case IRON_BOOTS:
						if (PercentCheck(10 + Rare_Increase) == true)
						{
							Lore.add(ChatColor.RED + HpBonus[0] + this.Calculate_Health(Tier.Rare) + HpBonus[1]);
						}
						else
						{
							Lore.add(ChatColor.RED + HpBonus[0] + this.Calculate_Health(Tier.Common) + HpBonus[1]);
						}
						break;
					case GOLD_LEGGINGS:
						if (PercentCheck(15 + Rare_Increase) == true)
						{
							Lore.add(ChatColor.RED + HpBonus[0] + this.Calculate_Health(Tier.Rare) + HpBonus[1]);
						}
						else
						{
							Lore.add(ChatColor.RED + HpBonus[0] + this.Calculate_Health(Tier.Common) + HpBonus[1]);
						}
						break;
					case GOLD_HELMET:
						if (PercentCheck(15 + Rare_Increase) == true)
						{
							Lore.add(ChatColor.RED + HpBonus[0] + this.Calculate_Health(Tier.Rare) + HpBonus[1]);
						}
						else
						{
							Lore.add(ChatColor.RED + HpBonus[0] + this.Calculate_Health(Tier.Common) + HpBonus[1]);
						}
						break;
					case GOLD_CHESTPLATE:
						if (PercentCheck(15 + Rare_Increase) == true)
						{
							Lore.add(ChatColor.RED + HpBonus[0] + this.Calculate_Health(Tier.Rare) + HpBonus[1]);
						}
						else
						{
							Lore.add(ChatColor.RED + HpBonus[0] + this.Calculate_Health(Tier.Common) + HpBonus[1]);
						}
						break;
					case GOLD_BOOTS:
						if (PercentCheck(15 + Rare_Increase) == true)
						{
							Lore.add(ChatColor.RED + HpBonus[0] + this.Calculate_Health(Tier.Rare) + HpBonus[1]);
						}
						else
						{
							Lore.add(ChatColor.RED + HpBonus[0] + this.Calculate_Health(Tier.Common) + HpBonus[1]);
						}
						break;
					case DIAMOND_LEGGINGS:
						if (PercentCheck(40 + Rare_Increase) == true)
						{
							Lore.add(ChatColor.RED + HpBonus[0] + this.Calculate_Health(Tier.Epic) + HpBonus[1]);
						}
						else
						{
							Lore.add(ChatColor.RED + HpBonus[0] + this.Calculate_Health(Tier.Rare) + HpBonus[1]);
						}
						break;
					case DIAMOND_HELMET:
						if (PercentCheck(40 + Rare_Increase) == true)
						{
							Lore.add(ChatColor.RED + HpBonus[0] + this.Calculate_Health(Tier.Epic) + HpBonus[1]);
						}
						else
						{
							Lore.add(ChatColor.RED + HpBonus[0] + this.Calculate_Health(Tier.Rare) + HpBonus[1]);
						}
						break;
					case DIAMOND_CHESTPLATE:
						if (PercentCheck(40 + Rare_Increase) == true)
						{
							Lore.add(ChatColor.RED + HpBonus[0] + this.Calculate_Health(Tier.Epic) + HpBonus[1]);
						}
						else
						{
							Lore.add(ChatColor.RED + HpBonus[0] + this.Calculate_Health(Tier.Rare) + HpBonus[1]);
						}
						break;
					case DIAMOND_BOOTS:
						if (PercentCheck(40 + Rare_Increase) == true)
						{
							Lore.add(ChatColor.RED + HpBonus[0] + this.Calculate_Health(Tier.Epic) + HpBonus[1]);
						}
						else
						{
							Lore.add(ChatColor.RED + HpBonus[0] + this.Calculate_Health(Tier.Rare) + HpBonus[1]);
						}
						break;
					case BOW:
						if (PercentCheck(2 + Rare_Increase) == true)
						{
							Damages = TotalWarItems.ItemHandler.Calculate_Damage(TotalWarItems.ItemHandler.getItemDamage("Bow", Tier.Epic));
							Lore.add(ChatColor.RED + "Deals " + Damages[0] + " to " + Damages[1] + " damage!");
						}
						else if(PercentCheck(15 + Rare_Increase) == true)
						{
							Damages = TotalWarItems.ItemHandler.Calculate_Damage(TotalWarItems.ItemHandler.getItemDamage("Bow", Tier.Rare));
							Lore.add(ChatColor.RED + "Deals " + Damages[0] + " to " + Damages[1] + " damage!");
						}
						else
						{
							Damages = TotalWarItems.ItemHandler.Calculate_Damage(TotalWarItems.ItemHandler.getItemDamage("Bow", Tier.Common));
							Lore.add(ChatColor.RED + "Deals " + Damages[0] + " to " + Damages[1] + " damage!");
						}
						break;
					case ARROW:
						break;
					default:
						break;
				}
				if (this.Weapons.contains(Result.getType()))
				{
					SkillHandlers.HandleCrafting(Player.getName(),this.getExpValues(Result.getType()));
				}
				PlayerHandler Handler = Skills.getPlayerHandler(Player.getName());
				if (Handler.getSkill("Crafting").getLevel() >= 30)
				{
					if (PercentCheck(Skills.getPlayerHandler(Player.getName()).getSkill("Crafting").getLevel() * 0.15))
					{
						Lore.add(ChatColor.YELLOW + "Infused with hidden potential");
					}
				}
				ResultMeta.setLore(Lore);
				Result.setItemMeta(ResultMeta);
			}
		}
		catch (Exception Ex)
		{
			//Sloppy Catch
		}
	}
}
