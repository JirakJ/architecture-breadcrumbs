# Architecture Breadcrumbs

[![Plugin Verification](https://github.com/JirakJ/architecture-breadcrumbs/actions/workflows/verify.yml/badge.svg)](https://github.com/JirakJ/architecture-breadcrumbs/actions/workflows/verify.yml)
[![Version](https://img.shields.io/badge/version-2026.8.0-blue.svg)](https://github.com/JirakJ/architecture-breadcrumbs/releases)
[![License](https://img.shields.io/badge/license-Apache--2.0-blue)](LICENSE)

> Stop losing architecture decisions — pin them to the lines of code they describe.

## ✨ Features

- **Add Architecture Breadcrumb** — editor popup menu or `Ctrl+Shift+B`; text, category, author and optional links to other breadcrumbs
- **Gutter icons** — one icon per breadcrumb line, tooltip shows the note; toggle per category
- **Breadcrumbs tool window** — all breadcrumbs with category filter and full-text search; double-click to navigate
- **Navigate Linked Breadcrumbs** — Go To menu; jumps to linked breadcrumbs or lets you pick any breadcrumb
- **6 categories** — Decision 📋, Pattern 🔧, Constraint 🔒, TODO 📝, Warning ⚠️, Context 💡
- **VCS-friendly storage** — `.arch-breadcrumbs.json` in the project root; commit it to share with the team
- Works in any file type

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
4. The breadcrumb appears as a gutter icon — hover it to see the note
5. Open **View → Tool Windows → Architecture Breadcrumbs** to browse all breadcrumbs in the project

## ⚙️ Configuration

**Settings → Tools → Architecture Breadcrumbs**

| Setting | Default | Description |
|---------|---------|-------------|
| Enable Architecture Breadcrumbs | `true` | Master toggle for gutter icons and the add/navigate actions |
| Show gutter icons | `true` | Display gutter icons for breadcrumbs |
| Enable Decision category | `true` | Show Decision breadcrumbs |
| Enable Pattern category | `true` | Show Pattern breadcrumbs |
| Enable Constraint category | `true` | Show Constraint breadcrumbs |
| Enable TODO category | `true` | Show TODO (Architecture) breadcrumbs |
| Enable Warning category | `true` | Show Warning breadcrumbs |
| Enable Context category | `true` | Show Context breadcrumbs |


## 🎯 Usage

### Adding Breadcrumbs
Right-click in the editor → **Add Architecture Breadcrumb**, or use the keyboard shortcut. A dialog lets you specify:
- **Text** — the breadcrumb description
- **Category** — Decision, Pattern, Constraint, TODO, Warning, or Context
- **Author** — auto-filled from system user
- **Linked breadcrumbs** — comma-separated IDs for cross-references

### Storage
Breadcrumbs are saved to `.arch-breadcrumbs.json` in the project root (a JSON array with id, text, category,
author, timestamp, filePath, lineNumber and linkedBreadcrumbs). Line numbers are not adjusted automatically
when the surrounding code moves.

### Navigating Breadcrumbs
Use **Go To → Navigate Linked Breadcrumbs** to jump between related breadcrumbs. The Breadcrumbs tool window (bottom panel) shows all breadcrumbs with filtering and search.

## 🔌 Keyboard Shortcuts

| Shortcut | Action |
|----------|--------|
| `Ctrl+Shift+B` | Add Architecture Breadcrumb |

## 📋 Requirements

- IntelliJ IDEA 2024.2+ (build 242+)

## 🏗️ Building from Source

```bash
git clone https://github.com/JirakJ/architecture-breadcrumbs.git
cd architecture-breadcrumbs
./gradlew build
```

## 📝 License

Apache License 2.0 — see [LICENSE](LICENSE) and [NOTICE](NOTICE).

## 👤 Author

**Jakub Jirák** — [@JirakJ](https://github.com/JirakJ)

## Free & open source

Since version 2026.8.0 the plugin is completely free — every feature is unlocked and no license is needed. The source code is open source under the [Apache License 2.0](LICENSE).

## Contributing

Issues and pull requests are welcome at https://github.com/JirakJ/architecture-breadcrumbs.
