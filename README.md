# Zman

A RuneLite plugin that shows a persistent overlay listing any RuneScape skill currently below the level required by a personal Project Zomboid progression rule.

See `planning/requirements.md` for the full rule and skill mapping, and `planning/BACKLOG.md` for the build order.

> **Compatibility note:** the Blacksmithing skill (feeds the RuneScape Smithing requirement) only exists in Project Zomboid **Build 42**. On Build 41, leave that config field at 0 (untracked).

## Development

### One-time: save Jagex login credentials for the dev client

`./gradlew run` launches a fresh, unauthenticated RuneLite client each time — logging in manually every run gets old fast. Do this once so it logs in automatically:

1. Make sure the normal RuneLite launcher is updated to 2.6.3+.
2. Run `RuneLite (configure)` from the Start menu (Windows).
3. In **Client arguments**, add `--insecure-write-credentials`, then Save.
4. Launch RuneLite through the Jagex Launcher as normal and log in once. This writes your session token to `.runelite/credentials.properties` in your user home directory.
5. From now on, `./gradlew run` picks up that credentials file automatically and logs in without prompting.
6. When you're done developing, delete `credentials.properties` (or end the session from your account page on runescape.com) so a stale token isn't sitting on disk.

Full reference: [Using Jagex Accounts](https://github.com/runelite/runelite/wiki/Using-Jagex-Accounts).

### Running

```bash
./gradlew run
```

Launches the real RuneLite client from source in developer mode with this plugin loaded. Once logged in, enable "Zman" in the plugin list.

### Repositioning the overlay

Hold **Alt**, then click and drag the "Zman" panel to move it. RuneLite remembers the new position across restarts.
