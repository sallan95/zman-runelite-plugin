# 04 — Persistent Overlay With a Static Deficiency List

**Epic:** Overlay
**Story Points:** 3
**POC:** Yes
**Completed:** Yes (2026-09-30)

## Verification

Confirmed in the live dev client: with Fishing at actual=42/required=99, a "Zman" panel appeared showing "Fishing: 42 / 99"; Mining (required=0, untracked) correctly did not appear.

**Found during verification:** the overlay's default `DYNAMIC` spawn position landed right on top of the hover-action text in the top-left of the viewport, making it hard to read. Fixed with `setPreferredLocation(new Point(10, 60))` in the constructor. Confirmed as unobstructed after the fix.

*(Superseded in Story 05: this `DYNAMIC` positioning was later replaced with the `TOP_RIGHT` anchor after it turned out to overlap other plugins' overlays once more of them were visible on screen — see Story 05's verification notes and `decisions.md` ADR-007.)*

## User Story

As the plugin author, I want a persistent on-screen panel listing pilot skills that are currently under-leveled, so I can visually confirm the overlay rendering pipeline works before wiring live updates in Story 05.

## Acceptance Criteria

* Given a pilot skill's real level is below its computed requirement, when the overlay renders, then a panel (e.g. "Mining: 8 / 30") is visible and stays visible while moving the camera or opening game menus.
* Given a pilot skill is at or above its requirement, or its PZ level is 0, when the overlay renders, then that skill does not appear in the list.

## Technical Notes

* `SkillRequirement` value class: skill, actual level, required level
* `ZmanOverlay extends OverlayPanel`; registered/unregistered via `OverlayManager.add()`/`.remove()` in `startUp()`/`shutDown()`
* Build content each `render()` via `PanelComponent` + `TitleComponent` + one `LineComponent` per deficient skill
* `OverlayPosition.DYNAMIC` so it's freely draggable from first run
* The deficiency list is computed once into a cached field for now, reusing Story 03's logic — live recompute triggers (config change, level-up) land in Story 05
* See `planning/decisions.md` ADR-001 (why persistent, not a toast)

## Dependencies

* Story 03.
