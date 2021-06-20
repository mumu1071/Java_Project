package course;

import org.zt.tools.TextUtil;
import course.model.Course;
import course.model.Group;
import course.model.Record;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author yangjie-mac
 */
public class Main {

    private final static String corse18 = "/Users/yangjie-mac/Desktop/数据分析/静姐/2018级课程.xls";
    private final static String corse19 = "/Users/yangjie-mac/Desktop/数据分析/静姐/2019级课程.xls";
    private final static String corse20 = "/Users/yangjie-mac/Desktop/数据分析/静姐/2020级课程.xls";

    private final static String resultPath = "/Users/yangjie-mac/Desktop/数据分析/静姐/result.xls";

    private static List<Group> groupList;


    public static void main(String[] args) {
        groupList = dealData();
        if (checkRecordList()) {
            sortCourse();
//            checkResult();
            printResult();
            ExcelUtils.writeExcel(resultPath, dealResultList());
        }
    }

    public static void printResult() {
        for (Group group : groupList) {
            for (List<Course> courseList : group.courseMap.values()) {
                for (Course course : courseList) {
                    System.out.println(group.level + " " + group.name + " " + course.toString());
                }
            }
        }
    }

    public static List<List<String>> dealResultList() {
        List<List<String>> resultList = new ArrayList<>();
        for (Group group : groupList) {
            for (List<Course> courseList : group.courseMap.values()) {
                for (Course course : courseList) {
                    List<String> rowList = new ArrayList<>();
                    rowList.add(String.valueOf(group.level));
                    rowList.add(group.name);
                    rowList.add(course.toString());
                    resultList.add(rowList);
                }
            }
        }
        return resultList;
    }

    public static void checkResult() {
        for (Group group : groupList) {
            for (List<Course> courseList : group.courseMap.values()) {
                for (Course course : courseList) {
                    if (course.classroom == 5 && course.locWeek == 2 && course.weekDay == 1) {
                        System.out.println(group.level + " " + group.name + " " + course.toString());
                    }
                }
            }
        }
    }

    public static boolean checkRecordList() {
        for (Group group : groupList) {
            for (Record record : group.recordList) {
                if (!record.isUse) {
                    continue;
                }
                if ((record.teacherList.size() == 0 || record.teacherList.contains(""))) {
                    System.out.println("教师错误 " + "年级 " + group.level + " 课程 " + record.name);
                }
                if (record.allHours == 0) {
                    System.out.println("总课时错误 " + "年级 " + group.level + " 课程 " + record.name);
                }
            }
        }
        return true;
    }


    public static void sortCourse() {
        for (Group group : groupList) {
            sortRecord(group, group.headerRecordList);
            sortRecord(group, group.normalRecordList);
        }
    }

    public static void setLocWeekTime(Group group, Course course, Record record) {
        //确定排课时间
        for (int locWeek : record.weekList) {
            for (int weekDay : record.dayList) {
                for (int time : record.timeList) {
                    if (checkLocWeekTime(group, record, locWeek, weekDay, time)) {
                        course.locWeek = locWeek;
                        course.weekDay = weekDay;
                        course.time = time;
                        return;
                    }
                }
            }
        }
    }

    public static void setTeacher(Record record, Course course) {
        for (String teacherName : record.teacherList) {
            if (TextUtil.notEmpty(teacherName)) {
                if (checkTeacher(teacherName, course)) {
                    course.teacher = teacherName;
                    return;
                }
            }
        }
    }

    public static void setClassroom(Record record, Course course) {
        //教室
        for (Integer classroom : record.classroomList) {
            if (checkClassroom(classroom, course)) {
                course.classroom = classroom;
                return;
            }
        }
    }

    public static void sortRecord(Group group, List<Record> recordList) {
        for (Record record : recordList) {
            List<Course> courseList = group.courseMap.get(record.name);
            for (Course course : courseList) {
                //设置时间
                setLocWeekTime(group, course, record);
                //设置老师
                setTeacher(record, course);
                //设置教室
                setClassroom(record, course);
                //校验是否排完
                course.isDone = checkIsDone(course);
            }
        }
    }

    public static boolean checkIsDone(Course course) {
        if (course.locWeek == 0 || course.weekDay == 0 || course.time == 0) {
            return false;
        }
        if (TextUtil.isEmpty(course.teacher)) {
            return false;
        }
        if (course.classroom == 0) {
            return false;
        }
        return true;
    }


    /**
     * 校验时间是否可用
     *
     * @param group   班级
     * @param record  记录
     * @param locWeek 第几周
     * @param weekDay 周几
     * @param time    时间
     * @return 是否可排
     */
    public static boolean checkLocWeekTime(Group group, Record record, int locWeek, int weekDay, int time) {
        //本班-本课-本周 已排满
        int countCourseWeek = 0;
        for (Course course : group.courseMap.get(record.name)) {
            if (course.locWeek == locWeek) {
                countCourseWeek++;
            }
        }
        if (countCourseWeek >= record.countCourseWeek) {
            return false;
        }
        //已排课的时间，不能用
        for (List<Course> courseList : group.courseMap.values()) {
            for (Course course : courseList) {
                if (course.locWeek == locWeek) {
                    if (course.weekDay == weekDay) {
                        //当天不能有相同课程
                        if (course.name.equals(record.name)) {
                            return false;
                        }
                        if (course.time == time) {
                            return false;
                        }
                    }
                }
            }
        }

        //此时间段有教室可用



        //此时间没有可用老师







        return true;
    }

    /**
     * 校验教室是否可用
     *
     * @param classroom 教室
     * @param course    任务
     * @return 是否可用
     */
    public static boolean checkClassroom(int classroom, Course course) {
        for (Group group : groupList) {
            for (List<Course> courseList : group.courseMap.values()) {
                for (Course tempCourse : courseList) {
                    if (tempCourse.classroom == classroom) {
                        if (tempCourse.locWeek == course.locWeek) {
                            if (tempCourse.weekDay == course.weekDay) {
                                if (tempCourse.time == course.time) {
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * 校验老师是否可用
     *
     * @param teacherName 老师姓名
     * @param course      任务
     * @return true 可用，false 不可用
     */
    public static boolean checkTeacher(String teacherName, Course course) {
        for (Group group : groupList) {
            for (List<Course> courseList : group.courseMap.values()) {
                for (Course tempCourse : courseList) {
                    if (teacherName.equals(tempCourse.teacher)) {
                        if (tempCourse.locWeek == course.locWeek) {
                            if (tempCourse.weekDay == course.weekDay) {
                                if (tempCourse.time == course.time) {
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }


    public static List<Group> dealData() {
        List<List<String>> excelList18 = ExcelUtils.readFromXLSX(corse18);
        List<Record> recordList18 = getCourseList(excelList18);

        List<List<String>> excelList19 = ExcelUtils.readFromXLSX(corse19);
        List<Record> recordList19 = getCourseList(excelList19);

        List<List<String>> excelList20 = ExcelUtils.readFromXLSX(corse20);
        List<Record> recordList20 = getCourseList(excelList20);

        List<Group> groupList = new ArrayList<>();
        //大三 3个班
        groupList.add(new Group(18, "一班", recordList18));
        groupList.add(new Group(18, "二班", recordList18));
        groupList.add(new Group(18, "三班", recordList18));
        //大二 3个班
        groupList.add(new Group(19, "一班", recordList19));
        groupList.add(new Group(19, "二班", recordList19));
        groupList.add(new Group(19, "三班", recordList19));
        //大一 2个班
        groupList.add(new Group(20, "一班", recordList20));
        groupList.add(new Group(20, "二班", recordList20));

        return groupList;
    }


    public static List<Record> getCourseList(List<List<String>> excelList) {
        List<Record> recordList = parseRecordList(excelList);
        for (Record record : recordList) {
            //周列表
            for (int i = 1; i <= 19; i++) {
                record.weekList.add(i);
            }
            //日列表
            for (int i = 1; i <= 5; i++) {
                record.dayList.add(i);
            }
            //时间列表
            if (record.isMustNight) {
                //只能晚上排
                record.timeList.add(3);
            } else if (record.isCanNight) {
                for (int i = 1; i <= 3; i++) {
                    record.timeList.add(i);
                }
            } else {
                for (int i = 1; i <= 2; i++) {
                    record.timeList.add(i);
                }
            }
            //教室
            if (record.isClipping) {
                record.classroomList.add(8);
                record.classroomList.add(9);
                record.classroomList.add(10);
            } else if (record.isGroup) {
                record.classroomList.add(7);
            } else {
                for (int i = 1; i <= 6; i++) {
                    record.classroomList.add(i);
                }
            }

            record.countCourse = record.allHours / record.singleHours;

        }
        return recordList;
    }

    public static List<Record> parseRecordList(List<List<String>> excelList) {
        List<Record> recordList = new ArrayList<>();
        for (List<String> rowList : excelList) {
            if (rowList.size() < 26) {
                break;
            }
            Record record = new Record();
            record.code = rowList.get(1);
            record.name = rowList.get(2);
            if (TextUtil.notEmpty(rowList.get(9))) {
                record.allHours = Integer.parseInt(rowList.get(9));
            }
            String strTeachers = rowList.get(16);
            if (TextUtil.notEmpty(strTeachers)) {
                String[] arrTeachers = strTeachers.split("/");
                record.teacherList.addAll(Arrays.asList(arrTeachers));
            }
            record.isGroup = "是".equals(rowList.get(17));
            record.isUse = !"否".equals(rowList.get(18));
            record.isCanNight = "是".equals(rowList.get(19));
            record.isMustNight = "是".equals(rowList.get(21));
            record.isClipping = "是".equals(rowList.get(20));
            record.isMachine = "是".equals(rowList.get(22));

            if (TextUtil.isEmpty(rowList.get(23))) {
                record.singleHours = 4;
            } else {
                record.singleHours = Integer.parseInt(rowList.get(23));
            }
            if (TextUtil.isEmpty(rowList.get(24))) {
                //最多10次
                record.countCourseWeek = 3;
            } else {
                record.countCourseWeek = Integer.parseInt(rowList.get(24));
            }
            record.isFourWeek = "是".equals(rowList.get(25));
            recordList.add(record);
        }
        return recordList;
    }


}
