# Zman RuneLite Plugin — Prioritized Backlog

Stories are tracked as local markdown files in `planning/stories/` (no external tracker). This file is the canonical build order and reference for returning sessions.

---

## POC Stories (sequential — build order is fixed)

Unlike a typical multi-feature backlog, these aren't independently prioritizable: each story's code is built directly on top of the classes the previous story introduced (e.g., Story 4's overlay reads the cached field Story 3 introduces; Story 5 wires triggers into the `recompute()` Story 3 defines). Build top to bottom.

| Order | Story | Title | Status |
|-------|-------|-------|--------|
| 1 | [01](stories/01-dev-environment-and-scaffold.md) | Dev environment + scaffold loads and does nothing | Done |
| 2 | [02](stories/02-config-panel-pilot-skills.md) | Config panel with two pilot skills | Done |
| 3 | [03](stories/03-read-skill-levels-and-pz-level-source-seam.md) | Read live skill levels, compute required vs. actual (with the future-data-source seam) | Done |
| 4 | [04](stories/04-persistent-overlay-static-list.md) | Persistent overlay with a static deficiency list | Done |
| 5 | [05](stories/05-wire-live-triggers.md) | Wire both live triggers into the overlay | Done |
| 6 | [06](stories/06-full-skill-mapping-config.md) | Expand config to the full PZ <-> RS mapping | Done |
| 7 | [07](stories/07-empty-state-and-overlay-ux.md) | Empty-state polish and overlay UX | Done |

---

## Deferred / Post-POC Backlog

Scoring method (same shape as other projects in this repo): Priority score = **(Value x 2) − Risk − Effort**. Value/Risk on a 1–5 scale, Effort in story points.

| Priority | Story | Title | V | R | E | Score | Needs Refinement |
|----------|-------|-------|---|---|---|-------|-------------------|
| 1 | [08](stories/08-f2p-only-toggle.md) | F2P-only toggle | 2 | 1 | 2 | 1 | No |
| 2 | [09](stories/09-all-skills-panel-toggle.md) | All-skills panel toggle | 3 | 3 | 3 | 0 | Partially — row grouping (by PZ skill vs. by RS skill) still open |
| 3 | [10](stories/10-color-code-skill-satisfaction.md) | Color-code skill rows by satisfaction status | 2 | 1 | 2 | 1 | Yes — exact colors TBD |
| 4 | — | Automated PZ data source (mod or API) | 5 | 5 | 8 | -3 | Yes — no mod/API exists yet, integration shape unknown |

### F2P-only toggle
- Value 2: Minor quality-of-life filter; only matters if the user plays on F2P worlds.
- Risk 1: Straightforward boolean config + a filter predicate; the skill list (F2P vs. members) is fixed and well-known.
- Effort 2
- **Score: (2x2) − 1 − 2 = 1**

### All-skills panel toggle
- Value 3: Real visibility upgrade — see full progression status, not just what's currently failing.
- Risk 3: New overlay class, new per-PZ-skill data model, and an open question on row grouping (26 PZ-skill rows vs. 18 RS-skill rows).
- Effort 3
- **Score: (3x2) − 3 − 3 = 0**

### Color-code skill rows by satisfaction status
- Value 2: Polish — faster at-a-glance reading, but the numbers already convey the same information.
- Risk 1: Just color selection on existing `LineComponent`s, no structural change.
- Effort 2
- **Score: (2x2) − 1 − 2 = 1**

> Story 10 scores the same as Story 08 on paper, but only delivers real value once Story 09 exists — every row in today's `ZmanOverlay` is already deficient by definition, so there's no status variation to color differently yet. Build Story 09 before Story 10 even though Story 09 scores lower.

### Automated PZ data source
- Value 5: This is the actual long-term goal — removes all manual config entry.
- Risk 5: No Project Zomboid mod or API exists yet to feed this; shape of the integration (mod writes a local file? an HTTP call? something else?) is completely undesigned.
- Effort 8 (placeholder — unrefined)
- **Score: (5x2) − 5 − 8 = -3**

> The `PzLevelSource` seam (ADR-002 in `decisions.md`) exists specifically so this story, whenever it's picked up, only requires a new implementation class plus new config items — not a `ZmanPlugin`/`recompute()` rewrite.

---

## Proposed Next Session

**The 7-story POC is complete and verified end to end in the live dev client (2026-09-30).** Next up is whichever deferred backlog item matters more in practice: pick up Story 08 (F2P-only toggle, already refined) if playing on F2P worlds, or start scoping the automated PZ data source once a feed (mod or API) actually exists.
