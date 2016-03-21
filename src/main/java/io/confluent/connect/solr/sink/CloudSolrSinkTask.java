package io.confluent.connect.solr.sink;


import java.util.Map;

public class CloudSolrSinkTask extends SolrSinkTask {
  @Override
  protected SolrInputDocumentHandlerFactory getSolrInputDocumentHandlerFactory(Map<String, String> map) {
    CloudSolrInputDocumentHandlerFactory cloudSolrInputDocumentHandlerFactory = new CloudSolrInputDocumentHandlerFactory();
    cloudSolrInputDocumentHandlerFactory.initialize(map);
    return cloudSolrInputDocumentHandlerFactory;
  }
}
