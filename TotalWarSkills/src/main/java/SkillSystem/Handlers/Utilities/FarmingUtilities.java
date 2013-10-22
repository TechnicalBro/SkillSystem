package main.java.SkillSystem.Handlers.Utilities;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

import main.java.SkillSystem.Skills;
import main.java.SkillSystem.Handlers.PlayerHandler;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.caved_in.Cooldown.Cooldown;

public class FarmingUtilities
{
	private List<Material> InstaGrowBlocks = Arrays.asList(new Material[] {Material.SUGAR_CANE_BLOCK,Material.CROPS,Material.CARROT,Material.POTATO,Material.PUMPKIN_STEM,Material.MELON_STEM,Material.NETHER_WARTS });
	private List<Material> FarmingTools = Arrays.asList(new Material[] {Material.WOOD_HOE,Material.STONE_HOE,Material.IRON_HOE,Material.GOLD_HOE,Material.DIAMOND_HOE});
	private List<EntityType> FarmAnimals = Arrays.asList(new EntityType [] {EntityType.CHICKEN,EntityType.PIG,EntityType.SHEEP,EntityType.COW });
	private Cooldown MilkCowMessage = new Cooldown(1);
	private Cooldown MilkCowCooldown = new Cooldown(600);
	private Cooldown PluckChickenCooldown = new Cooldown(5);
	
	public FarmingUtilities() { }
	
	public boolean canInstaGrow(String Player, Block Block)
	{
		if (this.InstaGrowBlocks.contains(Block.getType()))
		{
			PlayerHandler Handler = Skills.getPlayerHandler(Player);
			double instaGrowthChance = (this.instaGrowChancePerLevel(Block.getType()) * Handler.getSkill("Farming").getLevel());
			if (PercentCheck(instaGrowthChance))
			{
				return true;
			}
		}
		return false;
	}
	
	public boolean canAwardExp(Block Block)
	{
		Byte BlockData = Block.getData();
		switch (Block.getType())
		{
			case SUGAR_CANE_BLOCK:
				return BlockData.intValue() == 15;
			case CROPS:
				return BlockData.intValue() == 7;
			case CARROT:
				return BlockData.intValue() == 7;
			case POTATO:
				return BlockData.intValue() == 7;
			case PUMPKIN_STEM:
				return BlockData.intValue() == 7;
			case MELON_STEM:
				return BlockData.intValue() == 7;
			case NETHER_WARTS:
				return BlockData.intValue() == 3;
			default:
				break;
		}
		return false;
	}
	
	public boolean isFarmingBlock(Material Mat)
	{
		return this.InstaGrowBlocks.contains(Mat) || Mat.equals(Material.SUGAR_CANE_BLOCK);
	}
	
	public boolean isFarmingTool(ItemStack Item)
	{
		return FarmingTools.contains(Item.getType());
	}
	
	public boolean isUsingFarmingTool(Player Player)
	{
		if (Player.getItemInHand() != null)
		{
			return isFarmingTool(Player.getItemInHand());
		}
		return false;
	}
	
	public boolean isUsingBoneMeal(Player Player)
	{
		if (Player.getItemInHand() != null)
		{
			return (Player.getItemInHand().getType() == Material.INK_SACK && Player.getItemInHand().getDurability() == 15);
		}
		return false;
	}
	
	public double instaGrowChancePerLevel(Material Material)
	{
		switch (Material)
		{
			case SUGAR_CANE_BLOCK:
				return 1.4;
			case CROPS:
				return 1.8;
			case CARROT:
				return 1.5;
			case POTATO:
				return 1.5;
			case PUMPKIN_STEM:
				return 1.4;
			case MELON_STEM:
				return 1.3;
			case NETHER_WARTS:
				return 1.3;
			default:
				return 0;
		}
	}
	
	public double PluckChickenChancePerLevel()
	{
		return 1.5;
	}
	
	public int getCropExp(Material Material)
	{
		switch (Material)
		{
			case SUGAR_CANE_BLOCK:
				return 6;
			case CROPS:
				return 3;
			case CARROT:
				return 5;
			case POTATO:
				return 5;
			case PUMPKIN_STEM:
				return 8;
			case MELON_STEM:
				return 8;
			case NETHER_WARTS:
				return 8;
			default:
				return 0;
		}
	}
	
	public byte getGrowthData(Material Material)
	{
		switch (Material)
		{
			case SUGAR_CANE_BLOCK:
				return (byte)15;
			case CROPS:
				return (byte)7;
			case CARROT:
				return (byte)7;
			case POTATO:
				return (byte)7;
			case PUMPKIN_STEM:
				return (byte)7;
			case MELON_STEM:
				return (byte)7;
			case NETHER_WARTS:
				return (byte)3;
			default:
				break;
		}
		throw new NullPointerException("The material [" + Material.name().toLowerCase() + "] isn't supported to be 'insta' grown");
	}
	
	private boolean PercentCheck(double Chances)
	{
		if (new Random().nextInt(100) <= Chances)
		{
			return true;
		}
		return false;
	}
	
	public boolean isFarmAnimal(Entity Entity)
	{
		return this.FarmAnimals.contains(Entity.getType());
	}
	
	/**
	 * If the players uses a bucket on a cow, they're given "Delicious Milk" that has an expiration
	 * date written in the lore, consuming after the expiration date adds confusion for 5 seconds.
	 * @param Entity The entity to handle
	 * @param Player The Player who's milking
	 * @return true if the player milked the cow, false otherwise
	 */
	public boolean HandleCowMilk(LivingEntity Entity, Player Player)
	{
		ItemStack Hand = Player.getItemInHand();
		if (Hand != null && Hand.getType() != Material.AIR)
		{
			if (Hand.getType() == Material.BUCKET)
			{
				if (Player.getInventory().firstEmpty() != -1)
				{
					ItemStack MilkBucket = new ItemStack(Material.MILK_BUCKET,1);
					ItemMeta MilkMeta = MilkBucket.getItemMeta();
					MilkMeta.setDisplayName(ChatColor.WHITE + "Delicious Milk");
					SimpleDateFormat DateUser = new SimpleDateFormat("MM - dd");
					String BestBefore = DateUser.format(TimeHandler.GetDateIn(2));
					MilkMeta.setLore(Arrays.asList(new String[] { ChatColor.YELLOW + "Best Before: " + BestBefore }));
					MilkBucket.setItemMeta(MilkMeta);
					Player.setItemInHand(RemoveFromStack(Hand, 1));
					Player.getInventory().addItem(MilkBucket);
					Player.playSound(Player.getLocation(), Sound.COW_IDLE, 1.0F, 1.0F);
					return true;
				}
				else
				{
					if (!MilkCowMessage.IsOnCooldown(Player.getName()))
					{
						Player.sendMessage("You don't have enough space in your inventory to hold the milk, make some room then try again!");
						MilkCowMessage.SetOnCooldown(Player.getName());
					}
					return false;
				}
			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}
	}
	
	public boolean HandleChicken(LivingEntity Entity, Player Player)
	{
		if (Player.getItemInHand() != null && !PluckChickenCooldown.IsOnCooldown(Player.getName()))
		{
			if (Player.getItemInHand().getType() == Material.SHEARS)
			{
				PlayerHandler Handler = Skills.getPlayerHandler(Player.getName());
				if (PercentCheck(Handler.getSkill("Farming").getLevel() * this.PluckChickenChancePerLevel()))
				{
					ItemStack Feathers = new ItemStack(Material.FEATHER,1);
					if (Player.getInventory().firstEmpty() != -1)
					{
						Player.getInventory().addItem(Feathers);
					}
					else
					{
						Player.getWorld().dropItemNaturally(Entity.getLocation().add(1, 1, 1), Feathers);
					}
					Player.getItemInHand().setDurability((short) (Player.getItemInHand().getDurability() - 1));
					return true;
				}
				else
				{
					return false;
				}
			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * Remove an amount of items from an ItemStack
	 * @param Item Itemstack to remove the items from
	 * @param Amount Amount of items to remove
	 * @return A new Itemstack with the amount of items specified removed from it, but if < 0, null
	 */
	public ItemStack RemoveFromStack(ItemStack Item, int Amount)
	{
		if (Item.getAmount() > Amount)
		{
			ItemMeta StackMeta = Item.getItemMeta();
			ItemStack Return = new ItemStack(Item.getType(), Item.getAmount() - Amount);
			Return.setItemMeta(StackMeta);
			Return.setDurability(Item.getDurability());
			return Return;
		}
		return null;
	}
}
