package com.sallan95.zman;

import lombok.Value;
import net.runelite.api.Skill;

@Value
public class SkillRequirement
{
	Skill skill;
	int actual;
	int required;
}
