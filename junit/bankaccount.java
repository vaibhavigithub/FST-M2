package Activitiesclasses;

public class bankaccount {

        private Integer balance;

        // Create a constructor
        public bankaccount(Integer initialBalance) {
            balance = initialBalance;
        }

        // Add method to calculate
        // balance amount after withdrawal
        public Integer withdraw(Integer amount) {
            if (balance < amount) {
                throw new NotEnoughFundsException(amount, balance);
            }
            balance -= amount;
            return balance;
        }
    }

