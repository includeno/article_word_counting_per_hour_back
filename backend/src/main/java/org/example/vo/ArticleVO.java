package org.example.vo;

import lombok.Data;
import org.example.entity.Article;
import org.example.entity.ArticleVersion;

import java.util.Date;

@Data
public class ArticleVO {

    //文章id
    private Integer id;
    private String title;
    private String content;
    private Date createTime;
    private Integer version;

    public ArticleVO(Article article, ArticleVersion latestVersion) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = latestVersion.getContent();
        this.version = latestVersion.getVersion();
        this.createTime = latestVersion.getCreateTime();
    }
}
