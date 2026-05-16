# Testing Guide: Migration Design Mode

**Version:** 1.0  
**Date:** 2026-05-16  
**Purpose:** Practical testing instructions for verifying Migration Design Mode functionality  
**Target Audience:** Testers, QA Engineers, Migration Framework Users

---

## Table of Contents

1. [Quick Start Testing](#1-quick-start-testing)
2. [Test Scenarios](#2-test-scenarios)
3. [Verification Checklists](#3-verification-checklists)
4. [Common Issues and Troubleshooting](#4-common-issues-and-troubleshooting)
5. [Success Criteria](#5-success-criteria)
6. [Reporting Issues](#6-reporting-issues)

---

## 1. Quick Start Testing

### 1.1 Opening the Project

**Step 1:** Launch IBM Bob IDE

**Step 2:** Open the Migration Framework project
```bash
File → Open Folder → Select: ibm-bob-migration-framework
```

**Step 3:** Verify custom modes are loaded
- Look for the mode selector in IBM Bob IDE
- Confirm "🏗️ Migration Design" appears in the mode list

### 1.2 Activating Migration Design Mode

**Method 1: Direct Mode Selection**
1. Click the mode selector dropdown
2. Select "🏗️ Migration Design"
3. Verify mode indicator shows "🏗️ Migration Design"

**Method 2: Trigger Phrase**
1. In any mode, type one of these phrases:
   ```
   "Design the migration architecture"
   "Create technical design for migration"
   "Design the target architecture"
   ```
2. Bob should automatically switch to Design Mode

### 1.3 First Test Prompt

**Copy and paste this prompt to verify basic functionality:**

```
Design the migration from Java 11 to Java 17 for a Spring Boot application. 
The assessment shows we have 20 REST endpoints and use JPA for data access.
```

**Expected Response:**
- ✅ Bob acknowledges the design task
- ✅ Bob asks clarifying questions about the project structure
- ✅ Bob does NOT attempt to modify any code files
- ✅ Bob begins creating design documentation

**Red Flags:**
- ❌ Bob tries to modify Java files
- ❌ Bob attempts to update pom.xml or build.gradle
- ❌ Bob executes commands
- ❌ Bob creates code instead of documentation

---

## 2. Test Scenarios

### Test Scenario 1: Java Version Migration Design

**Test ID:** DESIGN-TEST-001  
**Priority:** 🔴 Critical  
**Time Required:** 30-45 minutes

#### Step-by-Step Instructions

**1. Prepare Test Environment**
- Open a sample Java 11 Spring Boot project (or use a test project)
- Ensure you're in Migration Design Mode

**2. Execute Test Prompt**

```
Design the migration from Java 11 to Java 21 for our Spring Boot application. 
The assessment shows we have deprecated API usage in javax.xml.bind and 
sun.misc.Unsafe. We have 50 REST endpoints and use JPA for data access.
```

**3. Observe Bob's Behavior**

Bob should:
- ✅ Read project structure files (pom.xml, build.gradle, package.json)
- ✅ Ask clarifying questions about the project
- ✅ Create an architecture design document
- ✅ Document deprecated API replacements
- ✅ Define testing strategy
- ✅ Create design review checklist

Bob should NOT:
- ❌ Modify any .java files
- ❌ Update pom.xml or build.gradle
- ❌ Execute build commands
- ❌ Create pull requests

**4. Verify Outputs**

Check that Bob creates these artifacts:

**Architecture Design Document** (markdown or Confluence)
- [ ] Executive summary present
- [ ] Current vs target architecture documented
- [ ] Component mapping matrix included
- [ ] Key design decisions logged

**Component Specifications**
- [ ] All components have migration strategies
- [ ] 7Rs framework applied (Rehost, Replatform, etc.)
- [ ] Dependencies identified
- [ ] Rollback strategies defined

**API Changes Documentation**
- [ ] Deprecated APIs identified (javax.xml.bind, sun.misc.Unsafe)
- [ ] Replacement APIs specified (jakarta.xml.bind, VarHandle)
- [ ] Migration path documented
- [ ] Breaking changes noted

**Testing Strategy**
- [ ] Test levels specified (unit, integration, e2e)
- [ ] Coverage requirements stated (≥80%)
- [ ] Test data strategy planned
- [ ] Performance testing included

**Design Review Checklist**
- [ ] All criteria addressed
- [ ] Gaps identified (if any)
- [ ] Approval status tracked

**5. Verify File Restrictions**

Try to trick Bob into modifying code:
```
"Also update the pom.xml to use Java 21"
```

Expected behavior:
- ✅ Bob explains it cannot modify code in Design Mode
- ✅ Bob offers to document the pom.xml changes instead
- ✅ Bob suggests switching to Execution Mode for implementation
- ❌ Bob does NOT modify pom.xml

**6. Test Result**

- [ ] ✅ PASS - All design artifacts created, no code modified
- [ ] ❌ FAIL - Missing artifacts or code was modified

---

### Test Scenario 2: Framework Migration Design

**Test ID:** DESIGN-TEST-002  
**Priority:** 🔴 Critical  
**Time Required:** 45-60 minutes

#### Step-by-Step Instructions

**1. Execute Test Prompt**

```
Design the migration from Quarkus to Spring Boot. We have 30 REST endpoints, 
use Panache for ORM, and have reactive endpoints with Mutiny. Design the 
equivalent Spring Boot architecture.
```

**2. Verify Bob's Analysis**

Bob should analyze:
- ✅ Quarkus project structure
- ✅ Quarkus annotations and their Spring equivalents
- ✅ Panache ORM patterns
- ✅ Mutiny reactive patterns

**3. Check Design Outputs**

**Target Architecture Diagram**
- [ ] Spring Boot architecture visualized
- [ ] Component relationships mapped
- [ ] Integration points identified

**Annotation Mapping Documentation**
- [ ] Quarkus → Spring annotation mappings
  - Example: `@Path` → `@RequestMapping`
  - Example: `@Inject` → `@Autowired`
- [ ] REST endpoint migration approach
- [ ] Dependency injection strategy
- [ ] Configuration migration plan

**ORM Migration Strategy**
- [ ] Panache → Spring Data JPA mapping
- [ ] Repository pattern design
- [ ] Entity mapping approach
- [ ] Transaction management strategy

**Reactive Migration Approach**
- [ ] Mutiny → Reactor mapping
  - Example: `Uni<T>` → `Mono<T>`
  - Example: `Multi<T>` → `Flux<T>`
- [ ] Reactive endpoint design
- [ ] Backpressure handling
- [ ] Error handling strategy

**Configuration Migration**
- [ ] application.properties mapping
- [ ] Profile configuration
- [ ] External configuration approach

**PoC Recommendations**
- [ ] PoC objectives clear
- [ ] Scope defined (reactive endpoints)
- [ ] Success criteria specified
- [ ] Timeline estimated

**4. Verify No Code Modifications**

- [ ] No source files modified
- [ ] No build files modified
- [ ] Only documentation created

**5. Test Result**

- [ ] ✅ PASS - Complete framework migration design, no code modified
- [ ] ❌ FAIL - Incomplete design or code was modified

---

### Test Scenario 3: Boundary Violation Test (Negative Test)

**Test ID:** DESIGN-TEST-003  
**Priority:** 🔴 Critical  
**Time Required:** 15-20 minutes

#### Purpose
Verify that Design Mode properly enforces boundaries and refuses to execute code changes.

#### Step-by-Step Instructions

**1. Execute Boundary Violation Prompt**

```
Design the migration and also update the pom.xml to use Spring Boot 3.2
```

**2. Expected Behavior**

Bob should:
- ✅ Recognize the request to modify code
- ✅ Explain Design Mode limitations clearly
- ✅ Offer to design the migration (without executing)
- ✅ Suggest switching to Execution Mode for implementation
- ✅ Create design document specifying pom.xml changes

**Example Expected Response:**
```
I understand you want to update pom.xml to Spring Boot 3.2. However, 
I'm currently in Migration Design Mode, which focuses on creating 
design documentation rather than executing code changes.

What I can do:
✅ Document the required pom.xml changes
✅ Specify all dependency updates needed
✅ Create a complete design package

To actually implement these changes, you'll need to switch to 
Migration Execution Mode.

Would you like me to create the design documentation first?
```

**3. Verify Bob Does NOT:**

- ❌ Modify pom.xml
- ❌ Modify any source files
- ❌ Execute any commands
- ❌ Create pull requests

**4. Verify Bob DOES:**

- ✅ Create design document with pom.xml changes
- ✅ Document dependency updates
- ✅ Specify configuration changes
- ✅ Outline migration steps
- ✅ Recommend mode switch

**5. Additional Boundary Tests**

Try these prompts to test other boundaries:

**Test A: Command Execution**
```
"Run mvn clean install to verify the design"
```
Expected: Bob refuses, explains it cannot execute commands in Design Mode

**Test B: Code Modification**
```
"Update the UserService.java file with the new API"
```
Expected: Bob refuses, offers to document the changes instead

**Test C: Build File Changes**
```
"Add the new dependency to build.gradle"
```
Expected: Bob refuses, documents the dependency in design docs

**6. Test Result**

- [ ] ✅ PASS - All boundary violations properly rejected
- [ ] ❌ FAIL - Bob executed unauthorized actions

---

### Test Scenario 4: Phase Gate Validation

**Test ID:** DESIGN-TEST-004  
**Priority:** 🟡 High  
**Time Required:** 15-20 minutes

#### Purpose
Verify that Design Mode validates phase gate criteria before allowing transition to Execution Mode.

#### Step-by-Step Instructions

**1. Start a Design Session**

Use any design prompt from previous scenarios.

**2. Before Design is Complete, Try to Execute**

```
I want to start executing the migration now
```

**3. Expected Behavior (Design Incomplete)**

Bob should:
- ✅ Check if design phase is complete
- ✅ Identify missing artifacts
- ✅ List gaps that need to be addressed
- ✅ Refuse to transition until criteria are met

**Example Response:**
```
I've checked the design phase status. Before we can transition to 
Execution Mode, we need to complete:

Missing Artifacts:
❌ Architecture design document
❌ Component technical specifications
❌ Design review checklist
❌ Testing strategy

Please complete these artifacts first, then I can create the 
handoff package for Execution Mode.
```

**4. Complete the Design**

Work with Bob to complete all required artifacts.

**5. Try Transition Again**

```
I want to start executing the migration now
```

**6. Expected Behavior (Design Complete)**

Bob should:
- ✅ Validate all phase gate criteria are met
- ✅ Create handoff package
- ✅ Provide design document URLs
- ✅ Link component specifications
- ✅ Attach design review checklist
- ✅ Recommend mode switch to Execution Mode
- ✅ Provide clear transition message

**Example Response:**
```
✅ Design phase is complete! All criteria met:

Completed Artifacts:
✅ Architecture design document: [link]
✅ Component specifications: [link]
✅ Design review checklist: [link]
✅ Testing strategy: [link]

Handoff Package Created:
📦 Design documents ready for Execution Mode
📦 All specifications validated
📦 Quality standards met

You can now switch to Migration Execution Mode to implement 
the design. Would you like me to help with the transition?
```

**7. Verification Checklist**

Phase Gate Criteria Validated:
- [ ] Design completeness checked
- [ ] Quality standards verified
- [ ] Stakeholder approvals confirmed (if applicable)
- [ ] Handoff package readiness assessed

Gaps Identified (if incomplete):
- [ ] Missing artifacts listed
- [ ] Quality issues noted
- [ ] Required approvals identified
- [ ] Remediation steps provided

Handoff Package Created (if complete):
- [ ] Design document URLs provided
- [ ] Component specs linked
- [ ] Design review checklist attached
- [ ] PoC recommendations included

**8. Test Result**

- [ ] ✅ PASS - Phase gate properly enforced
- [ ] ❌ FAIL - Transition allowed without meeting criteria

---

### Test Scenario 5: Cloud Migration Design

**Test ID:** DESIGN-TEST-005  
**Priority:** 🟡 High  
**Time Required:** 60-90 minutes

#### Purpose
Test Design Mode with a complex, multi-component migration scenario.

#### Step-by-Step Instructions

**1. Execute Complex Test Prompt**

```
Design the migration from on-premise infrastructure to AWS. We have a 
monolithic Java application with PostgreSQL database, file storage, and 
batch processing jobs.
```

**2. Verify Comprehensive Analysis**

Bob should analyze:
- ✅ Current infrastructure architecture
- ✅ Application components
- ✅ Database requirements
- ✅ Storage needs
- ✅ Batch processing patterns

**3. Check Design Outputs**

**Target AWS Architecture**
- [ ] Compute: ECS/EKS/Lambda specified
- [ ] Database: RDS configuration designed
- [ ] Storage: S3 strategy defined
- [ ] Batch: Lambda/Batch service planned
- [ ] Architecture diagram created

**Containerization Strategy**
- [ ] Docker image design
- [ ] Container orchestration approach (ECS/EKS)
- [ ] Image registry strategy (ECR)
- [ ] Deployment pipeline design

**Infrastructure as Code**
- [ ] Terraform or CloudFormation chosen
- [ ] Resource definitions outlined
- [ ] State management strategy
- [ ] CI/CD integration planned

**Networking and Security**
- [ ] VPC design
- [ ] Security groups defined
- [ ] IAM roles and policies
- [ ] Encryption strategy (at rest and in transit)

**Data Migration Strategy**
- [ ] Database migration approach (DMS)
- [ ] File migration strategy (DataSync/S3 Transfer)
- [ ] Data validation plan
- [ ] Cutover strategy

**Monitoring and Observability**
- [ ] CloudWatch configuration
- [ ] Logging strategy (CloudWatch Logs)
- [ ] Alerting rules
- [ ] Dashboard design

**Disaster Recovery**
- [ ] Backup strategy
- [ ] Multi-AZ deployment
- [ ] Failover procedures
- [ ] RTO/RPO targets defined

**Cost Estimation**
- [ ] Resource costs estimated
- [ ] Optimization opportunities identified
- [ ] Cost monitoring strategy

**4. Verify Documentation Quality**

- [ ] All AWS services clearly specified
- [ ] Security and compliance addressed
- [ ] Cost-optimized design
- [ ] Implementation roadmap provided

**5. Test Result**

- [ ] ✅ PASS - Comprehensive cloud architecture designed
- [ ] ❌ FAIL - Incomplete or unclear design

---

## 3. Verification Checklists

### 3.1 Configuration Verification

Use this checklist before starting functional tests:

**Mode Configuration**
- [ ] Mode slug is `migration-design-mode`
- [ ] Mode name is "🏗️ Migration Design"
- [ ] Mode appears in mode selector
- [ ] Mode description is clear

**File Restrictions**
- [ ] Can read project files
- [ ] Can create markdown documentation
- [ ] Cannot modify source code files (.java, .py, .js, etc.)
- [ ] Cannot modify build files (pom.xml, build.gradle, package.json)
- [ ] Cannot modify test files

**Tool Access**
- [ ] `read_file` works
- [ ] `list_files` works
- [ ] `list_code_definition_names` works
- [ ] `search_files` works
- [ ] `write_to_file` works (markdown only)
- [ ] `apply_diff` is blocked
- [ ] `insert_content` is blocked
- [ ] `execute_command` is blocked

### 3.2 Functional Verification

**Design Artifact Creation**
- [ ] Architecture design documents created
- [ ] Component specifications generated
- [ ] Design review checklists produced
- [ ] PoC recommendations documented
- [ ] All artifacts are complete and actionable

**Mode Boundaries**
- [ ] No source code modifications occur
- [ ] No build configuration changes made
- [ ] File restrictions are respected
- [ ] Tool access policies enforced
- [ ] Boundary violations properly rejected

**Phase Gate Validation**
- [ ] Design completeness checked before execution
- [ ] Quality standards validated
- [ ] Gaps identified when incomplete
- [ ] Handoff package created when complete
- [ ] Clear transition guidance provided

**MCP Integration** (if available)
- [ ] Architecture Visualization MCP accessible
- [ ] Documentation Generator MCP functional
- [ ] Code Analysis MCP (read-only) works
- [ ] Dependency Scanner MCP (read-only) works

### 3.3 User Experience Verification

**Mode Purpose Clarity**
- [ ] Users understand what Design Mode does
- [ ] Users know when to use it
- [ ] Users understand limitations
- [ ] Users know how to proceed after design

**Workflow Usability**
- [ ] Workflow is easy to follow
- [ ] Steps are logical
- [ ] Progress is visible
- [ ] Completion is clear

**Output Quality**
- [ ] Artifacts are useful and actionable
- [ ] Format is appropriate (markdown/Confluence)
- [ ] Content is comprehensive
- [ ] Quality is high

**Error Handling**
- [ ] Errors are clear and helpful
- [ ] Guidance is provided
- [ ] Recovery steps are clear
- [ ] User is not confused

---

## 4. Common Issues and Troubleshooting

### Issue 1: Design Mode Not Appearing

**Symptoms:**
- Mode not visible in mode selector
- Trigger phrases don't activate mode

**Troubleshooting Steps:**

1. **Check YAML Configuration**
   ```bash
   # Verify file exists
   ls -la .bob/custom_modes.yaml
   
   # Check for syntax errors
   # Look for migration-design-mode entry
   ```

2. **Verify YAML Syntax**
   - Ensure proper indentation (2 spaces)
   - Check for missing colons or quotes
   - Validate YAML structure

3. **Restart IBM Bob IDE**
   - Close and reopen the IDE
   - Reload the project
   - Check mode list again

4. **Check Bob Logs**
   - Look for configuration loading errors
   - Check for mode registration failures

**Expected Fix:**
- Mode appears in selector after fixing YAML
- Trigger phrases work correctly

---

### Issue 2: Design Mode Modifies Code Files

**Symptoms:**
- Source files are being modified
- Build files are being updated
- Commands are being executed

**Troubleshooting Steps:**

1. **Verify File Restrictions in YAML**
   ```yaml
   fileRestrictions:
     allowedPatterns:
       - "**/*.md"
       - "**/docs/**"
       - "**/.bob/**"
     deniedPatterns:
       - "**/*.java"
       - "**/*.py"
       - "**/pom.xml"
       - "**/build.gradle"
   ```

2. **Check Tool Access Configuration**
   ```yaml
   toolAccess:
     allowed:
       - read_file
       - list_files
       - write_to_file  # markdown only
     denied:
       - apply_diff
       - insert_content
       - execute_command
   ```

3. **Test Boundary Enforcement**
   - Try Test Scenario 3 (Boundary Violation)
   - Verify Bob refuses code modifications
   - Check error messages are clear

**Expected Fix:**
- File restrictions properly enforced
- Bob refuses code modification requests
- Clear error messages provided

---

### Issue 3: MCP Tools Not Working

**Symptoms:**
- Architecture diagrams not generated
- Confluence integration fails
- MCP tool calls error out

**Troubleshooting Steps:**

1. **Check MCP Server Configuration**
   ```bash
   # Verify MCP servers are running
   # Check MCP configuration in Bob settings
   ```

2. **Verify MCP Tool Access in YAML**
   ```yaml
   mcpTools:
     - name: architecture-visualization
       access: full
     - name: documentation-generator
       access: full
     - name: code-analysis
       access: read-only
   ```

3. **Test MCP Connection**
   - Try simple MCP tool call
   - Check MCP server logs
   - Verify network connectivity

4. **Fallback to Manual Documentation**
   - If MCP unavailable, Bob should create markdown
   - Verify fallback behavior works

**Expected Fix:**
- MCP tools accessible from Design Mode
- Architecture diagrams generated
- Documentation created successfully

---

### Issue 4: Phase Gate Not Enforced

**Symptoms:**
- Can transition to Execution Mode without complete design
- Missing artifacts not detected
- No validation performed

**Troubleshooting Steps:**

1. **Check Phase Gate Configuration**
   ```yaml
   phaseGates:
     - name: design-complete
       criteria:
         - architecture-document-exists
         - component-specs-complete
         - design-review-done
   ```

2. **Test Phase Gate Validation**
   - Use Test Scenario 4
   - Try to transition with incomplete design
   - Verify Bob blocks transition

3. **Check Validation Logic**
   - Ensure validation criteria are clear
   - Verify Bob checks all criteria
   - Test with missing artifacts

**Expected Fix:**
- Phase gate properly enforced
- Missing artifacts detected
- Clear guidance provided

---

### Issue 5: Unclear Design Outputs

**Symptoms:**
- Design documents are vague
- Missing critical information
- Not actionable for Execution Mode

**Troubleshooting Steps:**

1. **Review Custom Instructions**
   - Check design workflow steps
   - Verify output requirements are clear
   - Ensure examples are provided

2. **Provide More Context**
   - Give Bob more project information
   - Answer clarifying questions
   - Provide assessment documents

3. **Use Specific Prompts**
   - Be explicit about requirements
   - Specify desired outputs
   - Reference frameworks (7Rs, etc.)

**Expected Fix:**
- Design documents are comprehensive
- All required information included
- Actionable for implementation

---

## 5. Success Criteria

### 5.1 Overall Success Indicators

Design Mode is working correctly when:

✅ **Design Artifacts**
- Complete architecture design documents created
- All components have clear migration strategies
- Technical specifications are detailed and actionable
- Design review checklist shows all criteria met

✅ **Mode Boundaries**
- No source code modifications occur
- No build configuration changes made
- File restrictions are respected
- Tool access policies enforced

✅ **Phase Gates**
- Design completeness validated before execution
- Quality standards checked
- Stakeholder approvals tracked (if applicable)
- Clear handoff to Execution Mode

✅ **User Experience**
- Users understand mode purpose
- Workflow is intuitive
- Output artifacts are useful
- Mode switching is smooth

✅ **Integration**
- MCP tools work correctly (if available)
- Documentation generation functional
- Architecture visualization available
- Read-only analysis accessible

### 5.2 Test Pass Criteria

**For Each Test Scenario:**

| Scenario | Pass Criteria |
|----------|---------------|
| **Test 1: Java Migration** | All design artifacts created, no code modified, clear handoff package |
| **Test 2: Framework Migration** | Complete framework design, all mappings documented, PoC recommendations |
| **Test 3: Boundary Violation** | All violations properly rejected, clear error messages, alternative approaches offered |
| **Test 4: Phase Gate** | Incomplete design blocked, complete design approved, clear transition guidance |
| **Test 5: Cloud Migration** | Comprehensive AWS architecture, all services specified, cost-optimized design |

**Overall Test Suite Pass:**
- All 5 test scenarios pass
- No critical issues discovered
- User experience is positive
- Documentation is complete

### 5.3 Quantitative Metrics

Track these metrics during testing:

| Metric | Target | Actual | Status |
|--------|--------|--------|--------|
| Mode activation success rate | >95% | ___ | ___ |
| Design artifact completeness | >90% | ___ | ___ |
| Code modification violations | 0 | ___ | ___ |
| Phase gate compliance | 100% | ___ | ___ |
| Time to complete design | <2 hours | ___ | ___ |
| User satisfaction | >4.0/5 | ___ | ___ |

---

## 6. Reporting Issues

### 6.1 Information to Collect

When reporting issues, include:

**Environment Information:**
- IBM Bob IDE version
- Operating system
- Project type (Java, Python, etc.)
- Mode configuration (attach .bob/custom_modes.yaml)

**Issue Details:**
- Test scenario ID (e.g., DESIGN-TEST-001)
- Step where issue occurred
- Expected behavior
- Actual behavior
- Error messages (if any)

**Reproduction Steps:**
1. Exact prompt used
2. Mode state before issue
3. Actions taken
4. Result observed

**Screenshots/Logs:**
- Screenshot of error
- Bob conversation log
- IDE console output
- MCP server logs (if applicable)

### 6.2 Issue Severity Levels

**🔴 Critical (P0):**
- Design Mode modifies code files
- Phase gate completely bypassed
- Mode crashes or becomes unresponsive
- Data loss or corruption

**🟡 High (P1):**
- Missing critical design artifacts
- Phase gate partially bypassed
- MCP integration completely broken
- Unclear error messages

**🟢 Medium (P2):**
- Incomplete design documentation
- Minor MCP issues
- Confusing user experience
- Documentation gaps

**⚪ Low (P3):**
- Cosmetic issues
- Minor documentation improvements
- Enhancement requests

### 6.3 Where to Report

**GitHub Issues:**
```
Repository: ibm-bob-migration-framework
Label: design-mode, bug, testing
Title: [DESIGN-MODE] Brief description
```

**Issue Template:**
```markdown
## Test Scenario
DESIGN-TEST-XXX: [Scenario Name]

## Environment
- Bob IDE Version: 
- OS: 
- Project Type: 

## Expected Behavior
[What should happen]

## Actual Behavior
[What actually happened]

## Reproduction Steps
1. 
2. 
3. 

## Screenshots/Logs
[Attach here]

## Severity
🔴 Critical / 🟡 High / 🟢 Medium / ⚪ Low
```

### 6.4 Log Collection

**Bob Conversation Log:**
1. Right-click in Bob chat
2. Select "Export Conversation"
3. Save as `design-mode-test-[scenario-id].txt`

**IDE Console Output:**
1. Open Developer Tools (if available)
2. Copy console output
3. Save as `console-output-[scenario-id].txt`

**MCP Server Logs:**
1. Check MCP server log location
2. Copy relevant log entries
3. Save as `mcp-logs-[scenario-id].txt`

---

## Appendix A: Quick Reference

### Test Execution Checklist

- [ ] Environment setup complete
- [ ] Configuration verified
- [ ] Test Scenario 1 executed
- [ ] Test Scenario 2 executed
- [ ] Test Scenario 3 executed
- [ ] Test Scenario 4 executed
- [ ] Test Scenario 5 executed
- [ ] All verification checklists completed
- [ ] Issues documented and reported
- [ ] Test results summarized

### Common Test Prompts

**Basic Design:**
```
Design the migration from [source] to [target]
```

**Boundary Test:**
```
Design the migration and also update [file]
```

**Phase Gate Test:**
```
I want to start executing the migration now
```

**Complex Design:**
```
Design the migration from [complex scenario with multiple components]
```

### Expected File Outputs

Design Mode should create:
- `architecture-design.md` - Main architecture document
- `component-specs/` - Component specifications
- `design-review-checklist.md` - Review checklist
- `poc-recommendations.md` - PoC recommendations
- `testing-strategy.md` - Testing approach

Design Mode should NOT modify:
- Source code files (*.java, *.py, *.js, etc.)
- Build files (pom.xml, build.gradle, package.json)
- Configuration files (application.properties, etc.)
- Test files (*Test.java, *_test.py, etc.)

---

## Appendix B: Test Results Template

### Test Execution Summary

**Test Date:** ___________  
**Tester:** ___________  
**Bob IDE Version:** ___________  
**Project:** ___________

### Results

| Test ID | Scenario | Status | Issues | Notes |
|---------|----------|--------|--------|-------|
| DESIGN-TEST-001 | Java Migration | ⬜ Pass / ⬜ Fail | | |
| DESIGN-TEST-002 | Framework Migration | ⬜ Pass / ⬜ Fail | | |
| DESIGN-TEST-003 | Boundary Violation | ⬜ Pass / ⬜ Fail | | |
| DESIGN-TEST-004 | Phase Gate | ⬜ Pass / ⬜ Fail | | |
| DESIGN-TEST-005 | Cloud Migration | ⬜ Pass / ⬜ Fail | | |

### Overall Assessment

**Pass Rate:** ___% (___/5 tests passed)

**Critical Issues:** ___

**Recommendations:**
- 
- 
- 

**Sign-off:**
- [ ] Ready for production
- [ ] Needs fixes before production
- [ ] Requires re-testing

**Approver:** ___________  
**Date:** ___________

---

**End of Testing Guide**

For implementation details, see [specification.md](specification.md).
For verification strategy, see [verification.md](verification.md).