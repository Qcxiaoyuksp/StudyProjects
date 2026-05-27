package com.textfightinggame.model;

public class EnemyCharacter extends Character {
    private final String skill;
    private boolean defending;

    public EnemyCharacter(String name, int hp, int maxHP, int attack, int defense, String skill) {
        super(name, hp, maxHP, attack, defense);
        this.skill = skill;
        this.defending = false;
    }

    @Override
    public void takeDamage(int damage) {
        if (damage <= 0) {
            return;
        }
        int actualDamage = damage;
        if (defending) {
            actualDamage = Math.max(1, damage / 2);
            defending = false;
        }
        hp = Math.max(0, hp - actualDamage);
    }

    public String getSkill() {
        return skill;
    }

    public boolean isDefending() {
        return defending;
    }

    public void setDefending(boolean defending) {
        this.defending = defending;
    }
}

