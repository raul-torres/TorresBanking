package com.TorresBanking;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
//      Bank Introduction
        try {
            System.out.println("--------------------------------------------------------------");
            System.out.println("Hello and welcome to Torres Banking where your money can safely rest.");
            System.out.println("--------------------------------------------------------------");
            Thread.sleep(3000);
            System.out.flush();
        } catch(InterruptedException e){return;}
        BankAccount userAccount = BankAccount.OpenBankAccount();
        System.out.println("--------------------------------------------------------------");
        System.out.println("Welcome back " + userAccount.userName);
        userAccount.menu(userAccount);



    }
}

    class BankAccount {
        long balance = 0;
        ArrayList<Long> previousTransactions = new ArrayList<>();
        String userName;
        int userPin;
        boolean overDraft = false;

//        Bank Account constructor
        public BankAccount(String name, int pin) {
            userName = name;
            userPin = pin;
            previousTransactions.add(1L);
        }

        public static BankAccount OpenBankAccount() {
            Scanner scanner = new Scanner(System.in);
            String inputName;
            short inputPin = 0;

            System.out.println("Please enter your name.");
            do{
                inputName = scanner.nextLine();
                if(inputName.length() < 2 || inputName.length() > 15) {
                    System.out.println("Name must be between 2 to 15 characters long.");
                    System.out.println("Please enter your name.");
                }
            } while(inputName.length() < 2 || inputName.length() > 15);

            System.out.println("Please enter a 4 digit pin.");
            do{
                try{
                    inputPin = scanner.nextShort();
                    if(inputPin < 1000 || inputPin > 9999) {
                        System.out.println("Oops! It appears that is not 4 digits. Please enter a valid 4 digit pin.");
                    }
                } catch(InputMismatchException e) {
                    System.out.println("Pin can only be numbers. (4 Digits)");
                }
                scanner.nextLine();
            } while(inputPin < 1000 || inputPin > 9999);

            return new BankAccount(inputName, inputPin);
        }
//        Providing Menu option to the user
        public void menu(BankAccount userAccount) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("--------------------------------------------------------------");
            System.out.println("What can we help you with today?");
            System.out.println("--------------------------------------------------------------");
            System.out.println(" ");
            System.out.println("(A) Withdraw");
            System.out.println("(B) Deposit");
            System.out.println("(C) Check Balance");
            System.out.println("(D) Last Transactions");
            System.out.println("(E) Return Card");

            char inputOption = 'Z';
            while (inputOption == 'Z') {
                inputOption = scanner.next().charAt(0);
                inputOption = Character.toLowerCase(inputOption);

                switch (inputOption) {
                    case 'a':
                        userAccount.withdraw(userAccount);
                        break;
                    case 'b':
                        userAccount.deposit(userAccount);
                        break;
                    case 'c':
                        userAccount.checkBalance(userAccount);
                        break;
                    case 'd':
                        userAccount.lastTransaction(userAccount);
                        break;
                    case 'e':
                        userAccount.returnCard(userAccount);
                        break;
                    default:
                        System.out.println("Please enter a valid option");
                        inputOption = 'Z';
                        break;
                }
            }
        }

        Scanner scanner = new Scanner(System.in);
        public void withdraw(BankAccount userAccount) {
            System.out.println(" ");
            System.out.println("--------------------------------------------------------------");
            System.out.println("Please select an amount.");
            System.out.println("--------------------------------------------------------------");
            System.out.println("(A) Fast Cash: $25      (B) Fast Cash: $50");
            System.out.println("(C) Fast Cash: $75      (D) Fast Cash: $100");
            System.out.println("(E) Fast Cash: $200     (F) Fast Cash: $250");
            System.out.println("             (G) Other Amount");

            char inputOption = 'Z';
            while(inputOption == 'Z') {
                inputOption = scanner.next().charAt(0);
                inputOption = Character.toLowerCase(inputOption);
                switch(inputOption){
                    case 'a':
                        userAccount.fastCash(userAccount, 25);
                        break;
                    case 'b':
                        userAccount.fastCash(userAccount, 50);
                        break;
                    case 'c':
                        userAccount.fastCash(userAccount, 75);
                        break;
                    case 'd':
                        userAccount.fastCash(userAccount, 100);
                        break;
                    case 'e':
                        userAccount.fastCash(userAccount, 200);
                        break;
                    case 'f':
                        userAccount.fastCash(userAccount, 250);
                        break;
                    case 'g':
                        System.out.println("Enter an amount:");
                        int inputAmount = scanner.nextInt();
                        userAccount.fastCash(userAccount, inputAmount);
                    default:
                        System.out.println("Please enter a valid option");
                        inputOption = 'Z';
                        break;

                }
            }
        }
//      Making a deposit to the account
        public void deposit(BankAccount userAccount) {
            long depositAmount = 0;
            System.out.println("--------------------------------------------------------------");
            System.out.println("How much money would you like to deposit?");
            System.out.println("--------------------------------------------------------------");

            while(depositAmount == 0) {
                try {
                    depositAmount = scanner.nextInt();
                } catch(InputMismatchException e) {
                    System.out.println("This bank does not accept coins.");
                    System.out.println("Please enter a full dollar amount.");
                    depositAmount = 0;
                }
                scanner.nextLine();
            }

            if(depositAmount < 0) {
                System.out.println("Please provide an amount greater than 0.");
            } else {
                userAccount.balance = userAccount.balance + depositAmount;
                try {
                    System.out.println("Your new account balance is $" + balance);
                    if(userAccount.previousTransactions.size() > 11) {
                        int index = userAccount.previousTransactions.get(0).intValue();
                        userAccount.previousTransactions.set(index, depositAmount);
                        index = index++;
                        if(index > 10) {
                            index = 1;
                        }
                        userAccount.previousTransactions.set(0, (long)index);
                    } else {
                        userAccount.previousTransactions.add((long) depositAmount);
                    }
                    Thread.sleep(3000);
                    userAccount.menu(userAccount);
                } catch(InterruptedException ignored){}
            }


        }
//        Viewing current account balance
        public void checkBalance(BankAccount userAccount) {
            try {
                System.out.println("Your current account balance is $" + userAccount.balance);
                Thread.sleep(3000);
                userAccount.menu(userAccount);
            } catch(InterruptedException ignored){}
        }
//        Viewing the last transaction made
        public void lastTransaction(BankAccount userAccount) {

            System.out.println("--------------------------------------------------------------");
            System.out.println("Here are your last transactions.");
            for(int i = 1; i < userAccount.previousTransactions.size(); i++){
                long currentTransaction = userAccount.previousTransactions.get(i);
                if(currentTransaction > 0 ) {
                    System.out.println(" ");
                    System.out.println("Deposit: " + currentTransaction);
                } else if(currentTransaction < 0) {
                    System.out.println(" ");
                    currentTransaction *= -1;
                    System.out.println("Withdraw: " + currentTransaction);
                } else {
                    break;
                }
            }
            try{
                Thread.sleep(3000);
                userAccount.menu(userAccount);
            } catch(InterruptedException ignored){};

        }
//        Logging out of account
        public void returnCard(BankAccount userAccount) {
            try {
                System.out.println("Thank you " + userAccount.userName + " for choosing Torres Banking.");
                System.out.println("We hope you have a great day!");
                Thread.sleep(3000);
            } catch (InterruptedException ignored){}
        }
//        Method for fast cash
        public void fastCash(BankAccount userAccount, int amount) {
            long potentialBalance = userAccount.balance - amount;
            if(potentialBalance < 0 && potentialBalance > -100 && !userAccount.overDraft) {
                System.out.println("YOUR ACCOUNT IS IN RISK OF OVERDRAFT.");
                System.out.println("A $30 FEE WILL BE APPLIED TO YOUR ACCOUNT IF YOU DON'T MAKE A DEPOSIT");
                System.out.println("(A) Make a deposit      (B) Continue with Transaction ($30 Fee)");
                System.out.println("--------------------------------------------------------------");
                char inputOption = scanner.next().charAt(0);
                inputOption = Character.toLowerCase(inputOption);

                switch (inputOption) {
                    case 'a' -> {
                        userAccount.deposit(userAccount);
                        userAccount.menu(userAccount);
                    }
                    case 'b' -> {
                        potentialBalance -= 30;
                        userAccount.balance = potentialBalance;
                    }
                    default -> System.out.println("Please enter a valid option.");
                }

            } else if(potentialBalance <= -100){
                try {
                    System.out.println("Your current balance is $" + userAccount.balance);
                    System.out.println("Insufficient funds in your account.");
                    Thread.sleep(3000);
                } catch(InterruptedException ignored) {}
            }else {
                amount = -amount;
                userAccount.balance = potentialBalance;
                if(userAccount.previousTransactions.size() > 11) {
                    System.out.println("AMOUNT: " + amount);
                    int index = userAccount.previousTransactions.get(0).intValue();
                    userAccount.previousTransactions.set(index, (long) amount);
                    index = index++;
                    if(index > 10) {
                        index = 1;
                    }
                    userAccount.previousTransactions.set(0, (long)index);
                } else {
                    userAccount.previousTransactions.add((long) amount);
                }
            }
            try {
                System.out.println("Your new balance is $" + userAccount.balance);
                userAccount.menu(userAccount);
                Thread.sleep(3000);
            } catch (InterruptedException ignored) {}
        }
    }


