package course.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yangjie
 * @date 2020-11-29 15:13
 * Description: 班级
 */
public class Group {

    public Group(int level, String name, List<Record> recordList) {
        this.level = level;
        this.name = name;
        this.recordList = recordList;
        dealRecord(recordList);
    }

    public void dealRecord(List<Record> recordList) {
        for (Record record : recordList) {
            //不参加排课
            if (!record.isUse) {
                continue;
            }
            List<Course> courseList = new ArrayList<>();
            for (int i = 0; i < record.countCourse; i++) {
                courseList.add(new Course(record.code, record.name));
            }
            courseMap.put(record.name, courseList);

            if (record.isFourWeek) {
                headerRecordList.add(record);
            } else {
                normalRecordList.add(record);
            }
        }
    }

    public String name;

    /**
     * 年级
     */
    public int level;

    /**
     * 所有需要排的课表
     */
    public List<Record> recordList;

    /**
     * 优先排的课
     */
    public List<Record> headerRecordList = new ArrayList<>();
    /**
     * 其次排的课
     */
    public List<Record> normalRecordList = new ArrayList<>();

    /**
     * 课程 课程名称：排课任务
     */
    public Map<String, List<Course>> courseMap = new HashMap<>();


}
