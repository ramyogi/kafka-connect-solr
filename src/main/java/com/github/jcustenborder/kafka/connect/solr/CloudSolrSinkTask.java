/**
 * Copyright © 2016 Jeremy Custenborder (jcustenborder@gmail.com)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.jcustenborder.kafka.connect.solr;


import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.CloudSolrClient;

import java.util.Map;

public class CloudSolrSinkTask extends SolrSinkTask<CloudSolrSinkConnectorConfig> {


  @Override
  protected CloudSolrSinkConnectorConfig config(Map settings) {
    return new CloudSolrSinkConnectorConfig(settings);
  }

  @Override
  protected SolrClient client() {
    CloudSolrClient.Builder builder = new CloudSolrClient.Builder();
    builder.withZkHost(this.config.zookeeperHosts);
    builder.withZkChroot(this.config.zookeeperChroot);
    return builder.build();
  }
}
