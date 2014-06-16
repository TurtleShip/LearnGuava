package com.seulgi.base;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/*
    The Predicates class is a collection of useful methods for
    working with Predicate instances. The Predicates class offers
    some very helpful methods that should be expected from working
    with Boolean conditions, chaining Predicate instances with
    "and" or "or" conditions, and providing a "not" that evaluates
    to true if the given Predicate instance evaluates to false
    and vice versa.
 */
public class PredicatesTest {

    private enum Climate {
        COLD, COOL, TEMPERATE, WARM, HOT
    }

    private class City {
        private final String name;
        private Climate climate;
        private double rainfall;
        private int population;

        /*
            Default values will be assigned so that
            all predicates returns false.
         */
        public City(String name) {
            this.name = name;
            this.climate = Climate.HOT;
            this.rainfall = 50.0;
            this.population = 1_000_000;
        }

        public String getName() {
            return name;
        }

        public Climate getClimate() {
            return climate;
        }

        public double getRainfall() {
            return rainfall;
        }

        public int getPopulation() {
            return population;
        }

        public void setClimate(Climate climate) {
            this.climate = climate;
        }

        public void setRainfall(double rainfall) {
            this.rainfall = rainfall;
        }

        public void setPopulation(int population) {
            this.population = population;
        }
    }

    private class WarmClimatePredicate implements Predicate<City> {

        @Override
        public boolean apply(City input) {
            return input.getClimate().equals(Climate.WARM);
        }
    }

    private class LowRainfallPredicate implements Predicate<City> {

        @Override
        public boolean apply(City input) {
            return input.getRainfall() < 45.7;
        }
    }

    private class SmallPopulationPredicate implements Predicate<City> {

        @Override
        public boolean apply(City input) {
            return input.getPopulation() < 500_000;
        }
    }

    private class BigCityNamePredicate implements Predicate<String> {

        @Override
        public boolean apply(String name) {
            return name.equals("San Francisco") || name.equals("New York");
        }
    }

    /*
        This obviously is a crappy design, but I created it to play
        around with Function.
     */
    private class GetCityNameFunction implements Function<City, String> {

        @Override
        public String apply(City input) {
            return input.getName();
        }
    }

    @Test
    public void testPredicate() {
        City testCity = new City("Test");
        testCity.setClimate(Climate.COLD);
        assertThat((new WarmClimatePredicate().apply(testCity)), is(false));
        testCity.setClimate(Climate.WARM);
        assertThat((new WarmClimatePredicate().apply(testCity)), is(true));
    }

    @Test
    public void testPredicateAnd() {
        City testCity = new City("Test");
        Predicate<City> warmAndDry = Predicates.and(new WarmClimatePredicate(), new LowRainfallPredicate());
        assertThat(warmAndDry.apply(testCity), is(false));

        testCity.setClimate(Climate.WARM);
        testCity.setRainfall(5.0);
        assertThat(warmAndDry.apply(testCity), is(true));
    }

    @Test
    public void testPredicateOr() {
        City testCityOne = new City("Test1");
        City testCityTwo = new City("Test2");

        Predicate<City> warmOrDry = Predicates.or(new WarmClimatePredicate(), new LowRainfallPredicate());

        assertThat(warmOrDry.apply(testCityOne), is(false));
        assertThat(warmOrDry.apply(testCityTwo), is(false));

        testCityOne.setClimate(Climate.WARM);
        testCityTwo.setRainfall(0.0);

        assertThat(warmOrDry.apply(testCityOne), is(true));
        assertThat(warmOrDry.apply(testCityTwo), is(true));
    }

    @Test
    public void testPredicateNot() {
        City testCity = new City("Test");
        assertThat(Predicates.not(new WarmClimatePredicate()).apply(testCity), is(true));
        assertThat(Predicates.not(new LowRainfallPredicate()).apply(testCity), is(true));
        assertThat(Predicates.not(new SmallPopulationPredicate()).apply(testCity), is(true));
    }

    @Test
    public void testPredicateCompose() {
        /*
            The Predicates.compose method takes Function and Predicate as arguments and evaluates
            the given Predicate instance on the output returned from Function.
         */

        Predicate<City> bigNamePredicate
            = Predicates.compose(new BigCityNamePredicate(), new GetCityNameFunction());

        City sanFran = new City("San Francisco");
        City newYork = new City("New York");
        City shithaca = new City("Shithaca");

        assertThat(bigNamePredicate.apply(sanFran), is(true));
        assertThat(bigNamePredicate.apply(newYork), is(true));
        assertThat(bigNamePredicate.apply(shithaca), is(false));
    }
}
