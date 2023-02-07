package org.example.service.logic;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.example.entity.Article;
import org.example.entity.ArticleVersion;
import org.example.mapper.ArticleMapper;
import org.example.mapper.ArticleVersionMapper;
import org.example.utils.Valid;
import org.example.vo.ArticleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleLogicService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private ArticleVersionMapper articleVersionMapper;

    public ArticleVO getArticleById(Integer id) {
        Article article = articleMapper.selectById(id);
        if (article == null) {
            return null;
        }
        ArticleVersion latestVersion = articleVersionMapper.selectOne(new QueryWrapper<ArticleVersion>()
                .eq("article_id", id)
                .orderByDesc("version")
                .last("limit 1")
        );
        return new ArticleVO(article, latestVersion);
    }

    @Transactional
    public Boolean addArticle(String title, String content) {
        Article article = new Article();
        article.setTitle(title);
        article.setValid(Valid.TRUE.getCode());
        articleMapper.insert(article);

        ArticleVersion newVersion = new ArticleVersion();
        newVersion.setArticleId(article.getId());
        newVersion.setVersion(0);
        newVersion.setContent(content);
        newVersion.setCreateTime(new Date());
        articleVersionMapper.insert(newVersion);
        return Boolean.TRUE;
    }

    @Transactional
    public Boolean updateArticle(Integer articleId, String title, String content) {
        Article article = articleMapper.selectById(articleId);
        if (article == null) {
            return Boolean.FALSE;
        }
        article.setTitle(title);
        articleMapper.updateById(article);
        ArticleVersion latestVersion = articleVersionMapper.selectOne(new QueryWrapper<ArticleVersion>()
                .eq("article_id", articleId)
                .orderByDesc("version")
                .last("limit 1")
        );
        Integer version = latestVersion.getVersion();

        ArticleVersion newVersion = new ArticleVersion();
        newVersion.setArticleId(articleId);
        newVersion.setVersion(version + 1);
        newVersion.setContent(content);
        newVersion.setCreateTime(new Date());
        articleVersionMapper.insert(newVersion);
        return Boolean.TRUE;
    }

    public IPage<ArticleVO> getArticlesWithLatestVersion(Page<Article> page) {
        // 分页查询文章
        IPage<Article> articlePage = articleMapper.selectPage(page, new QueryWrapper<Article>().orderByDesc("update_time"));

        // 对于每一篇文章，查询它的最新版本
        List<ArticleVO> articleVOList = articlePage.getRecords().stream().map(article -> {
            ArticleVersion latestVersion = articleVersionMapper.selectOne(new QueryWrapper<ArticleVersion>()
                    .eq("article_id", article.getId())
                    .orderByDesc("version")
                    .last("limit 1")
            );

            return new ArticleVO(article, latestVersion);
        }).collect(Collectors.toList());

        IPage<ArticleVO> resultPage = new Page<>(articlePage.getCurrent(), articlePage.getSize(), articlePage.getTotal());
        resultPage.setRecords(articleVOList);
        return resultPage;
    }

    public IPage<ArticleVersion> getArticleVersions(Long articleId, Page<ArticleVersion> articleVersionPage) {
        // 查询每一个文章的所有版本，并按照版本号排序
        QueryWrapper<ArticleVersion> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("article_id", articleId);
        queryWrapper.orderByAsc("version");
        return articleVersionMapper.selectPage(articleVersionPage, queryWrapper);
    }


}
