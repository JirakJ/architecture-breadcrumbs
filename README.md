# Architecture Breadcrumbs

[![Build](https://github.com/JirakJ/architecture-breadcrumbs/actions/workflows/build.yml/badge.svg)](https://github.com/JirakJ/architecture-breadcrumbs/actions/workflows/build.yml)
[![Version](https://img.shields.io/badge/version-2026.2.2-blue.svg)](https://github.com/JirakJ/architecture-breadcrumbs/releases)
[![License](https://img.shields.io/badge/license-MIT-green.svg)](LICENSE)

> Stop Losing Architecture Decisions — Embed Them in Your Code

## ✨ Features

### Free Tier
- **Breadcrumb annotations** — leave `// @arch:pattern=`, `// @arch:reason=`, `// @arch:link=`, `// @arch:layer=` comments in code
- **Syntax highlighting** with colored backgrounds and inlay hints
- **Gutter icons** — visual markers for each breadcrumb category (Decision, Pattern, Constraint, TODO, Warning, Context)
- **Navigation** — Go to Related Breadcrumbs action
- **Breadcrumbs tool window** — browse all breadcrumbs in your project
- **Up to 3 custom layers** and **20 breadcrumbs**
- **6 breadcrumb categories** — Decision 📋, Pattern 🔧, Constraint 🔒, TODO 📝, Warning ⚠️, Context 💡
- **Multi-language support** — Java, Kotlin, Python, JavaScript/TypeScript, Go

### Pro Tier ($2.90/month or $29/year)
- **Architecture Map** tool window — visual overview of architectural structure
- **Auto-detection** of patterns and layers from package structure
- **Layer consistency checks**
- **Export** to Mermaid and PlantUML diagrams
- **Unlimited layers and breadcrumbs**
- **Cross-linking** between breadcrumbs

### Team Tier ($5.90/user/month)
- All Pro features
- **Team dashboard** with architecture overview
- **Consistency checks** across the team

## 📦 Installation

### From JetBrains Marketplace
1. Open **Settings → Plugins → Marketplace**
2. Search for **"Architecture Breadcrumbs"**
3. Click **Install**

### From ZIP
1. Download the latest release from [Releases](https://github.com/JirakJ/architecture-breadcrumbs/releases)
2. Open **Settings → Plugins → ⚙️ → Install Plugin from Disk**
3. Select the downloaded ZIP file

## 🚀 Quick Start

1. Open any source file in your project
2. Right-click in the editor and select **"Add Architecture Breadcrumb"** (or press `Ctrl+Shift+B`)
3. Fill in the breadcrumb text, select a category, and optionally link to other breadcrumbs
4. The breadcrumb appears as a gutter icon — click it to view details
5. Open **View → Tool Windows → Breadcrumbs** to browse all breadcrumbs in the project

## ⚙️ Configuration

**Settings → Tools → Architecture Breadcrumbs**

| Setting | Default | Description |
|---------|---------|-------------|
| Enable Architecture Breadcrumbs | `true` | Master toggle for the plugin |
| Show gutter icons | `true` | Display gutter icons for breadcrumbs |
| Icon style | `Default` | Icon style selection |
| Enable Decision category | `true` | Show Decision breadcrumbs |
| Enable Pattern category | `true` | Show Pattern breadcrumbs |
| Enable Constraint category | `true` | Show Constraint breadcrumbs |
| Enable TODO category | `true` | Show TODO (Architecture) breadcrumbs |
| Enable Warning category | `true` | Show Warning breadcrumbs |
| Enable Context category | `true` | Show Context breadcrumbs |

You can also configure the plugin via `.arch-breadcrumbs.yaml` in your project root for color schemes, filters, and team settings.

## 🎯 Usage

### Adding Breadcrumbs
Right-click in the editor → **Add Architecture Breadcrumb**, or use the keyboard shortcut. A dialog lets you specify:
- **Text** — the breadcrumb description
- **Category** — Decision, Pattern, Constraint, TODO, Warning, or Context
- **Author** — auto-filled from system user
- **Linked breadcrumbs** — comma-separated IDs for cross-references (Pro)

### Breadcrumb Syntax
```java
// @arch:pattern=Repository
// @arch:reason=Separates data access from business logic
// @arch:link=ADR-042
// @arch:layer=infrastructure
```

### Navigating Breadcrumbs
Use **Go To → Navigate Linked Breadcrumbs** to jump between related breadcrumbs. The Breadcrumbs tool window (bottom panel) shows all breadcrumbs with filtering and search.

## 🔌 Keyboard Shortcuts

| Shortcut | Action |
|----------|--------|
| `Ctrl+Shift+B` | Add Architecture Breadcrumb |

## 📋 Requirements

- IntelliJ IDEA 2024.3+ (build 243+)
- Java 21+

## 🏗️ Building from Source

```bash
git clone https://github.com/JirakJ/architecture-breadcrumbs.git
cd architecture-breadcrumbs
./gradlew build
```

## 📝 License

MIT License — see [LICENSE](LICENSE)

## 👤 Author

**Jakub Jirák** — [@JirakJ](https://github.com/JirakJ)
