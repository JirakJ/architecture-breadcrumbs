# Architecture Breadcrumbs — Screenshots and Media Guide

This document describes the screenshots and video content needed for the JetBrains Marketplace listing and marketing pages.

---

## Screenshots

### 1. Breadcrumb Annotations in the Editor

**Filename:** `01-annotations-in-editor.png`

Show a Java or Kotlin file with several `@arch:` annotations. The editor should display colored background highlighting for different breadcrumb categories (e.g., blue for Decision, green for Pattern, orange for Constraint). Inlay hints should be visible, rendering breadcrumb metadata inline after the comment.

**Key elements to capture:**
- At least 3 different breadcrumb categories visible
- Colored backgrounds distinguishing each category
- Inlay hints showing `reason`, `link`, and `layer` values
- Standard editor theme (light or dark, provide both if possible)

---

### 2. Gutter Icons

**Filename:** `02-gutter-icons.png`

Close-up of the editor gutter showing breadcrumb icons next to annotated lines. Each category should have a distinct icon. Include a tooltip popup showing the breadcrumb summary on hover.

**Key elements to capture:**
- Gutter icons for at least 4 categories (Decision, Pattern, Constraint, Risk)
- One tooltip popup expanded, showing category name, reason, and linked resource
- Clean view with no distracting warnings or errors in the gutter

---

### 3. Breadcrumbs Tool Window — Category View

**Filename:** `03-tool-window-categories.png`

The Breadcrumbs Tool Window open in the bottom or side panel. Show the category view with a tree structure: top-level nodes for each category (Decision, Pattern, Constraint, Trade-off, Assumption, Risk), expanded to show individual breadcrumbs underneath.

**Key elements to capture:**
- All 6 categories listed as tree nodes
- At least one category expanded showing 3-4 breadcrumbs
- Each breadcrumb showing its file location and short description
- Search/filter bar visible at the top of the tool window

---

### 4. Breadcrumbs Tool Window — File View

**Filename:** `04-tool-window-files.png`

Same tool window but switched to file view. Show a tree organized by file path, with breadcrumbs grouped under each file.

**Key elements to capture:**
- File-based tree structure with 3-4 files visible
- Breadcrumbs listed under each file with category icons
- Clear visual distinction between file nodes and breadcrumb nodes

---

### 5. Navigation Between Breadcrumbs

**Filename:** `05-navigation.png`

Demonstrate the navigation feature. Show a split or sequential view where clicking a breadcrumb reference navigates to a related breadcrumb in another file. A "Go to Related Breadcrumb" popup or action menu can be shown.

**Key elements to capture:**
- Source breadcrumb with a visible link or reference
- Target breadcrumb highlighted after navigation
- Navigation action visible (context menu, keyboard shortcut hint, or popup)

---

### 6. Architecture Map (Pro)

**Filename:** `06-architecture-map.png`

The Architecture Map visualization panel. Show a diagram-style view of layers and modules with breadcrumbs plotted across them. Connections between related breadcrumbs should be visible as lines or arrows.

**Key elements to capture:**
- Layer labels (e.g., Presentation, Domain, Infrastructure)
- Breadcrumb nodes placed within their respective layers
- Connection lines between cross-linked breadcrumbs
- "Pro" badge or indicator visible

---

### 7. Export to Mermaid Diagram (Pro)

**Filename:** `07-export-mermaid.png`

Show the export dialog or a generated Mermaid diagram rendered in a preview panel. The diagram should reflect the breadcrumb data from the project — layers, components, and decision links.

**Key elements to capture:**
- Export action or dialog with format selection (Mermaid / PlantUML)
- Preview of the generated diagram
- Recognizable mapping from breadcrumb categories to diagram elements

---

### 8. Pricing and Tier Comparison

**Filename:** `08-pricing-tiers.png`

A clean comparison graphic showing the three tiers (Free, Pro, Team) side by side with feature checkmarks. This is a designed asset, not a screenshot.

**Key elements to capture:**
- Three columns: Free, Pro, Team
- Feature rows with check/cross indicators
- Pricing displayed clearly ($0 / $2.90/mo / $5.90/user/mo)
- Visual emphasis on the Pro tier as the recommended option

---

## Video Demo Outline

**Duration:** 45–60 seconds
**Format:** Screen recording with text overlays (no voiceover required; add if desired)
**Resolution:** 1920×1080 minimum

### Scene 1 — The Problem (0:00–0:08)

- Open a complex codebase in IntelliJ IDEA
- Text overlay: *"Why was this built this way?"*
- Scroll through undocumented code — no comments, no context
- Text overlay: *"Architecture decisions get lost over time."*

### Scene 2 — Adding a Breadcrumb (0:08–0:20)

- Type a breadcrumb annotation in a Java file:
  ```java
  // @arch:decision @arch:reason=Chose CQRS to separate read/write scaling
  ```
- Show syntax highlighting activating immediately
- Gutter icon appears
- Inlay hint renders the reason text
- Text overlay: *"Embed decisions right in your code."*

### Scene 3 — Browsing Breadcrumbs (0:20–0:35)

- Open the Breadcrumbs Tool Window
- Show category view with multiple breadcrumbs across files
- Click a breadcrumb to navigate to its source location
- Switch to file view
- Text overlay: *"Browse and navigate all architectural context from one place."*

### Scene 4 — Architecture Map (0:35–0:48)

- Open the Architecture Map panel (Pro feature)
- Show layers and breadcrumb connections
- Zoom in on a cluster of related decisions
- Text overlay: *"Visualize your architecture. See the big picture."*

### Scene 5 — Call to Action (0:48–0:60)

- Show the JetBrains Marketplace install button
- Text overlay: *"Architecture Breadcrumbs — Free on the JetBrains Marketplace"*
- Display the tagline: *"Stop Losing Architecture Decisions — Embed Them in Your Code"*
- End card with install link

---

## Asset Requirements

| Asset | Dimensions | Format |
|---|---|---|
| Screenshots | 1920×1080 or 2x retina | PNG |
| Pricing graphic | 1200×600 | PNG or SVG |
| Video demo | 1920×1080, 30fps | MP4 |
| Plugin icon | 40×40 and 80×80 | SVG + PNG |
| Social preview | 1280×640 | PNG |
