package com.zman;

import com.google.inject.Provides;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.Skill;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.StatChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

@Slf4j
@PluginDescriptor(
	name = "Zman"
)
public class ZmanPlugin extends Plugin
{
	// Every RS skill with a PZ mapping - see planning/requirements.md for the full table.
	// Defence, Hitpoints, Firemaking, Thieving, Slayer, and Runecraft have no PZ counterpart
	// and are deliberately excluded; ConfigPzLevelSource returns 0 for them regardless.
	private static final Skill[] TRACKED_SKILLS = {
		Skill.FISHING, Skill.MINING, Skill.COOKING, Skill.FARMING, Skill.STRENGTH,
		Skill.AGILITY, Skill.SMITHING, Skill.SAILING, Skill.RANGED, Skill.FLETCHING,
		Skill.CONSTRUCTION, Skill.PRAYER, Skill.MAGIC, Skill.HERBLORE, Skill.HUNTER,
		Skill.CRAFTING, Skill.ATTACK, Skill.WOODCUTTING
	};

	@Inject
	private Client client;

	@Inject
	private PzLevelSource pzLevelSource;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private ZmanOverlay overlay;

	private boolean recomputedThisLogin;

	private List<SkillRequirement> deficiencies = Collections.emptyList();

	@Override
	protected void startUp() throws Exception
	{
		log.debug("Zman started!");
		overlayManager.add(overlay);
	}

	@Override
	protected void shutDown() throws Exception
	{
		log.debug("Zman stopped!");
		overlayManager.remove(overlay);
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged event)
	{
		if (event.getGameState() != GameState.LOGGED_IN)
		{
			recomputedThisLogin = false;
			// Avoid showing stale deficiencies from the last session at the login screen.
			deficiencies = Collections.emptyList();
		}
	}

	@Subscribe
	public void onGameTick(GameTick tick)
	{
		// GameStateChanged(LOGGED_IN) can fire before skill data is actually loaded,
		// so the first read of getRealSkillLevel() right after it can be a stale 0.
		// Waiting for the next tick guarantees skill data is populated.
		if (client.getGameState() == GameState.LOGGED_IN && !recomputedThisLogin)
		{
			recomputedThisLogin = true;
			recompute();
		}
	}

	@Subscribe
	public void onConfigChanged(ConfigChanged event)
	{
		if (event.getGroup().equals("zman") && client.getGameState() == GameState.LOGGED_IN)
		{
			recompute();
		}
	}

	@Subscribe
	public void onStatChanged(StatChanged event)
	{
		recompute();
	}

	private void recompute()
	{
		List<SkillRequirement> result = new ArrayList<>();
		for (Skill skill : TRACKED_SKILLS)
		{
			int required = Math.min(pzLevelSource.getPzLevel(skill) * 10, 99);
			int actual = client.getRealSkillLevel(skill);
			if (actual < required)
			{
				result.add(new SkillRequirement(skill, actual, required));
			}
		}
		deficiencies = result;
		log.debug("Recomputed: {} skill(s) below requirement", result.size());
	}

	List<SkillRequirement> getDeficiencies()
	{
		return deficiencies;
	}

	@Provides
	ZmanConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(ZmanConfig.class);
	}

	@Provides
	PzLevelSource providePzLevelSource(ConfigPzLevelSource impl)
	{
		return impl;
	}
}
