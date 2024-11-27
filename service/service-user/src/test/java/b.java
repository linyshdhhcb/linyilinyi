import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author linyi
 * @Date 2024/11/21
 * @ClassName: b
 */
public class b {
    public static void main(String[] args) {
        // 创建两个列表
        List<Integer> list1 = new ArrayList<>();
        list1.add(1);
        list1.add(2);
        list1.add(3);
        list1.add(4);

        List<Integer> list2 = new ArrayList<>();
        list2.add(3);
        list2.add(4);
        list2.add(5);
        list2.add(6);

        // 求两个列表的非交集部分
        List<Integer> nonIntersection = getNonIntersection(list1, list2);

        // 输出结果
        System.out.println("非交集部分: " + nonIntersection);
    }

    public static List<Integer> getNonIntersection(List<Integer> list1, List<Integer> list2) {
        // 创建两个临时列表，避免修改原始列表
        List<Integer> temp1 = new ArrayList<>(list1);
        List<Integer> temp2 = new ArrayList<>(list2);

        // 从 temp1 中移除与 temp2 交集的部分
        temp1.removeAll(temp2);

        // 从 temp2 中移除与 temp1 交集的部分
        temp2.removeAll(list1);

        // 合并两个临时列表
        temp1.addAll(temp2);

        return temp1;
    }


}
