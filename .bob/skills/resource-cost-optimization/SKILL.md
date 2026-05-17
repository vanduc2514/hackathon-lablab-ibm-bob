---
name: resource-cost-optimization
description: Optimize infrastructure resource allocation and reduce operational costs. Right-sizes compute, storage, and network resources while maintaining performance targets.
---

# Resource & Cost Optimization

You are optimizing infrastructure resources and operational costs for a migrated system. Your goal is to reduce waste, right-size resources, and implement cost-effective configurations without compromising performance or reliability.

## Objective

Analyze resource utilization patterns, identify over-provisioning or inefficiencies, and implement optimizations that reduce costs while meeting performance SLAs.

## Required Inputs

- `optimization_performance_analysis.md`: Resource utilization patterns and bottlenecks
- Infrastructure configuration: current resource allocations, pricing
- Cost reports: current spending by resource type
- Performance targets: SLAs that must be maintained

## Output Document

Create `optimization_resource_tuning.md` containing:

**Resource Utilization Analysis**: Current usage patterns vs. allocated capacity

**Cost Breakdown**: Spending by resource category with optimization opportunities

**Right-Sizing Recommendations**: Specific resource adjustments with expected savings

**Auto-Scaling Configuration**: Dynamic scaling policies to match demand

**Cost Savings Summary**: Projected monthly/annual savings from optimizations

## Optimization Categories

### 1. Compute Resources
- Right-size VM/container instances based on actual CPU/memory usage
- Implement auto-scaling to match demand patterns
- Use spot/preemptible instances for non-critical workloads
- Optimize container resource requests and limits

### 2. Database Resources
- Right-size database instances based on connection and query patterns
- Implement read replicas for read-heavy workloads
- Use connection pooling to reduce database connections
- Archive or delete unused data to reduce storage costs

### 3. Storage Optimization
- Implement lifecycle policies to move data to cheaper storage tiers
- Enable compression for logs and backups
- Delete unused snapshots and old backups
- Use appropriate storage classes (SSD vs HDD)

### 4. Network Optimization
- Reduce data transfer costs with CDN or edge caching
- Optimize API payload sizes
- Implement compression for network traffic
- Use private networking where possible

### 5. Monitoring & Observability
- Reduce log retention periods for non-critical logs
- Sample metrics instead of collecting everything
- Use cost-effective monitoring solutions
- Archive historical data to cheaper storage

## Analysis Process

1. **Collect Utilization Data**: Gather resource usage metrics over representative period
2. **Identify Waste**: Find over-provisioned resources with low utilization
3. **Calculate Savings**: Estimate cost reduction from each optimization
4. **Prioritize Changes**: Rank by savings potential vs. implementation risk
5. **Implement Gradually**: Apply changes incrementally with monitoring
6. **Validate Performance**: Ensure SLAs still met after optimization

## Example: Cloud Application Optimization

```
Resource Utilization Analysis:

Application Servers (3x m5.xlarge instances):
- CPU: 25% average, 45% peak
- Memory: 40% average, 60% peak
- Current Cost: $450/month
- Recommendation: Downsize to m5.large (2 vCPU, 8GB)
- Expected Savings: $225/month (-50%)
- Risk: Low (significant headroom)

Database (db.r5.2xlarge):
- CPU: 15% average, 30% peak
- Memory: 50% average, 70% peak
- Connections: 20 average, 50 peak (max 200)
- Current Cost: $720/month
- Recommendation: Downsize to db.r5.xlarge
- Expected Savings: $360/month (-50%)
- Risk: Medium (monitor connection pool)

Storage (500GB SSD):
- Used: 180GB (36%)
- Growth: 5GB/month
- Current Cost: $50/month
- Recommendation: Reduce to 250GB, implement lifecycle policy
- Expected Savings: $25/month (-50%)
- Risk: Low (plenty of headroom)

---

Auto-Scaling Configuration:

Current: Fixed 3 instances 24/7

Proposed:
- Minimum: 2 instances
- Maximum: 5 instances
- Scale up: CPU > 70% for 5 minutes
- Scale down: CPU < 30% for 10 minutes
- Schedule: Scale to 3 instances during business hours (8am-6pm)

Expected Impact:
- Average instances: 2.5 (down from 3)
- Peak capacity: 5 instances (up from 3)
- Cost Savings: $75/month (-17%)
- Improved resilience during traffic spikes

---

Cost Savings Summary:

| Category | Current | Optimized | Savings | % Reduction |
|----------|---------|-----------|---------|-------------|
| Compute | $450 | $225 | $225 | 50% |
| Database | $720 | $360 | $360 | 50% |
| Storage | $50 | $25 | $25 | 50% |
| Network | $100 | $80 | $20 | 20% |
| **Total** | **$1,320** | **$690** | **$630** | **48%** |

Annual Savings: $7,560
```

## Platform-Specific Optimizations

**AWS**: Reserved Instances, Savings Plans, Spot Instances, S3 Intelligent-Tiering

**Azure**: Reserved VM Instances, Azure Hybrid Benefit, Spot VMs, Cool/Archive storage

**GCP**: Committed Use Discounts, Preemptible VMs, Coldline/Archive storage

**Kubernetes**: Vertical Pod Autoscaler, Horizontal Pod Autoscaler, node auto-scaling

## Best Practices

**Monitor continuously**: Track utilization trends over time, not point-in-time snapshots

**Leave headroom**: Don't optimize to 100% utilization; maintain buffer for spikes

**Test under load**: Validate performance after downsizing with realistic load tests

**Implement gradually**: Make changes incrementally to isolate issues

**Document baselines**: Record current performance before optimization

## Validation Checklist

- [ ] Resource utilization analyzed over representative time period (≥7 days)
- [ ] Over-provisioned resources identified with supporting data
- [ ] Cost savings calculated for each optimization
- [ ] Performance impact assessed and validated
- [ ] Auto-scaling policies configured appropriately
- [ ] Monitoring alerts updated for new resource levels
- [ ] Rollback plan documented for each change

## Guardrails

**Performance first**: Never sacrifice SLA compliance for cost savings

**Gradual changes**: Implement one optimization at a time

**Monitor closely**: Watch performance metrics after each change

**Maintain buffer**: Keep 20-30% headroom for unexpected load spikes