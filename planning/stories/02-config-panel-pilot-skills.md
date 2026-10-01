# 02 — Config Panel With Two Pilot Skills

**Epic:** Config
**Story Points:** 2
**POC:** Yes
**Completed:** Yes (2026-09-30)

## User Story

As the plugin author, I want a config panel with two pilot PZ skill inputs, so I can validate the config item pattern — including a differently-named PZ skill — before scaling to all 26 in Story 06.

## Acceptance Criteria

* Given `ZmanConfig` with `@ConfigGroup("zman")`, when I open the plugin's config panel, then I see "Fishing" and "Metalworking" numeric inputs.
* Given either input, when I try to enter a value outside 0–10, then the UI enforces the `@Range(min=0, max=10)` bound.
* Given I set values for both, when I restart the RuneLite client, then the values are still there (RuneLite's own config storage — no persistence code needed).

## Verification

Confirmed in the live dev client: the config panel shows "Fishing" and "Metalworking" numeric inputs and both accept edits. (Value persistence across a restart is RuneLite's own config storage mechanism, not plugin code — not re-verified separately here.)

## Technical Notes

* `@ConfigItem(keyName=, name=, description=, section=)` + `@Range(min=0, max=10)` default-method pairs
* Wire via `@Provides ZmanConfig provideConfig(ConfigManager cm)` in `ZmanPlugin`
* The "Metalworking" pilot field deliberately validates the "config label differs from the RS skill it feeds" case (PZ Metalworking -> RS Mining requirement) ahead of Story 06, where most of the 26 items have this property.

## Dependencies

* Story 01.
