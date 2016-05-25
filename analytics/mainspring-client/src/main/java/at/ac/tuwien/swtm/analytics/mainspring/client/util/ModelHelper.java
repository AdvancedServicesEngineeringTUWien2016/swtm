/*
 * Copyright (C) 2011-2015 M2MLabs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package at.ac.tuwien.swtm.analytics.mainspring.client.util;

import java.util.TimeZone;
import java.util.logging.Logger;

public class ModelHelper {

    private static final Logger logger = Logger.getLogger(ModelHelper.class.getName());

    /**
     * return the server timezone
     *
     * @return timezone
     */
    public static TimeZone getServerTimeZone() {
        String timezone = System.getProperty("SERVER_TIMEZONE");
        if (timezone == null) {
            return TimeZone.getDefault();
        } else {
            return TimeZone.getTimeZone(timezone);
        }
    }

    public enum Status {

        OK, CREATED, ACCEPTED, NO_CONTENT, MOVED_PERMANENTLY, SEE_OTHER, NOT_MODIFIED, TEMPORARY_REDIRECT, BAD_REQUEST, UNAUTHORIZED, FORBIDDEN, NOT_FOUND, NOT_ACCEPTABLE, CONFLICT, GONE, PRECONDITION_FAILED, UNSUPPORTED_MEDIA_TYPE, INTERNAL_SERVER_ERROR, SERVICE_UNAVAILABLE;
    }

    public enum Datatype {

        Boolean, Date, Number, GpsData, Text;
    }

    public enum CommandStatus {

        New, Sent, Ok, Failed;
    }

    public enum ScriptType {

        HttpConnectorScript, MomentProcessingScript, SocketConnectorScript, Unknown, UtilityScript;
    }

    public enum EventFilterOperator {

        EQ, GT, LT, LIKE;
    }
}
