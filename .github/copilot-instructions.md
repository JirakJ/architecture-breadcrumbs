# Copilot Instructions — Architecture Breadcrumbs

## Projekt
JetBrains IDE plugin — systém architektonických anotací v kódu s vizualizací a navigací.

## Technický stack
- **Jazyk:** Kotlin (JVM 17+)
- **Build:** Gradle (Kotlin DSL) s `org.jetbrains.intellij.platform` plugin 2.x
- **Platform:** IntelliJ Platform SDK 2024.3+
- **Testování:** JUnit 5 + `BasePlatformTestCase`

## Build a testy
```bash
./gradlew build
./gradlew test
./gradlew test --tests "*.BreadcrumbParserTest.testRepositoryPattern"
./gradlew runIde
./gradlew verifyPlugin
```

## Architektura
- **Parser:** `BreadcrumbParser` — parsuje `// @arch:` komentáře z PSI stromu
- **Annotator:** `BreadcrumbAnnotator` — zvýrazňuje breadcrumb komentáře v editoru
- **Index:** `BreadcrumbIndex` — file-based index pro rychlé vyhledávání breadcrumbs (StubIndex)
- **Navigation:** `BreadcrumbRelatedProvider` — Go to Related Breadcrumbs action
- **UI:** `ArchitectureMapToolWindow` — stromová vizualizace Layer → Pattern → File
- **Auto-detection:** `PatternDetector` — heuristická detekce arch patterns z kódu (Paid)

## Konvence
- Balíčky: `com.jirak.archbreadcrumbs.*`
- Breadcrumb syntaxe: `// @arch:pattern=X`, `// @arch:layer=Y`, `// @arch:reason="Z"`
- Breadcrumbs jsou plain komentáře — kód funguje i bez pluginu
- Konfigurace vrstev/patterns v `.arch-breadcrumbs.yaml`
- Texty v `messages/ArchBreadcrumbsBundle.properties`
- Verzování: `2026.2.X`

## Freemium hranice
- Free: syntax highlighting, navigace, 3 layers
- Paid: architecture map, auto-detection, export, team dashboard
