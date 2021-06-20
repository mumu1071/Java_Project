package course.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yangjie
 * @date 2020-11-29 14:36
 * Description: 课程
 */
public class Record {

    public String code;
    public String name;
    public List<String> teacherList = new ArrayList<>();

    public List<Integer> classroomList = new ArrayList<>();
    /**
     * 可用周
     */
    public List<Integer> weekList = new ArrayList<>();

    public List<Integer> dayList = new ArrayList<>();

    public List<Integer> timeList = new ArrayList<>();


    /**
     * 总课时
     */
    public int allHours;

    /**
     * 单次课时
     */
    public int singleHours;

    /**
     * 共排课次数
     */
    public int countCourse;

    /**
     * 每周几次,默认
     */
    public int countCourseWeek;


    /**
     * 是否排入
     */
    public boolean isUse;

    /**
     * 是否前4周
     */
    public boolean isFourWeek;

    /**
     * 是否可排晚上
     */
    public boolean isCanNight;

    /**
     * 是否必须晚上
     */
    public boolean isMustNight;

    /**
     * 是否立体剪裁课-教室 8、9、10
     */
    public boolean isClipping;

    /**
     * 是否合班-教室 7
     */
    public boolean isGroup;

    /**
     * 是否上机课
     * Todo 暂没用
     */
    public boolean isMachine;

}
