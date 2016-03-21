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

import io.confluent.connect.solr.sink.config.SolrSinkTopicConfig;
import org.apache.kafka.connect.sink.SinkRecord;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

abstract class SolrInputDocumentHandler implements AutoCloseable {
  private static final Logger log = LoggerFactory.getLogger(SolrInputDocumentHandler.class);

  private final String topic;
  private final UpdateRequest updateRequest;
  private final SolrInputDocumentConverter solrInputDocumentConverter;

  protected final SolrClient solrClient;

  public SolrInputDocumentHandler(SolrSinkTopicConfig topicConfig, SolrClient solrClient, SolrInputDocumentConverter solrInputDocumentConverter) {
    this.topic = topicConfig.getTopic();
    this.solrClient = solrClient;
    this.solrInputDocumentConverter = solrInputDocumentConverter;
    this.updateRequest = new UpdateRequest();

    if(null!=topicConfig.getCommitWithin()){
      if(log.isInfoEnabled()){
        log.info("Setting CommitWithin for UpdateRequest to {} ms for {}.", topicConfig.getCommitWithin(), this.topic);
      }
      this.updateRequest.setCommitWithin(topicConfig.getCommitWithin());
    }
  }

  public String topic() {
    return this.topic;
  }

  protected abstract void beforeFlush(UpdateRequest inputDocuments);

  public void addRecord(SinkRecord record){
    SolrInputDocument solrInputDocument = this.solrInputDocumentConverter.convert(record);
    this.updateRequest.add(solrInputDocument);
  }

  @Override
  public void close() throws Exception {
    this.solrClient.close();
  }

  public void flush() throws IOException, SolrServerException {
    int documentsToFlush = this.updateRequest.getDocuments().size();

    if(log.isDebugEnabled()){
      log.debug("Writing {} document(s) for topic '{}' to solr.", documentsToFlush, this.topic);
    }

    beforeFlush(this.updateRequest);
    UpdateResponse updateResponse = updateRequest.process(this.solrClient);

    if(log.isInfoEnabled()){
      log.info("Wrote {} documents(s) for topic '{}' to solr in {} ms", documentsToFlush, this.topic, updateResponse.getElapsedTime());
    }

    this.updateRequest.clear();
  }
}
