package today.yapeteam.font

import net.minecraft.util.Identifier

/**
 * @author yuxiangll
 * @since 2024/6/10 上午7:47
 * IntelliJ IDEA
 */

class Texture(path: String) : Identifier(
    "coffee",
    if (isValid(path)) path
    else path
        .lowercase()
        .toCharArray()
        .fold(StringBuilder()) { acc, char -> acc.append(if (isPathCharacterValid(char)) char else "") }
        .toString()) {


}