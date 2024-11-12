package com.estudos.desafios.emprestimo.domain;

import com.estudos.desafios.emprestimo.factory.CustomerFactory;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CustomerTest {

    @Nested
    class isIncomeEqualOrLoweThan{
        @Test
        void shouldBeTrueWhenIncomeIsEqual() {
            var custumer = CustomerFactory.build(5000.0);
            assertTrue(custumer.isIncomeEqualOrLowerThan(5000.0));
        }

        @Test
        void shouldBeTrueWhenIncomeIsLowerThan() {
            var custumer = CustomerFactory.build(5000.0);
            assertTrue(custumer.isIncomeEqualOrLowerThan(6500.0));
        }

        @Test
        void shouldBeFalseWhenIncomeIsGreaterThanValue() {
            var custumer = CustomerFactory.build(5000.0);
            assertTrue(custumer.isIncomeEqualOrLowerThan(8000.0));
        }
    }

    @Nested
    class isIncomeEqualOrGreaterThan{
        @Test
        void shouldBeTrueWhenIncomeIsEqual() {
            var custumer = CustomerFactory.build(5000.0);
            assertTrue(custumer.isIncomeEqualOrGreaterThan(5000.0));
        }

        @Test
        void shouldBeTrueWhenIncomeIsGreaterThan() {
            var custumer = CustomerFactory.build(5000.0);
            assertTrue(custumer.isIncomeEqualOrGreaterThan(500.0));
        }

        @Test
        void shouldBeFalseWhenIncomeIsLowerThanValue() {
            var custumer = CustomerFactory.build(5000.0);
            assertFalse(custumer.isIncomeEqualOrGreaterThan(8000.0));
        }
    }

    @Nested
    class isIncomeBetween{
        @Test
        void shouldBeTrueWhenIncomeIsBetween() {
            var custumer = CustomerFactory.build(5000.0);
            assertTrue(custumer.isIncomeBetween(4900.0, 5500.0 ));
        }

        @Test
        void shouldBeFalseWhenIncomeIsNotBetween() {
            var custumer = CustomerFactory.build(5000.0);
            assertFalse(custumer.isIncomeBetween(1500.0, 2000.0));
        }

        @Test
        void shouldBeFalseWhenIncomeIsEqualToMin() {
            var custumer = CustomerFactory.build(5000.0);
            assertTrue(custumer.isIncomeBetween(5000.0, 6000.0));
        }

        @Test
        void shouldBeFalseWhenIncomeIsEqualToMax() {
            var custumer = CustomerFactory.build(6000.0);
            assertTrue(custumer.isIncomeBetween(5000.0, 6000.0));
        }
    }

    @Nested
    class isAgeLoweThan{
        @Test
        void shouldBeTrueWhenAgeIsLowerThan() {
            var custumer = CustomerFactory.build(18);
            assertTrue(custumer.isAgeLoweThan(25));
        }

        @Test
        void shouldBeFalseWhenAgeIsNotLowerThan() {
            var custumer = CustomerFactory.build(18);
            assertFalse(custumer.isAgeLoweThan(16));
        }

        @Test
        void shouldBeFalseWhenAgeIsEqualToValue() {
            var custumer = CustomerFactory.build(18);
            assertFalse(custumer.isAgeLoweThan(18));
        }
    }

    @Nested
    class isFromLocation{
        @Test
        void shouldBeTrueWhenLocationIsSame() {
            var custumer = CustomerFactory.build("SP");
            assertTrue(custumer.isFromLocation("SP"));
        }

        @Test
        void shouldBeFalseWhenLocationIsNotTheSame() {
            var custumer = CustomerFactory.build("SP");
            assertFalse(custumer.isFromLocation("RJ"));
        }
    }
}