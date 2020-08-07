package org.fhi360.ddd.domain;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import lombok.Data;

@Data
@Entity
public class Facility implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name = "facility_id")
    private int facilityId;
    @ColumnInfo(name = "state")
    private String state;
    @ColumnInfo(name = "lga")
    private String lga;
    @ColumnInfo(name = "name")
    private String name;

}
