FROM wildfly

ADD http://downloads.sourceforge.net/project/m2mlabs/release_0.9.5/mainspring.ear $JBOSS_HOME/standalone/deployments/
USER root
RUN chown jboss $JBOSS_HOME/standalone/deployments/mainspring.ear
USER jboss