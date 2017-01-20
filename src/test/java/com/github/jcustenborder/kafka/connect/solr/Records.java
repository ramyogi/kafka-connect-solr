package com.github.jcustenborder.kafka.connect.solr;

import com.google.common.collect.ImmutableMap;
import org.apache.kafka.common.record.TimestampType;
import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.data.SchemaBuilder;
import org.apache.kafka.connect.data.Struct;
import org.apache.kafka.connect.sink.SinkRecord;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

class Records {

  public static class TestCase {
    SinkRecord record;

  }

  public static class StructTestCase extends TestCase {
    Struct struct;
  }

  public static class MapTestCase extends TestCase {
    Map<String, Object> map;
  }

  static MapTestCase map() {
    MapTestCase testCase = new MapTestCase();

    testCase.map = ImmutableMap.of(
        "firstName", "example",
        "lastName", "user",
        "email", "example.user@example.com",
        "age", 27
    );
    testCase.record = new SinkRecord(
        "testing",
        1,
        null,
        null,
        null,
        testCase.map,
        1L,
        1484897702123L,
        TimestampType.CREATE_TIME
    );

    return testCase;
  }

  static StructTestCase struct() {
    StructTestCase testCase = new StructTestCase();

    Schema schema = SchemaBuilder.struct()
        .name("Testing")
        .field("firstName", Schema.OPTIONAL_STRING_SCHEMA)
        .field("lastName", Schema.OPTIONAL_STRING_SCHEMA)
        .field("email", Schema.OPTIONAL_STRING_SCHEMA)
        .field("age", Schema.OPTIONAL_INT32_SCHEMA)
        .build();
    testCase.struct = new Struct(schema)
        .put("firstName", "example")
        .put("lastName", "user")
        .put("email", "example.user@example.com")
        .put("age", 27);
    testCase.record = new SinkRecord(
        "testing",
        1,
        null,
        null,
        null,
        testCase.struct,
        2L,
        1484897702123L,
        TimestampType.CREATE_TIME
    );

    return testCase;
  }

  static List<SinkRecord> records() {
    return Arrays.asList(
        struct().record,
        map().record
    );
  }
}
