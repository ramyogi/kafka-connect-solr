package io.confluent.connect.solr.sink.config;

import io.confluent.connect.solr.sink.solr.SolrInputDocumentHandlerFactory;
import org.apache.kafka.common.config.AbstractConfig;
import org.apache.kafka.common.config.ConfigDef;

import java.util.Map;

/**
 *
 */
public class SolrSinkTaskConfig extends AbstractConfig {
  static ConfigDef config = baseConfigDef();

  public static final String SOLR_INPUT_DOCUMENT_HANDLER_CLASS_CONFIG = "solr.input.document.handler.class";
  private static final String SOLR_INPUT_DOCUMENT_HANDLER_CLASS_DOC = "Factory class used to get the SolrClient implementation.";

  public static ConfigDef baseConfigDef() {
    return new ConfigDef()
        .define(SOLR_INPUT_DOCUMENT_HANDLER_CLASS_CONFIG, ConfigDef.Type.CLASS, ConfigDef.Importance.HIGH, SOLR_INPUT_DOCUMENT_HANDLER_CLASS_DOC)
        ;
  }

  public SolrSinkTaskConfig(Map<String, String> props) {
    this(config, props);
  }

  protected SolrSinkTaskConfig(ConfigDef subclassConfigDef, Map<String, String> props) {
    super(subclassConfigDef, props);
  }

  /**
   * Factory class used to get the SolrClient implementation.
   * @return
   */
  public SolrInputDocumentHandlerFactory getSolrInputDocumentFactory(){
    return this.getConfiguredInstance(SOLR_INPUT_DOCUMENT_HANDLER_CLASS_CONFIG, SolrInputDocumentHandlerFactory.class);
  }
}
