package com.splitcorp.first.tamplateCallback;

public interface LineCallback<T> {
    T doSomethingWithLine(String line, T value);
}
