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
package io.confluent.connect.solr.sink;

import java.util.HashMap;
import java.util.Map;

abstract class SolrInputDocumentHandlerFactory implements AutoCloseable {
  public abstract void initialize(Map<String, String> props);
  protected abstract SolrInputDocumentHandler create(String topic);


  final Map<String, SolrInputDocumentHandler> topicToSolrInputDocumentHandler;

  public SolrInputDocumentHandlerFactory(){
    this.topicToSolrInputDocumentHandler = new HashMap<>();
  }

  public final SolrInputDocumentHandler get(String topic) {
    SolrInputDocumentHandler solrInputDocumentHandler = this.topicToSolrInputDocumentHandler.get(topic);

    if(null==solrInputDocumentHandler){
      solrInputDocumentHandler = create(topic);
      this.topicToSolrInputDocumentHandler.put(topic, solrInputDocumentHandler);
    }

    return solrInputDocumentHandler;
  }
}
