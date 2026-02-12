# Architecture Breadcrumbs — Implementační plán

**Verze:** 2026.2.1
**Status:** Připraveno k implementaci

---

## Fáze 1: Projekt scaffolding

- [ ] Inicializovat IntelliJ Platform Plugin projekt (Gradle Kotlin DSL)
  - [ ] `build.gradle.kts` — IntelliJ Platform 2024.3+, JVM 17
  - [ ] `settings.gradle.kts`, `gradle.properties` (verze `2026.2.1`)
  - [ ] Gradle wrapper (8.x)
- [ ] `src/main/resources/META-INF/plugin.xml`
  - [ ] Plugin ID: `com.jirak.archbreadcrumbs`
  - [ ] Závislosti: `com.intellij.modules.platform`, `com.intellij.modules.lang`
- [ ] Balíčková struktura: `com.jirak.archbreadcrumbs.*`
  - [ ] `.model` — datové třídy
  - [ ] `.parser` — breadcrumb komentáře parser
  - [ ] `.index` — file-based index
  - [ ] `.navigation` — Go to Related, contributors
  - [ ] `.ui` — annotator, tool window, gutter
  - [ ] `.detection` — auto-detection patterns
  - [ ] `.settings` — konfigurace
- [ ] `src/main/resources/messages/ArchBreadcrumbsBundle.properties`
- [ ] `.gitignore`
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

## Fáze 10: Polish & Release

- [ ] Příklad `.arch-breadcrumbs.yaml` v docs/
- [ ] Příklad breadcrumbs v ukázkovém projektu
- [ ] README.md
- [ ] `./gradlew verifyPlugin`, `./gradlew buildPlugin`
- [ ] Manuální testování: Java, Kotlin, Python, JS projekty
- [ ] Cross-IDE test
- [ ] Marketplace listing
- [ ] Git tag `v2026.2.1`

---

## Budoucí fáze (Paid)

- [ ] Auto-detection patterns (heuristiky z kódu)
- [ ] Layer consistency check (porušení pravidel)
- [ ] Graph view (vizualizace propojení mezi breadcrumbs)
- [ ] Export: Mermaid, PlantUML diagramy
- [ ] Team dashboard (architektonické pokrytí)
- [ ] Neomezené custom layers (free: max 3)
- [ ] Breadcrumb bar v horní části editoru
