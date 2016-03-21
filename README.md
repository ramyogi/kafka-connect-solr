Currently the CloudSolr configuration is the only thing that has been tested.

# CloudSolr Configuration



## Topic Configuration

Multiple topics can be managed by a single connector. This is configured per topic by prefixing a `<solr config instance>`. For example the configuration below uses `solr0` for the first topic, `solr1` for the second, etc.

| Name                                                         | Description                                                                                                          | Type    | Default                                                        | Importance |
|--------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------|---------|----------------------------------------------------------------|------------|
| `<solr config instance>`.topic                               | Kafka topic to map to                                                                                                | string  |                                                                | High       |
| `<solr config instance>`.collection.name                     | Solr collection to write the data to                                                                                 | string  |                                                                | High       |
| `<solr config instance>`.solr.input.document.converter.class | Factory class used to get the SolrInputDocumentConverter implementation.                                             | class   | io.confluent.connect.solr.sink.SolrInputDocumentConverter      | High       |
| `<solr config instance>`.commit.within                       | Configures Solr UpdaterRequest for a commit within the requested number of milliseconds.                             | int     | null                                                           | Low        |
| `<solr config instance>`.column.ignore.unknown.fields        | Flag to determine if the connector should raise an exception when it encountered a field it doesn't have configured. | boolean | false                                                          | Low        |

## Field Configuration

Field configuration is driven by a mapping from the source Kafka Connect schema to the SOLR schema. In the table below `<connect field name>` is replaced with the field name of the connect schema.
 
| Name                                                                     | Description                                       | Type   | Default | Importance |
|--------------------------------------------------------------------------|---------------------------------------------------|--------|---------|------------|
| `<solr config instance>`.column.mappings.`<connect field name>`.field    | Name of the field in the solr schema.             | string |         | High       |
| `<solr config instance>`.column.mappings.`<connect field name>`.currency | Currency code to use when writing data for field. | string | null    | Low        | 

## Complete Example

The follow example we are pulling from the Kafka topic `twitter` and writing to the SOLR collection `twitter` using 2 tasks. 

```
name=solrcloud
topics=twitter
tasks.max=2
connector.class=io.confluent.connect.solr.sink.CloudSolrSinkConnector
solr.zookeeper.hosts=192.168.99.100:2181
solr0.topic=twitter
solr0.collection.name=twitter
solr0.commit.within=1000
solr0.column.ignore.unknown.fields=true
solr0.column.mappings.createdAt.field=created_date
solr0.column.mappings.favoriteCount.field=favorite_count
solr0.column.mappings.text.field=text
```

# HttpSolr





 

