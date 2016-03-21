/**
 * Copyright (C) 2016 Jeremy Custenborder (jcustenborder@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.confluent.connect.solr.sink;

import io.confluent.connect.solr.sink.config.CloudSolrSinkConnectorConfig;
import io.confluent.connect.solr.sink.config.CloudSolrSinkTopicConfig;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.CloudSolrClient;

import java.util.List;
import java.util.Map;

class CloudSolrInputDocumentHandlerFactory extends SolrInputDocumentHandlerFactory {

  CloudSolrSinkConnectorConfig cloudSolrSinkConnectorConfig;
  SolrClient solrClient;

  @Override
  public void initialize(Map<String, String> props) {
    this.cloudSolrSinkConnectorConfig = new CloudSolrSinkConnectorConfig(props);

    List<String> zookeeperHost = this.cloudSolrSinkConnectorConfig.getZookeeperHosts();
    String chroot = this.cloudSolrSinkConnectorConfig.getZookeeperChroot();
    this.solrClient = new CloudSolrClient(zookeeperHost, chroot);
  }

  @Override
  protected SolrInputDocumentHandler create(String topic) {
    CloudSolrSinkTopicConfig cloudSolrSinkTopicConfig = this.cloudSolrSinkConnectorConfig.getTopicConfig(topic);
    SolrInputDocumentConverter solrInputDocumentConverter = cloudSolrSinkTopicConfig.getSolrInputDocumentConverter();

    return new CloudSolrInputDocumentHandler(cloudSolrSinkTopicConfig, solrClient, solrInputDocumentConverter, cloudSolrSinkTopicConfig);
  }

  @Override
  public void close() throws Exception {
    this.solrClient.close();
  }
}