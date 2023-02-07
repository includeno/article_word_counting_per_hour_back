package org.example.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("article_versions")
public class ArticleVersion {
    @TableId
    private Integer id;
    private Integer articleId;
    private String content;
    private Date createTime;
    private Integer version;
}