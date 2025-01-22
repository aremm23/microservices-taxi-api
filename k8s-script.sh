cd ./k8s

cd auth-service
kubectl apply -f auth-service-configmap.yml
kubectl apply -f auth-service.yml
cd ./..

cd elk
kubectl apply -f logstash-configmap.yml
kubectl apply -f elasticsearch.yml
kubectl apply -f logstash.yml
kubectl apply -f kibana.yml
cd ./..

cd keycloak
kubectl apply -f keycloak-configmap.yml
kubectl apply -f keycloak.yml
cd ./..

cd monitoring
kubectl apply -f grafana.yml
kubectl apply -f prometheus.yml
cd ./..

cd passenger-service
kubectl apply -f passenger-service-configmap.yml
kubectl apply -f passenger-service.yml
cd ./..

cd driver-service
kubectl apply -f driver-service-configmap.yml
kubectl apply -f driver-service.yml
cd ./..

cd review-service
kubectl apply -f review-service-configmap.yml
kubectl apply -f review-service.yml
cd ./..

cd payment-service
kubectl apply -f payment-service-configmap.yml
kubectl apply -f payment-service.yml
cd ./..

cd ride-service
kubectl apply -f ride-service-configmap.yml
kubectl apply -f ride-service.yml
cd ./..

cd kafka
kubectl apply -f kafka.yml
kubectl apply -f zookeeper.yml
cd ./..

cd mongo
kubectl apply -f mongo.yml
cd ./..

cd redis
kubectl apply -f redis.yml
cd ./..

cd postgres
kubectl apply -f kc-postgres-volume-claim.yml
kubectl apply -f passenger-postgres-volume-claim.yml
kubectl apply -f driver-postgres-volume-claim.yml
kubectl apply -f payment-postgres-volume-claim.yml
kubectl apply -f kc-postgres.yml
kubectl apply -f passenger-postgres.yml
kubectl apply -f driver-postgres.yml
kubectl apply -f payment-postgres.yml
cd ./..

cd mysql
kubectl apply -f mysql.yml
cd ./..

cd rabbit
kubectl apply -f rabbit.yml
cd ./..
