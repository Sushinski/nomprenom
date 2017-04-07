/*
 * created by Pavel Golubev golubev.pavel.spb@gmail.com
 * no license applied
 * You may use this file without any restrictions
 */

package com.nomprenom2.utils;

/*
1) если отчество начинается на гласный звук, имя должно оканчиваться только на согласный, и наоборот.
 Не рекомендуется сочетание: Илья Андреевич, это имя лучше подойдет к отчеству с согласной: Илья Сергеевич;

2) исключения составляют женские имена. Если отчество начинается на гласный,
 тут уж ничего не поделаешь – в русской именной системе разве что имя Нинель заканчивается на согласный;

3) на стыке имени и отчества не должно быть скопления согласных,
 а также в имени и отчестве не должны повторяться одни и те же согласные звуки:
  Ричард Григорьевич (повторение [р] делает имя труднопроизносимым);
 */

import android.content.Context;

import com.nomprenom2.R;
import com.nomprenom2.model.NameRecord;
import com.nomprenom2.model.ZodiacRecord;

import java.util.HashMap;

/**
 * Localized (only russian now) linguistic compare class for name and patronymic
 */
class NamePatrComp {
    private final String vowels;
    private final String consonants;

    NamePatrComp(Context context) {
        vowels = context.getResources().getString(R.string.vowels);
        consonants = context.getResources().getString(R.string.consonants);
    }

    /**
     * checks if character is vowel
     *
     * @param ch Character to check
     * @return
     */
    private boolean isVowel(char ch) {
        return vowels.indexOf(ch) >= 0;
    }

    /**
     * Checks if character is consonant
     *
     * @param ch Character to check
     * @return
     */
    private boolean isConsonant(char ch) {
        return consonants.indexOf(ch) >= 0;
    }

    /**
     * Checks compatibility for name and patronymic
     *
     * @param nr   Name model object to check
     * @param patr Patronymic to compare name to
     * @param sex  Gender fo name to check
     * @param zod  Zodiac for name to check
     * @return Compatibility in percentages
     */
    int compare(NameRecord nr,
                String patr, NameRecord.Sex sex, ZodiacRecord.ZodMonth zod) {
        String name = nr.name.toLowerCase();
        if (!isConsonant(name.charAt(0)) && !isVowel(name.charAt(0)))
            return 0; // not our language
        String patronymic = patr.toLowerCase();
        if (patronymic.equals(""))
            return 0;
        if (!isConsonant(patronymic.charAt(0)) && !isVowel(patronymic.charAt(0)))
            return 0; // not our language
        // 1
        int res = 100; // compability percentage
        int start = 1; // don't check last letter for girls
        if (sex == null || sex == NameRecord.Sex.Boy)
            start = 0;
        int len = name.length() > 5 ? 3 : 1; // limit letters to check for short names
        for (int i = start; i < len; ++i) {
            char name_last = name.charAt(name.length() - i - 1);
            char patr_first = patronymic.charAt(i);
            if (isVowel(name_last) == isVowel(patr_first))
                res -= 10;
        }

        // count equal consonants
        int entire_len = name.length() + patronymic.length();
        HashMap<Character, Integer> ch_freq = new HashMap<>(entire_len, 1);
        for (int i = 0; i < name.length(); ++i) {
            Character ch = name.charAt(i);
            if (isConsonant(ch)) {
                Integer fr = ch_freq.get(ch);
                if (fr != null)
                    fr += 1;
                else
                    fr = 1;
                ch_freq.put(ch, fr);
            }
        }

        for (int i = 0; i < patronymic.length(); ++i) {
            Character ch = patronymic.charAt(i);
            if (isConsonant(ch)) {
                Integer fr = ch_freq.get(ch);
                if (fr != null && fr > 2)
                    res -= 5;
            }
        }
        return res;
    }

}
