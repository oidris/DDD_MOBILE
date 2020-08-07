package org.fhi360.ddd.domain;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
@Entity
public class Patient implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name = "facility_name")
    private String facilityName;
    @ColumnInfo(name = "patient_id")
    private int patientId;
    @ColumnInfo(name = "hospital_num")
    private String hospitalNum;
    @ColumnInfo(name = "unique_id")
    private String uniqueId;
    @ColumnInfo(name = "surname")
    private String surname;
    @ColumnInfo(name = "other_names")
    private String otherNames;
    @ColumnInfo(name = "gender")
    private String gender;
    @ColumnInfo(name = "date_birth")
    private String dateBirth;
    @ColumnInfo(name = "address")
    private String address;
    @ColumnInfo(name = "phone")
    private String phone;
    @ColumnInfo(name = "date_started")
    private String dateStarted;
    @ColumnInfo(name = "last_clinic_stage")
    private String lastClinicStage;
    @ColumnInfo(name = "regimen_type")
    private String regimenType;
    @ColumnInfo(name = "regimen")
    private String regimen;
    @ColumnInfo(name = "last_viral_load")
    private double lastViralLoad;
    @ColumnInfo(name = "date_last_viral_load")
    private String dateLastViralLoad;
    @ColumnInfo(name = "viral_load_due_date")
    private String viralLoadDueDate;
    @ColumnInfo(name = "viralLoad_type")
    private String viralLoadType;
    @ColumnInfo(name = "dateLast_refill")
    private String dateLastRefill;
    @ColumnInfo(name = "date_next_refill")
    private String dateNextRefill;
    @ColumnInfo(name = "date_last_clinic")
    private String dateLastClinic;
    @ColumnInfo(name = "date_next_clinic")
    private String dateNextClinic;
    @ColumnInfo(name = "discontinued")
    private int discontinued;
    @ColumnInfo(name = "pin_code")
    private String pinCode;
    @ColumnInfo(name = "status")
    private int status;

    public Patient() {
    }

    public Patient(String facilityName, int patientId, String hospitalNum, String uniqueId, String surname, String otherNames, String gender, String dateBirth, String address, String phone, String dateStarted, String lastClinicStage, String regimenType, String regimen, double lastViralLoad, String dateLastViralLoad, String viralLoadDueDate, String viralLoadType, String dateLastRefill, String dateNextRefill, String dateLastClinic, String dateNextClinic, int discontinued, String pinCode, int status) {
        this.facilityName = facilityName;
        this.patientId = patientId;
        this.hospitalNum = hospitalNum;
        this.uniqueId = uniqueId;
        this.surname = surname;
        this.otherNames = otherNames;
        this.gender = gender;
        this.dateBirth = dateBirth;
        this.address = address;
        this.phone = phone;
        this.dateStarted = dateStarted;
        this.lastClinicStage = lastClinicStage;
        this.regimenType = regimenType;
        this.regimen = regimen;
        this.lastViralLoad = lastViralLoad;
        this.dateLastViralLoad = dateLastViralLoad;
        this.viralLoadDueDate = viralLoadDueDate;
        this.viralLoadType = viralLoadType;
        this.dateLastRefill = dateLastRefill;
        this.dateNextRefill = dateNextRefill;
        this.dateLastClinic = dateLastClinic;
        this.dateNextClinic = dateNextClinic;
        this.discontinued = discontinued;
        this.pinCode = pinCode;
        this.status = status;
    }


    public static Patient[] patientsPopulate() {
        return new Patient[]{
                new Patient(),
                new Patient("Quelimane Province of Zambezia", 133735,
                        "6970", "1335", "Maria", "dos Santos", "Female",
                        "14/02/1993", "17A Haj Estate Quelimane Province of Zambezia", "+258821947033", "15/09/2016", "Stage I",
                        "ART First Line Adult", "TDF(300mg)+3TC(300mg)+DTG(50mg)", 65,
                        "26/09/2017",
                        "26/09/2018", "Routine", "13/12/2019", "11/03/2020",
                        "09/05/2019", "07/07/2019", 0, "", 1),

                new Patient("Quelimane Province of Zambezia", 135284,
                        "6970", "1335", "Agostinho", "Waia", "Male",
                        "14/10/1974", "3. Zalala Beach Province of Zambezia ", "+259947947044", "15/04/2013", "Stage I",
                        "ART First Line Adult", "TDF(300mg)+3TC(300mg)+DTG(50mg)", 27,
                        "27/06/2017",
                        "27/06/2017", "Routine", "19/06/2020", "07/09/2020",
                        "29/07/2019", "19/06/2020", 0, "", 1),

                new Patient("Quelimane Province of Zambezia", 135294,
                        "69708", "13358", "Anabela", "Ernesto", "Female",
                        "25/12/1985", "2. Quelimane Cathedral", "+2599445758540", "14/12/2015", "Stage I",
                        "ART First Line Adult", "TDF(300mg)+3TC(300mg)+DTG(50mg)", 20,
                        "27/06/2017",
                        "12/02/2020", "Routine", "19/06/2020", "07/09/2020",
                        "29/07/2019", "20/08/2020", 0, "", 1),

                new Patient("Quelimane Province of Zambezia", 135317,
                        "27288", "30", "Alberto", "Vicente", "Male",
                        "14/10/1974", "3.Beach Province of Zambezia", "+259947947044", "15/04/2013", "Stage I",
                        "ART First Line Adult", "TDF(300mg)+3TC(300mg)+DTG(50mg)", 27,
                        "27/06/2017",
                        "27/06/2017", "Routine", "19/06/2020", "07/09/2020",
                        "29/07/2019", "19/06/2020", 0, "", 1),

                new Patient("Quelimane Province of Zambezia", 135311,
                        "2534", "50", "Georgina", "Castro", "Female",
                        "14/10/1974", "3. Mount Namuli Province of Zambezia", "+259947947044", "15/04/2013", "Stage I",
                        "ART First Line Adult", "TDF(300mg)+3TC(300mg)+DTG(50mg)", 27,
                        "27/06/2017",
                        "27/06/2017", "Routine", "19/06/2020", "07/09/2020",
                        "29/07/2019", "19/06/2020", 0, "", 1),

                new Patient("Quelimane Province of Zambezia", 135333,
                        "153", "LAG00153", "Maria", "Fernando", "Female",
                        "14/10/1974", "3.Zalala Beach Province of Zambezia", "+259947947044", "15/04/2013", "Stage I",
                        "ART First Line Adult", "TDF(300mg)+3TC(300mg)+DTG(50mg)", 27,
                        "27/06/2017",
                        "27/06/2017", "Routine", "19/06/2020", "07/09/2020",
                        "29/07/2019", "19/06/2020", 0, "", 1),

                new Patient("Quelimane Province of Zambezia", 135374,
                        "1166", "582", "Tomas", "Mascarenha", "Male",
                        "14/10/1974", "3 Near Zalala Beach Province of Zambezia ", "+259947947044", "15/04/2013", "Stage I",
                        "ART First Line Adult", "TDF(300mg)+3TC(300mg)+DTG(50mg)", 27,
                        "27/06/2017",
                        "27/06/2017", "Routine", "19/06/2020", "07/09/2020",
                        "29/07/2019", "19/06/2020", 0, "", 1),


        };


    }
}
