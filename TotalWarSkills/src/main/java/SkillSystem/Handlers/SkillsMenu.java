package main.java.SkillSystem.Handlers;

import org.bukkit.Material;
import org.bukkit.material.MaterialData;

public class SkillsMenu
{

	public SkillsMenu() { }
	
	public MaterialData getIcon(String SkillName)
	{
		switch (SkillName)
		{
			case "Melee":
				return new MaterialData(Material.IRON_SWORD);
			case "Archery":
				return new MaterialData(Material.BOW);
			case "Unarmed":
				return new MaterialData(Material.LEATHER_HELMET);
			case "Magic":
				return new MaterialData(Material.BLAZE_POWDER);
			case "Taming":
				return new MaterialData(Material.SADDLE);
			case "Crafting":
				return new MaterialData(Material.WORKBENCH);
			case "Farming":
				return new MaterialData(Material.IRON_HOE);
			default:
				return new MaterialData(Material.TNT);
		}
	}
	
}
