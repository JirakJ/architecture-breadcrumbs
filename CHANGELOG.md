# Changelog

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
