# CanWe
Internet Cloud CW

# Task Distribution

## TASK 2 – CLOUD IMPLEMENTATION & DEPLOYMENT

### 2.1 Network & Infrastructure Setup

| Task Component                           | A | B | C | D |
| ---------------------------------------- | - | - | - | - |
| Create Virtual Network (VNet)            | ✔ | — | — | — |
| Create Subnets (Frontend, Backend, DB)   | ✔ | — | — | — |
| Configure Network Security Groups (NSGs) | ✔ | — | — | — |
| Deploy AKS Cluster into Frontend Subnet  | — | ✔ | — | — |
| Attach Public Load Balancer / Ingress    | — | ✔ | — | — |
| Deploy Backend VM into Backend Subnet    | — | — | ✔ | — |
| Configure Backend Private IP Routing     | — | — | ✔ | — |
| Deploy PostgreSQL (Master + Replicas)    | — | — | — | ✔ |
| Configure Database Private Networking    | — | — | — | ✔ |

### 2.2 Application Implementation (Frontend, Backend, Database)

| Development Area                                    | A | B | C | D |
| --------------------------------------------------- | - | - | - | - |
| Frontend UI (Canvas, Drawing Tools)                 | ✔ | — | — | — |
| Frontend Sync Logic (WebSockets/Polling)            | — | ✔ | — | — |
| Backend API Endpoints (POST/GET whiteboard updates) | — | — | ✔ | — |
| Backend Sync Logic (State merging, sessions)        | — | — | ✔ | — |
| Database Schema Design (tables, relations)          | — | — | — | ✔ |
| Implement DB Write/Read Operations                  | — | — | — | ✔ |


### 2.3 Containerisation & Deployment to Cloud

| Containerisation / Deployment Task                 | A | B | C | D |
| -------------------------------------------------- | - | - | - | - |
| Build Frontend Dockerfile                          | ✔ | — | — | — |
| Build Backend Dockerfile                           | — | ✔ | — | — |
| Create Kubernetes Deployment Manifests             | — | — | ✔ | — |
| Configure Kubernetes Services (ClusterIP/NodePort) | — | — | ✔ | — |
| Configure Ingress + Load Balancer Routing          | — | — | — | ✔ |
| Push Images to Container Registry (ACR/DockerHub)  | ✔ | ✔ | ✔ | ✔ |


### 2.4 System Integration & Joint Testing

| Integration Task                        | A | B | C | D |
| --------------------------------------- | - | - | - | - |
| Test Frontend → Backend Network Routing | ✔ | ✔ | ✔ | ✔ |
| Test Backend → Database Connectivity    | ✔ | ✔ | ✔ | ✔ |
| Test Multi-user Whiteboard Sync         | ✔ | ✔ | ✔ | ✔ |
| Perform Bug Fixes per Component         | ✔ | ✔ | ✔ | ✔ |


## TASK 3 – TESTING, EVALUATION & ANALYSIS

### 3.1 Scalability Testing

| Scalability Area                             | A | B | C | D |
| -------------------------------------------- | - | - | - | - |
| Test AKS Pod Autoscaling                     | ✔ | — | — | — |
| Test Backend VM Scaling & Latency            | — | ✔ | — | — |
| Test PostgreSQL Replication Performance      | — | — | ✔ | — |
| Test Load Balancer Throughput & Distribution | — | — | — | ✔ |

### 3.2 Fault Tolerance Evaluation

| Fault Scenario                              | A | B | C | D |
| ------------------------------------------- | - | - | - | - |
| Kill a Frontend Pod → Observe Recovery      | ✔ | — | — | — |
| Stop Backend VM → Observe Frontend Behavior | — | ✔ | — | — |
| Break DB Replica → Test Failover            | — | — | ✔ | — |
| Simulate Load Balancer Failure → Evaluate   | — | — | — | ✔ |

### 3.3 Performance Metrics & Logs

| Logging / Metrics Task                          | A | B | C | D |
| ----------------------------------------------- | - | - | - | - |
| Collect AKS Metrics (CPU, Memory, Pod Restarts) | ✔ | — | — | — |
| Collect Backend VM Logs (journalctl / API logs) | — | ✔ | — | — |
| Collect Database Performance Metrics            | — | — | ✔ | — |
| Collect Load Balancer Logs                      | — | — | — | ✔ |





### Tech Stack
- Backend: Springboot Java
- Database : Postgre
- Frontend: HTML, css( , javascript
