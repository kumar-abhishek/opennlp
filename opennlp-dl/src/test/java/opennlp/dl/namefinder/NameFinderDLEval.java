/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package opennlp.dl.namefinder;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import ai.onnxruntime.OrtException;

import org.junit.Assert;
import org.junit.Test;

import opennlp.dl.AbstactDLTest;

import opennlp.tools.util.Span;

public class NameFinderDLEval extends AbstactDLTest {

  @Test
  public void tokenNameFinder1Test() throws Exception {

    // This test was written using the dslim/bert-base-NER model.
    // You will need to update the ids2Labels and assertions if you use a different model.

    final File model = new File(getOpennlpDataDir(), "onnx/namefinder/model.onnx");
    final File vocab = new File(getOpennlpDataDir(), "onnx/namefinder/vocab.txt");

    final String[] tokens = new String[]
        {"George", "Washington", "was", "president", "of", "the", "United", "States", "."};

    final NameFinderDL nameFinderDL = new NameFinderDL(model, vocab, false, getIds2Labels());
    final Span[] spans = nameFinderDL.find(tokens);

    for (Span span : spans) {
      System.out.println(span.toString());
    }

    Assert.assertEquals(1, spans.length);
    Assert.assertEquals(0, spans[0].getStart());
    Assert.assertEquals(2, spans[0].getEnd());
    Assert.assertEquals(8.251646041870117, spans[0].getProb(), 0.0);

  }

  @Test
  public void tokenNameFinder2Test() throws Exception {

    // This test was written using the dslim/bert-base-NER model.
    // You will need to update the ids2Labels and assertions if you use a different model.

    final File model = new File(getOpennlpDataDir(), "onnx/namefinder/model.onnx");
    final File vocab = new File(getOpennlpDataDir(), "onnx/namefinder/vocab.txt");

    final String[] tokens = new String[]{"His", "name", "was", "George", "Washington"};

    final NameFinderDL nameFinderDL = new NameFinderDL(model, vocab, false, getIds2Labels());
    final Span[] spans = nameFinderDL.find(tokens);

    for (Span span : spans) {
      System.out.println(span.toString());
    }

    Assert.assertEquals(1, spans.length);
    Assert.assertEquals(3, spans[0].getStart());
    Assert.assertEquals(5, spans[0].getEnd());

  }

  @Test
  public void tokenNameFinder3Test() throws Exception {

    // This test was written using the dslim/bert-base-NER model.
    // You will need to update the ids2Labels and assertions if you use a different model.

    final File model = new File(getOpennlpDataDir(), "onnx/namefinder/model.onnx");
    final File vocab = new File(getOpennlpDataDir(), "onnx/namefinder/vocab.txt");

    final String[] tokens = new String[]{"His", "name", "was", "George"};

    final NameFinderDL nameFinderDL = new NameFinderDL(model, vocab, false, getIds2Labels());
    final Span[] spans = nameFinderDL.find(tokens);

    for (Span span : spans) {
      System.out.println(span.toString());
    }

    Assert.assertEquals(1, spans.length);
    Assert.assertEquals(3, spans[0].getStart());
    Assert.assertEquals(4, spans[0].getEnd());

  }

  @Test
  public void tokenNameFinderNoInputTest() throws Exception {

    // This test was written using the dslim/bert-base-NER model.
    // You will need to update the ids2Labels and assertions if you use a different model.

    final File model = new File(getOpennlpDataDir(), "onnx/namefinder/model.onnx");
    final File vocab = new File(getOpennlpDataDir(), "onnx/namefinder/vocab.txt");

    final String[] tokens = new String[]{};

    final NameFinderDL nameFinderDL = new NameFinderDL(model, vocab, false, getIds2Labels());
    final Span[] spans = nameFinderDL.find(tokens);

    Assert.assertEquals(0, spans.length);

  }

  @Test
  public void tokenNameFinderNoEntitiesTest() throws Exception {

    // This test was written using the dslim/bert-base-NER model.
    // You will need to update the ids2Labels and assertions if you use a different model.

    final File model = new File(getOpennlpDataDir(), "onnx/namefinder/model.onnx");
    final File vocab = new File(getOpennlpDataDir(), "onnx/namefinder/vocab.txt");

    final String[] tokens = new String[]{"I", "went", "to", "the", "park"};

    final NameFinderDL nameFinderDL = new NameFinderDL(model, vocab, false, getIds2Labels());
    final Span[] spans = nameFinderDL.find(tokens);

    Assert.assertEquals(0, spans.length);

  }

  @Test
  public void tokenNameFinderMultipleEntitiesTest() throws Exception {

    // This test was written using the dslim/bert-base-NER model.
    // You will need to update the ids2Labels and assertions if you use a different model.

    final File model = new File(getOpennlpDataDir(), "onnx/namefinder/model.onnx");
    final File vocab = new File(getOpennlpDataDir(), "onnx/namefinder/vocab.txt");

    final String[] tokens = new String[]{"George", "Washington", "and", "Abraham", "Lincoln",
        "were", "presidents"};

    final NameFinderDL nameFinderDL = new NameFinderDL(model, vocab, false, getIds2Labels());
    final Span[] spans = nameFinderDL.find(tokens);

    for (Span span : spans) {
      System.out.println(span.toString());
    }

    Assert.assertEquals(2, spans.length);
    Assert.assertEquals(0, spans[0].getStart());
    Assert.assertEquals(2, spans[0].getEnd());
    Assert.assertEquals(3, spans[1].getStart());
    Assert.assertEquals(5, spans[1].getEnd());

  }

  @Test(expected = OrtException.class)
  public void invalidModel() throws Exception {

    // This test was written using the dslim/bert-base-NER model.
    // You will need to update the ids2Labels and assertions if you use a different model.

    final File model = new File("invalid.onnx");
    final File vocab = new File("vocab.txt");

    new NameFinderDL(model, vocab, true, getIds2Labels());

  }

  private Map<Integer, String> getIds2Labels() {

    final Map<Integer, String> ids2Labels = new HashMap<>();
    ids2Labels.put(0, "O");
    ids2Labels.put(1, "B-MISC");
    ids2Labels.put(2, "I-MISC");
    ids2Labels.put(3, "B-PER");
    ids2Labels.put(4, "I-PER");
    ids2Labels.put(5, "B-ORG");
    ids2Labels.put(6, "I-ORG");
    ids2Labels.put(7, "B-LOC");
    ids2Labels.put(8, "I-LOC");

    return ids2Labels;

  }

}
