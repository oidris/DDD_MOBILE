package org.fhi360.ddd.domain;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import lombok.Data;
@Data
@Entity
public class Regimen implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private  int id;
    @ColumnInfo(name = "regimen_id")
    private int regimenId;
    @ColumnInfo(name = "regimen")
    private String regimen;
    @ColumnInfo(name = "regimentype_id")
    private int regimentypeId;
    @ColumnInfo(name = "regimentype")
    private String regimentype;


}
