# 09 — All-Skills Panel Toggle

**Epic:** Post-POC / Overlay
**Story Points:** 3
**POC:** No
**Completed:** No

## User Story

As the plugin author, I want an optional second overlay panel that lists every one of my 26 tracked PZ skills alongside the OSRS skill(s) they feed, so I can see my full progression status at a glance — not just what I'm currently behind on.

## Acceptance Criteria

* Given a new `showAllSkillsPanel` config toggle (default `false`), when enabled, then a second overlay panel appears listing all 26 configured PZ skills, each showing: PZ skill name, PZ level, the OSRS skill(s) it feeds, and that OSRS skill's actual/required levels.
* Given a PZ skill that feeds a shared aggregate (the Crafting-family or weapon-family group), when displayed, then the panel indicates which RS skill(s) it contributes to (e.g., Axe shows both Woodcutting and Attack).
* Given the toggle is off (default), when the overlay renders, then only the existing deficiency-only panel shows, unchanged from Story 07.
* Given both panels are visible at once, when positioned, then they don't overlap each other.

## Technical Notes

* New class, e.g. `ZmanAllSkillsOverlay extends OverlayPanel`, separate from `ZmanOverlay`. Add/remove it via `OverlayManager` when the `showAllSkillsPanel` toggle flips (`ConfigChanged`), not just at plugin `startUp()`/`shutDown()`.
* `SkillRequirement` currently only models already-deficient RS skills (one row per RS skill). This panel needs a richer per-PZ-skill view — likely a new value class, e.g. `PzSkillStatus` (PZ skill name, PZ level, RS skill(s) fed, actual, required), separate from `SkillRequirement`.
* **Open design question to resolve at implementation time:** is this panel organized by the 26 PZ config items, or by the 18 RS skills (collapsing aggregate groups into one row each)? Current recommendation is by PZ skill (26 rows), since the ask was "all my PZ skills."
* Reuse `ConfigPzLevelSource`'s existing mapping logic rather than duplicating it — it already knows the full PZ-skill-to-RS-skill relationships.

## Dependencies

* Story 07 (full POC).
