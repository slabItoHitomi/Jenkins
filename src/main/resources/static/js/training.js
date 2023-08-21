/**
 * HTMLタグを除去する。
 *
 * @param sentence 対象の文字列
 * @returns {string} HTMLタグを除去した文字列
 */
function removeTags(sentence){
    sentence = sentence.replace(/<("[^"]*"|'[^']*'|[^'">])*>/g,'');
    return sentence;
}

/**
 * 改行コードを改行タグに変換する。
 *
 * @param sentence 対象の文字列
 * @returns {string} 変換後の文字列
 */
function convertBr(sentence){
    sentence = sentence.replace(/\r?\n/g, '<br>');
    return sentence;
}

