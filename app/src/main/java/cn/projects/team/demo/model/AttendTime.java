package cn.projects.team.demo.model;

import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 *考勤时间
 * </p>
 *
 * @author demo
 * @since 2021-03-11
 */
public class AttendTime implements Serializable {

    private static final long serialVersionUID = 1L;


    private Long time;

    /**
     * 公司上班时间
     */
    private String startTime;

    /**
     * 公司下班时间
     */
    private String endTime;

    private String address;

    private String mark;

    private List<Attendance> list ;

    public List<Attendance> getList() {
        return list;
    }

    public void setList(List<Attendance> list) {
        this.list = list;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }
}
