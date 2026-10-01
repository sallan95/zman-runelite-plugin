# Zman RuneLite Plugin — Requirements

## What

A RuneLite (RuneScape client) plugin that shows a persistent on-screen overlay listing any RuneScape (RS) skill the player is currently under-leveled in, relative to a personal progression rule tied to Project Zomboid (PZ).

**The rule:** PZ skill level N means the player should have at least RS level `N x 10` (capped at 99) in the equivalent skill before progressing further in PZ. Example: PZ Mining (really: Metalworking) level 1 means the player needs RS Mining level 10.

## Audience

Just the author — a personal-use tool, not intended for the RuneLite Plugin Hub (at least not for the POC).

## Constraints

- No automated way to read PZ data yet — the POC requires manually entering PZ levels into the RuneLite plugin's config panel.
- Overlay must be a **persistent on-screen panel**, not a chat message, toast, or OS notification.
- Overlay must reflect the latest state on **both** config changes and in-game RS skill level-ups, without a manual refresh or plugin restart.
- POC scope only — no external API, no Project Zomboid mod integration, no persistence beyond RuneLite's own config storage.

---

## PZ <-> RS Skill Mapping (confirmed)

Not a clean 1:1 per RS skill — some RS skills draw from several PZ skills (take the max), one PZ skill (Axe) feeds two different RS skills, and several RS skills have no PZ counterpart at all and are intentionally left untracked.

### Direct 1:1 (one PZ config item -> one RS skill requirement)

| RS skill | PZ skill |
|---|---|
| Cooking | Cooking |
| Fishing | Fishing |
| Farming | Farming |
| Strength | Strength |
| Agility | Fitness |
| Mining | Metalworking |
| Smithing | Blacksmithing *(PZ Build 42 — may not exist in the current build yet; config item still works, just reads 0/untracked until that build is available)* |
| Sailing | Mechanics |
| Ranged | Aiming |
| Fletching | Reloading |
| Construction | Carpentry |
| Prayer | First Aid |
| Magic | Electrical |
| Herblore | Foraging |
| Hunter | Trapping |

### Many-to-one aggregates (required level uses the MAX PZ level across a group)

- **RS Crafting** <- `MAX(Tailoring, Crafting[PZ], Glassmaking, Knapping, Pottery)` — 5 separate PZ config items feed one RS requirement.
- **RS Attack** <- `MAX(Axe, Long Blade, Short Blade, Long Blunt, Short Blunt, Spear)` — 6 separate PZ config items feed one RS requirement.
- **RS Woodcutting** <- `Axe` directly (not a max — just the Axe PZ level). This PZ config item is intentionally shared: the same Axe value feeds both the Woodcutting requirement *and* is one of the six inputs to the Attack max.

### Left unmapped (no PZ counterpart, no config item, never appears in the overlay)

Defence, Hitpoints, Firemaking, Thieving, Slayer, Runecraft.

### Formula

`required = min(pzLevel * 10, 99)` — RS levels cap at 99, so a maxed PZ skill (10) requires a maxed RS skill (99), not a literal 100.

### Explicitly out of scope

The reverse direction ("if RS Crafting hits 99, go level a PZ crafting skill to 10") is a personal-discipline rule for while playing PZ, not something RuneLite can check — RuneLite has no visibility into Project Zomboid. Noted for context only, never implemented in this plugin.

---

## Mechanics

- **Config panel**: 26 distinct PZ skill inputs (15 direct + 5 Crafting-family + 6 weapon-family), each an int 0–10, grouped into sections. Six RS skills have no corresponding config item at all (see "left unmapped" above).
- **Overlay**: persistent panel (RuneLite `OverlayPanel`), one line per currently-deficient skill (`actual < required`), draggable and position-persisted. Hidden entirely when nothing is deficient (default), with an opt-in "all caught up" confirmation line.
- **Live triggers**: overlay recomputes on config changes, on RS skill XP/level changes, and on login; clears on logout so the login screen's zeroed skills don't falsely show as deficient.

## Post-POC Notes

- **F2P-only toggle**: filter both config panel and overlay down to free-to-play RS skills only. See `planning/stories/08-f2p-only-toggle.md`.
- **Automated PZ data source**: replace manual config entry with a live feed (a Project Zomboid mod, then possibly an API) once one exists. The POC's `PzLevelSource` interface exists specifically to make this a drop-in addition later — see `planning/decisions.md` ADR-002.
