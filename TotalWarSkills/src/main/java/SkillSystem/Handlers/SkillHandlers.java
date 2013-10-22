package main.java.SkillSystem.Handlers;

import java.util.Random;

import main.java.SkillSystem.Skills;
import main.java.SkillSystem.Handlers.Fireworks.FireworkSettings;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import com.caved_in.TotalWar;

public class SkillHandlers {
	private static FireworkSettings Fireworks = new FireworkSettings();
	
	public SkillHandlers()
	{
		
	}
	
	
	public static void HandleCrafting(String Player,double Exp)
	{
		Skills.getPlayerHandler(Player).getSkill("Crafting").addExp((int)(Exp * getExpMultiplier(Player)));
		if (EXPChart.canLevelUp(Skills.getPlayerHandler(Player).getSkill("Crafting").getExp(), Skills.getPlayerHandler(Player).getSkill("Crafting").getLevel()))
		{
			Skills.getPlayerHandler(Player).getSkill("Crafting").addLevel(1);
			LevelupEffect(Player,"Congrats; You've leveled up your Crafting skill to " + Skills.getPlayerHandler(Player).getSkill("Crafting").getLevel() + "!");
		}
	}
	
	private static double getExpMultiplier(String PlayerName)
	{
		double Modifier = 0.0;
		Player Player = Bukkit.getPlayer(PlayerName);
		if (Player != null)
		{
			if (TotalWar.permission.has(Player, TotalWar.Player_Voter_Permission))
			{
				Modifier = 0.3;
			}
			if (TotalWar.permission.has(Player, TotalWar.Donator_Permission))
			{
				Modifier += TotalWar.Plugin_Config.getDonatorSkillExpMultiplier();
				return Modifier;
			}
		}
		return 1 + Modifier;
	}
	
	public static void LevelupEffect(String PlayerN,String Message)
	{
		Player Player = Bukkit.getPlayer(PlayerN);
		for(int I = 0; I < 2 + new Random().nextInt(5); I++)
		{
			Fireworks.playFw(Player.getLocation(), Fireworks.randomFireworkEffect()); 
		}
		Player.sendMessage(ChatColor.GOLD + Message);
	}
	
	public static double HandleUnarmedDamage(Player Attacker)
	{
		double UnarmedLevel = Skills.getPlayerHandler(Attacker.getName()).getSkill("Unarmed").getLevel();
		return UnarmedLevel * 0.8;
	}
	
	public static double HandleArcheryHeadshotDamage(Player Attacker)
	{
		double ArcheryLevel = Skills.getPlayerHandler(Attacker.getName()).getSkill("Archery").getLevel();
		return ArcheryLevel * 0.4;
	}
	
	public static double HandleMeleeDamage(Player Attacker)
	{
		double MeleeLevel = Skills.getPlayerHandler(Attacker.getName()).getSkill("Melee").getLevel();
		return MeleeLevel * 0.2;
	}
	
	public static void HandleUnarmed(String Player,double EnemyHP)
	{
		Skills.getPlayerHandler(Player).getSkill("Unarmed").addExp((int)Math.round((0.8 * EnemyHP) * getExpMultiplier(Player)));
		if (EXPChart.canLevelUp(Skills.getPlayerHandler(Player).getSkill("Unarmed").getExp(), Skills.getPlayerHandler(Player).getSkill("Unarmed").getLevel()))
		{
			Skills.getPlayerHandler(Player).getSkill("Unarmed").addLevel(1);
			LevelupEffect(Player,"Congrats; You've leveled up your Unarmed Combat skill to " + Skills.getPlayerHandler(Player).getSkill("Unarmed").getLevel() + "!");
		}
	}
	
	public static void HandleMobTaming(Player Player, LivingEntity Entity)
	{
		Skills.getPlayerHandler(Player.getName()).getSkill("Taming").addExp((int)Math.round((getTameExp(Entity.getType()) + (new Random().nextInt(20))) * getExpMultiplier(Player.getName())));
		if (EXPChart.canLevelUp(Skills.getPlayerHandler(Player.getName()).getSkill("Taming").getExp(), Skills.getPlayerHandler(Player.getName()).getSkill("Taming").getLevel()))
		{
			Skills.getPlayerHandler(Player.getName()).getSkill("Taming").addLevel(1);
			LevelupEffect(Player.getName(),"Congrats; You've leveled up your Taming skill to " + Skills.getPlayerHandler(Player.getName()).getSkill("Taming").getLevel() + "!");
		}
	}
	
	public static void HandleMobEXP(Player Player, double EXP)
	{
		Skills.getPlayerHandler(Player.getName()).getSkill("Taming").addExp((int)Math.round(EXP * getExpMultiplier(Player.getName())));
		if (EXPChart.canLevelUp(Skills.getPlayerHandler(Player.getName()).getSkill("Taming").getExp(), Skills.getPlayerHandler(Player.getName()).getSkill("Taming").getLevel()))
		{
			Skills.getPlayerHandler(Player.getName()).getSkill("Taming").addLevel(1);
			LevelupEffect(Player.getName(),"Congrats; You've leveled up your Taming skill to " + Skills.getPlayerHandler(Player.getName()).getSkill("Taming").getLevel() + "!");
		}
	}
	
	public static boolean canTame(EntityType Type, Player Player)
	{
		if (Skills.getPlayerHandler(Player.getName()).getSkill("Taming").getLevel() >= getTameRequirement(Type))
		{
			return true;
		}
		return false;
	}
	
	public static int getTameRequirement(EntityType Type)
	{
		switch (Type)
		{
		case BAT:
			return 3;
		case BLAZE:
			return 15;
		case CAVE_SPIDER:
			return 35;
		case CHICKEN:
			return 1;
		case COW:
			return 1;
		case CREEPER:
			return 5;
		case ENDERMAN:
			return 30;
		case ENDER_DRAGON:
			return 2000;
		case GHAST:
			return 80;
		case GIANT:
			return 85;
		case IRON_GOLEM:
			return 40;
		case MAGMA_CUBE:
			return 30;
		case MUSHROOM_COW:
			return 3;
		case OCELOT:
			return 1;
		case PIG:
			return 1;
		case PIG_ZOMBIE:
			return 25;
		case SHEEP:
			return 1;
		case SILVERFISH:
			return 18;
		case SKELETON:
			return 20;
		case SLIME:
			return 15;
		case SNOWMAN:
			return 24;
		case SPIDER:
			return 10;
		case SQUID:
			return 1;
		case VILLAGER:
			return 200;
		case WITCH:
			return 99;
		case WITHER:
			return 9000;
		case WOLF:
			return 1;
		case ZOMBIE:
			return 5;
		default:
			return 0;
		}
	}
	
	public static int getTameExp(EntityType Type)
	{
		switch (Type)
		{
		case BAT:
			return 15;
		case BLAZE:
			return 35;
		case CAVE_SPIDER:
			return 65;
		case CHICKEN:
			return 10;
		case COW:
			return 10;
		case CREEPER:
			return 25;
		case HORSE:
			return 15;
		case ENDERMAN:
			return 60;
		case ENDER_DRAGON:
			return 1;
		case GHAST:
			return 225;
		case GIANT:
			return 240;
		case IRON_GOLEM:
			return 120;
		case MAGMA_CUBE:
			return 30;
		case MUSHROOM_COW:
			return 40;
		case OCELOT:
			return 50;
		case PIG:
			return 20;
		case PIG_ZOMBIE:
			return 15;
		case SHEEP:
			return 40;
		case SILVERFISH:
			return 30;
		case SKELETON:
			return 40;
		case SLIME:
			return 55;
		case SNOWMAN:
			return 10;
		case SPIDER:
			return 80;
		case SQUID:
			return 40;
		case VILLAGER:
			return 2;
		case WITCH:
			return 2;
		case WITHER:
			return 9;
		case WOLF:
			return 40;
		case ZOMBIE:
			return 120;
		default:
			return 0;
		}
	}
	
	public static void HandleArchery(String Player,double EnemyHP)
	{
		Skills.getPlayerHandler(Player).getSkill("Archery").addExp((int)Math.round((EnemyHP / 2) * getExpMultiplier(Player)));
		if (EXPChart.canLevelUp(Skills.getPlayerHandler(Player).getSkill("Archery").getExp(), Skills.getPlayerHandler(Player).getSkill("Archery").getLevel()))
		{
			Skills.getPlayerHandler(Player).getSkill("Archery").addLevel(1);
			LevelupEffect(Player,"Congrats; You've leveled up your Archery skill to " + Skills.getPlayerHandler(Player).getSkill("Archery").getLevel() + "!");
		}
	}
	
	public static void HandleFarming(String Player,double EXP)
	{
		Skills.getPlayerHandler(Player).getSkill("Farming").addExp((int)Math.round(EXP * getExpMultiplier(Player)));
		if (EXPChart.canLevelUp(Skills.getPlayerHandler(Player).getSkill("Farming").getExp(), Skills.getPlayerHandler(Player).getSkill("Farming").getLevel()))
		{
			Skills.getPlayerHandler(Player).getSkill("Farming").addLevel(1);
			LevelupEffect(Player,"Congrats; You've leveled up your Farming skill to " + Skills.getPlayerHandler(Player).getSkill("Farming").getLevel() + "!");
		}
	}
	
	public static void HandleMelee(String Player, double EnemyHP)
	{
		Skills.getPlayerHandler(Player).getSkill("Melee").addExp((int)Math.round((EnemyHP / 2) * getExpMultiplier(Player)));
		if (EXPChart.canLevelUp(Skills.getPlayerHandler(Player).getSkill("Melee").getExp(), Skills.getPlayerHandler(Player).getSkill("Melee").getLevel()))
		{
			Skills.getPlayerHandler(Player).getSkill("Melee").addLevel(1);
			LevelupEffect(Player,"Congrats; You've leveled up your Melee skill to " + Skills.getPlayerHandler(Player).getSkill("Melee").getLevel() + "!");
		}
	}
	
	public static void HandleMagic(String Player, double EXP)
	{
		Skills.getPlayerHandler(Player).getSkill("Magic").addExp((int)EXP * getExpMultiplier(Player));
		if (EXPChart.canLevelUp(Skills.getPlayerHandler(Player).getSkill("Magic").getExp(), Skills.getPlayerHandler(Player).getSkill("Magic").getLevel()))
		{
			Skills.getPlayerHandler(Player).getSkill("Magic").addLevel(1);
			LevelupEffect(Player,"Congrats; You've leveled up your Magic skill to " + Skills.getPlayerHandler(Player).getSkill("Magic").getLevel() + "!");
		}
	}
}
