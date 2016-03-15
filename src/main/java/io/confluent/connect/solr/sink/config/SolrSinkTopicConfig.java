package io.confluent.connect.solr.sink.config;

import io.confluent.connect.solr.sink.solr.DefaultSolrInputDocumentConverter;
import io.confluent.connect.solr.sink.solr.SolrInputDocumentConverter;
import org.apache.kafka.common.config.AbstractConfig;
import org.apache.kafka.common.config.ConfigDef;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class SolrSinkTopicConfig extends AbstractConfig {
  static ConfigDef config = baseConfigDef();

  public static final String TOPIC_CONFIG = "topic";
  private static final String TOPIC_DOC = "Kafka topic";

  public static final String SOLR_INPUT_DOCUMENT_CONVERTER_CLASS_CONFIG = "solr.input.document.converter.class";
  private static final String SOLR_INPUT_DOCUMENT_CONVERTER_CLASS_DOC = "Factory class used to get the SolrClient implementation.";

  public static final String SOLR_COMMIT_WITHIN_CONFIG = "commit.within";
  private static final String SOLR_COMMIT_WITHIN_DOC = "Configures Solr UpdaterRequest for a commit within the requested number of milliseconds .";
  public static final String COLUMN_IGNORE_UNKNOWN_FIELDS_CONFIG = "column.ignore.unknown.fields";
  private static final String COLUMN_IGNORE_UNKNOWN_FIELDS_DOC = "Flag to determine if the connector should raise an exception when it encountered a field it doesn't have configured.";


  public static ConfigDef baseConfigDef() {
    return new ConfigDef()
        .define(TOPIC_CONFIG, ConfigDef.Type.STRING, ConfigDef.Importance.HIGH, TOPIC_DOC)
        .define(SOLR_INPUT_DOCUMENT_CONVERTER_CLASS_CONFIG, ConfigDef.Type.CLASS, DefaultSolrInputDocumentConverter.class.getName(), ConfigDef.Importance.HIGH, SOLR_INPUT_DOCUMENT_CONVERTER_CLASS_DOC)
        .define(SOLR_COMMIT_WITHIN_CONFIG, ConfigDef.Type.INT, null, ConfigDef.Importance.LOW, SOLR_COMMIT_WITHIN_DOC)
        .define(COLUMN_IGNORE_UNKNOWN_FIELDS_CONFIG, ConfigDef.Type.BOOLEAN, false, ConfigDef.Importance.LOW, COLUMN_IGNORE_UNKNOWN_FIELDS_DOC)
        ;
  }

  final List<FieldConfig> fieldConfigs;

  protected SolrSinkTopicConfig(ConfigDef subclassConfigDef, Map<String, String> props) {
    super(subclassConfigDef, props);
    this.fieldConfigs = loadFieldConfigs();
  }

  public SolrInputDocumentConverter getSolrInputDocumentFactory(){
    SolrInputDocumentConverter solrInputDocumentConverter = this.getConfiguredInstance(SOLR_INPUT_DOCUMENT_CONVERTER_CLASS_CONFIG, SolrInputDocumentConverter.class);
    solrInputDocumentConverter.configure(this);
    return solrInputDocumentConverter;
  }

  public String getTopic() {
    return this.getString(TOPIC_CONFIG);
  }

  public Integer getCommitWithin() {
    return this.getInt(SOLR_COMMIT_WITHIN_CONFIG);
  }

  private List<FieldConfig> loadFieldConfigs(){
    Pattern pattern = Pattern.compile("^column\\.mappings\\.(.+)\\.");

    Map<String, String> prefixes = new LinkedHashMap<>();

    Map<String, String> input = originalsStrings();

    for(Map.Entry<String, String> kvp:input.entrySet()) {
      Matcher matcher = pattern.matcher(kvp.getKey());

      if(!matcher.find()){
        continue;
      }

      String prefix = matcher.group(0);
      String sourceColumn = matcher.group(1);
      prefixes.put(sourceColumn, prefix);
    }

    List<FieldConfig> fieldConfigs = new ArrayList<>();

    for(Map.Entry<String,String> prefix:prefixes.entrySet()){
      Map<String, Object> prefixedOriginals = this.originalsWithPrefix(prefix.getValue());
      Map<String, String> stringOriginals = new LinkedHashMap<>();
      for(Map.Entry<String, Object> kvp:prefixedOriginals.entrySet()){
        stringOriginals.put(kvp.getKey(), kvp.getValue().toString());
      }
      FieldConfig fieldConfig = new FieldConfig(prefix.getKey(), stringOriginals);
      fieldConfigs.add(fieldConfig);
    }
    return fieldConfigs;
  }

  public List<FieldConfig> getFieldConfigs(){
    return this.fieldConfigs;
  }

  public boolean ignoreUnknownFields(){
    return this.getBoolean(COLUMN_IGNORE_UNKNOWN_FIELDS_CONFIG);
  }
}
