package org.example.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WordCountTest {

    @Test
    public void testCN() {
        String sentence = "表 3-7  用户注册与登录用例描述 用例名称 用户注册或登录 用例描述 用户进行账号注册或账号登录 参与者 普通用户 前置条件 用户打开首页 基本事件流 （注册）用户输入电子邮箱、用户名、密码以完成注册表单；系统验证并存储用户信息，完成注册 （登录）用户输入电子邮箱、密码以完成登录表单；系统验证表单信息，完成登录 后置条件 用户成功登录系统并跳转至首页 ";
        System.out.println(WordCount.getMSWordsCount(sentence));
        assertEquals(160, WordCount.getMSWordsCount(sentence));
    }

    @Test
    public void testEN() {
        String sentence = "Word is ;a, hot ";
        System.out.println(WordCount.getMSWordsCount(sentence));
        assertEquals(4, WordCount.getMSWordsCount(sentence));
    }

}