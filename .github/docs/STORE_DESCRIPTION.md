# JetBrains Marketplace — Store Description

> Paste the HTML below into the JetBrains Marketplace plugin description field.

---

```html
<h2>Stop Losing Architecture Decisions — Embed Them in Your Code</h2>

<p>
  Architecture Breadcrumbs lets you embed architectural context directly in your codebase.
  Annotate decisions, patterns, constraints, trade-offs, assumptions, and risks using
  simple structured comments. Your reasoning stays with your code — versioned, searchable,
  and visible to every developer on the team.
</p>

<h3>Why Architecture Breadcrumbs?</h3>

<p>
  Architectural knowledge degrades over time. Critical decisions end up in forgotten wiki pages,
  buried Slack threads, or the minds of people who have moved on. Architecture Breadcrumbs keeps
  that knowledge where it belongs — right next to the code it describes.
</p>

<h3>Breadcrumb Categories</h3>

<ul>
  <li><strong>Decision</strong> — Why a specific approach was chosen</li>
  <li><strong>Pattern</strong> — Which design patterns are in use and why</li>
  <li><strong>Constraint</strong> — Hard limits from business, regulations, or infrastructure</li>
  <li><strong>Trade-off</strong> — What was sacrificed and what was gained</li>
  <li><strong>Assumption</strong> — Hidden assumptions that should stay visible</li>
  <li><strong>Risk</strong> — Known risks flagged for ongoing awareness</li>
</ul>

<h3>Annotations</h3>

<p>Add breadcrumbs using structured comment annotations:</p>

<pre><code>// @arch:pattern=Repository @arch:reason=Decouples domain from persistence
// @arch:layer=infrastructure @arch:link=ADR-042</code></pre>

<p>
  Annotations are plain comments in your source files. They work with your existing
  version control workflow — no external files or sync tools required.
</p>

<h3>IDE Integration</h3>

<ul>
  <li>Syntax highlighting with colored backgrounds for each category</li>
  <li>Inlay hints showing breadcrumb details inline</li>
  <li>Gutter icons for quick identification</li>
  <li>Navigation between related breadcrumbs</li>
  <li>Breadcrumbs Tool Window — browse by category or file</li>
  <li>VCS-friendly storage via <code>.arch-breadcrumbs.json</code></li>
</ul>

<h3>Pro Features</h3>

<ul>
  <li>Unlimited breadcrumbs and layers</li>
  <li>Architecture Map — visualize breadcrumbs across layers and modules</li>
  <li>Auto-detection of common patterns and layer assignments</li>
  <li>Layer consistency checks</li>
  <li>Export to Mermaid and PlantUML diagrams</li>
  <li>Cross-linking between breadcrumbs</li>
</ul>

<h3>Team Features</h3>

<ul>
  <li>Team dashboard with breadcrumb coverage and trends</li>
  <li>Team consistency checks across repositories</li>
</ul>

<h3>Pricing</h3>

<table>
  <thead>
    <tr>
      <th>Tier</th>
      <th>Price</th>
      <th>Highlights</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>Free</td>
      <td>$0</td>
      <td>All 6 categories, up to 20 breadcrumbs, 3 custom layers, gutter icons, explorer</td>
    </tr>
    <tr>
      <td>Pro</td>
      <td>$2.90/mo or $29/yr</td>
      <td>Unlimited breadcrumbs/layers, Architecture Map, auto-detection, export, cross-linking</td>
    </tr>
    <tr>
      <td>Team</td>
      <td>$5.90/user/mo</td>
      <td>All Pro features + team dashboard + team consistency checks</td>
    </tr>
  </tbody>
</table>

<h3>Supported Languages</h3>

<p>Java, Kotlin, Python, JavaScript/TypeScript, Go</p>

<h3>Requirements</h3>

<ul>
  <li>IntelliJ IDEA 2024.3+ (build 243+)</li>
  <li>Java 21+</li>
  <li>Optional: Git4Idea plugin for VCS integration</li>
</ul>

<h3>Support</h3>

<ul>
  <li><a href="#">Documentation</a></li>
  <li><a href="#">Issue Tracker (GitHub)</a></li>
  <li><a href="#">Email Support: support@example.com</a></li>
  <li><a href="#">Community Forum</a></li>
</ul>
```
