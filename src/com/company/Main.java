package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // write your code here
        Main obj = new Main();
        Scanner sc = new Scanner(System.in);


        //spillernes start position
        int playerPosition = -10;
        int enemyPosition = 10;

        //Spillernes firePower
        int playerFirePower = 2500;
        int enemyFirePower = 2500;

        //Spillernes soldater
        int playerSoldier = 25;
        int enemySoldier = 25;

        /*
        spiller valg mellem 3 muligheder
        1. Ryk Frem
        2. Angrib
        3. Træk tilbage
         */
        while (enemySoldier > 0) { // Så længe fjendens liv er over 0 så tag en runde mere
            if (playerSoldier < 0) {
                System.out.println("Game over");
                break;
            }
            System.out.println("Choose between:\nAttack = 'a'\nMove forward = 'm'\nBack off = 'b'");
            System.out.print("Choose your next move: ");
            char nextMove = sc.next().charAt(0);
            System.out.println();

            if (nextMove == 'm') {
                playerPosition = obj.moveForward(playerPosition);
            } else if (nextMove == 'a') {
                playerFirePower = obj.attackFirePower(playerFirePower);
                enemySoldier = obj.attack(playerPosition, enemyPosition, enemySoldier, playerFirePower); // lav en en methode til attack
            } else if (nextMove == 'b') {
                playerFirePower += 250;
                playerPosition = obj.backOff(playerPosition); // lav en methode til at trække tilbage
            }
            System.out.println("\n\n\n");
            int nextEnemyMove = obj.enemyChoice();
            if (nextEnemyMove == 1) {
                enemyPosition = obj.enemyMoveForward(enemyPosition);
            } else if (nextEnemyMove == 2) {
                enemyFirePower = obj.attackFirePower(enemyFirePower);
                playerSoldier = obj.attack(enemyPosition, playerPosition, playerSoldier, enemyFirePower); // lav en en methode til attack
            } else if (nextEnemyMove == 3) {
                enemyFirePower += 250;
                enemyPosition = obj.backOff(enemyPosition);
            }

        }
        System.out.println("You have won the game!");

/*
        int enemyNextMove = obj.enemyChoice();
        if (enemyNextMove == 1){
            obj.enemyMoveForward(enemyPosition);
        } else if(enemyNextMove == 2){
            obj.enemyAttack(enemyPosition); // lav en en methode til attack
        } else if(enemyNextMove == 3){
            obj.enemyBackOff(enemyPosition); // lav en methode til at trække tilbage
        }
*/
    }

    public int throwDice() {
        int dice = (int) (Math.random() * 6 + 1);
        return dice;
    }

    public int enemyChoice() {
        int choice = (int) (Math.random() * 3 + 1);
        return choice;
    }

    public int moveForward(int currentPosition) {
        int diceThrow = throwDice();
        System.out.print("You rolled a: " + diceThrow + " ");
        if (diceThrow % 2 == 0) {
            currentPosition += 2;
        } else {
            currentPosition += 1;
        }
        if (currentPosition > 10) {
            currentPosition = 10;
        } else if (currentPosition < -10) {
            currentPosition = -10;
        }
        System.out.println("Your current position is: " + currentPosition);
        return currentPosition;

    }

    public int enemyMoveForward(int currentPosition) {
        int diceThrow = throwDice();
        System.out.print("Enemy rolled a: " + diceThrow + " ");
        if (diceThrow % 2 == 0) {
            currentPosition -= 2;
        } else {
            currentPosition -= 1;
        }
        if (currentPosition > 10) {
            currentPosition = 10;
        } else if (currentPosition < -10) {
            currentPosition = -10;
        }
        System.out.println("Enemy position is: " + currentPosition);
        return currentPosition;
    }

    public int attack(int currentPosition, int enemyPosition, int enemySoldier, int playerFirePower) {
        int currentEnemySoldiers = enemySoldier;

        int close1enemy = enemyPosition + 1;
        int close2enemy = enemyPosition - 1;

        if (playerFirePower < 0) {
            return currentPosition;
        }

        if (currentPosition == enemyPosition) {
            enemySoldier -= 6;
        } else if (currentPosition == close1enemy) {
            enemySoldier -= 5;
        } else if (currentPosition == close2enemy) {
            enemySoldier -= 5;
        }
        int enemySoldiersLeft = currentEnemySoldiers - enemySoldier;

        System.out.println("You have killed: " + enemySoldiersLeft);
        System.out.println("Enemy soldiers left: " + enemySoldier);
        return enemySoldier;


    }
    public int Enemyattack(int currentPosition, int enemyPosition, int enemySoldier, int playerFirePower) {
        int currentEnemySoldiers = enemySoldier;

        int close1enemy = enemyPosition + 1;
        int close2enemy = enemyPosition - 1;

        if (playerFirePower < 0) {
            return currentPosition;
        }

        if (currentPosition == enemyPosition) {
            enemySoldier += 6;
        } else if (currentPosition == close1enemy) {
            enemySoldier += 5;
        } else if (currentPosition == close2enemy) {
            enemySoldier += 5;
        }
        int enemySoldiersLeft = currentEnemySoldiers - enemySoldier;

        System.out.println("You have killed: " + enemySoldiersLeft);
        System.out.println("Enemy soldiers left: " + enemySoldier);
        return enemySoldier;
    }
    public int attackFirePower(int playerFirePower) {
        int dice = throwDice();
        System.out.println("You have rolled a: " + dice + " ");

        int firePower = dice * 100;
        System.out.println("You have used: " + firePower + " of your firepower");
        int newFirePower = playerFirePower - firePower;
        System.out.println("Current firepower remaining: " + newFirePower);
        System.out.println();
        return newFirePower;
    }
    public int enemyAttackFirePower(int playerFirePower) {
        int dice = throwDice();
        System.out.println("Enemy has rolled a: " + dice + " ");

        int firePower = dice * 100;
        System.out.println("Enemy has used: " + firePower + " of your firepower");
        int newFirePower = playerFirePower - firePower;
        System.out.println("Current firepower remaining: " + newFirePower);
        System.out.println();
        return newFirePower;
    }

    public int backOff(int currentPositon) {
        //Trække sig tilbage mod egen lejr.
        //Et terningkast afgør hvor mange felter: 1-2 = 1 felt, 3-4 = 2 felter, 5-6 = 3 felter.
        // Under tilbagetrækning modtager man ny ildkraft (250point pr. træk).
        int dice = throwDice();
        System.out.println("You rolled a: " + dice);
        int startPosition = currentPositon;

        if (dice < 3) {
            currentPositon -= 1;
        } else if (2 < dice && dice < 5) {
            currentPositon -= 2;
        } else {
            currentPositon -= 3;
        }
        if (currentPositon > 10) {
            currentPositon = 10;
        } else if (currentPositon < -10) {
            currentPositon = -10;
        }

        int newPosition = startPosition - currentPositon;
        System.out.println("You have moved: " + newPosition);
        System.out.println("You are now in positon: " + currentPositon);
        return currentPositon;
    }

    public int enemyBackOff(int currentPositon){
        int dice = throwDice();
        System.out.println("Enemy rolled a: " + dice);
        int startPosition = currentPositon;

        if (dice < 3) {
            currentPositon += 1;
        } else if (2 < dice && dice < 5) {
            currentPositon += 2;
        } else {
            currentPositon += 3;
        }
        if (currentPositon > 10) {
            currentPositon = 10;
        } else if (currentPositon < -10) {
            currentPositon = -10;
        }

        int newPosition = startPosition - currentPositon;
        System.out.println("Enemy has moved moved: " + newPosition);
        System.out.println("Enemy is now in positon: " + currentPositon);
        return currentPositon;
    }
}