#!/bin/bash

set -e

restartCluster() {
	docker rm -f kubelet || true
	cleanupContainers

	# TODO: actually, this should also start kube-proxy but strangely, it does not. Maybe ask on stackoverflow - meanwhile we start it manually
	docker run \
		--volume=/:/rootfs:ro \
		--volume=/sys:/sys:ro \
		--volume=/dev:/dev \
		--volume=/var/lib/docker/:/var/lib/docker:ro \
		--volume=/var/lib/kubelet/:/var/lib/kubelet:rw \
		--volume=/var/run:/var/run:rw \
		--net=host \
		--pid=host \
		--privileged=true \
		-d \
		--name kubelet \
		gcr.io/google_containers/hyperkube:v1.2.2 \
		/hyperkube kubelet --containerized --hostname-override="127.0.0.1" --address="0.0.0.0" --api-servers=http://localhost:8080 --config=/etc/kubernetes/manifests --cluster-dns=10.0.0.10 --cluster-domain=aws.local
	
	docker run -d --name hyperkube --net=host --privileged gcr.io/google_containers/hyperkube:v1.2.2 /hyperkube proxy --master=http://127.0.0.1:8080 --v=2 # hyperkube
}

restartServices() {
	kubectl delete -f dns || true
	kubectl delete -f cassandra || true
	kubectl delete -f activemq || true
	kubectl delete -f mainspring || true
	
	kubectl create -f dns
	kubectl create -f cassandra/cassandra_pod.yml
	kubectl create -f cassandra/cassandra_service.yml
	kubectl create -f activemq
	kubectl create -f mainspring
}

restartAppServices() {
	kubectl delete -f analytics || true
	kubectl delete -f notifications || true
	kubectl delete -f planner || true
	kubectl delete -f resources || true
	kubectl delete -f admin-webapp || true
	
	kubectl create -f analytics
	kubectl create -f notifications
	kubectl create -f planner
	kubectl create -f resources
	kubectl create -f admin-webapp
}

createSecrets() {
	kubectl create secret generic maps.google.com --from-literal=apikey=${GOOGLE_MAPS_API_KEY}
}

copyArtifacts() {
	rm -f api-gateway/api-gateway-webapp*.war
	cp -f ../was/api-gateway/webapp/target/api-gateway-webapp*.war api-gateway/
	rm -f masterdata/master-data*.war
	cp -f ../was/master-data/webapp/target/master-data*.war masterdata/
	rm -f opscanner/opscanner*.war
	cp -f ../was/opscanner/webapp/target/opscanner*.war opscanner/
	rm -f booking/booking-webapp*.war
	cp -f ../was/booking/webapp/target/booking-webapp*.war booking/
	rm -f storefront/store-front-webapp*.war
	cp -f ../was/store-front/webapp/target/store-front-webapp*.war storefront/
}

buildAppServices() {
	make -C mainspring
}

# This removes stops and removes all containers plus REMOVES ALL VOLUMES ATTACHED TO THE CONTAINER
# The function DOES NOT remove containers with a name ending with 'Data'.
# Therefore any data containers should follow this name pattern in order to not be removed by this function.
cleanupContainers() {
	echo "Stopping and removing containers..."
	docker rm -f -v $(docker ps -a | awk '
	  NR==1{
		FIRSTLINEWIDTH=length($0)
		IDPOS=index($0,"CONTAINER ID");
		IMAGEPOS=index($0,"IMAGE");
		COMMANDPOS=index($0,"COMMAND");
		CREATEDPOS=index($0,"CREATED");
		STATUSPOS=index($0,"STATUS");
		PORTSPOS=index($0,"PORTS");
		NAMESPOS=index($0,"NAMES");
		UPDATECOL();
	  }
	  function UPDATECOL () {
		ID=substr($0,IDPOS,IMAGEPOS-IDPOS-1);
		IMAGE=substr($0,IMAGEPOS,COMMANDPOS-IMAGEPOS-1);
		COMMAND=substr($0,COMMANDPOS,CREATEDPOS-COMMANDPOS-1);
		CREATED=substr($0,CREATEDPOS,STATUSPOS-CREATEDPOS-1);
		STATUS=substr($0,STATUSPOS,PORTSPOS-STATUSPOS-1);
		PORTS=substr($0,PORTSPOS,NAMESPOS-PORTSPOS-1);
		NAMES=substr($0, NAMESPOS);
	  }
	  function PRINT () {
		print ID;
	  }
	  NR>1 {
		UPDATECOL();
		if( NAMES !~ /.*Data\s*$/) {
			PRINT();
		}
	  }' | less -E;) || true
}

if [ -n "$1" ]; then
	$@
fi

exit 0