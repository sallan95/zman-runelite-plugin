# 01 — Dev Environment + Scaffold

**Epic:** Environment
**Story Points:** 2
**POC:** Yes
**Completed:** Yes (2026-09-30)

## User Story

As the plugin author, I want a working RuneLite plugin dev environment and a scaffolded project that loads in the client, so that I can start building real functionality with confidence the toolchain works.

## Acceptance Criteria

* Given JDK 11 and VS Code with the "Extension Pack for Java" installed, when I generate the project from the `runelite/example-plugin` template into `zman-runelite-plugin/` and rename `com.example` -> `com.zman` (`ExamplePlugin` -> `ZmanPlugin`, `ExampleConfig` -> `ZmanConfig`, etc.), then the project structure matches the template with all references renamed.
* Given the renamed project, when I run `./gradlew run`, then the full RuneLite client launches in developer mode with no build errors.
* Given the client is running, when I log in with a real/dev Jagex account, then "Zman" appears in the client's plugin list.
* Given "Zman" is in the plugin list, when I toggle it on and off, then no exceptions appear in the console, and `startUp()`/`shutDown()` debug log lines are visible (`--debug` is already passed by the template's `run` task).

## Verification

Confirmed in the live dev client on 2026-09-30: `./gradlew run` builds clean, "Zman" appears in the plugin list, and toggling it on/off repeatedly produces the expected `Zman started!`/`Zman stopped!` debug log lines each time with no exceptions. No visible in-game effect yet, which is correct — this story is scaffold-only.

## Technical Notes

* Template: https://github.com/runelite/example-plugin
* Gradle 8.10 via wrapper, JDK 11 (Eclipse Temurin recommended by RuneLite docs; local Oracle JDK 11.0.9 confirmed sufficient — no new install needed)
* Update `runelite-plugin.properties` (displayName, author, `plugins=com.zman.ZmanPlugin`) and `build.gradle` group
* Dev loop: `./gradlew run` executes `ExternalPluginManager.loadBuiltin(ZmanPlugin.class); RuneLite.main(args);` from `src/test/java/com/zman/ZmanPluginTest.java`
* See `planning/decisions.md` ADR-004 (template over full client fork) and ADR-005 (VS Code over IntelliJ)
* One-time Jagex account credential setup for auto-login in the dev client — see the "Development" section of the root `README.md`, or https://github.com/runelite/runelite/wiki/Using-Jagex-Accounts

## Dependencies

* None — first story.
