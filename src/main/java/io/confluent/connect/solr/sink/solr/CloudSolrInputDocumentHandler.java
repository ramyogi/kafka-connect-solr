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
package io.confluent.connect.solr.sink.solr;

import io.confluent.connect.solr.sink.config.CloudSolrSinkTopicConfig;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.request.UpdateRequest;

class CloudSolrInputDocumentHandler extends SolrInputDocumentHandler {
  final CloudSolrSinkTopicConfig cloudSolrSinkTopicConfig;
  final String collection;

  public String getCollection() {
    return collection;
  }

  public CloudSolrInputDocumentHandler(CloudSolrSinkTopicConfig topicConfig, SolrClient solrClient, SolrInputDocumentConverter solrInputDocumentConverter, CloudSolrSinkTopicConfig cloudSolrSinkTopicConfig) {
    super(topicConfig, solrClient, solrInputDocumentConverter);
    this.cloudSolrSinkTopicConfig = cloudSolrSinkTopicConfig;
    this.collection = this.cloudSolrSinkTopicConfig.getCollectionName();
  }

  @Override
  protected void beforeFlush(UpdateRequest updateRequest) {
    updateRequest.setParam("collection", this.collection);
  }
}
