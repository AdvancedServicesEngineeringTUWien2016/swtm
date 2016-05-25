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

public class CsConstants {
    
    public final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
    
    public final static byte TYPE_WHOLENUMBER = 1;
    public final static byte TYPE_DECIMALNUMBER = 2;
    public final static byte TYPE_BINARY = 3;
    public final static byte TYPE_DATE = 4;
    public final static byte TYPE_TEXT = 5;
    
    public final static int RANGE_SIZE = Short.MAX_VALUE;
    
    public static final String CLUSTER_NAME = "TestCluster";
    public static final String CASSANDRA_URI = "localhost:9160";
    public static final String CASSANDRA_CQL_URI = "localhost";
    public static final String KEYSPACE_NAME = "cs";
    
    public static String NAME_SEPERATOR = ":";
    
    
}
