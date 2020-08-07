package org.fhi360.ddd.repositories;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import org.fhi360.ddd.domain.Patient;
import org.fhi360.ddd.domain.PreLoadRegimen;

import java.util.List;

@Dao
public interface PatientRepository {

    @Query("SELECT * FROM patient")
    List<Patient> findByAll();

    @Query("SELECT * FROM patient where date_started BETWEEN  :to AND  :from")
    List<Patient> dateRange(String to, String from);

    @Query("SELECT * FROM patient")
    Cursor getAllPatient();


    @Query("SELECT * FROM patient where id = :id")
    Patient findOne(int id);

    @Query("SELECT count(*) FROM patient where gender =:femaleGender")
    int genderCount(String femaleGender);


    @Query("SELECT * FROM patient where hospital_num = :hospitalNum and unique_id =:uniqueId")
    boolean findHospitalNum(String hospitalNum, String uniqueId);


    @Query("SELECT * FROM patient WHERE  patient_id =:patientId")
    Patient findByPatient(int patientId);

    @Query("SELECT count(*) FROM patient where pin_code = :pin_code")
    int count(String pin_code);


    @Query("SELECT * FROM patient")
    List<Patient> findByAll1();

    @Query("SELECT count(*) FROM patient where regimen !=null")
    int countRegiment();

    @Query("SELECT * FROM patient where pin_code = :pincode")
    List<Patient> findByPin(String pincode);


    @Insert
    long save(Patient patients);

    @Update
    void update(Patient patients);

    @Query("DELETE  FROM patient where id = 1")
    void delete();

    @Insert
    void insertAll(Patient... patients);

    @Query("SELECT  *  FROM patient WHERE CURRENT_TIME > date_next_refill ORDER BY surname ASC")
    List<Patient> getDefaulters();

    @Query("SELECT  count(*)  FROM patient WHERE CURRENT_TIME > date_next_refill ORDER BY surname ASC")
    int getDefaultersCount();


    @Query("SELECT  *  FROM patient WHERE CURRENT_TIME - date_next_refill >:period and surname LIKE :name OR other_names LIKE :name ORDER BY surname ASC")
    List<Patient> getDefaulters(String period, String name);
}
