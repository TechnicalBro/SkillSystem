package main.java.SkillSystem.PlayerSkills;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import main.java.SkillSystem.Handlers.TextData;

public class Skill {
	
	private String[] LevelTag = new String[] {"<Level>","</Level>" };
	private String[] EXPTag = new String[] {"<Exp>","</Exp>" };
	
	private String FileLocation;
	private String SkillName = "";
	
	public Skill(String FileLocation, String SkillName)
	{
		this.FileLocation = FileLocation;
		this.SkillName = SkillName;
	}
	
	@Override
	public String toString()
	{
		return this.SkillName;
	}
	
	public double getLevel()
	{
		try
		{
			return Double.parseDouble((TextData.getBetween(FileUtils.readFileToString(new File(this.FileLocation)),LevelTag[0],LevelTag[1])));
		}
		catch (NumberFormatException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return 0;
	}
	
	public double getExp()
	{
		try
		{
			return Double.parseDouble((TextData.getBetween(FileUtils.readFileToString(new File(this.FileLocation)),EXPTag[0],EXPTag[1])));
		}
		catch (NumberFormatException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return 0;
	}
	
	public void addExp(double Amount)
	{
		try
		{
			double newExp = (this.getExp() + Amount);
			FileUtils.writeStringToFile(new File(this.FileLocation), this.LevelTag[0] + this.getLevel() + this.LevelTag[1] + "\n" + this.EXPTag[0] + newExp + this.EXPTag[1], false);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void addLevel(int Amount)
	{
		try
		{
			double newLevel = (this.getLevel() + Amount);
			FileUtils.writeStringToFile(new File(this.FileLocation), this.LevelTag[0] + newLevel + this.LevelTag[1] + "\n" + this.EXPTag[0] + this.getExp() + this.EXPTag[1], false);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

}
