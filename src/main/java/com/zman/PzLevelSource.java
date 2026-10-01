package com.zman;

import net.runelite.api.Skill;

/**
 * Where PZ skill levels come from. The POC's only implementation reads manually-entered
 * config values; a later implementation can read from an automated Project Zomboid data
 * feed without ZmanPlugin's comparison logic needing to change.
 */
public interface PzLevelSource
{
	int getPzLevel(Skill skill);
}
