<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet version="2.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
				xmlns:ee="urn:jboss:domain:ee:4.0">

    <xsl:output method="xml" indent="yes"/>

	<xsl:template match="//ee:subsystem/ee:spec-descriptor-property-replacement">
		<ee:spec-descriptor-property-replacement>true</ee:spec-descriptor-property-replacement>
	</xsl:template>

    <xsl:template match="@*|node()">
        <xsl:copy>
            <xsl:apply-templates select="@*|node()"/>
        </xsl:copy>
    </xsl:template>

</xsl:stylesheet>