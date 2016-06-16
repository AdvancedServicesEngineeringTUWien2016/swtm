#!/bin/sh
`tce-load -wi make`
`tce-load -wi bash`
`sudo curl -o /tmp/kubectl -O https://storage.googleapis.com/kubernetes-release/release/v1.2.2/bin/linux/amd64/kubectl && sudo mv /tmp/kubectl /usr/local/bin/kubectl`
sudo chmod +x /usr/local/bin/kubectl
#`sudo wget -o /tmp/gofabric8 https://fabric8-ci.fusesource.com/job/gofabric8/lastSuccessfulBuild/artifact/src/github.com/fabric8io/gofabric8/build/gofabric8 && sudo mv /tmp/gofabric8 /usr/local/bin/gofabric8`
#sudo chmod +x /usr/local/bin/gofabric8

make -C wildfly-base
make -C wildfly-camel
make -C cassandra
make -C mainspring