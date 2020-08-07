package org.fhi360.ddd.domain;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
@Entity
public class User implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name = "username")
    private String username;
    @ColumnInfo(name = "password")
    private String password;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "role")
    private String role;
    @ColumnInfo(name = "phone")
    private String phone;
    @ColumnInfo(name = "district")
    private String district;
    @ColumnInfo(name = "province")
    private String province;
    @ColumnInfo(name = "contact")
    private String contactDetail;
    @ColumnInfo(name = "date_registration")
    private Date date;

}
