/*
 *  Copyright © 2017-2018 AT&T Intellectual Property.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.onap.ccsdk.cds.blueprintsprocessor.designer.api.domain

import com.fasterxml.jackson.databind.JsonNode
import org.onap.ccsdk.cds.controllerblueprints.core.utils.JacksonUtils
import javax.persistence.AttributeConverter
import javax.persistence.Converter

/**
 * @author Brinda Santh
 */
@Converter
class JpaJsonNodeConverter : AttributeConverter<JsonNode, String> {

    override fun convertToDatabaseColumn(node: JsonNode): String {
        return JacksonUtils.getJson(node, true)
    }

    override fun convertToEntityAttribute(dbData: String): JsonNode {
        return JacksonUtils.jsonNode(dbData)
    }
}
