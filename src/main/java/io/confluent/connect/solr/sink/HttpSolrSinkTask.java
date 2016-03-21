package io.confluent.connect.solr.sink;


import java.util.Map;

public class HttpSolrSinkTask extends SolrSinkTask {
  @Override
  protected SolrInputDocumentHandlerFactory getSolrInputDocumentHandlerFactory(Map<String, String> map) {
    HttpSolrInputDocumentHandlerFactory httpSolrInputDocumentHandlerFactory = new HttpSolrInputDocumentHandlerFactory();
    httpSolrInputDocumentHandlerFactory.initialize(map);
    return httpSolrInputDocumentHandlerFactory;
  }
}
