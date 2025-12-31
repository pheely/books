# README

How to Trigger the Switchover
When you need to failover to a different region:

Update the property: Update your configuration source (e.g., a Kubernetes ConfigMap, Spring Cloud Config Server, or an externalized file).

Example new value: spring.data.redis.cluster.nodes=10.1.0.5:6379 (Region B IP).

Call the refresh endpoint: Send a POST request to your application.

Bash

curl -X POST http://your-app-ip:8080/actuator/refresh
Why this works for Cross-Region:
Zero Downtime Rebinding: @RefreshScope ensures that the next time any code uses RedisTemplate, it will be using a new connection factory pointing to the new regional IP.

Topology Awareness: Even if the IP hasn't changed but the cluster layout has, the enableAllAdaptiveRefreshTriggers setting allows the Lettuce client to automatically learn about new shards in the new region.
