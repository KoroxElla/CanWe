#!/bin/bash

# Build Docker image
docker build -t whiteboard-backend:latest .

# Apply Kubernetes configurations
kubectl apply -f k8s/configmap.yaml
kubectl apply -f k8s/secrets.yaml
kubectl apply -f k8s/pvc.yaml
kubectl apply -f k8s/postgres.yaml
kubectl apply -f k8s/deployment.yaml
kubectl apply -f k8s/service-clusterip.yaml

# For development with external access
# kubectl apply -f k8s/service-nodeport.yaml

# Check deployment status
kubectl get pods
kubectl get services
