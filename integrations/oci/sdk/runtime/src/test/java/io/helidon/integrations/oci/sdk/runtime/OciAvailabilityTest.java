/*
 * Copyright (c) 2023, 2024 Oracle and/or its affiliates.
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

package io.helidon.integrations.oci.sdk.runtime;

import java.util.Objects;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OciAvailabilityTest {

    @Test
    void isRunningOnOci() {
        OciConfig ociConfigBean = Objects.requireNonNull(OciExtension.ociConfig());
        assertThat(OciAvailabilityDefault.runningOnOci(ociConfigBean),
                                 is(false));
    }

    @Test
    void getValidOpcPath() {
        assertThat(OciAvailabilityDefault.getOpcPath("http://169.254.169.254/opc/v2/"),
                   is("/opc/v2/"));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "http169.254.169.254opcv2",
            "http://169.254.169.254",
    })
    void getInValidOpcPath(String metadataServiceBaseURL) {
        Exception e = assertThrows(IllegalStateException.class, () -> {
            OciAvailabilityDefault.getOpcPath(metadataServiceBaseURL);
        });
        String expectedMessage = "Unable to find opc path from '" + metadataServiceBaseURL + "'";
        assertThat(e.getMessage(), is(expectedMessage));
    }
}
