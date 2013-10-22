package main.java.SkillSystem.Handlers;

import java.util.HashMap;

public class EXPChart {
	
	public static int getExpUntilNextLevel(int CurrentEXP,int CurrentLevel)
	{
		return ((int)Math.round(experienceForLevel(CurrentLevel + 1) - CurrentEXP));
	}
	
	public static boolean canLevelUp(double CurrentEXP, double CurrentLevel)
	{
		if (experienceForLevel(CurrentLevel + 1) <= CurrentEXP)
		{
			return true;
		}
		return false;
	}
	
	public static double experienceForLevel(double level)
	{
		int total = 0;
		for (int i = 1; i < level; i++)
		{
		  total += i + 300 * Math.pow(2, i / 7.0);
		}
		
		return (int)(total * 0.8);
	}

}
