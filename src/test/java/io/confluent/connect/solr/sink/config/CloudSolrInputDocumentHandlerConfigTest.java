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

import io.confluent.connect.solr.sink.solr.CloudSolrInputDocumentHandlerFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CloudSolrInputDocumentHandlerConfigTest {
  Map<String, String> props;
  CloudSolrInputDocumentHandlerConfig config;

  @Before
  public void before(){
    this.props = new LinkedHashMap<>();
    this.props.put(CloudSolrInputDocumentHandlerConfig.ZOOKEEPER_CHROOT_CONFIG, "/solr");
    this.props.put(CloudSolrInputDocumentHandlerConfig.ZOOKEEPER_HOSTS_CONFIG, "server1:2181,server2:2181,server3:2181");

    this.config = new CloudSolrInputDocumentHandlerConfig(this.props);
  }

  @Test
  public void getZookeeperChroot(){
    final String expected = "/solr";
    final String actual = this.config.getZookeeperChroot();
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void getZookeeperChroot_null(){
    this.props.remove(CloudSolrInputDocumentHandlerConfig.ZOOKEEPER_CHROOT_CONFIG);
    this.config = new CloudSolrInputDocumentHandlerConfig(props);
    final String expected = null;
    final String actual = this.config.getZookeeperChroot();
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void getZookeeperHosts(){
    final List<String> expected = Arrays.asList("server1:2181", "server2:2181", "server3:2181");
    final List<String> actual = this.config.getZookeeperHosts();
    Assert.assertEquals(expected, actual);
  }

  @Test(expected = org.apache.kafka.common.config.ConfigException.class)
  public void getZookeeperHosts_null(){
    this.props.remove(CloudSolrInputDocumentHandlerConfig.ZOOKEEPER_HOSTS_CONFIG);
    this.config = new CloudSolrInputDocumentHandlerConfig(props);
  }
}
