FROM wildfly

ADD changeDatabase.xsl $JBOSS_HOME/
RUN java -jar /usr/share/java/saxon.jar -s:$JBOSS_HOME/standalone/configuration/standalone.xml -xsl:$JBOSS_HOME/changeDatabase.xsl -o:$JBOSS_HOME/standalone/configuration/standalone.xml; java -jar /usr/share/java/saxon.jar -s:$JBOSS_HOME/standalone/configuration/standalone-ha.xml -xsl:$JBOSS_HOME/changeDatabase.xsl -o:$JBOSS_HOME/standalone/configuration/standalone-ha.xml; rm $JBOSS_HOME/changeDatabase.xsl

ADD http://search.maven.org/remotecontent?filepath=org/apache/activemq/activemq-rar/5.13.3/activemq-rar-5.13.3.rar $JBOSS_HOME/standalone/deployments/activemq-rar-5.13.3.rar
USER root
RUN chown jboss $JBOSS_HOME/standalone/deployments/activemq-rar-5.13.3.rar
USER jboss

ADD notification-webapp-*.war /opt/jboss/wildfly/standalone/deployments/