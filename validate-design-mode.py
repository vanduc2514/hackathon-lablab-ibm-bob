#!/usr/bin/env python3
"""
Design Mode Implementation Validation Script

This script validates that the Migration Design Mode implementation
is complete and correct according to specifications.

Usage: python validate-design-mode.py
Exit codes: 0 = all pass, 1 = any fail
"""

import os
import sys
import yaml
import re
from pathlib import Path
from typing import List, Tuple, Dict

# Fix Windows console encoding
if sys.platform == 'win32':
    import codecs
    sys.stdout = codecs.getwriter('utf-8')(sys.stdout.buffer, 'strict')
    sys.stderr = codecs.getwriter('utf-8')(sys.stderr.buffer, 'strict')

# ANSI color codes
class Colors:
    GREEN = '\033[92m'
    RED = '\033[91m'
    YELLOW = '\033[93m'
    BLUE = '\033[94m'
    BOLD = '\033[1m'
    RESET = '\033[0m'

def print_header(text: str):
    """Print section header"""
    print(f"\n{Colors.BOLD}{Colors.BLUE}{'=' * 60}{Colors.RESET}")
    print(f"{Colors.BOLD}{Colors.BLUE}{text}{Colors.RESET}")
    print(f"{Colors.BOLD}{Colors.BLUE}{'=' * 60}{Colors.RESET}\n")

def print_check(passed: bool, message: str, indent: int = 0):
    """Print check result with color"""
    prefix = "  " * indent
    symbol = f"{Colors.GREEN}✓{Colors.RESET}" if passed else f"{Colors.RED}✗{Colors.RESET}"
    print(f"{prefix}[{symbol}] {message}")

def print_error(message: str, indent: int = 1):
    """Print error message"""
    prefix = "  " * indent
    print(f"{prefix}{Colors.RED}ERROR: {message}{Colors.RESET}")

def print_warning(message: str, indent: int = 1):
    """Print warning message"""
    prefix = "  " * indent
    print(f"{prefix}{Colors.YELLOW}WARNING: {message}{Colors.RESET}")

class ValidationResult:
    """Track validation results"""
    def __init__(self):
        self.total = 0
        self.passed = 0
        self.failed = 0
        self.errors: List[str] = []
    
    def add_check(self, passed: bool, error_msg: str = ""):
        self.total += 1
        if passed:
            self.passed += 1
        else:
            self.failed += 1
            if error_msg:
                self.errors.append(error_msg)
    
    def is_success(self) -> bool:
        return self.failed == 0

# Base path for the project
BASE_PATH = Path(__file__).resolve().parent

def validate_yaml_config() -> ValidationResult:
    """Validate YAML configuration file"""
    print_header("YAML Configuration Validation")
    result = ValidationResult()
    
    yaml_path = BASE_PATH / ".bob" / "custom_modes.yaml"
    
    # Check file exists
    exists = yaml_path.exists()
    print_check(exists, f"File exists: {yaml_path}")
    result.add_check(exists, f"YAML file not found: {yaml_path}")
    
    if not exists:
        return result
    
    # Check valid YAML syntax
    try:
        with open(yaml_path, 'r', encoding='utf-8') as f:
            config = yaml.safe_load(f)
        print_check(True, "Valid YAML syntax")
        result.add_check(True)
    except yaml.YAMLError as e:
        print_check(False, "Valid YAML syntax")
        print_error(f"YAML parsing error: {e}", indent=2)
        result.add_check(False, f"Invalid YAML: {e}")
        return result
    
    # Check migration-design-mode entry exists
    custom_modes = config.get('customModes', [])
    design_mode = None
    for mode in custom_modes:
        if mode.get('slug') == 'migration-design-mode':
            design_mode = mode
            break
    
    has_entry = design_mode is not None
    print_check(has_entry, "migration-design-mode entry exists")
    result.add_check(has_entry, "migration-design-mode entry not found in YAML")
    
    if not design_mode:
        return result
    
    # Check required fields
    required_fields = {
        'description': 'Has non-empty description',
        'roleDefinition': 'Has non-empty roleDefinition',
        'whenToUse': 'Has non-empty whenToUse',
        'customInstructions': 'Has non-empty customInstructions'
    }
    
    for field, check_msg in required_fields.items():
        value = design_mode.get(field, '')
        has_value = bool(value and str(value).strip())
        print_check(has_value, check_msg)
        result.add_check(has_value, f"Missing or empty field: {field}")
    
    # Check customInstructions contains key phrases
    custom_instructions = str(design_mode.get('customInstructions', '')).lower()
    
    key_phrases = [
        (r'design|architecture', 'Contains "design" or "architecture"'),
        (r'not\s+execute|do\s+not\s+execute|must\s+not.*execute', 'Contains execution boundaries (NOT execute)'),
        (r'phase\s+gate', 'Contains "phase gate"'),
        (r'handoff', 'Contains "handoff"')
    ]
    
    for pattern, check_msg in key_phrases:
        has_phrase = bool(re.search(pattern, custom_instructions))
        print_check(has_phrase, check_msg)
        result.add_check(has_phrase, f"Missing key phrase: {check_msg}")
    
    return result

def validate_documentation_files() -> ValidationResult:
    """Validate documentation files exist and have required content"""
    print_header("Documentation Validation")
    result = ValidationResult()
    
    # Technical specifications
    tech_spec_path = BASE_PATH / "hackathon-ideas" / "migration-technical-specifications.md"
    exists = tech_spec_path.exists()
    print_check(exists, f"File exists: {tech_spec_path.name}")
    result.add_check(exists, f"File not found: {tech_spec_path}")
    
    if exists:
        with open(tech_spec_path, 'r', encoding='utf-8') as f:
            content = f.read()
        has_section = bool(re.search(r'###?\s+1\.3.*Migration\s+Design\s+Mode|##\s+1\.3', content, re.IGNORECASE))
        print_check(has_section, "Contains Section 1.3 (Migration Design Mode)", indent=1)
        result.add_check(has_section, "Section 1.3 not found in technical specifications")
    
    # Skills and workflows
    skills_path = BASE_PATH / "hackathon-ideas" / "migration-skills-and-workflows.md"
    exists = skills_path.exists()
    print_check(exists, f"File exists: {skills_path.name}")
    result.add_check(exists, f"File not found: {skills_path}")
    
    if exists:
        with open(skills_path, 'r', encoding='utf-8') as f:
            content = f.read()
        has_phase = bool(re.search(r'Phase\s+2\.5|Design', content, re.IGNORECASE))
        print_check(has_phase, "Contains Phase 2.5 or Design workflows", indent=1)
        result.add_check(has_phase, "Phase 2.5/Design not found in skills document")
    
    return result

def validate_documentation_folder() -> ValidationResult:
    """Validate documentation folder structure"""
    print_header("Documentation Folder Validation")
    result = ValidationResult()
    
    docs_path = BASE_PATH / "docs" / "design-mode"
    
    # Check folder exists
    exists = docs_path.exists() and docs_path.is_dir()
    print_check(exists, f"Folder exists: {docs_path}")
    result.add_check(exists, f"Folder not found: {docs_path}")
    
    if not exists:
        return result
    
    # Check required files
    required_files = [
        'README.md',
        'implementation-plan.md',
        'specification.md',
        'verification.md',
        'testing-guide.md',
        'test-execution-report.md'
    ]
    
    for filename in required_files:
        file_path = docs_path / filename
        exists = file_path.exists()
        print_check(exists, f"File exists: {filename}", indent=1)
        result.add_check(exists, f"Missing file: {filename}")
    
    return result

def validate_content() -> ValidationResult:
    """Validate content includes critical boundaries"""
    print_header("Content Validation")
    result = ValidationResult()
    
    yaml_path = BASE_PATH / ".bob" / "custom_modes.yaml"
    
    if not yaml_path.exists():
        print_error("Cannot validate content - YAML file not found")
        result.add_check(False, "YAML file not found for content validation")
        return result
    
    try:
        with open(yaml_path, 'r', encoding='utf-8') as f:
            config = yaml.safe_load(f)
        
        custom_modes = config.get('customModes', [])
        design_mode = None
        for mode in custom_modes:
            if mode.get('slug') == 'migration-design-mode':
                design_mode = mode
                break
        
        if not design_mode:
            print_error("migration-design-mode not found in YAML")
            result.add_check(False, "migration-design-mode not found")
            return result
        
        # Get all text content
        all_content = ' '.join([
            str(design_mode.get('description', '')),
            str(design_mode.get('roleDefinition', '')),
            str(design_mode.get('whenToUse', '')),
            str(design_mode.get('customInstructions', ''))
        ]).lower()
        
        # Critical content checks
        checks = [
            (r'architecture|design', 'Mentions "architecture" or "design"'),
            (r'not\s+execute|do\s+not\s+execute|must\s+not.*execute|no\s+code\s+execution', 
             'Mentions "NOT" or "do not" with "execute"'),
            (r'markdown|\.md\s+file', 'Mentions "markdown" or ".md" files'),
            (r'phase\s+gate|transition', 'Mentions "phase gate" or "transition"'),
            (r'assessment.*execution|assessment\s+mode.*execution\s+mode', 
             'Mentions Assessment and Execution (integration points)')
        ]
        
        for pattern, check_msg in checks:
            has_content = bool(re.search(pattern, all_content))
            print_check(has_content, check_msg)
            result.add_check(has_content, f"Missing critical content: {check_msg}")
        
    except Exception as e:
        print_error(f"Error validating content: {e}")
        result.add_check(False, f"Content validation error: {e}")
    
    return result

def validate_cross_references() -> ValidationResult:
    """Validate cross-references between documentation files"""
    print_header("Cross-Reference Validation")
    result = ValidationResult()
    
    docs_path = BASE_PATH / "docs" / "design-mode"
    
    if not docs_path.exists():
        print_error("Documentation folder not found")
        result.add_check(False, "Cannot validate cross-references - folder missing")
        return result
    
    # Get all markdown files
    md_files = list(docs_path.glob("*.md"))
    
    if not md_files:
        print_error("No markdown files found in docs/design-mode/")
        result.add_check(False, "No markdown files found")
        return result
    
    # Check for basic internal links
    all_links_valid = True
    broken_links = []
    
    for md_file in md_files:
        try:
            with open(md_file, 'r', encoding='utf-8') as f:
                content = f.read()
            
            # Find markdown links [text](file.md)
            links = re.findall(r'\[([^\]]+)\]\(([^)]+\.md)\)', content)
            
            for link_text, link_target in links:
                # Check if it's a relative link to another doc file
                if not link_target.startswith('http'):
                    target_path = docs_path / link_target
                    if not target_path.exists():
                        all_links_valid = False
                        broken_links.append(f"{md_file.name} -> {link_target}")
        
        except Exception as e:
            print_warning(f"Could not check links in {md_file.name}: {e}")
    
    if broken_links:
        print_check(False, "Internal links valid")
        for broken in broken_links:
            print_error(f"Broken link: {broken}", indent=2)
        result.add_check(False, f"Found {len(broken_links)} broken internal links")
    else:
        print_check(True, "Internal links valid (basic check)")
        result.add_check(True)
    
    # Check that files reference each other
    has_references = False
    for md_file in md_files:
        if md_file.name == 'README.md':
            continue
        try:
            with open(md_file, 'r', encoding='utf-8') as f:
                content = f.read()
            if re.search(r'\[.*\]\(.*\.md\)', content):
                has_references = True
                break
        except:
            pass
    
    print_check(has_references, "Documentation files reference each other")
    result.add_check(has_references, "No cross-references found between docs")
    
    return result

def print_summary(results: Dict[str, ValidationResult]):
    """Print validation summary"""
    print_header("VALIDATION SUMMARY")
    
    total_checks = sum(r.total for r in results.values())
    total_passed = sum(r.passed for r in results.values())
    total_failed = sum(r.failed for r in results.values())
    
    for section, result in results.items():
        status = f"{Colors.GREEN}✓{Colors.RESET}" if result.is_success() else f"{Colors.RED}✗{Colors.RESET}"
        print(f"[{status}] {section}: {result.passed}/{result.total} checks passed")
    
    print(f"\n{Colors.BOLD}Total: {total_passed}/{total_checks} checks passed{Colors.RESET}")
    
    if total_failed == 0:
        print(f"\n{Colors.GREEN}{Colors.BOLD}✅ ALL CHECKS PASSED{Colors.RESET}")
        print(f"\n{Colors.GREEN}Implementation is ready for manual testing in IBM Bob IDE.{Colors.RESET}")
        return True
    else:
        print(f"\n{Colors.RED}{Colors.BOLD}❌ {total_failed} CHECKS FAILED{Colors.RESET}")
        print(f"\n{Colors.RED}Please fix the issues above before proceeding.{Colors.RESET}")
        
        # Print all errors
        print(f"\n{Colors.BOLD}Error Summary:{Colors.RESET}")
        for section, result in results.items():
            if result.errors:
                print(f"\n{Colors.YELLOW}{section}:{Colors.RESET}")
                for error in result.errors:
                    print(f"  • {error}")
        
        return False

def main():
    """Main validation function"""
    print(f"\n{Colors.BOLD}{Colors.BLUE}Design Mode Implementation Validation{Colors.RESET}")
    print(f"{Colors.BLUE}{'=' * 60}{Colors.RESET}\n")
    
    # Check if project path is accessible
    if not BASE_PATH.exists():
        print_error(f"Project path not found: {BASE_PATH}")
        print_error("Please ensure the project files are available")
        sys.exit(1)
    
    # Run all validations
    results = {
        "YAML Configuration": validate_yaml_config(),
        "Documentation Files": validate_documentation_files(),
        "Documentation Folder": validate_documentation_folder(),
        "Content Validation": validate_content(),
        "Cross-References": validate_cross_references()
    }
    
    # Print summary and exit
    success = print_summary(results)
    sys.exit(0 if success else 1)

if __name__ == "__main__":
    main()

# Made with Bob
