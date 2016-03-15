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


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.confluent.connect.solr.sink.config.CloudSolrSinkTopicConfig;
import io.confluent.connect.solr.sink.config.SolrSinkTopicConfig;
import org.apache.kafka.connect.data.*;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.SolrInputField;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DefaultSolrInputDocumentConverterTest {

  SolrInputDocumentConverter solrInputDocumentConverter;

  @Before
  public void before(){
    this.solrInputDocumentConverter = new DefaultSolrInputDocumentConverter();

    Map<String, String> props = new HashMap<>();
    props.put(SolrSinkTopicConfig.TOPIC_CONFIG, "foo");
    props.put(CloudSolrSinkTopicConfig.COLLECTION_NAME_CONFIG, "foo");
    CloudSolrSinkTopicConfig topicConfig = new CloudSolrSinkTopicConfig(props);
    this.solrInputDocumentConverter.configure(topicConfig);
  }

  private void convertField(Schema fieldSchema, final Object inputValue, final Object expectedValue){
    final String FIELD_NAME = "asdf";
    final String TOPIC="test";
    final Schema valueSchema = SchemaBuilder.struct().
        field(FIELD_NAME, fieldSchema)
        .build();
    final Field field = valueSchema.field(FIELD_NAME);
    final Struct value = new Struct(valueSchema)
        .put(FIELD_NAME, inputValue);
    SolrInputDocument solrInputDocument = new SolrInputDocument();
    this.solrInputDocumentConverter.convertField(value, field, solrInputDocument);

    SolrInputField solrInputField = solrInputDocument.getField(FIELD_NAME);

    if(null==expectedValue){
      Assert.assertNull(
          String.format("SolrInputDocument should not have '%s' field.", FIELD_NAME),
          solrInputField
      );
    } else {
      Assert.assertNotNull(
          String.format("SolrInputDocument should have '%s' field.", FIELD_NAME),
          solrInputField
      );

      final Object actualValue = solrInputField.getValue();
      Assert.assertEquals(
          String.format("'%s' field of SolrInputDocument does not have the expected value.", FIELD_NAME),
          expectedValue,
          actualValue
      );
    }
  }

  @Test
  public void convertField_timestamp_schema(){
    Schema schema = Timestamp.SCHEMA;
    final Date expected = new Date();
    convertField(schema, expected, expected);
  }

  @Test
  public void convertField_optional_timestamp_schema(){
    Schema schema = Timestamp.builder().optional().build();
    final Date expected = new Date();
    convertField(schema, expected, expected);
    convertField(schema, null, null);
  }

  @Test
  public void convertField_int8_schema(){
    convertField(Schema.INT8_SCHEMA, Byte.MAX_VALUE, Byte.MAX_VALUE);
    convertField(Schema.INT8_SCHEMA, Byte.MIN_VALUE, Byte.MIN_VALUE);
    convertField(Schema.INT8_SCHEMA, (byte)0, (byte)0);
  }

  @Test
  public void convertField_optional_int8_schema(){
    convertField(Schema.OPTIONAL_INT8_SCHEMA, Byte.MAX_VALUE, Byte.MAX_VALUE);
    convertField(Schema.OPTIONAL_INT8_SCHEMA, Byte.MIN_VALUE, Byte.MIN_VALUE);
    convertField(Schema.OPTIONAL_INT8_SCHEMA, (byte)0, (byte)0);
    convertField(Schema.OPTIONAL_INT8_SCHEMA, null, null);
  }

  @Test
  public void convertField_int16_schema(){
    convertField(Schema.INT16_SCHEMA, Short.MAX_VALUE, Short.MAX_VALUE);
    convertField(Schema.INT16_SCHEMA, Short.MIN_VALUE, Short.MIN_VALUE);
    convertField(Schema.INT16_SCHEMA, (short)0, (short)0);
  }

  @Test
  public void convertField_optional_int16_schema(){
    convertField(Schema.OPTIONAL_INT16_SCHEMA, Short.MAX_VALUE, Short.MAX_VALUE);
    convertField(Schema.OPTIONAL_INT16_SCHEMA, Short.MIN_VALUE, Short.MIN_VALUE);
    convertField(Schema.OPTIONAL_INT16_SCHEMA, (short)0, (short)0);
    convertField(Schema.OPTIONAL_INT16_SCHEMA, null, null);
  }


  @Test
  public void convertField_int32_schema(){
    convertField(Schema.INT32_SCHEMA, Integer.MAX_VALUE, Integer.MAX_VALUE);
    convertField(Schema.INT32_SCHEMA, Integer.MIN_VALUE, Integer.MIN_VALUE);
    convertField(Schema.INT32_SCHEMA, 0, 0);
  }

  @Test
  public void convertField_optional_int32_schema(){
    convertField(Schema.OPTIONAL_INT32_SCHEMA, Integer.MAX_VALUE, Integer.MAX_VALUE);
    convertField(Schema.OPTIONAL_INT32_SCHEMA, Integer.MIN_VALUE, Integer.MIN_VALUE);
    convertField(Schema.OPTIONAL_INT32_SCHEMA, 0, 0);
    convertField(Schema.OPTIONAL_INT32_SCHEMA, null, null);
  }

  @Test
  public void convertField_int64_schema(){
    convertField(Schema.INT64_SCHEMA, Long.MAX_VALUE, Long.MAX_VALUE);
    convertField(Schema.INT64_SCHEMA, Long.MIN_VALUE, Long.MIN_VALUE);
    convertField(Schema.INT64_SCHEMA, 0L, 0L);
  }

  @Test
  public void convertField_optional_int64_schema(){
    convertField(Schema.OPTIONAL_INT64_SCHEMA, Long.MAX_VALUE, Long.MAX_VALUE);
    convertField(Schema.OPTIONAL_INT64_SCHEMA, Long.MIN_VALUE, Long.MIN_VALUE);
    convertField(Schema.OPTIONAL_INT64_SCHEMA, 0L, 0L);
    convertField(Schema.OPTIONAL_INT64_SCHEMA, null, null);
  }

  @Test
  public void convertField_float32_schema(){
    convertField(Schema.FLOAT32_SCHEMA, Float.MAX_VALUE, Float.MAX_VALUE);
    convertField(Schema.FLOAT32_SCHEMA, Float.MIN_VALUE, Float.MIN_VALUE);
    convertField(Schema.FLOAT32_SCHEMA, 0.0F, 0.0F);
  }

  @Test
  public void convertField_optional_float32_schema(){
    convertField(Schema.OPTIONAL_FLOAT32_SCHEMA, Float.MAX_VALUE, Float.MAX_VALUE);
    convertField(Schema.OPTIONAL_FLOAT32_SCHEMA, Float.MIN_VALUE, Float.MIN_VALUE);
    convertField(Schema.OPTIONAL_FLOAT32_SCHEMA, 0.0F, 0.0F);
    convertField(Schema.OPTIONAL_FLOAT32_SCHEMA, null, null);
  }

  @Test
  public void convertField_float64_schema(){
    convertField(Schema.FLOAT64_SCHEMA, Double.MAX_VALUE, Double.MAX_VALUE);
    convertField(Schema.FLOAT64_SCHEMA, Double.MIN_VALUE, Double.MIN_VALUE);
    convertField(Schema.FLOAT64_SCHEMA, 0.0D, 0.0D);
  }

  @Test
  public void convertField_optional_float64_schema(){
    convertField(Schema.OPTIONAL_FLOAT64_SCHEMA, Double.MAX_VALUE, Double.MAX_VALUE);
    convertField(Schema.OPTIONAL_FLOAT64_SCHEMA, Double.MIN_VALUE, Double.MIN_VALUE);
    convertField(Schema.OPTIONAL_FLOAT64_SCHEMA, 0.0D, 0.0D);
    convertField(Schema.OPTIONAL_FLOAT64_SCHEMA, null, null);
  }
  
  @Test
  public void convertField_boolean_schema(){
    convertField(Schema.BOOLEAN_SCHEMA, Boolean.TRUE, Boolean.TRUE);
    convertField(Schema.BOOLEAN_SCHEMA, Boolean.FALSE, Boolean.FALSE);
  }

  @Test
  public void convertField_optional_boolean_schema(){
    convertField(Schema.OPTIONAL_BOOLEAN_SCHEMA, Boolean.TRUE, Boolean.TRUE);
    convertField(Schema.OPTIONAL_BOOLEAN_SCHEMA, Boolean.FALSE, Boolean.FALSE);
    convertField(Schema.OPTIONAL_BOOLEAN_SCHEMA, null, null);
  }

  @Test
  public void convertField_string_schema(){
    convertField(Schema.STRING_SCHEMA, "This is a test", "This is a test");
  }

  @Test
  public void convertField_optional_string_schema(){
    convertField(Schema.OPTIONAL_STRING_SCHEMA, "This is a test", "This is a test");
    convertField(Schema.OPTIONAL_STRING_SCHEMA, null, null);
  }
}
