package com.seulgi.util;

import com.google.common.base.Objects;

public class Person {

    public enum Sex {
        MALE, FEMALE
    }

    private String firstName;
    private String lastName;
    private int age;
    private Sex sex;

    public Person(String firstName, String lastName, int age, Sex sex) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.sex = sex;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public Sex getSex() {
        return sex;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
            .omitNullValues()
            .add("First name", firstName)
            .add("Last name", lastName)
            .add("age", age)
            .add("sex", sex)
            .toString();
    }
}
