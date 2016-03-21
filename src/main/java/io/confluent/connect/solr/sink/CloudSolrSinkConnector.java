package io.confluent.connect.solr.sink;

import org.apache.kafka.connect.connector.Task;

public class CloudSolrSinkConnector extends SolrSinkConnector {
  @Override
  public Class<? extends Task> taskClass() {
    return CloudSolrSinkTask.class;
  }
}
