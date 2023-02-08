package org.example.service.logic;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.Article;
import org.example.entity.ArticleVersion;
import org.example.service.sql.ArticleService;
import org.example.service.sql.ArticleVersionService;
import org.example.utils.DateUtils;
import org.example.utils.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AnalyzeLogicService {
    @Autowired
    private ArticleService articleService;

    @Autowired
    private ArticleVersionService articleVersionService;

    public Integer getRecentWordsInSeconds(Long number) {
        Date nowTime = new Date();
        LocalDateTime now = DateUtils.convertToLocalDateTimeViaInstant(nowTime);
        LocalDateTime past = now.minusSeconds(number);
        Date pastTime = DateUtils.convertToDateViaInstant(past);
        System.out.println("nowTime:" + nowTime);
        System.out.println("pastTime:" + pastTime);

        QueryWrapper<Article> articleQueryWrapper = new QueryWrapper<>();
        articleQueryWrapper.le("update_time", now);
        articleQueryWrapper.ge("update_time", past);
        List<Article> articles = articleService.list(articleQueryWrapper);
        List<Integer> ids = articles.stream().map(item -> item.getId()).collect(Collectors.toList());
        if (ids.isEmpty()) {
            return 0;
        }

        //查询指定时间段内的所有记录
        QueryWrapper<ArticleVersion> articleVersionQueryWrapper = new QueryWrapper<>();
        articleVersionQueryWrapper.in("article_id", ids);
        articleVersionQueryWrapper.le("create_time", now);
        articleVersionQueryWrapper.ge("create_time", past);
        articleVersionQueryWrapper.orderByAsc("version");
        List<ArticleVersion> list = articleVersionService.list(articleVersionQueryWrapper);
        log.info("ArticleVersion list:{}", list);

        //根据ArticleId分组
        Map<Integer, List<ArticleVersion>> map = list.stream().collect(Collectors.groupingBy(ArticleVersion::getArticleId));

        Integer length = 0;
        for (Map.Entry<Integer, List<ArticleVersion>> entry : map.entrySet()) {
            List<ArticleVersion> valueList = entry.getValue();
            if (valueList.size() == 1) {
                length = length + valueList.get(0).getLength();
            } else {
                List<ArticleVersion> sort = valueList;
                System.out.println(sort);
                Integer first = -1;
                Integer second = sort.size();
                for (int i = 0; i + 1 < sort.size(); i++) {
                    Date a1 = sort.get(i).getCreateTime();
                    Date a2 = sort.get(i + 1).getCreateTime();
                    if (a1.toInstant().isBefore(pastTime.toInstant()) && a2.toInstant().isAfter(pastTime.toInstant())) {
                        first = i;
                    }
                    if (a1.toInstant().isBefore(nowTime.toInstant()) && a2.toInstant().isAfter(nowTime.toInstant())) {
                        second = i;
                    }
                }
                if (first >= 0 && second < sort.size()) {
                    length = length + sort.get(second).getLength() - sort.get(first).getLength();
                    System.out.println("A1");
                } else if (first == -1 && second < sort.size()) {
                    length = length + sort.get(second).getLength();
                    System.out.println("A2");
                } else if (first == -1 && second == sort.size()) {
                    if (sort.get(sort.size() - 1).getCreateTime().toInstant().isBefore(pastTime.toInstant())) {
                        System.out.println("B1");
                    }
                    if (sort.get(0).getCreateTime().toInstant().isAfter(nowTime.toInstant())) {
                        System.out.println("B2");
                    }
                } else {
                    length = length + sort.get(sort.size() - 1).getLength() - sort.get(first).getLength();
                    System.out.println("A3");
                }
            }
        }
        return length;
    }

    public Integer getRecentWordsAllTime() {
        QueryWrapper<Article> articleQueryWrapper = new QueryWrapper<>();
        articleQueryWrapper.eq("valid", Valid.TRUE.getCode());
        List<Article> articles = articleService.list(articleQueryWrapper);
        List<Integer> ids = articles.stream().map(item -> item.getId()).collect(Collectors.toList());
        if (ids.isEmpty()) {
            return 0;
        }

        Integer length = 0;
        for (Integer id : ids) {
            ArticleVersion latestVersion = articleVersionService.getOne(new QueryWrapper<ArticleVersion>()
                    .eq("article_id", id)
                    .orderByDesc("version")
                    .last("limit 1")
            );
            length = length + latestVersion.getLength();
        }
        return length;
    }
}
