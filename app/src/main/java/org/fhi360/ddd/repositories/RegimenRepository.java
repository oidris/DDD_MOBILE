package org.fhi360.ddd.repositories;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import org.fhi360.ddd.domain.Regimen;
import org.fhi360.ddd.domain.User;

import java.util.List;

@Dao
public interface RegimenRepository {
    @Query("SELECT * FROM regimen")
    List<Regimen> findByAll();

    @Query("SELECT * FROM regimen where id = :id")
    Regimen findByOne(int id);

    @Query("SELECT * FROM regimen where regimentype = :regimentype")
    Regimen getRegimenTypeId(String regimentype);

    @Query("SELECT regimen FROM regimen where (regimentype_id >= 1 AND regimentype_id <= 4) OR regimentype_id = 14")
    List<String> getARV();


    @Query("SELECT regimen FROM regimen where regimentype_id = :regimentypeId")
    List<String> getRegimens(int regimentypeId);

    @Query("SELECT regimen FROM regimen where regimentype_id = 8")
    List<String> getOtherMedicines();

    @Insert
    void save(Regimen regimen);

    @Insert
    void insertAll(Regimen... regimen);

    @Update
    void update(Regimen regimen);
}
