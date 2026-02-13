# Changelog

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
- Cross-linking between breadcrumbs (Pro)
- Architecture Map tool window (Pro)
- Auto-detection of patterns and layers from package structure (Pro)
- Layer consistency checks (Pro)
- Export to Mermaid and PlantUML (Pro)
- Unlimited layers and breadcrumbs (Pro)
- Team dashboard with architecture overview (Team)
- `.arch-breadcrumbs.yaml` project configuration support
- Notification support via balloon notifications
- Background indexation with breadcrumb parsing under 100ms
