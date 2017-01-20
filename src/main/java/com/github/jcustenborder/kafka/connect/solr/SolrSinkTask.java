/**
 * Copyright © 2016 Jeremy Custenborder (jcustenborder@gmail.com)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.jcustenborder.kafka.connect.solr;


import org.apache.kafka.connect.errors.RetriableException;
import org.apache.kafka.connect.sink.SinkRecord;
import org.apache.kafka.connect.sink.SinkTask;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;

public abstract class SolrSinkTask<T extends SolrSinkConnectorConfig> extends SinkTask {
  private static final Logger log = LoggerFactory.getLogger(SolrSinkTask.class);
  protected T config;

  protected abstract T config(Map<String, String> settings);

  protected abstract SolrClient client();

  protected SolrClient client;

  @Override
  public void start(Map<String, String> settings) {
    log.info("Starting");
    this.config = config(settings);
    log.info("Creating Solr client.");
    this.client = client();
    log.info("Created Solr client {}.", this.client);
  }

  @Override
  public void put(Collection<SinkRecord> collection) {
    UpdateRequest updateRequest = new UpdateRequest();

    if (this.config.useBasicAuthentication) {
      log.trace("Configuring UpdateRequest to use basic authentication. Username = '{}'", this.config.username);
      updateRequest.setBasicAuthCredentials(this.config.username, this.config.password);
    }

    int count = 0;

    for (SinkRecord record : collection) {
      SolrInputDocument solrInputDocument = SolrInputDocumentBuilder.build(record);
      updateRequest.add(solrInputDocument);
      count++;
    }

    try {
      log.trace("Sending {} documents to solr.", count);
      UpdateResponse response = updateRequest.process(this.client);
      if (null != response && log.isTraceEnabled()) {
        log.trace("ElapsedTime = {} QTime = {}", response.getElapsedTime(), response.getQTime());
      }
    } catch (SolrServerException e) {
      throw new RetriableException("Exception thrown while processing request", e);
    } catch (IOException e) {
      throw new RetriableException("Exception thrown while processing request", e);
    }
  }

  @Override
  public void stop() {
    try {
      this.client.close();
    } catch (IOException e) {
      log.error("Exception thrown while closing client.", e);
    }
  }

  @Override
  public String version() {
    return VersionUtil.getVersion();
  }
}
