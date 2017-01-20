package com.github.jcustenborder.kafka.connect.solr;

import com.google.common.collect.ImmutableMap;
import org.apache.kafka.connect.sink.SinkRecord;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrRequest;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.common.util.NamedList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.withSettings;

public class SolrSinkTaskTest {
  private static final Logger log = LoggerFactory.getLogger(SolrSinkTaskTest.class);
  SolrSinkTask task;
  SolrClient client;

  @BeforeEach
  public void before() throws IOException, SolrServerException {
    this.task = mock(SolrSinkTask.class, Mockito.CALLS_REAL_METHODS);
    this.client = mock(SolrClient.class);
    when(this.task.client()).thenReturn(this.client);
    when(this.task.config(anyMap())).thenAnswer(invocationOnMock -> {
      Map<String, String> settings = invocationOnMock.getArgument(0);
      return new SolrSinkConnectorConfig(SolrSinkConnectorConfig.config(), settings);
    });

    Map<String, String> settings = ImmutableMap.of();
    this.task.start(settings);
    when(this.client.request(any(UpdateRequest.class), any())).thenAnswer(new Answer<Object>() {
      @Override
      public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
        log.trace("request");
        NamedList<Object> result = new NamedList<>();
        NamedList<Object> responseHeaders = new NamedList<>();
        responseHeaders.add("status", 200);
        responseHeaders.add("QTime", 123);
        result.add("responseHeader", responseHeaders);
        return result;
      }
    });
  }

  @Test
  public void test() {
    List<SinkRecord> records = Records.records();
    this.task.put(records);


  }

}
