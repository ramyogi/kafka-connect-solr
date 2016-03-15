package io.confluent.connect.solr.sink.config;

import org.apache.kafka.common.config.ConfigDef;

import java.util.List;
import java.util.Map;

public class CloudSolrInputDocumentHandlerConfig extends SolrInputDocumentHandlerConfig<CloudSolrSinkTopicConfig> {

  public static final String ZOOKEEPER_HOSTS_CONFIG = "solr.zookeeper.hosts";
  private static final String ZOOKEEPER_HOSTS_DOC = "Zookeeper hosts that are used to store solr configuration.";
  public static final String ZOOKEEPER_CHROOT_CONFIG = "solr.zookeeper.chroot";
  private static final String ZOOKEEPER_CHROOT_DOC = "Chroot within solr for the zookeeper configuration.";

  static ConfigDef configDef(){
    return baseConfigDef()
        .define(ZOOKEEPER_HOSTS_CONFIG, ConfigDef.Type.LIST, ConfigDef.Importance.HIGH, ZOOKEEPER_HOSTS_DOC)
        .define(ZOOKEEPER_CHROOT_CONFIG, ConfigDef.Type.STRING, null, ConfigDef.Importance.HIGH, ZOOKEEPER_CHROOT_DOC)
        ;
  }

  public List<String> getZookeeperHosts(){
    return this.getList(ZOOKEEPER_HOSTS_CONFIG);
  }

  public String getZookeeperChroot(){
    return this.getString(ZOOKEEPER_CHROOT_CONFIG);
  }

  protected CloudSolrInputDocumentHandlerConfig(ConfigDef subclassConfigDef, Map<String, String> props) {
    super(subclassConfigDef, props);
  }

  @Override
  protected CloudSolrSinkTopicConfig createTopicConfig(Map<String, String> props) {
    return new CloudSolrSinkTopicConfig(props);
  }


  public CloudSolrInputDocumentHandlerConfig(Map<String, String> props) {
    this(configDef(), props);
  }

}
