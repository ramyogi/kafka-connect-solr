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


import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.connect.errors.ConnectException;
import org.apache.kafka.connect.sink.SinkRecord;
import org.apache.kafka.connect.sink.SinkTask;
import org.apache.solr.client.solrj.SolrServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class SolrSinkTask extends SinkTask {
  private static final Logger log = LoggerFactory.getLogger(SolrSinkTask.class);
  SolrInputDocumentHandlerFactory solrInputDocumentHandlerFactory;

  protected abstract SolrInputDocumentHandlerFactory getSolrInputDocumentHandlerFactory(Map<String, String> map);

  @Override
  public String version() {
    return VersionUtil.getVersion();
  }

  @Override
  public void start(Map<String, String> map) {
    this.solrInputDocumentHandlerFactory = getSolrInputDocumentHandlerFactory(map);
  }

  @Override
  public void put(Collection<SinkRecord> collection) {
    Set<SolrInputDocumentHandler> solrInputDocumentHandlers = new HashSet<>();

    for(SinkRecord sinkRecord:collection){
      SolrInputDocumentHandler solrInputDocumentHandler = this.solrInputDocumentHandlerFactory.get(sinkRecord.topic());
      solrInputDocumentHandlers.add(solrInputDocumentHandler);
      solrInputDocumentHandler.addRecord(sinkRecord);
    }

    for(SolrInputDocumentHandler solrInputDocumentHandler:solrInputDocumentHandlers){
      if(log.isDebugEnabled()){
        log.debug("flushing documents for {}", solrInputDocumentHandler.topic());
      }
      try {
        solrInputDocumentHandler.flush();
      } catch(IOException|SolrServerException ex){
        throw new ConnectException(
            String.format("Exception thrown while calling write to solr for topic '%s'", solrInputDocumentHandler.topic()),
            ex
        );
      }
    }
  }

  @Override
  public void flush(Map<TopicPartition, OffsetAndMetadata> map) {

  }

  @Override
  public void stop() {
    try {
      this.solrInputDocumentHandlerFactory.close();
    } catch (Exception ex){
      if(log.isErrorEnabled()){
        log.error("Exception thrown while calling solrInputDocumentHandlerFactory.close();", ex);
      }
    }
  }
}
