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
package io.confluent.connect.solr.sink.config;

import org.apache.kafka.common.config.ConfigDef;

import java.util.List;
import java.util.Map;

public class CloudSolrSinkConnectorConfig extends SolrSinkConnectorConfig<CloudSolrSinkTopicConfig> {

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

  protected CloudSolrSinkConnectorConfig(ConfigDef subclassConfigDef, Map<String, String> props) {
    super(subclassConfigDef, props);
  }

  @Override
  protected CloudSolrSinkTopicConfig createTopicConfig(Map<String, String> props) {
    return new CloudSolrSinkTopicConfig(props);
  }


  public CloudSolrSinkConnectorConfig(Map<String, String> props) {
    this(configDef(), props);
  }

}
