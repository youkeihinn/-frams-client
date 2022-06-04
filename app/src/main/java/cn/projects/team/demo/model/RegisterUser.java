package cn.projects.team.demo.model;

import java.util.Date;
import java.io.Serializable;

/**
 * <p>
 * 用户
 * </p>
 *
 * @author demo
 * @since 2019-10-08
 */

public class RegisterUser implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 主键
     */
    public Long userId;

    /**
     * 头像
     */
    public String userName;

    /**
     * 昵称
     */
    public String name;

    public String juserPass;
    /**
     * 密码
     */
    public String userPass;
    public String headImage ;
    public String sex;
    public String adrress;
    public String tel;
    public String no;
    public String deptName;
}
