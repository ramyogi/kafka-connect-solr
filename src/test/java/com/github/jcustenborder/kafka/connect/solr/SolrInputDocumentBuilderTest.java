package com.github.jcustenborder.kafka.connect.solr;

import org.apache.kafka.connect.data.Field;
import org.apache.solr.common.SolrInputDocument;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SolrInputDocumentBuilderTest {

  @Test
  public void buildMap() {
    Records.MapTestCase mapTestCase = Records.map();

    SolrInputDocument document = SolrInputDocumentBuilder.build(mapTestCase.record);
    assertNotNull(document, "document should not be null.");
    for (Map.Entry<String, Object> kvp : mapTestCase.map.entrySet()) {
      assertEquals(kvp.getValue(), document.getFieldValue(kvp.getKey()), kvp.getKey() + " does not match.");
    }
  }


  @Test
  public void buildStruct() {
    Records.StructTestCase structTestCase = Records.struct();
    SolrInputDocument document = SolrInputDocumentBuilder.build(structTestCase.record);
    assertNotNull(document, "document should not be null.");
    List<Field> fields = structTestCase.struct.schema().fields();
    for (Field field : fields) {
      Object value = structTestCase.struct.get(field);
      assertEquals(value, document.getFieldValue(field.name()), field.name() + " does not match.");
    }
  }

}
