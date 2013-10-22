package main.java.SkillSystem.Handlers.Utilities;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TimeHandler
{
	public static String getDurationBreakdown(long millis)
	{
		if (millis < 0)
		{
			return null;
		}
		// long days = TimeUnit.MILLISECONDS.toDays(millis);
		// millis -= TimeUnit.DAYS.toMillis(days);
		long hours = TimeUnit.MILLISECONDS.toHours(millis);
		millis -= TimeUnit.HOURS.toMillis(hours);
		long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
		// millis -= TimeUnit.MINUTES.toMillis(minutes);
		// long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);

		StringBuilder sb = new StringBuilder(64);
		// sb.append(days);
		// sb.append(" Days ");
		if (hours > 0)
		{
			sb.append(hours);
			sb.append(" hour, ");
		}
		sb.append(minutes);
		sb.append(" mins");
		// sb.append(seconds);
		// sb.append(" Seconds");

		return (sb.toString());
	}
	
	public static Date GetDateIn(int Days)
	{
		Calendar Cal = Calendar.getInstance();
		Cal.add(Calendar.DATE, Days);
		return Cal.getTime();
	}
}
