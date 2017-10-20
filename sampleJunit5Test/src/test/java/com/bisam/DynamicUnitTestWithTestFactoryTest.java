package com.bisam;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.stream.Stream;

// Aller voir
// https://github.com/eugenp/tutorials/blob/master/junit5/

class DynamicUnitTestWithTestFactoryTest {

    @TestFactory
    @DisplayName("FindById - Dynamic Test Generator")
    Stream<DynamicTest> generateFindByIdDynamicTests() {
        Long[] ids = {1L, 2L, 3L, 4L, 5L};

        return Stream.of(ids).map(id -> DynamicTest.dynamicTest("DynamicTest: Find by ID " + id, () -> {
            DumbClass person = new DumbClass(id);
            Assertions.assertNotNull(person);
            Assertions.assertEquals(person.myMethod(), "toto");
        }));
    }
}
