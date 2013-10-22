package main.java.SkillSystem.Events;

import main.java.SkillSystem.Skills;
import main.java.SkillSystem.Handlers.SkillHandlers;
import main.java.SkillSystem.Handlers.Utilities.FarmingUtilities;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreak implements Listener
{
	private FarmingUtilities FarmingUtil = new FarmingUtilities();
	public BlockBreak(Skills Plugin)
	{
		Plugin.getServer().getPluginManager().registerEvents(this, Plugin);
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void BlockIsBroken(BlockBreakEvent Event)
	{
		if (Event.isCancelled())
		{
			return;
		}
		
		if (FarmingUtil.isFarmingBlock(Event.getBlock().getType()) && FarmingUtil.isUsingFarmingTool(Event.getPlayer()))
		{
			if (FarmingUtil.canAwardExp(Event.getBlock()))
			{
				SkillHandlers.HandleFarming(Event.getPlayer().getName(), FarmingUtil.getCropExp(Event.getBlock().getType()));
			}
		}
	}
	
}
