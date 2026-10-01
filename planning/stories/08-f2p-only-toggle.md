# 08 — F2P-Only Toggle

**Epic:** Post-POC
**Story Points:** 2
**POC:** No
**Completed:** No

## User Story

As the plugin author, I want to optionally filter the config panel and overlay down to only free-to-play RS skills, so the tool stays relevant if I'm playing on an F2P account/world.

## Acceptance Criteria

* Given the `f2pOnly` config toggle is enabled, when I open the config panel, then only F2P-eligible skill inputs are shown (Attack, Defence, Strength, Hitpoints, Ranged, Prayer, Magic, Cooking, Woodcutting, Fletching, Fishing, Firemaking, Crafting, Smithing, Mining).
* Given `f2pOnly` is enabled, when the overlay renders, then members-only skills are excluded from the deficiency list even if they'd otherwise be deficient.
* Given `f2pOnly` is disabled (default), when the config panel and overlay render, then behavior is unchanged from Story 07.

## Technical Notes

* Deferred until after the POC (Stories 01–07) is validated in real play — see `planning/BACKLOG.md` for scoring/rationale
* The F2P skill list is fixed and well-known; no external lookup needed

## Dependencies

* Story 07 (all POC stories).
