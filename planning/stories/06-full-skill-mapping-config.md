# 06 — Expand Config to the Full PZ <-> RS Mapping

**Epic:** Config
**Story Points:** 5
**POC:** Yes
**Completed:** Yes (2026-09-30)

## Verification

Confirmed in the live dev client: config panel shows all 26 items across the three sections (Direct Skills, Crafting Skills, Weapon Skills) correctly labeled. User tested the Crafting and Weapon Skills groups directly, including setting individual weapon fields and Axe, and confirmed the MAX-aggregate behavior for both Attack and Crafting, and that Axe correctly drives both Woodcutting and Attack.

## User Story

As the plugin author, I want all 26 PZ skill inputs from the confirmed mapping table configured and correctly wired — including the Crafting/Attack aggregates and the shared Axe input — so the plugin covers every skill I actually care about, not just the two pilots.

## Acceptance Criteria

* Given the config panel, when I open it, then I see all 26 PZ inputs grouped into three sections (13 remaining direct skills, 5 Crafting-family, 6 weapon-family) with correct labels wherever the RS and PZ names differ (e.g. "Sailing (PZ: Mechanics)").
* Given any one of the 6 weapon-skill fields is set higher than the others, when `recompute()` runs, then the RS Attack requirement equals that field's level x10 (capped at 99).
* Given the Axe field is set, when `recompute()` runs, then it raises both the Attack max and the Woodcutting requirement.
* Given the 6 unmapped RS skills (Defence, Hitpoints, Firemaking, Thieving, Slayer, Runecraft), when `recompute()` runs, then they never appear in the overlay, since `ConfigPzLevelSource` always returns 0 for them.
* Given a few minutes of fast-XP training on a tracked skill, when `StatChanged` fires repeatedly, then there is no noticeable performance impact.

## Technical Notes

* Full mapping table: see `planning/requirements.md`
* In `ConfigPzLevelSource`: `Skill.CRAFTING` and `Skill.ATTACK` return `Math.max(...)` across their respective PZ skill groups; `Skill.WOODCUTTING` returns the same Axe field used inside the Attack max
* Apply Story 02's `@ConfigItem` + `@Range` pattern once per remaining PZ skill, grouped under `@ConfigSection` (13 direct-skill items in one section, 5 Crafting-family items in a second, 6 weapon-family items in a third)

## Dependencies

* Story 05.
