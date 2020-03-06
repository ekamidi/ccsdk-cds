/*
 *  Copyright © 2020 IBM, Bell Canada.
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

package org.onap.ccsdk.error.catalog.services.repository

import org.onap.ccsdk.error.catalog.services.domain.Domain
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * @param <T> Model
 */
@Repository
interface DomainRepository : JpaRepository<Domain, String> {

    /**
     * This is a findByNameAndApplicationId method
     *
     * @param name name
     * @param applicationId applicationId
     * @return Optional<T>
     */
    fun findByNameAndApplicationId(name: String, applicationId: String): Domain?

    /**
     * This is a findAllByApplicationId method
     *
     * @param applicationId applicationId
     * @return List<T>
     */
    fun findAllByApplicationId(applicationId: String): List<Domain>
}
