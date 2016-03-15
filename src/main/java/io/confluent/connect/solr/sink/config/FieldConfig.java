package io.confluent.connect.solr.sink.config;


import org.apache.kafka.common.config.AbstractConfig;
import org.apache.kafka.common.config.ConfigDef;

import java.util.Map;

public class FieldConfig extends AbstractConfig {
  static ConfigDef config = baseConfigDef();
  
  public static final String SOLR_FIELD_CONFIG = "field";
  private static final String SOLR_FIELD_DOC = "Name of the field in the solr schema.";

  public static final String SOLR_FIELD_CURRENCY_CONFIG = "currency";
  private static final String SOLR_FIELD_CURRENCY_DOC = "Currency code to use when writing data for field.";

  public static ConfigDef baseConfigDef() {
    return new ConfigDef()
        .define(SOLR_FIELD_CONFIG, ConfigDef.Type.STRING, ConfigDef.Importance.HIGH, SOLR_FIELD_DOC)
        .define(SOLR_FIELD_CURRENCY_CONFIG, ConfigDef.Type.STRING, null, ConfigDef.Importance.LOW, SOLR_FIELD_CURRENCY_DOC)
        ;
  }

  final String structField;

  public FieldConfig(String structField, Map<String, String> props) {
    super(config, props, false);
    this.structField = structField;
  }

  /**
   * Name of the field of the incoming struct.
   * @return
   */
  public String getStructField() {
    return structField;
  }

  /**
   * Name of the field in the solr schema.
   * @return Name of the field in the solr schema.
   */
  public String getSolrFieldName() {
    return this.getString(SOLR_FIELD_CONFIG);
  }

  /**
   * Currency code for the field.
   * @return
   */
  public String getSolrFieldCurrency() {
    return this.getString(SOLR_FIELD_CURRENCY_CONFIG);
  }
}
