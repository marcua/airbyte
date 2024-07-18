/*
 * Copyright (c) 2023 Airbyte, Inc., all rights reserved.
 */
package io.airbyte.cdk.integrations.base

import com.fasterxml.jackson.databind.JsonNode
import io.airbyte.cdk.integrations.base.adaptive.AdaptiveSourceRunner
import io.airbyte.commons.features.EnvVariableFeatureFlags
import io.airbyte.commons.features.FeatureFlags
import io.airbyte.protocol.models.v0.AirbyteConnectionStatus
import io.airbyte.protocol.models.v0.ConnectorSpecification

interface Integration {
    /**
     * Fetch the specification for the integration.
     *
     * @return specification.
     * @throws Exception
     * - any exception.
     */
    @Throws(Exception::class) fun spec(): ConnectorSpecification

    /**
     * Check whether, given the current configuration, the integration can connect to the
     * integration.
     *
     * @param config
     * - integration-specific configuration object as json. e.g. { "username": "airbyte",
     * "password": "super secure" }
     * @return Whether or not the connection was successful. Optional message if it was not.
     * @throws Exception
     * - any exception.
     */
    @Throws(Exception::class) fun check(config: JsonNode): AirbyteConnectionStatus?

    val featureFlags: FeatureFlags
    fun isCloudDeployment(): Boolean {
        return AdaptiveSourceRunner.CLOUD_MODE.equals(
            featureFlags.deploymentMode(),
            ignoreCase = true
        )
    }
}
