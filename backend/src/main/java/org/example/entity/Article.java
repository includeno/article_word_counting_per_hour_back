package org.example.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("articles")
public class Article {
    @TableId
    private Integer id;
    private String title;
    private Integer valid;
}
