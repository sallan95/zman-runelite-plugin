# 05 — Wire Both Live Triggers Into the Overlay

**Epic:** Core Logic
**Story Points:** 3
**POC:** Yes
**Completed:** Yes (2026-09-30)

## Verification

Confirmed in the live dev client: editing a PZ level in the config panel updated the overlay immediately with no restart ("it is updating"). Logout-clearing uses the same already-verified `GameStateChanged` handler pattern from Story 03/04 but wasn't separately exercised step-by-step.

**Overlay positioning, round 2:** after this story's recompute() changes landed, the user found the `DYNAMIC` + manual `Point(10, 60)` position from Story 04 was overlapping other plugins' overlays. Switched to `OverlayPosition.TOP_RIGHT` (RuneLite's managed, auto-stacking slot below the minimap) — this fixed the overlap and put it in the same neighborhood as the Mining XP tracker. The user asked whether it could pixel-align exactly with another plugin's XP bar; resolved by explaining RuneLite overlays (including anchored ones) support repositioning in the live client (hold Alt, then click and drag), which is the right tool for fine pixel alignment rather than guessing offsets from a screenshot. User confirmed the current position is fine as-is — no further code change needed. See `planning/decisions.md` ADR-007.

## User Story

As the plugin author, I want the overlay to update immediately whenever I change a PZ level or my RS skill changes in-game, so the tool actually reflects reality without restarts.

## Acceptance Criteria

* Given the plugin is running and logged in, when I edit a PZ level in the config panel, then the overlay updates within the same game tick with no plugin restart.
* Given a tracked skill's real level crosses its required threshold (via level-up or ordinary XP gain), when that happens in-game, then the overlay line updates or disappears instantly.
* Given I log out, when the game state changes away from `LOGGED_IN`, then the overlay clears instead of showing false deficiencies (since `getRealSkillLevel` returns 0 pre-login).

## Technical Notes

* Extract `recompute()` as the single source of truth, called from all three triggers below
* `@Subscribe onConfigChanged(ConfigChanged e)` — filter on `event.getGroup().equals("zman")` since this event fires for every plugin's config changes
* `@Subscribe onStatChanged(StatChanged e)` — fires on every XP gain, not just level-ups; cheap enough (~24 int comparisons) to call `recompute()` on every event
* `@Subscribe onGameStateChanged(GameStateChanged e)` — clear the cached deficiency list outside `LOGGED_IN`

## Dependencies

* Story 04.
