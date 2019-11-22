/*
 *  Copyright © 2019 IBM.
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

package org.onap.ccsdk.cds.controllerblueprints.core.service

import org.onap.ccsdk.cds.controllerblueprints.core.BluePrintProcessorException
import org.springframework.context.ApplicationContext
import kotlin.reflect.KClass

/**
 * Generic Bluepring Dependency Service, which will be used mainly in scripts.
 * This will be initialised only once during the Application startup.
 * Function modules, shall add their own dependency function names as an extension function.
 *
 * @author Brinda Santh
 */

object BluePrintDependencyService {

    lateinit var applicationContext: ApplicationContext

    fun inject(applicationContext: ApplicationContext) {
        BluePrintDependencyService.applicationContext = applicationContext
    }

    inline fun <reified T> instance(name: String): T {
        return applicationContext.getBean(name) as? T
            ?: throw BluePrintProcessorException("failed to get instance($name)")
    }

    inline fun <reified T> instance(type: Class<T>): T {
        return applicationContext.getBean(type)
            ?: throw BluePrintProcessorException("failed to get instance($type)")
    }

    inline fun <reified T> instance(type: KClass<*>): T {
        return applicationContext.getBean(type.java) as? T
            ?: throw BluePrintProcessorException("failed to get instance($type)")
    }
}
