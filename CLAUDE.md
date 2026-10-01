@AGENTS.md

## Project Overview

A personal-use RuneLite plugin. Shows a persistent overlay listing any RuneScape (RS) skill currently below the level required by a personal Project Zomboid (PZ) progression rule: PZ skill level N requires RS level `min(N * 10, 99)` in the mapped skill. See `planning/requirements.md` for the full PZ<->RS skill mapping (it is not 1:1 — some RS skills aggregate several PZ skills via MAX, several RS skills have no PZ mapping at all).

Full planning history — requirements, clarifying Q&A, architectural decisions, and the story-by-story backlog — lives under `planning/`. Check `planning/reminders.md` first when returning to this project.

## Stack

- Java 11, Gradle (wrapper included — use `./gradlew`, no local Gradle install needed)
- RuneLite client API (`net.runelite:client`, pulled from `https://repo.runelite.net`)
- Lombok (`@Slf4j`, etc.)

## Commands

```bash
./gradlew run    # Launches the real RuneLite client from source in developer mode with this plugin loaded
./gradlew build  # Compile + test
```

## Project Structure

```
src/main/java/com/zman/    # Plugin source
src/test/java/com/zman/    # ZmanPluginTest — the ./gradlew run entry point (not unit tests)
planning/                  # requirements, clarifications, decisions, backlog, stories
```

## Current Architecture (fill in as stories land)

- `ZmanPlugin` — plugin lifecycle, event subscriptions, owns the cached deficiency list via `recompute()` (Story 05+)
- `ZmanConfig` — manual PZ-level config items (Story 02+)
- `PzLevelSource` / `ConfigPzLevelSource` — seam between `recompute()` and the config, so a future automated PZ data source is a new implementation, not a rewrite (Story 03+, see `planning/decisions.md` ADR-002)
- `ZmanOverlay` / `SkillRequirement` — renders the cached deficiency list (Story 04+)
