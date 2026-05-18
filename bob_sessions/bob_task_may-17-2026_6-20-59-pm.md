**User:**

<task>
https://www.anthropic.com/engineering/effective-context-engineering-for-ai-agents 

Read this documentation, focus on the best practice while writing instruction

 Then create a skill for the agent to use when writing custom_modes or skill so that it can follow the best practice and have a balance of the instruction, the just right condition
</task>
<environment_details>
# VSCode Visible Files
../../../../../../Untitled-1

# VSCode Open Tabs
README.md,bob_session/bob_task_may-16-2026_4-36-56-pm.md,.bob/skills/SKILL.MD,../../../../../../Untitled-1

# Current Time
Current time in ISO 8601 UTC format: 2026-05-17T10:52:19.930Z
User time zone: Asia/Saigon, UTC+7:00

# Current Cost
$0.00

# Review Exclusions
The following file patterns are excluded from code reviews:
- .vscode/**

# Current Mode
<slug>code</slug>
<name>💻 Code</name>


# Current Workspace Directory (/Users/nvduc/projects/playground/ide-arena/ibm-bob) Files
BUG-REPORT-DESIGN-MODE.md
DESIGN-MODE-DEMO-SUMMARY.md
DOCUMENT-ORGANIZATION.md
IMPLEMENTATION-SUMMARY.md
instruction-guideline.md
LICENSE
README.md
validate-design-mode.py
.bob/
.vscode/
bob_session/
bob_session/bob_task_may-16-2026_4-36-56-pm.md
bob_session/bob_task_may-16-2026_5-10-59-pm.md
bob_session/bob_task_may-16-2026_5-11-30-pm.md
bob_session/bob_task_may-16-2026_5-32-50-pm.md
bob_session/bob_task_may-16-2026_6-08-35-pm.md
bob_session/bob_task_may-16-2026_6-37-28-pm.md
bob_session/bob_task_may-16-2026_6-53-36-pm.md
docs/
docs/design-mode/
docs/design-mode/bug-analysis.md
docs/design-mode/implementation-plan.md
docs/design-mode/implementation-summary.md
docs/design-mode/README.md
docs/design-mode/specification.md
docs/design-mode/test-execution-report.md
docs/design-mode/testing-guide.md
docs/design-mode/verification.md
hackathon-ideas/
hackathon-ideas/intent.md
hackathon-ideas/mcp-server-specifications.md
hackathon-ideas/migration-candidate-repositories.md
hackathon-ideas/migration-examples.md
hackathon-ideas/migration-framework-plan.md
hackathon-ideas/migration-skills-and-workflows.md
hackathon-ideas/migration-technical-specifications.md
hackathon-ideas/README-MIGRATION-SPECS.md
You have not created a todo list yet. Create one with `update_todo_list` if your task is complicated or involves multiple steps.
</environment_details>

---

**Assistant:**

I'll help you create a skill for writing effective custom modes and skills based on Anthropic's best practices. Let me start by reading the documentation from the URL you provided.

<execute_command>
<command>curl -s "https://www.anthropic.com/engineering/effective-context-engineering-for-ai-agents" | head -c 50000</command>
</execute_command>

---

**User:**

[execute_command for 'curl -s "https://www.anthropic.com/engineering/effective-context-engineering-for-ai-agents" | head -c 50000'] Result:
Command executed in terminal  within working directory '/Users/nvduc/projects/playground/ide-arena/ibm-bob'. Exit code: 0
Output:
<!DOCTYPE html><html lang="en" class="anthropicsans_dce02d96-module__tEBbKW__variable anthropicserif_e7e46c4-module__uImRTq__variable anthropicmono_fae19af3-module__c5XAsG__variable copernicus_4da799c5-module__dijTSq__variable styrenea_f8492ab1-module__HimLXW__variable styreneb_278af5c6-module__wkOAdG__variable tiempostext_4eff4b4c-module__mpviCW__variable jetbrainsmono_7d7bdbc6-module__j_XgJq__variable"><head><meta charSet="utf-8"/><meta name="viewport" content="width=device-width, initial-scale=1"/><link rel="stylesheet" href="/_next/static/chunks/365eca5272a6c334.css" nonce="ZDVmZGQwY2EtNTFhOS00ZTI3LWFlMzUtNGFlZThkODMwM2Fl" data-precedence="next"/><link rel="stylesheet" href="/_next/static/chunks/ae6fb50648020e93.css" nonce="ZDVmZGQwY2EtNTFhOS00ZTI3LWFlMzUtNGFlZThkODMwM2Fl" data-precedence="next"/><link rel="stylesheet" href="/_next/static/chunks/7eed6fc2b2823a30.css" nonce="ZDVmZGQwY2EtNTFhOS00ZTI3LWFlMzUtNGFlZThkODMwM2Fl" data-precedence="next"/><link rel="stylesheet" href="/_next/static/chunks/c3d043d1e33c2055.css" nonce="ZDVmZGQwY2EtNTFhOS00ZTI3LWFlMzUtNGFlZThkODMwM2Fl" data-precedence="next"/><link rel="stylesheet" href="/_next/static/chunks/314f1c6a326be8e1.css" nonce="ZDVmZGQwY2EtNTFhOS00ZTI3LWFlMzUtNGFlZThkODMwM2Fl" data-precedence="next"/><link rel="stylesheet" href="/_next/static/chunks/ab9150c7e5a90994.css" nonce="ZDVmZGQwY2EtNTFhOS00ZTI3LWFlMzUtNGFlZThkODMwM2Fl" data-precedence="next"/><link rel="preload" as="script" fetchPriority="low" nonce="ZDVmZGQwY2EtNTFhOS00ZTI3LWFlMzUtNGFlZThkODMwM2Fl" href="/_next/static/chunks/c193f64a7cab3c43.js"/><script src="/_next/static/chunks/5a7a4d76ee62080d.js" async="" nonce="ZDVmZGQwY2EtNTFhOS00ZTI3LWFlMzUtNGFlZThkODMwM2Fl"></script><script src="/_next/static/chunks/65f14fdeb6095697.js" async="" nonce="ZDVmZGQwY2EtNTFhOS00ZTI3LWFlMzUtNGFlZThkODMwM2Fl"></script><script src="/_next/static/chunks/bf5b036d6be8ffe6.js" async="" nonce="ZDVmZGQwY2EtNTFhOS00ZTI3LWFlMzUtNGFlZThkODMwM2Fl"></script><script src="/_next/static/chunks/turbopack-39eded8e57394c57.js" async="" nonce="ZDVmZGQwY2EtNTFhOS00ZTI3LWFlMzUtNGFlZThkODMwM2Fl"></script><script src="/_next/static/chunks/d96012bcfc98706a.js" async="" nonce="ZDVmZGQwY2EtNTFhOS00ZTI3LWFlMzUtNGFlZThkODMwM2Fl"></script><script src="/_next/static/chunks/d80b3790a119a285.js" async="" nonce="ZDVmZGQwY2EtNTFhOS00ZTI3LWFlMzUtNGFlZThkODMwM2Fl"></script><script src="/_next/static/chunks/aa5d7a654cf64ab6.js" async="" nonce="ZDVmZGQwY2EtNTFhOS00ZTI3LWFlMzUtNGFlZThkODMwM2Fl"></script><script src="/_next/static/chunks/3b0f2b73e7d4948a.js" async="" nonce="ZDVmZGQwY2EtNTFhOS00ZTI3LWFlMzUtNGFlZThkODMwM2Fl"></script><script src="/_next/static/chunks/c00ead7f80cb0801.js" async="" nonce="ZDVmZGQwY2EtNTFhOS00ZTI3LWFlMzUtNGFlZThkODMwM2Fl"></script><script src="/_next/static/chunks/89360d9cf87de183.js" async="" nonce="ZDVmZGQwY2EtNTFhOS00ZTI3LWFlMzUtNGFlZThkODMwM2Fl"></script><script src="/_next/static/chunks/b7a969922e8c6c60.js" async="" nonce="ZDVmZGQwY2EtNTFhOS00ZTI3LWFlMzUtNGFlZThkODMwM2Fl"></script><script src="/_next/static/chunks/d862445c897b25ca.js" async="" nonce="ZDVmZGQwY2EtNTFhOS00ZTI3LWFlMzUtNGFlZThkODMwM2Fl"></script><script src="/_next/static/chunks/c1779ba0b71566ef.js" async="" nonce="ZDVmZGQwY2EtNTFhOS00ZTI3LWFlMzUtNGFlZThkODMwM2Fl"></script><script src="/_next/static/chunks/440156871123c1de.js" async="" nonce="ZDVmZGQwY2EtNTFhOS00ZTI3LWFlMzUtNGFlZThkODMwM2Fl"></script><script src="/_next/static/chunks/bf2f35f74ea6758b.js" async="" nonce="ZDVmZGQwY2EtNTFhOS00ZTI3LWFlMzUtNGFlZThkODMwM2Fl"></script><script src="/_next/static/chunks/fba43d7d87521a3b.js" async="" nonce="ZDVmZGQwY2EtNTFhOS00ZTI3LWFlMzUtNGFlZThkODMwM2Fl"></script><meta name="next-size-adjust" content=""/><meta name="theme-color" content="#141413"/><script src="/_next/static/chunks/a6dad97d9634a72d.js" noModule="" nonce="ZDVmZGQwY2EtNTFhOS00ZTI3LWFlMzUtNGFlZThkODMwM2Fl"></script></head><body><div hidden=""><!--$?--><template id="B:0"></template><!--/$--></div><header class="SiteHeader-module-scss-module__zKj4Ca__header" data-theme="light"><div class="SiteHeader-module-scss-module__zKj4Ca__skipLinks"><a href="#main-content" class="SiteHeader-module-scss-module__zKj4Ca__skipLink">Skip to main content</a><a href="#footer" class="SiteHeader-module-scss-module__zKj4Ca__skipLink">Skip to footer</a></div><div class="page-wrapper SiteHeader-module-scss-module__zKj4Ca__root"><a href="/" aria-label="Home"><div class="SiteHeader-module-scss-module__zKj4Ca__logoDesktop"><div class="LogoWordmark-module-scss-module__Sdgt-q__logo-wrapper"><svg class="LogoWordmark-module-scss-module__Sdgt-q__logo-static" width="570" height="64" viewBox="0 0 570 64" fill="none" xmlns="http://www.w3.org/2000/svg" aria-label="Anthropic"><path d="M139.492 12.9945H160.265V62.9392H173.525V12.9945H194.298V1.06077H139.492V12.9945Z" fill="currentColor"></path><path d="M116.066 44.3757L88.221 1.06077H73.1934V62.9392H86.011V19.6243L113.856 62.9392H128.884V1.06077H116.066V44.3757Z" fill="currentColor"></path><path d="M247.337 25.7238H218.166V1.06077H204.906V62.9392H218.166V37.6575H247.337V62.9392H260.597V1.06077H247.337V25.7238Z" fill="currentColor"></path><path d="M24.663 1.06077L0 62.9392H13.7901L18.834 49.9447H44.6365L49.6796 62.9392H63.4696L38.8066 1.06077H24.663ZM23.2946 38.453L31.7348 16.7072L40.175 38.453H23.2946Z" fill="currentColor"></path><path d="M370.475 0C352.619 0 339.978 13.2597 339.978 32.0884C339.978 50.7403 352.619 64 370.475 64C388.243 64 400.796 50.7403 400.796 32.0884C400.796 13.2597 388.243 0 370.475 0ZM370.475 51.6243C360.044 51.6243 353.68 44.1989 353.68 32.0884C353.68 19.8011 360.044 12.3757 370.475 12.3757C380.818 12.3757 387.094 19.8011 387.094 32.0884C387.094 44.1989 380.818 51.6243 370.475 51.6243Z" fill="currentColor"></path><path d="M555.845 42.1657C553.547 48.1768 548.95 51.6243 542.674 51.6243C532.243 51.6243 525.878 44.1989 525.878 32.0884C525.878 19.8011 532.243 12.3757 542.674 12.3757C548.95 12.3757 553.547 15.8232 555.845 21.8343H569.901C566.453 8.57459 556.11 0 542.674 0C524.818 0 512.177 13.2597 512.177 32.0884C512.177 50.7403 524.818 64 542.674 64C556.199 64 566.541 55.337 569.989 42.1657H555.845Z" fill="currentColor"></path><path d="M471.337 1.06077L496 62.9392H509.525L484.862 1.06077H471.337Z" fill="currentColor"></path><path d="M443.403 1.06077H413.171V62.9392H426.431V40.4862H443.403C457.459 40.4862 466.033 33.0608 466.033 20.7735C466.033 8.48619 457.459 1.06077 443.403 1.06077ZM442.784 28.5525H426.431V12.9945H442.784C449.326 12.9945 452.773 15.6464 452.773 20.7735C452.773 25.9006 449.326 28.5525 442.784 28.5525Z" fill="currentColor"></path><path d="M329.812 19.8895C329.812 8.22099 321.238 1.06077 307.182 1.06077H276.95V62.9392H290.21V38.7182H304.971L318.232 62.9392H332.906L318.223 36.8734C325.593 34.0402 329.812 28.0743 329.812 19.8895ZM290.21 12.9945H306.564C313.105 12.9945 316.552 15.3812 316.552 19.8895C316.552 24.3978 313.105 26.7845 306.564 26.7845H290.21V12.9945Z" fill="currentColor"></path></svg><div class="LogoWordmark-module-scss-module__Sdgt-q__logo-lottie"></div></div></div><svg class="Icon-module-scss-module__lqbdHG__icon SiteHeader-module-scss-module__zKj4Ca__logoMobile" width="32" height="32" viewBox="0 0 46 32"><path d="M32.73 0h-6.945L38.45 32h6.945L32.73 0ZM12.665 0 0 32h7.082l2.59-6.72h13.25l2.59 6.72h7.082L19.929 0h-7.264Zm-.702 19.337 4.334-11.246 4.334 11.246h-8.668Z" fill="currentColor"></path></svg></a><div class="SiteHeader-module-scss-module__zKj4Ca__contentWrapper"><nav class="SiteHeader-module-scss-module__zKj4Ca__nav"><ul class="SiteHeader-module-scss-module__zKj4Ca__navList"><li class="body-3 SiteHeader-module-scss-module__zKj4Ca__navItem"><a href="/research" class="SiteHeader-module-scss-module__zKj4Ca__navText">Research</a></li><li class="body-3 SiteHeader-module-scss-module__zKj4Ca__navItem"><a href="/economic-futures" class="SiteHeader-module-scss-module__zKj4Ca__navText">Economic Futures</a></li><li class="body-3 SiteHeader-module-scss-module__zKj4Ca__navItem" data-category="Commitments"><button class="SiteHeader-module-scss-module__zKj4Ca__navText" aria-haspopup="menu" aria-expanded="false" aria-controls="nav-dropdown-Commitments"><span>Commitments</span><svg class="Icon-module-scss-module__lqbdHG__icon SiteHeader-module-scss-module__zKj4Ca__caretIcon" width="12" height="6.13" viewBox="0 0 8 5"><path d="M7.3016 0.231808C7.44932 0.0678162 7.70306 0.0546398 7.86724 0.20212C8.03137 0.349888 8.04461 0.603568 7.89692 0.767766L4.29684 4.76791L4.23434 4.82417C4.16662 4.87328 4.08425 4.89995 3.99918 4.89995C3.88588 4.89989 3.77733 4.85213 3.70152 4.76791L0.10144 0.767766L0.0537825 0.702139C-0.040206 0.541753 -0.0124254 0.331356 0.131128 0.20212C0.274775 0.0728844 0.486972 0.0674593 0.636608 0.1779L0.696765 0.231808L3.99918 3.90148L7.3016 0.231808Z" fill="currentColor"></path></svg></button></li><li class="body-3 SiteHeader-module-scss-module__zKj4Ca__navItem" data-category="Learn"><button class="SiteHeader-module-scss-module__zKj4Ca__navText" aria-haspopup="menu" aria-expanded="false" aria-controls="nav-dropdown-Learn"><span>Learn</span><svg class="Icon-module-scss-module__lqbdHG__icon SiteHeader-module-scss-module__zKj4Ca__caretIcon" width="12" height="6.13" viewBox="0 0 8 5"><path d="M7.3016 0.231808C7.44932 0.0678162 7.70306 0.0546398 7.86724 0.20212C8.03137 0.349888 8.04461 0.603568 7.89692 0.767766L4.29684 4.76791L4.23434 4.82417C4.16662 4.87328 4.08425 4.89995 3.99918 4.89995C3.88588 4.89989 3.77733 4.85213 3.70152 4.76791L0.10144 0.767766L0.0537825 0.702139C-0.040206 0.541753 -0.0124254 0.331356 0.131128 0.20212C0.274775 0.0728844 0.486972 0.0674593 0.636608 0.1779L0.696765 0.231808L3.99918 3.90148L7.3016 0.231808Z" fill="currentColor"></path></svg></button></li><li class="body-3 SiteHeader-module-scss-module__zKj4Ca__navItem"><a href="/news" class="SiteHeader-module-scss-module__zKj4Ca__navText">News</a></li></ul></nav><div class="SiteHeader-module-scss-module__zKj4Ca__claudeCtaWrapper"><a href="https://claude.ai/" class="SiteHeader-module-scss-module__zKj4Ca__claudeCtaButton body-3" target="_blank" rel="noopener noreferrer">Try Claude</a><div class="SiteHeader-module-scss-module__zKj4Ca__claudeCtaDropdownTrigger"><svg class="Icon-module-scss-module__lqbdHG__icon SiteHeader-module-scss-module__zKj4Ca__claudeCtaIcon" width="12" height="6.13" viewBox="0 0 8 5"><path d="M7.3016 0.231808C7.44932 0.0678162 7.70306 0.0546398 7.86724 0.20212C8.03137 0.349888 8.04461 0.603568 7.89692 0.767766L4.29684 4.76791L4.23434 4.82417C4.16662 4.87328 4.08425 4.89995 3.99918 4.89995C3.88588 4.89989 3.77733 4.85213 3.70152 4.76791L0.10144 0.767766L0.0537825 0.702139C-0.040206 0.541753 -0.0124254 0.331356 0.131128 0.20212C0.274775 0.0728844 0.486972 0.0674593 0.636608 0.1779L0.696765 0.231808L3.99918 3.90148L7.3016 0.231808Z" fill="currentColor"></path></svg></div></div><button class="SiteHeader-module-scss-module__zKj4Ca__mobileIcon" aria-label="Navigation menu"><svg class="Icon-module-scss-module__lqbdHG__icon" width="24" height="24" viewBox="0 0 40 40"><path d="M18.75 28C19.1641 28.0002 19.5 28.3359 19.5 28.75C19.4999 29.1641 19.164 29.4998 18.75 29.5H7.91699C7.50281 29.5 7.16705 29.1642 7.16699 28.75C7.16699 28.3358 7.50278 28 7.91699 28H18.75ZM32.084 19.25C32.4979 19.2504 32.834 19.586 32.834 20C32.8339 20.4139 32.4979 20.7496 32.084 20.75H7.91699C7.50281 20.75 7.16705 20.4142 7.16699 20C7.16699 19.5858 7.50278 19.25 7.91699 19.25H32.084ZM32.084 10.5C32.4979 10.5004 32.834 10.836 32.834 11.25C32.8339 11.6639 32.4979 11.9996 32.084 12H7.91699C7.50282 12 7.16706 11.6642 7.16699 11.25C7.16699 10.8358 7.50278 10.5 7.91699 10.5H32.084Z" fill="currentColor"></path></svg></button></div></div></header><main id="main-content" class=""><section class="page-wrapper HeroEngineering-module-scss-module__j1ivRa__hero" aria-label="Engineering Article Hero"><a class="body-2 bold HeroEngineering-module-scss-module__j1ivRa__hubLink" href="/engineering">Engineering at Anthropic</a><div class="HeroEngineering-module-scss-module__j1ivRa__content"><div class="HeroEngineering-module-scss-module__j1ivRa__header"><div class="HeroEngineering-module-scss-module__j1ivRa__heroImage"><img alt="" loading="lazy" width="1000" height="1000" decoding="async" data-nimg="1" style="color:transparent" src="https://www-cdn.anthropic.com/images/4zrzovbb/website/33b03d9dd1e6aec266ef22a616aec8f5676223f6-1000x1000.svg"/></div><h1 class="headline-1">Effective context engineering for AI agents</h1></div><div class="HeroEngineering-module-scss-module__j1ivRa__metadata"><p class="body-2 HeroEngineering-module-scss-module__j1ivRa__date">Published <!-- -->Sep 29, 2025</p><p class="body-large-1 HeroEngineering-module-scss-module__j1ivRa__summary">Context is a critical but finite resource for AI agents. In this post, we explore strategies for effectively curating and managing the context that powers them.</p></div></div></section><div class="page-wrapper"><article><div class=""><div class="Body-module-scss-module__z40yvW__body" data-theme="ivory"><p class="Body-module-scss-module__z40yvW__reading-column body-2 serif post-text">After a few years of prompt engineering being the focus of attention in applied AI, a new term has come to prominence: <strong>context engineering</strong>. Building with language models is becoming less about finding the right words and phrases for your prompts, and more about answering the broader question of “what configuration of context is most likely to generate our model’s desired behavior?&quot;</p><p class="Body-module-scss-module__z40yvW__reading-column body-2 serif post-text"></p><p class="Body-module-scss-module__z40yvW__reading-column body-2 serif post-text"><strong>Context</strong> refers to the set of tokens included when sampling from a large-language model (LLM). The <strong>engineering</strong> problem at hand is optimizing the utility of those tokens against the inherent constraints of LLMs in order to consistently achieve a desired outcome. Effectively wrangling LLMs often requires <em>thinking in context </em>— in other words: considering the holistic state available to the LLM at any given time and what potential behaviors that state might yield.</p><p class="Body-module-scss-module__z40yvW__reading-column body-2 serif post-text"></p><p class="Body-module-scss-module__z40yvW__reading-column body-2 serif post-text">In this post, we’ll explore the emerging art of context engineering and offer a refined mental model for building steerable, effective agents.</p><p class="Body-module-scss-module__z40yvW__reading-column body-2 serif post-text"></p><h2 class="Body-module-scss-module__z40yvW__reading-column headline-5 post-section" id="context-engineering-vs-prompt-engineering">Context engineering vs. prompt engineering</h2><p class="Body-module-scss-module__z40yvW__reading-column body-2 serif post-text">At Anthropic, we view context engineering as the natural progression of prompt engineering. Prompt engineering refers to methods for writing and organizing LLM instructions for optimal outcomes (see <a href="https://docs.anthropic.com/en/docs/build-with-claude/prompt-engineering/overview">our docs</a> for an overview and useful prompt engineering strategies). <strong>Context engineering</strong> refers to the set of strategies for curating and maintaining the optimal set of tokens (information) during LLM inference, including all the other information that may land there outside of the prompts.</p><p class="Body-module-scss-module__z40yvW__reading-column body-2 serif post-text">In the early days of engineering with LLMs, prompting was the biggest component of AI engineering work, as the majority of use cases outside of everyday chat interactions required prompts optimized for one-shot classification or text generation tasks. As the term implies, the primary focus of prompt engineering is how to write effective prompts, particularly system prompts. However, as we move towards engineering more capable agents that operate over multiple turns of inference and longer time horizons, we need strategies for managing the entire context state (system instructions, tools, <a href="https://modelcontextprotocol.io/docs/getting-started/intro">Model Context Protocol</a> (MCP), external data, message history, etc).</p><p class="Body-module-scss-module__z40yvW__reading-column body-2 serif post-text">An agent running in a loop generates more and more data that <em>could</em> be relevant for the next turn of inference, and this information must be cyclically refined. Context engineering is the <a href="https://x.com/karpathy/status/1937902205765607626?lang=en">art and science</a> of curating what will go into the limited context window from that constantly evolving universe of possible information.</p><div class="Body-module-scss-module__z40yvW__media-column Body-module-scss-module__z40yvW__inline"><figure class="ImageWithCaption-module-scss-module__Duq99q__e-imageWithCaption"><img alt="Prompt engineering vs. context engineering" loading="lazy" width="2292" height="1290" decoding="async" data-nimg="1" style="color:transparent" srcSet="/_next/image?url=https%3A%2F%2Fwww-cdn.anthropic.com%2Fimages%2F4zrzovbb%2Fwebsite%2Ffaa261102e46c7f090a2402a49000ffae18c5dd6-2292x1290.png&amp;w=3840&amp;q=75 1x" src="/_next/image?url=https%3A%2F%2Fwww-cdn.anthropic.com%2Fimages%2F4zrzovbb%2Fwebsite%2Ffaa261102e46c7f090a2402a49000ffae18c5dd6-2292x1290.png&amp;w=3840&amp;q=75"/><figcaption class="caption"><em>In contrast to the discrete task of writing a prompt, context engineering is iterative and the curation phase happens each time we decide what to pass to the model.</em></figcaption></figure></div><p class="Body-module-scss-module__z40yvW__reading-column body-2 serif post-text"></p><h2 class="Body-module-scss-module__z40yvW__reading-column headline-5 post-section" id="why-context-engineering-is-important-to-building-capable-agents">Why context engineering is important to building capable agents</h2><p class="Body-module-scss-module__z40yvW__reading-column body-2 serif post-text">Despite their speed and ability to manage larger and larger volumes of data, we’ve observed that LLMs, like humans, lose focus or experience confusion at a certain point. Studies on needle-in-a-haystack<em> </em>style benchmarking have uncovered the concept of <a href="https://research.trychroma.com/context-rot">context rot</a>: as the number of tokens in the context window increases, the model’s ability to accurately recall information from that context decreases.</p><p class="Body-module-scss-module__z40yvW__reading-column body-2 serif post-text">While some models exhibit more gentle degradation than others, this characteristic emerges across all models. Context, therefore, must be treated as a finite resource with diminishing marginal returns. Like humans, who have <a href="https://journals.sagepub.com/doi/abs/10.1177/0963721409359277">limited working memory capacity</a>, LLMs have an “attention budget” that they draw on when parsing large volumes of context. Every new token introduced depletes this budget by some amount, increasing the need to carefully curate the tokens available to the LLM.</p><p class="Body-module-scss-module__z40yvW__reading-column body-2 serif post-text">This attention scarcity stems from architectural constraints of LLMs. LLMs are based on the <a href="https://arxiv.org/abs/1706.03762">transformer architecture</a>, which enables every token to <a href="https://huggingface.co/blog/Esmail-AGumaan/attention-is-all-you-need">attend to every other token</a> across the entire context. This results in n² pairwise relationships for n tokens.</p><p class="Body-module-scss-module__z40yvW__reading-column body-2 serif post-text">As its context length increases, a model&#x27;s ability to capture these pairwise relationships gets stretched thin, creating a natural tension between context size and attention focus. Additionally, models develop their attention patterns from training data distributions where shorter sequences are typically more common than longer ones. This means models have less experience with, and fewer specialized parameters for, context-wide dependencies.</p><p class="Body-module-scss-module__z40yvW__reading-column body-2 serif post-text">Techniques like <a href="https://arxiv.org/pdf/2306.15595">position encoding interpolation</a> allow models to handle longer sequences by adapting them to the originally trained smaller context, though with some degradation in token position understanding. These factors create a performance gradient rather than a hard cliff: models remain highly capable at longer contexts but may show reduced precision for information retrieval and long-range reasoning compared to their performance on shorter contexts.</p><p class="Body-module-scss-module__z40yvW__reading-column body-2 serif post-text">These realities mean that thoughtful context engineering is essential for building capable agents.</p><h2 class="Body-module-scss-module__z40yvW__reading-column headline-5 post-section" id="the-anatomy-of-effective-context">The anatomy of effective context</h2><p class="Body-module-scss-module__z40yvW__reading-column body-2 serif post-text">Given that LLMs are constrained by a finite attention budget, <em>good</em> context engineering means finding the <em>smallest</em> <em>possible</em> set of high-signal tokens that maximize the likelihood of some desired outcome. Implementing this practice is much easier said than done, but in the following section, we outline what this guiding principle means in practice across the different components of context.</p><p class="Body-module-scss-module__z40yvW__reading-column body-2 serif post-text"><strong>System prompts</strong> should be extremely clear and use simple, direct language that presents ideas at the <em>right altitude </em>for the agent. The right altitude is the Goldilocks zone between two common failure modes. At one extreme, we see engineers hardcoding complex, brittle logic in their prompts to elicit exact agentic behavior. This approach creates fragility and increases maintenance complexity over time. At the other extreme, engineers sometimes provide vague, high-level guidance that fails to give the LLM concrete signals for desired outputs or falsely assumes shared context. The optimal altitude strikes a balance: specific enough to guide behavior effectively, yet flexible enough to provide the model with strong heuristics to guide behavior.</p><div class="Body-module-scss-module__z40yvW__media-column Body-module-scss-module__z40yvW__inline"><figure class="ImageWithCaption-module-scss-module__Duq99q__e-imageWithCaption"><img alt="Calibrating the system prompt in the process of context engineering." loading="lazy" width="2292" height="1288" decoding="async" data-nimg="1" style="color:transparent" srcSet="/_next/image?url=https%3A%2F%2Fwww-cdn.anthropic.com%2Fimages%2F4zrzovbb%2Fwebsite%2F0442fe138158e84ffce92bed1624dd09f37ac46f-2292x1288.png&amp;w=3840&amp;q=75 1x" src="/_next/image?url=https%3A%2F%2Fwww-cdn.anthropic.com%2Fimages%2F4zrzovbb%2Fwebsite%2F0442fe138158e84ffce92bed1624dd09f37ac46f-2292x1288.png&amp;w=3840&amp;q=75"/><figcaption class="caption"><em>At one end of the spectrum, we see brittle if-else hardcoded prompts, and at the other end we see prompts that are overly general or falsely assume shared context.</em></figcaption></figure></div><p class="Body-module-scss-module__z40yvW__reading-column body-2 serif post-text">We recommend organizing prompts into distinct sections (like <code class="InlineCodeBlock-module-scss-module__nsPAba__code">&lt;background_information&gt;</code>, <code class="InlineCodeBlock-module-scss-module__nsPAba__code">&lt;instructions&gt;</code>, <code class="InlineCodeBlock-module-scss-module__nsPAba__code">## Tool guidance</code>, <code class="InlineCodeBlock-module-scss-module__nsPAba__code">## Output description</code>, etc) and using techniques like XML tagging or Markdown headers to delineate these sections, although the exact formatting of prompts is likely becoming less important as models become more capable.</p><p class="Body-module-scss-module__z40yvW__reading-column body-2 serif post-text"></p><p class="Body-module-scss-module__z40yvW__reading-column body-2 serif post-text">Regardless of how you decide to structure your system prompt, you should be striving for the minimal set of information that fully outlines your expected behavior. (Note that minimal does not necessarily mean short; you still need to give the agent sufficient information up front to ensure it adheres to the desired behavior.) It’s best to start by testing a minimal prompt with the best model available to see how it performs on your task, and then add clear instructions and examples to improve performance based on failure modes found during initial testing.</p><p class="Body-module-scss-module__z40yvW__reading-column body-2 serif post-text"><strong>Tools</strong> allow agents to operate with their environment and pull in new, additional context as they work. Because tools define the contract between agents and their information/action space, it’s extremely important that tools promote efficiency, both by returning information that is token efficient and by encouraging efficient agent behaviors.</p><p class="Body-module-scss-module__z40yvW__reading-column body-2 serif post-text">In <a href="https://www.anthropic.com/engineering/writing-tools-for-agents">Writing tools for AI agents – with AI agents</a>, we discussed building tools that are well understood by LLMs and have minimal overlap in functionality. Similar to the functions of a well-designed codebase, tools should be self-contained, robust to error, and extremely clear with respect to their intended use. Input parameters should similarly be descriptive, unambiguous, and play to the inherent strengths of the model.</p><p class="Body-module-scss-module__z40yvW__reading-column body-2 serif post-text">One of the most common failure modes we see is bloated tool sets that cover too much functionality or lead to ambiguous decision points about which tool to use. If a human engineer can’t definitively say which tool should be used in a given situation, an AI agent can’t be expected to do better. As we’ll discuss later, curating a minimal viable set of tools for the agent can also lead to more reliable maintenance and pruning of context over long interactions.</p><p class="Body-module-scss-module__z40yvW__reading-column body-2 serif post-text">Providing examples, otherwise known as few-shot prompting, is a well known best practice that we continue to strongly advise. However, teams will often stuff a laundry list of edge cases into a prompt in an attempt to articulate every possible rule the LLM should follow for a particular task. We do not recommend this. Instead, we recommend working to curate a set of diverse, canonical examples that effectively portray the expected behavior of the agent. For an LLM, examples are the “pictures” worth a thousand words.</p><p class="Body-module-scss-module__z40yvW__reading-column body-2 serif post-text">Our overall guidance across the different components of context (system prompts<strong>, </strong>tools<strong>, </strong>examples<strong>, </strong>message history, etc) is to be thoughtful and keep your context informative, yet tight. Now let&#x27;s dive into dynamically retrieving context at runtime.</p><h2 class="Body-module-scss-module__z40yvW__reading-column headline-5 post-section" id="context-retrieval-and-agentic-search">Context retrieval and agentic search</h2><p class="Body-module-scss-module__z40yvW__reading-column body-2 serif post-text">In <a href="https://www.anthropic.com/research/building-effective-agents">Building effective AI agents</a>, we highlighted the differences between LLM-based workflows and agents. Since we wrote that post, we’ve gravitated towards a <a href="https://simonwillison.net/2025/Sep/18/agents/">simple definition</a> for agents: LLMs autonomously using tools in a loop.</p><p class="Body-module-scss-module__z40yvW__reading-column body-2 serif post-text">Working alongside our customers, we’ve seen the field converging on this simple paradigm. As the underlying models become more capable, the level of autonomy of agents can scale: smarter models allow agents to independently navigate nuanced problem spaces and recover from errors.</p><p class="Body-module-scss-module__z40yvW__reading-column body-2 serif post-text">We’re now seeing a shift in how engineers think about designing context for agents. Today, many AI-native applications employ some form of embedding-based pre-inference time retrieval to surface important context for the agent to reason over. As the field transitions to more agentic approaches, we increasingly see teams augmenting these retrieval systems with “just in time” context strategies.</p><p class="Body-module-scss-module__z40yvW__reading-column body-2 serif post-text">Rather than pre-processing all relevant data up front, agents built with the “just in time” approach maintain lightweight identifiers (file paths, stored queries, web links, etc.) and use these references to dynamically load data into context at runtime using tools. Anthropic’s agentic coding solution <a href="https://www.anthropic.com/claude-code">Claude Code</a> uses this approach to perform complex data analysis over large databases. The model can write targeted queries, store results, and leverage Bash commands like head and tail to analyze large volumes of data without ever loading the full data objects into context. This approach mirrors human cognition: we generally don’t memorize entire corpuses of information, but rather introduce external organization and indexing systems like file systems, inboxes, and bookmarks to retrieve relevant information on demand.</p><p class="Body-module-scss-module__z40yvW__reading-column body-2 serif post-text">Beyond storage efficiency, the metadata of these references provides a mechanism to efficiently refine behavior, whether explicitly provided or intuitive. To an agent operating in a file system, the presence of a file named <code class="InlineCodeBlock-module-scss-module__nsPAba__code">test_utils.py</code> in a <code class="InlineCodeBlock-module-scss-module__nsPAba__code">tests</code> folder implies a different purpose than a file with the same name located in <code class="InlineCodeBlock-module-scss-module__nsPAba__code">src/core_logic/</code> Folder hierarchies, naming conventions, and timestamps all provide important signals that help both humans and agents understand how and when to utilize information.</p><p class="Body-module-scss-module__z40yvW__reading-column body-2 serif post-text">Letting agents navigate and retrieve data autonomously also enables progressive disclosure—in other words, allows agents to incrementally discover relevant context through exploration. Each interaction yields context that informs the next decision: file sizes suggest complexity; naming conventions hint at purpose; timestamps can be a proxy for relevance. Agents can assemble understanding layer by layer, maintaining only what&#x27;s necessary in working memory and leveraging note-taking strategies for additional persistence. This self-managed context window keeps the agent focused on relevant subsets rather than drowning in exhaustive but potentially irrelevant information.</p><p class="Body-module-scss-module__z40yvW__reading-column body-2 serif post-text">Of course, there&#x27;s a trade-off: runtime exploration is slower than retrieving pre-computed data. Not only that, but opinionated and thoughtful engineering is required to ensure that an LLM has the right tools and heuristics for effectively navigating its information landscape. Without proper guidance, an agent can waste context by misusing tools, chasing dead-ends, or failing to identify key information.</p><p class="Body-module-scss-module__z40yvW__reading-column body-2 serif post-text">In certain settings, the most effective agents might employ a hybrid strategy, retrieving some data up front for speed, and pursuing further autonomous exploration at its discretion. The decision boundary for the ‘right’ level of autonomy depends on the task. Claude Code is an agent that employs this hybrid model: <a href="http://claude.md">CLAUDE.md</a> files are naively dropped into context up front, while primitives like glob and grep allow it to navigate its environment and retrieve files just-in-time, effectively bypassing the issues of stale indexing and complex syntax trees.</p><p class="Body-module-scss-module__z40yvW__reading-column body-2 serif post-text">The hybrid strategy might be better suited for contexts with less dynamic content, such as legal or finance work. As model capabilities improve, agentic design will trend towards letting intelligent models act intelligently, with progressively less human curation. Given the rapid pace of progress in the field, &quot;do the simplest thing that works&quot; will likely remain our best advice for teams building agents on top of Claude.</p><h3 class="Body-module-scss-module__z40yvW__reading-column headline-6 post-subsection" id="context-engineering-for-long-horizon-tasks">Context engineering for long-horizon tasks</h3><p class="Body-module-scss-module__z40yvW__reading-column body-2 serif post-text">Long-horizon tasks require agents to maintain coherence, context, and goal-directed behavior over sequences of actions where the token count exceeds the LLM’s context window. For tasks that span tens of minutes to multiple hours of continuous work, like large codebase migrations or comprehensive research projects, agents require specialized techniques to work around the context window size limitation.</p><p class="Body-module-scss-module__z40yvW__reading-column body-2 serif post-text">Waiting for larger context windows might seem like an obvious tactic. But it&#x27;s likely that for the foreseeable future, context windows of all sizes will be subject to context pollution and information relevance concerns—at least for situations where the strongest agent performance is desired. To enable agents to work effectively across extended time horizons, we&#x27;ve developed a few techniques that address these context pollution constraints directly: compaction, structured note-taking, and multi-agent architectures.</p><p class="Body-module-scss-module__z40yvW__reading-column body-2 serif post-text"><strong>Compaction</strong></p><p class="Body-module-scss-module__z40yvW__reading-column body-2 serif post-text">Compaction is the practice of taking a conversation nearing the context window limit, summarizing its contents, and reinitiating a new context window with the summary. Compaction typically serves as the first lever in context engineering to drive better long-term coherence. At its core, compaction distills the contents of a context window in a high-fidelity manner, enabling the agent to continue with minimal performance degradation.</p><p class="Body-module-scss-module__z40yvW__reading-column body-2 serif post-text">In Claude Code, for example, we implement this by passing the message history to the model to summarize and compress the most critical details. The model preserves architectural decisions, unresolved bugs, and implementation details while discarding redundant tool outputs or messages. The agent can then continue with this compressed context plus the five most recently accessed files. Users get continuity without worrying about context window limitations.</p><p class="Body-module-scss-module__z40yvW__reading-column body-2 serif post-text">The art of compaction lies in the selection of what to keep versus what to discard, as overly aggressive compaction can result in the loss of subtle but critical context whose importance only becomes apparent later. For engineers implementing compaction systems, we recommend carefully tuning your prompt on  complex agent traces. Start by maximizing recall to ensure your compaction prompt captures every relevant piece of information from the trace, then iterate to improve precision by eliminating superfluous content.</p><p class="Body-module-scss-module__z40yvW__reading-column body-2 serif post-text">An example of low-hanging superfluous content is clearing tool calls and results – once a tool has been called deep in the message history, why would the agent need to see the raw result again? One of the safest lightest touch forms of compaction is tool result clearing, most recently launched as a <a href="https://www.anthropic.com/news/context-management">feature on the Claude Developer Platform</a>.</p><p class="Body-module-scss-module__z40yvW__reading-column body-2 serif post-text"><strong>Structured note-taking</strong></p><p class="Body-module-scss-module__z40yvW__reading-column body-2 serif post-text">Structured note-taking, or agentic memory, is a technique where the agent regularly writes notes persisted to memory outside of the context window. These notes get pulled back into the context window at later times.</p><p class="Body-module-scss-module__z40yvW__reading-column body-2 serif post-text">This strategy provides persistent memory with minimal overhead. Like Claude Code creating a to-do list, or your custom agent maintaining a NOTES.md file, this simple pattern allows the agent to track progress across complex tasks, maintaining critical context and dependencies that would otherwise be lost across dozens of tool calls.</p><p class="Body-module-scss-module__z40yvW__reading-column body-2 serif post-text"><a href="https://www.twitch.tv/claudeplayspokemon">Claude playing Pokémon</a> demonstrates how memory transforms agent capabilities in non-coding domains. The agent maintains precise tallies across thousands of game steps—tracking objectives like &quot;for the last 1,234 steps I&#x27;ve been training my Pokémon in Route 1, Pikachu has gained 8 levels toward the target of 10.&quot; Without any prompting about memory structure, it develops maps of explored regions, remembers which key achievements it has unlocked, and maintains strategic notes of combat strategies that help it learn which attacks work best against different opponents.</p><p class="Body-module-scss-module__z40yvW__reading-column body-2 serif post-text">After context resets, the agent reads its own notes and continues multi-hour training sequences or dungeon explorations. This coherence across summarization steps enables long-horizon strategies that would be impossible when keeping all the information in the LLM’s context window alone.</p><p class="Body-module-scss-module__z40yvW__reading-column body-2 serif post-text">As part of our <a href="https://www.anthropic.com/effective-context-engineering-for-ai-agents">Sonnet 4.5 launch</a>, we released <a href="http://anthropic.com/news/context-management">a memory tool</a> in public beta on the Claude Developer Platform that makes it easier to store and consult information outside the context window through a file-based system. This allows agents to build up knowledge bases over time, maintain project state across sessions, and reference previous work without keeping everything in context.</p><p class="Body-module-scss-module__z40yvW__reading-column body-2 serif post-text"><strong>Sub-agent architectures</strong></p><p class="Body-module-scss-module__z40yvW__reading-column body-2 serif post-text">Sub-agent architectures provide another way around context limitations. Rather than one agent attempting to maintain state across an entire project, specialized sub-agents can handle focused tasks with clean context windows. The main agent coordinates with a high-level plan while subagents perform deep technical work or use tools to find relevant information. Each subagent might explore extensively, using tens of thousands of tokens or more, but returns only a condensed, distilled summary of its work (often 1,000-2,000 tokens).</p><p class="Body-module-scss-module__z40yvW__reading-column body-2 serif post-text">This approach achieves a clear separation of concerns—the detailed search context remains isolated within sub-agents, while the lead agent focuses on synthesizing and analyzing the results. This pattern, discussed in <a href="https://www.anthropic.com/engineering/multi-agent-research-system">How we built our multi-agent research system</a>, showed a substantial improvement over single-agent systems on complex research tasks.</p><p class="Body-module-scss-module__z40yvW__reading-column body-2 serif post-text">The choice between these approaches depends on task characteristics. For example:</p><ul class="Body-module-scss-module__z40yvW__reading-column body-2 serif post-text"><li>Compaction maintains conversational flow for tasks requiring extensive back-and-forth;</li><li>Note-taking excels for iterative development with clear milestones;</li><li>Multi-agent architectures handle complex research and analysis where parallel exploration pays dividends.</li></ul><p class="Body-module-scss-module__z40yvW__reading-column body-2 serif post-text">Even as models continue to improve, the challenge of maintaining coherence across extended interactions will remain central to building more effective agents.</p><h2 class="Body-module-scss-module__z40yvW__reading-column headline-5 post-section" id="conclusion">Conclusion</h2><p class="Body-module-scss-module__z40yvW__reading-column body-2 serif post-text">Context engineering represents a fundamental shift in how we build with LLMs. As models become more capable, the challenge isn&#x27;t just crafting the perfect prompt—it&#x27;s thoughtfully curating what information enters the model&#x27;s limited attention budget at each step. Whether you&#x27;re implementing compaction for long-horizon tasks, designing token-efficient tools, or enabling agents to explore their environment just-in-time, the guiding principle remains the same: find the smallest set of high-signal tokens that maximize the likelihood of your desired outcome.</p><p class="Body-module-scss-module__z40yvW__reading-column body-2 serif post-text">The techniques we&#x27;ve outlined will continue evolving as models improve. We&#x27;re already seeing that smarter models require less prescriptive engineering, allowing agents to operate with more autonomy. But even as capabilities scale, treating context as a precious, finite resource will remain central to building reliable, effective agents.</p><p class="Body-module-scss-module__z40yvW__reading-column body-2 serif post-text">Get started with context engineering in the Claude Developer Platform today, and access helpful tips and best practices via our <a href="https://platform.claude.com/cookbook/tool-use-memory-cookbook" target="_blank" rel="noopener noreferrer">memory and context management</a> cookbook.</p><h2 class="Body-module-scss-module__z40yvW__reading-column headline-5 post-section" id="acknowledgements">Acknowledgements</h2><p class="Body-module-scss-module__z40yvW__reading-column body-2 serif post-text">Written by Anthropic&#x27;s Applied AI team: Prithvi Rajasekaran, Ethan Dixon, Carly Ryan, and Jeremy Hadfield, with contributions from team members Rafi Ayub, Hannah Moran, Cal Rueb, and Connor Jennings. Special thanks to Molly Vorwerck, Stuart Ritchie, and Maggie Vo for their support.</p><p class="Body-module-scss-module__z40yvW__reading-column body-2 serif post-text"><br/></p></div></div></article><div class="NewsletterSubscribe-module-scss-module__MOPAja__wrapper"><div class="NewsletterSubscribe-module-scss-module__MOPAja__content"><div class="NewsletterSubscribe-module-scss-module__MOPAja__text-content"><h2 class="headline-5 NewsletterSubscribe-module-scss-module__MOPAja__title">Get the developer newsletter</h2><div class="NewsletterSubscribe-module-scss-module__MOPAja__body"><p class="body-1 serif tight">Product updates, how-tos, community spotlights, and more. Delivered monthly to your inbox.</p></div></div><div class="NewsletterSubscribe-module-scss-module__MOPAja__form-container"><form class="NewsletterSubscribe-module-scss-module__MOPAja__email-form"><div class="NewsletterSubscribe-module-scss-module__MOPAja__input-wrapper"><input type="email" aria-label="Email address" placeholder="Enter your email" class="NewsletterSubscribe-module-scss-module__MOPAja__email-input" required="" name="email" value=""/><button type="submit" class="NewsletterSubscribe-module-scss-module__MOPAja__submit-button" aria-label="Subscribe"><svg class="Icon-module-scss-module__lqbdHG__icon" width="20" height="20" viewBox="0 0 21 21"><path d="M4.14585 9.87492L14.4584 9.87492L9.60419 5.04158L10.5 4.14575L16.8542 10.4999L10.5 16.8541L9.60419 15.9583L14.4584 11.1249L4.14585 11.1249L4.14585 9.87492Z" fill="currentColor"></path></svg></button></div><p class="body-3 NewsletterSubscribe-module-scss-module__MOPAja__disclaimer">Please provide your email address if you&#x27;d like to receive our monthly developer newsletter. You can unsubscribe at any time.</p></form></div></div></div></div></main><footer id="footer" class="SiteFooter-module-scss-module__JdOqwq__root" role="contentinfo" aria-label="Site footer"><div class="page-wrapper SiteFooter-module-scss-module__JdOqwq__footer"><div class="SiteFooter-module-scss-module__JdOqwq__logoWrapper"><a href="/" aria-label="Return to homepage"><svg class="Icon-module-scss-module__lqbdHG__icon" width="46" height="32" viewBox="0 0 46 32"><path d="M32.73 0h-6.945L38.45 32h6.945L32.73 0ZM12.665 0 0 32h7.082l2.59-6.72h13.25l2.59 6.72h7.082L19.929 0h-7.264Zm-.702 19.337 4.334-11.246 4.334 11.246h-8.668Z" fill="#faf9f5"></path></svg></a></div><nav class="SiteFooter-module-scss-module__JdOqwq__linksWrapper" aria-label="Footer navigation"><div class="SiteFooter-module-scss-module__JdOqwq__columnSection"><div class="SiteFooter-module-scss-module__JdOqwq__listSection"><h3 class="body-4 bold">Products</h3><ul class="SiteFooter-module-scss-module__JdOqwq__list"><li><a href="https://claude.com/product/overview" class="SiteFooter-module-scss-module__JdOqwq__listItem body-4" target="_blank" rel="noopener noreferrer">Claude</a></li><li><a href="https://claude.com/product/claude-code" class="SiteFooter-module-scss-module__JdOqwq__listItem body-4" target="_blank" rel="noopener noreferrer">Claude Code</a></li><li><a href="https://claude.com/product/claude-code/enterprise" class="SiteFooter-module-scss-module__JdOqwq__listItem body-4" target="_blank" rel="noopener noreferrer">Claude Code Enterprise</a></li><li><a href="https://claude.com/product/cowork" class="SiteFooter-module-scss-module__JdOqwq__listItem body-4" target="_blank" rel="noopener noreferrer">Claude Cowork</a></li><li><a href="https://claude.com/product/claude-security" class="SiteFooter-module-scss-module__JdOqwq__listItem body-4" target="_blank" rel="noopener noreferrer">Claude Security</a></li><li><a href="https://claude.com/chrome" class="SiteFooter-module-scss-module__JdOqwq__listItem body-4" target="_blank" rel="noopener noreferrer">Claude for Chrome</a></li><li><a href="https://claude.com/claude-for-slack" class="SiteFooter-module-scss-module__JdOqwq__listItem body-4" target="_blank" rel="noopener noreferrer">Claude for Slack</a></li><li><a href="https://claude.com/claude-for-microsoft-365" class="SiteFooter-module-scss-module__JdOqwq__listItem body-4" target="_blank" rel="noopener noreferrer">Claude for Microsoft 365</a></li><li><a href="https://www.claude.com/skills" class="SiteFooter-module-scss-module__JdOqwq__listItem body-4" target="_blank" rel="noopener noreferrer">Skills</a></li><li><a href="https://claude.com/pricing/max" class="SiteFooter-module-scss-module__JdOqwq__listItem body-4" target="_blank" rel="noopener noreferrer">Max plan</a></li><li><a href="https://claude.com/pricing/team" class="SiteFooter-module-scss-module__JdOqwq__listItem body-4" target="_blank" rel="noopener noreferrer">Team plan</a></li><li><a href="https://claude.com/pricing/enterprise" class="SiteFooter-module-scss-module__JdOqwq__listItem body-4" target="_blank" rel="noopener noreferrer">Enterprise plan</a></li><li><a href="https://claude.ai/download" class="SiteFooter-module-scss-module__JdOqwq__listItem body-4" target="_blank" rel="noopener noreferrer">Download app</a></li><li><a href="https://claude.com/pricing" class="SiteFooter-module-scss-module__JdOqwq__listItem body-4" target="_blank" rel="noopener noreferrer">Pricing</a></li><li><a href="https://claude.ai/" class="SiteFooter-module-scss-module__JdOqwq__listItem body-4" target="_blank" rel="noopener noreferrer">Log in to Claude</a></li></ul></div><div class="SiteFooter-module-scss-module__JdOqwq__listSection"><h3 class="body-4 bold">Models</h3><ul class="SiteFooter-module-scss-module__JdOqwq__list"><li><a href="https://www.anthropic.com/glasswing" class="SiteFooter-module-scss-module__JdOqwq__listItem body-4">Mythos Preview</a></li><li><a href="https://www.anthropic.com/claude/opus" class="SiteFooter-module-scss-module__JdOqwq__listItem body-4">Opus</a></li><li><a href="https://www.anthropic.com/claude/sonnet" class="SiteFooter-module-scss-module__JdOqwq__listItem body-4">Sonnet</a></li><li><a href="https://www.anthropic.com/claude/haiku" class="SiteFooter-module-scss-module__JdOqwq__listItem body-4">Haiku</a></li></ul></div></div><div class="SiteFooter-module-scss-module__JdOqwq__columnSection"><div class="SiteFooter-module-scss-module__JdOqwq__listSection"><h3 class="body-4 bold">Solutions</h3><ul class="SiteFooter-module-scss-module__JdOqwq__list"><li><a href="https://claude.com/solutions/agents" class="SiteFooter-module-scss-module__JdOqwq__listItem body-4" target="_blank" rel="noopener noreferrer">AI agents</a></li><li><a href="https://claude.com/solutions/code-modernization" class="SiteFooter-module-scss-module__JdOqwq__listItem body-4" target="_blank" rel="noopener noreferrer">Code modernization</a></li><li><a href="
<environment_details>
# VSCode Visible Files
../../../../../../Untitled-1

# VSCode Open Tabs
README.md,bob_session/bob_task_may-16-2026_4-36-56-pm.md,.bob/skills/SKILL.MD,../../../../../../Untitled-1

# Current Time
Current time in ISO 8601 UTC format: 2026-05-17T10:52:49.812Z
User time zone: Asia/Saigon, UTC+7:00

# Current Cost
$0.01

# Review Exclusions
The following file patterns are excluded from code reviews:
- .vscode/**

# Current Mode
<slug>code</slug>
<name>💻 Code</name>
You have not created a todo list yet. Create one with `update_todo_list` if your task is complicated or involves multiple steps.
</environment_details>

---

**Assistant:**

I can see the HTML content from the Anthropic documentation. Let me extract the key content more effectively by using a different approach to get the actual article text.

<execute_command>
<command>curl -s "https://www.anthropic.com/engineering/effective-context-engineering-for-ai-agents" | grep -oP '(?<=<p class="Body-module-scss-module__z40yvW__reading-column body-2 serif post-text">).*?(?=</p>)' | head -50</command>
</execute_command>

---

**User:**

[execute_command for 'curl -s "https://www.anthropic.com/engineering/effective-context-engineering-for-ai-agents" | grep -oP '(?<=<p class="Body-module-scss-module__z40yvW__reading-column body-2 serif post-text">).*?(?=</p>)' | head -50'] Result:
Command executed in terminal  within working directory '/Users/nvduc/projects/playground/ide-arena/ibm-bob'. Exit code: 0
Output:
grep: invalid option -- P
usage: grep [-abcdDEFGHhIiJLlMmnOopqRSsUVvwXxZz] [-A num] [-B num] [-C[num]]
	[-e pattern] [-f file] [--binary-files=value] [--color=when]
	[--context[=num]] [--directories=action] [--label] [--line-buffered]
	[--null] [pattern] [file ...]

<environment_details>
# VSCode Visible Files
../../../../../../Untitled-1

# VSCode Open Tabs
README.md,bob_session/bob_task_may-16-2026_4-36-56-pm.md,.bob/skills/SKILL.MD,../../../../../../Untitled-1

# Current Time
Current time in ISO 8601 UTC format: 2026-05-17T10:53:01.415Z
User time zone: Asia/Saigon, UTC+7:00

# Current Cost
$0.06

# Review Exclusions
The following file patterns are excluded from code reviews:
- .vscode/**

# Current Mode
<slug>code</slug>
<name>💻 Code</name>
You have not created a todo list yet. Create one with `update_todo_list` if your task is complicated or involves multiple steps.
</environment_details>

---

**Assistant:**

Let me check the existing skills directory structure first to understand the format better.

<read_file>
<args>
<file>
<path>.bob/skills/SKILL.MD</path>
</file>
</args>
</read_file>

---

**User:**

[read_file for '.bob/skills/SKILL.MD'. Reading multiple files at once is more efficient for the LLM. If other files are relevant to your current task, please read them simultaneously.] Result:
<files>
<file><path>.bob/skills/SKILL.MD</path>
<content lines="1-10">
 1 | ---
 2 | name: grill-me
 3 | description: Interview the user relentlessly about a plan or design until reaching shared understanding, resolving each branch of the decision tree. Use when user wants to stress-test a plan, get grilled on their design, or mentions "grill me".
 4 | ---
 5 | 
 6 | Interview me relentlessly about every aspect of this plan until we reach a shared understanding. Walk down each branch of the design tree, resolving dependencies between decisions one-by-one. For each question, provide your recommended answer.
 7 | 
 8 | Ask the questions one at a time.
 9 | 
10 | If a question can be answered by exploring the codebase, explore the codebase instead.
</content>
</file>
</files>
<environment_details>
# VSCode Visible Files
../../../../../../Untitled-1

# VSCode Open Tabs
README.md,bob_session/bob_task_may-16-2026_4-36-56-pm.md,.bob/skills/SKILL.MD,../../../../../../Untitled-1

# Current Time
Current time in ISO 8601 UTC format: 2026-05-17T10:53:04.211Z
User time zone: Asia/Saigon, UTC+7:00

# Current Cost
$0.11

# Review Exclusions
The following file patterns are excluded from code reviews:
- .vscode/**

# Current Mode
<slug>code</slug>
<name>💻 Code</name>
You have not created a todo list yet. Create one with `update_todo_list` if your task is complicated or involves multiple steps.
</environment_details>

---

**Assistant:**

Now let me check if there are other skills to understand the pattern better, and also look at the instruction guideline file.

<read_file>
<args>
<file>
<path>instruction-guideline.md</path>
</file>
<file>
<path>README.md</path>
</file>
</args>
</read_file>

---

**User:**

[read_file for 'instruction-guideline.md', 'README.md'] Result:
<files>
<file><path>instruction-guideline.md</path>
<content/><notice>File is empty</notice>
</file>
<file><path>README.md</path>
<content lines="1-176">
  1 | # IBM Bob Migration Framework
  2 | 
  3 | > **Created for the IBM Bob Hackathon**
  4 | 
  5 | A project that extends IBM Bob's capabilities to assist with complex technical migration processes across diverse scenarios.
  6 | 
  7 | ## Overview
  8 | 
  9 | This project contains specialized skills, MCP (Model Context Protocol) integrations, and custom modes that empower IBM Bob to orchestrate and execute technical migrations with precision and efficiency.
 10 | 
 11 | ## What This Project Provides
 12 | 
 13 | ### Custom Migration Modes
 14 | Seven specialized modes that guide you through each phase of migration:
 15 | 
 16 | - **Migration Assessment Mode** - Comprehensive analysis and discovery
 17 | - **Migration Planning Mode** - Strategic planning and roadmap creation
 18 | - **Migration Design Mode** - Technical design documentation
 19 | - **Migration Execution Mode** - Automated migration execution
 20 | - **Migration Validation Mode** - Quality assurance and testing
 21 | - **Migration Optimization Mode** - Performance tuning and best practices
 22 | - **Migration Hypercare Mode** - Post-migration monitoring and support
 23 | - **Migration Orchestrator Mode** - Intelligent phase orchestration
 24 | 
 25 | ### Migration Skills
 26 | Specialized skills for different migration scenarios:
 27 | 
 28 | - **Java Migration Skill** - Version upgrades, JVM changes, API updates
 29 | - **Framework Migration Skill** - Framework transitions (e.g., Quarkus → Spring Boot)
 30 | - **Cloud Migration Skill** - Cloud platform migrations and modernization
 31 | - **Database Migration Skill** - Database transitions and data migration
 32 | - **Dependency Management Skill** - Dependency discovery and conflict resolution
 33 | - **Testing Skill** - Automated test generation and execution
 34 | - **Rollback Skill** - Safe rollback procedures and recovery
 35 | 
 36 | ### MCP Integrations
 37 | High-priority tools for migration automation:
 38 | 
 39 | - **Dependency Analysis MCP** - Analyze dependencies, detect conflicts
 40 | - **Test Automation MCP** - Generate and execute comprehensive test suites
 41 | - **Data Quality Assessment MCP** - Validate data integrity and consistency
 42 | 
 43 | ## How Bob Helps with Migrations
 44 | 
 45 | Bob leverages the **7Rs Migration Framework** to provide intelligent assistance throughout your migration journey:
 46 | 
 47 | ### 1. **Find the Suitable Migration Strategy**
 48 | Bob analyzes your current state and recommends the optimal approach from the 7Rs framework:
 49 | - Rehost (Lift and Shift)
 50 | - Replatform (Lift, Adaptation, and Shift)
 51 | - Repurchase (Drop and Shop)
 52 | - Refactor/Re-architect (Modernize)
 53 | - Retire (Decommission)
 54 | - Retain (Keep as-is)
 55 | - Relocate (Hypervisor-level migration)
 56 | 
 57 | ### 2. **Design and Planning**
 58 | Bob creates comprehensive migration plans including:
 59 | - Dependency mapping and impact analysis
 60 | - Risk assessment and mitigation strategies
 61 | - Resource allocation and timeline estimation
 62 | - Rollback procedures and contingency plans
 63 | - Technical design documentation
 64 | 
 65 | ### 3. **Auto Execution of the Migration**
 66 | Bob automates the migration process:
 67 | - Code transformation and refactoring
 68 | - Configuration updates and environment setup
 69 | - Dependency resolution and updates
 70 | - Infrastructure provisioning (IaC)
 71 | - Deployment automation with CI/CD integration
 72 | 
 73 | ### 4. **Validation**
 74 | Bob ensures migration quality through:
 75 | - Automated test generation and execution
 76 | - Code quality analysis and security scanning
 77 | - Performance benchmarking and comparison
 78 | - Data integrity validation
 79 | - Functional and integration testing
 80 | 
 81 | ### 5. **Hypercare**
 82 | Bob provides ongoing support post-migration:
 83 | - Real-time monitoring and alerting
 84 | - Performance optimization recommendations
 85 | - Issue detection and resolution guidance
 86 | - Documentation updates and knowledge transfer
 87 | - Continuous improvement suggestions
 88 | 
 89 | ## Supported Migration Scenarios
 90 | 
 91 | ### Core Migration Types
 92 | 1. **Version Migration** - Upgrade technology versions (e.g., Java 11 → Java 21)
 93 | 2. **Framework Migration** - Switch frameworks (e.g., Quarkus → Spring Boot)
 94 | 3. **Cloud Migration** - Move to cloud platforms (e.g., WebSphere → AWS)
 95 | 4. **Programming Language Migration** - Language transitions (e.g., JavaScript → Python)
 96 | 5. **Dependency Migration** - Update dependencies (e.g., JDBC → JPA/Hibernate)
 97 | 6. **Data Migration** - Database transitions (e.g., PostgreSQL → MongoDB)
 98 | 
 99 | ### Extended Scenarios
100 | - Monolith to Microservices
101 | - Container Platform Migration (Docker Swarm → Kubernetes)
102 | - CI/CD Pipeline Migration
103 | - Authentication/Authorization Migration
104 | - Frontend Framework Migration
105 | - Testing Framework Migration
106 | - And many more...
107 | 
108 | ## ✅ Implementation Status
109 | 
110 | **Current Status:** Migration Design Mode is fully functional and ready for use!
111 | 
112 | **Recent Updates:**
113 | - ✅ Bug discovered and resolved (2026-05-16)
114 | - ✅ File reading capability verified and working
115 | - ✅ All design tools operational
116 | - ✅ Real-world demo completed successfully
117 | 
118 | **Documentation:**
119 | - 📚 **[Design Mode Documentation Hub](docs/design-mode/README.md)** - Complete documentation index
120 | - 📋 **[Implementation Summary](docs/design-mode/implementation-summary.md)** - Overview and metrics
121 | - 🐛 **[Bug Report](BUG-REPORT-DESIGN-MODE.md)** - Bug resolution details
122 | - 🎯 **[Demo Summary](DESIGN-MODE-DEMO-SUMMARY.md)** - Demo validation results
123 | - 🏗️ **[Sample Architecture Design](../sample-java-migration-test/MIGRATION-ARCHITECTURE-DESIGN.md)** - Real-world example
124 | 
125 | ---
126 | 
127 | ## Getting Started
128 | 
129 | ### Prerequisites
130 | - IBM Bob IDE installed
131 | - Access to your migration source code/systems
132 | - Basic understanding of your migration goals
133 | 
134 | ### Quick Start
135 | 
136 | 1. **Clone this Project:**
137 | 
138 |    ```bash
139 |    git clone <repository-url>
140 |    cd ibm-bob-migration-framework
141 |    ```
142 | 
143 | > This project can also be downloaded and unziped instead of cloning
144 | 
145 | 2. **Open in IBM Bob IDE:**
146 |    - Launch IBM Bob IDE
147 |    - Open the project folder
148 |    - Bob will automatically detect custom modes and skills
149 | 
150 | 3. **Start with Orchestration Mode:**
151 |    - Switch to "Migration Orchestrator Mode"
152 |    - Bob will guide you through the migration phases
153 |    - Follow Bob's recommendations and validations
154 | 
155 | > This project also supports jump to each modes without using the orchestration, usefull for long-going migration session that each of the mode can be continued
156 | 
157 | 4. **Execute Your Migration:**
158 |    - Bob will orchestrate the entire process
159 |    - Review and approve each phase transition
160 |    - Monitor progress through automated checkpoints
161 | 
162 | ## Contributing
163 | 
164 | This is a hackathon project template. Feel free to:
165 | - Extend with additional migration scenarios
166 | - Add new custom skills
167 | - Integrate additional MCP servers
168 | - Share your migration success stories
169 | 
170 | ## License
171 | 
172 | This project is created for the IBM Bob Hackathon and is provided as-is for educational and demonstration purposes.
173 | 
174 | ---
175 | 
176 | **Ready to migrate?** Open this project in IBM Bob IDE and let Bob guide you through your migration journey! 🚀
</content>
</file>
</files>
<environment_details>
# VSCode Visible Files
../../../../../../Untitled-1

# VSCode Open Tabs
README.md,bob_session/bob_task_may-16-2026_4-36-56-pm.md,.bob/skills/SKILL.MD,../../../../../../Untitled-1

# Current Time
Current time in ISO 8601 UTC format: 2026-05-17T10:53:07.423Z
User time zone: Asia/Saigon, UTC+7:00

# Current Cost
$0.16

# Review Exclusions
The following file patterns are excluded from code reviews:
- .vscode/**

# Current Mode
<slug>code</slug>
<name>💻 Code</name>
You have not created a todo list yet. Create one with `update_todo_list` if your task is complicated or involves multiple steps.
</environment_details>

---

**Assistant:**

Now let me list the skills directory to see if there are more examples:

<list_files>
<path>.bob/skills</path>
<recursive>false</recursive>
</list_files>

---

**User:**

[list_files for '.bob/skills'] Result:
🛡️ SKILL.MD
<environment_details>
# VSCode Visible Files
../../../../../../Untitled-1

# VSCode Open Tabs
README.md,bob_session/bob_task_may-16-2026_4-36-56-pm.md,.bob/skills/SKILL.MD,../../../../../../Untitled-1

# Current Time
Current time in ISO 8601 UTC format: 2026-05-17T10:53:10.139Z
User time zone: Asia/Saigon, UTC+7:00

# Current Cost
$0.22

# Review Exclusions
The following file patterns are excluded from code reviews:
- .vscode/**

# Current Mode
<slug>code</slug>
<name>💻 Code</name>
You have not created a todo list yet. Create one with `update_todo_list` if your task is complicated or involves multiple steps.
</environment_details>

---

**Assistant:**

Based on the Anthropic documentation I retrieved and the existing skill format, I'll now create a comprehensive skill for writing effective custom modes and skills. Let me create this skill file:

<write_to_file>
<path>.bob/skills/context-engineering-skill.md</path>
<content>---
name: context-engineering
description: Guide for writing effective custom modes and skills using context engineering best practices. Use when creating or refining system prompts, custom modes, or skills to ensure optimal balance between specificity and flexibility.
---

# Context Engineering for Custom Modes & Skills

You are tasked with creating or refining a custom mode or skill. Apply these context engineering principles to achieve the optimal balance between specificity and flexibility.

## Core Principles

### 1. Find the Right Altitude
Your instructions should exist in the "Goldilocks zone":
- **Too Low (Brittle)**: Hardcoded if-else logic, overly prescriptive rules that break easily
- **Too High (Vague)**: Generic guidance that assumes shared context or lacks concrete signals
- **Just Right**: Specific enough to guide behavior, flexible enough to provide strong heuristics

**Example of Right Altitude:**
```
❌ Too Low: "If the user asks about Java, respond with 'Java is a programming language.' If they ask about Python, respond with 'Python is a programming language.'"

❌ Too High: "Help the user with programming questions."

✅ Just Right: "You are a programming assistant. When users ask about languages, provide context-appropriate explanations that match their expertise level. Include practical examples and highlight key differences when comparing technologies."
```

### 2. Minimize Token Count, Maximize Signal
- Use the **smallest possible set** of high-signal tokens
- Every token should earn its place in the context window
- Remove redundant information and edge cases
- Focus on canonical, diverse examples rather than exhaustive lists

### 3. Structure for Clarity
Organize prompts into distinct sections using XML tags or Markdown headers:
- `<background_information>` or `## Background`
- `<instructions>` or `## Instructions`
- `<tool_guidance>` or `## Tool Guidance`
- `<output_format>` or `## Output Format`
- `<examples>` or `## Examples`

### 4. Progressive Disclosure
Start minimal and add based on failure modes:
1. Begin with the simplest prompt that could work
2. Test with the best available model
3. Identify failure modes through testing
4. Add clear instructions and examples to address failures
5. Iterate until performance is acceptable

## Writing System Prompts

### Essential Components
1. **Role Definition**: Who is the agent? What is their expertise?
2. **Core Objective**: What is the primary goal or task?
3. **Key Constraints**: What are the critical boundaries or limitations?
4. **Behavioral Heuristics**: What principles guide decision-making?

### Anti-Patterns to Avoid
- ❌ Listing every possible edge case
- ❌ Hardcoding complex branching logic
- ❌ Assuming the model knows your domain context
- ❌ Mixing multiple concerns without clear separation
- ❌ Using vague language like "be helpful" or "do your best"

### Best Practices
- ✅ Use simple, direct language
- ✅ Provide 2-3 diverse, canonical examples
- ✅ State explicit success criteria
- ✅ Include clear error handling guidance
- ✅ Specify output format expectations

## Tool Design Principles

### Token Efficiency
- Tools should return **minimal, high-signal information**
- Avoid verbose outputs that pollute context
- Design tools to encourage efficient agent behavior
- Consider what information is truly necessary vs. nice-to-have

### Clear Contracts
- Each tool should have a **single, well-defined purpose**
- Avoid overlapping functionality between tools
- Make tool names and descriptions unambiguous
- If a human can't decide which tool to use, neither can the agent

### Parameter Design
- Use descriptive, unambiguous parameter names
- Provide clear constraints and validation rules
- Play to the model's strengths (natural language over complex syntax)
- Include examples in tool descriptions

## Examples and Few-Shot Prompting

### Quality Over Quantity
- Provide **2-5 diverse, canonical examples**
- Each example should demonstrate a different aspect of desired behavior
- Avoid redundant examples that show the same pattern
- Examples are "pictures worth a thousand words"

### Example Structure
```
<example>
<scenario>Brief description of the situation</scenario>
<input>What the user provides</input>
<reasoning>Key decision points (optional)</reasoning>
<output>Expected response or action</output>
</example>
```

## Context Management Strategies

### For Long-Horizon Tasks
Choose the appropriate strategy based on task characteristics:

1. **Compaction**: Summarize and compress context when nearing limits
   - Best for: Conversational flows requiring extensive back-and-forth
   - Implementation: Preserve critical details, discard redundant information

2. **Structured Note-Taking**: Maintain persistent memory outside context
   - Best for: Iterative development with clear milestones
   - Implementation: Use NOTES.md or similar for tracking progress

3. **Sub-Agent Architectures**: Specialized agents for focused tasks
   - Best for: Complex research/analysis with parallel exploration
   - Implementation: Main agent coordinates, sub-agents handle deep work

### Just-In-Time Context Retrieval
- Maintain lightweight identifiers (paths, queries, links)
- Load data dynamically at runtime using tools
- Leverage metadata for efficient navigation
- Enable progressive disclosure through exploration

## Validation Checklist

Before finalizing your custom mode or skill, verify:

- [ ] Instructions are at the right altitude (not too specific, not too vague)
- [ ] Every section has a clear purpose
- [ ] Token count is minimized while maintaining clarity
- [ ] Examples are diverse and canonical (not exhaustive)
- [ ] Tools have clear, non-overlapping purposes
- [ ] Output format is explicitly specified
- [ ] Success criteria are measurable
- [ ] Error handling is addressed
- [ ] The prompt has been tested with real scenarios
- [ ] Failure modes have been identified and addressed

## Iterative Refinement Process

1. **Start Simple**: Write the minimal viable prompt
2. **Test**: Run against representative scenarios
3. **Identify Failures**: Document where behavior deviates
4. **Add Specificity**: Address failures with targeted additions
5. **Remove Bloat**: Cut anything that doesn't improve outcomes
6. **Repeat**: Continue until performance is acceptable

## Remember

- Context is a **finite resource** with diminishing returns
- Models experience "attention budget" constraints
- Longer context ≠ better performance
- Think in terms of **information density**, not just length
- The goal is the **smallest set of tokens** that maximizes desired outcomes

---

## Application

When creating a custom mode or skill:
1. Define the core objective clearly
2. Identify the right altitude for instructions
3. Structure content into logical sections
4. Provide 2-3 canonical examples
5. Design token-efficient tools (if applicable)
6. Test and iterate based on failure modes
7. Validate against the checklist above

Your custom mode or skill should be **clear, concise, and capable** - giving the model just enough guidance to succeed without constraining its intelligence.
</content>
<line_count>178</line_count>
</write_to_file>

---

**User:**

[write_to_file for '.bob/skills/context-engineering-skill.md'] Result:
<file_write_result>
<path>.bob/skills/context-engineering-skill.md</path>
<operation>created</operation>
<notice>
<i>You do not need to re-read the file, as you have seen all changes</i>
<i>Proceed with the task using these changes as the new baseline.</i>
</notice>
</file_write_result>

<environment_details>
# VSCode Visible Files
.bob/skills/context-engineering-skill.md

# VSCode Open Tabs
README.md,bob_session/bob_task_may-16-2026_4-36-56-pm.md,../../../../../../Untitled-1,.bob/skills/context-engineering-skill.md

# Current Time
Current time in ISO 8601 UTC format: 2026-05-17T10:53:50.519Z
User time zone: Asia/Saigon, UTC+7:00

# Current Cost
$0.29

# Review Exclusions
The following file patterns are excluded from code reviews:
- .vscode/**

# Current Mode
<slug>code</slug>
<name>💻 Code</name>
You have not created a todo list yet. Create one with `update_todo_list` if your task is complicated or involves multiple steps.
</environment_details>

---

**Assistant:**

<attempt_completion>
<result>
Created a comprehensive context engineering skill at `.bob/skills/context-engineering-skill.md` based on Anthropic's best practices.

**Key Features:**

1. **Right Altitude Principle**: Guides finding the balance between overly specific (brittle) and too vague instructions

2. **Core Principles**:
   - Minimize token count while maximizing signal
   - Structure for clarity using sections
   - Progressive disclosure approach
   - Token efficiency focus

3. **Practical Guidance**:
   - System prompt writing best practices
   - Tool design principles for efficiency
   - Example/few-shot prompting strategies
   - Context management for long-horizon tasks

4. **Validation Checklist**: 11-point checklist to ensure quality before finalizing

5. **Iterative Refinement Process**: Step-by-step approach to improve prompts

6. **Anti-Patterns & Best Practices**: Clear examples of what to avoid and what to embrace

The skill can be invoked when creating or refining custom modes/skills to ensure they follow context engineering best practices, achieving optimal balance between specificity and flexibility while treating context as the finite resource it is.
</result>
</attempt_completion>

---

**User:**



New instructions for task continuation:
<user_message>
You are an expert at writing system prompt instructions for AI agents.

I have an existing instruction file for a Migration Design Mode agent that is too bloated and unbalanced. Rewrite it following these principles from Anthropic's context engineering guide:

PRINCIPLES TO APPLY:
- Find the smallest possible set of high-signal tokens that achieve the desired behavior
- Operate at the "right altitude": specific enough to guide behavior, flexible enough to handle variation — avoid hardcoded brittle step-by-step logic
- Do NOT enumerate every edge case. Use 1-2 canonical examples that demonstrate the expected behavior pattern instead
- Remove all content that is redundant, repeated, or inferable by the model from context
- Keep structure flat. Avoid deep nesting of checklists within checklists
- Guardrails should be stated once, concisely — not repeated across multiple sections

PROBLEMS TO FIX IN THE CURRENT INSTRUCTION:
1. The 6 primary objectives, 10-step workflow, success/failure indicators, and phase gate criteria all say the same thing in different words — collapse into one clear definition of the task and its output
2. The output document templates (Architecture Design Doc, Component Spec, PoC Recommendations) are overly prescriptive markdown scaffolds — replace with a short description of what each artifact must communicate
3. Time estimates on workflow steps ("15-20 minutes") are brittle and meaningless — remove
4. The "Collaboration Approach" section restates general good agent behavior — remove
5. The 3 example scenarios repeat the same pattern with different nouns — keep only 1 canonical example, cut the rest
6. Guardrails (MUST NOT / MUST DO) are repeated in both the guardrails section and the failure indicators — state once

ABSTRACT TASK STRUCTURE TO PRESERVE (use this as the backbone):
The design phase agent has 6 tasks:
1. Target State Definition — what does the end state look like?
2. Transformation Mapping — how does each component get there?
3. Interface & Contract Specification — what must stay stable or change?
4. Dependency & Tool Substitution Plan — what replaces what?
5. Testing Strategy Design — how do we validate correctness?
6. Pilot Execution — does the design actually work? (produces a revised design if not)

OUTPUT:
Produce the rewritten instruction as a single markdown document. Aim for roughly 40-50% of the original word count. Every sentence must earn its place.

Re-write the migration-design-mode defines in custom_modes, reduce the noise, also use the skill context-engineering for guideline
</user_message>
[Image]
<environment_details>
# VSCode Visible Files
../../../../../../Untitled-1

# VSCode Open Tabs
README.md,bob_session/bob_task_may-16-2026_4-36-56-pm.md,../../../../../../Untitled-1

# Current Time
Current time in ISO 8601 UTC format: 2026-05-17T11:09:23.268Z
User time zone: Asia/Saigon, UTC+7:00

# Current Cost
$0.36

# Review Exclusions
The following file patterns are excluded from code reviews:
- .vscode/**

# Current Mode
<slug>code</slug>
<name>💻 Code</name>


# Current Workspace Directory (/Users/nvduc/projects/playground/ide-arena/ibm-bob) Files
BUG-REPORT-DESIGN-MODE.md
DESIGN-MODE-DEMO-SUMMARY.md
DOCUMENT-ORGANIZATION.md
IMPLEMENTATION-SUMMARY.md
LICENSE
README.md
validate-design-mode.py
.bob/
.vscode/
bob_session/
bob_session/bob_task_may-16-2026_4-36-56-pm.md
bob_session/bob_task_may-16-2026_5-10-59-pm.md
bob_session/bob_task_may-16-2026_5-11-30-pm.md
bob_session/bob_task_may-16-2026_5-32-50-pm.md
bob_session/bob_task_may-16-2026_6-08-35-pm.md
bob_session/bob_task_may-16-2026_6-37-28-pm.md
bob_session/bob_task_may-16-2026_6-53-36-pm.md
bob_session/bob_task_may-17-2026_4-08-07-am.md
bob_session/bob_task_may-17-2026_4-48-31-am.md
bob_session/bob_task_may-17-2026_5-28-04-am.md
bob_session/bob_task_may-17-2026_5-39-34-am.md
docs/
docs/design-mode/
docs/design-mode/bug-analysis.md
docs/design-mode/implementation-plan.md
docs/design-mode/implementation-summary.md
docs/design-mode/README.md
docs/design-mode/specification.md
docs/design-mode/test-execution-report.md
docs/design-mode/testing-guide.md
docs/design-mode/verification.md
hackathon-ideas/
hackathon-ideas/intent.md
hackathon-ideas/mcp-server-specifications.md
hackathon-ideas/migration-candidate-repositories.md
hackathon-ideas/migration-examples.md
hackathon-ideas/migration-framework-plan.md
hackathon-ideas/migration-skills-and-workflows.md
hackathon-ideas/migration-technical-specifications.md
hackathon-ideas/README-MIGRATION-SPECS.md
You have not created a todo list yet. Create one with `update_todo_list` if your task is complicated or involves multiple steps.
</environment_details>

---

**Assistant:**

I'll help you rewrite the Migration Design Mode instruction following context engineering best practices. First, let me read the current instruction file and the context engineering skill.

<read_file>
<args>
<file>
<path>.bob/custom_modes/migration-design-mode.md</path>
</file>
<file>
<path>.bob/skills/context-engineering-skill.md</path>
</file>
</args>
</read_file>

---

**User:**

[read_file for '.bob/custom_modes/migration-design-mode.md', '.bob/skills/context-engineering-skill.md'] Result:
<files>
<file><path>.bob/custom_modes/migration-design-mode.md</path><error>File not found</error><notice>The file was not found. Consider using list_files to find available files before attempting to read them.</notice></file>
<file><path>.bob/skills/context-engineering-skill.md</path><error>File not found</error><notice>The file was not found. Consider using list_files to find available files before attempting to read them.</notice></file>
</files>
<environment_details>
# VSCode Visible Files
../../../../../../Untitled-1

# VSCode Open Tabs
README.md,bob_session/bob_task_may-16-2026_4-36-56-pm.md,../../../../../../Untitled-1

# Current Time
Current time in ISO 8601 UTC format: 2026-05-17T11:09:27.702Z
User time zone: Asia/Saigon, UTC+7:00

# Current Cost
$0.43

# Review Exclusions
The following file patterns are excluded from code reviews:
- .vscode/**

# Current Mode
<slug>code</slug>
<name>💻 Code</name>
You have not created a todo list yet. Create one with `update_todo_list` if your task is complicated or involves multiple steps.
</environment_details>

---

**Assistant:**

Let me list the files in the .bob directory to find the correct paths:

<list_files>
<path>.bob</path>
<recursive>true</recursive>
</list_files>

---

**User:**

[list_files for '.bob'] Result:
🛡️ custom_modes.yaml
🛡️ mcp.json
🛡️ skills/
🛡️ skills/SKILL.MD
🛡️ skills/context-engineering/
🛡️ skills/context-engineering/SKILL.md
<environment_details>
# VSCode Visible Files
../../../../../../Untitled-1

# VSCode Open Tabs
README.md,bob_session/bob_task_may-16-2026_4-36-56-pm.md,../../../../../../Untitled-1

# Current Time
Current time in ISO 8601 UTC format: 2026-05-17T11:09:47.901Z
User time zone: Asia/Saigon, UTC+7:00

# Current Cost
$0.50

# Review Exclusions
The following file patterns are excluded from code reviews:
- .vscode/**

# Current Mode
<slug>code</slug>
<name>💻 Code</name>
You have not created a todo list yet. Create one with `update_todo_list` if your task is complicated or involves multiple steps.
</environment_details>

---

**Assistant:**

Now let me read the custom_modes.yaml file and the context engineering skill:

<read_file>
<args>
<file>
<path>.bob/custom_modes.yaml</path>
</file>
<file>
<path>.bob/skills/context-engineering/SKILL.md</path>
</file>
</args>
</read_file>

---

**User:**

[read_file for '.bob/custom_modes.yaml', '.bob/skills/context-engineering/SKILL.md'] Result:
<files>
<file><path>.bob/custom_modes.yaml</path>
<content lines="1-659">
  1 | customModes:
  2 |   - slug: migration-assessment-mode
  3 |     name: Migration Assessment Mode
  4 |     description: Comprehensive analysis and discovery of migration requirements, risks, and feasibility
  5 |     roleDefinition: Comprehensive analysis and discovery of migration requirements, risks, and feasibility
  6 |     whenToUse: ""
  7 |     customInstructions: ""
  8 |     source: project
  9 |     groups: []
 10 |   - slug: migration-planning-mode
 11 |     name: Migration Planning Mode
 12 |     description: Mode for Planning the migration
 13 |     roleDefinition: "Plan the migration for me "
 14 |     whenToUse: ""
 15 |     customInstructions: ""
 16 |     source: project
 17 |     groups: []
 18 |   - slug: migration-design-mode
 19 |     name: 🏗️ Migration Design
 20 |     description: Architecture design and technical specification creation for migration projects. Transforms assessment findings and migration plans into detailed technical designs, component strategies, and implementation blueprints without executing code changes.
 21 |     
 22 |     roleDefinition: |
 23 |       You are Bob in Migration Design mode, an expert software architect specializing in migration architecture design and technical specification creation.
 24 |       
 25 |       Your role is to transform assessment findings and migration plans into comprehensive technical designs that guide the execution phase. You create architecture documents, component migration strategies, API specifications, and implementation blueprints - but you do NOT execute code changes or perform migrations.
 26 |     
 27 |     whenToUse: |
 28 |       Activate Migration Design mode when:
 29 |       - Assessment phase is complete with approved findings
 30 |       - Migration plan exists with defined scope and timeline
 31 |       - User requests "design the migration architecture"
 32 |       - Need to create technical specifications before execution
 33 |       - Transitioning from Planning phase to Execution phase
 34 |       - User asks to "design how we'll migrate X to Y"
 35 |       - Keywords: "design migration", "architecture design", "technical specs", "migration blueprint"
 36 |       
 37 |       DO NOT use this mode when:
 38 |       - Assessment is not yet complete (use Assessment Mode)
 39 |       - User wants to execute migration code changes (use Execution Mode)
 40 |       - User needs to analyze current state (use Assessment Mode)
 41 |       - User wants to create project plan (use Planning Mode)
 42 |     
 43 |     customInstructions: |
 44 |       # Migration Design Mode - Custom Instructions
 45 |       
 46 |       ## Primary Objectives
 47 |       
 48 |       1. **Create Target Architecture Design**
 49 |          - Design target system architecture aligned with platform best practices
 50 |          - Map current components to target architecture
 51 |          - Define component boundaries and responsibilities
 52 |          - Identify architectural patterns (microservices, event-driven, etc.)
 53 |       
 54 |       2. **Define Component Migration Strategies**
 55 |          - Apply 7Rs framework per component (Rehost, Replatform, Refactor, Repurchase, Retire, Retain, Relocate)
 56 |          - Document migration approach for each component
 57 |          - Identify dependencies and migration sequence
 58 |          - Define rollback strategies per component
 59 |       
 60 |       3. **Design APIs and Interfaces**
 61 |          - Specify API contracts for migrated services
 62 |          - Design interface compatibility layers if needed
 63 |          - Document breaking changes and migration paths
 64 |          - Define versioning strategy
 65 |       
 66 |       4. **Create Data Migration Schema**
 67 |          - Design target data models
 68 |          - Map source to target schema
 69 |          - Define data transformation rules
 70 |          - Plan data validation approach
 71 |       
 72 |       5. **Define Integration Patterns**
 73 |          - Design integration points between components
 74 |          - Specify communication patterns (REST, messaging, etc.)
 75 |          - Define error handling and retry logic
 76 |          - Document security and authentication flows
 77 |       
 78 |       6. **Design Testing Strategy**
 79 |          - Define test levels (unit, integration, e2e)
 80 |          - Specify test coverage requirements
 81 |          - Design test data strategy
 82 |          - Plan performance and security testing
 83 |       
 84 |       ## Expected Inputs
 85 |       
 86 |       You should receive from previous phases:
 87 |       - **Assessment Report** (from Assessment Mode)
 88 |         - Current architecture analysis
 89 |         - Dependency graph
 90 |         - Risk matrix
 91 |         - Migration readiness score
 92 |         - Recommended 7Rs strategies
 93 |       - **Migration Plan** (from Planning Mode)
 94 |         - Phase-by-phase breakdown
 95 |         - Task dependencies
 96 |         - Resource allocation
 97 |         - Timeline and milestones
 98 |         - Success criteria
 99 |       
100 |       If these inputs are missing, ask the user to provide them or run Assessment/Planning modes first.
101 |       
102 |       ## Expected Outputs
103 |       
104 |       You must create the following design artifacts:
105 |       
106 |       ### 1. Architecture Design Document (Confluence)
107 |       Structure:
108 |       ```markdown
109 |       # Migration Architecture Design: [Project Name]
110 |       
111 |       ## Executive Summary
112 |       - Migration type and scope
113 |       - Target architecture overview
114 |       - Key design decisions
115 |       
116 |       ## Current vs Target Architecture
117 |       - Current architecture diagram
118 |       - Target architecture diagram
119 |       - Component mapping matrix
120 |       
121 |       ## Component Migration Strategies
122 |       For each component:
123 |       - Component name and purpose
124 |       - 7Rs strategy (Rehost/Replatform/Refactor/etc.)
125 |       - Migration approach
126 |       - Dependencies
127 |       - Risks and mitigations
128 |       
129 |       ## API and Interface Design
130 |       - API specifications (OpenAPI/Swagger)
131 |       - Interface contracts
132 |       - Breaking changes
133 |       - Compatibility layers
134 |       
135 |       ## Data Migration Design
136 |       - Source and target schemas
137 |       - Transformation rules
138 |       - Data validation approach
139 |       - Migration sequence
140 |       
141 |       ## Integration Patterns
142 |       - Integration architecture
143 |       - Communication patterns
144 |       - Error handling
145 |       - Security design
146 |       
147 |       ## Testing Strategy
148 |       - Test levels and coverage
149 |       - Test data approach
150 |       - Performance testing plan
151 |       - Security testing plan
152 |       
153 |       ## Proof of Concept Recommendations
154 |       - PoC objectives
155 |       - Scope and timeline
156 |       - Success criteria
157 |       - Resource requirements
158 |       
159 |       ## Design Decisions Log
160 |       - Key decisions made
161 |       - Alternatives considered
162 |       - Rationale
163 |       - Trade-offs
164 |       ```
165 |       
166 |       ### 2. Technical Specifications per Component
167 |       For each component to be migrated, create:
168 |       ```markdown
169 |       # Component Technical Specification: [Component Name]
170 |       
171 |       ## Overview
172 |       - Component purpose
173 |       - Current technology stack
174 |       - Target technology stack
175 |       
176 |       ## Before/After Code Structure
177 |       - Current structure
178 |       - Target structure
179 |       - File organization
180 |       
181 |       ## Configuration Changes
182 |       - Current configuration
183 |       - Target configuration
184 |       - Migration steps
185 |       
186 |       ## Dependency Updates
187 |       - Current dependencies
188 |       - Target dependencies
189 |       - Compatibility notes
190 |       
191 |       ## Test Requirements
192 |       - Test scenarios
193 |       - Coverage requirements
194 |       - Test data needs
195 |       
196 |       ## Migration Steps (High-Level)
197 |       - Step-by-step approach
198 |       - Validation points
199 |       - Rollback procedure
200 |       ```
201 |       
202 |       ### 3. Design Review Checklist
203 |       ```markdown
204 |       # Design Review Checklist
205 |       
206 |       ## Architecture Alignment
207 |       - [ ] Follows target platform best practices
208 |       - [ ] Scalability requirements addressed
209 |       - [ ] Performance requirements achievable
210 |       - [ ] Security requirements met
211 |       
212 |       ## Integration Points
213 |       - [ ] All integration points identified
214 |       - [ ] Communication patterns defined
215 |       - [ ] Error handling specified
216 |       - [ ] Authentication/authorization designed
217 |       
218 |       ## Data Migration
219 |       - [ ] Schema mapping complete
220 |       - [ ] Transformation rules defined
221 |       - [ ] Data validation approach specified
222 |       - [ ] Migration sequence planned
223 |       
224 |       ## Testing Strategy
225 |       - [ ] Test levels defined
226 |       - [ ] Coverage requirements specified
227 |       - [ ] Test data strategy planned
228 |       - [ ] Performance testing included
229 |       
230 |       ## Risk Management
231 |       - [ ] Risks identified and documented
232 |       - [ ] Mitigation strategies defined
233 |       - [ ] Rollback procedures specified
234 |       - [ ] Contingency plans created
235 |       ```
236 |       
237 |       ### 4. Proof of Concept (PoC) Recommendations
238 |       ```markdown
239 |       # PoC Recommendations
240 |       
241 |       ## Objectives
242 |       - What to validate
243 |       - Success criteria
244 |       
245 |       ## Scope
246 |       - Components to include
247 |       - Features to implement
248 |       - Out of scope items
249 |       
250 |       ## Timeline
251 |       - Duration estimate
252 |       - Key milestones
253 |       
254 |       ## Resources
255 |       - Team members needed
256 |       - Tools and infrastructure
257 |       - Budget estimate
258 |       
259 |       ## Success Criteria
260 |       - Technical validation points
261 |       - Performance benchmarks
262 |       - Decision criteria
263 |       ```
264 |       
265 |       ## Design Workflow
266 |       
267 |       Follow this step-by-step process:
268 |       
269 |       ### Step 1: Gather Context (5-10 minutes)
270 |       1. Review assessment report and migration plan
271 |       2. Understand current architecture using:
272 |          - `list_files` to explore project structure
273 |          - `list_code_definition_names` to understand components
274 |          - `read_file` to examine key configuration files
275 |       3. Clarify target platform requirements
276 |       4. Identify any missing information
277 |       
278 |       ### Step 2: Design Target Architecture (15-20 minutes)
279 |       1. Create target architecture diagram (use Architecture Visualization MCP)
280 |       2. Map current components to target architecture
281 |       3. Define component boundaries and responsibilities
282 |       4. Identify architectural patterns to apply
283 |       5. Document key design decisions
284 |       
285 |       ### Step 3: Define Component Strategies (20-30 minutes)
286 |       1. For each component, determine 7Rs strategy:
287 |          - **Rehost:** Lift-and-shift with minimal changes
288 |          - **Replatform:** Optimize for target platform
289 |          - **Refactor:** Restructure for cloud-native patterns
290 |          - **Repurchase:** Replace with SaaS/managed service
291 |          - **Retire:** Decommission if no longer needed
292 |          - **Retain:** Keep as-is if not ready to migrate
293 |          - **Relocate:** Move to different infrastructure
294 |       2. Document migration approach per component
295 |       3. Identify dependencies and sequence
296 |       4. Define rollback strategies
297 |       
298 |       ### Step 4: Design APIs and Interfaces (15-20 minutes)
299 |       1. Specify API contracts (OpenAPI/Swagger format)
300 |       2. Design interface compatibility layers if needed
301 |       3. Document breaking changes
302 |       4. Define versioning strategy
303 |       5. Plan backward compatibility approach
304 |       
305 |       ### Step 5: Create Data Migration Schema (15-20 minutes)
306 |       1. Design target data models
307 |       2. Map source schema to target schema
308 |       3. Define transformation rules
309 |       4. Plan data validation approach
310 |       5. Specify migration sequence (batch vs incremental)
311 |       
312 |       ### Step 6: Define Integration Patterns (10-15 minutes)
313 |       1. Design integration architecture
314 |       2. Specify communication patterns
315 |       3. Define error handling and retry logic
316 |       4. Document security and authentication flows
317 |       5. Plan monitoring and observability
318 |       
319 |       ### Step 7: Design Testing Strategy (10-15 minutes)
320 |       1. Define test levels (unit, integration, e2e)
321 |       2. Specify coverage requirements (≥80% recommended)
322 |       3. Design test data strategy
323 |       4. Plan performance testing approach
324 |       5. Plan security testing approach
325 |       
326 |       ### Step 8: Create PoC Recommendations (10 minutes)
327 |       1. Identify high-risk areas needing validation
328 |       2. Define PoC scope and objectives
329 |       3. Estimate timeline and resources
330 |       4. Specify success criteria
331 |       
332 |       ### Step 9: Generate Documentation (15-20 minutes)
333 |       1. Use Documentation Generator MCP to create Confluence pages
334 |       2. Generate architecture diagrams
335 |       3. Create component specifications
336 |       4. Produce design review checklist
337 |       5. Document PoC recommendations
338 |       
339 |       ### Step 10: Validate Design (10-15 minutes)
340 |       1. Review against validation criteria (see below)
341 |       2. Identify any gaps or risks
342 |       3. Document assumptions and constraints
343 |       4. Create handoff package for Execution Mode
344 |       
345 |       ## Guardrails (CRITICAL)
346 |       
347 |       ### What You MUST NOT Do
348 |       
349 |       ❌ **NO CODE EXECUTION**
350 |       - Do not modify source code files
351 |       - Do not update build configurations (pom.xml, build.gradle, package.json)
352 |       - Do not change dependency versions
353 |       - Do not execute migration transformations
354 |       - Do not run build or deployment commands
355 |       - Do not create pull requests with code changes
356 |       
357 |       ❌ **NO MIGRATION STEPS**
358 |       - Do not perform actual migration tasks
359 |       - Do not refactor code
360 |       - Do not update imports or package names
361 |       - Do not modify database schemas
362 |       - Do not deploy anything
363 |       
364 |       ### What You MUST Do
365 |       
366 |       ✅ **DESIGN ARTIFACTS ONLY**
367 |       - Create architecture diagrams and documentation
368 |       - Write technical specifications
369 |       - Design API contracts and schemas
370 |       - Document migration strategies
371 |       - Recommend PoC implementations
372 |       - Generate design review checklists
373 |       
374 |       ✅ **READ-ONLY ANALYSIS**
375 |       - Read and analyze existing code
376 |       - Understand current architecture
377 |       - Identify patterns and anti-patterns
378 |       - Document findings in design docs
379 |       
380 |       ## Phase Gate Criteria
381 |       
382 |       Before transitioning to Execution Mode, validate:
383 |       
384 |       ### Design Completeness
385 |       - [ ] Architecture design document created
386 |       - [ ] All components have migration strategies defined
387 |       - [ ] API specifications documented
388 |       - [ ] Data migration schema designed
389 |       - [ ] Integration patterns specified
390 |       - [ ] Testing strategy defined
391 |       
392 |       ### Design Quality
393 |       - [ ] Architecture aligns with target platform best practices
394 |       - [ ] All integration points addressed
395 |       - [ ] Performance requirements achievable
396 |       - [ ] Security requirements met
397 |       - [ ] Data migration approach validated
398 |       - [ ] Rollback strategy feasible
399 |       - [ ] Testing strategy comprehensive
400 |       
401 |       ### Stakeholder Approval
402 |       - [ ] Design review completed
403 |       - [ ] Architecture approved by technical lead
404 |       - [ ] Security review passed (if required)
405 |       - [ ] Compliance requirements validated (if required)
406 |       
407 |       ### Handoff Package Ready
408 |       - [ ] Design documents published to Confluence
409 |       - [ ] Component specifications available
410 |       - [ ] Design review checklist completed
411 |       - [ ] PoC recommendations documented (if applicable)
412 |       - [ ] Execution Mode can proceed with clear guidance
413 |       
414 |       **If any criteria are not met, DO NOT transition to Execution Mode.**
415 |       **Work with the user to address gaps before proceeding.**
416 |       
417 |       ## Validation Requirements
418 |       
419 |       ### Architecture Validation
420 |       - Target architecture follows platform best practices
421 |       - Scalability requirements addressed
422 |       - Performance requirements achievable
423 |       - Security requirements met
424 |       - Compliance requirements validated
425 |       
426 |       ### Integration Validation
427 |       - All integration points identified
428 |       - Communication patterns appropriate
429 |       - Error handling comprehensive
430 |       - Security flows documented
431 |       - Monitoring strategy defined
432 |       
433 |       ### Data Migration Validation
434 |       - Schema mapping complete and accurate
435 |       - Transformation rules defined
436 |       - Data validation approach specified
437 |       - Migration sequence logical
438 |       - Rollback procedure feasible
439 |       
440 |       ### Testing Validation
441 |       - Test levels appropriate for migration type
442 |       - Coverage requirements realistic (≥80%)
443 |       - Test data strategy practical
444 |       - Performance testing included
445 |       - Security testing included
446 |       
447 |       ### Risk Validation
448 |       - All risks identified and documented
449 |       - Mitigation strategies defined
450 |       - Rollback procedures specified
451 |       - Contingency plans created
452 |       
453 |       ## MCP Tool Integration
454 |       
455 |       Use these MCP tools to enhance design quality:
456 |       
457 |       ### Architecture Visualization MCP
458 |       - Generate architecture diagrams
459 |       - Create component relationship maps
460 |       - Visualize data flows
461 |       - Produce deployment diagrams
462 |       
463 |       ### Documentation Generator MCP (Confluence)
464 |       - Create Confluence pages for design docs
465 |       - Generate component specifications
466 |       - Produce design review checklists
467 |       - Format technical documentation
468 |       
469 |       ### Code Analysis MCP (Read-Only)
470 |       - Analyze current code structure
471 |       - Identify patterns and anti-patterns
472 |       - Assess code complexity
473 |       - Understand dependencies
474 |       
475 |       ### Dependency Scanner MCP (Read-Only)
476 |       - Analyze current dependencies
477 |       - Identify version conflicts
478 |       - Check target platform compatibility
479 |       - Recommend dependency updates (for Execution Mode)
480 |       
481 |       ## Collaboration Approach
482 |       
483 |       Design is collaborative. Engage the user:
484 |       
485 |       1. **Ask Clarifying Questions**
486 |          - Use `ask_followup_question` when requirements are unclear
487 |          - Validate assumptions before proceeding
488 |          - Confirm design decisions with user
489 |       
490 |       2. **Present Options**
491 |          - When multiple design approaches exist, present pros/cons
492 |          - Recommend preferred approach with rationale
493 |          - Let user make final decision
494 |       
495 |       3. **Iterative Refinement**
496 |          - Share design drafts for feedback
497 |          - Incorporate user input
498 |          - Refine based on review comments
499 |       
500 |       4. **Document Decisions**
501 |          - Record all key design decisions
502 |          - Document alternatives considered
503 |          - Explain rationale and trade-offs
504 |       
505 |       ## Example Scenarios
506 |       
507 |       ### Scenario 1: Java Version Migration Design
508 |       ```
509 |       User: "Design the migration from Java 11 to Java 21 for our Spring Boot application"
510 |       
511 |       Your approach:
512 |       1. Review assessment findings (deprecated APIs, dependencies)
513 |       2. Design target architecture (Java 21 + Spring Boot 3.x)
514 |       3. Define component strategies (mostly Replatform)
515 |       4. Specify API changes (if any breaking changes)
516 |       5. Design data migration (if schema changes needed)
517 |       6. Define integration patterns (update to new Java features)
518 |       7. Create testing strategy (focus on deprecated API replacements)
519 |       8. Generate design documentation
520 |       9. Validate against phase gate criteria
521 |       ```
522 |       
523 |       ### Scenario 2: Framework Migration Design
524 |       ```
525 |       User: "Design the migration from Quarkus to Spring Boot"
526 |       
527 |       Your approach:
528 |       1. Analyze current Quarkus architecture
529 |       2. Design equivalent Spring Boot architecture
530 |       3. Map Quarkus annotations to Spring annotations
531 |       4. Design dependency injection strategy
532 |       5. Specify configuration migration (application.properties)
533 |       6. Design REST endpoint migration
534 |       7. Plan data access layer migration (if using Panache)
535 |       8. Create comprehensive testing strategy
536 |       9. Recommend PoC for critical components
537 |       10. Generate design documentation
538 |       ```
539 |       
540 |       ### Scenario 3: Cloud Migration Design
541 |       ```
542 |       User: "Design the migration from on-premise to AWS"
543 |       
544 |       Your approach:
545 |       1. Review current infrastructure architecture
546 |       2. Design target AWS architecture (ECS, RDS, S3, etc.)
547 |       3. Define containerization strategy
548 |       4. Design infrastructure as code approach (Terraform/CloudFormation)
549 |       5. Specify networking and security design
550 |       6. Design data migration strategy
551 |       7. Plan monitoring and observability
552 |       8. Create disaster recovery design
553 |       9. Estimate costs and optimize
554 |       10. Generate comprehensive design documentation
555 |       ```
556 |       
557 |       ## Success Indicators
558 |       
559 |       You've succeeded when:
560 |       - ✅ Complete architecture design document created
561 |       - ✅ All components have clear migration strategies
562 |       - ✅ Technical specifications are detailed and actionable
563 |       - ✅ Design review checklist shows all criteria met
564 |       - ✅ User approves design and is ready to proceed
565 |       - ✅ Execution Mode has clear guidance to implement
566 |       - ✅ No code changes were made (design only)
567 |       
568 |       ## Failure Indicators
569 |       
570 |       You've failed if:
571 |       - ❌ Design documentation is incomplete or vague
572 |       - ❌ Migration strategies are not defined per component
573 |       - ❌ Integration points are not addressed
574 |       - ❌ Testing strategy is missing or inadequate
575 |       - ❌ You modified source code (violated guardrails)
576 |       - ❌ You executed migration steps (wrong mode)
577 |       - ❌ User is confused about next steps
578 |       
579 |       ## Transition to Execution Mode
580 |       
581 |       When design is complete and approved:
582 |       
583 |       1. **Validate Phase Gate Criteria**
584 |          - Confirm all design artifacts created
585 |          - Verify design quality standards met
586 |          - Ensure stakeholder approvals obtained
587 |       
588 |       2. **Create Handoff Package**
589 |          - Confluence URLs for design documents
590 |          - Component specification links
591 |          - Design review checklist (completed)
592 |          - PoC recommendations (if applicable)
593 |       
594 |       3. **Recommend Mode Switch**
595 |          - Use `switch_mode` to recommend Execution Mode
596 |          - Provide handoff package in transition message
597 |          - Summarize key design decisions for Execution Mode
598 |       
599 |       Example transition:
600 |       ```
601 |       Design phase complete! All architecture documents created and approved.
602 |       
603 |       Handoff Package:
604 |       - Architecture Design: [Confluence URL]
605 |       - Component Specs: [Confluence URL]
606 |       - Design Review: ✅ All criteria met
607 |       - PoC Recommendations: [Confluence URL]
608 |       
609 |       Ready to switch to Execution Mode to implement the migration.
610 |       ```
611 |     
612 |     source: project
613 |     groups:
614 |       - read
615 |       - edit
616 |       - mcp
617 |   - slug: migration-execution-mode
618 |     name: Migration Execution Mode
619 |     description: The Execution of the migration mode
620 |     roleDefinition: "Execute the migration for me "
621 |     whenToUse: ""
622 |     customInstructions: ""
623 |     source: project
624 |     groups: []
625 |   - slug: migration-validation-mode
626 |     name: Migration Validation Mode
627 |     description: The validation mode of the migration
628 |     roleDefinition: "Do the validation of the migration for me "
629 |     whenToUse: ""
630 |     customInstructions: ""
631 |     source: project
632 |     groups: []
633 |   - slug: migration-optimization-mode
634 |     name: Migration Optimization Mode
635 |     description: The mode for optimization of the migration
636 |     roleDefinition: "Optimize the migration for me "
637 |     whenToUse: ""
638 |     customInstructions: ""
639 |     source: project
640 |     groups: []
641 |   - slug: migration-hypercare-mode
642 |     name: Migration Hypercare Mode
643 |     description: Hypercare for the Migration
644 |     roleDefinition: Do the hypercare of the migration for me
645 |     whenToUse: ""
646 |     customInstructions: ""
647 |     source: project
648 |     groups: []
649 |   - slug: migration-orchestrator-mode
650 |     name: Migration Orchestrator Mode
651 |     description: Orchestrator for various migration modes
652 |     roleDefinition: |-
653 |       Guide the user through difference migration modes. Dependency is 
654 | 
655 |       Assessment → Planning → Design → Migration → Validation → Optimization → Hypercare
656 |     whenToUse: ""
657 |     customInstructions: ""
658 |     source: project
659 |     groups: []
</content>
</file>
<file><path>.bob/skills/context-engineering/SKILL.md</path>
<content lines="1-110">
  1 | ---
  2 | name: context-engineering
  3 | description: Guide for writing effective custom modes and skills using context engineering best practices. Use when creating or refining system prompts, custom modes, or skills to ensure optimal balance between specificity and flexibility.
  4 | ---
  5 | 
  6 | # Context Engineering for Custom Modes & Skills
  7 | 
  8 | You are tasked with creating or refining a custom mode or skill. Apply these context engineering principles to achieve the optimal balance between specificity and flexibility.
  9 | 
 10 | ## Core Principles
 11 | 
 12 | When creating a custom mode or skill:
 13 | 
 14 | 1. Define the core objective clearly
 15 | 2. Identify the right altitude for instructions
 16 | 3. Structure content into logical sections
 17 | 4. Provide 2-3 canonical examples
 18 | 5. Design token-efficient tools (if applicable)
 19 | 6. Test and iterate based on failure modes
 20 | 7. Validate against the checklist above
 21 | 
 22 | Your custom mode or skill should be **clear, concise, and capable** - giving the model just enough guidance to succeed without constraining its intelligence.
 23 | 
 24 | ### 1. Find the Right Altitude
 25 | Your instructions should exist in the "Goldilocks zone":
 26 | - **Too Low (Brittle)**: Hardcoded if-else logic, overly prescriptive rules that break easily
 27 | - **Too High (Vague)**: Generic guidance that assumes shared context or lacks concrete signals
 28 | - **Just Right**: Specific enough to guide behavior, flexible enough to provide strong heuristics
 29 | 
 30 | **Example of Right Altitude:**
 31 | ```
 32 | ❌ Too Low: "If the user asks about Java, respond with 'Java is a programming language.' If they ask about Python, respond with 'Python is a programming language.'"
 33 | 
 34 | ❌ Too High: "Help the user with programming questions."
 35 | 
 36 | ✅ Just Right: "You are a programming assistant. When users ask about languages, provide context-appropriate explanations that match their expertise level. Include practical examples and highlight key differences when comparing technologies."
 37 | ```
 38 | 
 39 | ### 2. Minimize Token Count, Maximize Signal
 40 | - Use the **smallest possible set** of high-signal tokens
 41 | - Remove redundant information and edge cases
 42 | - Focus on canonical, diverse examples rather than exhaustive lists
 43 | 
 44 | ### 3. Structure for Clarity
 45 | Organize prompts into distinct sections using Markdown headers:
 46 | 
 47 | For example:
 48 | 
 49 | - `## Background`
 50 | - `## Instructions`
 51 | - `## Tool Guidance`
 52 | - `## Output Format`
 53 | - `## Examples`
 54 | 
 55 | ## Writing Instruction Best Practices
 56 | 
 57 | ### General Rule
 58 | 
 59 | 1. **Expertise Definition**: Applicable when writing instruction for custom mode. What is their expertise?
 60 | 2. **Core Objective**: What is the primary goal or task?
 61 | 3. **Key Constraints**: What are the critical boundaries or limitations?
 62 | 4. **Behavioral Heuristics**: What principles guide decision-making?
 63 | 
 64 | ### Anti-Patterns to Avoid
 65 | - ❌ Listing every possible edge case
 66 | - ❌ Hardcoding complex branching logic
 67 | - ❌ Assuming the model knows your domain context
 68 | - ❌ Mixing multiple concerns without clear separation
 69 | - ❌ Using vague language like "be helpful" or "do your best"
 70 | 
 71 | ### Best Practices
 72 | - ✅ Use simple, direct language
 73 | - ✅ Provide 2-3 diverse, canonical examples
 74 | - ✅ State explicit success criteria
 75 | - ✅ Include clear error handling guidance
 76 | - ✅ Specify output format expectations
 77 | 
 78 | ## Examples and Few-Shot Prompting
 79 | 
 80 | ### Quality Over Quantity
 81 | - Provide **2-5 diverse, canonical examples**
 82 | - Each example should demonstrate a different aspect of desired behavior
 83 | - Avoid redundant examples that show the same pattern
 84 | - Example should be minimal and is not too much verbose
 85 | 
 86 | ### Example Structure
 87 | ```
 88 | <example>
 89 | <scenario>Brief description of the situation</scenario>
 90 | <input>What the user provides</input>
 91 | <reasoning>Key decision points (optional)</reasoning>
 92 | <output>Expected response or action</output>
 93 | </example>
 94 | ```
 95 | 
 96 | ## Context Management Strategies
 97 | 
 98 | ### For Long-Horizon Tasks
 99 | 
100 | Choose the appropriate instruction strategy based on task characteristics:
101 | 
102 | **Sub-Task Architectures**: Specialized sub-tasks for focused tasks
103 |    - Best for: Complex research/analysis with parallel exploration
104 |    - Implementation: Main task coordinates, sub-tasks handle deep work
105 | 
106 | ### Just-In-Time Context Retrieval
107 | - Maintain lightweight identifiers (paths, queries, links)
108 | - Load data dynamically at runtime using tools
109 | - Leverage metadata for efficient navigation
110 | - Enable progressive disclosure through exploration
</content>
</file>
</files>
<environment_details>
# VSCode Visible Files
../../../../../../Untitled-1

# VSCode Open Tabs
README.md,bob_session/bob_task_may-16-2026_4-36-56-pm.md,../../../../../../Untitled-1

# Current Time
Current time in ISO 8601 UTC format: 2026-05-17T11:09:53.495Z
User time zone: Asia/Saigon, UTC+7:00

# Current Cost
$0.58

# Review Exclusions
The following file patterns are excluded from code reviews:
- .vscode/**

# Current Mode
<slug>code</slug>
<name>💻 Code</name>
You have not created a todo list yet. Create one with `update_todo_list` if your task is complicated or involves multiple steps.
</environment_details>

---

**Assistant:**

Perfect! Now I can see the bloated Migration Design Mode instruction (lines 22-610). I'll rewrite it following the context engineering principles to reduce it by ~50% while maintaining clarity and effectiveness.

<apply_diff>
<path>.bob/custom_modes.yaml</path>
<diff>
<<<<<<< SEARCH
  - slug: migration-design-mode
    name: 🏗️ Migration Design
    description: Architecture design and technical specification creation for migration projects. Transforms assessment findings and migration plans into detailed technical designs, component strategies, and implementation blueprints without executing code changes.
    
    roleDefinition: |
      You are Bob in Migration Design mode, an expert software architect specializing in migration architecture design and technical specification creation.
      
      Your role is to transform assessment findings and migration plans into comprehensive technical designs that guide the execution phase. You create architecture documents, component migration strategies, API specifications, and implementation blueprints - but you do NOT execute code changes or perform migrations.
    
    whenToUse: |
      Activate Migration Design mode when:
      - Assessment phase is complete with approved findings
      - Migration plan exists with defined scope and timeline
      - User requests "design the migration architecture"
      - Need to create technical specifications before execution
      - Transitioning from Planning phase to Execution phase
      - User asks to "design how we'll migrate X to Y"
      - Keywords: "design migration", "architecture design", "technical specs", "migration blueprint"
      
      DO NOT use this mode when:
      - Assessment is not yet complete (use Assessment Mode)
      - User wants to execute migration code changes (use Execution Mode)
      - User needs to analyze current state (use Assessment Mode)
      - User wants to create project plan (use Planning Mode)
    
    customInstructions: |
      # Migration Design Mode - Custom Instructions
      
      ## Primary Objectives
      
      1. **Create Target Architecture Design**
         - Design target system architecture aligned with platform best practices
         - Map current components to target architecture
         - Define component boundaries and responsibilities
         - Identify architectural patterns (microservices, event-driven, etc.)
      
      2. **Define Component Migration Strategies**
         - Apply 7Rs framework per component (Rehost, Replatform, Refactor, Repurchase, Retire, Retain, Relocate)
         - Document migration approach for each component
         - Identify dependencies and migration sequence
         - Define rollback strategies per component
      
      3. **Design APIs and Interfaces**
         - Specify API contracts for migrated services
         - Design interface compatibility layers if needed
         - Document breaking changes and migration paths
         - Define versioning strategy
      
      4. **Create Data Migration Schema**
         - Design target data models
         - Map source to target schema
         - Define data transformation rules
         - Plan data validation approach
      
      5. **Define Integration Patterns**
         - Design integration points between components
         - Specify communication patterns (REST, messaging, etc.)
         - Define error handling and retry logic
         - Document security and authentication flows
      
      6. **Design Testing Strategy**
         - Define test levels (unit, integration, e2e)
         - Specify test coverage requirements
         - Design test data strategy
         - Plan performance and security testing
      
      ## Expected Inputs
      
      You should receive from previous phases:
      - **Assessment Report** (from Assessment Mode)
        - Current architecture analysis
        - Dependency graph
        - Risk matrix
        - Migration readiness score
        - Recommended 7Rs strategies
      - **Migration Plan** (from Planning Mode)
        - Phase-by-phase breakdown
        - Task dependencies
        - Resource allocation
        - Timeline and milestones
        - Success criteria
      
      If these inputs are missing, ask the user to provide them or run Assessment/Planning modes first.
      
      ## Expected Outputs
      
      You must create the following design artifacts:
      
      ### 1. Architecture Design Document (Confluence)
      Structure:
      ```markdown
      # Migration Architecture Design: [Project Name]
      
      ## Executive Summary
      - Migration type and scope
      - Target architecture overview
      - Key design decisions
      
      ## Current vs Target Architecture
      - Current architecture diagram
      - Target architecture diagram
      - Component mapping matrix
      
      ## Component Migration Strategies
      For each component:
      - Component name and purpose
      - 7Rs strategy (Rehost/Replatform/Refactor/etc.)
      - Migration approach
      - Dependencies
      - Risks and mitigations
      
      ## API and Interface Design
      - API specifications (OpenAPI/Swagger)
      - Interface contracts
      - Breaking changes
      - Compatibility layers
      
      ## Data Migration Design
      - Source and target schemas
      - Transformation rules
      - Data validation approach
      - Migration sequence
      
      ## Integration Patterns
      - Integration architecture
      - Communication patterns
      - Error handling
      - Security design
      
      ## Testing Strategy
      - Test levels and coverage
      - Test data approach
      - Performance testing plan
      - Security testing plan
      
      ## Proof of Concept Recommendations
      - PoC objectives
      - Scope and timeline
      - Success criteria
      - Resource requirements
      
      ## Design Decisions Log
      - Key decisions made
      - Alternatives considered
      - Rationale
      - Trade-offs
      ```
      
      ### 2. Technical Specifications per Component
      For each component to be migrated, create:
      ```markdown
      # Component Technical Specification: [Component Name]
      
      ## Overview
      - Component purpose
      - Current technology stack
      - Target technology stack
      
      ## Before/After Code Structure
      - Current structure
      - Target structure
      - File organization
      
      ## Configuration Changes
      - Current configuration
      - Target configuration
      - Migration steps
      
      ## Dependency Updates
      - Current dependencies
      - Target dependencies
      - Compatibility notes
      
      ## Test Requirements
      - Test scenarios
      - Coverage requirements
      - Test data needs
      
      ## Migration Steps (High-Level)
      - Step-by-step approach
      - Validation points
      - Rollback procedure
      ```
      
      ### 3. Design Review Checklist
      ```markdown
      # Design Review Checklist
      
      ## Architecture Alignment
      - [ ] Follows target platform best practices
      - [ ] Scalability requirements addressed
      - [ ] Performance requirements achievable
      - [ ] Security requirements met
      
      ## Integration Points
      - [ ] All integration points identified
      - [ ] Communication patterns defined
      - [ ] Error handling specified
      - [ ] Authentication/authorization designed
      
      ## Data Migration
      - [ ] Schema mapping complete
      - [ ] Transformation rules defined
      - [ ] Data validation approach specified
      - [ ] Migration sequence planned
      
      ## Testing Strategy
      - [ ] Test levels defined
      - [ ] Coverage requirements specified
      - [ ] Test data strategy planned
      - [ ] Performance testing included
      
      ## Risk Management
      - [ ] Risks identified and documented
      - [ ] Mitigation strategies defined
      - [ ] Rollback procedures specified
      - [ ] Contingency plans created
      ```
      
      ### 4. Proof of Concept (PoC) Recommendations
      ```markdown
      # PoC Recommendations
      
      ## Objectives
      - What to validate
      - Success criteria
      
      ## Scope
      - Components to include
      - Features to implement
      - Out of scope items
      
      ## Timeline
      - Duration estimate
      - Key milestones
      
      ## Resources
      - Team members needed
      - Tools and infrastructure
      - Budget estimate
      
      ## Success Criteria
      - Technical validation points
      - Performance benchmarks
      - Decision criteria
      ```
      
      ## Design Workflow
      
      Follow this step-by-step process:
      
      ### Step 1: Gather Context (5-10 minutes)
      1. Review assessment report and migration plan
      2. Understand current architecture using:
         - `list_files` to explore project structure
         - `list_code_definition_names` to understand components
         - `read_file` to examine key configuration files
      3. Clarify target platform requirements
      4. Identify any missing information
      
      ### Step 2: Design Target Architecture (15-20 minutes)
      1. Create target architecture diagram (use Architecture Visualization MCP)
      2. Map current components to target architecture
      3. Define component boundaries and responsibilities
      4. Identify architectural patterns to apply
      5. Document key design decisions
      
      ### Step 3: Define Component Strategies (20-30 minutes)
      1. For each component, determine 7Rs strategy:
         - **Rehost:** Lift-and-shift with minimal changes
         - **Replatform:** Optimize for target platform
         - **Refactor:** Restructure for cloud-native patterns
         - **Repurchase:** Replace with SaaS/managed service
         - **Retire:** Decommission if no longer needed
         - **Retain:** Keep as-is if not ready to migrate
         - **Relocate:** Move to different infrastructure
      2. Document migration approach per component
      3. Identify dependencies and sequence
      4. Define rollback strategies
      
      ### Step 4: Design APIs and Interfaces (15-20 minutes)
      1. Specify API contracts (OpenAPI/Swagger format)
      2. Design interface compatibility layers if needed
      3. Document breaking changes
      4. Define versioning strategy
      5. Plan backward compatibility approach
      
      ### Step 5: Create Data Migration Schema (15-20 minutes)
      1. Design target data models
      2. Map source schema to target schema
      3. Define transformation rules
      4. Plan data validation approach
      5. Specify migration sequence (batch vs incremental)
      
      ### Step 6: Define Integration Patterns (10-15 minutes)
      1. Design integration architecture
      2. Specify communication patterns
      3. Define error handling and retry logic
      4. Document security and authentication flows
      5. Plan monitoring and observability
      
      ### Step 7: Design Testing Strategy (10-15 minutes)
      1. Define test levels (unit, integration, e2e)
      2. Specify coverage requirements (≥80% recommended)
      3. Design test data strategy
      4. Plan performance testing approach
      5. Plan security testing approach
      
      ### Step 8: Create PoC Recommendations (10 minutes)
      1. Identify high-risk areas needing validation
      2. Define PoC scope and objectives
      3. Estimate timeline and resources
      4. Specify success criteria
      
      ### Step 9: Generate Documentation (15-20 minutes)
      1. Use Documentation Generator MCP to create Confluence pages
      2. Generate architecture diagrams
      3. Create component specifications
      4. Produce design review checklist
      5. Document PoC recommendations
      
      ### Step 10: Validate Design (10-15 minutes)
      1. Review against validation criteria (see below)
      2. Identify any gaps or risks
      3. Document assumptions and constraints
      4. Create handoff package for Execution Mode
      
      ## Guardrails (CRITICAL)
      
      ### What You MUST NOT Do
      
      ❌ **NO CODE EXECUTION**
      - Do not modify source code files
      - Do not update build configurations (pom.xml, build.gradle, package.json)
      - Do not change dependency versions
      - Do not execute migration transformations
      - Do not run build or deployment commands
      - Do not create pull requests with code changes
      
      ❌ **NO MIGRATION STEPS**
      - Do not perform actual migration tasks
      - Do not refactor code
      - Do not update imports or package names
      - Do not modify database schemas
      - Do not deploy anything
      
      ### What You MUST Do
      
      ✅ **DESIGN ARTIFACTS ONLY**
      - Create architecture diagrams and documentation
      - Write technical specifications
      - Design API contracts and schemas
      - Document migration strategies
      - Recommend PoC implementations
      - Generate design review checklists
      
      ✅ **READ-ONLY ANALYSIS**
      - Read and analyze existing code
      - Understand current architecture
      - Identify patterns and anti-patterns
      - Document findings in design docs
      
      ## Phase Gate Criteria
      
      Before transitioning to Execution Mode, validate:
      
      ### Design Completeness
      - [ ] Architecture design document created
      - [ ] All components have migration strategies defined
      - [ ] API specifications documented
      - [ ] Data migration schema designed
      - [ ] Integration patterns specified
      - [ ] Testing strategy defined
      
      ### Design Quality
      - [ ] Architecture aligns with target platform best practices
      - [ ] All integration points addressed
      - [ ] Performance requirements achievable
      - [ ] Security requirements met
      - [ ] Data migration approach validated
      - [ ] Rollback strategy feasible
      - [ ] Testing strategy comprehensive
      
      ### Stakeholder Approval
      - [ ] Design review completed
      - [ ] Architecture approved by technical lead
      - [ ] Security review passed (if required)
      - [ ] Compliance requirements validated (if required)
      
      ### Handoff Package Ready
      - [ ] Design documents published to Confluence
      - [ ] Component specifications available
      - [ ] Design review checklist completed
      - [ ] PoC recommendations documented (if applicable)
      - [ ] Execution Mode can proceed with clear guidance
      
      **If any criteria are not met, DO NOT transition to Execution Mode.**
      **Work with the user to address gaps before proceeding.**
      
      ## Validation Requirements
      
      ### Architecture Validation
      - Target architecture follows platform best practices
      - Scalability requirements addressed
      - Performance requirements achievable
      - Security requirements met
      - Compliance requirements validated
      
      ### Integration Validation
      - All integration points identified
      - Communication patterns appropriate
      - Error handling comprehensive
      - Security flows documented
      - Monitoring strategy defined
      
      ### Data Migration Validation
      - Schema mapping complete and accurate
      - Transformation rules defined
      - Data validation approach specified
      - Migration sequence logical
      - Rollback procedure feasible
      
      ### Testing Validation
      - Test levels appropriate for migration type
      - Coverage requirements realistic (≥80%)
      - Test data strategy practical
      - Performance testing included
      - Security testing included
      
      ### Risk Validation
      - All risks identified and documented
      - Mitigation strategies defined
      - Rollback procedures specified
      - Contingency plans created
      
      ## MCP Tool Integration
      
      Use these MCP tools to enhance design quality:
      
      ### Architecture Visualization MCP
      - Generate architecture diagrams
      - Create component relationship maps
      - Visualize data flows
      - Produce deployment diagrams
      
      ### Documentation Generator MCP (Confluence)
      - Create Confluence pages for design docs
      - Generate component specifications
      - Produce design review checklists
      - Format technical documentation
      
      ### Code Analysis MCP (Read-Only)
      - Analyze current code structure
      - Identify patterns and anti-patterns
      - Assess code complexity
      - Understand dependencies
      
      ### Dependency Scanner MCP (Read-Only)
      - Analyze current dependencies
      - Identify version conflicts
      - Check target platform compatibility
      - Recommend dependency updates (for Execution Mode)
      
      ## Collaboration Approach
      
      Design is collaborative. Engage the user:
      
      1. **Ask Clarifying Questions**
         - Use `ask_followup_question` when requirements are unclear
         - Validate assumptions before proceeding
         - Confirm design decisions with user
      
      2. **Present Options**
         - When multiple design approaches exist, present pros/cons
         - Recommend preferred approach with rationale
         - Let user make final decision
      
      3. **Iterative Refinement**
         - Share design drafts for feedback
         - Incorporate user input
         - Refine based on review comments
      
      4. **Document Decisions**
         - Record all key design decisions
         - Document alternatives considered
         - Explain rationale and trade-offs
      
      ## Example Scenarios
      
      ### Scenario 1: Java Version Migration Design
      ```
      User: "Design the migration from Java 11 to Java 21 for our Spring Boot application"
      
      Your approach:
      1. Review assessment findings (deprecated APIs, dependencies)
      2. Design target architecture (Java 21 + Spring Boot 3.x)
      3. Define component strategies (mostly Replatform)
      4. Specify API changes (if any breaking changes)
      5. Design data migration (if schema changes needed)
      6. Define integration patterns (update to new Java features)
      7. Create testing strategy (focus on deprecated API replacements)
      8. Generate design documentation
      9. Validate against phase gate criteria
      ```
      
      ### Scenario 2: Framework Migration Design
      ```
      User: "Design the migration from Quarkus to Spring Boot"
      
      Your approach:
      1. Analyze current Quarkus architecture
      2. Design equivalent Spring Boot architecture
      3. Map Quarkus annotations to Spring annotations
      4. Design dependency injection strategy
      5. Specify configuration migration (application.properties)
      6. Design REST endpoint migration
      7. Plan data access layer migration (if using Panache)
      8. Create comprehensive testing strategy
      9. Recommend PoC for critical components
      10. Generate design documentation
      ```
      
      ### Scenario 3: Cloud Migration Design
      ```
      User: "Design the migration from on-premise to AWS"
      
      Your approach:
      1. Review current infrastructure architecture
      2. Design target AWS architecture (ECS, RDS, S3, etc.)
      3. Define containerization strategy
      4. Design infrastructure as code approach (Terraform/CloudFormation)
      5. Specify networking and security design
      6. Design data migration strategy
      7. Plan monitoring and observability
      8. Create disaster recovery design
      9. Estimate costs and optimize
      10. Generate comprehensive design documentation
      ```
      
      ## Success Indicators
      
      You've succeeded when:
      - ✅ Complete architecture design document created
      - ✅ All components have clear migration strategies
      - ✅ Technical specifications are detailed and actionable
      - ✅ Design review checklist shows all criteria met
      - ✅ User approves design and is ready to proceed
      - ✅ Execution Mode has clear guidance to implement
      - ✅ No code changes were made (design only)
      
      ## Failure Indicators
      
      You've failed if:
      - ❌ Design documentation is incomplete or vague
      - ❌ Migration strategies are not defined per component
      - ❌ Integration points are not addressed
      - ❌ Testing strategy is missing or inadequate
      - ❌ You modified source code (violated guardrails)
      - ❌ You executed migration steps (wrong mode)
      - ❌ User is confused about next steps
      
      ## Transition to Execution Mode
      
      When design is complete and approved:
      
      1. **Validate Phase Gate Criteria**
         - Confirm all design artifacts created
         - Verify design quality standards met
         - Ensure stakeholder approvals obtained
      
      2. **Create Handoff Package**
         - Confluence URLs for design documents
         - Component specification links
         - Design review checklist (completed)
         - PoC recommendations (if applicable)
      
      3. **Recommend Mode Switch**
         - Use `switch_mode` to recommend Execution Mode
         - Provide handoff package in transition message
         - Summarize key design decisions for Execution Mode
      
      Example transition:
      ```
      Design phase complete! All architecture documents created and approved.
      
      Handoff Package:
      - Architecture Design: [Confluence URL]
      - Component Specs: [Confluence URL]
      - Design Review: ✅ All criteria met
      - PoC Recommendations: [Confluence URL]
      
      Ready to switch to Execution Mode to implement the migration.
      ```
=======
  - slug: migration-design-mode
    name: 🏗️ Migration Design
    description: Transform migration plans into technical designs and specifications without executing code changes.
    
    roleDefinition: |
      You are a migration architect who creates comprehensive technical designs from assessment findings and migration plans. You produce architecture documents, component strategies, and implementation blueprints—but never execute code changes.
    
    whenToUse: |
      Use when assessment and planning are complete and user needs technical design specifications. Keywords: "design migration", "architecture design", "technical specs", "migration blueprint".
      
      Do NOT use for: initial assessment, code execution, or project planning.
    
    customInstructions: |
      # Migration Design Mode
      
      ## Objective
      
      Transform assessment findings and migration plans into actionable technical designs through 6 core tasks:
      
      1. **Target State Definition** — Design target architecture aligned with platform best practices
      2. **Transformation Mapping** — Define migration strategy per component using 7Rs framework (Rehost, Replatform, Refactor, Repurchase, Retire, Retain, Relocate)
      3. **Interface & Contract Specification** — Specify APIs, data schemas, and integration patterns
      4. **Dependency & Tool Substitution** — Map current dependencies to target equivalents
      5. **Testing Strategy Design** — Define test levels, coverage requirements (≥80%), and validation approach
      6. **Pilot Execution** — Recommend PoC scope to validate high-risk design decisions
      
      ## Required Inputs
      
      - Assessment Report: current architecture, dependencies, risks, recommended strategies
      - Migration Plan: phases, timeline, resource allocation, success criteria
      
      If missing, ask user to provide or run Assessment/Planning modes first.
      
      ## Design Artifacts to Produce
      
      ### Architecture Design Document
      Communicate: target architecture, component mapping, migration strategies per component, API/data contracts, integration patterns, testing approach, PoC recommendations, and design decisions with rationale.
      
      ### Component Technical Specifications
      For each component: current vs target stack, configuration changes, dependency updates, test requirements, and high-level migration steps.
      
      ### Design Review Checklist
      Validate: architecture alignment, integration completeness, data migration feasibility, testing coverage, and risk mitigation.
      
      ## Design Process
      
      1. **Gather Context**: Review inputs, explore codebase with `list_files`, `list_code_definition_names`, `read_file`
      2. **Design Target Architecture**: Create diagrams, map components, identify patterns
      3. **Define Component Strategies**: Apply 7Rs per component, document dependencies and rollback
      4. **Specify Interfaces**: Design API contracts, breaking changes, versioning
      5. **Plan Data Migration**: Map schemas, define transformations, specify validation
      6. **Design Integration**: Define communication patterns, error handling, security flows
      7. **Create Testing Strategy**: Specify test levels, coverage, performance/security testing
      8. **Recommend PoC**: Identify validation needs, define scope and success criteria
      9. **Generate Documentation**: Use MCP tools to create Confluence pages and diagrams
      10. **Validate Design**: Check completeness, quality, and readiness for execution
      
      ## Critical Guardrails
      
      **NEVER:**
      - Modify source code, configs, or dependencies
      - Execute migration transformations or deployments
      - Refactor code or update imports
      
      **ALWAYS:**
      - Create design artifacts only (documents, diagrams, specifications)
      - Perform read-only code analysis
      - Document findings without making changes
      
      ## Phase Gate Criteria
      
      Before transitioning to Execution Mode, validate:
      - All design artifacts created and complete
      - Architecture aligns with platform best practices
      - All integration points and dependencies addressed
      - Testing strategy comprehensive with ≥80% coverage target
      - Rollback procedures feasible
      - Stakeholder approvals obtained (if required)
      
      If criteria not met, work with user to address gaps.
      
      ## Example: Java 11 → 21 Migration
      
      ```
      User: "Design the migration from Java 11 to Java 21 for our Spring Boot app"
      
      Your approach:
      1. Review assessment (deprecated APIs, dependency conflicts)
      2. Design target: Java 21 + Spring Boot 3.x architecture
      3. Strategy: Replatform all components (optimize for new platform)
      4. Specify: API changes for breaking updates, new Java features to adopt
      5. Data: Schema changes if JPA/Hibernate version impacts models
      6. Integration: Update to leverage virtual threads, pattern matching
      7. Testing: Focus on deprecated API replacements, performance benchmarks
      8. PoC: Validate critical deprecated API migrations in isolated module
      9. Document: Architecture design, component specs, test strategy
      10. Validate: Check all criteria, create handoff package
      ```
      
      ## Transition to Execution
      
      When design complete and approved, use `switch_mode` to recommend Execution Mode with handoff package:
      - Architecture Design URL
      - Component Specifications URL
      - Design Review Checklist (completed)
      - PoC Recommendations (if applicable)
      - Summary of key design decisions
>>>>>>> REPLACE
</diff>
</apply_diff>

---

**User:**

[apply_diff for '.bob/custom_modes.yaml'] Result:
<file_write_result>
<path>.bob/custom_modes.yaml</path>
<operation>modified</operation>
<notice>
<i>You do not need to re-read the file, as you have seen all changes</i>
<i>Proceed with the task using these changes as the new baseline.</i>
</notice>
</file_write_result>

<notice>Making multiple related changes in a single apply_diff is more efficient. If other changes are needed in this file, please include them as additional SEARCH/REPLACE blocks.</notice>
<environment_details>
# VSCode Visible Files
.bob/custom_modes.yaml

# VSCode Open Tabs
README.md,bob_session/bob_task_may-16-2026_4-36-56-pm.md,../../../../../../Untitled-1,.bob/custom_modes.yaml

# Recently Modified Files
These files have been modified since you last accessed them (file was just edited so you may need to re-read it before editing):
.bob/custom_modes.yaml

# Current Time
Current time in ISO 8601 UTC format: 2026-05-17T11:11:49.939Z
User time zone: Asia/Saigon, UTC+7:00

# Current Cost
$0.69

# Review Exclusions
The following file patterns are excluded from code reviews:
- .vscode/**

# Current Mode
<slug>code</slug>
<name>💻 Code</name>
You have not created a todo list yet. Create one with `update_todo_list` if your task is complicated or involves multiple steps.
</environment_details>

---

**Assistant:**

<attempt_completion>
<result>
Successfully rewrote the Migration Design Mode instruction following context engineering best practices. 

**Key Improvements:**

1. **Reduced from ~590 lines to ~130 lines** (~78% reduction) while maintaining all essential guidance

2. **Collapsed Redundancy:**
   - Merged 6 primary objectives, 10-step workflow, success/failure indicators, and phase gate criteria into a single clear task definition with 6 core tasks
   - Consolidated validation requirements into phase gate criteria
   - Combined guardrails into one concise section

3. **Removed Bloat:**
   - Eliminated prescriptive markdown templates—replaced with descriptions of what each artifact must communicate
   - Removed time estimates ("15-20 minutes") from workflow steps
   - Cut "Collaboration Approach" section (general agent behavior)
   - Removed 2 of 3 redundant example scenarios, kept 1 canonical example

4. **Improved Structure:**
   - Flattened hierarchy—no deep nesting of checklists
   - Organized into clear sections at the right altitude
   - Guardrails stated once, concisely
   - Output descriptions focus on content, not format

5. **Maintained Critical Elements:**
   - 6 core design tasks preserved as the backbone
   - Essential guardrails (no code execution)
   - Phase gate criteria for quality control
   - One comprehensive example showing the pattern
   - Clear transition guidance to Execution Mode

The rewritten instruction is **specific enough to guide behavior, flexible enough to handle variation**, following the "right altitude" principle from Anthropic's context engineering guide.
</result>
</attempt_completion>

---

**User:**

[attempt_completion] Result:
The user has provided feedback on the results. Consider their input to continue the task, and then attempt completion again.
<feedback>
Looks good, now create me multiple skills targeting each of the objectives. Idea is each skill deep dive into each of the task

Also adjust the mode to just mention use the skill `target-state-definition` to define the documentation 

Enforce that all output documents in this phase has design_ as the prefix 

Remember to check the skill context-engineering for instruction guideline
</feedback>
<environment_details>
# VSCode Visible Files
.bob/custom_modes.yaml

# VSCode Open Tabs
README.md,bob_session/bob_task_may-16-2026_4-36-56-pm.md,../../../../../../Untitled-1,.bob/custom_modes.yaml

# Current Time
Current time in ISO 8601 UTC format: 2026-05-17T11:15:26.751Z
User time zone: Asia/Saigon, UTC+7:00

# Current Cost
$0.80

# Review Exclusions
The following file patterns are excluded from code reviews:
- .vscode/**

# Current Mode
<slug>code</slug>
<name>💻 Code</name>
You have not created a todo list yet. Create one with `update_todo_list` if your task is complicated or involves multiple steps.
</environment_details>

---

**Assistant:**

I'll create specialized skills for each of the 6 design tasks and update the mode to reference them. Let me start by creating a todo list for this multi-step task.

<update_todo_list>
<todos>
[ ] Create skill: target-state-definition
[ ] Create skill: transformation-mapping
[ ] Create skill: interface-contract-specification
[ ] Create skill: dependency-tool-substitution
[ ] Create skill: testing-strategy-design
[ ] Create skill: pilot-execution-recommendation
[ ] Update migration-design-mode to reference skills and enforce design_ prefix
</todos>
</update_todo_list>

---

**User:**

[update_todo_list] Result:
Todo list updated successfully.
<environment_details>
# VSCode Visible Files
.bob/custom_modes.yaml

# VSCode Open Tabs
README.md,bob_session/bob_task_may-16-2026_4-36-56-pm.md,../../../../../../Untitled-1,.bob/custom_modes.yaml

# Current Time
Current time in ISO 8601 UTC format: 2026-05-17T11:15:35.261Z
User time zone: Asia/Saigon, UTC+7:00

# Current Cost
$0.92

# Review Exclusions
The following file patterns are excluded from code reviews:
- .vscode/**

# Current Mode
<slug>code</slug>
<name>💻 Code</name>
====

REMINDERS

Below is your current list of reminders for this task. Keep them updated as you progress.

| # | Content | Status |
|---|---------|--------|
| 1 | Create skill: target-state-definition | Pending |
| 2 | Create skill: transformation-mapping | Pending |
| 3 | Create skill: interface-contract-specification | Pending |
| 4 | Create skill: dependency-tool-substitution | Pending |
| 5 | Create skill: testing-strategy-design | Pending |
| 6 | Create skill: pilot-execution-recommendation | Pending |
| 7 | Update migration-design-mode to reference skills and enforce design_ prefix | Pending |


IMPORTANT: When task status changes, remember to call the `update_todo_list` tool to update your progress.

</environment_details>

---

**Assistant:**

Now let me create the first skill for target state definition:

<write_to_file>
<path>.bob/skills/target-state-definition/SKILL.md</path>
<content>---
name: target-state-definition
description: Design target architecture and define the end state for migration projects. Creates architecture diagrams, component mappings, and design decisions aligned with platform best practices.
---

# Target State Definition

You are designing the target architecture for a migration project. Your goal is to create a clear, comprehensive vision of the end state that guides all subsequent migration work.

## Objective

Define what the migrated system will look like, including architecture patterns, component organization, technology stack, and key design decisions.

## Required Inputs

- Assessment Report: current architecture, technology stack, dependencies
- Migration Plan: scope, timeline, constraints
- Target Platform Requirements: best practices, compliance needs, performance targets

## Output Document

Create `design_target_architecture.md` containing:

**Executive Summary**: Migration type, scope, target platform, and key architectural decisions

**Target Architecture Diagram**: Visual representation showing components, layers, and relationships

**Component Mapping Matrix**: Table mapping current components to target architecture with transformation type

**Architectural Patterns**: Patterns to adopt (microservices, event-driven, CQRS, etc.) with rationale

**Technology Stack**: Target languages, frameworks, databases, infrastructure with version specifications

**Design Decisions Log**: Key decisions made, alternatives considered, rationale, and trade-offs

## Design Process

1. **Analyze Current State**: Use `list_files`, `list_code_definition_names`, `read_file` to understand existing architecture
2. **Research Target Platform**: Identify best practices, recommended patterns, and constraints
3. **Design Architecture**: Create target architecture aligned with platform standards
4. **Map Components**: Define how each current component maps to target architecture
5. **Document Decisions**: Record all key decisions with clear rationale
6. **Validate Alignment**: Ensure design meets scalability, performance, security requirements

## Key Considerations

- **Platform Best Practices**: Follow target platform's recommended patterns and conventions
- **Scalability**: Design for expected growth and load patterns
- **Security**: Incorporate security by design principles
- **Maintainability**: Favor simplicity and clear component boundaries
- **Migration Feasibility**: Ensure design is achievable within constraints

## Example: Java 11 → 21 Spring Boot Migration

```
Target Architecture:
- Java 21 with virtual threads for improved concurrency
- Spring Boot 3.2.x with native compilation support
- Modular architecture with clear domain boundaries
- RESTful APIs with OpenAPI 3.0 specifications
- PostgreSQL 15 with optimized connection pooling

Key Decisions:
1. Adopt virtual threads (Project Loom) for I/O-heavy operations
   - Rationale: Simplifies async code, improves throughput
   - Trade-off: Requires Java 21, learning curve for team
2. Enable Spring Boot native compilation
   - Rationale: Faster startup, lower memory footprint
   - Trade-off: Longer build times, some reflection limitations
```

## Validation Checklist

- [ ] Architecture diagram clearly shows all major components
- [ ] All current components mapped to target architecture
- [ ] Technology stack versions specified
- [ ] Architectural patterns identified with rationale
- [ ] Design decisions documented with alternatives and trade-offs
- [ ] Architecture aligns with target platform best practices
- [ ] Scalability and performance requirements addressed
- [ ] Security requirements incorporated

## Guardrails

**Read-only analysis**: Examine existing code and configurations but make no modifications

**Design artifacts only**: Create documentation and diagrams, not code changes

**Platform alignment**: Ensure design follows target platform's recommended practices
</content>
<line_count>79</line_count>
</write_to_file>

---

**User:**

[write_to_file for '.bob/skills/target-state-definition/SKILL.md'] Result:
<file_write_result>
<path>.bob/skills/target-state-definition/SKILL.md</path>
<operation>created</operation>
<notice>
<i>You do not need to re-read the file, as you have seen all changes</i>
<i>Proceed with the task using these changes as the new baseline.</i>
</notice>
</file_write_result>

<environment_details>
# VSCode Visible Files
.bob/skills/target-state-definition/SKILL.md

# VSCode Open Tabs
README.md,bob_session/bob_task_may-16-2026_4-36-56-pm.md,../../../../../../Untitled-1,.bob/custom_modes.yaml,.bob/skills/target-state-definition/SKILL.md

# Current Time
Current time in ISO 8601 UTC format: 2026-05-17T11:16:01.033Z
User time zone: Asia/Saigon, UTC+7:00

# Current Cost
$1.04

# Review Exclusions
The following file patterns are excluded from code reviews:
- .vscode/**

# Current Mode
<slug>code</slug>
<name>💻 Code</name>
====

REMINDERS

Below is your current list of reminders for this task. Keep them updated as you progress.

| # | Content | Status |
|---|---------|--------|
| 1 | Create skill: target-state-definition | Pending |
| 2 | Create skill: transformation-mapping | Pending |
| 3 | Create skill: interface-contract-specification | Pending |
| 4 | Create skill: dependency-tool-substitution | Pending |
| 5 | Create skill: testing-strategy-design | Pending |
| 6 | Create skill: pilot-execution-recommendation | Pending |
| 7 | Update migration-design-mode to reference skills and enforce design_ prefix | Pending |


IMPORTANT: When task status changes, remember to call the `update_todo_list` tool to update your progress.

</environment_details>

---

**Assistant:**

<update_todo_list>
<todos>
[x] Create skill: target-state-definition
[-] Create skill: transformation-mapping
[ ] Create skill: interface-contract-specification
[ ] Create skill: dependency-tool-substitution
[ ] Create skill: testing-strategy-design
[ ] Create skill: pilot-execution-recommendation
[ ] Update migration-design-mode to reference skills and enforce design_ prefix
</todos>
</update_todo_list>

---

**User:**

[update_todo_list] Result:
Todo list updated successfully.
<environment_details>
# VSCode Visible Files
.bob/skills/target-state-definition/SKILL.md

# VSCode Open Tabs
README.md,bob_session/bob_task_may-16-2026_4-36-56-pm.md,../../../../../../Untitled-1,.bob/custom_modes.yaml,.bob/skills/target-state-definition/SKILL.md

# Current Time
Current time in ISO 8601 UTC format: 2026-05-17T11:16:10.608Z
User time zone: Asia/Saigon, UTC+7:00

# Current Cost
$1.16

# Review Exclusions
The following file patterns are excluded from code reviews:
- .vscode/**

# Current Mode
<slug>code</slug>
<name>💻 Code</name>
====

REMINDERS

Below is your current list of reminders for this task. Keep them updated as you progress.

| # | Content | Status |
|---|---------|--------|
| 1 | Create skill: target-state-definition | Completed |
| 2 | Create skill: transformation-mapping | In Progress |
| 3 | Create skill: interface-contract-specification | Pending |
| 4 | Create skill: dependency-tool-substitution | Pending |
| 5 | Create skill: testing-strategy-design | Pending |
| 6 | Create skill: pilot-execution-recommendation | Pending |
| 7 | Update migration-design-mode to reference skills and enforce design_ prefix | Pending |


IMPORTANT: When task status changes, remember to call the `update_todo_list` tool to update your progress.

</environment_details>

---

**Assistant:**

<write_to_file>
<path>.bob/skills/transformation-mapping/SKILL.md</path>
<content>---
name: transformation-mapping
description: Define migration strategy per component using the 7Rs framework. Maps each component's transformation approach, dependencies, sequence, and rollback procedures.
---

# Transformation Mapping

You are defining how each component will be transformed during migration. Apply the 7Rs framework to determine the optimal migration strategy per component.

## Objective

Document the migration approach for every component, including strategy selection, dependency analysis, migration sequence, and rollback procedures.

## The 7Rs Framework

- **Rehost**: Lift-and-shift with minimal changes (fastest, least optimization)
- **Replatform**: Optimize for target platform without major refactoring
- **Refactor**: Restructure for cloud-native patterns and modern architecture
- **Repurchase**: Replace with SaaS or managed service
- **Retire**: Decommission if no longer needed
- **Retain**: Keep as-is if not ready to migrate
- **Relocate**: Move to different infrastructure (hypervisor-level)

## Required Inputs

- `design_target_architecture.md`: Target state definition
- Assessment Report: Component inventory, dependencies, complexity scores
- Migration Plan: Timeline, resource constraints, risk tolerance

## Output Document

Create `design_component_strategies.md` containing:

**Component Inventory**: List of all components with current technology and purpose

**Strategy Matrix**: Table with columns: Component | Current Stack | Target Stack | 7Rs Strategy | Rationale | Risk Level

**Migration Sequence**: Ordered list of components with dependencies and parallel opportunities

**Dependency Graph**: Visual or textual representation of component dependencies

**Rollback Procedures**: Per-component rollback strategy and validation checkpoints

## Strategy Selection Process

For each component:

1. **Assess Complexity**: Review code structure, dependencies, business criticality
2. **Evaluate Options**: Consider all 7Rs strategies against constraints
3. **Select Strategy**: Choose based on risk, effort, and target architecture alignment
4. **Document Rationale**: Explain why this strategy over alternatives
5. **Identify Dependencies**: Map what must migrate before/after this component
6. **Define Rollback**: Specify how to safely revert if migration fails

## Example: E-commerce Application

```
Component: User Authentication Service
- Current: Custom JWT implementation in Java 11
- Target: Spring Security 6 with OAuth2 in Java 21
- Strategy: Replatform
- Rationale: Leverage Spring Security's mature OAuth2 support, reduce custom code
- Dependencies: Must migrate after database layer, before API gateway
- Risk: Medium (authentication is critical but well-tested patterns exist)
- Rollback: Keep old service running, route traffic back via feature flag

Component: Legacy Reporting Module
- Current: Perl scripts generating PDF reports
- Target: N/A
- Strategy: Retire
- Rationale: Usage analytics show <1% utilization, functionality replaced by BI tool
- Dependencies: None (isolated module)
- Risk: Low (minimal usage, alternative exists)
- Rollback: Archive scripts, document alternative in BI tool
```

## Migration Sequencing Guidelines

- **Foundation First**: Migrate infrastructure, databases, core services before dependent components
- **Parallel Opportunities**: Identify independent components that can migrate simultaneously
- **Risk Distribution**: Don't migrate all high-risk components in same phase
- **Validation Points**: Define checkpoints between component migrations
- **Incremental Value**: Prioritize components that deliver business value early

## Validation Checklist

- [ ] All components assigned a 7Rs strategy
- [ ] Rationale documented for each strategy decision
- [ ] Dependencies identified and mapped
- [ ] Migration sequence is logical and respects dependencies
- [ ] Rollback procedure defined per component
- [ ] Risk levels assessed and distributed across phases
- [ ] Parallel migration opportunities identified

## Guardrails

**Strategy over tactics**: Define what to do, not how to implement (that's for Execution Mode)

**Dependency accuracy**: Verify dependencies through code analysis, not assumptions

**Realistic sequencing**: Ensure migration order is achievable within timeline constraints
</content>
<line_count>93</line_count>
</write_to_file>

---

**User:**

[write_to_file for '.bob/skills/transformation-mapping/SKILL.md'] Result:
<file_write_result>
<path>.bob/skills/transformation-mapping/SKILL.md</path>
<operation>created</operation>
<notice>
<i>You do not need to re-read the file, as you have seen all changes</i>
<i>Proceed with the task using these changes as the new baseline.</i>
</notice>
</file_write_result>

<environment_details>
# VSCode Visible Files
.bob/skills/transformation-mapping/SKILL.md

# VSCode Open Tabs
README.md,bob_session/bob_task_may-16-2026_4-36-56-pm.md,../../../../../../Untitled-1,.bob/custom_modes.yaml,.bob/skills/target-state-definition/SKILL.md,.bob/skills/transformation-mapping/SKILL.md

# Current Time
Current time in ISO 8601 UTC format: 2026-05-17T11:16:36.223Z
User time zone: Asia/Saigon, UTC+7:00

# Current Cost
$1.28

# Review Exclusions
The following file patterns are excluded from code reviews:
- .vscode/**

# Current Mode
<slug>code</slug>
<name>💻 Code</name>
====

REMINDERS

Below is your current list of reminders for this task. Keep them updated as you progress.

| # | Content | Status |
|---|---------|--------|
| 1 | Create skill: target-state-definition | Completed |
| 2 | Create skill: transformation-mapping | In Progress |
| 3 | Create skill: interface-contract-specification | Pending |
| 4 | Create skill: dependency-tool-substitution | Pending |
| 5 | Create skill: testing-strategy-design | Pending |
| 6 | Create skill: pilot-execution-recommendation | Pending |
| 7 | Update migration-design-mode to reference skills and enforce design_ prefix | Pending |


IMPORTANT: When task status changes, remember to call the `update_todo_list` tool to update your progress.

</environment_details>

---

**Assistant:**

<update_todo_list>
<todos>
[x] Create skill: target-state-definition
[x] Create skill: transformation-mapping
[-] Create skill: interface-contract-specification
[ ] Create skill: dependency-tool-substitution
[ ] Create skill: testing-strategy-design
[ ] Create skill: pilot-execution-recommendation
[ ] Update migration-design-mode to reference skills and enforce design_ prefix
</todos>
</update_todo_list>

---

**User:**

[update_todo_list] Result:
Todo list updated successfully.
<environment_details>
# VSCode Visible Files
.bob/skills/transformation-mapping/SKILL.md

# VSCode Open Tabs
README.md,bob_session/bob_task_may-16-2026_4-36-56-pm.md,../../../../../../Untitled-1,.bob/custom_modes.yaml,.bob/skills/target-state-definition/SKILL.md,.bob/skills/transformation-mapping/SKILL.md

# Current Time
Current time in ISO 8601 UTC format: 2026-05-17T11:16:41.353Z
User time zone: Asia/Saigon, UTC+7:00

# Current Cost
$1.41

# Review Exclusions
The following file patterns are excluded from code reviews:
- .vscode/**

# Current Mode
<slug>code</slug>
<name>💻 Code</name>
====

REMINDERS

Below is your current list of reminders for this task. Keep them updated as you progress.

| # | Content | Status |
|---|---------|--------|
| 1 | Create skill: target-state-definition | Completed |
| 2 | Create skill: transformation-mapping | Completed |
| 3 | Create skill: interface-contract-specification | In Progress |
| 4 | Create skill: dependency-tool-substitution | Pending |
| 5 | Create skill: testing-strategy-design | Pending |
| 6 | Create skill: pilot-execution-recommendation | Pending |
| 7 | Update migration-design-mode to reference skills and enforce design_ prefix | Pending |


IMPORTANT: When task status changes, remember to call the `update_todo_list` tool to update your progress.

</environment_details>

---

**Assistant:**

<write_to_file>
<path>.bob/skills/interface-contract-specification/SKILL.md</path>
<content>---
name: interface-contract-specification
description: Design API contracts, data schemas, and integration patterns for migrated components. Specifies interfaces, breaking changes, versioning strategies, and compatibility layers.
---

# Interface & Contract Specification

You are defining the contracts and interfaces for migrated components. Your goal is to ensure clear, stable boundaries between components and external systems.

## Objective

Specify all APIs, data schemas, and integration patterns that define how components communicate, including handling of breaking changes and backward compatibility.

## Required Inputs

- `design_target_architecture.md`: Target architecture and component boundaries
- `design_component_strategies.md`: Component migration strategies
- Current API documentation or code analysis
- Integration requirements from stakeholders

## Output Document

Create `design_interface_contracts.md` containing:

**API Specifications**: OpenAPI/Swagger definitions for all REST APIs, or equivalent for other protocols

**Data Schemas**: Database schemas, message formats, event structures with field definitions and constraints

**Breaking Changes**: List of incompatible changes with migration paths for consumers

**Versioning Strategy**: API versioning approach (URL, header, content negotiation) with deprecation policy

**Compatibility Layers**: Adapters or facades needed to maintain backward compatibility during transition

**Integration Patterns**: Communication protocols (REST, gRPC, messaging), authentication flows, error handling

## Design Process

1. **Inventory Interfaces**: Identify all APIs, database schemas, message formats, events
2. **Analyze Changes**: Compare current vs target to identify breaking changes
3. **Design Contracts**: Specify target interface contracts with clear semantics
4. **Plan Compatibility**: Design adapters or versioning to support gradual migration
5. **Document Patterns**: Define standard integration patterns for consistency
6. **Validate Completeness**: Ensure all integration points are addressed

## API Design Principles

- **Consistency**: Use consistent naming, error formats, and patterns across APIs
- **Versioning**: Plan for evolution with clear versioning and deprecation strategy
- **Documentation**: Provide complete, accurate specifications (OpenAPI preferred)
- **Error Handling**: Define standard error responses with actionable messages
- **Security**: Specify authentication, authorization, and data protection requirements

## Example: REST API Migration

```
API: User Management Service

Current (v1):
- Endpoint: GET /users/{id}
- Auth: Basic authentication
- Response: XML format

Target (v2):
- Endpoint: GET /api/v2/users/{id}
- Auth: OAuth2 Bearer token
- Response: JSON with HAL links
- Breaking Changes:
  * XML removed (JSON only)
  * Basic auth deprecated (OAuth2 required)
  * Field name changes: userName → username, emailAddr → email

Migration Path:
1. Deploy v2 alongside v1
2. Maintain v1 with deprecation warnings (6 months)
3. Provide migration guide for consumers
4. Monitor v1 usage, communicate with remaining clients
5. Retire v1 after grace period

Compatibility Layer:
- API Gateway translates v1 requests to v2 format
- Response transformer converts v2 JSON to v1 XML
- Authentication proxy upgrades Basic to OAuth2 using service account
```

## Data Schema Design

```
Database: User Table Migration

Current Schema:
- user_name VARCHAR(50)
- email_addr VARCHAR(100)
- created_dt TIMESTAMP

Target Schema:
- username VARCHAR(100) NOT NULL
- email VARCHAR(255) NOT NULL UNIQUE
- created_at TIMESTAMPTZ NOT NULL DEFAULT NOW()
- updated_at TIMESTAMPTZ

Migration Strategy:
1. Add new columns alongside old (dual-write phase)
2. Backfill data from old to new columns
3. Update application to read from new columns
4. Verify data consistency
5. Drop old columns after validation period
```

## Integration Pattern Examples

**Synchronous REST**: For request-response operations requiring immediate feedback

**Asynchronous Messaging**: For event-driven workflows, decoupling, and resilience

**gRPC**: For high-performance internal service communication

**GraphQL**: For flexible client-driven data fetching (if applicable)

## Validation Checklist

- [ ] All APIs documented with OpenAPI/equivalent specifications
- [ ] Data schemas defined with types, constraints, and relationships
- [ ] Breaking changes identified with migration paths
- [ ] Versioning strategy defined and consistent
- [ ] Compatibility layers designed for gradual migration
- [ ] Authentication and authorization flows specified
- [ ] Error handling patterns standardized
- [ ] Integration patterns appropriate for use cases

## Guardrails

**Specification only**: Define contracts, don't implement them

**Consumer impact**: Always consider impact on API consumers and provide migration paths

**Standards compliance**: Follow target platform's API design standards and conventions
</content>
<line_count>117</line_count>
</write_to_file>

---

**User:**

[write_to_file for '.bob/skills/interface-contract-specification/SKILL.md'] Result:
<file_write_result>
<path>.bob/skills/interface-contract-specification/SKILL.md</path>
<operation>created</operation>
<notice>
<i>You do not need to re-read the file, as you have seen all changes</i>
<i>Proceed with the task using these changes as the new baseline.</i>
</notice>
</file_write_result>

<environment_details>
# VSCode Visible Files
.bob/skills/interface-contract-specification/SKILL.md

# VSCode Open Tabs
README.md,bob_session/bob_task_may-16-2026_4-36-56-pm.md,../../../../../../Untitled-1,.bob/custom_modes.yaml,.bob/skills/target-state-definition/SKILL.md,.bob/skills/transformation-mapping/SKILL.md,.bob/skills/interface-contract-specification/SKILL.md

# Current Time
Current time in ISO 8601 UTC format: 2026-05-17T11:17:07.227Z
User time zone: Asia/Saigon, UTC+7:00

# Current Cost
$1.54

# Review Exclusions
The following file patterns are excluded from code reviews:
- .vscode/**

# Current Mode
<slug>code</slug>
<name>💻 Code</name>
====

REMINDERS

Below is your current list of reminders for this task. Keep them updated as you progress.

| # | Content | Status |
|---|---------|--------|
| 1 | Create skill: target-state-definition | Completed |
| 2 | Create skill: transformation-mapping | Completed |
| 3 | Create skill: interface-contract-specification | In Progress |
| 4 | Create skill: dependency-tool-substitution | Pending |
| 5 | Create skill: testing-strategy-design | Pending |
| 6 | Create skill: pilot-execution-recommendation | Pending |
| 7 | Update migration-design-mode to reference skills and enforce design_ prefix | Pending |


IMPORTANT: When task status changes, remember to call the `update_todo_list` tool to update your progress.

</environment_details>

---

**Assistant:**

<update_todo_list>
<todos>
[x] Create skill: target-state-definition
[x] Create skill: transformation-mapping
[x] Create skill: interface-contract-specification
[-] Create skill: dependency-tool-substitution
[ ] Create skill: testing-strategy-design
[ ] Create skill: pilot-execution-recommendation
[ ] Update migration-design-mode to reference skills and enforce design_ prefix
</todos>
</update_todo_list>

---

**User:**

[update_todo_list] Result:
Todo list updated successfully.
<environment_details>
# VSCode Visible Files
.bob/skills/transformation-mapping/SKILL.md

# VSCode Open Tabs
README.md,bob_session/bob_task_may-16-2026_4-36-56-pm.md,../../../../../../Untitled-1,.bob/custom_modes.yaml,.bob/skills/target-state-definition/SKILL.md,.bob/skills/transformation-mapping/SKILL.md,.bob/skills/interface-contract-specification/SKILL.md

# Current Time
Current time in ISO 8601 UTC format: 2026-05-17T11:17:23.460Z
User time zone: Asia/Saigon, UTC+7:00

# Current Cost
$1.67

# Review Exclusions
The following file patterns are excluded from code reviews:
- .vscode/**

# Current Mode
<slug>code</slug>
<name>💻 Code</name>
====

REMINDERS

Below is your current list of reminders for this task. Keep them updated as you progress.

| # | Content | Status |
|---|---------|--------|
| 1 | Create skill: target-state-definition | Completed |
| 2 | Create skill: transformation-mapping | Completed |
| 3 | Create skill: interface-contract-specification | Completed |
| 4 | Create skill: dependency-tool-substitution | In Progress |
| 5 | Create skill: testing-strategy-design | Pending |
| 6 | Create skill: pilot-execution-recommendation | Pending |
| 7 | Update migration-design-mode to reference skills and enforce design_ prefix | Pending |


IMPORTANT: When task status changes, remember to call the `update_todo_list` tool to update your progress.

</environment_details>

---

**Assistant:**

<write_to_file>
<path>.bob/skills/dependency-tool-substitution/SKILL.md</path>
<content>---
name: dependency-tool-substitution
description: Map current dependencies and tools to target platform equivalents. Identifies version updates, replacements, and compatibility requirements for libraries, frameworks, and tooling.
---

# Dependency & Tool Substitution

You are mapping current dependencies to their target platform equivalents. Your goal is to identify what needs to be updated, replaced, or removed during migration.

## Objective

Create a comprehensive mapping of all dependencies, libraries, frameworks, and tools from current to target state, including version compatibility and substitution strategies.

## Required Inputs

- `design_target_architecture.md`: Target technology stack
- `design_component_strategies.md`: Component migration strategies
- Current dependency manifests (pom.xml, package.json, requirements.txt, etc.)
- Target platform compatibility matrix

## Output Document

Create `design_dependency_mapping.md` containing:

**Dependency Inventory**: Complete list of current dependencies with versions and usage

**Substitution Matrix**: Table with columns: Current Dependency | Version | Target Dependency | Version | Change Type | Compatibility Notes

**Breaking Changes**: Dependencies with incompatible APIs requiring code changes

**Deprecated Dependencies**: Libraries to remove with replacement recommendations

**New Dependencies**: Additional libraries needed for target platform

**Tool Updates**: Build tools, CI/CD, testing frameworks, and development tooling changes

## Change Types

- **Direct Update**: Same library, newer version (e.g., Spring Boot 2.7 → 3.2)
- **Equivalent Replacement**: Different library, same functionality (e.g., Log4j → Logback)
- **Platform Native**: Replace with platform-provided service (e.g., custom auth → AWS Cognito)
- **Removal**: Deprecated functionality no longer needed
- **Addition**: New dependency required by target platform

## Analysis Process

1. **Extract Dependencies**: Parse dependency manifests, analyze imports
2. **Check Compatibility**: Verify each dependency works with target platform
3. **Identify Conflicts**: Find version conflicts or incompatible combinations
4. **Research Alternatives**: For incompatible dependencies, find suitable replacements
5. **Document Changes**: Specify exact versions and migration notes
6. **Validate Completeness**: Ensure all transitive dependencies considered

## Example: Java Spring Boot Migration

```
Dependency Mapping: Java 11 → Java 21, Spring Boot 2.7 → 3.2

Direct Updates:
- spring-boot-starter-web: 2.7.18 → 3.2.0
  * Breaking: javax.* → jakarta.* namespace change
  * Action: Update all imports in code
  
- hibernate-core: 5.6.15 → 6.4.0
  * Breaking: Criteria API changes, deprecated methods removed
  * Action: Refactor query code, update entity mappings

Equivalent Replacements:
- javax.validation:validation-api → jakarta.validation:jakarta.validation-api
  * Reason: Jakarta EE namespace migration
  * Compatibility: API mostly unchanged, import updates only

Removals:
- jaxb-api (no longer needed, included in Java 11 but removed in 17+)
  * Action: Add explicit dependency if XML binding still needed
  * Alternative: Use Jackson for JSON, consider removing XML support

Additions:
- spring-boot-starter-actuator: 3.2.0 (new)
  * Reason: Enhanced monitoring for production
  * Configuration: Expose health and metrics endpoints

Tool Updates:
- Maven: 3.6.3 → 3.9.5
- maven-compiler-plugin: 3.8.1 → 3.11.0 (for Java 21 support)
- JUnit: 4.13.2 → 5.10.1 (Jupiter)
```

## Conflict Resolution Strategies

**Version Convergence**: When multiple dependencies require different versions of the same library, choose the highest compatible version

**Exclusions**: Exclude transitive dependencies that conflict, add explicit dependency with correct version

**BOM Management**: Use Bill of Materials (BOM) to manage related dependency versions consistently

**Compatibility Testing**: Validate that resolved versions work together through integration tests

## Validation Checklist

- [ ] All current dependencies inventoried with versions
- [ ] Target dependencies specified with exact versions
- [ ] Breaking changes identified with required code modifications
- [ ] Version conflicts resolved with clear strategy
- [ ] Transitive dependencies considered
- [ ] Build tool and plugin updates specified
- [ ] Testing framework updates documented
- [ ] CI/CD tool compatibility verified

## Guardrails

**Exact versions**: Always specify exact versions, not ranges (e.g., 3.2.0, not 3.2.x)

**Compatibility verification**: Don't assume compatibility—verify with target platform documentation

**Security considerations**: Check for known vulnerabilities in target versions, prefer latest stable releases
</content>
<line_count>109</line_count>
</write_to_file>

---

**User:**

[write_to_file for '.bob/skills/dependency-tool-substitution/SKILL.md'] Result:
<file_write_result>
<path>.bob/skills/dependency-tool-substitution/SKILL.md</path>
<operation>created</operation>
<notice>
<i>You do not need to re-read the file, as you have seen all changes</i>
<i>Proceed with the task using these changes as the new baseline.</i>
</notice>
</file_write_result>

<environment_details>
# VSCode Visible Files
.bob/skills/dependency-tool-substitution/SKILL.md

# VSCode Open Tabs
README.md,bob_session/bob_task_may-16-2026_4-36-56-pm.md,../../../../../../Untitled-1,.bob/custom_modes.yaml,.bob/skills/target-state-definition/SKILL.md,.bob/skills/transformation-mapping/SKILL.md,.bob/skills/dependency-tool-substitution/SKILL.md,.bob/skills/interface-contract-specification/SKILL.md

# Current Time
Current time in ISO 8601 UTC format: 2026-05-17T11:18:07.330Z
User time zone: Asia/Saigon, UTC+7:00

# Current Cost
$1.81

# Review Exclusions
The following file patterns are excluded from code reviews:
- .vscode/**

# Current Mode
<slug>code</slug>
<name>💻 Code</name>
====

REMINDERS

Below is your current list of reminders for this task. Keep them updated as you progress.

| # | Content | Status |
|---|---------|--------|
| 1 | Create skill: target-state-definition | Completed |
| 2 | Create skill: transformation-mapping | Completed |
| 3 | Create skill: interface-contract-specification | Completed |
| 4 | Create skill: dependency-tool-substitution | In Progress |
| 5 | Create skill: testing-strategy-design | Pending |
| 6 | Create skill: pilot-execution-recommendation | Pending |
| 7 | Update migration-design-mode to reference skills and enforce design_ prefix | Pending |


IMPORTANT: When task status changes, remember to call the `update_todo_list` tool to update your progress.

</environment_details>

---

**Assistant:**

<update_todo_list>
<todos>
[x] Create skill: target-state-definition
[x] Create skill: transformation-mapping
[x] Create skill: interface-contract-specification
[x] Create skill: dependency-tool-substitution
[-] Create skill: testing-strategy-design
[ ] Create skill: pilot-execution-recommendation
[ ] Update migration-design-mode to reference skills and enforce design_ prefix
</todos>
</update_todo_list>

---

**User:**

[update_todo_list] Result:
Todo list updated successfully.
<environment_details>
# VSCode Visible Files
.bob/skills/dependency-tool-substitution/SKILL.md

# VSCode Open Tabs
README.md,bob_session/bob_task_may-16-2026_4-36-56-pm.md,../../../../../../Untitled-1,.bob/custom_modes.yaml,.bob/skills/target-state-definition/SKILL.md,.bob/skills/transformation-mapping/SKILL.md,.bob/skills/dependency-tool-substitution/SKILL.md,.bob/skills/interface-contract-specification/SKILL.md

# Current Time
Current time in ISO 8601 UTC format: 2026-05-17T11:18:12.659Z
User time zone: Asia/Saigon, UTC+7:00

# Current Cost
$1.94

# Review Exclusions
The following file patterns are excluded from code reviews:
- .vscode/**

# Current Mode
<slug>code</slug>
<name>💻 Code</name>
====

REMINDERS

Below is your current list of reminders for this task. Keep them updated as you progress.

| # | Content | Status |
|---|---------|--------|
| 1 | Create skill: target-state-definition | Completed |
| 2 | Create skill: transformation-mapping | Completed |
| 3 | Create skill: interface-contract-specification | Completed |
| 4 | Create skill: dependency-tool-substitution | Completed |
| 5 | Create skill: testing-strategy-design | In Progress |
| 6 | Create skill: pilot-execution-recommendation | Pending |
| 7 | Update migration-design-mode to reference skills and enforce design_ prefix | Pending |


IMPORTANT: When task status changes, remember to call the `update_todo_list` tool to update your progress.

</environment_details>

---

**Assistant:**

<write_to_file>
<path>.bob/skills/testing-strategy-design/SKILL.md</path>
<content>---
name: testing-strategy-design
description: Design comprehensive testing strategy for migration validation. Defines test levels, coverage requirements, test data approach, and performance/security testing plans.
---

# Testing Strategy Design

You are designing the testing strategy to validate migration correctness and quality. Your goal is to ensure comprehensive test coverage that catches issues before production.

## Objective

Define a multi-level testing approach that validates functional correctness, performance, security, and data integrity throughout the migration process.

## Required Inputs

- `design_target_architecture.md`: Target architecture and components
- `design_component_strategies.md`: Migration strategies per component
- `design_interface_contracts.md`: API contracts and integration points
- Current test coverage and existing test suites

## Output Document

Create `design_testing_strategy.md` containing:

**Test Levels**: Unit, integration, system, acceptance testing scope and responsibilities

**Coverage Requirements**: Target coverage percentages and critical path identification

**Test Data Strategy**: Data generation, anonymization, and environment management approach

**Performance Testing**: Load, stress, and scalability testing plans with success criteria

**Security Testing**: Vulnerability scanning, penetration testing, and compliance validation

**Regression Testing**: Strategy for ensuring existing functionality remains intact

**Test Automation**: Tools, frameworks, and CI/CD integration approach

## Test Pyramid

**Unit Tests (70%)**: Test individual functions and classes in isolation
- Fast execution, high coverage
- Mock external dependencies
- Focus on business logic and edge cases

**Integration Tests (20%)**: Test component interactions and API contracts
- Verify interfaces work correctly together
- Test database interactions, message flows
- Use test containers or embedded services

**End-to-End Tests (10%)**: Test complete user workflows
- Validate critical business scenarios
- Test across all system layers
- Run in production-like environment

## Testing Process

1. **Inventory Existing Tests**: Analyze current test suites, identify gaps
2. **Define Coverage Targets**: Set realistic coverage goals (≥80% recommended)
3. **Design Test Scenarios**: Create test cases for each migration component
4. **Plan Test Data**: Define data requirements and generation strategy
5. **Specify Performance Tests**: Design load tests matching production patterns
6. **Plan Security Tests**: Identify security testing requirements
7. **Design Automation**: Select tools and integrate with CI/CD pipeline

## Example: Spring Boot Migration Testing

```
Testing Strategy: Java 11 → 21, Spring Boot 2.7 → 3.2

Unit Testing:
- Framework: JUnit 5 (Jupiter)
- Coverage Target: 85% line coverage, 75% branch coverage
- Focus Areas:
  * Business logic in service layer
  * Data transformation and validation
  * Error handling and edge cases
- Tools: JaCoCo for coverage, Mockito for mocking

Integration Testing:
- Framework: Spring Boot Test with @SpringBootTest
- Scope:
  * REST API endpoints (all controllers)
  * Database operations (repository layer)
  * Message queue interactions
  * External service integrations (with WireMock)
- Test Containers: PostgreSQL, Redis, RabbitMQ
- Coverage Target: All API endpoints, critical integration paths

Performance Testing:
- Tool: JMeter or Gatling
- Scenarios:
  * Normal load: 100 req/sec for 30 minutes
  * Peak load: 500 req/sec for 10 minutes
  * Stress test: Gradual increase to failure point
- Success Criteria:
  * P95 latency < 200ms under normal load
  * No memory leaks over 30-minute test
  * Graceful degradation under stress

Security Testing:
- SAST: SonarQube for code analysis
- DAST: OWASP ZAP for runtime scanning
- Dependency Check: Snyk or OWASP Dependency-Check
- Focus Areas:
  * SQL injection, XSS vulnerabilities
  * Authentication and authorization
  * Sensitive data exposure
  * Known CVEs in dependencies
```

## Test Data Strategy

**Synthetic Data**: Generate realistic test data programmatically
- Pros: Consistent, no privacy concerns, easy to scale
- Cons: May miss real-world edge cases

**Anonymized Production Data**: Sanitize and use production data
- Pros: Real-world scenarios, edge cases included
- Cons: Privacy concerns, requires anonymization process

**Hybrid Approach**: Combine synthetic and anonymized data
- Use synthetic for most tests, anonymized for specific scenarios

## Regression Testing

**Baseline Establishment**: Run full test suite on current system, capture results as baseline

**Continuous Comparison**: After each migration step, compare results to baseline

**Automated Regression Suite**: Maintain suite of tests that must pass for each release

**Visual Regression**: For UI changes, use screenshot comparison tools

## Validation Checklist

- [ ] Test levels defined with clear scope and responsibilities
- [ ] Coverage targets specified (≥80% recommended)
- [ ] Test data strategy addresses privacy and realism
- [ ] Performance testing includes load, stress, and scalability tests
- [ ] Security testing covers SAST, DAST, and dependency scanning
- [ ] Regression testing strategy prevents functionality loss
- [ ] Test automation integrated with CI/CD pipeline
- [ ] Success criteria defined for each test level

## Guardrails

**Realistic targets**: Set achievable coverage goals based on project constraints

**Automation first**: Prioritize automated tests over manual testing for repeatability

**Test early**: Design tests before implementation to guide development (TDD approach)

**Production parity**: Test environments should closely match production configuration
</content>
<line_count>135</line_count>
</write_to_file>

---

**User:**

[write_to_file for '.bob/skills/testing-strategy-design/SKILL.md'] Result:
<file_write_result>
<path>.bob/skills/testing-strategy-design/SKILL.md</path>
<operation>created</operation>
<notice>
<i>You do not need to re-read the file, as you have seen all changes</i>
<i>Proceed with the task using these changes as the new baseline.</i>
</notice>
</file_write_result>

<environment_details>
# VSCode Visible Files
.bob/skills/testing-strategy-design/SKILL.md

# VSCode Open Tabs
README.md,bob_session/bob_task_may-16-2026_4-36-56-pm.md,../../../../../../Untitled-1,.bob/custom_modes.yaml,.bob/skills/target-state-definition/SKILL.md,.bob/skills/transformation-mapping/SKILL.md,.bob/skills/dependency-tool-substitution/SKILL.md,.bob/skills/testing-strategy-design/SKILL.md,.bob/skills/SKILL.MD,.bob/skills/interface-contract-specification/SKILL.md

# Current Time
Current time in ISO 8601 UTC format: 2026-05-17T11:18:41.565Z
User time zone: Asia/Saigon, UTC+7:00

# Current Cost
$2.09

# Review Exclusions
The following file patterns are excluded from code reviews:
- .vscode/**

# Current Mode
<slug>code</slug>
<name>💻 Code</name>
====

REMINDERS

Below is your current list of reminders for this task. Keep them updated as you progress.

| # | Content | Status |
|---|---------|--------|
| 1 | Create skill: target-state-definition | Completed |
| 2 | Create skill: transformation-mapping | Completed |
| 3 | Create skill: interface-contract-specification | Completed |
| 4 | Create skill: dependency-tool-substitution | Completed |
| 5 | Create skill: testing-strategy-design | In Progress |
| 6 | Create skill: pilot-execution-recommendation | Pending |
| 7 | Update migration-design-mode to reference skills and enforce design_ prefix | Pending |


IMPORTANT: When task status changes, remember to call the `update_todo_list` tool to update your progress.

</environment_details>

---

**Assistant:**

<update_todo_list>
<todos>
[x] Create skill: target-state-definition
[x] Create skill: transformation-mapping
[x] Create skill: interface-contract-specification
[x] Create skill: dependency-tool-substitution
[x] Create skill: testing-strategy-design
[-] Create skill: pilot-execution-recommendation
[ ] Update migration-design-mode to reference skills and enforce design_ prefix
</todos>
</update_todo_list>

---

**User:**

[update_todo_list] Result:
Todo list updated successfully.
<environment_details>
# VSCode Visible Files
.bob/skills/SKILL.MD

# VSCode Open Tabs
README.md,bob_session/bob_task_may-16-2026_4-36-56-pm.md,../../../../../../Untitled-1,.bob/custom_modes.yaml,.bob/skills/target-state-definition/SKILL.md,.bob/skills/transformation-mapping/SKILL.md,.bob/skills/dependency-tool-substitution/SKILL.md,.bob/skills/testing-strategy-design/SKILL.md,.bob/skills/SKILL.MD,.bob/skills/interface-contract-specification/SKILL.md

# Current Time
Current time in ISO 8601 UTC format: 2026-05-17T11:18:57.984Z
User time zone: Asia/Saigon, UTC+7:00

# Current Cost
$2.23

# Review Exclusions
The following file patterns are excluded from code reviews:
- .vscode/**

# Current Mode
<slug>code</slug>
<name>💻 Code</name>
====

REMINDERS

Below is your current list of reminders for this task. Keep them updated as you progress.

| # | Content | Status |
|---|---------|--------|
| 1 | Create skill: target-state-definition | Completed |
| 2 | Create skill: transformation-mapping | Completed |
| 3 | Create skill: interface-contract-specification | Completed |
| 4 | Create skill: dependency-tool-substitution | Completed |
| 5 | Create skill: testing-strategy-design | Completed |
| 6 | Create skill: pilot-execution-recommendation | In Progress |
| 7 | Update migration-design-mode to reference skills and enforce design_ prefix | Pending |


IMPORTANT: When task status changes, remember to call the `update_todo_list` tool to update your progress.

</environment_details>

---

**Assistant:**

<write_to_file>
<path>.bob/skills/pilot-execution-recommendation/SKILL.md</path>
<content>---
name: pilot-execution-recommendation
description: Recommend Proof of Concept (PoC) scope to validate high-risk design decisions before full migration. Defines objectives, scope, success criteria, and resource requirements.
---

# Pilot Execution Recommendation

You are recommending a Proof of Concept (PoC) to validate critical design decisions before committing to full migration. Your goal is to identify what needs validation and define a focused pilot that reduces risk.

## Objective

Identify high-risk or uncertain aspects of the migration design and recommend a targeted PoC that validates feasibility, performance, and approach before full-scale execution.

## Required Inputs

- All design documents: target architecture, component strategies, interface contracts, dependencies, testing strategy
- Risk assessment from Assessment phase
- Technical uncertainties or unknowns
- Stakeholder concerns or questions

## Output Document

Create `design_poc_recommendations.md` containing:

**Validation Objectives**: What specific questions or risks the PoC will address

**Scope Definition**: Components, features, and scenarios to include (and explicitly exclude)

**Success Criteria**: Measurable outcomes that determine PoC success or failure

**Timeline & Resources**: Estimated duration, team members needed, infrastructure requirements

**Implementation Approach**: High-level steps to execute the PoC

**Decision Framework**: How PoC results will inform go/no-go decisions

## When to Recommend a PoC

Recommend a PoC when:
- **New Technology**: Team lacks experience with target platform or framework
- **Performance Uncertainty**: Unclear if target architecture meets performance requirements
- **Complex Integration**: Novel integration patterns or external system dependencies
- **Data Migration Risk**: Large-scale data transformation with potential data loss
- **Breaking Changes**: Significant API changes affecting many consumers
- **Cost Uncertainty**: Cloud migration with unclear cost implications

Skip PoC when:
- Well-understood technology with team expertise
- Low-risk, straightforward migration (e.g., minor version update)
- Time constraints don't allow for pilot phase

## PoC Design Process

1. **Identify Risks**: Review all design documents, highlight uncertainties
2. **Prioritize Validation**: Focus on highest-risk or highest-impact items
3. **Define Scope**: Select minimal components that validate key decisions
4. **Set Criteria**: Define specific, measurable success metrics
5. **Estimate Effort**: Calculate realistic timeline and resource needs
6. **Plan Execution**: Outline implementation steps and validation approach

## Example: Framework Migration PoC

```
PoC Recommendation: Quarkus → Spring Boot Migration

Validation Objectives:
1. Verify Spring Boot performance meets requirements (target: <200ms P95 latency)
2. Validate dependency injection migration approach (Quarkus CDI → Spring)
3. Confirm REST endpoint migration pattern works for all annotation types
4. Test data access layer migration (Panache → Spring Data JPA)
5. Validate build time and startup time acceptable for CI/CD pipeline

Scope:
Include:
- 2 representative REST controllers (simple CRUD + complex business logic)
- 1 service layer with dependency injection
- 1 repository with Panache queries → Spring Data JPA
- Integration tests for all endpoints
- Performance test simulating production load

Exclude:
- Authentication/authorization (use mock)
- Message queue integration (defer to later phase)
- All other 15 controllers (validate pattern, not exhaustive migration)
- UI/frontend changes

Success Criteria:
✅ All REST endpoints functional with equivalent behavior
✅ P95 latency ≤ 200ms under 100 req/sec load
✅ Unit test coverage ≥ 80%
✅ Integration tests pass with test containers
✅ Build time < 5 minutes
✅ Startup time < 30 seconds
✅ No critical security vulnerabilities in dependencies

Failure Criteria (triggers design revision):
❌ Performance >300ms P95 latency (unacceptable degradation)
❌ Data access layer requires major refactoring (pattern not viable)
❌ Build time >10 minutes (CI/CD impact too high)

Timeline: 2 weeks
- Week 1: Implement migration for scoped components
- Week 2: Testing, performance validation, documentation

Resources:
- 2 developers (full-time)
- 1 QA engineer (part-time for test design)
- AWS test environment (t3.medium instances)
- Budget: ~$500 for infrastructure

Decision Framework:
- All success criteria met → Proceed with full migration
- 1-2 criteria missed → Revise design, re-run PoC
- 3+ criteria missed or failure criteria hit → Reconsider migration approach
```

## PoC Execution Approach

1. **Setup Environment**: Provision infrastructure, configure tools
2. **Implement Scope**: Migrate selected components following design
3. **Run Tests**: Execute functional, integration, performance tests
4. **Measure Results**: Collect metrics against success criteria
5. **Document Findings**: Record what worked, what didn't, lessons learned
6. **Make Recommendation**: Go/no-go decision with supporting evidence

## Validation Checklist

- [ ] High-risk areas identified and prioritized
- [ ] PoC scope is minimal yet representative
- [ ] Success criteria are specific and measurable
- [ ] Failure criteria define when to stop and reconsider
- [ ] Timeline is realistic (typically 1-4 weeks)
- [ ] Resource requirements specified
- [ ] Decision framework clear for interpreting results
- [ ] PoC results will meaningfully reduce risk

## Guardrails

**Focused scope**: PoC should validate specific risks, not attempt full migration

**Time-boxed**: Set clear deadline to prevent scope creep

**Measurable outcomes**: Define objective criteria, not subjective assessments

**Disposable code**: PoC code may be throwaway—focus on learning, not production quality
</content>
<line_count>131</line_count>
</write_to_file>

---

**User:**

[write_to_file for '.bob/skills/pilot-execution-recommendation/SKILL.md'] Result:
<file_write_result>
<path>.bob/skills/pilot-execution-recommendation/SKILL.md</path>
<operation>created</operation>
<notice>
<i>You do not need to re-read the file, as you have seen all changes</i>
<i>Proceed with the task using these changes as the new baseline.</i>
</notice>
</file_write_result>

<environment_details>
# VSCode Visible Files
.bob/skills/pilot-execution-recommendation/SKILL.md

# VSCode Open Tabs
README.md,bob_session/bob_task_may-16-2026_4-36-56-pm.md,../../../../../../Untitled-1,.bob/custom_modes.yaml,.bob/skills/target-state-definition/SKILL.md,.bob/skills/transformation-mapping/SKILL.md,.bob/skills/dependency-tool-substitution/SKILL.md,.bob/skills/testing-strategy-design/SKILL.md,.bob/skills/SKILL.MD,.bob/skills/pilot-execution-recommendation/SKILL.md,.bob/skills/interface-contract-specification/SKILL.md

# Current Time
Current time in ISO 8601 UTC format: 2026-05-17T11:19:32.968Z
User time zone: Asia/Saigon, UTC+7:00

# Current Cost
$2.38

# Review Exclusions
The following file patterns are excluded from code reviews:
- .vscode/**

# Current Mode
<slug>code</slug>
<name>💻 Code</name>
====

REMINDERS

Below is your current list of reminders for this task. Keep them updated as you progress.

| # | Content | Status |
|---|---------|--------|
| 1 | Create skill: target-state-definition | Completed |
| 2 | Create skill: transformation-mapping | Completed |
| 3 | Create skill: interface-contract-specification | Completed |
| 4 | Create skill: dependency-tool-substitution | Completed |
| 5 | Create skill: testing-strategy-design | Completed |
| 6 | Create skill: pilot-execution-recommendation | In Progress |
| 7 | Update migration-design-mode to reference skills and enforce design_ prefix | Pending |


IMPORTANT: When task status changes, remember to call the `update_todo_list` tool to update your progress.

</environment_details>

---

**Assistant:**

<update_todo_list>
<todos>
[x] Create skill: target-state-definition
[x] Create skill: transformation-mapping
[x] Create skill: interface-contract-specification
[x] Create skill: dependency-tool-substitution
[x] Create skill: testing-strategy-design
[x] Create skill: pilot-execution-recommendation
[-] Update migration-design-mode to reference skills and enforce design_ prefix
</todos>
</update_todo_list>

---

**User:**

[update_todo_list] Result:
Todo list updated successfully.
<environment_details>
# VSCode Visible Files
.bob/skills/target-state-definition/SKILL.md

# VSCode Open Tabs
README.md,bob_session/bob_task_may-16-2026_4-36-56-pm.md,../../../../../../Untitled-1,.bob/custom_modes.yaml,.bob/skills/target-state-definition/SKILL.md,.bob/skills/transformation-mapping/SKILL.md,.bob/skills/dependency-tool-substitution/SKILL.md,.bob/skills/testing-strategy-design/SKILL.md,.bob/skills/SKILL.MD,.bob/skills/pilot-execution-recommendation/SKILL.md,.bob/skills/interface-contract-specification/SKILL.md

# Current Time
Current time in ISO 8601 UTC format: 2026-05-17T11:19:40.806Z
User time zone: Asia/Saigon, UTC+7:00

# Current Cost
$2.53

# Review Exclusions
The following file patterns are excluded from code reviews:
- .vscode/**

# Current Mode
<slug>code</slug>
<name>💻 Code</name>
====

REMINDERS

Below is your current list of reminders for this task. Keep them updated as you progress.

| # | Content | Status |
|---|---------|--------|
| 1 | Create skill: target-state-definition | Completed |
| 2 | Create skill: transformation-mapping | Completed |
| 3 | Create skill: interface-contract-specification | Completed |
| 4 | Create skill: dependency-tool-substitution | Completed |
| 5 | Create skill: testing-strategy-design | Completed |
| 6 | Create skill: pilot-execution-recommendation | Completed |
| 7 | Update migration-design-mode to reference skills and enforce design_ prefix | In Progress |


IMPORTANT: When task status changes, remember to call the `update_todo_list` tool to update your progress.

</environment_details>

---

**Assistant:**

Now let me update the migration-design-mode to reference these skills and enforce the design_ prefix:

<read_file>
<args>
<file>
<path>.bob/custom_modes.yaml</path>
<line_range>18-130</line_range>
</file>
</args>
</read_file>

---

**User:**

[read_file for '.bob/custom_modes.yaml'. Reading multiple files at once is more efficient for the LLM. If other files are relevant to your current task, please read them simultaneously.] Result:
<files>
<file><path>.bob/custom_modes.yaml</path>
<content lines="18-130">
 18 |   - slug: migration-design-mode
 19 |     name: 🏗️ Migration Design
 20 |     description: Transform migration plans into technical designs and specifications without executing code changes.
 21 |     
 22 |     roleDefinition: |
 23 |       You are a migration architect who creates comprehensive technical designs from assessment findings and migration plans. You produce architecture documents, component strategies, and implementation blueprints—but never execute code changes.
 24 |     
 25 |     whenToUse: |
 26 |       Use when assessment and planning are complete and user needs technical design specifications. Keywords: "design migration", "architecture design", "technical specs", "migration blueprint".
 27 |       
 28 |       Do NOT use for: initial assessment, code execution, or project planning.
 29 |     
 30 |     customInstructions: |
 31 |       # Migration Design Mode
 32 |       
 33 |       ## Objective
 34 |       
 35 |       Transform assessment findings and migration plans into actionable technical designs through 6 core tasks:
 36 |       
 37 |       1. **Target State Definition** — Design target architecture aligned with platform best practices
 38 |       2. **Transformation Mapping** — Define migration strategy per component using 7Rs framework (Rehost, Replatform, Refactor, Repurchase, Retire, Retain, Relocate)
 39 |       3. **Interface & Contract Specification** — Specify APIs, data schemas, and integration patterns
 40 |       4. **Dependency & Tool Substitution** — Map current dependencies to target equivalents
 41 |       5. **Testing Strategy Design** — Define test levels, coverage requirements (≥80%), and validation approach
 42 |       6. **Pilot Execution** — Recommend PoC scope to validate high-risk design decisions
 43 |       
 44 |       ## Required Inputs
 45 |       
 46 |       - Assessment Report: current architecture, dependencies, risks, recommended strategies
 47 |       - Migration Plan: phases, timeline, resource allocation, success criteria
 48 |       
 49 |       If missing, ask user to provide or run Assessment/Planning modes first.
 50 |       
 51 |       ## Design Artifacts to Produce
 52 |       
 53 |       ### Architecture Design Document
 54 |       Communicate: target architecture, component mapping, migration strategies per component, API/data contracts, integration patterns, testing approach, PoC recommendations, and design decisions with rationale.
 55 |       
 56 |       ### Component Technical Specifications
 57 |       For each component: current vs target stack, configuration changes, dependency updates, test requirements, and high-level migration steps.
 58 |       
 59 |       ### Design Review Checklist
 60 |       Validate: architecture alignment, integration completeness, data migration feasibility, testing coverage, and risk mitigation.
 61 |       
 62 |       ## Design Process
 63 |       
 64 |       1. **Gather Context**: Review inputs, explore codebase with `list_files`, `list_code_definition_names`, `read_file`
 65 |       2. **Design Target Architecture**: Create diagrams, map components, identify patterns
 66 |       3. **Define Component Strategies**: Apply 7Rs per component, document dependencies and rollback
 67 |       4. **Specify Interfaces**: Design API contracts, breaking changes, versioning
 68 |       5. **Plan Data Migration**: Map schemas, define transformations, specify validation
 69 |       6. **Design Integration**: Define communication patterns, error handling, security flows
 70 |       7. **Create Testing Strategy**: Specify test levels, coverage, performance/security testing
 71 |       8. **Recommend PoC**: Identify validation needs, define scope and success criteria
 72 |       9. **Generate Documentation**: Use MCP tools to create Confluence pages and diagrams
 73 |       10. **Validate Design**: Check completeness, quality, and readiness for execution
 74 |       
 75 |       ## Critical Guardrails
 76 |       
 77 |       **NEVER:**
 78 |       - Modify source code, configs, or dependencies
 79 |       - Execute migration transformations or deployments
 80 |       - Refactor code or update imports
 81 |       
 82 |       **ALWAYS:**
 83 |       - Create design artifacts only (documents, diagrams, specifications)
 84 |       - Perform read-only code analysis
 85 |       - Document findings without making changes
 86 |       
 87 |       ## Phase Gate Criteria
 88 |       
 89 |       Before transitioning to Execution Mode, validate:
 90 |       - All design artifacts created and complete
 91 |       - Architecture aligns with platform best practices
 92 |       - All integration points and dependencies addressed
 93 |       - Testing strategy comprehensive with ≥80% coverage target
 94 |       - Rollback procedures feasible
 95 |       - Stakeholder approvals obtained (if required)
 96 |       
 97 |       If criteria not met, work with user to address gaps.
 98 |       
 99 |       ## Example: Java 11 → 21 Migration
100 |       
101 |       ```
102 |       User: "Design the migration from Java 11 to Java 21 for our Spring Boot app"
103 |       
104 |       Your approach:
105 |       1. Review assessment (deprecated APIs, dependency conflicts)
106 |       2. Design target: Java 21 + Spring Boot 3.x architecture
107 |       3. Strategy: Replatform all components (optimize for new platform)
108 |       4. Specify: API changes for breaking updates, new Java features to adopt
109 |       5. Data: Schema changes if JPA/Hibernate version impacts models
110 |       6. Integration: Update to leverage virtual threads, pattern matching
111 |       7. Testing: Focus on deprecated API replacements, performance benchmarks
112 |       8. PoC: Validate critical deprecated API migrations in isolated module
113 |       9. Document: Architecture design, component specs, test strategy
114 |       10. Validate: Check all criteria, create handoff package
115 |       ```
116 |       
117 |       ## Transition to Execution
118 |       
119 |       When design complete and approved, use `switch_mode` to recommend Execution Mode with handoff package:
120 |       - Architecture Design URL
121 |       - Component Specifications URL
122 |       - Design Review Checklist (completed)
123 |       - PoC Recommendations (if applicable)
124 |       - Summary of key design decisions
125 |     
126 |     source: project
127 |     groups:
128 |       - read
129 |       - edit
130 |       - mcp
</content>
</file>
</files>
<environment_details>
# VSCode Visible Files
.bob/skills/target-state-definition/SKILL.md

# VSCode Open Tabs
README.md,bob_session/bob_task_may-16-2026_4-36-56-pm.md,../../../../../../Untitled-1,.bob/custom_modes.yaml,.bob/skills/target-state-definition/SKILL.md,.bob/skills/transformation-mapping/SKILL.md,.bob/skills/dependency-tool-substitution/SKILL.md,.bob/skills/testing-strategy-design/SKILL.md,.bob/skills/SKILL.MD,.bob/skills/pilot-execution-recommendation/SKILL.md,.bob/skills/interface-contract-specification/SKILL.md

# Current Time
Current time in ISO 8601 UTC format: 2026-05-17T11:19:44.648Z
User time zone: Asia/Saigon, UTC+7:00

# Current Cost
$2.68

# Review Exclusions
The following file patterns are excluded from code reviews:
- .vscode/**

# Current Mode
<slug>code</slug>
<name>💻 Code</name>
====

REMINDERS

Below is your current list of reminders for this task. Keep them updated as you progress.

| # | Content | Status |
|---|---------|--------|
| 1 | Create skill: target-state-definition | Completed |
| 2 | Create skill: transformation-mapping | Completed |
| 3 | Create skill: interface-contract-specification | Completed |
| 4 | Create skill: dependency-tool-substitution | Completed |
| 5 | Create skill: testing-strategy-design | Completed |
| 6 | Create skill: pilot-execution-recommendation | Completed |
| 7 | Update migration-design-mode to reference skills and enforce design_ prefix | In Progress |


IMPORTANT: When task status changes, remember to call the `update_todo_list` tool to update your progress.

</environment_details>

---

**Assistant:**

<apply_diff>
<path>.bob/custom_modes.yaml</path>
<diff>
<<<<<<< SEARCH
    customInstructions: |
      # Migration Design Mode
      
      ## Objective
      
      Transform assessment findings and migration plans into actionable technical designs through 6 core tasks:
      
      1. **Target State Definition** — Design target architecture aligned with platform best practices
      2. **Transformation Mapping** — Define migration strategy per component using 7Rs framework (Rehost, Replatform, Refactor, Repurchase, Retire, Retain, Relocate)
      3. **Interface & Contract Specification** — Specify APIs, data schemas, and integration patterns
      4. **Dependency & Tool Substitution** — Map current dependencies to target equivalents
      5. **Testing Strategy Design** — Define test levels, coverage requirements (≥80%), and validation approach
      6. **Pilot Execution** — Recommend PoC scope to validate high-risk design decisions
      
      ## Required Inputs
      
      - Assessment Report: current architecture, dependencies, risks, recommended strategies
      - Migration Plan: phases, timeline, resource allocation, success criteria
      
      If missing, ask user to provide or run Assessment/Planning modes first.
      
      ## Design Artifacts to Produce
      
      ### Architecture Design Document
      Communicate: target architecture, component mapping, migration strategies per component, API/data contracts, integration patterns, testing approach, PoC recommendations, and design decisions with rationale.
      
      ### Component Technical Specifications
      For each component: current vs target stack, configuration changes, dependency updates, test requirements, and high-level migration steps.
      
      ### Design Review Checklist
      Validate: architecture alignment, integration completeness, data migration feasibility, testing coverage, and risk mitigation.
      
      ## Design Process
      
      1. **Gather Context**: Review inputs, explore codebase with `list_files`, `list_code_definition_names`, `read_file`
      2. **Design Target Architecture**: Create diagrams, map components, identify patterns
      3. **Define Component Strategies**: Apply 7Rs per component, document dependencies and rollback
      4. **Specify Interfaces**: Design API contracts, breaking changes, versioning
      5. **Plan Data Migration**: Map schemas, define transformations, specify validation
      6. **Design Integration**: Define communication patterns, error handling, security flows
      7. **Create Testing Strategy**: Specify test levels, coverage, performance/security testing
      8. **Recommend PoC**: Identify validation needs, define scope and success criteria
      9. **Generate Documentation**: Use MCP tools to create Confluence pages and diagrams
      10. **Validate Design**: Check completeness, quality, and readiness for execution
      
      ## Critical Guardrails
      
      **NEVER:**
      - Modify source code, configs, or dependencies
      - Execute migration transformations or deployments
      - Refactor code or update imports
      
      **ALWAYS:**
      - Create design artifacts only (documents, diagrams, specifications)
      - Perform read-only code analysis
      - Document findings without making changes
      
      ## Phase Gate Criteria
      
      Before transitioning to Execution Mode, validate:
      - All design artifacts created and complete
      - Architecture aligns with platform best practices
      - All integration points and dependencies addressed
      - Testing strategy comprehensive with ≥80% coverage target
      - Rollback procedures feasible
      - Stakeholder approvals obtained (if required)
      
      If criteria not met, work with user to address gaps.
      
      ## Example: Java 11 → 21 Migration
      
      ```
      User: "Design the migration from Java 11 to Java 21 for our Spring Boot app"
      
      Your approach:
      1. Review assessment (deprecated APIs, dependency conflicts)
      2. Design target: Java 21 + Spring Boot 3.x architecture
      3. Strategy: Replatform all components (optimize for new platform)
      4. Specify: API changes for breaking updates, new Java features to adopt
      5. Data: Schema changes if JPA/Hibernate version impacts models
      6. Integration: Update to leverage virtual threads, pattern matching
      7. Testing: Focus on deprecated API replacements, performance benchmarks
      8. PoC: Validate critical deprecated API migrations in isolated module
      9. Document: Architecture design, component specs, test strategy
      10. Validate: Check all criteria, create handoff package
      ```
      
      ## Transition to Execution
      
      When design complete and approved, use `switch_mode` to recommend Execution Mode with handoff package:
      - Architecture Design URL
      - Component Specifications URL
      - Design Review Checklist (completed)
      - PoC Recommendations (if applicable)
      - Summary of key design decisions
=======
    customInstructions: |
      # Migration Design Mode
      
      ## Objective
      
      Transform assessment findings and migration plans into actionable technical designs through 6 specialized skills. Each skill produces a design document with the `design_` prefix.
      
      ## Design Skills
      
      Use these skills to complete each design task:
      
      1. **target-state-definition** — Design target architecture (produces `design_target_architecture.md`)
      2. **transformation-mapping** — Define migration strategy per component using 7Rs framework (produces `design_component_strategies.md`)
      3. **interface-contract-specification** — Specify APIs, data schemas, and integration patterns (produces `design_interface_contracts.md`)
      4. **dependency-tool-substitution** — Map current dependencies to target equivalents (produces `design_dependency_mapping.md`)
      5. **testing-strategy-design** — Define test levels, coverage requirements (≥80%), and validation approach (produces `design_testing_strategy.md`)
      6. **pilot-execution-recommendation** — Recommend PoC scope to validate high-risk design decisions (produces `design_poc_recommendations.md`)
      
      ## Required Inputs
      
      - Assessment Report: current architecture, dependencies, risks, recommended strategies
      - Migration Plan: phases, timeline, resource allocation, success criteria
      
      If missing, ask user to provide or run Assessment/Planning modes first.
      
      ## Design Workflow
      
      1. **Gather Context**: Review inputs, explore codebase with `list_files`, `list_code_definition_names`, `read_file`
      2. **Execute Skills**: Use each skill in sequence or as needed based on user request
      3. **Generate Documentation**: Create design documents with `design_` prefix using MCP tools if available
      4. **Validate Completeness**: Ensure all required design artifacts are created
      5. **Prepare Handoff**: Package all design documents for Execution Mode
      
      ## Document Naming Convention
      
      **CRITICAL**: All design phase documents MUST use the `design_` prefix:
      - `design_target_architecture.md`
      - `design_component_strategies.md`
      - `design_interface_contracts.md`
      - `design_dependency_mapping.md`
      - `design_testing_strategy.md`
      - `design_poc_recommendations.md`
      
      This naming convention ensures clear phase separation and prevents confusion with execution artifacts.
      
      ## Critical Guardrails
      
      **NEVER:**
      - Modify source code, configs, or dependencies
      - Execute migration transformations or deployments
      - Refactor code or update imports
      
      **ALWAYS:**
      - Create design artifacts only (documents, diagrams, specifications)
      - Use `design_` prefix for all output documents
      - Perform read-only code analysis
      - Document findings without making changes
      
      ## Phase Gate Criteria
      
      Before transitioning to Execution Mode, validate:
      - All 6 design documents created with `design_` prefix
      - Architecture aligns with platform best practices
      - All integration points and dependencies addressed
      - Testing strategy comprehensive with ≥80% coverage target
      - Rollback procedures feasible
      - Stakeholder approvals obtained (if required)
      
      If criteria not met, work with user to address gaps.
      
      ## Example Usage
      
      ```
      User: "Design the migration from Java 11 to Java 21 for our Spring Boot app"
      
      Your approach:
      1. Use `target-state-definition` skill to create design_target_architecture.md
      2. Use `transformation-mapping` skill to create design_component_strategies.md
      3. Use `interface-contract-specification` skill to create design_interface_contracts.md
      4. Use `dependency-tool-substitution` skill to create design_dependency_mapping.md
      5. Use `testing-strategy-design` skill to create design_testing_strategy.md
      6. Use `pilot-execution-recommendation` skill to create design_poc_recommendations.md
      7. Validate all documents created with design_ prefix
      8. Create handoff package for Execution Mode
      ```
      
      ## Transition to Execution
      
      When design complete and approved, use `switch_mode` to recommend Execution Mode with handoff package listing all `design_*` documents created.
>>>>>>> REPLACE
</diff>
</apply_diff>

---

**User:**

[apply_diff for '.bob/custom_modes.yaml'] Result:
<file_write_result>
<path>.bob/custom_modes.yaml</path>
<operation>modified</operation>
<notice>
<i>You do not need to re-read the file, as you have seen all changes</i>
<i>Proceed with the task using these changes as the new baseline.</i>
</notice>
</file_write_result>

<notice>Making multiple related changes in a single apply_diff is more efficient. If other changes are needed in this file, please include them as additional SEARCH/REPLACE blocks.</notice>
<environment_details>
# VSCode Visible Files
.bob/custom_modes.yaml

# VSCode Open Tabs
README.md,bob_session/bob_task_may-16-2026_4-36-56-pm.md,../../../../../../Untitled-1,.bob/skills/target-state-definition/SKILL.md,.bob/custom_modes.yaml,.bob/skills/transformation-mapping/SKILL.md,.bob/skills/dependency-tool-substitution/SKILL.md,.bob/skills/testing-strategy-design/SKILL.md,.bob/skills/SKILL.MD,.bob/skills/pilot-execution-recommendation/SKILL.md,.bob/skills/interface-contract-specification/SKILL.md

# Current Time
Current time in ISO 8601 UTC format: 2026-05-17T11:20:42.128Z
User time zone: Asia/Saigon, UTC+7:00

# Current Cost
$2.84

# Review Exclusions
The following file patterns are excluded from code reviews:
- .vscode/**

# Current Mode
<slug>code</slug>
<name>💻 Code</name>
====

REMINDERS

Below is your current list of reminders for this task. Keep them updated as you progress.

| # | Content | Status |
|---|---------|--------|
| 1 | Create skill: target-state-definition | Completed |
| 2 | Create skill: transformation-mapping | Completed |
| 3 | Create skill: interface-contract-specification | Completed |
| 4 | Create skill: dependency-tool-substitution | Completed |
| 5 | Create skill: testing-strategy-design | Completed |
| 6 | Create skill: pilot-execution-recommendation | Completed |
| 7 | Update migration-design-mode to reference skills and enforce design_ prefix | In Progress |


IMPORTANT: When task status changes, remember to call the `update_todo_list` tool to update your progress.

</environment_details>

---

**Assistant:**

<update_todo_list>
<todos>
[x] Create skill: target-state-definition
[x] Create skill: transformation-mapping
[x] Create skill: interface-contract-specification
[x] Create skill: dependency-tool-substitution
[x] Create skill: testing-strategy-design
[x] Create skill: pilot-execution-recommendation
[x] Update migration-design-mode to reference skills and enforce design_ prefix
</todos>
</update_todo_list>

---

**User:**

[update_todo_list] Result:
Todo list updated successfully.
<environment_details>
# VSCode Visible Files
.bob/mcp.json

# VSCode Open Tabs
README.md,bob_session/bob_task_may-16-2026_4-36-56-pm.md,../../../../../../Untitled-1,.bob/skills/target-state-definition/SKILL.md,.bob/custom_modes.yaml,.bob/mcp.json,.bob/skills/transformation-mapping/SKILL.md,.bob/skills/dependency-tool-substitution/SKILL.md,.bob/skills/testing-strategy-design/SKILL.md,.bob/skills/SKILL.MD,.bob/skills/pilot-execution-recommendation/SKILL.md,.bob/skills/interface-contract-specification/SKILL.md

# Current Time
Current time in ISO 8601 UTC format: 2026-05-17T11:20:49.062Z
User time zone: Asia/Saigon, UTC+7:00

# Current Cost
$3.01

# Review Exclusions
The following file patterns are excluded from code reviews:
- .vscode/**

# Current Mode
<slug>code</slug>
<name>💻 Code</name>
====

REMINDERS

Below is your current list of reminders for this task. Keep them updated as you progress.

| # | Content | Status |
|---|---------|--------|
| 1 | Create skill: target-state-definition | Completed |
| 2 | Create skill: transformation-mapping | Completed |
| 3 | Create skill: interface-contract-specification | Completed |
| 4 | Create skill: dependency-tool-substitution | Completed |
| 5 | Create skill: testing-strategy-design | Completed |
| 6 | Create skill: pilot-execution-recommendation | Completed |
| 7 | Update migration-design-mode to reference skills and enforce design_ prefix | Completed |


IMPORTANT: When task status changes, remember to call the `update_todo_list` tool to update your progress.

</environment_details>

---

**Assistant:**

<attempt_completion>
<result>
Successfully created 6 specialized skills for Migration Design Mode and updated the mode to reference them with enforced `design_` prefix naming convention.

## Created Skills

1. **target-state-definition** → `design_target_architecture.md`
   - Designs target architecture, component mapping, technology stack
   - Includes architecture diagrams and design decisions log

2. **transformation-mapping** → `design_component_strategies.md`
   - Applies 7Rs framework per component
   - Defines migration sequence, dependencies, and rollback procedures

3. **interface-contract-specification** → `design_interface_contracts.md`
   - Specifies API contracts (OpenAPI/Swagger)
   - Documents breaking changes, versioning, and compatibility layers

4. **dependency-tool-substitution** → `design_dependency_mapping.md`
   - Maps current dependencies to target equivalents
   - Identifies version conflicts and substitution strategies

5. **testing-strategy-design** → `design_testing_strategy.md`
   - Defines test pyramid (unit/integration/e2e)
   - Specifies coverage targets (≥80%), performance, and security testing

6. **pilot-execution-recommendation** → `design_poc_recommendations.md`
   - Recommends PoC scope for high-risk validations
   - Defines success criteria, timeline, and resources

## Updated Migration Design Mode

- **Skill-based workflow**: Mode now references each skill explicitly
- **Enforced naming**: All design documents MUST use `design_` prefix
- **Clear separation**: Prevents confusion between design and execution artifacts
- **Streamlined instructions**: Reduced from ~95 lines to ~75 lines while adding skill references

## Key Features

Each skill follows context engineering best practices:
- Right altitude: Specific guidance without brittle hardcoding
- Minimal token count with high signal
- 1-2 canonical examples per skill
- Clear validation checklists
- Consistent structure across all skills

The mode now provides a clear, modular approach where users can invoke individual skills as needed or follow the complete workflow for comprehensive design documentation.
</result>
</attempt_completion>

