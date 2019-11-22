/*
 * Copyright © 2018-2019 AT&T Intellectual Property.
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

package org.onap.ccsdk.cds.blueprintsprocessor.functions.message.prioritization

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan
open class MessagePrioritizationConfiguration

object MessagePrioritizationConstants {

    const val SOURCE_INPUT = "source-prioritization-input"

    const val PROCESSOR_PRIORITIZE = "processor-prioritization-prioritize"
    const val PROCESSOR_AGGREGATE = "processor-prioritization-aggregate"
    const val PROCESSOR_OUTPUT = "processor-prioritization-output"

    const val SINK_OUTPUT = "sink-prioritization-output"
    const val SINK_EXPIRED = "sink-prioritization-expired"
}
