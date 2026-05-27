package com.textfightinggame.model;

import java.util.ArrayList;
import java.util.List;

public class HeroCharacter extends Character {
    private int mp;
    private int maxMP;
    private final List<String> skillList = new ArrayList<>();
    private final List<Consumable> packageList = new ArrayList<>();

    public HeroCharacter(String name, int hp, int maxHP, int attack, int defense, int mp, int maxMP) {
        super(name, hp, maxHP, attack, defense);
        this.mp = mp;
        this.maxMP = maxMP;
    }

    public boolean spendMp(int cost) {
        if (mp < cost) {
            return false;
        }
        mp -= cost;
        return true;
    }

    public void addMp(int amount) {
        if (amount <= 0) {
            return;
        }
        mp = Math.min(maxMP, mp + amount);
    }

    public void addSkill(String skill) {
        skillList.add(skill);
    }

    public List<String> getSkillList() {
        return skillList;
    }

    public List<Consumable> getPackageList() {
        return packageList;
    }

    public void addItem(Consumable item) {
        packageList.add(item);
    }

    public Consumable removeItemByName(String name) {
        for (int i = 0; i < packageList.size(); i++) {
            if (packageList.get(i).getName().equals(name)) {
                return packageList.remove(i);
            }
        }
        return null;
    }

    public int getMp() {
        return mp;
    }

    public int getMaxMP() {
        return maxMP;
    }

    public void setMaxMP(int maxMP) {
        this.maxMP = maxMP;
    }

    public void setMp(int mp) {
        this.mp = Math.min(mp, maxMP);
    }

    @Override
    public void show() {
        System.out.printf("%s HP: %d/%d MP: %d/%d ATK: %d DEF: %d%n",
                name, hp, maxHP, mp, maxMP, attack, defense);
    }
}

