<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="2.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:ds="urn:jboss:domain:datasources:4.0"
				xmlns:ee="urn:jboss:domain:ee:4.0"
				xmlns:ejb="urn:jboss:domain:ejb3:4.0"
				xmlns:ra="urn:jboss:domain:resource-adapters:4.0">

    <xsl:output method="xml" indent="yes"/>

	<xsl:template match="//ee:subsystem/ee:default-bindings">
		<ee:default-bindings context-service="java:jboss/ee/concurrency/context/default" managed-executor-service="java:jboss/ee/concurrency/executor/default" managed-scheduled-executor-service="java:jboss/ee/concurrency/scheduler/default" managed-thread-factory="java:jboss/ee/concurrency/factory/default"/>
	</xsl:template>
    <xsl:template match="//ds:subsystem/ds:datasources/ds:datasource[@jndi-name='java:jboss/datasources/ExampleDS']">
		<ds:datasource jndi-name="java:jboss/datasources/NotificationDS" pool-name="NotificationsDS" enabled="true" use-java-context="true">
			<ds:connection-url>jdbc:h2:mem:notifications;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE</ds:connection-url>
			<ds:driver>h2</ds:driver>
			<ds:security>
				<ds:user-name>sa</ds:user-name>
				<ds:password>sa</ds:password>
			</ds:security>
		</ds:datasource>
    </xsl:template>
	
	<xsl:template match="//ejb:subsystem">
		<xsl:copy>
			<xsl:apply-templates select="@* | *"/>
			<ejb:mdb>
				<ejb:resource-adapter-ref resource-adapter-name="activemq-rar.rar"/>
				<ejb:bean-instance-pool-ref pool-name="mdb-strict-max-pool"/>
			</ejb:mdb>
		</xsl:copy>
	</xsl:template>
	
	<xsl:template match="//ra:subsystem">
		<xsl:copy>
			<ra:resource-adapters>
                <ra:resource-adapter id="activemq-rar.rar">
                    <ra:archive>
                        activemq-rar-5.13.3.rar
                    </ra:archive>
                    <ra:transaction-support>XATransaction</ra:transaction-support>
                    <ra:config-property name="ServerUrl">
                        tcp://activemq-service:61616?jms.rmIdFromConnectionId=true
                    </ra:config-property>
					<config-property name="Password">
						admin
					</config-property>
					<config-property name="UserName">
						admin
					</config-property>
                    <ra:connection-definitions>
                        <ra:connection-definition class-name="org.apache.activemq.ra.ActiveMQManagedConnectionFactory" jndi-name="java:/ActiveMQConnectionFactory" enabled="true" pool-name="ConnectionFactory">
                            <ra:xa-pool>
                                <ra:min-pool-size>1</ra:min-pool-size>
                                <ra:max-pool-size>20</ra:max-pool-size>
                                <ra:prefill>false</ra:prefill>
                                <ra:is-same-rm-override>false</ra:is-same-rm-override>
                            </ra:xa-pool>
                        </ra:connection-definition>
                    </ra:connection-definitions>
                </ra:resource-adapter>
            </ra:resource-adapters>
		</xsl:copy>
	</xsl:template>

    <xsl:template match="@*|node()">
        <xsl:copy>
            <xsl:apply-templates select="@*|node()"/>
        </xsl:copy>
    </xsl:template>

</xsl:stylesheet>