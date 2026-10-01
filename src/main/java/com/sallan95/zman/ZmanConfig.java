package com.sallan95.zman;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.ConfigSection;
import net.runelite.client.config.Range;

@ConfigGroup("zman")
public interface ZmanConfig extends Config
{
	@ConfigItem(
		keyName = "showAllCaughtUp",
		name = "Show \"all caught up\" message",
		description = "When no tracked skill is deficient, show a small confirmation line instead of hiding the overlay entirely"
	)
	default boolean showAllCaughtUp()
	{
		return false;
	}

	@ConfigSection(
		name = "Direct Skills",
		description = "PZ skills that map 1:1 to a single RS skill requirement",
		position = 0
	)
	String directSkillsSection = "directSkills";

	@ConfigSection(
		name = "Crafting Skills",
		description = "PZ skills whose highest level sets the RS Crafting requirement",
		position = 1
	)
	String craftingSkillsSection = "craftingSkills";

	@ConfigSection(
		name = "Weapon Skills",
		description = "PZ skills whose highest level sets the RS Attack requirement. Axe also feeds RS Woodcutting directly",
		position = 2
	)
	String weaponSkillsSection = "weaponSkills";

	// --- Direct Skills ---

	@ConfigItem(
		keyName = "fishing",
		name = "Fishing",
		description = "Your current Project Zomboid Fishing level (0 = not tracked)",
		section = directSkillsSection
	)
	@Range(min = 0, max = 10)
	default int fishing()
	{
		return 0;
	}

	@ConfigItem(
		keyName = "metalworking",
		name = "Metalworking",
		description = "Your current Project Zomboid Metalworking level. Feeds the RuneScape Mining requirement (0 = not tracked)",
		section = directSkillsSection
	)
	@Range(min = 0, max = 10)
	default int metalworking()
	{
		return 0;
	}

	@ConfigItem(
		keyName = "cooking",
		name = "Cooking",
		description = "Your current Project Zomboid Cooking level (0 = not tracked)",
		section = directSkillsSection
	)
	@Range(min = 0, max = 10)
	default int cooking()
	{
		return 0;
	}

	@ConfigItem(
		keyName = "farming",
		name = "Farming",
		description = "Your current Project Zomboid Farming level (0 = not tracked)",
		section = directSkillsSection
	)
	@Range(min = 0, max = 10)
	default int farming()
	{
		return 0;
	}

	@ConfigItem(
		keyName = "strength",
		name = "Strength",
		description = "Your current Project Zomboid Strength level (0 = not tracked)",
		section = directSkillsSection
	)
	@Range(min = 0, max = 10)
	default int strength()
	{
		return 0;
	}

	@ConfigItem(
		keyName = "fitness",
		name = "Fitness",
		description = "Your current Project Zomboid Fitness level. Feeds the RuneScape Agility requirement (0 = not tracked)",
		section = directSkillsSection
	)
	@Range(min = 0, max = 10)
	default int fitness()
	{
		return 0;
	}

	@ConfigItem(
		keyName = "blacksmithing",
		name = "Blacksmithing",
		description = "Your current Project Zomboid Blacksmithing level (PZ Build 42). Feeds the RuneScape Smithing requirement (0 = not tracked)",
		section = directSkillsSection
	)
	@Range(min = 0, max = 10)
	default int blacksmithing()
	{
		return 0;
	}

	@ConfigItem(
		keyName = "mechanics",
		name = "Mechanics",
		description = "Your current Project Zomboid Mechanics level. Feeds the RuneScape Sailing requirement (0 = not tracked)",
		section = directSkillsSection
	)
	@Range(min = 0, max = 10)
	default int mechanics()
	{
		return 0;
	}

	@ConfigItem(
		keyName = "aiming",
		name = "Aiming",
		description = "Your current Project Zomboid Aiming level. Feeds the RuneScape Ranged requirement (0 = not tracked)",
		section = directSkillsSection
	)
	@Range(min = 0, max = 10)
	default int aiming()
	{
		return 0;
	}

	@ConfigItem(
		keyName = "reloading",
		name = "Reloading",
		description = "Your current Project Zomboid Reloading level. Feeds the RuneScape Fletching requirement (0 = not tracked)",
		section = directSkillsSection
	)
	@Range(min = 0, max = 10)
	default int reloading()
	{
		return 0;
	}

	@ConfigItem(
		keyName = "carpentry",
		name = "Carpentry",
		description = "Your current Project Zomboid Carpentry level. Feeds the RuneScape Construction requirement (0 = not tracked)",
		section = directSkillsSection
	)
	@Range(min = 0, max = 10)
	default int carpentry()
	{
		return 0;
	}

	@ConfigItem(
		keyName = "firstAid",
		name = "First Aid",
		description = "Your current Project Zomboid First Aid level. Feeds the RuneScape Prayer requirement (0 = not tracked)",
		section = directSkillsSection
	)
	@Range(min = 0, max = 10)
	default int firstAid()
	{
		return 0;
	}

	@ConfigItem(
		keyName = "electrical",
		name = "Electrical",
		description = "Your current Project Zomboid Electrical level. Feeds the RuneScape Magic requirement (0 = not tracked)",
		section = directSkillsSection
	)
	@Range(min = 0, max = 10)
	default int electrical()
	{
		return 0;
	}

	@ConfigItem(
		keyName = "foraging",
		name = "Foraging",
		description = "Your current Project Zomboid Foraging level. Feeds the RuneScape Herblore requirement (0 = not tracked)",
		section = directSkillsSection
	)
	@Range(min = 0, max = 10)
	default int foraging()
	{
		return 0;
	}

	@ConfigItem(
		keyName = "trapping",
		name = "Trapping",
		description = "Your current Project Zomboid Trapping level. Feeds the RuneScape Hunter requirement (0 = not tracked)",
		section = directSkillsSection
	)
	@Range(min = 0, max = 10)
	default int trapping()
	{
		return 0;
	}

	// --- Crafting Skills (highest of the 5 sets the RS Crafting requirement) ---

	@ConfigItem(
		keyName = "tailoring",
		name = "Tailoring",
		description = "Your current Project Zomboid Tailoring level (0 = not tracked)",
		section = craftingSkillsSection
	)
	@Range(min = 0, max = 10)
	default int tailoring()
	{
		return 0;
	}

	@ConfigItem(
		keyName = "craftingPz",
		name = "Crafting",
		description = "Your current Project Zomboid Crafting level (0 = not tracked)",
		section = craftingSkillsSection
	)
	@Range(min = 0, max = 10)
	default int craftingPz()
	{
		return 0;
	}

	@ConfigItem(
		keyName = "glassmaking",
		name = "Glassmaking",
		description = "Your current Project Zomboid Glassmaking level (0 = not tracked)",
		section = craftingSkillsSection
	)
	@Range(min = 0, max = 10)
	default int glassmaking()
	{
		return 0;
	}

	@ConfigItem(
		keyName = "knapping",
		name = "Knapping",
		description = "Your current Project Zomboid Knapping level (0 = not tracked)",
		section = craftingSkillsSection
	)
	@Range(min = 0, max = 10)
	default int knapping()
	{
		return 0;
	}

	@ConfigItem(
		keyName = "pottery",
		name = "Pottery",
		description = "Your current Project Zomboid Pottery level (0 = not tracked)",
		section = craftingSkillsSection
	)
	@Range(min = 0, max = 10)
	default int pottery()
	{
		return 0;
	}

	// --- Weapon Skills (highest of the 6 sets the RS Attack requirement; Axe also feeds Woodcutting) ---

	@ConfigItem(
		keyName = "axe",
		name = "Axe",
		description = "Your current Project Zomboid Axe level. Feeds RuneScape Woodcutting directly, and also counts toward Attack (0 = not tracked)",
		section = weaponSkillsSection
	)
	@Range(min = 0, max = 10)
	default int axe()
	{
		return 0;
	}

	@ConfigItem(
		keyName = "longBlade",
		name = "Long Blade",
		description = "Your current Project Zomboid Long Blade level (0 = not tracked)",
		section = weaponSkillsSection
	)
	@Range(min = 0, max = 10)
	default int longBlade()
	{
		return 0;
	}

	@ConfigItem(
		keyName = "shortBlade",
		name = "Short Blade",
		description = "Your current Project Zomboid Short Blade level (0 = not tracked)",
		section = weaponSkillsSection
	)
	@Range(min = 0, max = 10)
	default int shortBlade()
	{
		return 0;
	}

	@ConfigItem(
		keyName = "longBlunt",
		name = "Long Blunt",
		description = "Your current Project Zomboid Long Blunt level (0 = not tracked)",
		section = weaponSkillsSection
	)
	@Range(min = 0, max = 10)
	default int longBlunt()
	{
		return 0;
	}

	@ConfigItem(
		keyName = "shortBlunt",
		name = "Short Blunt",
		description = "Your current Project Zomboid Short Blunt level (0 = not tracked)",
		section = weaponSkillsSection
	)
	@Range(min = 0, max = 10)
	default int shortBlunt()
	{
		return 0;
	}

	@ConfigItem(
		keyName = "spear",
		name = "Spear",
		description = "Your current Project Zomboid Spear level (0 = not tracked)",
		section = weaponSkillsSection
	)
	@Range(min = 0, max = 10)
	default int spear()
	{
		return 0;
	}
}
