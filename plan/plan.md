# Architecture Breadcrumbs — Implementační plán

**Verze:** 2026.2.1
**Status:** Připraveno k implementaci

---

## Fáze 0: Plugin Ikona & Branding

- [ ] Navrhnout plugin ikonu (SVG + PNG)
  - [ ] Koncept: 🏗️ Drobečková navigace / blueprint
  - [ ] Hlavní ikona: 40×40 SVG pro Marketplace listing
  - [ ] Toolbar ikona: 16×16 + 13×13 SVG (light + dark varianta)
  - [ ] Tool window ikona: 13×13 SVG
  - [ ] `src/main/resources/META-INF/pluginIcon.svg` (40×40, light theme)
  - [ ] `src/main/resources/META-INF/pluginIcon_dark.svg` (40×40, dark theme)
  - [ ] Action ikony pro toolbar/menu (16×16 SVG, light + dark)
- [ ] Barevná paleta konzistentní s DX brandem
- [ ] Marketplace banner obrázek (1280×800)
- [ ] Screenshoty pro Marketplace listing (min 3, 1280×800 PNG)

## Fáze 1: Projekt scaffolding

- [ ] Inicializovat IntelliJ Platform Plugin projekt (Gradle Kotlin DSL)
  - [ ] `build.gradle.kts` — IntelliJ Platform 2024.3+, JVM 17
  - [ ] `settings.gradle.kts`, `gradle.properties` (verze `2026.2.1`)
  - [ ] Gradle wrapper (8.x)
- [ ] `src/main/resources/META-INF/plugin.xml`
  - [ ] Plugin ID: `com.jakubjirak.archbreadcrumbs`
  - [ ] Závislosti: `com.intellij.modules.platform`, `com.intellij.modules.lang`
- [ ] Balíčková struktura: `com.jakubjirak.archbreadcrumbs.*`
  - [ ] `.model` — datové třídy
  - [ ] `.parser` — breadcrumb komentáře parser
  - [ ] `.index` — file-based index
  - [ ] `.navigation` — Go to Related, contributors
  - [ ] `.ui` — annotator, tool window, gutter
  - [ ] `.detection` — auto-detection patterns
  - [ ] `.settings` — konfigurace
- [ ] `src/main/resources/messages/ArchBreadcrumbsBundle.properties`
- [ ] `.gitignore` (build/, .idea/, .gradle/, .kotlin/, out/)
- [ ] `gradle/libs.versions.toml` — version catalog
- [ ] `LICENSE` (Apache-2.0)
- [ ] `.editorconfig` (Kotlin style conventions)
- [ ] Detekt konfigurace (`detekt.yml`)
- [ ] GitHub Actions CI workflow (`.github/workflows/build.yml`)
- [ ] Ověřit `./gradlew build`

## Fáze 2: Datový model

- [ ] `Breadcrumb.kt` — data class
  - [ ] `file: VirtualFile`, `offset: Int`, `length: Int`
  - [ ] `pattern: String?` (Repository, Factory, Observer, ...)
  - [ ] `layer: String?` (presentation, business, infrastructure)
  - [ ] `reason: String?`
  - [ ] `link: String?` (ADR reference nebo URL)
  - [ ] `tags: List<String>`
- [ ] `ArchLayer.kt` — data class (name, color, description)
- [ ] `ArchPattern.kt` — data class (name, description, icon)
- [ ] `BreadcrumbSyntax.kt` — konstanty syntaxe (`@arch:pattern=`, `@arch:layer=`, etc.)
- [ ] Unit testy

## Fáze 3: Breadcrumb Parser

- [ ] `BreadcrumbParser.kt`
  - [ ] `fun parse(comment: PsiComment): Breadcrumb?`
  - [ ] Rozpoznání `// @arch:` prefixu
  - [ ] Parsování key=value párů: `pattern=X`, `layer=Y`, `reason="Z"`, `link=ADR-042`
  - [ ] Podpora multi-line breadcrumbs (consecutive `// @arch:` lines)
  - [ ] Validace: známé keys, povinné aspoň jedno z pattern/layer
- [ ] `BreadcrumbPsiVisitor.kt`
  - [ ] Prochází PSI strom souboru, vyhledává komentáře s `@arch:` prefixem
  - [ ] Jazykově agnostické — využívá `PsiComment` interface
- [ ] Unit testy pro každý formát (single-line, multi-line, with quotes, with link)
- [ ] Edge case testy (malformed, partial, nested comments)

## Fáze 4: File-Based Index

- [ ] `BreadcrumbIndex.kt` extends `FileBasedIndexExtension`
  - [ ] Indexer: extrahuje breadcrumbs z komentářů při indexaci souboru
  - [ ] Key: pattern/layer, Value: breadcrumb metadata
  - [ ] Umožňuje rychlý lookup: „najdi všechny breadcrumbs s pattern=Repository"
- [ ] `BreadcrumbIndexUtil.kt`
  - [ ] `fun findByPattern(project: Project, pattern: String): List<Breadcrumb>`
  - [ ] `fun findByLayer(project: Project, layer: String): List<Breadcrumb>`
  - [ ] `fun findAll(project: Project): List<Breadcrumb>`
- [ ] Unit testy (index test fixtures)

## Fáze 5: Editor Annotator

- [ ] `BreadcrumbAnnotator.kt` implements `Annotator`
  - [ ] Registrace v `plugin.xml`
  - [ ] Zvýrazněný pozadí blok pro breadcrumb komentáře (barva dle layer)
  - [ ] Bold/italic pro key=value páry
  - [ ] Custom `TextAttributesKey` pro breadcrumb syntax
- [ ] Color scheme contributions pro light/dark themes
- [ ] `BreadcrumbHighlightingColors.kt` — definice barev per layer

## Fáze 6: Gutter ikony

- [ ] `BreadcrumbLineMarkerProvider.kt` implements `LineMarkerProvider`
  - [ ] Ikona 🏗️ u breadcrumb komentáře
  - [ ] Tooltip: pattern, layer, reason (zkráceně)
  - [ ] Click action: navigace na related breadcrumbs
- [ ] SVG ikony pro každý layer (konfigurovatelné)

## Fáze 7: Navigace

- [ ] `GotoRelatedBreadcrumbsAction.kt` — action (Ctrl+Shift+B)
  - [ ] Registrace v `plugin.xml` s keyboard shortcutem
  - [ ] Popup list: všechny breadcrumbs se stejným pattern nebo layer
  - [ ] Navigace na vybraný breadcrumb
- [ ] `BreadcrumbRelatedItemProvider.kt` implements `RelatedItemLineMarkerProvider`
  - [ ] Integration s Go to Related (Ctrl+Alt+Home)
- [ ] `BreadcrumbReference.kt` implements `PsiReference`
  - [ ] Reference z `@arch:link=ADR-042` na ADR soubor
  - [ ] Ctrl+Click navigace

## Fáze 8: Architecture Map Tool Window

- [ ] `ArchitectureMapToolWindow.kt` implements `ToolWindowFactory`
  - [ ] Registrace v `plugin.xml`
  - [ ] Tree view: Layer → Pattern → File → Breadcrumb
  - [ ] Double-click → navigace do souboru
  - [ ] Filtrování: jen vybraný layer, jen vybraný pattern
  - [ ] Search bar
  - [ ] Refresh action (re-index)
- [ ] Lazy loading: data z indexu

## Fáze 9: Konfigurace

- [ ] `ArchBreadcrumbsSettings.kt` implements `PersistentStateComponent`
  - [ ] Definované layers (name, color, description) — default: presentation, business, infrastructure
  - [ ] Definované patterns (name, description) — default: Repository, Factory, Observer, Strategy, etc.
  - [ ] Display preferences (show gutter, show background, show in breadcrumb bar)
- [ ] `ArchBreadcrumbsConfigurable.kt` — Settings → Tools → Architecture Breadcrumbs
  - [ ] Layer editor (tabulka: name, color picker, description)
  - [ ] Pattern editor (tabulka: name, description)
  - [ ] Display toggles
- [ ] `.arch-breadcrumbs.yaml` team config
- [ ] Unit testy

## Fáze 10: Freemium & Licensing Infrastructure

- [ ] Integrace JetBrains Marketplace Licensing API
  - [ ] `LicenseChecker.kt` — ověření licence přes `com.intellij.ide.plugins.PluginManager` a Marketplace API
  - [ ] `LicenseStatus.kt` — enum: FREE, TRIAL, PAID, EXPIRED
  - [ ] `FreemiumGate.kt` — centrální guard pro paid features
    - [ ] `fun isPaidFeatureAvailable(featureId: String): Boolean`
    - [ ] Graceful degradation — paid UI elementy skryté/disabled ve free tieru
  - [ ] Trial period: 30 dní plný přístup
- [ ] `plugin.xml` — `<product-descriptor>` element pro Marketplace licensing
  - [ ] `code` atribut (unique product code)
  - [ ] `release-date`, `release-version`
- [ ] Nag screen / upgrade prompt
  - [ ] Non-intrusive balloon notification při pokusu o paid feature
  - [ ] „Upgrade to Pro" action → Marketplace stránka
  - [ ] Max 1× denně (ne spam)
- [ ] Feature flags
  - [ ] `FeatureRegistry.kt` — definice free vs. paid features
  - [ ] Každý paid feature má `@PaidFeature("feature.id")` anotaci nebo guard check
- [ ] Unit testy pro licensing logic (mock Marketplace API)

## Fáze 11: Production-Grade Quality

- [ ] Error Handling & Resilience
  - [ ] Globální exception handler (`ErrorReportSubmitter` implementace)
  - [ ] Graceful degradation — plugin nesmí crashnout IDE
  - [ ] Try-catch na všech EP entry pointech
  - [ ] Timeout na všech I/O operacích (network, git, file system)
  - [ ] Rate limiting na API volání
- [ ] Logging
  - [ ] `Logger` instance v každé třídě (IntelliJ `Logger.getInstance()`)
  - [ ] Structured logging: DEBUG pro vývoj, INFO pro operace, WARN/ERROR pro problémy
  - [ ] Žádné citlivé údaje v logech
- [ ] Telemetrie (opt-in, anonymní)
  - [ ] `PluginUsageTracker.kt` — FUS (Feature Usage Statistics) integrace
  - [ ] Metriky: aktivace featury, error rate, latence
  - [ ] Registrace FUS group v `plugin.xml`
- [ ] Performance
  - [ ] Profiling na velkých projektech (10k+ souborů)
  - [ ] Background thread pro všechny I/O operace (nesmí blokovat EDT)
  - [ ] Read/write action správně (ReadAction, WriteAction, smart mode check)
  - [ ] Disposal pattern: `Disposable` pro všechny listeners a services
  - [ ] Memory leak check (WeakReference kde potřeba)
- [ ] Testing — production úroveň
  - [ ] Unit testy: ≥80 % coverage core logiky
  - [ ] Integrační testy: `BasePlatformTestCase` pro PSI, inspections, EP
  - [ ] UI testy: `HeavyPlatformTestCase` pro tool windows, dialogy
  - [ ] Performance testy: benchmark na velkých souborech
- [ ] CI/CD Pipeline
  - [ ] GitHub Actions workflow:
    - [ ] `./gradlew check` — kompilace + testy
    - [ ] `./gradlew verifyPlugin` — plugin strukturální validace
    - [ ] `./gradlew verifyPluginProjectConfiguration` — metadata validace
    - [ ] `./gradlew runPluginVerifier` — binární kompatibilita s target IDE verzemi
    - [ ] Qodana scan (JetBrains code quality)
  - [ ] Release workflow:
    - [ ] Tag-based release (push tag → build → publish)
    - [ ] `./gradlew publishPlugin` — automatický upload na Marketplace
    - [ ] Changelog generace z git commitů
  - [ ] Dependabot / Renovate pro dependency updates
- [ ] Kódová kvalita
  - [ ] Kotlin coding conventions (official JetBrains style)
  - [ ] Detekt nebo ktlint pro statickou analýzu
  - [ ] Žádné deprecated API (IntelliJ Platform compatibility)
  - [ ] `@ApiStatus.Experimental` / `@ApiStatus.Internal` anotace
- [ ] Dokumentace
  - [ ] KDoc pro všechny public API
  - [ ] README.md: instalace, konfigurace, screenshoty, FAQ
  - [ ] CHANGELOG.md (Keep a Changelog format)
  - [ ] CONTRIBUTING.md (pokud open source)

## Fáze 12: Polish & Release

- [ ] Příklad `.arch-breadcrumbs.yaml` v docs/
- [ ] Příklad breadcrumbs v ukázkovém projektu
- [ ] README.md
- [ ] `./gradlew verifyPlugin`, `./gradlew buildPlugin`
- [ ] Manuální testování: Java, Kotlin, Python, JS projekty
- [ ] Cross-IDE test
- [ ] Marketplace listing
- [ ] Git tag `v2026.2.1`

## Fáze 13: Git Enhancement — Commit-Linked ADRs

- [ ] Rozšíření `BreadcrumbParser` o `@arch:commit=<sha>` atribut
  - [ ] Parsování commit SHA z breadcrumb syntaxe
  - [ ] `CommitLinkedBreadcrumb` datový model
- [ ] Auto-detection: git blame pro řádek s breadcrumb → commit metadata
  - [ ] `GitAnnotationProvider` integrace pro blame data
- [ ] Tooltip: commit message, autor, datum přidruženého commitu
- [ ] Navigace: klik na commit SHA → VCS Log s filter na commit
- [ ] Action „Link to Current Commit" — přidá `commit=` s HEAD SHA
- [ ] ADR propojení: `@arch:link=ADR-NNN` → soubor v `docs/adr/`
- [ ] Unit testy

## Fáze 14: Git Enhancement — Blame-Aware Architecture Notes

- [ ] `BlameAnnotatedBreadcrumb` datový model (author, date, stale detection)
  - [ ] Git blame integrace přes `GitAnnotationProvider.annotate(file)`
- [ ] Architecture Map tool window — nové sloupce „Author" a „Last Modified"
  - [ ] Filtrování dle autora: „Show only my breadcrumbs"
  - [ ] Timeline view: chronologický seznam dle commit date
- [ ] Stale breadcrumb detection
  - [ ] Porovnání blame date breadcrumb řádku vs. okolní kód (±10 řádků)
  - [ ] Warning annotation + quick-fix: „Review breadcrumb", „Mark as reviewed"
- [ ] Unit testy

## Fáze 15: Git Enhancement — Branch Impact Analysis

- [ ] `BranchArchitectureImpact` datový model (added/removed/modified breadcrumbs)
  - [ ] `BreadcrumbDiff` pro modifikované breadcrumby
- [ ] Action „Compare Architecture with Branch"
  - [ ] Branch selector dialog (combo box)
  - [ ] Diff: přidané, odebrané, modifikované breadcrumby
  - [ ] Layer impact a pattern impact agregace
- [ ] Architecture Review Checklist — auto-generovaný Markdown
  - [ ] Copy-to-clipboard pro PR description
- [ ] VCS Log integrace: highlight commitů měnících breadcrumby
- [ ] Unit testy

---

## Budoucí fáze (Paid)

- [ ] Auto-detection patterns (heuristiky z kódu)
- [ ] Layer consistency check (porušení pravidel)
- [ ] Graph view (vizualizace propojení mezi breadcrumbs)
- [ ] Export: Mermaid, PlantUML diagramy
- [ ] Team dashboard (architektonické pokrytí)
- [ ] Neomezené custom layers (free: max 3)
- [ ] Breadcrumb bar v horní části editoru
