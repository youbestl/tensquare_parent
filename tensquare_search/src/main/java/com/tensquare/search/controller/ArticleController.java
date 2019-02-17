package com.tensquare.search.controller;

import com.tensquare.search.pojo.Article;
import com.tensquare.search.service.ArticleService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * @author LiangDong.
 */
@RestController
@RequestMapping("/article")
@CrossOrigin
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Article article) {
        articleService.add(article);
        return new Result(true, StatusCode.OK, "添加成功");
    }

    @RequestMapping(value = "/search/{key}/{page}/{size}", method = RequestMethod.GET)
    public Result findByKey(@PathVariable("key") String key,
                            @PathVariable("page") Integer page,
                            @PathVariable("size") Integer size) {
        Page<Article> pageResult = articleService.findByKey(key, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<>(pageResult.getTotalElements(), pageResult.getContent()));
    }
}
