package io.confluent.connect.solr.sink.solr;

import io.confluent.connect.solr.sink.config.HttpSolrSinkTopicConfig;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.request.UpdateRequest;

class HttpSolrInputDocumentHandler  extends SolrInputDocumentHandler {
  public HttpSolrInputDocumentHandler(HttpSolrSinkTopicConfig topicConfig, SolrClient solrClient, SolrInputDocumentConverter solrInputDocumentConverter) {
    super(topicConfig, solrClient, solrInputDocumentConverter);
  }

  @Override
  protected void beforeFlush(UpdateRequest inputDocuments) {

  }
}
