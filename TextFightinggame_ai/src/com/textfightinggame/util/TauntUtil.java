package com.textfightinggame.util;

public class TauntUtil {
    private static final String[] TAUNTS = {
            "就这？再练练吧！",
            "别气馁，失败也是经验。",
            "今天手感不太行啊。",
            "你被打懵了，醒醒！",
            "再来一局，你会更强。",
            "这点实力还想挑战我？",
            "别放弃，下一次肯定更好。",
            "胜负已分，回去补补课。",
            "小心点，敌人可不会手软。",
            "别灰心，命运还在等待翻盘。"
    };

    public static String randomTaunt() {
        int index = RandomUtil.randomInt(0, TAUNTS.length - 1);
        return TAUNTS[index];
    }
}
