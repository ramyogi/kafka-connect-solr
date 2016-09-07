package io.confluent.connect.solr.sink;

import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.connect.connector.Task;

public class CloudSolrSinkConnector extends SolrSinkConnector {
  @Override
  public Class<? extends Task> taskClass() {
    return CloudSolrSinkTask.class;
  }

  @Override
  public ConfigDef config() {

    ConfigDef def = new ConfigDef();

//    def.




    return null;
  }
}
