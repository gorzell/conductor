/**
 * Copyright 2016 Netflix, Inc.
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
package com.netflix.conductor.dao;

import java.util.List;
import java.util.Map;

import com.netflix.conductor.core.events.queue.Message;

/**
 * 
 * @author Viren
 * DAO responsible for managing queuing for the tasks.
 *
 */
public interface QueueDAO {

    /**
     *
     * @param queueName name of the queue
     * @param id message id
     * @param offsetTimeInSecond time in seconds, after which the message should be marked visible.  (for timed queues)
     */
    public void push(String queueName, String id, long offsetTimeInSecond);

    /**
     * @param queueName Name of the queue
     * @param messages messages to be pushed.
     */
    public void push(String queueName, List<Message> messages);

    /**
     *
     * @param queueName Name of the queue
     * @param id message id
     * @param offsetTimeInSecond time in seconds, after which the message should be marked visible.  (for timed queues)
     * @return true if the element was added to the queue.  false otherwise indicating the element already exists in the queue.
     */
    public boolean pushIfNotExists(String queueName, String id, long offsetTimeInSecond);

    /**
     *
     * @param queueName Name of the queue
     * @param count number of messages to be read from the queue
     * @param timeout timeout in milliseconds
     * @return list of elements from the named queue
     */
    public List<String> pop(String queueName, int count, int timeout);


    /**
     *
     * @param queueName Name of the queue
     * @param count number of messages to be read from the queue
     * @param timeout timeout in milliseconds
     * @return list of elements from the named queue
     */
    public List<Message> pollMessages(String queueName, int count, int timeout);

    /**
     *
     * @param queueName Name of the queue
     * @param messageId Message id
     */
    public void remove(String queueName, String messageId);

    /**
     *
     * @param queueName Name of the queue
     * @return size of the queue
     */
    public int getSize(String queueName);

    /**
     *
     * @param queueName Name of the queue
     * @param messageId Message Id
     * @return true if the message was found and ack'ed
     */
    public boolean ack(String queueName, String messageId);

    /**
     * Extend the lease of the unacknowledged message for longer period.
     * @param queueName Name of the queue
     * @param messageId Message Id
     * @param unackTimeout timeout in milliseconds for which the unack lease should be extended. (replaces the current value with this value)
     * @return true if the message was updated with extended lease.  false otherwise.
     */
    public boolean setUnackTimeout(String queueName, String messageId, long unackTimeout);

    /**
     *
     * @param queueName Name of the queue
     */
    public void flush(String queueName);

    /**
     *
     * @return key : queue name, value: size of the queue
     */
    public Map<String, Long> queuesDetail();

    /**
     *
     * @return key : queue name, value: map of shard name to size and unack queue size
     */
    public Map<String, Map<String, Map<String, Long>>> queuesDetailVerbose();

    public default void processUnacks(String queueName) {

    }

    /**
     * Sets the offset time without pulling out the message from the queue
     * @param queueName name of the queue
     * @param id message id
     * @param offsetTimeInSecond time in seconds, after which the message should be marked visible.  (for timed queues)
     * @return true if the message is in queue and the change was successful else returns false
     */
    public boolean setOffsetTime(String queueName, String id, long offsetTimeInSecond);


}
