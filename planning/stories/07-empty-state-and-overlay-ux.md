# 07 — Empty-State Polish and Overlay UX

**Epic:** Polish
**Story Points:** 2
**POC:** Yes
**Completed:** Yes (2026-09-30)

## Verification

Confirmed in the live dev client: overlay hides entirely by default when nothing is deficient; the `showAllCaughtUp` toggle shows the "All caught up!" line instead when enabled; repositioning the panel (hold Alt + click-drag — not plain click-drag, corrected from the Story 05 note) works as expected. User confirmed "It all looks good!" — this was the last POC story; the full 7-story POC (planning/BACKLOG.md) is now complete.

## User Story

As the plugin author, I want the overlay to disappear entirely when nothing is deficient, and to stay wherever I drag it, so the tool is unobtrusive during normal play.

## Acceptance Criteria

* Given all tracked skills are currently satisfied (or all PZ levels are 0), when the overlay renders, then nothing is drawn by default (`render()` returns `null`).
* Given the `showAllCaughtUp` config option is enabled, when all tracked skills are satisfied, then a single unobtrusive confirmation line is shown instead of full hiding.
* Given I drag the overlay to a new screen position using RuneLite's built-in overlay-edit mode, when I restart the client, then the overlay reappears in the same position.

## Technical Notes

* `showAllCaughtUp` boolean config item, default `false`
* RuneLite's `OverlayManager` persists a dragged position automatically (`saveOverlay`/`resetOverlay`) — no custom persistence code needed

## Dependencies

* Story 06.
