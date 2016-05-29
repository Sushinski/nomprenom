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

import java.util.HashMap;
import java.util.Map;

public class NamePatrComp {
    public final String wovels;

    public NamePatrComp(Context context){
        wovels = context.getResources().getString(R.string.vowels);
    }

    public boolean isWowel(char ch){
        return wovels.indexOf(ch) >= 0;
    }

    public boolean isConsonant(char ch){
        return !isWowel(ch);
    }
    public int compare(String name, String patronymic, boolean isMale){
        // 1
        int res = 0; // compability percentage
        char name_last = name.charAt(name.length()-1);
        char patr_first = patronymic.charAt(0);
        boolean is_name_end_vowel = isWowel(name_last);
        boolean is_surname_start_vowel = isWowel(patr_first);
        if((is_name_end_vowel != is_surname_start_vowel) ||
               !isMale )
            res += 30;
        // 3
        int entire_len = name.length()+patronymic.length();
        HashMap<Character, Integer> ch_freq = new HashMap<>(entire_len, 1);
        for (int i = 0; i < name.length(); ++i) {
            Character ch = name.charAt(i);
            if(isConsonant(Character.toLowerCase(ch))) {
                Integer fr = ch_freq.get(ch);
                if (fr != null)
                    fr += 1;
                else
                    fr = 1;
                ch_freq.put(ch, fr);
            }
        }
        // todo подсчет
        int incr = 100-res/entire_len;
        for (Map.Entry<Character, Integer> e : ch_freq.entrySet() ) {
            if(e.getValue() > 1)
                res += incr * e.getValue();
        }
        return res;
    }

}
