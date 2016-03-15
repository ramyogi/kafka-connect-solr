package io.confluent.connect.solr.sink.solr;

import io.confluent.connect.solr.sink.config.CloudSolrInputDocumentHandlerConfig;
import io.confluent.connect.solr.sink.config.CloudSolrSinkTopicConfig;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.CloudSolrClient;

import java.util.List;
import java.util.Map;

public class CloudSolrInputDocumentHandlerFactory extends SolrInputDocumentHandlerFactory {

  CloudSolrInputDocumentHandlerConfig cloudSolrSinkConnectorConfig;
  SolrClient solrClient;

  @Override
  public void initialize(Map<String, String> props) {
    this.cloudSolrSinkConnectorConfig = new CloudSolrInputDocumentHandlerConfig(props);

    List<String> zookeeperHost = this.cloudSolrSinkConnectorConfig.getZookeeperHosts();
    String chroot = this.cloudSolrSinkConnectorConfig.getZookeeperChroot();
    this.solrClient = new CloudSolrClient(zookeeperHost, chroot);
  }

  @Override
  protected SolrInputDocumentHandler create(String topic) {
    CloudSolrSinkTopicConfig cloudSolrSinkTopicConfig = this.cloudSolrSinkConnectorConfig.getTopicConfig(topic);
    SolrInputDocumentConverter solrInputDocumentConverter = cloudSolrSinkTopicConfig.getSolrInputDocumentFactory();

    return new CloudSolrInputDocumentHandler(cloudSolrSinkTopicConfig, solrClient, solrInputDocumentConverter, cloudSolrSinkTopicConfig);
  }

  @Override
  public void close() throws Exception {
    this.solrClient.close();
  }
}