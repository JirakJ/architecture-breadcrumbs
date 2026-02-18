# Architecture Breadcrumbs - JetBrains Marketplace Listing

## 📝 Plugin Information

**Plugin Name:** Architecture Breadcrumbs
**Plugin ID:** `com.jakubjirak.archbreadcrumbs`
**Version:** 2026.2.11
**Product Code:** PARCHBREADCRMBS
**Category:** Code Tools
**Tags:** architecture, design-patterns, code-documentation, code-navigation, software-architecture, annotations, breadcrumbs

---

## 🎯 Short Description (Max 255 chars)

Code tells you what — Architecture Breadcrumbs tells you why. Embed architectural context in your codebase with rich annotations for decisions, patterns, constraints, and trade-offs.

---

## 📖 Full Description

### Code Tells You What. Architecture Breadcrumbs Tells You Why.

**Architecture Breadcrumbs** is a JetBrains plugin that lets you embed architectural context directly in your codebase. Annotate decisions, patterns, constraints, and trade-offs as structured comments that every developer on your team can discover, navigate, and understand — without leaving the code.

#### 🔥 Why Architecture Breadcrumbs?

**Problem:** Architectural knowledge lives in developers' heads, scattered wikis, or outdated diagrams. When someone asks "why is this a Repository pattern?" or "what constraint led to this design?", the answer is either lost or requires a meeting. Code comments are unstructured and unsearchable.

**Solution:** Architecture Breadcrumbs gives your architectural annotations structure, visibility, and navigability. Drop a `// @arch:pattern=Repository` comment, and it becomes a searchable, cross-linked, syntax-highlighted breadcrumb with gutter icons, inlay hints, and a dedicated tool window — making architectural knowledge a first-class citizen in your codebase.

#### ⚡ Key Features

**📋 6 Breadcrumb Categories**
- 📋 **Decision** — Why this approach was chosen
- 🔧 **Pattern** — Which design pattern is applied
- 🔒 **Constraint** — Technical or business constraints
- 📝 **TODO** — Architectural improvements planned
- ⚠️ **Warning** — Risks and known trade-offs
- 💡 **Context** — Background information and rationale

**🎨 Rich Editor Integration**
- Syntax highlighting with colored backgrounds
- Inlay hints for breadcrumb metadata
- Clickable gutter icons with hover tooltips
- Keyboard shortcut: `Ctrl+Shift+B` to add breadcrumbs

**🔍 Breadcrumb Explorer**
- Dedicated tool window (bottom panel) for browsing all breadcrumbs
- Filter by category, layer, or pattern
- Full-text search across all annotations
- Cross-file navigation — jump to related breadcrumbs instantly

**🌐 Multi-Language Support**
- Java, Kotlin, Python, JavaScript/TypeScript, Go
- Works anywhere code comments are supported

**📁 VCS-Friendly Storage**
- Breadcrumbs are plain code comments — they live in your repo
- Configuration stored in `.arch-breadcrumbs.json` and `.arch-breadcrumbs.yaml`
- Team-shareable via version control

**Breadcrumb Syntax:**
```java
// @arch:pattern=Repository
// @arch:reason=Separates data access from business logic
// @arch:link=ADR-042
// @arch:layer=infrastructure
```

#### 💎 Pricing Model

**FREE (Community):**
- All 6 breadcrumb categories (Decision, Pattern, Constraint, TODO, Warning, Context)
- Syntax highlighting, gutter icons, and inlay hints
- Breadcrumb Explorer tool window with filtering and search
- Cross-file navigation (Go to Related Breadcrumbs)
- Multi-language support (Java, Kotlin, Python, JS/TS, Go)
- Up to 3 custom layers and 20 breadcrumbs per project
- VCS-friendly storage

**PRO ($2.90/month or $29/year):**
- **Architecture Map** — visual tree view: Layer → Pattern → File
- Auto-detection of patterns (Repository, Factory, Observer) and layers from package structure
- Layer consistency checks and architectural violation detection
- Diagram export to **Mermaid** and **PlantUML** formats
- Unlimited layers, patterns, and breadcrumbs
- Cross-linking between breadcrumbs

**TEAM ($5.90/user/month):**
- Everything in Pro
- Team dashboard with architecture coverage metrics
- Team-wide consistency checks across projects
- Centralized architecture overview

#### 🎓 Perfect For

- **New Team Members** understanding architectural context during onboarding
- **Senior Developers** documenting in-code decisions and trade-offs
- **Software Architects** maintaining a birds-eye view of codebase patterns
- **Tech Leads** enforcing architectural consistency across teams
- **Organizations** needing architectural governance and traceability

#### 📦 Requirements

- JetBrains IDE 2024.3+ (IntelliJ IDEA or any IntelliJ-based IDE)
- Java 21+

#### 🚀 Quick Start

1. Install **Architecture Breadcrumbs** from the JetBrains Marketplace
2. Add your first breadcrumb with `Ctrl+Shift+B` or type `// @arch:pattern=Repository`
3. See the gutter icon appear — hover for the tooltip, click to navigate
4. Open the **Architecture Breadcrumbs** tool window (bottom panel) to browse all annotations
5. Configure layers and categories under **Settings → Tools → Architecture Breadcrumbs**

---

## 📸 Screenshots & Media

### Required Screenshots
1. **Breadcrumb annotations** — Editor showing colored `@arch:` comments with gutter icons and inlay hints
2. **Breadcrumb Explorer** — Tool window listing all breadcrumbs with category filters
3. **Cross-file navigation** — Jumping from one breadcrumb to a related breadcrumb in another file
4. **Architecture Map (Pro)** — Visual tree showing Layer → Pattern → File hierarchy
5. **Diagram export (Pro)** — Generated Mermaid or PlantUML diagram of architectural layers
6. **Add breadcrumb dialog** — Keyboard shortcut popup for creating a new breadcrumb
7. **Settings panel** — Configuration for layers, patterns, colors, and filters

---

## 🏷️ Marketplace Categories

**Primary:** Code Tools
**Secondary:** Documentation, Navigation

---

## 🔍 Keywords (SEO)

architecture, breadcrumbs, design patterns, code documentation, architectural annotations, software architecture, code navigation, repository pattern, factory pattern, layer architecture, architectural decisions, code context, onboarding, technical documentation, mermaid, plantuml, architecture map

---

## 📋 Compatibility

**Compatible Products:**
- IntelliJ IDEA (Community & Ultimate)
- WebStorm
- PyCharm
- GoLand
- PhpStorm
- CLion
- Rider
- RubyMine
- Android Studio
- All IntelliJ-based IDEs

**Minimum Version:** 232 (IntelliJ IDEA 2024.3+)

---

## 📞 Vendor Information

**Vendor Name:** Jakub Jirak
**Email:** jirak@jakubjirak.com
**Website:** https://jakubjirak.com

**Support Channels:**
- Email: support@jakubjirak.com
- GitHub Issues: https://github.com/jakubjirak/architecture-breadcrumbs/issues

---

## ✅ Pre-Launch Checklist

- [ ] Plugin icon (SVG, 40x40) uploaded
- [ ] Screenshots (minimum 5) captured and uploaded
- [ ] Short description within 255-character limit
- [ ] Full description formatted with HTML for marketplace rendering
- [ ] Pricing tiers configured in JetBrains Marketplace portal
- [ ] Product code `PARCHBREADCRMBS` registered
- [ ] Vendor profile completed (name, email, website)
- [ ] Support channels documented and active
- [ ] Plugin tested on minimum supported IDE version (2024.3)
- [ ] Plugin tested on latest IDE version
- [ ] Breadcrumb annotations tested across all 5 supported languages
- [ ] Gutter icons and inlay hints verified for all 6 categories
- [ ] Cross-file navigation tested with multi-module projects
- [ ] `.arch-breadcrumbs.json` and `.arch-breadcrumbs.yaml` VCS workflow validated
- [ ] change-notes populated for initial release
- [ ] Privacy policy URL added (if collecting any telemetry)
