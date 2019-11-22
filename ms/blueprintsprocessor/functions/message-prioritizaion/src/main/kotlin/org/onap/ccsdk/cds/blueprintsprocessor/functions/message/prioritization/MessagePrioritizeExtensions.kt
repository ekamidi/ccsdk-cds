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

import org.onap.ccsdk.cds.blueprintsprocessor.functions.message.prioritization.db.MessagePrioritization
import org.onap.ccsdk.cds.blueprintsprocessor.functions.message.prioritization.service.MessagePrioritizationStateService
import org.onap.ccsdk.cds.blueprintsprocessor.services.execution.AbstractComponentFunction
import org.onap.ccsdk.cds.controllerblueprints.core.service.BluePrintDependencyService

/**
 * Register the MessagePrioritizationStateService and exposed dependency
 */
fun BluePrintDependencyService.messagePrioritizationStateService(): MessagePrioritizationStateService =
    instance(MessagePrioritizationStateService::class)

/**
 * Expose messagePrioritizationStateService to AbstractComponentFunction
 */
fun AbstractComponentFunction.messagePrioritizationStateService() =
    BluePrintDependencyService.messagePrioritizationStateService()

/**
 * MessagePrioritization correlation extensions
 */
fun MessagePrioritization.toFormatedCorrelation(): String {
    val ascendingKey = this.correlationId!!.split(",")
        .map { it.trim() }.sorted().joinToString(",")
    return ascendingKey
}

fun MessagePrioritization.toTypeNCorrelation(): TypeCorrelationKey {
    val ascendingKey = this.correlationId!!.split(",")
        .map { it.trim() }.sorted().joinToString(",")
    return TypeCorrelationKey(this.type, ascendingKey)
}
