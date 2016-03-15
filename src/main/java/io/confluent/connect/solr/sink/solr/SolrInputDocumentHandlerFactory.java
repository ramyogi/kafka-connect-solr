package io.confluent.connect.solr.sink.solr;

import java.util.HashMap;
import java.util.Map;

public abstract class SolrInputDocumentHandlerFactory implements AutoCloseable {
  public abstract void initialize(Map<String, String> props);
  protected abstract SolrInputDocumentHandler create(String topic);


  final Map<String, SolrInputDocumentHandler> topicToSolrInputDocumentHandler;

  public SolrInputDocumentHandlerFactory(){
    this.topicToSolrInputDocumentHandler = new HashMap<>();
  }

  public final SolrInputDocumentHandler get(String topic) {
    SolrInputDocumentHandler solrInputDocumentHandler = this.topicToSolrInputDocumentHandler.get(topic);

    if(null==solrInputDocumentHandler){
      solrInputDocumentHandler = create(topic);
      this.topicToSolrInputDocumentHandler.put(topic, solrInputDocumentHandler);
    }

    return solrInputDocumentHandler;
  }
}
