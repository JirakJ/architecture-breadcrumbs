# Architecture Breadcrumbs — Software Requirements Specification (SRS)

**Verze:** 2026.2.1
**Autor:** Jakub Jirák
**Datum:** 12. února 2026
**Model:** Freemium JetBrains Plugin

---

## 1. Úvod

### 1.1 Účel
Architecture Breadcrumbs umožňuje vývojářům zanechávat a číst architektonické anotace přímo v kódu — lehké, verzovatelné „breadcrumbs" vysvětlující PROČ je kód strukturovaný tak, jak je.

### 1.2 Rozsah
Plugin přidává speciální anotační systém (`@arch`, `// @arch:`) pro architektonická rozhodnutí, zobrazuje je v editoru jako zvýrazněné bloky a poskytuje navigaci mezi propojenými breadcrumbs.

---

## 2. Celkový popis

### 2.1 Perspektiva produktu
Architektonická rozhodnutí jsou tradičně dokumentována v ADR (Architecture Decision Records) nebo wiki — odtrženě od kódu. Breadcrumbs vnáší kontext přímo tam, kde ho vývojář potřebuje.

### 2.2 Uživatelské skupiny
- **Noví členové týmu** — potřebují pochopit architektonické důvody
- **Senior vývojáři** — chtějí dokumentovat rozhodnutí in-context
- **Architekti** — potřebují přehled architektonických patterns v codebase

---

## 3. Funkční požadavky

### FR-1: Breadcrumb anotace
- **FR-1.1:** Syntaxe breadcrumb:
  ```
  // @arch:pattern=Repository
  // @arch:reason="Isolates DB access from business logic"
  // @arch:link=ADR-042
  // @arch:layer=infrastructure
  ```
- **FR-1.2:** Podpora v Java, Kotlin, Python, JS/TS, Go
- **FR-1.3:** Parsování přes custom PSI annotator

### FR-2: Editor vizualizace
- **FR-2.1:** Zvýrazněný blok (colored background) pro breadcrumb komentáře
- **FR-2.2:** Gutter ikona (🏗️ architektura) s hover tooltip
- **FR-2.3:** Breadcrumb bar v horní části editoru: File → Module → Layer → Pattern
- **FR-2.4:** Inlay hint zobrazující pattern/layer inline

### FR-3: Navigace
- **FR-3.1:** Go to Related Breadcrumbs (Ctrl+Shift+B) — najde všechny breadcrumbs se stejným pattern/layer
- **FR-3.2:** Architecture Map — tool window s přehledem všech breadcrumbs ve stromu: Layer → Pattern → File
- **FR-3.3:** Propojení s ADR (link na soubor nebo URL)

### FR-4: Auto-detection (Paid)
- **FR-4.1:** Heuristická detekce architektonických patterns (Repository, Factory, Observer, etc.)
- **FR-4.2:** Návrh breadcrumbs pro neoanotovaný kód
- **FR-4.3:** Layer detection (presentation, business, infrastructure) z package structure

### FR-5: Team Dashboard (Paid)
- **FR-5.1:** Přehled architektonického pokrytí (% kódu s breadcrumbs)
- **FR-5.2:** Architektonická konzistence check (porušení layer pravidel)
- **FR-5.3:** Export architecture map (Mermaid, PlantUML)

### FR-6: Konfigurace
- **FR-6.1:** Definovatelné layers a patterns (`.arch-breadcrumbs.yaml`)
- **FR-6.2:** Barevné schéma per layer
- **FR-6.3:** Filtry: zobrazit jen určité layers/patterns

---

## 4. Nefunkční požadavky

### NFR-1: Výkon
- Breadcrumb parsing při otevření souboru < 100ms
- Architecture map indexace na pozadí

### NFR-2: Kompatibilita
- IntelliJ IDEA 2024.3+
- Všechny JetBrains IDE

### NFR-3: Non-invasive
- Breadcrumbs jsou běžné komentáře — nerozbijí kompilaci
- Funguje i bez pluginu (komentáře jsou čitelné)

---

## 5. Datový model

### 5.1 Breadcrumb
```
Breadcrumb {
  file: VirtualFile
  offset: Int
  pattern: String? (Repository, Factory, Observer...)
  layer: String? (presentation, business, infrastructure)
  reason: String?
  link: String? (ADR reference nebo URL)
  tags: List<String>
  author: String (z git blame)
  createdAt: Instant
}
```

---

## 6. Freemium model

### Free tier
- Breadcrumb syntax highlighting + gutter ikony
- Navigace: Go to Related Breadcrumbs
- Max 3 custom layers

### Paid tier
- Architecture Map tool window
- Auto-detection patterns
- Layer consistency check
- Export (Mermaid, PlantUML)
- Neomezené layers/patterns
- Team dashboard
