package com.textfightinggame.model;

public class Character {
    protected String name;
    protected int hp;
    protected int maxHP;
    protected int attack;
    protected int defense;

    public Character(String name, int hp, int maxHP, int attack, int defense) {
        this.name = name;
        this.hp = hp;
        this.maxHP = maxHP;
        this.attack = attack;
        this.defense = defense;
    }

    public boolean isAlive() {
        return hp > 0;
    }

    public void heal(int amount) {
        if (amount <= 0) {
            return;
        }
        hp = Math.min(maxHP, hp + amount);
    }

    public void takeDamage(int damage) {
        if (damage <= 0) {
            return;
        }
        hp = Math.max(0, hp - damage);
    }

    public void show() {
        System.out.printf("%s HP: %d/%d ATK: %d DEF: %d%n", name, hp, maxHP, attack, defense);
    }

    public String getName() {
        return name;
    }

    public int getHp() {
        return hp;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public void setMaxHP(int maxHP) {
        this.maxHP = maxHP;
    }

    public void setHp(int hp) {
        this.hp = Math.min(hp, maxHP);
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }
}

