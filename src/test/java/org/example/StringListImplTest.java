package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class StringListImplTest {
    StringList out;
    @BeforeEach
    void createStringList() {
        out = new StringListImpl(1);
        out.add("aaa");
        out.add("bbb");
        out.add("ccc");
        out.add("!");
        out.add("");
        out.add("123");
    }
    @Test
    void addTest() {
        String returnValue=out.add("ddd");
        assertEquals("ddd",returnValue);
        String[] expected = {"aaa","bbb","ccc","!","","123","ddd"};
        assertArrayEquals(expected,out.toArray());
        assertEquals(7,out.size());
        assertThrows(NullPointerException.class, ()->out.add(null));
    }

    @Test
    void addByIndexTest() {
        String resultValue = out.add(0,"Индекс 0");
        assertEquals("Индекс 0",resultValue);
        out.add(6,"Индекс 6");
        out.add(7,"Индекс 7"); //приводит к расширению массива с 8 до 16 элементов
        String[] expected = {"Индекс 0","aaa","bbb","ccc","!","","Индекс 6","Индекс 7","123"};
        assertArrayEquals(expected,out.toArray());
        assertEquals(9,out.size());
        assertThrows(NullPointerException.class, ()->out.add(0,null));
        assertThrows(ArrayIndexOutOfBoundsException.class, ()->out.add(-1,"111"));
        assertThrows(ArrayIndexOutOfBoundsException.class, ()->out.add(9,"111"));
    }

    @Test
    void setTest() {
        String resultValue = out.set(1,"Изменено");
        assertEquals("bbb",resultValue);
        String[] expected = {"aaa","Изменено","ccc","!","","123"};
        assertArrayEquals(expected,out.toArray());
        assertThrows(NullPointerException.class, ()->out.set(0,null));
        assertThrows(ArrayIndexOutOfBoundsException.class, ()->out.set(-1,"111"));
        assertThrows(ArrayIndexOutOfBoundsException.class, ()->out.set(9,"111"));

        out.clear();
        assertThrows(ArrayIndexOutOfBoundsException.class, ()->out.set(0,"123"));  // в пустом списке set не работает
    }

    @Test
    void removeTest() {
        String resultValue = out.remove("bbb");
        assertEquals("bbb",resultValue);
        String[] expected = {"aaa","ccc","!","","123"};
        assertArrayEquals(expected,out.toArray());
        assertEquals(5,out.size());
        assertThrows(NullPointerException.class, ()->out.remove(null));
        assertThrows(ItemNotFound.class, ()->out.remove("321"));
    }

    @org.junit.jupiter.api.Test
    void removeByIndexTest() {
        String resultValue = out.remove(0);
        assertEquals("aaa",resultValue);
        out.remove(4);
        String[] expected = {"bbb","ccc","!",""};
        assertArrayEquals(expected,out.toArray());
        assertEquals(4,out.size());
        assertThrows(ArrayIndexOutOfBoundsException.class, ()->out.remove(-1));
        assertThrows(ArrayIndexOutOfBoundsException.class, ()->out.remove(4));
    }

    @Test
    void containsTest() {
        assertTrue(out.contains("bbb"));
        assertFalse(out.contains("321"));
        assertFalse(out.contains(null));
    }

    @Test
    void indexOfTest() {
        out.add("");
        assertEquals(1,out.indexOf("bbb"));
        assertEquals(4,out.indexOf(""));
        assertEquals(-1,out.indexOf("321"));
        assertEquals(-1, out.indexOf(null));
    }

    @Test
    void lastIndexOfTest() {
        out.add(""); //в седьмую позицию, индекс 6
        //System.out.println(out);
        assertEquals(1,out.lastIndexOf("bbb"));
        assertEquals(6,out.lastIndexOf(""));
        assertEquals(-1,out.lastIndexOf("321"));
        assertEquals(-1, out.lastIndexOf(null));
    }

    @Test
    void get() {
        assertEquals("bbb",out.get(1));
        assertThrows(ArrayIndexOutOfBoundsException.class, ()->out.get(-1));
        assertThrows(ArrayIndexOutOfBoundsException.class, ()->out.get(6));
    }

    @Test
    void equalsTest() {
        assertTrue(out.equals(out));          //вызывается мой метод
        assertTrue(out.equals((Object)out));  //вызывается метод для Object

        StringList other = new StringListImpl(15);  //специально с другим размером
        Arrays.stream(out.toArray()).forEach(e->other.add(e)); //наполняем other из out
        assertTrue(out.equals(other));          //вызывается мой метод
        assertFalse(out.equals((Object)other)); //так вызывается метод для Object

        other.set(0,"xyz");
        assertFalse(out.equals(other));

        assertThrows(NullPointerException.class,()->out.equals(null));  //так вызывается мой метод
        assertFalse(out.equals((Object)null));  //так вызывается метод для Object
    }

    @Test
    void sizeTest() {
        assertEquals(6,out.size());
        out.add("");
        assertEquals(7,out.size());
        out.remove("");
        assertEquals(6,out.size());
        out.clear();
        assertEquals(0,out.size());
    }

    @org.junit.jupiter.api.Test
    void isEmpty() {
        assertFalse(out.isEmpty());
        IntStream.range(0, out.size()).forEach((e)->out.remove(0));
        assertTrue(out.isEmpty());

        out = new StringListImpl(100);
        assertTrue(out.isEmpty());
    }

    @Test
    void clearTest() {
        out.clear();
        assertTrue(out.isEmpty());
        assertEquals(0,out.size());
    }

    @Test
    void toArrayTest() {
        String[] expected = {"aaa","bbb","ccc","!","","123"};
        assertArrayEquals(expected,out.toArray());

        out.clear();  //сравним пустые массивы
        String[] expected2 = {};
        assertArrayEquals(expected2,out.toArray());
    }

    @Test
    void toStringTest() {
        assertEquals("[aaa, bbb, ccc, !, , 123, null, null]",out.toString());
    }
}