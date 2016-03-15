package io.confluent.connect.solr.sink;

import org.apache.kafka.connect.connector.Task;
import org.apache.kafka.connect.sink.SinkConnector;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class SolrSinkConnector extends SinkConnector {

  @Override
  public String version() {
    return VersionUtil.getVersion();
  }

  Map<String, String> config;

  @Override
  public void start(Map<String, String> map) {
    this.config = map;
  }

  @Override
  public Class<? extends Task> taskClass() {
    return SolrSinkTask.class;
  }

  @Override
  public List<Map<String, String>> taskConfigs(int count) {
    List<Map<String, String>> results = new ArrayList<>();

    for(int i=0;i<count;i++){
      results.add(config);
    }

    return results;
  }

  @Override
  public void stop() {

  }
}
