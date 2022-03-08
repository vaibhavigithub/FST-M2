package Activities;
import Activitiesclasses.bankaccount;
import org.junit.jupiter.api.Test;
import Activitiesclasses.NotEnoughFundsException;

import static org.junit.jupiter.api.Assertions.*;
 class Activity2 {


        @Test
        void notEnoughFunds() {
            // Create an object for BankAccount class
            bankaccount account = new bankaccount(9);

            // Assertion for exception
            assertThrows(NotEnoughFundsException.class, () -> account.withdraw(10),
                    "Balance must be greater than amount of withdrawal");
        }

        @Test
        void enoughFunds() {
            // Create an object for BankAccount class
            bankaccount account = new bankaccount(100);

            // Assertion for no exceptions
            assertDoesNotThrow(() -> account.withdraw(100));
        }
    }

