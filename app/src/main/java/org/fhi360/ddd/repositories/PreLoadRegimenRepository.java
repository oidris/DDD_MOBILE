package org.fhi360.ddd.repositories;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import org.fhi360.ddd.domain.PreLoadRegimen;
import org.fhi360.ddd.domain.Regimen;

import java.util.List;

@Dao
public interface PreLoadRegimenRepository {
    @Query("SELECT * FROM preloadregimen")
    List<PreLoadRegimen> findByAll();

    @Query("SELECT * FROM preloadregimen where id = :id")
    PreLoadRegimen findByOne(int id);

    @Query("SELECT * FROM preloadregimen where regimentype = :regimentype")
    PreLoadRegimen getRegimenTypeId(String regimentype);

    @Query("SELECT regimen FROM preloadregimen where regimentype_id == 300")
    List<String> getARV();


    @Query("SELECT regimen FROM preloadregimen where regimentype_id = :regimentypeId")
    List<String> getRegimens(int regimentypeId);

    @Query("SELECT regimen FROM preloadregimen where regimentype_id = 8")
    List<String> getOtherMedicines();

    @Insert
    void save(PreLoadRegimen regimen);

    @Insert
    void insertAll(PreLoadRegimen... regimen);

    @Update
    void update(PreLoadRegimen regimen);
}
