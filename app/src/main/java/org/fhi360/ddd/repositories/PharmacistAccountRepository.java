package org.fhi360.ddd.repositories;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import org.fhi360.ddd.domain.Account;

import java.util.List;

@Dao
public interface PharmacistAccountRepository {
    @Query("SELECT * FROM account")
    List<Account> findByAll();

    @Query("SELECT * FROM account where pin_code = :pincCode")
    Account findByCommunityPin(String pincCode);

    @Query("SELECT * FROM account where phone = :phone and email =:email")
    Account findByPhoneAndEmail(String  phone,String email);


    @Insert
    void save(Account account);
    @Update
    void update(Account account);


}
