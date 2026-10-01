# 10 — Color-Code Skill Rows by Satisfaction Status

**Epic:** Post-POC / Overlay
**Story Points:** 2
**POC:** No
**Completed:** No

## User Story

As the plugin author, I want skill rows across the overlay panels to be visually color-coded (e.g., a "needs attention" color while deficient, a different color once satisfied), so I can tell status at a glance without reading every number.

## Acceptance Criteria

* Given a skill row is currently deficient (`actual < required`), when rendered, then its line uses a clearly "needs attention" color.
* Given a skill row is satisfied (`actual >= required`), when rendered in the all-skills panel (Story 09), then its line uses a visually distinct "satisfied" color.
* Given color is the primary signal, when choosing the palette, then it should remain distinguishable for common forms of colorblindness (e.g., avoid relying on a pure red/green pair). Exact colors are TBD at implementation time — the actual/required numbers already shown on every line provide a non-color fallback cue, which reduces how much is riding on color choice alone.

## Technical Notes

* Applies to both `ZmanOverlay` (every row shown there is already deficient by definition, so this mostly standardizes a consistent "needs attention" color) and `ZmanAllSkillsOverlay` from Story 09 (where both states appear together, so color actually distinguishes something there).
* Mechanism: `LineComponent.builder()...leftColor(...)/.rightColor(...)` — no structural changes needed, just a color chosen from `actual < required`.
* TBD: exact colors. Starting suggestion is to avoid a pure red/green pair (hardest for the most common forms of colorblindness) — e.g. orange for deficient, a blue-leaning green for satisfied — but this should be confirmed/adjusted when actually implemented and seen on screen.

## Dependencies

* Story 09 — the color distinction is most valuable once both satisfied and unsatisfied rows appear together in the all-skills panel. (Could technically be scoped down to just recoloring `ZmanOverlay`'s existing deficiency rows without Story 09, but every row there is already deficient, so there's no status variation to actually distinguish.)
