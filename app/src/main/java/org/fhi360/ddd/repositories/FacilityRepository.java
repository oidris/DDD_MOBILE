package org.fhi360.ddd.repositories;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import org.fhi360.ddd.domain.Facility;

import java.util.List;
@Dao
public interface FacilityRepository {

    @Insert
    void save(Facility facility);

    @Update
    void update(Facility facility);


    @Query("SELECT * FROM facility where facility_id = :facilityId")
    Facility findOne(int facilityId);


    @Query("SELECT * FROM facility")
     List<Facility> findAll();


    @Query("SELECT * FROM facility limit 1")
   Facility getFacility();

}
