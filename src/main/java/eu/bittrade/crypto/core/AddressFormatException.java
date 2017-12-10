/*
 * Copyright 2011 Google Inc.
 * Copyright 2015 Andreas Schildbach
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package eu.bittrade.crypto.core;

/**
 * <code>AddressFormatExceptions</code> are thrown in case an address does not
 * has the expected format.
 */
public class AddressFormatException extends IllegalArgumentException {
    /** Generated serial UID. */
    private static final long serialVersionUID = -6999431742004892343L;

    /** Initiate a new, empty <code>AddressFormatException</code>. */
    public AddressFormatException() {
        super();
    }

    /**
     * Initiate a new, empty <code>AddressFormatException</code> including a
     * message.
     * 
     * @param message
     *            The message to set.
     */
    public AddressFormatException(String message) {
        super(message);
    }
}