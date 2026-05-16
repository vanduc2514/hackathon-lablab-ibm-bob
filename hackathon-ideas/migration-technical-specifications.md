# Bob's Migration Framework - Technical Specifications

**Version:** 1.0  
**Date:** 2026-05-16  
**Status:** Implementation Ready

---

## Table of Contents

1. [Custom Migration Modes](#1-custom-migration-modes)
2. [MCP Server Specifications](#2-mcp-server-specifications)
3. [Migration-Specific Skills](#3-migration-specific-skills)
4. [Workflow Instructions](#4-workflow-instructions)
5. [Tool Integration Architecture](#5-tool-integration-architecture)
6. [Data Models](#6-data-models)
7. [Implementation Guidelines](#7-implementation-guidelines)

---

## 1. Custom Migration Modes

### 1.1 Assessment Mode

**Mode Slug:** `migration-assessment`  
**Mode Name:** 🔍 Migration Assessment  
**Purpose:** Comprehensive analysis and discovery of migration requirements, risks, and feasibility

#### When to Use
- Starting a new migration project
- Need to analyze codebase complexity and dependencies
- Evaluating migration feasibility and effort
- Creating initial assessment reports
- Identifying risks and technical debt

#### Mode-Specific Instructions

```markdown
You are Bob in Migration Assessment mode, an expert technical analyst specializing in migration discovery and risk assessment.

Your primary objectives:
1. Perform comprehensive codebase analysis to understand structure, dependencies, and complexity
2. Identify migration risks, technical debt, and breaking changes
3. Recommend appropriate 7Rs migration strategies per component
4. Generate detailed assessment reports with actionable insights
5. Create migration readiness scores and effort estimations

Analysis Approach:
- Start with project structure analysis using list_files and list_code_definition_names
- Use search_files to identify deprecated APIs, security vulnerabilities, and technical debt
- Leverage Dependency Scanner MCP to map dependency trees and identify conflicts
- Use Code Analysis MCP for static analysis and complexity metrics
- Integrate with JIRA MCP to create assessment epics and track findings
- Generate Confluence documentation using Documentation Generator MCP

Assessment Workflow:
1. Gather project context (language, framework, architecture)
2. Scan codebase structure and inventory files
3. Analyze dependencies (direct and transitive)
4. Identify deprecated APIs and breaking changes
5. Assess test coverage and quality
6. Evaluate security vulnerabilities
7. Calculate migration complexity score
8. Recommend 7Rs strategy per component
9. Generate assessment report with risk matrix
10. Create JIRA epic with findings

Validation Requirements:
- All source code files must be scanned
- Build system compatibility verified
- Critical dependencies identified and documented
- Breaking changes catalogued with remediation paths
- Test infrastructure assessed for adequacy
- Rollback feasibility confirmed

Output Format:
- Assessment Report (Confluence page with executive summary, detailed analysis, risk matrix)
- JIRA Epic with linked issues for each finding
- Dependency Graph (visual representation)
- Migration Readiness Score (0-100 scale)
- Effort Estimation (story points and hours)
```

#### Tools and Capabilities

**Available Tools:**
- `read_file` - Analyze source code and configuration files
- `list_files` - Inventory project structure
- `list_code_definition_names` - Extract code definitions
- `search_files` - Find patterns, deprecated APIs, vulnerabilities
- `use_mcp_tool` - Access MCP servers:
  - Dependency Scanner MCP
  - Security Scanner MCP
  - JIRA Integration MCP
  - Documentation Generator MCP
- `ask_followup_question` - Gather additional context
- `attempt_completion` - Present assessment results

**Restricted Tools:**
- `write_to_file` - Only for creating assessment reports (`.md` files)
- `apply_diff` - Not available (read-only analysis)
- `insert_content` - Not available
- `execute_command` - Limited to read-only commands

#### File Restrictions

**Allowed File Patterns:**
- `\.md$` - Markdown documentation
- `assessment-.*\.md$` - Assessment reports
- `migration-.*\.md$` - Migration documentation

**Prohibited Operations:**
- Cannot modify source code files
- Cannot modify build configurations
- Cannot modify test files
- Read-only access to all project files

#### Integration with Other Modes

**Transitions:**
- **To Planning Mode:** After assessment completion and approval
- **To Ask Mode:** For clarification on findings
- **Trigger:** User approves assessment report

**Data Handoff:**
- Assessment report (Confluence URL)
- JIRA epic ID
- Dependency graph data
- Risk matrix
- Recommended 7Rs strategies
- Effort estimation

#### Example Prompts

```
"Assess this Java 11 application for migration to Java 21"
"Analyze dependencies and identify breaking changes for Spring Boot 3 upgrade"
"Evaluate migration complexity for moving this monolith to microservices"
"Create an assessment report for migrating from Oracle to PostgreSQL"
```

---

### 1.2 Migration Execution Mode

**Mode Slug:** `migration-execution`  
**Mode Name:** 🚀 Migration Execution  
**Purpose:** Hands-on code migration, refactoring, and transformation

#### When to Use
- Executing approved migration plans
- Performing code transformations and refactoring
- Updating dependencies and configurations
- Implementing migration patterns
- Creating migration scripts and automation

#### Mode-Specific Instructions

```markdown
You are Bob in Migration Execution mode, an expert software engineer specializing in code migration and transformation.

Your primary objectives:
1. Execute migration tasks according to approved plans
2. Transform code to target platform/framework/version
3. Update dependencies, configurations, and build files
4. Implement migration patterns and best practices
5. Ensure backward compatibility where required
6. Create comprehensive tests for migrated code

Execution Approach:
- Follow the approved migration plan and task sequence
- Use apply_diff for surgical code changes
- Use write_to_file for new files or complete rewrites
- Leverage Test Generator MCP to create test suites
- Use Dependency Scanner MCP to validate dependency updates
- Integrate with JIRA MCP to update task status
- Document changes using Documentation Generator MCP

Migration Workflow:
1. Review migration task from JIRA
2. Read and understand current implementation
3. Plan transformation approach
4. Execute code changes incrementally
5. Update dependencies and configurations
6. Generate or update tests
7. Validate changes compile and pass tests
8. Document migration decisions
9. Update JIRA task status
10. Proceed to next task or request validation

Safety Protocols:
- Always create backups before major changes
- Use Rollback Manager MCP to create checkpoints
- Validate each change before proceeding
- Run tests after each significant modification
- Document all breaking changes
- Maintain rollback procedures
```

#### Tools and Capabilities

**Available Tools:**
- All file editing tools: `read_file`, `write_to_file`, `apply_diff`, `insert_content`
- `list_files`, `list_code_definition_names`, `search_files`
- `execute_command` - Full access for builds, tests, deployments
- `use_mcp_tool` - Access to all MCP servers
- `obtain_git_diff` - Review changes before committing
- `attempt_completion` - Complete migration tasks

#### File Restrictions

**Allowed File Patterns:**
- `.*\.java$`, `.*\.kt$`, `.*\.ts$`, `.*\.js$`, `.*\.py$` - Source files
- `.*\.xml$`, `.*\.gradle$`, `.*\.properties$` - Configuration files
- `.*\.yml$`, `.*\.json$`, `.*\.md$` - Config and documentation

**Prohibited Operations:**
- Cannot modify production databases directly
- Cannot deploy to production without approval

#### Example Prompts

```
"Migrate this Java 11 class to use Java 21 features"
"Update Spring Boot dependencies from 2.7 to 3.2"
"Refactor this Quarkus REST endpoint to Spring Boot"
```

---

### 1.3 Optimization Mode

**Mode Slug:** `migration-optimization`  
**Mode Name:** ⚡ Migration Optimization  
**Purpose:** Performance tuning, cost optimization, and efficiency improvements post-migration

#### When to Use
- After successful migration validation
- Performance issues identified
- Cost optimization opportunities detected
- Need to leverage new platform features

#### Mode-Specific Instructions

```markdown
You are Bob in Migration Optimization mode, an expert performance engineer specializing in post-migration optimization.

Your primary objectives:
1. Analyze performance metrics and identify bottlenecks
2. Optimize code for target platform capabilities
3. Reduce resource consumption and costs
4. Leverage new platform features for efficiency
5. Implement caching, connection pooling, and other optimizations
6. Validate improvements with benchmarks

Optimization Workflow:
1. Establish performance baseline metrics
2. Profile application under realistic load
3. Identify top bottlenecks and inefficiencies
4. Prioritize optimizations by impact
5. Implement optimization changes
6. Measure performance improvements
7. Validate cost reductions
8. Document optimizations
9. Update monitoring and alerts
10. Generate optimization report
```

#### Tools and Capabilities

**Available Tools:**
- All file editing tools for code optimization
- `execute_command` - Run performance tests, profiling tools
- `use_mcp_tool` - Performance Profiler MCP, Cost Estimator MCP
- `attempt_completion` - Present optimization results

#### Example Prompts

```
"Optimize this migrated service for better throughput"
"Reduce cloud costs for this migrated application"
"Leverage Java 21 virtual threads to improve concurrency"
```

---

### 1.4 Hypercare Mode

**Mode Slug:** `migration-hypercare`  
**Mode Name:** 🏥 Migration Hypercare  
**Purpose:** Post-migration monitoring, issue resolution, and stabilization

#### When to Use
- Immediately after production migration
- First 30-90 days post-migration
- Monitoring for migration-related issues
- Quick issue resolution and hotfixes

#### Mode-Specific Instructions

```markdown
You are Bob in Migration Hypercare mode, an expert support engineer specializing in post-migration stabilization.

Your primary objectives:
1. Monitor migrated systems for issues and anomalies
2. Quickly diagnose and resolve migration-related problems
3. Analyze incident patterns and root causes
4. Implement hotfixes and emergency patches
5. Gather lessons learned
6. Ensure smooth transition to steady-state operations

Hypercare Workflow:
1. Monitor system health and metrics
2. Detect anomalies or incidents
3. Triage and prioritize issues
4. Diagnose root cause
5. Implement fix or workaround
6. Validate resolution
7. Document incident and resolution
8. Update runbooks
9. Analyze patterns
10. Generate hypercare report
```

#### Tools and Capabilities

**Available Tools:**
- All file editing tools for hotfixes

## 2. MCP Server Specifications

**Note:** Detailed MCP server specifications are documented in [`mcp-server-specifications.md`](mcp-server-specifications.md:1)

### Summary of MCP Servers

The migration framework requires 10 specialized MCP servers, prioritized as follows:

#### High Priority (⭐⭐⭐) - Implement First
1. **Dependency Scanner MCP** - Analyze dependencies, identify conflicts, assess impact
2. **Test Generator MCP** - Generate comprehensive test suites for migrated code
3. **Data Quality Analyzer MCP** - Profile and validate data quality for database migrations

#### Medium Priority (⭐⭐) - Implement Second
4. **Performance Profiler MCP** - Profile application performance and identify optimizations
5. **Security Scanner MCP** - Scan for vulnerabilities and compliance issues
6. **Rollback Manager MCP** - Manage checkpoints and rollback procedures
7. **Documentation Generator MCP** - Generate migration documentation and Confluence pages
8. **JIRA Integration MCP** - Manage JIRA tickets for migration tracking

#### Lower Priority (⭐) - Implement Later
9. **Cost Estimator MCP** - Estimate and optimize cloud migration costs
10. **Change Management MCP** - Manage stakeholder communication and training

For complete specifications including operations, input/output schemas, and data models, see [`mcp-server-specifications.md`](mcp-server-specifications.md:1).

---

## 3. Migration-Specific Skills

**Note:** Detailed skill specifications are documented in [`migration-skills-and-workflows.md`](migration-skills-and-workflows.md:1)

### Summary of Migration Skills

#### 3.1 Java Migration Skill
- **Purpose:** Handle Java version upgrades (11 → 17 → 21)
- **Complexity:** Medium to High
- **Key Capabilities:**
  - Dependency analysis and updates
  - Deprecated API identification and replacement
  - New feature adoption (records, sealed classes, virtual threads)
  - Test generation and validation

#### 3.2 Framework Migration Skill
- **Purpose:** Handle framework transitions (Quarkus → Spring Boot, etc.)
- **Complexity:** High
- **Key Capabilities:**
  - Framework compatibility analysis
  - Annotation and configuration conversion
  - Architecture pattern migration
  - Integration testing

#### 3.3 Cloud Migration Skill
- **Purpose:** Handle legacy to cloud migrations
- **Complexity:** High
- **Key Capabilities:**
  - Cloud readiness assessment
  - Infrastructure provisioning
  - Containerization
  - Security and compliance configuration

#### 3.4 Database Migration Skill
- **Purpose:** Handle database platform changes
- **Complexity:** Very High
- **Key Capabilities:**
  - Schema conversion
  - Data migration and validation
  - Query optimization
  - Stored procedure conversion

For complete skill specifications including workflows, integration points, and success criteria, see [`migration-skills-and-workflows.md`](migration-skills-and-workflows.md:1).

---

## 4. Workflow Instructions

**Note:** Detailed workflow instructions are documented in [`migration-skills-and-workflows.md`](migration-skills-and-workflows.md:1)

### Summary of Workflows

#### 4.1 Phase Transition Workflows
Seven phase gates with validation criteria:
1. Assessment → Planning
2. Planning → Design
3. Design → Migration
4. Migration → Validation
5. Validation → Optimization
6. Optimization → Hypercare
7. Hypercare → Complete

#### 4.2 Dependency Management Workflows
- Dependency discovery and tracking
- Update prioritization and strategy
- Conflict resolution procedures

#### 4.3 Validation Workflows
- Code validation (build, quality, tests)
- Security validation (scans, compliance)
- Performance validation (profiling, load testing)
- Data validation (integrity, quality)

#### 4.4 Rollback Workflows
- Rollback triggers (automatic and manual)
- Rollback procedures
- Post-rollback validation

#### 4.5 Documentation Workflows
- Phase-specific documentation requirements
- Documentation generation process
- Confluence integration

For complete workflow specifications, see [`migration-skills-and-workflows.md`](migration-skills-and-workflows.md:1).

---

## 5. Tool Integration Architecture

**Note:** Detailed integration architecture is documented in [`migration-skills-and-workflows.md`](migration-skills-and-workflows.md:1)

### Summary of Integrations

#### 5.1 Version Control (Git)
- Branch strategy for migration work
- Commit message standards
- Code review requirements
- Release tagging

#### 5.2 CI/CD Pipeline
- Automated build and test stages
- Security scanning integration
- Deployment automation
- Bob monitoring and reporting

#### 5.3 Testing Frameworks
- Support for JUnit, Jest, pytest, etc.
- Test generation and execution
- Coverage reporting
- Result aggregation

#### 5.4 Monitoring Tools
- APM integration
- Log aggregation
- Metrics collection
- Alerting and incident response

#### 5.5 Project Management (JIRA, Confluence)
- Epic and story structure
- Status tracking and reporting
- Documentation generation
- Stakeholder communication

For complete integration specifications and architecture diagrams, see [`migration-skills-and-workflows.md`](migration-skills-and-workflows.md:1).

---

## 6. Data Models

### 6.1 Migration Context

```typescript
interface MigrationContext {
  migration_id: string;
  migration_type: "java_version" | "framework" | "cloud" | "database";
  source: {
    version: string;
    platform: string;
    environment: string;
  };
  target: {
    version: string;
    platform: string;
    environment: string;
  };
  strategy: "relocate" | "rehost" | "replatform" | "refactor" | "repurchase" | "retire" | "retain";
  current_phase: "assessment" | "planning" | "design" | "migration" | "validation" | "optimization" | "hypercare";
  status: "not_started" | "in_progress" | "blocked" | "completed" | "failed";
  created_at: string;
  updated_at: string;
}
```

### 6.2 Assessment Result

```typescript
interface AssessmentResult {
  assessment_id: string;
  migration_id: string;
  readiness_score: number; // 0-100
  complexity_score: number; // 0-100
  risk_score: number; // 0-100
  effort_estimate: {
    story_points: number;
    hours: number;
    duration_weeks: number;
  };
  dependencies: {
    total: number;
    direct: number;
    transitive: number;
    conflicts: number;
  };
  breaking_changes: BreakingChange[];
  risks: Risk[];
  recommendations: string[];
  created_at: string;
}
```

### 6.3 Migration Task

```typescript
interface MigrationTask {
  task_id: string;
  migration_id: string;
  jira_key: string;
  title: string;
  description: string;
  phase: string;
  status: "todo" | "in_progress" | "review" | "done" | "blocked";
  priority: "low" | "medium" | "high" | "critical";
  story_points: number;
  assignee: string;
  dependencies: string[]; // task_ids
  artifacts: Artifact[];
  created_at: string;
  updated_at: string;
  completed_at?: string;
}
```

### 6.4 Checkpoint

```typescript
interface Checkpoint {
  checkpoint_id: string;
  migration_id: string;
  name: string;
  description: string;
  phase: string;
  timestamp: string;
  components: {
    type: "code" | "database" | "configuration" | "infrastructure";
    location: string;
    backup_path: string;
    size_bytes: number;
    checksum: string;
  }[];
  verification_status: "pending" | "verified" | "failed";
  rollback_tested: boolean;
}
```

### 6.5 Validation Result

```typescript
interface ValidationResult {
  validation_id: string;
  migration_id: string;
  validation_type: "functional" | "performance" | "security" | "data";
  status: "passed" | "failed" | "warning";
  tests_run: number;
  tests_passed: number;
  tests_failed: number;
  coverage_percentage: number;
  issues: Issue[];
  metrics: Record<string, number>;
  report_url: string;
  created_at: string;
}
```

### 6.6 Incident

```typescript
interface Incident {
  incident_id: string;
  migration_id: string;
  severity: "P1" | "P2" | "P3" | "P4";
  title: string;
  description: string;
  status: "open" | "investigating" | "resolved" | "closed";
  detected_at: string;
  resolved_at?: string;
  root_cause: string;
  resolution: string;
  impact: string;
  lessons_learned: string[];
}
```

---

## 7. Implementation Guidelines

### 7.1 Mode Implementation

#### Creating a Custom Mode

1. **Define Mode Configuration**
   ```json
   {
     "slug": "migration-assessment",
     "name": "🔍 Migration Assessment",
     "description": "Comprehensive analysis and discovery",
     "instructions": "path/to/instructions.md",
     "file_restrictions": ["\.md$", "assessment-.*\.md$"],
     "available_tools": ["read_file", "list_files", "search_files", "use_mcp_tool"],
     "mcp_servers": ["dependency-scanner", "security-scanner", "jira-integration"]
   }
   ```

2. **Write Mode Instructions**
   - Define mode purpose and objectives
   - Specify workflow and approach
   - Document validation requirements
   - Provide example prompts

3. **Configure File Restrictions**
   - Define allowed file patterns (regex)
   - Specify prohibited operations
   - Document rationale for restrictions

4. **Test Mode Behavior**
   - Verify mode activates correctly
   - Test file restriction enforcement
   - Validate tool availability
   - Test mode transitions

#### Mode Transition Implementation

```typescript
interface ModeTransition {
  from_mode: string;
  to_mode: string;
  trigger: "user_request" | "automatic" | "gate_passed";
  validation_required: boolean;
  approval_required: boolean;
  data_handoff: Record<string, any>;
}
```

### 7.2 MCP Server Implementation

#### Server Structure

```typescript
// server.ts
import { Server } from "@modelcontextprotocol/sdk/server/index.js";
import { StdioServerTransport } from "@modelcontextprotocol/sdk/server/stdio.js";

const server = new Server(
  {
    name: "dependency-scanner",
    version: "1.0.0",
  },
  {
    capabilities: {
      tools: {},
    },
  }
);

// Register tools
server.setRequestHandler(ListToolsRequestSchema, async () => ({
  tools: [
    {
      name: "scan_dependencies",
      description: "Scan project dependencies",
      inputSchema: {
        type: "object",
        properties: {
          project_path: { type: "string" },
          language: { type: "string" },
          build_system: { type: "string" }
        },
        required: ["project_path", "language", "build_system"]
      }
    }
  ]
}));

// Implement tool handlers
server.setRequestHandler(CallToolRequestSchema, async (request) => {
  if (request.params.name === "scan_dependencies") {
    // Implementation
    return {
      content: [
        {
          type: "text",
          text: JSON.stringify(result)
        }
      ]
    };
  }
});

// Start server
const transport = new StdioServerTransport();
await server.connect(transport);
```

#### Testing MCP Servers

```bash
# Test server locally
npx @modelcontextprotocol/inspector node dist/index.js

# Test with Bob
# Add to .bob/mcp.json
{
  "mcpServers": {
    "dependency-scanner": {
      "command": "node",
      "args": ["path/to/server/dist/index.js"]
    }
  }
}
```

### 7.3 Skill Implementation

#### Skill Structure

```typescript
interface Skill {
  name: string;
  trigger_conditions: TriggerCondition[];
  required_inputs: Record<string, any>;
  workflow: WorkflowStep[];
  mcp_integrations: string[];
  mode_transitions: ModeTransition[];
  success_criteria: SuccessCriterion[];
}
```

#### Skill Activation

```typescript
function shouldActivateSkill(
  userMessage: string,
  context: MigrationContext
): boolean {
  // Check trigger conditions
  const keywords = ["java upgrade", "java migration", "java 21"];
  const hasKeyword = keywords.some(k => 
    userMessage.toLowerCase().includes(k)
  );
  
  // Check context
  const isJavaProject = context.source.platform === "java";
  
  return hasKeyword && isJavaProject;
}
```

### 7.4 Workflow Implementation

#### Phase Gate Implementation

```typescript
interface PhaseGate {
  from_phase: string;
  to_phase: string;
  prerequisites: Prerequisite[];
  validation_steps: ValidationStep[];
  approval_required: boolean;
  approvers: string[];
}

async function validatePhaseGate(
  gate: PhaseGate,
  context: MigrationContext
): Promise<GateValidationResult> {
  const results: ValidationResult[] = [];
  
  // Check prerequisites
  for (const prereq of gate.prerequisites) {
    const result = await checkPrerequisite(prereq, context);
    results.push(result);
  }
  
  // Execute validation steps
  for (const step of gate.validation_steps) {
    const result = await executeValidation(step, context);
    results.push(result);
  }
  
  const allPassed = results.every(r => r.status === "passed");
  
  return {
    gate_passed: allPassed,
    results,
    approval_required: gate.approval_required,
    approvers: gate.approvers
  };
}
```

### 7.5 Integration Implementation

#### JIRA Integration Example

```typescript
async function createMigrationEpic(
  assessment: AssessmentResult
): Promise<string> {
  const epicData = {
    project_key: "MIG",
    summary: `Migration: ${assessment.migration_type}`,
    description: generateEpicDescription(assessment),
    labels: ["migration", assessment.migration_type],
    custom_fields: {
      readiness_score: assessment.readiness_score,
      effort_estimate: assessment.effort_estimate.story_points
    }
  };
  
  const result = await jiraMcp.createEpic(epicData);
  return result.epic_key;
}
```

#### Confluence Integration Example

```typescript
async function publishAssessmentReport(
  assessment: AssessmentResult
): Promise<string> {
  const content = await documentationMcp.generateDocs({
    doc_type: "assessment",
    artifacts: [assessment],
    template: "assessment-template"
  });
  
  const result = await documentationMcp.updateConfluence({
    space_key: "MIG",
    page_title: `Assessment: ${assessment.migration_id}`,
    content: content.document_content,
    parent_page_id: "123456"
  });
  
  return result.page_url;
}
```

### 7.6 Testing Guidelines

#### Unit Testing

```typescript
describe("Java Migration Skill", () => {
  it("should activate for Java upgrade requests", () => {
    const message = "Migrate from Java 11 to Java 21";
    const context = { source: { platform: "java" } };
    
    expect(shouldActivateSkill(message, context)).toBe(true);
  });
  
  it("should identify deprecated APIs", async () => {
    const result = await skill.identifyDeprecatedAPIs(projectPath);
    
    expect(result).toContainEqual({
      api: "javax.xml.bind",
      replacement: "jakarta.xml.bind",
      usage_count: 5
    });
  });
});
```

#### Integration Testing

```typescript
describe("Migration Workflow Integration", () => {
  it("should complete assessment phase", async () => {
    const context = createTestContext();
    
    // Execute assessment
    const assessment = await executeAssessment(context);
    
    // Verify outputs
    expect(assessment.readiness_score).toBeGreaterThan(60);
    expect(assessment.jira_epic_key).toBeDefined();
    expect(assessment.confluence_url).toBeDefined();
  });
});
```

#### End-to-End Testing

```typescript
describe("Complete Migration Flow", () => {
  it("should migrate Java 11 to 21", async () => {
    // Setup test project
    const project = await setupTestProject("java-11-sample");
    
    // Execute migration
    const result = await executeMigration({
      project_path: project.path,
      source_version: "11",
      target_version: "21"
    });
    
    // Verify success
    expect(result.status).toBe("completed");
    expect(result.tests_passed).toBe(100);
    expect(result.build_successful).toBe(true);
  });
});
```

---

## 8. Next Steps for Implementation

### Phase 1: Foundation (Weeks 1-4)

**Week 1-2: Core Infrastructure**
- [ ] Setup project structure
- [ ] Implement base mode framework
- [ ] Create MCP server templates
- [ ] Setup testing infrastructure

**Week 3-4: High Priority MCPs**
- [ ] Implement Dependency Scanner MCP
- [ ] Implement Test Generator MCP
- [ ] Implement Data Quality Analyzer MCP
- [ ] Create integration tests

### Phase 2: Core Modes (Weeks 5-8)

**Week 5-6: Assessment & Planning Modes**
- [ ] Implement Assessment Mode
- [ ] Implement Planning Mode (enhance existing)
- [ ] Create mode transition logic
- [ ] Test mode workflows

**Week 7-8: Execution & Validation**
- [ ] Implement Migration Execution Mode
- [ ] Enhance validation workflows
- [ ] Create checkpoint system
- [ ] Test rollback procedures

### Phase 3: Skills (Weeks 9-12)

**Week 9-10: Java & Framework Skills**
- [ ] Implement Java Migration Skill
- [ ] Implement Framework Migration Skill
- [ ] Create skill activation logic
- [ ] Test skill workflows

**Week 11-12: Cloud & Database Skills**
- [ ] Implement Cloud Migration Skill
- [ ] Implement Database Migration Skill
- [ ] Integrate with MCPs
- [ ] End-to-end testing

### Phase 4: Integration (Weeks 13-16)

**Week 13-14: Tool Integration**
- [ ] Implement JIRA Integration MCP
- [ ] Implement Documentation Generator MCP
- [ ] Setup CI/CD integration
- [ ] Configure monitoring

**Week 15-16: Optimization & Polish**
- [ ] Implement Optimization Mode
- [ ] Implement Hypercare Mode
- [ ] Performance optimization
- [ ] Documentation completion

---

## Appendix A: Configuration Examples

### Mode Configuration

```json
{
  "modes": [
    {
      "slug": "migration-assessment",
      "name": "🔍 Migration Assessment",
      "description": "Comprehensive migration analysis",
      "instructions_file": "modes/assessment/instructions.md",
      "file_restrictions": {
        "allowed_patterns": ["\.md$", "assessment-.*\.md$"],
        "prohibited_operations": ["modify_source_code"]
      },
      "available_tools": [
        "read_file",
        "list_files",
        "search_files",
        "use_mcp_tool",
        "ask_followup_question"
      ],
      "mcp_servers": [
        "dependency-scanner",
        "security-scanner",
        "jira-integration",
        "documentation-generator"
      ]
    }
  ]
}
```

### MCP Server Configuration

```json
{
  "mcpServers": {
    "dependency-scanner": {
      "command": "node",
      "args": ["./mcp-servers/dependency-scanner/dist/index.js"],
      "env": {
        "LOG_LEVEL": "info"
      }
    },
    "test-generator": {
      "command": "node",
      "args": ["./mcp-servers/test-generator/dist/index.js"]
    },
    "jira-integration": {
      "command": "node",
      "args": ["./mcp-servers/jira-integration/dist/index.js"],
      "env": {
        "JIRA_URL": "${JIRA_URL}",
        "JIRA_TOKEN": "${JIRA_TOKEN}"
      }
    }
  }
}
```

---

## Appendix B: Reference Documentation

### Related Documents
- [`migration-framework-plan.md`](migration-framework-plan.md:1) - Original migration framework plan
- [`mcp-server-specifications.md`](mcp-server-specifications.md:1) - Detailed MCP server specs
- [`migration-skills-and-workflows.md`](migration-skills-and-workflows.md:1) - Skills and workflow specs

### External References
- [Model Context Protocol Documentation](https://modelcontextprotocol.io)
- [7Rs Migration Framework](https://aws.amazon.com/blogs/enterprise-strategy/6-strategies-for-migrating-applications-to-the-cloud/)
- [Spring Boot Migration Guide](https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-3.0-Migration-Guide)
- [Java 21 Migration Guide](https://docs.oracle.com/en/java/javase/21/migrate/)

---

**Document Version:** 1.0  
**Last Updated:** 2026-05-16  
**Status:** Implementation Ready  
**Approval:** Pending Technical Review

- `execute_command` - Full access for diagnostics
- `use_mcp_tool` - Performance Profiler, Rollback Manager, JIRA Integration
- `attempt_completion` - Close hypercare phase

#### Example Prompts

```
"Investigate performance degradation after migration"
"Diagnose integration failure with legacy system"
"Implement hotfix for data inconsistency issue"
```

---

## 2. MCP Server Specifications

This section will be continued in a separate document part due to length.
