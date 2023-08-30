package src.lang;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MergeGroup {
    public static void main(String[] args) {
        Map<String, Map<String, SampleClass>> scoreMap = new HashMap<>();
        // Sample Setup
        List<SampleClass> list = new ArrayList<>();
        list.add(new SampleClass("3","1",43));
        list.add(new SampleClass("1","2",10));
        list.add(new SampleClass("1","3",43));
        list.add(new SampleClass("1","3",60));
        list.add(new SampleClass("1","2",8));
        list.add(new SampleClass("2","1",10));
        list.add(new SampleClass("1","3",90)); // expected
        list.add(new SampleClass("2","2",22)); // expected
        list.add(new SampleClass("1","2",61)); // expected
        list.add(new SampleClass("2","3",99)); // expected
        list.add(new SampleClass("2","1",55)); // expected
        list.add(new SampleClass("3","2",77)); // expected
        list.add(new SampleClass("1","2",51));
        list.add(new SampleClass("2","3",44));
        list.add(new SampleClass("1","3",12));
        list.add(new SampleClass("2","3",70));
        list.add(new SampleClass("1","2",9));
        list.add(new SampleClass("1","2",6));
        list.add(new SampleClass("1","2",1));
        list.add(new SampleClass("1","3",72));
        // 학년별 각 반의 최고 평균 반 Map 만들기
        list.forEach(sc -> {
            Map<String, SampleClass> map = Optional.ofNullable(scoreMap.get(sc.grade)).orElseGet(HashMap::new);
            map.merge(sc.classNo, sc, (c1, c2) -> c1.averageScore >= c2.averageScore ? c1 : c2);
            scoreMap.put(sc.grade, map);
        });
        // Expected Result Test
        scoreMap.forEach((grade,map) -> {
            map.forEach((classNo, cls) -> {
                System.out.println(String.format("%s 학년 %s 반 %d 점", cls.grade, cls.classNo, cls.averageScore));
            });
        });
    }

    static class SampleClass {
        String grade; // 학년
        String classNo; // 반
        Integer averageScore; // 반 평균;

        public SampleClass(String grade, String classNo, Integer averageScore) {
            this.grade = grade;
            this.classNo = classNo;
            this.averageScore = averageScore;
        }
    }
}
