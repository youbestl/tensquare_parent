package com.tensquare.user.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import javax.servlet.http.HttpServletRequest;

import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import util.IdWorker;

import com.tensquare.user.dao.AdminDao;
import com.tensquare.user.pojo.Admin;
import util.JwtUtil;

/**
 * 服务层
 *
 * @author Administrator
 */
@Service
public class AdminService {

    @Autowired
    private AdminDao adminDao;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 查询全部列表
     *
     * @return
     */
    public List<Admin> findAll() {
        return adminDao.findAll();
    }

    /**
     * 条件查询+分页
     *
     * @param whereMap
     * @param page
     * @param size
     * @return
     */
    public Page<Admin> findSearch(Map whereMap, int page, int size) {
        Specification<Admin> specification = createSpecification(whereMap);
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return adminDao.findAll(specification, pageRequest);
    }

    /**
     * 条件查询
     *
     * @param whereMap
     * @return
     */
    public List<Admin> findSearch(Map whereMap) {
        Specification<Admin> specification = createSpecification(whereMap);
        return adminDao.findAll(specification);
    }

    /**
     * 根据ID查询实体
     *
     * @param id
     * @return
     */
    public Admin findById(String id) {
        return adminDao.findById(id).get();
    }

    /**
     * 增加
     *
     * @param admin
     */
    public void add(Admin admin) {
        admin.setId(idWorker.nextId() + "");
        //对密码进行加密处理
        admin.setPassword(encoder.encode(admin.getPassword()));
        adminDao.save(admin);
    }

    /**
     * 修改
     *
     * @param admin
     */
    public void update(Admin admin) {
        adminDao.save(admin);
    }

    /**
     * 删除
     *
     * @param id
     */
    public void deleteById(String id) {
        /*String authorization = request.getHeader("Authorization");
        if (StringUtils.isEmpty(authorization)) {
            throw new RuntimeException("权限不足!");
        }
        if (authorization.startsWith("Bearer ")) {
            throw new RuntimeException("权限不足!");
        }
        String token = authorization.substring(7);
        try {
            Claims claims = jwtUtil.parseJWT(token);
            String roles = claims.get("roles") + "";
            if (roles == null || !roles.equals("admin")) {
                throw new RuntimeException("权限不足!");
            }
        } catch (Exception e) {
            throw new RuntimeException("权限不足!");
        }*/
        String token = request.getAttribute("claims_admin") + "";
        if (StringUtils.isEmpty(token)) {
            throw new RuntimeException("权限不足");
        }
        adminDao.deleteById(id);
    }

    /**
     * 动态条件构建
     *
     * @param searchMap
     * @return
     */
    private Specification<Admin> createSpecification(Map searchMap) {

        return new Specification<Admin>() {

            @Override
            public Predicate toPredicate(Root<Admin> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
                // ID
                if (searchMap.get("id") != null && !"".equals(searchMap.get("id"))) {
                    predicateList.add(cb.like(root.get("id").as(String.class), "%" + (String) searchMap.get("id") + "%"));
                }
                // 登陆名称
                if (searchMap.get("loginname") != null && !"".equals(searchMap.get("loginname"))) {
                    predicateList.add(cb.like(root.get("loginname").as(String.class), "%" + (String) searchMap.get("loginname") + "%"));
                }
                // 密码
                if (searchMap.get("password") != null && !"".equals(searchMap.get("password"))) {
                    predicateList.add(cb.like(root.get("password").as(String.class), "%" + (String) searchMap.get("password") + "%"));
                }
                // 状态
                if (searchMap.get("state") != null && !"".equals(searchMap.get("state"))) {
                    predicateList.add(cb.like(root.get("state").as(String.class), "%" + (String) searchMap.get("state") + "%"));
                }

                return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));

            }
        };

    }

    public Admin login(Admin admin) {
        Admin adminLogin = adminDao.findByLoginname(admin.getLoginname());
        if (adminLogin != null && encoder.matches(admin.getPassword(), adminLogin.getPassword())) {
            return adminLogin; //登录成功
        }
        //登录失败
        return null;
    }
}
