package com.zq.poem.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by SGXM on 2019/11/19.
 */

/**
* @Description: Util to check whether the specific string is consists of Chinese character.
* @Author: SGXM
* @Date: 2019/11/19
*/
public class CharacterUtil {
    /**
     *
     * @param string the string will be check
     * @return whether string only contains Chinese character
     */
public static Boolean checkCharacter(String string) {
    String regEx = "[\u4e00-\u9fa5]";
    Pattern pat = Pattern.compile(regEx);
    Matcher matcher = pat.matcher(string);
    int num = 0;
    while(matcher.find()){
        num++;
    }
    return num == string.length();

}
}
