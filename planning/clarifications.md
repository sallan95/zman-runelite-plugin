# Zman RuneLite Plugin — Planning Clarifications

Q&A from the initial planning conversation.

---

**Q1: What is PZ, and how does a PZ skill level translate into a required RuneScape level?**

PZ = Project Zomboid, a separate game with its own per-skill levels capped at 10. Initial example: PZ Mining level 1 means RS Mining needs to be at least level 10.

---

**Q2: When should RuneLite check the RS skill level against the configured requirement and show the popup?**

Both — on a config change (user edits a PZ level) and whenever the RS skill changes in-game (level-up / XP gain).

---

**Q3: What should the in-client notification look like?**

A persistent on-screen overlay, not a chat message, toast, or OS notification.

---

**Q4: Where should this project live?**

A new folder in the existing `programming/` directory (later revised to nest under a `zman/` parent — see Q16).

---

**Q5: How should a PZ skill level become a "required RS level"?**

Formula: `required = pzLevel * 10`. Not a manual per-skill table, not a raw RS-level entry — the plugin computes it from the PZ level.

---

**Q6: Should the overlay be a persistent list or a transient toast?**

Persistent list (recommended and chosen) — an always-visible panel listing every skill currently below its requirement. This naturally satisfies "update on config change" and "update on level-up" without separate popup/toast logic, since it just reflects current state every render.

---

**Q7: Full PZ <-> RS skill mapping — round 1**

Initial guesses were offered for skills without an obvious same-named PZ counterpart. Confirmed/corrected:
- Ranged -> Aiming
- Fletching -> Reloading
- Smithing -> Blacksmithing (PZ Build 42 — not yet released as of this writing)
- Mining -> Metalworking (correcting an earlier wrong assumption that PZ has a literal "Mining" skill — the original example's "PZ mining" was describing the Metalworking activity colloquially)
- Crafting -> aggregate of Tailoring, Crafting, Glassmaking, Knapping, Pottery (see Q8 for the exact rule)
- Woodcutting -> Axe
- Construction -> Carpentry
- Sailing -> Mechanics (confirmed earlier, before this round)
- Attack should include the weapon skills (Axe, Long Blade, Short Blade, Long Blunt, Short Blunt, Spear) via the same aggregate pattern as Crafting.

---

**Q8: Confirm the Crafting and Attack aggregation rule, and the shared Axe input**

- Crafting required level = `MAX(Tailoring, Crafting[PZ], Glassmaking, Knapping, Pottery) * 10` (capped at 99).
- Attack required level = `MAX(Axe, Long Blade, Short Blade, Long Blunt, Short Blunt, Spear) * 10` (capped at 99).
- Confirmed intentional: the same PZ Axe level feeds both the Woodcutting requirement directly *and* is one of the six inputs to the Attack max.

---

**Q9: RS caps at 99, but PZ level 10 x 10 = 100 — how should that be handled?**

Confirmed: PZ level 10 is equivalent to RS level 99 (the true max), not a literal 100. Formula is `min(pzLevel * 10, 99)`.

---

**Q10: The "reverse rule" — if RS Crafting hits 99, level up a PZ skill to match — should this be a plugin feature?**

No. Correctly identified as a personal-discipline reminder for while playing PZ, not something RuneLite can check (no visibility into Project Zomboid). Documented for context only; not implemented.

---

**Q11: What about Defence and Hitpoints?**

Intentionally untracked — no PZ equivalent, no config item.

---

**Q12: Remaining "no PZ equivalent" skills?**

- Prayer -> First Aid
- Magic -> Electrical
- Herblore -> Foraging
- Firemaking, Thieving, Slayer, Runecraft stay unmapped (no PZ equivalent), as originally assumed.

---

**Q13: Hunter and Agility — never explicitly confirmed in earlier rounds**

- Hunter -> Trapping (confirmed).
- Agility -> Fitness (chosen over the offered Nimble/Sprinting/Lightfooted guesses).

---

**Q14: Future scope — anything to flag for later?**

After the POC, add an F2P-only toggle to filter the config panel and overlay down to free-to-play RS skills. Captured as a deferred backlog item, not part of the POC story sequence. See `planning/BACKLOG.md` and `planning/stories/08-f2p-only-toggle.md`.

---

**Q15: Which JDK version, and IntelliJ vs. VS Code?**

- JDK 11 specifically (RuneLite targets `release 11`); Eclipse Temurin is RuneLite's documented recommendation, but the existing local Oracle JDK 11.0.9 install is equally fine functionally — no need to install a second JDK 11.
- IntelliJ is not required — this is a plain Gradle project. VS Code with the "Extension Pack for Java" (bundles Gradle for Java support) works the same way; the dev loop is the same `./gradlew run` task either way.

---

**Q16: Project folder structure and story tracking**

- Nest the plugin under a `zman/` parent directory (the broader personal project this plugin is part of): `programming/zman/zman-runelite-plugin/`.
- No Linear team for this project — stories are tracked as local markdown files under `planning/stories/` only (no "Linear:" field, unlike phillies-stadium-arg).
