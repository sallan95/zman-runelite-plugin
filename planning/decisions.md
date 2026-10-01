# Architectural Decisions

---

## ADR-001 — Persistent overlay instead of chat message, toast, or OS notification

**Date:** 2026-09-30
**Status:** Decided
**Story:** POC (all stories)

### Decision

Show skill deficiencies as a persistent, always-visible `OverlayPanel`, not a transient toast, chat message, or OS-level notification.

### Context

The plugin needs to reflect the latest deficiency state whenever either of two independent triggers fires: a config change (user edits a PZ level) or an in-game RS skill change (XP gain / level-up).

### Reasons

- A persistent panel that recomputes and redraws every frame from current state automatically satisfies both triggers with a single mechanism — there's no need to build separate "show a toast on trigger A" and "show a toast on trigger B" logic, and no risk of a toast being missed while alt-tabbed or looking elsewhere on screen.
- Matches how other RuneLite overlays (XP tracker, boss timers) behave, so it's a familiar interaction pattern for the user.

### Tradeoffs

- A persistent panel takes up permanent screen real estate while any skill is deficient. Mitigated by hiding it entirely (`render()` returns `null`) when nothing is deficient (see the empty-state story).

---

## ADR-002 — `PzLevelSource` seam to decouple skill-level lookup from config

**Date:** 2026-09-30
**Status:** Decided
**Story:** Story 3 (seam introduced), all later stories depend on it

### Decision

Introduce a `PzLevelSource` interface (`int getPzLevel(Skill skill)`) between `ZmanPlugin`'s `recompute()` logic and the actual PZ level values. The POC ships exactly one implementation, `ConfigPzLevelSource`, backed entirely by manually-entered `ZmanConfig` fields.

### Context

The user's stated long-term direction is to replace manual config entry with an automated feed — first a Project Zomboid mod, later possibly an API — once one exists. That is explicitly out of scope for the POC, but the POC's core logic (`recompute()`) shouldn't need to change shape when that day comes.

### Reasons

- `recompute()` only ever calls `getPzLevel(skill)` — it has no idea whether the answer came from a config field, a max-aggregate over several config fields, or (later) a live feed.
- All the mapping complexity (direct lookups, the Crafting/Attack MAX aggregates, the shared Axe input, the six always-0 unmapped skills) lives in one place: `ConfigPzLevelSource`. Adding a second `PzLevelSource` implementation later is additive, not a rewrite.

### Tradeoffs

- One extra interface + implementation class for what could, in the POC alone, just be a method on `ZmanPlugin`. Accepted deliberately — this is the one piece of "build for the future" the user explicitly asked for, everywhere else the design stays minimal.

---

## ADR-003 — Required level formula: `min(pzLevel * 10, 99)`

**Date:** 2026-09-30
**Status:** Decided
**Story:** Story 3

### Decision

`required RS level = min(pzLevel * 10, 99)`, not a literal `pzLevel * 10`.

### Context

PZ skills cap at level 10; RS skills cap at level 99. A literal `10 * 10 = 100` would make a maxed PZ skill an unreachable RS requirement.

### Reasons

- The user confirmed PZ level 10 should be treated as equivalent to RS level 99 (the true RS cap), not 100.
- `Math.min(pzLevel * 10, 99)` implements this with no special-casing needed elsewhere — for PZ levels 0–9 the multiplication alone is already ≤ 90, well under the cap, so the `min` only ever bites at the top end.

---

## ADR-004 — Scaffold from `runelite/example-plugin`, not a fork of the full client

**Date:** 2026-09-30
**Status:** Decided
**Story:** Story 1

### Decision

Generate the project from the official `runelite/example-plugin` GitHub template. Do not fork or clone the entire `runelite/runelite` client repository.

### Context

RuneLite supports two development models: forking the whole client (heavy, meant for contributing to RuneLite core itself) and building an independent plugin project that pulls the client as a Gradle dependency (`net.runelite:client:latest.release`) and runs it via a small test-class launcher in developer mode.

### Reasons

- The independent-plugin model is what `example-plugin` exists for, is far lighter (no need to build/maintain the entire client codebase), and is the officially documented path for exactly this use case.
- `./gradlew run` boots the real, current RuneLite client with the plugin registered — full fidelity for manual testing — without any of the client's own source in this repo.

---

## ADR-005 — VS Code instead of IntelliJ IDEA

**Date:** 2026-09-30
**Status:** Decided
**Story:** Story 1

### Decision

Use VS Code with the "Extension Pack for Java" (Microsoft) for local development, rather than IntelliJ IDEA as the RuneLite wiki's guide assumes.

### Context

The RuneLite "Building with IntelliJ IDEA" wiki page is IntelliJ-specific documentation, but the underlying project is a plain Gradle project with no IDE-specific configuration.

### Reasons

- Nothing about `example-plugin`'s `build.gradle` or the `ExternalPluginManager.loadBuiltin(...)` dev-mode launcher is IntelliJ-specific.
- VS Code's Java extension pack provides equivalent Gradle task running and debugging; the user already has a VS Code-centric workflow for other projects in this repo.

### Tradeoffs

- Slightly more manual Gradle task discovery than IntelliJ's one-click "Run" gutter icon for the generated test class. Not a functional blocker.

---

## ADR-006 — Defer post-login skill reads to the first `GameTick`, not `GameStateChanged`

**Date:** 2026-09-30
**Status:** Decided
**Story:** Story 3 (found during verification)

### Decision

Never read `Client.getRealSkillLevel(...)` directly inside a `GameStateChanged` handler at the moment it transitions to `LOGGED_IN`. Instead, set a flag and do the read on the next `GameTick` while `client.getGameState() == GameState.LOGGED_IN`.

### Context

Story 3's first implementation read skill levels directly in the `GameStateChanged` handler and consistently logged `actual=0` for skills that were actually in the 40s/50s on the real account. RuneLite can fire `GameStateChanged(LOGGED_IN)` before the client has finished populating skill data internally.

### Reasons

- Waiting one tick (0.6s) is imperceptible to the user and guarantees skill data is actually loaded.
- A boolean flag (set on first qualifying tick, cleared on any non-`LOGGED_IN` state) keeps this a one-shot check per login rather than running every tick indefinitely.

### Carries forward to

Story 05's live triggers. `StatChanged` itself is not affected (it only fires once real data actually changes), but any logic that reads current skill state in direct response to a `GameStateChanged` transition needs this same one-tick deferral.

---

## ADR-007 — Overlay anchored `TOP_RIGHT`, not freely-positioned `DYNAMIC`

**Date:** 2026-09-30
**Status:** Decided
**Story:** Story 04 (first attempt), revised in Story 05

### Decision

`ZmanOverlay` uses `OverlayPosition.TOP_RIGHT` (RuneLite's managed, auto-stacking slot below the minimap), not `OverlayPosition.DYNAMIC` with a manually-chosen default point.

### Context

Story 04 originally used `DYNAMIC` with `setPreferredLocation(new Point(10, 60))` to dodge the top-left hover-action text. That worked in isolation, but once other plugins' overlays were also on screen, the free-floating `DYNAMIC` position overlapped them — `DYNAMIC` overlays don't participate in RuneLite's automatic collision avoidance the way anchored positions do.

### Reasons

- `TOP_RIGHT` is a managed zone: RuneLite auto-stacks every overlay anchored there (by priority) so they don't overlap each other, which is exactly the problem that needed solving.
- It naturally lands near other skill-related overlays (e.g., the XP tracker), which is a sensible neighborhood for this plugin's content.

### Tradeoffs

- Less precise placement control than `DYNAMIC` — can't pixel-align with another plugin's overlay purely from code. Resolved by relying on RuneLite's built-in reposition gesture (**hold Alt, then click and drag** — available even for anchored overlays) for any fine-tuning the user wants; confirmed with the user that the default `TOP_RIGHT` placement was good enough without needing exact alignment.
