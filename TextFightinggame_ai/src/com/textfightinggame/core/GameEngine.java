package com.textfightinggame.core;

import com.textfightinggame.battle.BattleEngine;
import com.textfightinggame.battle.EnemyFactory;
import com.textfightinggame.model.Consumable;
import com.textfightinggame.model.EnemyCharacter;
import com.textfightinggame.model.HeroCharacter;
import com.textfightinggame.model.User;
import com.textfightinggame.util.RandomUtil;
import com.textfightinggame.util.TauntUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GameEngine {
    private static final List<Consumable> DROP_POOL = new ArrayList<>();

    static {
        DROP_POOL.add(new Consumable("桃子", 10));
        DROP_POOL.add(new Consumable("煎蛋", 20));
        DROP_POOL.add(new Consumable("花酿鸡", 30));
        DROP_POOL.add(new Consumable("黑白鲈鱼", 40));
        DROP_POOL.add(new Consumable("白玉汤", 50));
    }

    private final BattleEngine battleEngine = new BattleEngine();

    public void run(Scanner scanner, User user) {
        System.out.println("=== 文字格斗游戏 ===");
        System.out.println("欢迎你，" + user.getUsername() + "！🎮\n");
        HeroCharacter hero = createHero(scanner, user.getUsername());

        int wins = 0;
        int battleCount = 0;
        boolean keepPlaying = true;
        while (keepPlaying && hero.isAlive()) {
            battleCount++;
            EnemyCharacter enemy = EnemyFactory.randomEnemy();
            if (battleCount >= 2) {
                EnemyFactory.applyGrowth(enemy);
            }
            boolean win = battleEngine.runBattle(scanner, hero, enemy);
            if (win) {
                wins++;
                applyVictoryRewards(hero);
                if (wins % 3 == 0) {
                    applyHeroGrowth(hero);
                }
                keepPlaying = askContinue(scanner);
            } else {
                System.out.println("你被击败了！💀");
                System.out.println(TauntUtil.randomTaunt());
                break;
            }
        }
        System.out.println("游戏结束，总胜场数: " + wins + "。");
    }

    private HeroCharacter createHero(Scanner scanner, String name) {
        int baseHP = 100;
        int baseAttack = 10;
        int baseDefense = 0;
        int baseMP = 30;

        int remaining = 20;
        int hpPoints = readAllocation(scanner, "生命值", remaining, "每点 +10 HP");
        remaining -= hpPoints;
        int atkPoints = readAllocation(scanner, "攻击力", remaining, "每点 +2 ATK");
        remaining -= atkPoints;
        int defPoints = readAllocation(scanner, "防御力", remaining, "每点 +1 DEF");
        remaining -= defPoints;
        int mpPoints = readAllocation(scanner, "蓝条", remaining, "每点 +5 MP");

        int maxHP = baseHP + hpPoints * 10;
        int attack = baseAttack + atkPoints * 2;
        int defense = baseDefense + defPoints;
        int maxMP = baseMP + mpPoints * 5;

        HeroCharacter hero = new HeroCharacter(name, maxHP, maxHP, attack, defense, maxMP, maxMP);
        hero.addSkill("普通攻击");
        hero.addSkill("强力一击");
        hero.addSkill("生命汲取");

        System.out.println("\n角色创建完成 🎯:");
        hero.show();
        System.out.println();
        return hero;
    }

    private int readAllocation(Scanner scanner, String label, int remaining, String perPointTip) {
        if (remaining <= 0) {
            return 0;
        }
        System.out.printf("分配%s点数 (%s，剩余 %d): ", label, perPointTip, remaining);
        String input = scanner.nextLine().trim();
        int value;
        try {
            value = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            value = 0;
        }
        if (value < 0) {
            value = 0;
        }
        if (value > remaining) {
            value = remaining;
        }
        return value;
    }

    private void applyVictoryRewards(HeroCharacter hero) {
        int heal = RandomUtil.randomInt(20, 40);
        hero.heal(heal);
        int mpRecover = (int) Math.round(hero.getMaxMP() * 0.3);
        hero.addMp(mpRecover);
        System.out.printf("胜利结算：恢复 %d HP，恢复 %d MP。🏆%n", heal, mpRecover);
        if (RandomUtil.randomInt(1, 3) == 1) {
            Consumable drop = DROP_POOL.get(RandomUtil.randomInt(0, DROP_POOL.size() - 1));
            hero.addItem(new Consumable(drop.getName(), drop.getNum()));
            System.out.println("获得道具: " + drop.getName());
        } else {
            System.out.println("没有掉落道具。");
        }
        System.out.println();
    }

    private void applyHeroGrowth(HeroCharacter hero) {
        hero.setMaxHP(hero.getMaxHP() + 30);
        hero.setHp(hero.getHp() + 30);
        hero.setAttack(hero.getAttack() + 5);
        hero.setDefense(hero.getDefense() + 3);
        hero.setMaxMP(hero.getMaxMP() + 10);
        hero.setMp(hero.getMp() + 10);
        System.out.println("恭喜！累计胜利 3 场，属性提升！🎉");
        hero.show();
        System.out.println();
    }

    private boolean askContinue(Scanner scanner) {
        System.out.print("是否继续下一场战斗？(Y/N): ");
        String input = scanner.nextLine().trim();
        if ("N".equalsIgnoreCase(input)) {
            return false;
        }
        return true;
    }
}
