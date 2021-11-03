package com.geekbrains.lesson3;

import java.util.stream.Stream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ArithmeticUtilsTest {

    @BeforeAll
    static void beforeAll() {

    }

    private static Stream<Arguments> params() {
        return Stream.of(
                Arguments.of(1, 2, 3),
                Arguments.of(7, -7, 0),
                Arguments.of(5, 5, 10)
        );
    }

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {

    }

    @DisplayName("Тест суммы двух чисел")
    @ParameterizedTest(name = "{index}) {0} + {1} = {2}")
    @MethodSource("params")
    void sum(int x, int y, int expected) {
        int actually = ArithmeticUtils.sum(x, y);
        Assertions.assertEquals(expected, actually);
    }
}