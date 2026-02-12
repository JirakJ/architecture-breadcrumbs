# Architecture Breadcrumbs — Business Requirements Specification (BRS)

**Verze:** 2026.2.1
**Autor:** Jakub Jirák
**Datum:** 12. února 2026
**Model:** Freemium JetBrains Plugin

---

## 1. Executive Summary

Architecture Breadcrumbs přináší architektonický kontext přímo do kódu. Vývojáři zanechávají lehké anotace vysvětlující architektonická rozhodnutí, plugin je vizualizuje a umožňuje navigaci. Řeší problém „proč je to takhle?" bez opuštění editoru.

---

## 2. Obchodní cíle

### 2.1 KPI
| Metrika | Cíl (6 měsíců) | Cíl (12 měsíců) |
|---------|:---:|:---:|
| Celkové instalace | 5 000 | 20 000 |
| MAU | 2 000 | 8 000 |
| Paid konverze | 3 % | 5 % |
| Marketplace rating | ≥ 4.0 | ≥ 4.2 |

---

## 3. Cílový trh

- **Střední/velké týmy (10+)** — kde architektonický kontext se ztrácí
- **Projekty s vysokou rotací** — onboarding = klíčový use case
- **Enterprise** — compliance požadavky na architektonickou dokumentaci

---

## 4. Konkurenční prostředí

**Přímá konkurence neexistuje.** Nejbližší:
- ADR nástroje (dokumenty, ne code-level)
- ArchUnit (testy, ne anotace)
- Architecture diagrams (extern, ne inline)

---

## 5. Revenue model

| Tier | Cena |
|------|------|
| **Free** | $0 — highlighting, navigace, 3 layers |
| **Pro** | $2.90/měsíc nebo $29/rok — architecture map, auto-detection, export |
| **Team** | $5.90/user/měsíc — Pro + dashboard, consistency checks |

---

## 6. Go-to-Market

1. Blog: „Stop Losing Architecture Decisions — Embed Them in Your Code"
2. Architecture community (DDD, Clean Architecture skupiny)
3. Conference talks: „Architecture as Code Comments Done Right"

---

## 7. Milníky

| Milestone | Popis |
|-----------|-------|
| **M1: MVP** | Breadcrumb syntax, highlighting, gutter ikony, navigace |
| **M2: Map** | Architecture Map tool window |
| **M3: Auto** | Pattern auto-detection, layer detection |
| **M4: Export** | Mermaid/PlantUML export |
| **M5: Team** | Dashboard, consistency checks |

---

## 8. Success Criteria

1. 10 000+ instalací do 9 měsíců
2. Adoption v alespoň 3 open source projektech (viditelnost)
3. ≥ 3 % paid konverze
4. Pozitivní feedback z DDD/architecture komunity
