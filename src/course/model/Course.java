package course.model;

import org.zt.tools.TextUtil;

/**
 * @author yangjie
 * @date 2020-11-29 15:16
 * Description: 课程
 */
public class Course {

    public String code;
    public String name;

    public Course(String code, String name) {
        this.code = code;
        this.name = name;
    }

    /**
     * 是否已排课
     */
    public boolean isDone;

    /**
     * 开始周
     */
    public int startWeek;

    /**
     * 结束周
     */
    public int endWeek;

    /**
     * 第几周
     */
    public int locWeek;

    /**
     * 周几
     */
    public int weekDay;

    /**
     * 1 是上午，2下午，3是晚上
     */
    public int time;

    /**
     * 老师
     */
    public String teacher;

    /**
     * 教室
     * 1-6 普通教室，7 合班教室、8 9 10 是立裁教室
     */
    public int classroom;


    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(name);
        stringBuilder.append(" 第" + locWeek + " 周");
        stringBuilder.append(" 星期" + weekDay);
        if (time == 1) {
            stringBuilder.append(" 上午 ");
        } else if (time == 2) {
            stringBuilder.append(" 下午 ");
        } else if (time == 3) {
            stringBuilder.append(" 晚上 ");
        }
        if (TextUtil.notEmpty(teacher)) {
            stringBuilder.append(teacher + " ");
        } else {
            stringBuilder.append(" 无老师");
        }
        if (classroom == 0) {
            stringBuilder.append(" 无教室");
        } else {
            stringBuilder.append("第" + classroom + "教室");
        }
        return stringBuilder.toString();
    }
}














