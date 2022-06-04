package cn.projects.team.demo.model;

import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 部门表
 * </p>
 *
 * @author demo
 * @since 2021-03-11
 */
public class Dept implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 部门id
     */
    private Long deptId;

    /**
     * 部门名称
     */
    private String name;

    /**
     * 部门备注
     */
    private String mark;

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }
}
