package com.tensquare.base.service;

import com.tensquare.base.dao.LabelDao;
import com.tensquare.base.pojo.Label;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import util.IdWorker;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * @author LiangDong.
 */

@Service
@Transactional
public class LabelService {

    @Autowired
    private LabelDao labelDao;

    @Autowired
    private IdWorker idWorker;

    public List<Label> findAll() {
        return labelDao.findAll();
    }

    public Label findById(String id) {
        return labelDao.findById(id).get();
    }

    public void save(Label label) {
        label.setId(idWorker.nextId() + "");
        labelDao.save(label);
    }

    public void update(Label label) {
        labelDao.save(label); //save = insertOrUpdate
    }

    public void deleteById(String id) {
        labelDao.deleteById(id);
    }

    public List<Label> findSearch(Label label) {

        /**
         * 参数说明：
         *  root 根对象，也就是要把条件封装到哪个对象中。where 类名 = label.getid
         *  query 封装的都是查询关键字，比如group by order by 等
         *  cb 用来封装条件对象
         */
        return labelDao.findAll((root, query, cb) -> {
            List<Predicate> list = new ArrayList<>();
            if (StringUtils.isNotEmpty(label.getLabelname())) {
                Predicate labelname = cb.like(root.get("labelname").as(String.class), "%" + label.getLabelname() + "%");
                list.add(labelname);
            }
            if (StringUtils.isNotEmpty(label.getState())) {
                Predicate state = cb.equal(root.get("state").as(String.class), label.getState());
                list.add(state);
            }
            return cb.and(list.toArray(new Predicate[list.size()]));
        });
    }

    public Page<Label> pageQuery(Label label, Integer page, Integer size) {
        //封装分页对象
        Pageable pageable = PageRequest.of(page - 1, size);
        return labelDao.findAll((root, query, cb) -> {
            List<Predicate> list = new ArrayList<>();
            if (StringUtils.isNotEmpty(label.getLabelname())) {
                Predicate labelname = cb.like(root.get("labelname").as(String.class), "%" + label.getLabelname() + "%");
                list.add(labelname);
            }
            if (StringUtils.isNotEmpty(label.getState())) {
                Predicate state = cb.equal(root.get("state").as(String.class), label.getState());
                list.add(state);
            }
            return cb.and(list.toArray(new Predicate[list.size()]));
        }, pageable);

    }
}
