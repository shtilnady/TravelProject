package com.example.travelproject;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface AccountDao {
    @Insert
    void insertAccount(Account account);
    @Delete
    void deleteAccount(Account account);
    @Query("SELECT * FROM accounts WHERE id = :id_")
    Account getAccount(int id_);
    @Query("SELECT * FROM accounts")
    List<Account> getAll();
    @Query("SELECT COUNT(*) FROM accounts")
    int getSize();
    @Query("SELECT password FROM accounts WHERE id = :id_")
    String getPasswordWithId(int id_);
}
