# Changelog

## [Unreleased]

### Fixed
- Gutter icon was added once per token on a breadcrumb line; now exactly one icon per breadcrumb.
- New breadcrumbs show their gutter icon immediately (highlighting is restarted after adding).
- Tool window updates from background store loads now happen on the EDT.
- A hand-edited `.arch-breadcrumbs.json` with missing fields or an unknown category no longer causes
  NullPointerExceptions; invalid entries are skipped and optional fields defaulted.
- Files in a sibling directory whose name starts with the project path (e.g. `/proj2` vs `/proj`)
  were stored with a broken relative path.
- Empty breadcrumb text is now reported in the dialog instead of silently ignoring OK.
- Minimum IDE raised to 2024.2 (build 242): the plugin is compiled to Java 21 bytecode, which the
  JBR 17 runtime of IDEs 2023.2–2024.1 cannot load.

### Removed
- Non-functional "Icon style" setting.
- Description/README claims for features that never shipped (`@arch:` comment syntax, syntax
  highlighting, inlay hints, architecture map, auto-detection, layer checks, Mermaid/PlantUML export,
  team dashboard, Go-specific support, `.arch-breadcrumbs.yaml`), unused git helper classes, the unused
  message bundle, the empty Git4Idea optional dependency and the leftover ProGuard obfuscation task.

## [2026.8.0] - 2026-11-10

### Changed
- The plugin is now completely free — all features unlocked, no license needed.
- Source code is now open source under Apache-2.0: https://github.com/JirakJ/architecture-breadcrumbs

## [2026.3.6] - 2026-04-18

### Changed
- Enabled freemium licensing via `<product-descriptor optional="true">`. Plugin remains fully functional without a license; premium features may be introduced in future minor releases.


## [2026.2.6] - 2026-02-14

### Fixed
- Removed deprecated API usage (postStartupActivity → projectActivity, StatusBarWidget.getPresentation)
- No experimental API usage


## [2026.2.5] - 2026-02-13

### Added
- Added ProGuard obfuscation for code protection


## [2026.2.4] - 2026-02-13

### Changed
- Version bump for JetBrains Marketplace publication


## [2026.2.3] - 2026-02-13

### Changed
- Expanded compatibility range: sinceBuild 232.0+ (IntelliJ 2023.2+)
- Removed upper version bound for future IDE compatibility


## [2026.2.2] - 2026-02-13

### Added
- Initial release
- Breadcrumb annotation syntax (`// @arch:pattern=`, `// @arch:reason=`, `// @arch:link=`, `// @arch:layer=`)
- 6 breadcrumb categories: Decision, Pattern, Constraint, TODO, Warning, Context
- Editor visualization with colored backgrounds, gutter icons, and inlay hints
- Breadcrumbs tool window for browsing all project breadcrumbs
- Add Architecture Breadcrumb action with dialog (text, category, author, linked breadcrumbs)
- Navigate Linked Breadcrumbs action for jumping between related breadcrumbs
- Configurable settings: enable/disable plugin, gutter icons, icon style, and individual categories
- Multi-language support: Java, Kotlin, Python, JavaScript/TypeScript, Go
- Git integration for automatic author detection via git blame
- Cross-linking between breadcrumbs
- Architecture Map tool window
- Auto-detection of patterns and layers from package structure
- Layer consistency checks
- Export to Mermaid and PlantUML
- Unlimited layers and breadcrumbs
- Team dashboard with architecture overview
- `.arch-breadcrumbs.yaml` project configuration support
- Notification support via balloon notifications
- Background indexation with breadcrumb parsing under 100ms
