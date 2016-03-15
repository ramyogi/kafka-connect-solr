package io.confluent.connect.solr.sink.config;

import org.apache.kafka.common.config.ConfigDef;

import java.util.Map;

public class HttpSolrSinkTopicConfig extends SolrSinkTopicConfig {

  public static final String COLLECTION_NAME_CONFIG = "collection.name";
  private static final String COLLECTION_NAME_DOC = "Name of the solr collection to write to.";

  static ConfigDef configDef(){
    return baseConfigDef()
        .define(COLLECTION_NAME_CONFIG, ConfigDef.Type.STRING, ConfigDef.Importance.HIGH, COLLECTION_NAME_DOC)
        ;
  }

  public String getCollectionName(){
    return this.getString(COLLECTION_NAME_CONFIG);
  }

  protected HttpSolrSinkTopicConfig(ConfigDef subclassConfigDef, Map<String, String> props) {
    super(subclassConfigDef, props);
  }

  public HttpSolrSinkTopicConfig(Map<String, String> props) {
    this(configDef(), props);
  }

}
