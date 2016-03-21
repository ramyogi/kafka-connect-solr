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
package io.confluent.connect.solr.sink;

import io.confluent.connect.solr.sink.config.HttpSolrSinkConnectorConfig;
import io.confluent.connect.solr.sink.config.HttpSolrSinkTopicConfig;
import org.apache.kafka.connect.errors.ConnectException;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.ConcurrentUpdateSolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

class HttpSolrInputDocumentHandlerFactory extends SolrInputDocumentHandlerFactory {
  private static final Logger log = LoggerFactory.getLogger(HttpSolrInputDocumentHandlerFactory.class);
  HttpSolrSinkConnectorConfig httpSolrSinkConnectorConfig;

  @Override
  public void initialize(Map<String, String> props) {
    this.httpSolrSinkConnectorConfig = new HttpSolrSinkConnectorConfig(props);
  }

  @Override
  protected SolrInputDocumentHandler create(String topic) {
    HttpSolrSinkTopicConfig topicConfig = this.httpSolrSinkConnectorConfig.getTopicConfig(topic);
    SolrInputDocumentConverter solrInputDocumentConverter = topicConfig.getSolrInputDocumentConverter();
    String coreName = topicConfig.getCoreName();
    String coreUrl;
    try {
      URL baseUrl = new URL(this.httpSolrSinkConnectorConfig.getSolrUrl());
      coreUrl = new URL(baseUrl,coreName).toString();
    } catch (MalformedURLException e) {
      throw new ConnectException("Invalid Solr Url", e);
    }

    if(log.isInfoEnabled()) {
      log.info("Configuring topic '{}' to url '{}'", topicConfig.getCoreName(), coreUrl);
    }

    SolrClient solrClient = new HttpSolrClient(coreUrl);
    return new HttpSolrInputDocumentHandler(topicConfig, solrClient, solrInputDocumentConverter);
  }

  @Override
  public void close() throws Exception {

  }
}
