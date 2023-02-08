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
    private String md5;
    private Integer length;
    private Date createTime;
    private Integer version;

    public ArticleVersion() {

    }

    public ArticleVersion(Integer articleId, Integer length, Date createTime, Integer version) {
        this.articleId = articleId;
        this.length = length;
        this.createTime = createTime;
        this.version = version;
    }
}