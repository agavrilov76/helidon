/*
 * Copyright (c) 2023 Oracle and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.helidon.nima.webclient.api;

import java.util.List;

import io.helidon.builder.api.Prototype;
import io.helidon.config.metadata.Configured;
import io.helidon.config.metadata.ConfiguredOption;
import io.helidon.inject.configdriven.api.ConfigBean;
import io.helidon.nima.webclient.spi.ProtocolConfig;
import io.helidon.nima.webclient.spi.ProtocolConfigProvider;

/**
 * Web client configuration.
 */
@Configured(root = true, prefix = "clients")
@ConfigBean(repeatable = true, wantDefault = true)
@Prototype.Blueprint
interface WebClientConfigBlueprint extends HttpClientConfigBlueprint, Prototype.Factory<WebClient> {
    /**
     * Configuration of client protocols.
     *
     * @return client protocol configurations
     */
    @ConfiguredOption(provider = true, providerType = ProtocolConfigProvider.class)
    @Prototype.Singular
    List<ProtocolConfig> protocolConfigs();

    /**
     * List of HTTP protocol IDs by order of preference. If left empty, all discovered providers will be used, ordered by
     * weight.
     * <p>
     * For example if both HTTP/2 and HTTP/1.1 providers are available (considering HTTP/2 has higher weights), for ALPN
     * we will send h2 and http/1.1 and decide based on response.
     * If TLS is not used, we would attempt an upgrade (or use prior knowledge if configured in {@link #protocolConfigs()}).
     *
     * @return list of HTTP protocol IDs in order of preference
     */
    @Prototype.Singular
    List<String> protocolPreference();
}