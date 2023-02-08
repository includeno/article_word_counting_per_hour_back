package org.example.service.logic;

import org.example.entity.ArticleVersion;
import org.example.service.sql.ArticleVersionService;
import org.example.utils.DateUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class AnalyzeLogicServiceTest {
    @MockBean
    ArticleVersionService articleVersionService;
    @Autowired
    AnalyzeLogicService analyzeLogicService;

    @Test
    public void testGetRecentWords() {
        List<ArticleVersion> list = new ArrayList<>();
        list.add(new ArticleVersion(1,2, DateUtils.convertToDateViaInstant(LocalDateTime.now().minusMinutes(1).minusSeconds(1)),0));
        list.add(new ArticleVersion(1,5, DateUtils.convertToDateViaInstant(LocalDateTime.now().minusSeconds(4)),1));
        list.add(new ArticleVersion(1,1, DateUtils.convertToDateViaInstant(LocalDateTime.now().minusSeconds(3)),2));
        list.add(new ArticleVersion(1,4, DateUtils.convertToDateViaInstant(LocalDateTime.now().minusSeconds(2)),3));
        list.add(new ArticleVersion(1,7, DateUtils.convertToDateViaInstant(LocalDateTime.now().minusSeconds(1)),4));


        Mockito.doReturn(list).when(articleVersionService).list(Mockito.any());
        Integer length = analyzeLogicService.getRecentWordsInSeconds(60L);
        assertEquals(5, length);
    }

    @Test
    public void testGetRecentWordsRe() {
        List<ArticleVersion> list = new ArrayList<>();
        list.add(new ArticleVersion(1,2, DateUtils.convertToDateViaInstant(LocalDateTime.now().minusSeconds(5)),0));
        list.add(new ArticleVersion(1,5, DateUtils.convertToDateViaInstant(LocalDateTime.now().minusSeconds(4)),1));
        list.add(new ArticleVersion(1,1, DateUtils.convertToDateViaInstant(LocalDateTime.now().minusSeconds(3)),2));
        list.add(new ArticleVersion(1,4, DateUtils.convertToDateViaInstant(LocalDateTime.now().minusSeconds(2)),3));
        list.add(new ArticleVersion(1,7, DateUtils.convertToDateViaInstant(LocalDateTime.now().plusSeconds(1)),4));


        Mockito.doReturn(list).when(articleVersionService).list(Mockito.any());
        Integer length = analyzeLogicService.getRecentWordsInSeconds(60L);
        assertEquals(4, length);
    }

    @Test
    public void testGetRecentWords2() {
        List<ArticleVersion> list = new ArrayList<>();
        list.add(new ArticleVersion(1,2, DateUtils.convertToDateViaInstant(LocalDateTime.now().minusMinutes(1).minusSeconds(1)),0));
        list.add(new ArticleVersion(1,5, DateUtils.convertToDateViaInstant(LocalDateTime.now().minusSeconds(4)),1));
        list.add(new ArticleVersion(1,1, DateUtils.convertToDateViaInstant(LocalDateTime.now().minusSeconds(3)),2));
        list.add(new ArticleVersion(1,4, DateUtils.convertToDateViaInstant(LocalDateTime.now().minusSeconds(2)),3));
        list.add(new ArticleVersion(1,7, DateUtils.convertToDateViaInstant(LocalDateTime.now().plusSeconds(1)),4));


        Mockito.doReturn(list).when(articleVersionService).list(Mockito.any());
        Integer length = analyzeLogicService.getRecentWordsInSeconds(60L);
        assertEquals(2, length);
    }

    @Test
    public void testGetRecentWords3() {
        List<ArticleVersion> list = new ArrayList<>();
        list.add(new ArticleVersion(1,2, DateUtils.convertToDateViaInstant(LocalDateTime.now().minusHours(1).minusSeconds(1)),0));
        list.add(new ArticleVersion(1,5, DateUtils.convertToDateViaInstant(LocalDateTime.now().minusHours(4)),1));
        list.add(new ArticleVersion(1,1, DateUtils.convertToDateViaInstant(LocalDateTime.now().minusHours(3)),2));
        list.add(new ArticleVersion(1,4, DateUtils.convertToDateViaInstant(LocalDateTime.now().minusHours(2)),3));
        list.add(new ArticleVersion(1,7, DateUtils.convertToDateViaInstant(LocalDateTime.now().minusHours(1)),4));


        Mockito.doReturn(list).when(articleVersionService).list(Mockito.any());
        Integer length = analyzeLogicService.getRecentWordsInSeconds(60L);
        assertEquals(0, length);
    }

    @Test
    public void testGetRecentWords4() {
        List<ArticleVersion> list = new ArrayList<>();
        list.add(new ArticleVersion(1,2, DateUtils.convertToDateViaInstant(LocalDateTime.now().plusSeconds(1)),0));
        list.add(new ArticleVersion(1,5, DateUtils.convertToDateViaInstant(LocalDateTime.now().plusSeconds(4)),1));
        list.add(new ArticleVersion(1,1, DateUtils.convertToDateViaInstant(LocalDateTime.now().plusSeconds(5)),2));
        list.add(new ArticleVersion(1,4, DateUtils.convertToDateViaInstant(LocalDateTime.now().plusSeconds(6)),3));
        list.add(new ArticleVersion(1,7, DateUtils.convertToDateViaInstant(LocalDateTime.now().plusSeconds(7)),4));


        Mockito.doReturn(list).when(articleVersionService).list(Mockito.any());
        Integer length = analyzeLogicService.getRecentWordsInSeconds(60L);
        assertEquals(0, length);
    }
}