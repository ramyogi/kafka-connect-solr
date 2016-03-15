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

import io.confluent.connect.solr.sink.solr.SolrInputDocumentHandlerFactory;
import org.apache.kafka.common.config.AbstractConfig;
import org.apache.kafka.common.config.ConfigDef;

import java.util.Map;

/**
 *
 */
public class SolrSinkTaskConfig extends AbstractConfig {
  static ConfigDef config = baseConfigDef();

  public static final String SOLR_INPUT_DOCUMENT_HANDLER_CLASS_CONFIG = "solr.input.document.handler.class";
  private static final String SOLR_INPUT_DOCUMENT_HANDLER_CLASS_DOC = "Factory class used to get the SolrClient implementation.";

  public static ConfigDef baseConfigDef() {
    return new ConfigDef()
        .define(SOLR_INPUT_DOCUMENT_HANDLER_CLASS_CONFIG, ConfigDef.Type.CLASS, ConfigDef.Importance.HIGH, SOLR_INPUT_DOCUMENT_HANDLER_CLASS_DOC)
        ;
  }

  public SolrSinkTaskConfig(Map<String, String> props) {
    this(config, props);
  }

  protected SolrSinkTaskConfig(ConfigDef subclassConfigDef, Map<String, String> props) {
    super(subclassConfigDef, props);
  }

  /**
   * Factory class used to get the SolrClient implementation.
   * @return
   */
  public SolrInputDocumentHandlerFactory getSolrInputDocumentFactory(){
    return this.getConfiguredInstance(SOLR_INPUT_DOCUMENT_HANDLER_CLASS_CONFIG, SolrInputDocumentHandlerFactory.class);
  }
}
