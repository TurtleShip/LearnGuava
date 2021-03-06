package com.seulgi.base;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


public class SplitterTest {
  private static final String DELIMITER = ",";
  private static final List EXPECTED_LIST = Lists.newArrayList("One", "Two", "Three");


  @Test
  public void splitterSplitsString() {
    String words = "One,Two,Three";
    Splitter splitter = Splitter.on(DELIMITER);
    List<String> splittedWords = Lists.newArrayList(splitter.split(words));
    assertThat(splittedWords, is(EXPECTED_LIST));
  }

  @Test
  public void splitterCanTrimResults() {
    String words = "One    ,    Two    ,      Three";
    Splitter splitter = Splitter.on(DELIMITER).trimResults();
    List<String> splittedWords = Lists.newArrayList(splitter.split(words));
    assertThat(splittedWords, is(EXPECTED_LIST));
  }

  @Test
  public void splitterCanOmitEmptyStrings() {
    String words = "One, , , ,  , Two ,  ,  ,  , Three";
    Splitter splitter = Splitter.on(DELIMITER).trimResults().omitEmptyStrings();
    List<String> splittedWords = Lists.newArrayList(splitter.split(words));
    assertThat(splittedWords, is(EXPECTED_LIST));
  }

  @Test
  public void mapSplitterCanCreateMaps() {
    String startString = "Seoul=Korea|Washington D.C.=U.S.|Tokyo=Japan";
    Map<String, String> expectedMap = Maps.newLinkedHashMap();
    expectedMap.put("Seoul", "Korea");
    expectedMap.put("Washington D.C.", "U.S.");
    expectedMap.put("Tokyo", "Japan");
    Splitter.MapSplitter mapSplitter = Splitter.on("|").withKeyValueSeparator("=");
    Map<String, String> returnedMap = mapSplitter.split(startString);
    assertThat(returnedMap, is(expectedMap));
  }

}
