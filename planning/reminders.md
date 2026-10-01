# Reminders

Things to come back to when returning to this project.

---

## Current State (as of 2026-09-30)

**The full 7-story POC is done and verified end to end in the live dev client.** This session:
- Researched RuneLite's real plugin APIs (template, config system, overlay system, event bus) — see `decisions.md` and the POC plan this was derived from.
- Confirmed the full PZ <-> RS skill mapping through several rounds of clarification — see `requirements.md` and `clarifications.md`.
- Confirmed local JDK 11 (Oracle, 11.0.9) is already installed and sufficient — no new JDK install needed.
- Confirmed VS Code (not IntelliJ) as the dev environment.
- Created this `planning/` structure.
- Scaffolded `zman-runelite-plugin/` from the `runelite/example-plugin` template, renamed everything to `com.zman`/`ZmanPlugin`, added a `.gitignore`, `git init`'d locally (no commits yet).
- Documented the one-time Jagex-account dev-login credential setup in the root `README.md`.
- Verified in the real client: "Zman" loads, appears in the plugin list, toggles on/off cleanly with correct debug log lines, no errors.
- Added `ZmanConfig` (Fishing + Metalworking pilot fields) — confirmed visible and editable in the config panel.
- Added the `PzLevelSource`/`ConfigPzLevelSource` seam and live skill-level comparison — confirmed correct actual/required numbers logged on login (`FISHING: actual=42, required=99`, `MINING: actual=52, required=0`).
- **Found and fixed a real bug**: reading skill levels directly in the `GameStateChanged` -> `LOGGED_IN` handler returns stale `0`s — RuneLite can fire that event before skill data loads. Fixed by deferring to the first `GameTick` after login. **Carry this pattern into Story 05.**
- Added `SkillRequirement` + `ZmanOverlay` — confirmed the panel renders correctly ("Fishing: 42 / 99", Mining correctly hidden). Had to nudge the overlay's default position down (`setPreferredLocation`) since `DYNAMIC` spawned it on top of the hover-action text.
- Confirmed with the user: config changes do **not** yet update the overlay live — that's expected, since the deficiency list is currently computed once per login (Story 05 fixes this).
- Wired `recompute()` into `ConfigChanged` (filtered to group `zman`), `StatChanged`, and the existing `GameTick`-deferred login path. Confirmed config edits update the overlay immediately in the live client.
- Switched the overlay from `DYNAMIC` to `OverlayPosition.TOP_RIGHT` after it overlapped other plugins' overlays — see ADR-007. User confirmed the resulting position (near the XP tracker) is fine; fine pixel-alignment is a manual reposition (hold Alt, click-drag), not a code concern.
- Built out all 26 PZ config items (13 remaining direct + 5 Crafting-family + 6 weapon-family) across 3 `@ConfigSection`s; implemented the `Skill.CRAFTING`/`Skill.ATTACK` `IntStream.max()` aggregates and the shared Axe-feeds-Woodcutting-and-Attack case in `ConfigPzLevelSource`; extended `ZmanPlugin`'s `recompute()` to cover all 18 mapped skills via `TRACKED_SKILLS`. User tested the Crafting and Weapon groups directly and confirmed correct aggregate behavior.
- Added the `showAllCaughtUp` config toggle and confirmed: overlay hides by default when nothing's deficient, shows "All caught up!" when the toggle is on. Corrected the reposition gesture across all docs: it's **hold Alt + click-drag**, not plain click-drag.
- Added two more backlog stories per user request: **09 — All-skills panel toggle** (a second overlay showing all 26 tracked PZ skills and their RS equivalents, not just deficient ones) and **10 — color-code skill rows by satisfaction status** (red/TBD-colorblind-safe-equivalent until satisfied). Both are Post-POC, not yet built.

**No more mandatory sequential work remains on the original 7-story POC** — it fully satisfies the requirements in `requirements.md`. Four backlog items now exist for whenever work resumes.

---

## Next Session — Start Here

The core POC is done. Backlog order (see `BACKLOG.md` for full scoring):

1. **Story 08 — F2P-only toggle** (ready to build) if playing on F2P worlds.
2. **Story 09 — All-skills panel toggle** (ready to build, with one open question: row grouping by PZ skill (26) vs. RS skill (18) — current lean is by PZ skill).
3. **Story 10 — Color-code satisfaction status** — build after Story 09, since Story 09 is what gives the color distinction something to actually distinguish (every row in today's deficiency-only panel is already deficient).
4. **Automated PZ data source** (mod or API) — not yet refined, no story file exists yet. Start by figuring out how a Project Zomboid mod would actually get data out to this plugin (a local file? a tiny local HTTP server the mod calls? something else?), then write a new `PzLevelSource` implementation per ADR-002 — `ZmanPlugin`/`recompute()` should not need to change.

Also consider: nothing has been committed to git yet (repo was `git init`'d but no commits exist) — worth asking the user whether/when to make a first commit.
