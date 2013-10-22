package main.java.SkillSystem.Handlers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import main.java.SkillSystem.PlayerSkills.Skill;

public class PlayerHandler {
	
	private String DefaultSkillText = "<Level>1</Level>\n<Exp>0</Exp>";
	
	private String[] SkillNames = new String[] {"Melee","Archery","Magic","Unarmed","Taming","Crafting","Farming" };
	private ArrayList<Skill> Skills = new ArrayList<Skill>();
	
	public PlayerHandler(String PlayerName)
	{
		File PlayerLocation = new File("plugins/SkillSystem/" + PlayerName);
		if (!PlayerLocation.exists())
		{
			PlayerLocation.mkdir();
		}
		for(String S : SkillNames)
		{
			File SkillLocation = new File("plugins/SkillSystem/" + PlayerName + "/" + S + ".txt");
			if (!SkillLocation.exists())
			{
				try
				{
					SkillLocation.createNewFile();
					FileUtils.writeStringToFile(SkillLocation, DefaultSkillText, false);
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
			Skills.add(new Skill("plugins/SkillSystem/" + PlayerName + "/" + S + ".txt", S));
		}
	}
	
	public Skill getSkill(String Name)
	{
		for(Skill S : this.Skills)
		{
			if (S.toString().equalsIgnoreCase(Name))
			{
				return S;
			}
		}
		return null;
	}

}
