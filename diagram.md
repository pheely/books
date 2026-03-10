**Flowchart diagram**

```mermaid
graph TD
    A[User Request: Refund Order] --> B{Agent Reasoning}
      B --> C[Tool: check_order_status]
      C --> D{Order Found?}
      D -->|No| E[Error Response:<br/>Ask user to verify]
      D -->|Yes| F{Order Status?}
      F -->|Delivered| G[Tool: process_refund]
      F -->|Processing| H[Error Response:<br/>Cannot refund yet]
      F -->|Cancelled| I[Error Response:<br/>Already cancelled]
      G --> J{Refund Success?}
      J -->|Yes| K[Success Response:<br/>Confirm to user]
      J -->|No| L[Tool: escalate_to_supervisor]
      H --> M[Offer escalation]
      L --> N[Supervisor assigned]

      style E fill:#ffcccc
      style H fill:#fff9cc
      style I fill:#fff9cc
      style K fill:#ccffcc
      style L fill:#ffe
```

**Sequence diagram**

```mermaid
sequenceDiagram
    participant Alice
    participant Bob
    Alice->>John: Hello John, how are you?
    loop HealthCheck
        John->>John: Fight against hypochondria
    end
    Note right of John: Rational thoughts <br/>prevail!
    John-->>Alice: Great!
    John->>Bob: How about you?
    Bob-->>John: Jolly good!
```

**Error handling**

```mermaid
flowchart TD
      A[Tool Error Received] --> B{Error Type?}
      B -->|temporary_failure| C[Acknowledge<br/>to Customer]
      C --> D[Retry Once]
      D --> E{Success?}
      E -->|Yes| F[Continue Workflow]
      E -->|No| G[Apologize & Ask<br/>to Try Later]
      B -->|not_found| H[Ask User to<br/>Verify Input]
      H --> I[Offer Alternative<br/>Lookup Methods]
      B -->|invalid_format| J[Explain<br/>Correct Format]
      J --> K[Provide<br/>Format Examples]
      B -->|permission_denied| L[Escalate<br/>Immediately]
      B -->|system_error| M[Apologize<br/>Profusely]
      M --> N[Log Error<br/>for Team]
      N --> O[Offer<br/>Callback]

      style L fill:#ffcccc
      style G fill:#fff9cc
      style H fill:#fff9cc
      style J fill:#fff9cc
      style M fill:#ffcccc
      style F fill:#ccffcc
