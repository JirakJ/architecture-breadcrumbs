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
- Balíčky: `com.jakubjirak.archbreadcrumbs.*`
- Breadcrumb syntaxe: `// @arch:pattern=X`, `// @arch:layer=Y`, `// @arch:reason="Z"`
- Breadcrumbs jsou plain komentáře — kód funguje i bez pluginu
- Konfigurace vrstev/patterns v `.arch-breadcrumbs.yaml`
- Texty v `messages/ArchBreadcrumbsBundle.properties`
- Verzování: `2026.2.X`

## Production-grade požadavky
- Všechny I/O operace na background threadech (`Task.Backgroundable`, coroutines)
- Read/Write actions správně (`ReadAction.run {}`, `WriteAction.run {}`)
- `Disposable` pattern pro listeners a services
- Exception handling: žádné unhandled exceptions, graceful degradation
- Logging: `Logger.getInstance(MyClass::class.java)` v každé třídě
- EDT nesmí být blokován — žádné síťové/git/file operace na EDT
- Smart mode check (`DumbService.isDumb()`) kde potřeba

## Freemium architektura
- `FreemiumGate` centrální guard: `FreemiumGate.isPaidFeatureAvailable("feature.id")`
- JetBrains Marketplace Licensing API přes `<product-descriptor>` v `plugin.xml`
- Paid UI elementy: skryté nebo disabled ve free tieru (ne crash/error)
- Trial: 30 dní plný přístup, pak downgrade na free tier
- Upgrade prompt: max 1× denně, non-intrusive balloon

## CI/CD
- GitHub Actions: `./gradlew check`, `verifyPlugin`, `runPluginVerifier`
- Detekt pro statickou analýzu Kotlin kódu
- Tag-based release → `publishPlugin` na Marketplace
- Kotlin coding conventions (JetBrains official style)

## Ikona
- `src/main/resources/META-INF/pluginIcon.svg` (40×40, light)
- `src/main/resources/META-INF/pluginIcon_dark.svg` (40×40, dark)
- Action ikony: 16×16 + 13×13 SVG (light + dark varianty)

## Freemium hranice
- Free: syntax highlighting, navigace, 3 layers
- Paid: architecture map, auto-detection, export, team dashboard
