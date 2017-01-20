package com.github.jcustenborder.kafka.connect.solr;

import io.confluent.kafka.connect.utils.config.MarkdownFormatter;
import org.junit.jupiter.api.Test;

public class CloudSolrSinkConnectorConfigTest {
  @Test
  public void toMarkdown() {
    System.out.println(MarkdownFormatter.toMarkdown(CloudSolrSinkConnectorConfig.config()));
  }
}
