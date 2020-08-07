package org.fhi360.ddd.repositories;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import org.fhi360.ddd.domain.Devolve;

import java.util.Date;
import java.util.List;

@Dao
public interface DevolveRepository {
    @Query("SELECT * FROM devolve")
    List<Devolve> findByAll();

    @Query("SELECT * FROM devolve where facility_id =:facilityId and patient_id=:patientId and date_devolved =:dateDevolved")
    Devolve findByOne(int facilityId, int patientId, String dateDevolved);

    @Query("SELECT * FROM devolve WHERE   patient_id = :patientId ORDER BY date_devolved DESC LIMIT 1")
    Devolve getLastDevolve( int patientId);

    @Query("SELECT * FROM devolve ORDER BY date_devolved DESC LIMIT 1")
    Devolve getLastDevolve1();

    @Insert
    void save(Devolve devolve);

    @Query("UPDATE devolve set date_discontinued =:dateDiscontinued , reason_discontinued =:reasonDiscontinued where  patient_id=:patientId")
    void update1(int patientId, Date dateDiscontinued, String reasonDiscontinued);

    @Update
    void update(Devolve devolve);
}
