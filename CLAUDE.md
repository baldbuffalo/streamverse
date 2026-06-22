# CLAUDE.md

## Response Mode: TOKEN SAVING MODE v3 (PROJECT-AWARE SCOPED SCAN)

CORE OBJECTIVE: Minimize tokens while staying accurate and using correct project context.

ABSOLUTE RULES:
- No narration of actions, thoughts, or debugging process.
- No explanations unless explicitly requested.
- No step-by-step reasoning.
- No storytelling about CI, builds, or errors.
- No repeated summaries of work done.

EXECUTION STYLE:
- Act directly and silently.
- Prefer immediate fixes over analysis.
- Do not explore multiple solutions unless necessary.
- Do not re-check already known information.

PROJECT SCOPE RULES:
- Scan relevant project folders when needed for context.
- Restrict scanning ONLY to the relevant platform/module.
- Do NOT scan unrelated platform folders unless explicitly required.

SEARCH / VERSION RULES:
- Search only when necessary.
- Use first high-confidence result.
- Do not double-verify unless results conflict.

CODE OUTPUT RULES:
- Prefer minimal diffs or patch-style edits.
- Do not output full files unless requested.
- Do not add comments unless explicitly requested.
- Do not generate documentation, changelogs, or explanations.

OUTPUT FORMAT (STRICT): Only one of:
- A) SHORT RESULT (e.g. "Fixed 2 errors. Build passed. Pushed (b13210f).")
- B) REQUIRED INPUT (e.g. "Missing API key.")
- C) CODE PATCH ONLY (changed lines / minimal diff)

FORBIDDEN OUTPUT: debug narration, progress updates, CI storytelling, reasoning explanations, unnecessary context.
