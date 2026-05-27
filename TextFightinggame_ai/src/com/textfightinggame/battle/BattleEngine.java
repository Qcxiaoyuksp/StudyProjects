package com.textfightinggame.battle;

import com.textfightinggame.model.Consumable;
import com.textfightinggame.model.EnemyCharacter;
import com.textfightinggame.model.HeroCharacter;
import com.textfightinggame.util.RandomUtil;

import java.util.List;
import java.util.Scanner;

public class BattleEngine {
    public boolean runBattle(Scanner scanner, HeroCharacter hero, EnemyCharacter enemy) {
        System.out.println("\n=== 战斗开始 ⚔️ ===");
        while (hero.isAlive() && enemy.isAlive()) {
            printStatus(hero, enemy);
            playerTurn(scanner, hero, enemy);
            if (!enemy.isAlive()) {
                break;
            }
            enemyTurn(hero, enemy);
        }
        System.out.println("=== 战斗结束 ===\n");
        return hero.isAlive();
    }

    private void printStatus(HeroCharacter hero, EnemyCharacter enemy) {
        String heroHpBar = renderBar(hero.getHp(), hero.getMaxHP(), 20);
        String heroMpBar = renderBar(hero.getMp(), hero.getMaxMP(), 20);
        String enemyHpBar = renderBar(enemy.getHp(), enemy.getMaxHP(), 20);
        System.out.printf("玩家 %s HP %s %d/%d%n", hero.getName(), heroHpBar, hero.getHp(), hero.getMaxHP());
        System.out.printf("玩家 %s MP %s %d/%d%n", hero.getName(), heroMpBar, hero.getMp(), hero.getMaxMP());
        System.out.printf("敌人 %s HP %s %d/%d%n", enemy.getName(), enemyHpBar, enemy.getHp(), enemy.getMaxHP());
        System.out.println("--------------------");
    }

    private void playerTurn(Scanner scanner, HeroCharacter hero, EnemyCharacter enemy) {
        while (true) {
            System.out.println("1 普通攻击");
            System.out.println("2 强力一击 (10 MP)");
            System.out.println("3 生命汲取 (10 MP)");
            System.out.println("4 使用道具");
            System.out.print("请选择行动: ");
            String input = scanner.nextLine().trim();
            switch (input) {
                case "1" -> {
                    int damage = computeDamage(hero.getAttack(), enemy.getDefense());
                    int actualDamage = applyDamageToEnemy(enemy, damage);
                    System.out.printf("你使用普通攻击，造成 %d 点伤害。%n", actualDamage);
                    return;
                }
                case "2" -> {
                    if (!hero.spendMp(10)) {
                        System.out.println("MP 不足，技能施放失败。");
                        return;
                    }
                    int rawAttack = (int) Math.round(hero.getAttack() * 1.8);
                    int damage = computeDamage(rawAttack, enemy.getDefense());
                    int actualDamage = applyDamageToEnemy(enemy, damage);
                    System.out.printf("你使用强力一击 💥，造成 %d 点伤害。%n", actualDamage);
                    return;
                }
                case "3" -> {
                    if (!hero.spendMp(10)) {
                        System.out.println("MP 不足，技能施放失败。");
                        return;
                    }
                    int heal = RandomUtil.randomInt(0, 20);
                    hero.heal(heal);
                    System.out.printf("你使用生命汲取 ✨，回复 %d 点生命。%n", heal);
                    return;
                }
                case "4" -> {
                    useItem(scanner, hero);
                    return;
                }
                default -> System.out.println("输入有误，请重新选择。");
            }
        }
    }

    private void useItem(Scanner scanner, HeroCharacter hero) {
        List<Consumable> items = hero.getPackageList();
        if (items.isEmpty()) {
            System.out.println("背包为空，本回合未使用道具。");
            return;
        }
        System.out.println("背包道具:");
        for (Consumable item : items) {
            System.out.printf("- %s (+%d HP)%n", item.getName(), item.getNum());
        }
        System.out.print("请输入道具名: ");
        String name = scanner.nextLine().trim();
        Consumable target = hero.removeItemByName(name);
        if (target == null) {
            System.out.println("道具不存在，本回合未使用道具。");
            return;
        }
        hero.heal(target.getNum());
        System.out.printf("你使用 %s，回复 %d 点生命。%n", target.getName(), target.getNum());
    }

    private void enemyTurn(HeroCharacter hero, EnemyCharacter enemy) {
        boolean useSkill = RandomUtil.randomInt(0, 1) == 1;
        if (!useSkill) {
            int damage = computeDamage(enemy.getAttack(), hero.getDefense());
            hero.takeDamage(damage);
            System.out.printf("敌人使用普通攻击，造成 %d 点伤害。%n", damage);
            return;
        }

        switch (enemy.getSkill()) {
            case "猛击" -> {
                int rawAttack = (int) Math.round(enemy.getAttack() * 1.5);
                int damage = computeDamage(rawAttack, hero.getDefense());
                hero.takeDamage(damage);
                System.out.printf("敌人使用猛击 💥，造成 %d 点伤害。%n", damage);
            }
            case "快速攻击" -> {
                int hit = (int) Math.round(enemy.getAttack() * 0.5);
                int damage1 = computeDamage(hit, hero.getDefense());
                int damage2 = computeDamage(hit, hero.getDefense());
                int total = damage1 + damage2;
                hero.takeDamage(total);
                System.out.printf("敌人使用快速攻击 ⚡，造成 %d 点伤害。%n", total);
            }
            case "防御姿态" -> {
                if (enemy.isDefending()) {
                    System.out.println("敌人保持防御姿态 🛡️，下一次伤害减半。");
                } else {
                    enemy.setDefending(true);
                    System.out.println("敌人进入防御姿态 🛡️，下一次伤害减半。");
                }
            }
            case "火球术" -> {
                int rawAttack = (int) Math.round(enemy.getAttack() * 1.8);
                int damage = computeDamage(rawAttack, hero.getDefense());
                hero.takeDamage(damage);
                System.out.printf("敌人使用火球术 🔥，造成 %d 点伤害。%n", damage);
            }
            default -> System.out.println("敌人动作异常，错过了攻击。");
        }
    }

    private int computeDamage(int attack, int defense) {
        return Math.max(1, attack - defense);
    }

    private int applyDamageToEnemy(EnemyCharacter enemy, int damage) {
        int actualDamage = enemy.isDefending() ? Math.max(1, damage / 2) : damage;
        enemy.takeDamage(damage);
        return actualDamage;
    }

    private String renderBar(int current, int max, int length) {
        if (max <= 0) {
            return "[]";
        }
        int filled = (int) Math.round((current * 1.0 / max) * length);
        if (filled < 0) {
            filled = 0;
        }
        if (filled > length) {
            filled = length;
        }
        StringBuilder bar = new StringBuilder();
        bar.append('[');
        for (int i = 0; i < length; i++) {
            bar.append(i < filled ? '=' : '-');
        }
        bar.append(']');
        return bar.toString();
    }
}
