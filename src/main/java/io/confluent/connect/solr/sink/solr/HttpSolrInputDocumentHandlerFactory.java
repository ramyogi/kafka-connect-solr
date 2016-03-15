package io.confluent.connect.solr.sink.solr;

import java.util.Map;

public class HttpSolrInputDocumentHandlerFactory extends SolrInputDocumentHandlerFactory {

  @Override
  public void initialize(Map<String, String> props) {

  }

  @Override
  protected SolrInputDocumentHandler create(String topic) {
    return null;
  }

  @Override
  public void close() throws Exception {

  }
}
