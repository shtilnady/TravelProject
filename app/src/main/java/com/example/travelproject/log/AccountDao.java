package com.example.travelproject.log;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface AccountDao {
    @Insert
    void insertAccount(Account account);
    @Query("DELETE FROM accounts")
    void deleteAll();
    @Query("SELECT * FROM accounts WHERE login = :login")
    Account getAccount(String login);
    @Query("SELECT * FROM accounts")
    List<Account> getAll();
    @Query("SELECT COUNT(*) FROM accounts")
    int getSize();
    @Query("SELECT password FROM accounts WHERE login = :login")
    String getPasswordWithId(String login);
    @Query("SELECT EXISTS(SELECT login FROM accounts WHERE login = :login)")
    boolean contains(String login);
}
