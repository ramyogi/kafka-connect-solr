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
package io.confluent.connect.solr.sink.solr;

import io.confluent.connect.solr.sink.config.FieldConfig;
import io.confluent.connect.solr.sink.config.SolrSinkTopicConfig;
import org.apache.kafka.connect.data.Field;
import org.apache.kafka.connect.data.Struct;
import org.apache.kafka.connect.errors.ConnectException;
import org.apache.kafka.connect.sink.SinkRecord;
import org.apache.solr.common.SolrInputDocument;

import java.util.*;

public abstract class SolrInputDocumentConverter {
  Map<String, String> connectFieldToSolrFieldMapping;
  Map<String, String> currencyFields;

  boolean ignoreUnknownFields;


  public void configure(SolrSinkTopicConfig topicConfig){
    this.ignoreUnknownFields = topicConfig.ignoreUnknownFields();
    List<FieldConfig> fieldConfigs = topicConfig.getFieldConfigs();

    Map<String, String> connectFieldToSolrFieldMapping = new HashMap<>();
    Map<String, String> currencyFields = new HashMap<>();

    for(FieldConfig fieldConfig:fieldConfigs){
      connectFieldToSolrFieldMapping.put(fieldConfig.getStructField(), fieldConfig.getSolrFieldName());

      if(null!=fieldConfig.getSolrFieldCurrency()){
        currencyFields.put(fieldConfig.getStructField(), fieldConfig.getSolrFieldCurrency());
      }
    }

    this.connectFieldToSolrFieldMapping = connectFieldToSolrFieldMapping;
    this.currencyFields = currencyFields;
  }

  void convertField(final Struct row, Field field, SolrInputDocument solrInputDocument){
    final Object value = row.get(field);

    if(null==value && field.schema().isOptional()){
      return;
    }

    String solrField = connectFieldToSolrFieldMapping.get(field.name());

    if(null==solrField){
      solrField=field.name();
    }

    if(currencyFields.containsKey(field.name())){
      String currencyCode = currencyFields.get(field.name());
      String currency = String.format("%s,%s", value, currencyCode);
      solrInputDocument.addField(solrField, value);
    } else {
      switch(field.schema().type()){
        default:
          solrInputDocument.addField(solrField, value);
          break;
      }
    }
  }

  public SolrInputDocument convert(SinkRecord sinkRecord){
    if(null==this.connectFieldToSolrFieldMapping||null==this.currencyFields){
      throw new ConnectException("configure() must be called before convert().");
    }

    if(null==sinkRecord) throw new NullPointerException("sinkRecord should not be null.");
    if(!(sinkRecord.value() instanceof Struct)) throw new IllegalStateException("sinkRecord.value() should be struct.");
    Struct valueStruct = (Struct)sinkRecord.value();
    SolrInputDocument solrInputDocument = new SolrInputDocument();

    for(Field field:sinkRecord.valueSchema().fields()){
      convertField(valueStruct, field, solrInputDocument);
    }

    return solrInputDocument;
  }
}
