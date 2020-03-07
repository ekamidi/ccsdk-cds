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

package org.onap.ccsdk.error.catalog.core

import org.slf4j.LoggerFactory
import org.slf4j.helpers.MessageFormatter
import kotlin.reflect.KClass

fun <T : Any> logger(clazz: T) = LoggerFactory.getLogger(clazz.javaClass)!!

fun <T : KClass<*>> logger(clazz: T) = LoggerFactory.getLogger(clazz.java)!!

fun format(message: String, vararg args: Any?): String {
    if (args != null && args.isNotEmpty()) {
        return MessageFormatter.arrayFormat(message, args).message
    }
    return message
}