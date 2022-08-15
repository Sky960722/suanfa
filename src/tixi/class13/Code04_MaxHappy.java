package tixi.class13;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

//派对的最大快乐值
//员工信息的定义如下：
//class Employee{
//public int happy;//这名员工可以带来的快乐值
//List<Employee> subordinates;//这名员工有哪些直接下级
//}
//公司的每个员工都符合 Employee 类的描述，整个公司的人员结构可以看做是一棵标准的、没有环的多叉树。树的根节点是公司唯一的老板。除老板之外的每个员工都有唯一的直接上级。叶节点是没有任何下属的基层员工（subordinates
// 列表为空），除基层员工外，每个员工都有一个或多个直接下级。
//
//        这个公司现在要办 party，你可以决定哪些员工来，哪些员工不来，规则：
//        如果某个员工来了，那么这个员工的所有直接下级都不能来；
//        排队的整体快乐值是所有到场员工快乐值的累加；
//        你的目标是让排队的整体快乐值尽量大。
//        给定一棵多叉树的根节点 boss，请返回排队的最大快乐值。
public class Code04_MaxHappy {
    public static class Employee {
        public int happy;
        public List<Employee> nexts;

        public Employee(int h) {
            happy = h;
            nexts = new ArrayList<>();
        }

    }

    //当前根节点的最大快乐值来源两个方面
    //1.当前根节点不参加
    //1.1则底下员工全部参加的最大值maxHappy1
    //2.当前根节点参加
    //2.1则底下员工不参加的最大值maxHappy2
    public static int maxHappy2(Employee boss) {
        if (boss == null) {
            return 0;
        }
        Info allInfo = process2(boss);
        return Math.max(allInfo.yes, allInfo.no);
    }

    //返回的信息，参加，最大值
    //不参加，最大值
    public static class Info {
        public int yes;
        public int no;

        public Info(int yes, int no) {
            this.yes = yes;
            this.no = no;
        }
    }

    public static Info process2(Employee e) {
        if (e == null) {
            return new Info(0, 0);
        }
        int yes = e.happy;
        int no = 0;
        for (int i = 0; i < e.nexts.size(); i++) {
            Info eInfo = process2(e.nexts.get(i));
            yes += eInfo.no;
            no += Math.max(eInfo.yes, eInfo.no);
        }
        return new Info(yes, no);
    }

    //当前根节点不来，则底下员工来的maxHappy
    //当前根节点来，则底下员工不来的maxHappy


    public static int maxHappy1(Employee boss) {
        if (boss == null) {
            return 0;
        }
        return process1(boss, false);
    }

    public static int process1(Employee e, boolean up) {
        if (e == null) {
            return 0;
        }
        //如果up是false，则说明e的领导不参加,e参加或者不参加
        //maxHappy是e.Happy+process2(E.nexts,true)
        //如果up是true，则说明e的领导参加，e不参加
        //maxHappy是process2(e.nexts,false)

        if (up) {
            int ans = 0;
            for (int i = 0; i < e.nexts.size(); i++) {
                ans += process1(e.nexts.get(i), false);
            }
            return ans;
        } else {
            //p1来
            int p1 = e.happy;
            int p2 = 0;
            for (Employee next : e.nexts) {
                p1 += process1(next, true);
                p2 += process1(next, false);
            }
            return Math.max(p1, p2);
        }
    }





    public static Employee generateEmployeeTree(int maxLevel, int maxEmployees, int maxHappy) {
        return generate(1, maxLevel, maxEmployees, maxHappy);
    }

    public static Employee generate(int level, int maxLevel, int maxEmployees, int maxHappy) {
        if (level > maxEmployees || Math.random() < 0.5) {
            return null;
        }
        int happy = ((int) (Math.random() * maxHappy)) + 1;
        Employee root = new Employee(happy);
        int allEmployees = ((int) (Math.random() * maxEmployees)) + 1;
        for (int i = 0; i < allEmployees; i++) {
            Employee e = generate(level + 1, maxLevel, maxEmployees, maxHappy);
            if (e != null) {
                root.nexts.add(e);
            }
        }
        return root;
    }


    public static void main(String[] args) {
        int testTimes = 1000;
        int maxHappy = 100;
        int maxLevel = 5;
        int maxEmployees = 5;
        for (int i = 0; i < testTimes; i++) {
            Employee boss = generateEmployeeTree(maxLevel, maxEmployees, maxHappy);
            if (maxHappy1(boss) != maxHappy2(boss)) {
                System.out.println("失败");
            }
        }
        System.out.println("finish");
    }
}
