/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package opennlp.tools.tokenize;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

public class WordpieceTokenizerTest {

  @Test
  public void testSentence() {

    final Tokenizer tokenizer = new WordpieceTokenizer(getVocabulary());
    final String[] tokens = tokenizer.tokenize("the quick brown fox jumps over the very lazy dog");

    final String[] expected = {"[CLS]", "the", "quick", "brown", "fox", "jumps", "over", "the",
      "[UNK]", "lazy", "dog", "[SEP]"};

    Assert.assertArrayEquals(expected, tokens);

  }

  @Test
  public void testSentenceWithPunctuation() {

    final Tokenizer tokenizer = new WordpieceTokenizer(getVocabulary());
    final String[] tokens = tokenizer.tokenize("The quick brown fox jumps over the very lazy dog.");

    final String[] expected = {"[CLS]", "[UNK]", "quick", "brown", "fox", "jumps", "over", "the",
      "[UNK]", "lazy", "dog", "[UNK]", "[SEP]"};

    Assert.assertArrayEquals(expected, tokens);

  }

  private Set<String> getVocabulary() {

    final Set<String> vocabulary = new HashSet<>();

    vocabulary.add("the");
    vocabulary.add("quick");
    vocabulary.add("brown");
    vocabulary.add("fox");
    vocabulary.add("jumps");
    vocabulary.add("over");
    vocabulary.add("the");
    vocabulary.add("lazy");
    vocabulary.add("dog");

    return vocabulary;

  }

}
