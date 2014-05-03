package com.seulgi;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class FunctionsTest {

    private Map<String, State> stateMap;
    private ImmutableSet<City> caCities;
    private ImmutableSet<City> nyCities;
    private State california;
    private State newYork;

    private class State {

        private String name;
        private Iterable<City> mainCities;
        private String code;

        private State(String name, Set<City> mainCities, String code) {
            this.name = name;
            this.mainCities = mainCities;
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public String getCode() {
            return code;
        }

        public Iterable<City> getMainCities() {
            return mainCities;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setMainCities(Set<City> mainCities) {
            this.mainCities = mainCities;
        }

        public void setCode(String code) {
            this.code = code;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            State state = (State) o;

            if (code != null ? !code.equals(state.code) : state.code != null) return false;
            if (mainCities != null ? !mainCities.equals(state.mainCities) : state.mainCities != null) return false;
            if (name != null ? !name.equals(state.name) : state.name != null) return false;

            return true;
        }
    }

    private class City {
        private String name;
        private String zipCode;
        private int population;

        public City(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    /*
        Function that takes an instance of State and returns comma-separated list
        of main cities in the State.
     */
    private class StateToCityString implements Function<State, String> {
        @Override
        public String apply(State input) {
            return Joiner.on(",").join(input.getMainCities());
        }
    }

    @Before
    public void setUp() {
        caCities = ImmutableSet.of(
            new City("San Francisco"),
            new City("Los Angeles"),
            new City("San Diego")
        );
        california = new State("California", caCities, "CA");

        nyCities = ImmutableSet.of(
            new City("New York"),
            new City("Shithaca"),
            new City("Watertown")
        );

        newYork = new State("New York", nyCities, "NY");

        stateMap = ImmutableMap.of("California", california, "New York", newYork);
    }

    @Test
    public void testForMap() {
        /*
            The forMap method takes Map<K,V> and returns a function
            (Function<K,V>) whose apply method will perform a map lookup.
         */

        Function<String, State> lookup = Functions.forMap(stateMap);
        assertThat(lookup.apply("California"), is(california));
        assertThat(lookup.apply("New York"), is(newYork));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testForMapThrowsIllegalArgumentException() {
        Function<String, State> lookup = Functions.forMap(stateMap);
        lookup.apply("Ohio");
    }

    @Test
    public void testForMapWithDefaultValues() {
        State defaultState = new State("Aliens", null, "AL");
        Function<String, State> lookup = Functions.forMap(stateMap, defaultState);
        assertThat(lookup.apply("Ubuntu"), is(defaultState));
    }

    @Test
    public void testComposed() {
        /*
            Functions.compose method takes two Function instances as arguments
            and returns a single Function instance that is a composition of the two.
         */

        Function<String, State> lookup = Functions.forMap(stateMap);
        Function<State, String> stateFunction = new StateToCityString();

        Function<String, String> composed = Functions.compose(stateFunction, lookup);

        String caCityList = composed.apply("California");

        assertThat(caCityList, is("San Francisco,Los Angeles,San Diego"));
    }
}
