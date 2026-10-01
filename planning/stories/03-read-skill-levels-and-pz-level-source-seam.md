# 03 — Read Live Skill Levels, Compute Required vs. Actual (With the Future-Data-Source Seam)

**Epic:** Core Logic
**Story Points:** 3
**POC:** Yes
**Completed:** Yes (2026-09-30)

## Verification

Confirmed in the live dev client: `FISHING: actual=42, required=99` and `MINING: actual=52, required=0`, matching the PZ levels configured (Fishing=10 -> capped at 99; Metalworking=0 -> untracked). `ZmanPlugin` only reads skill data through `PzLevelSource`, never `ZmanConfig` directly.

**Bug found and fixed during verification:** the initial implementation read skill levels directly inside the `GameStateChanged` -> `LOGGED_IN` handler, which returned `actual=0` for both skills — RuneLite can fire that event before skill data is actually loaded. Fixed by deferring the read to the first `GameTick` after reaching `LOGGED_IN` (tracked with a `loggedPilotSkillsThisSession` flag, reset on any non-`LOGGED_IN` state). This pattern should carry forward into Story 05's live-trigger wiring.

## User Story

As the plugin author, I want to compute each pilot skill's required RS level from its configured PZ level and compare it to the live RS level, behind a swappable data-source interface, so the core comparison logic never has to change when a real PZ data feed replaces manual entry later.

## Acceptance Criteria

* Given a `PzLevelSource` interface and its only implementation, `ConfigPzLevelSource`, mapping `Skill.FISHING` and `Skill.MINING` to the Story 02 config fields, when `ZmanPlugin` is wired via Guice `@Provides` (mirroring the existing `ZmanConfig` provider), then `ZmanPlugin` never calls a `ZmanConfig` getter directly — only through `PzLevelSource`.
* Given known PZ level values, when the player logs in (`GameStateChanged` -> `LOGGED_IN`), then the debug console logs the correct actual and required levels for both pilot skills.
* Given a PZ level of 10, when the required level is computed, then it is 99, not a literal 100 (`Math.min(pzLevel * 10, 99)`).

## Technical Notes

* `Client.getRealSkillLevel(Skill skill)` for the actual level (not `getBoostedSkillLevel`, which includes potion effects)
* Inject `Client` into `ZmanPlugin`
* This story only refreshes on login/plugin toggle — full live refresh (config change + level-up triggers) is Story 05, not this one
* See `planning/decisions.md` ADR-002 (the seam) and ADR-003 (the cap formula)

## Dependencies

* Story 02.
