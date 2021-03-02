package com.shahid.aietest.ui.interfaces;


// TaskDelegate to notify to implementing class for delete/update command
public interface TaskDelegate {
    void updateItem(int rowId);
    void deleteItem(int rowId);
}

