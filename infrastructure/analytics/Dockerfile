FROM wildfly-camel

ADD changeDatabase.xsl $JBOSS_HOME/
RUN java -jar /usr/share/java/saxon.jar -s:$JBOSS_HOME/standalone/configuration/standalone-camel.xml -xsl:$JBOSS_HOME/changeDatabase.xsl -o:$JBOSS_HOME/standalone/configuration/standalone-camel.xml; java -jar /usr/share/java/saxon.jar -s:$JBOSS_HOME/standalone/configuration/standalone-ha.xml -xsl:$JBOSS_HOME/changeDatabase.xsl -o:$JBOSS_HOME/standalone/configuration/standalone-ha.xml; rm $JBOSS_HOME/changeDatabase.xsl

ADD analytics-webapp-*.war /opt/jboss/wildfly/standalone/deployments/