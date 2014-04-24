package com.seulgi;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.junit.Test;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class JoinerTest {
    private static final String DELIMETER = "|";
    private static final List<String> STRING_LIST = Lists.newArrayList("One", "Two", "Three");
    private static final List<String> STRING_LIST_WITH_NULLS = Lists.newArrayList("One", null, "Two", null, "Three", null);
    private static final String EXPECTED_JOINED_STRING = "One|Two|Three";

    @Test
    public void joinerCanJoinStrings() {
        String joinedString = Joiner.on(DELIMETER).join(STRING_LIST);
        assertThat(joinedString, is(EXPECTED_JOINED_STRING));
    }

    @Test
    public void joinerCanSipNulls() {
        String joinedString = Joiner.on(DELIMETER).skipNulls().join(STRING_LIST_WITH_NULLS);
        assertThat(joinedString, is(EXPECTED_JOINED_STRING));

    }

    @Test
    public void joinerCanReplaceNulls() {
        String joinedString = Joiner.on(DELIMETER).useForNull("EMPTY").join(STRING_LIST_WITH_NULLS);
        String expectedString = "One|EMPTY|Two|EMPTY|Three|EMPTY";
        assertThat(joinedString, is(expectedString));
    }

    @Test(expected = NullPointerException.class)
    public void joinerInstanceIsImmutable() {
        Joiner joiner = Joiner.on(DELIMETER);
        // This will NOT mutate joiner. Instead, it returns a new instance of joiner with skipNulls enabled.
        joiner.skipNulls();
        joiner.join(STRING_LIST_WITH_NULLS);
    }

    @Test
    public void joinerAppendsToStringBuilder() {
        StringBuilder stringBuilder = new StringBuilder();
        Joiner joiner = Joiner.on("|").skipNulls();
        stringBuilder = joiner.appendTo(stringBuilder, "One", "Two", "Three");
        assertThat(stringBuilder.toString(), is(EXPECTED_JOINED_STRING));
    }

    @Test
    public void joinerAppendsToAppendableInstance() {
        final String tempFileName = "temp.txt";
        File file = new File(tempFileName);
        try (FileWriter fileWriter = new FileWriter(file)) {
            Joiner joiner = Joiner.on("|").skipNulls();
            FileWriter appendedFileWriter = joiner.appendTo(fileWriter, STRING_LIST);
            appendedFileWriter.flush();

            FileReader fileReader = new FileReader(new File(tempFileName));
            char[] buffer = new char[50];
            fileReader.read(buffer);
            assertThat(String.valueOf(buffer).trim(), is(EXPECTED_JOINED_STRING));


        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            if (!file.delete()) {
                System.out.println("Unable to delete " + tempFileName);
                System.out.println("Please manually delete the file");
            }
        }
    }

    @Test
    public void joinerJoinsMap() {
        String expectedString = "Seoul=Korea|Washington D.C.=U.S.|Tokyo=Japan";
        Map<String, String> testMap = Maps.newLinkedHashMap();
        testMap.put("Seoul", "Korea");
        testMap.put("Washington D.C.", "U.S.");
        testMap.put("Tokyo", "Japan");
        String returnedString = Joiner.on("|").withKeyValueSeparator("=").join(testMap);
        assertThat(returnedString, is(expectedString));
    }

}
