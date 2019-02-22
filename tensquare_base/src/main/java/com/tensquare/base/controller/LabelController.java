package com.tensquare.base.controller;

import com.tensquare.base.pojo.Label;
import com.tensquare.base.service.LabelService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author LiangDong.
 */
@RestController
@CrossOrigin //解决跨域问题
@RequestMapping("/label")
@RefreshScope //解决配置中心更新自定义配置问题
public class LabelController {

    @Autowired
    private LabelService labelService;

    @Autowired
    private HttpServletRequest request;

    @Value("${ip}")
    private String ip;

    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        System.out.println("ip为：" + ip);
        String authorization = request.getHeader("Authorization") + "";
        System.out.println(authorization);
        return new Result(true, StatusCode.OK, "查询成功", labelService.findAll());
    }

    @RequestMapping(value = "/{labelId}", method = RequestMethod.GET)
    public Result findById(@PathVariable String labelId) {
        return new Result(true, StatusCode.OK, "查询成功", labelService.findById(labelId));
    }

    @RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestBody Label label) {
        labelService.save(label);
        return new Result(true, StatusCode.OK, "添加成功");
    }

    @RequestMapping(value = "/{labelId}", method = RequestMethod.PUT)
    public Result update(@PathVariable String labelId, @RequestBody Label label) {
        label.setId(labelId);
        labelService.update(label);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    @RequestMapping(value = "/{labelId}", method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable String labelId) {
        labelService.deleteById(labelId);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    @RequestMapping(value = "/search")
    public Result findSearch(@RequestBody Label label) {
        return new Result(true, StatusCode.OK, "查询成功", labelService.findSearch(label));
    }

    @RequestMapping(value = "/search/{page}/{size}")
    public Result pageQuery(@RequestBody Label label, @PathVariable Integer page, @PathVariable Integer size) {
        Page<Label> pageList = labelService.pageQuery(label, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<>(pageList.getTotalElements(), pageList.getContent()));
    }

}
