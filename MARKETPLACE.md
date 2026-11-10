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

<h2>Free &amp; Open Source</h2>
<p>Every feature — including the Architecture Map, auto-detection, layer consistency checks, diagram export and unlimited layers — is free for everyone, no license needed. Source code: <a href="https://github.com/JirakJ/architecture-breadcrumbs">GitHub</a> (Apache-2.0).</p>

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

Free & open source (Apache-2.0) — every feature is available to everyone, no license needed.

## Screenshots Needed
1. **Editor with breadcrumbs** — source code showing colored breadcrumb annotations with gutter icons and inlay hints
2. **Breadcrumbs tool window** — the centralized browsing panel with filtering by category and search
3. **Architecture Map** — visual tree view showing Layer → Pattern → File hierarchy
4. **Layer consistency check** — inspection results showing architectural layer violations
5. **Diagram export** — generated Mermaid or PlantUML diagram of the project architecture
