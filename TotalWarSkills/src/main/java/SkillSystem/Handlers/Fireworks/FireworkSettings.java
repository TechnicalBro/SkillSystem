package main.java.SkillSystem.Handlers.Fireworks;

import java.util.Random;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.FireworkEffect.Type;
 
public class FireworkSettings
{
	//A random for colors, types and booleans coming
	static Random newrandom = new Random();
	//Getting the playerclass
	FireworkEffectPlayer fplayer = new FireworkEffectPlayer();
	 
	//Making a random fireworkType
	public static Type randomType()
	{
		//Getting the double and use NextDouble() so you get a new number
		double random1 = newrandom.nextDouble() * 10;
		//The number goes from 0 to 10, if the number is 2 give Ball with 2/4 give Burst etc.
		if(random1 < 2)
		{
			return Type.BALL;
		}
		else if(random1 >= 2 && random1 < 4)
		{
			return Type.BURST;
		}
		else if(random1 >= 4 && random1 < 6)
		{
			return Type.CREEPER;
		}
		else if(random1 >= 6 && random1 < 8)
		{
			return Type.STAR;
		}
		else
		{
			return Type.BALL_LARGE;
		}
	}
	 
	//Random Colors, I only used the ones from Minecraft
	public static Color randomColor()
	{
		//The same as with the types but then with colors
		double random2 = newrandom.nextDouble()*8;
		if(random2 < 1)
		{
			return Color.WHITE;
		}
		else if(random2 >= 1 && random2 < 2)
		{
			return Color.ORANGE;
		}
		else if(random2 >=2 && random2 < 3)
		{
			return Color.BLUE;
		}
		else if(random2 >= 3 && random2 < 4)
		{
			return Color.YELLOW;
		}
		else if(random2 >= 4 && random2 < 5)
		{
			return Color.GREEN;
		}
		else if(random2 >= 5 && random2 < 6)
		{
			return Color.GRAY;
		}
		else if(random2 >= 6 && random2 < 7)
		{
			return Color.PURPLE;
		}
		else
		{
			return Color.RED;
		}
	}
	 
	//Simple random boolean
	public static boolean randomBoolean()
	{
		double random3 = newrandom.nextDouble() * 8;
		if(random3 <= 4)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	 
	//FireworkEffectPlayer but then shorter, this more clearer view
	public void playFw(Location location, FireworkEffect fwEffect)
	{
		try
		{
			fplayer.playFirework(location.getWorld(), location, fwEffect);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	 
	//A random fireworkeffect with everything in it
	public FireworkEffect randomFireworkEffect()
	{
		return FireworkEffect.builder().with(randomType()).withColor(randomColor()).withFade(randomColor()).trail(randomBoolean()).flicker(randomBoolean()).build();
	}
}