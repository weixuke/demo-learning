package com.demo.lambda;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Test_lambda {
    public static void main(String args[]){
        Test_lambda tl = new Test_lambda();
        List<Demo> list = new ArrayList<Demo>();
        Demo d1 = tl.new Demo();
        d1.setAge(10);
        d1.setName("zhangsan");
        list.add(d1);

        d1 = tl.new Demo();
        d1.setAge(11);
        d1.setName("li");
        list.add(d1);

        d1 = tl.new Demo();
        d1.setAge(9);
        d1.setName("wang");
        list.add(d1);

        list = list.stream().filter(ma->ma.getAge()>=9).collect(Collectors.toList());

        list.forEach(ma->System.out.println(ma.getName()));
    }
    class Demo{
        private String name;
        private int age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }
}

