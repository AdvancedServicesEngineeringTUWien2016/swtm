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
package at.ac.tuwien.swtm.analytics.mainspring.client.adapter;

import java.util.logging.Logger;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="compileresult")
public class ScriptCompileResultAdapter {
    
    private boolean result = false;
    private String message;
    private static final Logger logger =
       Logger.getLogger(ScriptCompileResultAdapter.class.getName());

    
    public ScriptCompileResultAdapter() {
    }
    
    public ScriptCompileResultAdapter(boolean result, String message) {
        this.result = result;
        this.message = message;
    }
    
    @XmlElement (name = "result")
    public boolean getResult() {
        return result;
    }
       
    @XmlElement (name = "message")
    public String getMessage() {
        return message;
    }
    
}
