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

public class HttpSolrSinkConnectorConfig extends SolrSinkConnectorConfig<HttpSolrSinkTopicConfig> {

  public static final String SOLR_URL_CONFIG = "solr.url";
  private static final String SOLR_URL_DOC = "Url to connect to solr with.";

  static ConfigDef configDef(){
    return baseConfigDef()
        .define(SOLR_URL_CONFIG, ConfigDef.Type.STRING, ConfigDef.Importance.HIGH, SOLR_URL_DOC)
        ;
  }


  protected HttpSolrSinkConnectorConfig(ConfigDef subclassConfigDef, Map<String, String> props) {
    super(subclassConfigDef, props);
  }

  @Override
  protected HttpSolrSinkTopicConfig createTopicConfig(Map props) {
    return new HttpSolrSinkTopicConfig(props);
  }

  public HttpSolrSinkConnectorConfig(Map<String, String> props) {
    this(configDef(),props);
  }

  public String getSolrUrl() {
    return this.getString(SOLR_URL_CONFIG);
  }
}
