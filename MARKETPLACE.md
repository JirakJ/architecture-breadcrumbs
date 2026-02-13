# Architecture Breadcrumbs — JetBrains Marketplace Listing

## Plugin Name
Architecture Breadcrumbs

## Tagline
Embed architectural decisions directly in your code — never lose context again.

## Short Description
Track design patterns, architectural decisions, and layer boundaries with inline code annotations. Navigate, visualize, and export your architecture — all without leaving the IDE.

## Full Description

```html
<p>
  <b>Bridge the gap between architecture documentation and code.</b> Architecture Breadcrumbs lets you
  embed lightweight annotations directly in your source code to mark design patterns, architectural
  decisions, and layer boundaries. Every decision stays connected to the code it affects — no more
  outdated wikis or disconnected documentation.
</p>

<h2>Key Features</h2>
<ul>
  <li><b>Inline breadcrumb annotations</b> — mark code with <code>@arch:pattern=</code>, <code>@arch:reason=</code>, <code>@arch:layer=</code>, and <code>@arch:link=</code></li>
  <li><b>6 breadcrumb categories</b> — Decision 📋, Pattern 🔧, Constraint 🔒, TODO 📝, Warning ⚠️, Context 💡</li>
  <li><b>Gutter icons with tooltips</b> — visual indicators next to annotated lines</li>
  <li><b>Colored syntax highlighting</b> — distinct background colors per breadcrumb category</li>
  <li><b>Breadcrumbs tool window</b> — centralized browsing with filtering and search</li>
  <li><b>Cross-file navigation</b> — jump between related breadcrumbs (<code>Ctrl+Shift+B</code>)</li>
  <li><b>Inlay hints</b> — inline pattern and layer information displayed in the editor</li>
  <li><b>Multi-language support</b> — Java, Kotlin, Python, JavaScript, TypeScript, Go</li>
  <li><b>VCS-friendly</b> — breadcrumbs are standard code comments; code compiles without the plugin</li>
</ul>

<h2>Free Tier</h2>
<p>Full breadcrumb authoring and navigation — no credit card required:</p>
<ul>
  <li>Breadcrumb syntax highlighting with colored backgrounds</li>
  <li>Gutter icons with hover tooltips</li>
  <li>All 6 breadcrumb categories (Decision, Pattern, Constraint, TODO, Warning, Context)</li>
  <li>Basic navigation with "Go to Related Breadcrumbs" (<code>Ctrl+Shift+B</code>)</li>
  <li>Breadcrumbs tool window with filtering and search</li>
  <li>Up to 3 custom architectural layers</li>
  <li>Multi-language support (Java, Kotlin, Python, JS/TS, Go)</li>
  <li>Inlay hints for pattern and layer info</li>
</ul>

<h2>Pro Tier ($2.90/month)</h2>
<p>Designed for teams who want to visualize and enforce architecture:</p>
<ul>
  <li><b>Architecture Map</b> — visual tree overview organized by Layer → Pattern → File</li>
  <li><b>Auto-detection</b> — heuristic recognition of patterns (Repository, Factory, Observer) and layers from package structure</li>
  <li><b>Layer consistency checks</b> — validate architectural layer rules and detect violations</li>
  <li><b>Diagram export</b> — export architecture to Mermaid and PlantUML formats</li>
  <li><b>Unlimited layers, patterns, and breadcrumbs</b></li>
  <li><b>Cross-linking</b> — link related breadcrumbs across your codebase</li>
</ul>

<h2>Team Tier ($5.90/user/month)</h2>
<p>Designed for teams who need shared architectural governance:</p>
<ul>
  <li>All Pro features included</li>
  <li><b>Team dashboard</b> — architecture coverage metrics across the codebase</li>
  <li><b>Team-wide consistency checks</b> — validate architectural rules across projects</li>
  <li><b>Centralized architecture overview</b> — unified view of architectural decisions</li>
</ul>

<h2>Getting Started</h2>
<ol>
  <li><b>Install</b> — search "Architecture Breadcrumbs" in <code>Settings → Plugins</code> and click Install</li>
  <li><b>Add a breadcrumb</b> — type <code>// @arch:pattern=Repository @arch:reason=Data access abstraction</code> in any source file</li>
  <li><b>Navigate</b> — open the Breadcrumbs tool window or press <code>Ctrl+Shift+B</code> to browse all architectural annotations</li>
</ol>

<h2>Compatibility</h2>
<ul>
  <li>IntelliJ IDEA 2024.3+ and all JetBrains IDEs</li>
  <li>macOS, Linux, and Windows</li>
  <li>Non-invasive: breadcrumbs are standard code comments — your code compiles with or without the plugin</li>
</ul>

<h2>Part of the Developer Productivity Suite</h2>
<p>
  Architecture Breadcrumbs is part of a comprehensive developer productivity toolkit. Combine it with
  <b>ADR Companion</b> (manage Architecture Decision Records),
  <b>Kubernetes Context Guard</b> (always know your kubectl context),
  <b>Terminal Safety Rails</b> (intercept dangerous terminal commands), and
  <b>Local Secrets Tripwire</b> (prevent secret leaks in code)
  for complete development workflow protection and architectural governance.
</p>
```

## Tags
architecture, design-patterns, code-documentation, code-navigation, software-architecture

## Category
Code Tools

## Pricing
| Tier | Price | Features |
|------|-------|----------|
| Free | $0 | Breadcrumb annotations, 6 categories, gutter icons, tool window, navigation, 3 custom layers, multi-language |
| Pro | $2.90/month or $29/year | Architecture Map, auto-detection, layer consistency checks, Mermaid/PlantUML export, unlimited layers |
| Team | $5.90/user/month | All Pro features + team dashboard, team-wide consistency checks, centralized architecture overview |

## Screenshots Needed
1. **Editor with breadcrumbs** — source code showing colored breadcrumb annotations with gutter icons and inlay hints
2. **Breadcrumbs tool window** — the centralized browsing panel with filtering by category and search
3. **Architecture Map (Pro)** — visual tree view showing Layer → Pattern → File hierarchy
4. **Layer consistency check (Pro)** — inspection results showing architectural layer violations
5. **Diagram export (Pro)** — generated Mermaid or PlantUML diagram of the project architecture
