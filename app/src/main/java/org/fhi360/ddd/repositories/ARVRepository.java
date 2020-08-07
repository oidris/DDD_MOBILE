package org.fhi360.ddd.repositories;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import org.fhi360.ddd.domain.ARV;
import org.fhi360.ddd.domain.Appointment;

import java.util.List;

@Dao
public interface ARVRepository {
    @Insert
    void save(ARV arv);

    @Update
    void update(ARV arv);

    @Query("SELECT * from ARV")
    List<ARV> findAll();

}
