package com.sts.sqlitedemo.bean;

/**
 * @author SuTs
 * @create 2019/10/25
 * @Describe
 */
public class Person {
    private int _id;
    private String name;
    private int age;


    @Override
    public String toString() {
        return "Person{" +
                "_id=" + _id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public Person(int _id, String name, int age) {
        this._id = _id;
        this.name = name;
        this.age = age;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

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
