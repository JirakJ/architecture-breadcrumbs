# Architecture Breadcrumbs — Git Enhancement Specifikace

**Verze:** 2026.2.1
**Status:** Plánováno
**Závislost:** Git4Idea API, `com.intellij.git4idea`

---

## Přehled

Rozšíření pluginu o Git-based funkce pro propojení architektonických rozhodnutí s konkrétními commity, blame-aware poznámky a cross-branch impact analýzu. Tyto funkce umožňují sledovat „kdy a proč" se architektura změnila a jak branches ovlivňují architektonickou strukturu.

---

## FR-G1: Commit-Linked ADRs

**Popis:** Architektonická rozhodnutí (ADR — Architecture Decision Records) propojená se specifickými commity. Každý breadcrumb může být navázán na commit, kde bylo rozhodnutí implementováno.

### Akceptační kritéria

- [ ] AC-1: Rozšíření breadcrumb syntaxe o `@arch:commit=<sha>` atribut
- [ ] AC-2: Plugin automaticky detekuje commit SHA z git blame pro řádek kde je breadcrumb definován
- [ ] AC-3: Tooltip nad breadcrumbem zobrazí commit message, autora a datum přidruženého commitu
- [ ] AC-4: Navigace: klik na commit SHA → zobrazí commit diff v IDE VCS Log
- [ ] AC-5: Action „Link to Current Commit" — automaticky přidá `commit=` atribut s aktuálním HEAD SHA
- [ ] AC-6: ADR soubory (docs/adr/*.md) automaticky propojené s breadcrumby přes `@arch:link=ADR-NNN`

### Datový model — rozšíření

```kotlin
data class CommitLinkedBreadcrumb(
    val breadcrumb: Breadcrumb,
    val commitSha: String?,
    val commitMessage: String?,
    val commitAuthor: String?,
    val commitDate: Instant?,
    val adrFile: VirtualFile?,     // propojený ADR dokument
    val adrId: String?             // ADR-042
)
```

### Implementační poznámky

- Rozšířit `BreadcrumbParser` o parsování `commit=` atributu
- `GitAnnotationProvider` pro blame data (author, date per line)
- Navigace: `VcsLogManager.getInstance().openLogTab()` s filter na commit SHA
- Cache blame dat per file s invalidací při file change

---

## FR-G2: Blame-Aware Architecture Notes

**Popis:** Git blame integrace — kdo a kdy udělal architektonické rozhodnutí. Automatická anotace breadcrumbů informacemi z git blame.

### Akceptační kritéria

- [ ] AC-1: U každého breadcrumbu se zobrazí autor a datum z git blame (kdo přidal breadcrumb komentář)
- [ ] AC-2: Architecture Map tool window zobrazí sloupec „Author" a „Last Modified" pro každý breadcrumb
- [ ] AC-3: Filtrování breadcrumbů dle autora: „Show only my breadcrumbs" / „Show by author"
- [ ] AC-4: Timeline view: chronologický seznam architektonických rozhodnutí seřazený dle commit date
- [ ] AC-5: Detekce „stale breadcrumbs" — breadcrumb jehož okolní kód se změnil ale breadcrumb samotný ne (potenciálně zastaralý)
- [ ] AC-6: Warning annotation pro stale breadcrumbs s quick-fix: „Review breadcrumb" nebo „Mark as reviewed"

### Datový model — rozšíření

```kotlin
data class BlameAnnotatedBreadcrumb(
    val breadcrumb: Breadcrumb,
    val author: String,
    val authorDate: Instant,
    val lastCodeChangeDate: Instant?,  // datum poslední změny okolního kódu
    val isStale: Boolean,              // breadcrumb neaktualizován ale kód ano
    val staleDays: Int?                // počet dní od poslední aktualizace breadcrumbu
)

data class ArchitectureTimeline(
    val entries: List<BlameAnnotatedBreadcrumb>,
    val authors: Set<String>,
    val dateRange: ClosedRange<Instant>
)
```

### Implementační poznámky

- `GitAnnotationProvider.annotate(file)` pro blame data per line
- Stale detection: porovnání blame date breadcrumb řádku vs. blame date okolního kódu (±10 řádků)
- Timeline view: nový tab v Architecture Map tool window
- Author filter: `ComboBoxAction` v toolbar tool window

---

## FR-G3: Branch Impact Analysis

**Popis:** Analýza jak branch ovlivní architekturu projektu — cross-branch porovnání breadcrumbů pro identifikaci architektonických změn v PR/branch.

### Akceptační kritéria

- [ ] AC-1: Action „Compare Architecture with Branch" — porovná breadcrumby mezi aktuálním branchem a vybraným branchem
- [ ] AC-2: Zobrazí: přidané breadcrumby, odebrané breadcrumby, modifikované breadcrumby
- [ ] AC-3: Layer impact: které architektonické vrstvy jsou ovlivněny branchem
- [ ] AC-4: Pattern impact: které design patterns jsou přidány/odebrány
- [ ] AC-5: „Architecture Review Checklist" — automaticky generovaný seznam architektonických změn pro code review
- [ ] AC-6: Integrace s VCS Log: highlight commitů které mění breadcrumby

### Datový model — rozšíření

```kotlin
data class BranchArchitectureImpact(
    val sourceBranch: String,
    val targetBranch: String,
    val addedBreadcrumbs: List<Breadcrumb>,
    val removedBreadcrumbs: List<Breadcrumb>,
    val modifiedBreadcrumbs: List<BreadcrumbDiff>,
    val affectedLayers: Set<String>,
    val affectedPatterns: Set<String>,
    val impactSummary: String       // auto-generovaný popis dopadu
)

data class BreadcrumbDiff(
    val before: Breadcrumb,
    val after: Breadcrumb,
    val changedFields: Set<String>  // pattern, layer, reason, link
)
```

### Implementační poznámky

- `git diff <source>..<target> -- '*.kt' '*.java' '*.py'` pro soubory se změnami
- Parsování breadcrumbů z obou verzí souborů (`git show <branch>:<file>`)
- Tool window dialog: branch selector (combo box) + diff tabulka
- Architecture Review Checklist: Markdown export pro PR description

---

## Konfigurace — rozšíření `ArchBreadcrumbsSettings`

```yaml
# .arch-breadcrumbs.yaml — nové sekce
git-enhancement:
  commit-linked-adrs:
    enabled: true
    adr-directory: "docs/adr"     # cesta k ADR souborům
    auto-link-commits: true        # automaticky přidávat commit SHA
    adr-pattern: "ADR-\\d+"       # regex pro ADR identifikátory
  blame-aware-notes:
    enabled: true
    stale-threshold-days: 30       # breadcrumb starší než 30 dní = stale warning
    show-author-in-gutter: true
    show-date-in-gutter: false
  branch-impact:
    enabled: true
    default-compare-branch: "main"
    auto-generate-checklist: true
    checklist-format: "markdown"   # markdown | plaintext
```
