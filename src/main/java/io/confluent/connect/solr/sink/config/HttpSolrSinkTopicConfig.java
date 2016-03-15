/**
 * Copyright (C) 2016 Jeremy Custenborder (jcustenborder@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.confluent.connect.solr.sink.config;

import org.apache.kafka.common.config.ConfigDef;

import java.util.Map;

public class HttpSolrSinkTopicConfig extends SolrSinkTopicConfig {

  public static final String COLLECTION_NAME_CONFIG = "collection.name";
  private static final String COLLECTION_NAME_DOC = "Name of the solr collection to write to.";

  static ConfigDef configDef(){
    return baseConfigDef()
        .define(COLLECTION_NAME_CONFIG, ConfigDef.Type.STRING, ConfigDef.Importance.HIGH, COLLECTION_NAME_DOC)
        ;
  }

  public String getCollectionName(){
    return this.getString(COLLECTION_NAME_CONFIG);
  }

  protected HttpSolrSinkTopicConfig(ConfigDef subclassConfigDef, Map<String, String> props) {
    super(subclassConfigDef, props);
  }

  public HttpSolrSinkTopicConfig(Map<String, String> props) {
    this(configDef(), props);
  }

}
