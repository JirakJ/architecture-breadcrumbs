# Architecture Breadcrumbs

## Stop Losing Architecture Decisions — Embed Them in Your Code

Your architecture decisions deserve better than a forgotten wiki page. Architecture Breadcrumbs is a JetBrains IDE plugin that lets you embed architectural context directly in your codebase — decisions, patterns, constraints, and trade-offs — right where they matter most.

[Install Free from JetBrains Marketplace](#) · [View on GitHub](#)

---

## The Problem

Architecture knowledge decays. Teams make critical decisions — why a particular pattern was chosen, what constraints shaped the design, which trade-offs were accepted — and then that knowledge disappears into outdated wikis, buried Slack threads, or the minds of engineers who leave.

New team members read the code but not the reasoning. They refactor what shouldn't be refactored. They repeat mistakes that were already considered and rejected. The architecture erodes, one uninformed change at a time.

**Architecture Breadcrumbs fixes this.** It keeps architectural reasoning attached to the code it describes, versioned alongside it, and visible to every developer who opens the file.

---

## Key Features

### 🏷️ Six Breadcrumb Categories

Annotate your code with purpose-built categories that cover every kind of architectural context:

- **Decision** — Record why a specific approach was chosen
- **Pattern** — Document which design patterns are in use and why
- **Constraint** — Mark hard limits from business rules, regulations, or infrastructure
- **Trade-off** — Explain what was sacrificed and what was gained
- **Assumption** — Surface hidden assumptions before they become bugs
- **Risk** — Flag known risks so they stay visible

### 📝 Simple Comment-Based Annotations

Add breadcrumbs using structured comments that live in your source code:

```java
// @arch:pattern=Repository @arch:reason=Decouples domain from persistence
// @arch:layer=infrastructure @arch:link=ADR-042
```

No special files to maintain. No external tools to sync. The annotations travel with your code through branches, merges, and rebases.

### 🎨 Rich Visual Feedback

- **Syntax highlighting** with colored backgrounds for each category
- **Inlay hints** that surface breadcrumb details inline
- **Gutter icons** for quick identification of annotated lines
- Never miss an architectural annotation again

### 🧭 Navigation and Browsing

- Jump between related breadcrumbs across your project
- **Breadcrumbs Tool Window** — browse all annotations by category or by file
- Find every Decision, Pattern, or Risk in your codebase in seconds

### 🗺️ Architecture Map (Pro)

Visualize how your breadcrumbs connect across layers and modules. See the big picture of your system's architectural landscape in a single view.

### 🔍 Auto-Detection (Pro)

Automatically detect common patterns and layer assignments based on your code structure. Spend less time annotating and more time building.

### 📤 Export to Diagrams (Pro)

Export your architectural annotations to **Mermaid** or **PlantUML** diagrams. Generate living architecture documentation that stays current with your code.

### 👥 Team Dashboard (Team)

Track breadcrumb coverage, consistency, and trends across your team. Ensure architectural decisions are communicated and followed.

---

## How It Works

### Step 1: Annotate

Add breadcrumb comments to your code using the `@arch:` prefix. Choose a category, add a reason, and optionally link to external documents like ADRs.

```python
# @arch:decision @arch:reason=Using event sourcing for audit trail requirements
# @arch:constraint=Must support 10k events/sec @arch:link=NFR-007
```

### Step 2: Browse

Open the Breadcrumbs Tool Window to see all annotations organized by category or file. Filter, search, and navigate your architectural context without leaving the IDE.

### Step 3: Navigate

Click any breadcrumb to jump to its location. Follow links between related breadcrumbs to trace decisions across modules and layers.

### Step 4: Share

Breadcrumbs are stored in source code comments and an optional `.arch-breadcrumbs.json` file — both VCS-friendly. Push to your repository and every team member sees the same architectural context.

---

## Pricing

### Free

**$0** — Get started with the essentials.

- All 6 breadcrumb categories
- Up to 20 breadcrumbs per project
- Up to 3 custom layers
- Gutter icons and syntax highlighting
- Breadcrumbs explorer tool window

[Install Free](#)

### Pro

**$2.90/month** or **$29/year** — For individual developers and small projects.

- Everything in Free
- Unlimited breadcrumbs and layers
- Architecture Map visualization
- Auto-detection of patterns and layers
- Layer consistency checks
- Export to Mermaid and PlantUML
- Cross-linking between breadcrumbs

[Start Pro Trial](#)

### Team

**$5.90/user/month** — For teams that take architecture seriously.

- Everything in Pro
- Team dashboard with coverage metrics
- Team consistency checks
- Centralized breadcrumb management

[Contact Sales](#)

---

## What Developers Are Saying

> "Architecture Breadcrumbs changed how our team thinks about documentation. Decisions stay with the code, not in a wiki nobody reads."
>
> — *[Name], Senior Engineer at [Company]*

> "I onboarded onto a new codebase in half the time. Every design decision was right there in the code, with the reasoning attached."
>
> — *[Name], Software Developer at [Company]*

> "We stopped having the same architecture debates every quarter. The breadcrumbs make it clear why things were built the way they were."
>
> — *[Name], Tech Lead at [Company]*

---

## Frequently Asked Questions

### What languages are supported?

Architecture Breadcrumbs supports **Java**, **Kotlin**, **Python**, **JavaScript/TypeScript**, and **Go**. Breadcrumb annotations use standard comment syntax for each language.

### Does this add noise to my code?

Breadcrumb annotations are concise, single-line comments. They add less noise than a typical TODO and far more value. The IDE renders them with visual styling so they stand out when relevant and stay unobtrusive when you're focused on logic.

### How is this different from regular code comments?

Regular comments are unstructured and unsearchable. Architecture Breadcrumbs are categorized, indexed, navigable, and visualized. You can browse all decisions in your project, check layer consistency, and export them to diagrams — none of which is possible with plain comments.

### What are the system requirements?

- IntelliJ IDEA 2024.3 or later (build 243+)
- Java 21 or later
- Optional: Git4Idea plugin for VCS integration

### Can I use this with existing ADR tools?

Yes. Use the `@arch:link=` annotation to reference ADR documents, wiki pages, or any external resource. Architecture Breadcrumbs complements your existing documentation workflow — it doesn't replace it.

---

## Start Embedding Architecture in Your Code

Your future self — and your teammates — will thank you.

[Install from JetBrains Marketplace](#) · [Read the Documentation](#) · [View on GitHub](#)
