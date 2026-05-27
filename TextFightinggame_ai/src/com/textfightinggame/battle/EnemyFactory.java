package com.textfightinggame.battle;

import com.textfightinggame.model.EnemyCharacter;
import com.textfightinggame.util.RandomUtil;

import java.util.ArrayList;
import java.util.List;

public class EnemyFactory {
    private static final List<EnemyCharacter> BASE_ENEMIES = new ArrayList<>();

    static {
        BASE_ENEMIES.add(new EnemyCharacter("初级战士", 80, 80, 15, 10, "猛击"));
        BASE_ENEMIES.add(new EnemyCharacter("敏捷刺客", 60, 60, 20, 5, "快速攻击"));
        BASE_ENEMIES.add(new EnemyCharacter("重装坦克", 120, 120, 10, 20, "防御姿态"));
        BASE_ENEMIES.add(new EnemyCharacter("神秘法师", 70, 70, 25, 58, "火球术"));
    }

    public static EnemyCharacter randomEnemy() {
        int index = RandomUtil.randomInt(0, BASE_ENEMIES.size() - 1);
        EnemyCharacter template = BASE_ENEMIES.get(index);
        return new EnemyCharacter(template.getName(), template.getMaxHP(), template.getMaxHP(),
                template.getAttack(), template.getDefense(), template.getSkill());
    }

    public static void applyGrowth(EnemyCharacter enemy) {
        enemy.setMaxHP(enemy.getMaxHP() + 10);
        enemy.setHp(enemy.getMaxHP());
        enemy.setAttack(enemy.getAttack() + 3);
        enemy.setDefense(enemy.getDefense() + 2);
        enemy.setDefending(false);
    }
}
