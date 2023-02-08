package org.example.utils;
//https://blog.csdn.net/wqbs369/article/details/87284775
public class WordCount {
    /**
     * 类似word统计字符数
     *
     * @param context
     * @return
     */
    public static int getMSWordsCount(String context) {
        int words_count = 0;
        //中文单词
        String cn_words = context.replaceAll("[^(\\u4e00-\\u9fa5，。《》？；’‘：“”【】、）（……￥！·)]", "");
        int cn_words_count = cn_words.length();
        //非中文单词
        String non_cn_words = context.replaceAll("[^(a-zA-Z0-9`\\-=\';.,/~!@#$%^&*()_+|}{\":><?\\[\\])]", " ");
        int non_cn_words_count = 0;
        String[] ss = non_cn_words.split(" ");
        for (String s : ss) {
            if (s.trim().length() != 0) non_cn_words_count++;
        }
        //中文和非中文单词合计
        words_count = cn_words_count + non_cn_words_count;
        //ToolLog.d(ConstString.TAG, "汉字：" + cn_words_count + "\n\t字符：" + non_cn_words_count);
        return words_count;
    }
}
