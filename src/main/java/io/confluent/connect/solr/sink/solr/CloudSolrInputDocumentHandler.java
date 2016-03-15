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
