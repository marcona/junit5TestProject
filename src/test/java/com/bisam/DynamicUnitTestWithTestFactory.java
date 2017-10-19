package com.bisam;

import org.hamcrest.core.Is;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import java.util.stream.Stream;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

// Aller voir
// https://github.com/eugenp/tutorials/blob/master/junit5/

public class DynamicUnitTestWithTestFactory {

    @TestFactory
    @DisplayName("FindById - Dynamic Test Generator")
    Stream<DynamicTest> generateFindByIdDynamicTests() {
        Long[] ids = {1L, 2L, 3L, 4L, 5L};

        return Stream.of(ids).map(id -> dynamicTest("DynamicTest: Find by ID " + id, () -> {
            First person = new First(id);
            assertNotNull(person);
            int index = id.intValue() - 1;
            assertThat(person.maMethode(), Is.is("toto"));
        }));
    }
}
