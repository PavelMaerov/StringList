package org.example;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        StringList stringList = new StringListImpl(2);
        stringList.add("aaa");
        stringList.add("bbb");
        stringList.add("ccc");
        stringList.add("!");
        stringList.add("");
        stringList.add("123");
        stringList.add(0,"Индекс 0");
        System.out.println(stringList);
        stringList.add(6,"Индекс 6");
        System.out.println(stringList);
        stringList.add(7,"Индекс 7");
        System.out.println(stringList);
        stringList.remove("aaa");
        System.out.println(stringList);
        stringList.remove("Индекс 7");
        System.out.println(stringList);
        System.out.println(stringList.size());
        System.out.println(stringList.indexOf("bbb"));
        System.out.println(stringList.equals(stringList));  //так вызывается мой метод
        System.out.println(stringList.equals((Object)stringList));  //так вызывается метод для Object
        StringList other = new StringListImpl(15);
        Arrays.stream(stringList.toArray()).forEach(e->other.add(e));
        System.out.println(stringList.equals(other));
        other.clear();
        System.out.println(other);
        System.out.println(other.isEmpty());
        System.out.println(stringList.equals(other));
        System.out.println(stringList.contains("xxx"));

    }
}