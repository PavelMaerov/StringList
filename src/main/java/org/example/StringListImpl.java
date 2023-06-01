package org.example;

import java.util.Arrays;

public class StringListImpl implements StringList{
    private int size;   //размер массива, заполненный данными, без свободного места в конце
    private int limitSize; //размер массива array, включая свободное место в конце
    private String[] array;  //массив для хранения строк

    public StringListImpl(int limitSize) {  //конструктор
        if (limitSize<=0) limitSize=1;
        size=0;  //необязательно
        this.limitSize = limitSize;
        array=new String[limitSize];
    }
    //Служебная процедура. Вызывается при добавлении элемента.
    //Проверяет наличие свободного места и при необходимости увеличивает массив
    private void checkSize() {
        if (size == limitSize) {
            //если не хватает места в массиве - удваиваем его
            //для простоты, чтобы не придумывать коэффициентов расширения
            limitSize = 2*limitSize;
            array = Arrays.copyOf(array, limitSize);  //копирование старого массива в новый больший
        }
    }
    //Служебная процедура. Вызывается для проверки переданного индекса.
    //Проверяет, что индекс находится в пределах допустимого диапазона
    //Если нет, то выбрасывает исключение
    private void checkIndex(int index) {
        if (index>=size || index<0) throw new ArrayIndexOutOfBoundsException();
    }
    @Override
    public String add(String item) {
        if (item==null) throw new NullPointerException();
        checkSize();
        array[size++] = item; //добавление в конец по индексу size
        return item;
    }

    @Override
    public String add(int index, String item) {
        if (item==null) throw new NullPointerException();
        checkIndex(index);
        checkSize();
        //сдвиг участка после вставляемого элемента вправо
        System.arraycopy(array, index, array, index+1, size-index);
        array[index] = item;  //замена элемента по индексу size
        size++;
        return item;
    }

    @Override
    public String set(int index, String item) {
        if (item==null) throw new NullPointerException();
        checkIndex(index);
        String oldValue = array[index]; //запоминаем старое значение. В задании это не задано
        array[index] = item;
        return oldValue;
    }

    @Override
    public String remove(String item) {
        if (item==null) throw new NullPointerException();
        int index = indexOf(item); //определяем индекс удаляемого элемента
        if (index==-1) throw new ItemNotFound(); //самодельное RunTime исключение
        return remove(index);  //удалить по найденному индексу
    }

    @Override
    public String remove(int index) {
        checkIndex(index);
        String removedItem = array[index];  //запомним удаляемый элемент
        //сдвиг участка после удаляемого элемента влево
        System.arraycopy(array, index+1, array, index, size-index-1);
        array[size-1] = null; //очистка последнего элемента
        size--;
        return removedItem;
    }

    @Override
    public boolean contains(String item) {
        return indexOf(item) != -1; //определяем индекс искомого элемента. Если не -1, значит найден
    }

    @Override
    public int indexOf(String item) {
        //--------------- вариант заимствования метода у List--------
        //в Arrays нет indexOf, но есть в List
        //Поведение метода List точно соответствует требуемому от StringList
        //int index = Arrays.asList(array).indexOf(item);
        //if (index>=size) index=-1; //если параметр null, то можем найти null на свободном месте массива
        //return index;
        //---------------------самодельный метод---------------------
        for (int i=0; i<size; i++) {
           if (array[i].equals(item)) return i;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(String item) {
        int index = Arrays.asList(array).lastIndexOf(item);
        if (index>=size) index=-1; //если параметр null, то можем найти null на свободном месте массива
        return index;
    }

    @Override
    public String get(int index) {
        checkIndex(index);
        return array[index];
    }

    @Override
    //этот метод не переопределяет Object.equals, т.к. имеет другую сигнатуру
    public boolean equals(StringList otherList) {
        //array.equals() сравнивает ссылки, пользоваться им нельзя
        //Если передан параметр = null, то ничего делать не надо. otherList.toArray() вызовет NullPointerException
        //на сравнение подаем не полный array, а только его значимую часть
        return Arrays.equals(toArray(), otherList.toArray());
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size==0;
    }

    @Override
    public void clear() {
        size=0;
        //делать shrink не задали, поэтому массив останется большим. Да и исходный limitSize мы уже потеряли
        array=new String[limitSize];
    }

    @Override
    public String[] toArray() {
        //return array.clone(); возвратит с nullами на свободном конце
        return Arrays.copyOf(array, size);
    }
    //toString заданием не определено. Вывожу полный массив со свободным концом
    public String toString() {
        return Arrays.toString(array);
    }
}
