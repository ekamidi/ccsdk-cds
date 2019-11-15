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

package org.onap.ccsdk.cds.blueprintsprocessor.functions.message.prioritization.service

import org.onap.ccsdk.cds.blueprintsprocessor.functions.message.prioritization.MessageState
import org.onap.ccsdk.cds.blueprintsprocessor.functions.message.prioritization.db.MessagePrioritization
import org.onap.ccsdk.cds.blueprintsprocessor.functions.message.prioritization.db.PrioritizationMessageRepository
import org.onap.ccsdk.cds.blueprintsprocessor.functions.message.prioritization.toFormatedCorrelation
import org.onap.ccsdk.cds.controllerblueprints.core.BluePrintProcessorException
import org.onap.ccsdk.cds.controllerblueprints.core.logger
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

interface MessagePrioritizationStateService {

    suspend fun saveMessage(message: MessagePrioritization): MessagePrioritization

    suspend fun getMessage(id: String): MessagePrioritization

    suspend fun getMessages(ids: List<String>): List<MessagePrioritization>?

    suspend fun getExpiryEligibleMessages(count: Int): List<MessagePrioritization>?

    suspend fun getMessageForStatesNotExpiredIn(group: String, states: List<String>, count: Int)
            : List<MessagePrioritization>?

    suspend fun getMessageForStatesExpired(group: String, states: List<String>, count: Int)
            : List<MessagePrioritization>?

    suspend fun getExpiredMessages(group: String, expiryDate: Date, count: Int): List<MessagePrioritization>?

    suspend fun getCorrelatedMessages(group: String, states: List<String>, types: List<String>?,
                                      correlationIds: String): List<MessagePrioritization>?

    suspend fun updateMessagesState(ids: List<String>, state: String)

    suspend fun updateMessageState(id: String, state: String): MessagePrioritization

    suspend fun setMessageState(id: String, state: String)

    suspend fun setMessagesState(ids: List<String>, state: String)

    suspend fun setMessageStateANdError(id: String, state: String, error: String)

    suspend fun setMessageStateAndAggregatedIds(id: String, state: String, aggregatedIds: List<String>)

    suspend fun deleteMessage(id: String)

    suspend fun deleteMessageByGroup(group: String)

    suspend fun deleteMessageStates(group: String, states: List<String>)

    suspend fun deleteExpiredMessage(group: String, retentionDays: Int)
}

@Service
open class MessagePrioritizationStateServiceImpl(
        private val prioritizationMessageRepository: PrioritizationMessageRepository)
    : MessagePrioritizationStateService {

    private val log = logger(MessagePrioritizationStateServiceImpl::class)

    @Transactional
    override suspend fun saveMessage(message: MessagePrioritization): MessagePrioritization {
        if (!message.correlationId.isNullOrBlank()) {
            message.correlationId = message.toFormatedCorrelation()
        }
        message.updatedDate = Date()
        return prioritizationMessageRepository.save(message)
    }

    override suspend fun getMessage(id: String): MessagePrioritization {
        return prioritizationMessageRepository.findById(id).orElseGet(null)
                ?: throw BluePrintProcessorException("couldn't find message for id($id)")
    }

    override suspend fun getMessages(ids: List<String>): List<MessagePrioritization>? {
        return prioritizationMessageRepository.findAllById(ids)
    }

    override suspend fun getExpiryEligibleMessages(count: Int): List<MessagePrioritization>? {
        return prioritizationMessageRepository
                .findByStateInAndExpiredDate(arrayListOf(MessageState.NEW.name, MessageState.WAIT.name),
                        Date(), PageRequest.of(0, count))
    }

    override suspend fun getMessageForStatesNotExpiredIn(group: String, states: List<String>, count: Int)
            : List<MessagePrioritization>? {
        return prioritizationMessageRepository.findByGroupAndStateInAndNotExpiredDate(group,
                states, Date(), PageRequest.of(0, count))
    }

    override suspend fun getMessageForStatesExpired(group: String, states: List<String>, count: Int)
            : List<MessagePrioritization>? {
        return prioritizationMessageRepository.findByGroupAndStateInAndExpiredDate(group,
                states, Date(), PageRequest.of(0, count))
    }

    override suspend fun getExpiredMessages(group: String, expiryDate: Date, count: Int)
            : List<MessagePrioritization>? {
        return prioritizationMessageRepository.findByByGroupAndExpiredDate(group,
                expiryDate, PageRequest.of(0, count))
    }

    override suspend fun getCorrelatedMessages(group: String, states: List<String>, types: List<String>?,
                                               correlationIds: String): List<MessagePrioritization>? {
        return if (!types.isNullOrEmpty()) {
            prioritizationMessageRepository.findByGroupAndTypesAndCorrelationId(group, states, types, correlationIds)
        } else {
            prioritizationMessageRepository.findByGroupAndCorrelationId(group, states, correlationIds)
        }
    }

    @Transactional
    override suspend fun updateMessagesState(ids: List<String>, state: String) {
        ids.forEach {
            val updated = updateMessageState(it, state)
            log.info("message($it) update to state(${updated.state})")
        }
    }

    @Transactional
    override suspend fun setMessageState(id: String, state: String) {
        prioritizationMessageRepository.setStateForMessageId(id, state, Date())
    }

    @Transactional
    override suspend fun setMessagesState(ids: List<String>, state: String) {
        prioritizationMessageRepository.setStateForMessageIds(ids, state, Date())
    }

    @Transactional
    override suspend fun setMessageStateANdError(id: String, state: String, error: String) {
        prioritizationMessageRepository.setStateAndErrorForMessageId(id, state, error, Date())
    }

    @Transactional
    override suspend fun updateMessageState(id: String, state: String): MessagePrioritization {
        val updateMessage = getMessage(id).apply {
            this.updatedDate = Date()
            this.state = state
        }
        return saveMessage(updateMessage)
    }

    @Transactional
    override suspend fun setMessageStateAndAggregatedIds(id: String, state: String, aggregatedIds: List<String>) {
        val groupedIds = aggregatedIds.joinToString(",")
        prioritizationMessageRepository.setStateAndAggregatedMessageIds(id, state, groupedIds, Date())
    }

    override suspend fun deleteMessage(id: String) {
        return prioritizationMessageRepository.deleteById(id)
    }

    override suspend fun deleteMessageByGroup(group: String) {
        return prioritizationMessageRepository.deleteGroup(group)
    }

    override suspend fun deleteMessageStates(group: String, states: List<String>) {
        return prioritizationMessageRepository.deleteGroupAndStateIn(group, states)
    }

    override suspend fun deleteExpiredMessage(group: String, retentionDays: Int) {
        return prioritizationMessageRepository.deleteGroupAndStateIn(group,
                arrayListOf(MessageState.EXPIRED.name))
    }
}