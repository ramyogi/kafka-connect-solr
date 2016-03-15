package io.confluent.connect.solr.sink.config;

import org.apache.kafka.common.config.ConfigDef;

import java.util.Map;

public class HttpSolrInputDocumentHandlerConfig extends SolrInputDocumentHandlerConfig {



  protected HttpSolrInputDocumentHandlerConfig(ConfigDef subclassConfigDef, Map<String, String> props) {
    super(subclassConfigDef, props);
  }

  @Override
  protected SolrSinkTopicConfig createTopicConfig(Map props) {
    return null;
  }

  public HttpSolrInputDocumentHandlerConfig(Map<String, String> props) {
    this(config,props);
  }


}
