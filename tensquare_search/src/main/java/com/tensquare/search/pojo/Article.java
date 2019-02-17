package com.tensquare.search.pojo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import java.io.Serializable;

/**
 * @author LiangDong.
 */
@Setter
@Getter
@Document(indexName = "tensquare_article", type = "article")
public class Article implements Serializable {

    @Id
    private String id;

    /**
     * index为true表示该字段可以被搜索
     * 是否分词，表示搜索时是整体匹配还是单词匹配
     * 是否存储，是否在页面上显示
     * analyzer 与 searchAnalyzer 指定的分词名称必须一致
     */
    @Field(index = true, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String title;

    @Field(index = true, analyzer = "ik_max_word", searchAnalyzer = "ik_max_word")
    private String content;

    private String state;


}
