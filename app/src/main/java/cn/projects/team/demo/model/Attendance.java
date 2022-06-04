package cn.projects.team.demo.model;

import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 员工考勤
 * </p>
 *
 * @author demo
 * @since 2021-03-11
 */
public class Attendance implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 主键
     */
    private Long attendId;

    /**
     * 考勤员工
     */
    private Long userId;

    /**
     * 考勤时间
     */
    private String time;

    /**
     * 考勤类型
     */
    private String type;
    private String pic;
    private String  no;

  
    private String name;

    private String address;
    private Long timeId;

    public Long getTimeId() {
        return timeId;
    }

    public void setTimeId(Long timeId) {
        this.timeId = timeId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getAttendId() {
        return attendId;
    }

    public void setAttendId(Long attendId) {
        this.attendId = attendId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
