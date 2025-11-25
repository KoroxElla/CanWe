# CanWe
Internet Cloud CW

## Task Distribution

| Task                            | Member A                         | Member B                                  | Member C                                 | Member D                                          |
| ------------------------------- | -------------------------------- | ----------------------------------------- | ---------------------------------------- | ------------------------------------------------- |
| **Azure Virtual Network**       | Create VNet, define subnets      | Help define subnets and NSGs              | Validate subnet connectivity             | Document network for frontend/backend             |
| **Backend VM**                  | Install Java, run backend server | Configure API endpoints                   | Ensure DB connection works               | Test backend responses from frontend pods         |
| **Database (PostgreSQL)**       | Help configure master DB         | Set up read replicas                      | Configure strong consistency replication | Test read/write from backend                      |
| **Frontend (AKS + Containers)** | Create Docker image for frontend | Deploy pods to AKS                        | Configure ingress & test LB routing      | Implement HPA (autoscaling) & monitor pod metrics |
| **Load Balancer**               | Configure LB rules               | Validate traffic routing to frontend pods | Integrate backend health checks          | Test failover & scaling scenarios                 |
| **Code & Version Control**      | Push code to GitHub              | Pull/test teammates’ code                 | Review PRs & merge changes               | Ensure frontend/backend compatibility             |
| **Documentation & Testing**     | Document infrastructure steps    | Document DB and replication               | Document frontend & container setup      | Document testing & results                        |


