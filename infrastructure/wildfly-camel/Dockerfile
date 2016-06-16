FROM wildflyext/wildfly-camel

# Install PostgreSQL driver
RUN mkdir -p $JBOSS_HOME/modules/system/layers/base/org/postgresql/main
ADD postgres/postgresql-9.4-1201.jdbc41.jar $JBOSS_HOME/modules/system/layers/base/org/postgresql/main/
ADD module.xml $JBOSS_HOME/modules/system/layers/base/org/postgresql/main/

ADD enableDescriptorPropertyReplacement.xsl $JBOSS_HOME/
RUN java -jar /usr/share/java/saxon.jar -s:$JBOSS_HOME/standalone/configuration/standalone-camel.xml -xsl:$JBOSS_HOME/enableDescriptorPropertyReplacement.xsl -o:$JBOSS_HOME/standalone/configuration/standalone-camel.xml; java -jar /usr/share/java/saxon.jar -s:$JBOSS_HOME/standalone/configuration/standalone-ha.xml -xsl:$JBOSS_HOME/enableDescriptorPropertyReplacement.xsl -o:$JBOSS_HOME/standalone/configuration/standalone-ha.xml; rm $JBOSS_HOME/enableDescriptorPropertyReplacement.xsl

ADD standalone.conf $JBOSS_HOME/bin/
ADD mgmt-users.properties $JBOSS_HOME/standalone/configuration