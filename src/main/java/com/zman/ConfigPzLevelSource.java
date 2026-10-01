package com.zman;

import java.util.stream.IntStream;
import javax.inject.Inject;
import net.runelite.api.Skill;

/**
 * The POC's only PzLevelSource: reads manually-entered PZ levels straight from ZmanConfig.
 * All skill-to-config-field mapping complexity lives here, not in ZmanPlugin:
 * - Most skills are a direct one-field lookup.
 * - CRAFTING and ATTACK take the max across a group of PZ skill fields.
 * - WOODCUTTING reuses the same Axe field that also feeds the ATTACK group.
 * - Skills with no PZ counterpart (Defence, Hitpoints, Firemaking, Thieving, Slayer,
 *   Runecraft) always return 0 (untracked) - there is no config item backing them.
 */
public class ConfigPzLevelSource implements PzLevelSource
{
	private final ZmanConfig config;

	@Inject
	public ConfigPzLevelSource(ZmanConfig config)
	{
		this.config = config;
	}

	@Override
	public int getPzLevel(Skill skill)
	{
		switch (skill)
		{
			case FISHING:
				return config.fishing();
			case MINING:
				return config.metalworking();
			case COOKING:
				return config.cooking();
			case FARMING:
				return config.farming();
			case STRENGTH:
				return config.strength();
			case AGILITY:
				return config.fitness();
			case SMITHING:
				return config.blacksmithing();
			case SAILING:
				return config.mechanics();
			case RANGED:
				return config.aiming();
			case FLETCHING:
				return config.reloading();
			case CONSTRUCTION:
				return config.carpentry();
			case PRAYER:
				return config.firstAid();
			case MAGIC:
				return config.electrical();
			case HERBLORE:
				return config.foraging();
			case HUNTER:
				return config.trapping();
			case CRAFTING:
				return craftingLevel();
			case ATTACK:
				return weaponLevel();
			case WOODCUTTING:
				return config.axe();
			default:
				// Defence, Hitpoints, Firemaking, Thieving, Slayer, Runecraft: no PZ counterpart.
				return 0;
		}
	}

	private int craftingLevel()
	{
		return IntStream.of(
			config.tailoring(), config.craftingPz(), config.glassmaking(), config.knapping(), config.pottery()
		).max().orElse(0);
	}

	private int weaponLevel()
	{
		return IntStream.of(
			config.axe(), config.longBlade(), config.shortBlade(),
			config.longBlunt(), config.shortBlunt(), config.spear()
		).max().orElse(0);
	}
}
