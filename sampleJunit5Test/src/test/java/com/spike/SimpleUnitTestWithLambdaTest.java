package com.spike;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class SimpleUnitTestWithLambdaTest {
    @Test
    void lambdaExpressions() {
        List<Integer> numbers = Arrays.asList(1, 2, 3);
        Assertions.assertTrue(numbers.stream()
                .mapToInt(i -> i)
                .sum() > 5, "Sum should be greater than 5");
    }

    @Disabled("test to show MultipleFailuresError")
    @Test
    void groupAssertions() {
        int[] numbers = {0, 1, 2, 3, 4};
        Assertions.assertAll("numbers", () -> Assertions.assertEquals(numbers[0], 1), () -> Assertions.assertEquals(numbers[3], 3), () -> Assertions.assertEquals(numbers[4], 1));
    }

    @Test
    @Disabled
    void disabledTest() {
        Assertions.assertTrue(false);
    }
}