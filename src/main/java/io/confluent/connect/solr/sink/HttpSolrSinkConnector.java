package io.confluent.connect.solr.sink;

import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.connect.connector.Task;

public class HttpSolrSinkConnector extends SolrSinkConnector {
  @Override
  public Class<? extends Task> taskClass() {
    return HttpSolrSinkTask.class;
  }

  @Override
  public ConfigDef config() {
    return null;
  }
}
