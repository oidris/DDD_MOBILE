package org.fhi360.ddd.webservice;
import android.annotation.SuppressLint;
import android.content.Context;
import org.fhi360.ddd.Db.DDDDb;
import org.fhi360.ddd.domain.Facility;
import org.fhi360.ddd.domain.Patient;
import org.fhi360.ddd.domain.Regimen;
import org.fhi360.ddd.util.DateUtil;
import org.fhi360.ddd.util.Scrambler;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.Date;

public class WebserviceResponseServerHandler {
    private Context context;

    public WebserviceResponseServerHandler(Context context) {
        this.context = context;
    }

    @SuppressLint("SimpleDateFormat")
    public void parseResult(String result) {
        try {
            if (!result.isEmpty()) {
                JSONObject response = new JSONObject(result);
                JSONArray facilities = response.optJSONArray("facilities");
                if (facilities != null) {
                    for (int i = 0; i < facilities.length(); i++) {
                        JSONObject facility = facilities.optJSONObject(i);
                        int facilityId = facility.getInt("facility_id");
                        String state = facility.getString("state");
                        String lga = facility.getString("lga");
                        String name = facility.getString("name");
                        Facility id = DDDDb.getInstance(context).facilityRepository().findOne(facilityId);
                        if (id != null) {
                            Facility facility1 = new Facility();
                            facility1.setId(id.getId());
                            facility1.setFacilityId(facilityId);
                            facility1.setState(state);
                            facility1.setName(name);
                            facility1.setLga(lga);
                            DDDDb.getInstance(context).facilityRepository().update(facility1);
                        } else {
                            Facility facility1 = new Facility();
                            facility1.setFacilityId(facilityId);
                            facility1.setState(state);
                            facility1.setLga(lga);
                            facility1.setName(name);
                            DDDDb.getInstance(context).facilityRepository().save(facility1);
                        }
                    }
                }

                JSONArray regimens = response.optJSONArray("regimens");
                if (regimens != null) {
                    for (int i = 0; i < regimens.length(); i++) {
                        JSONObject regimen = regimens.optJSONObject(i);
                        int regimenId = regimen.getInt("regimen_id");
                        String description = regimen.getString("regimen");
                        int regimentypeId = regimen.getInt("regimentype_id");
                        String regimentype = regimen.getString("regimentype");

                        Regimen id = DDDDb.getInstance(context).regimenRepository().findByOne(regimenId);
                        if (id != null) {
                            Regimen regimen1 = new Regimen();
                            regimen1.setId(id.getId());
                            regimen1.setRegimen(description);
                            regimen1.setRegimentypeId(regimentypeId);
                            regimen1.setRegimenId(regimenId);
                            regimen1.setRegimentype(regimentype);
                          //  DDDDb.getInstance(context).regimenRepository().update(regimen1);
                        } else {
                            Regimen regimen1 = new Regimen();
                            regimen1.setRegimenId(regimenId);
                            regimen1.setRegimen(description);
                            regimen1.setRegimentypeId(regimentypeId);
                            regimen1.setRegimentype(regimentype);
                           // DDDDb.getInstance(context).regimenRepository().save(regimen1);

                        }
                    }
                }

                JSONArray patients = response.optJSONArray("patients");
                Scrambler scrambler = new Scrambler();
                if (patients != null) {
                    for (int i = 0; i < patients.length(); i++) {
                        JSONObject patient = patients.optJSONObject(i);
                        int facilityId = patient.getInt("facility_id");
                        int patientId = patient.getInt("patient_id");
                        String hospitalNum = patient.getString("hospital_num");
                        String uniqueId = patient.getString("unique_id");
                        String surname = patient.getString("surname");
                        surname = scrambler.unscrambleCharacters(surname);
                        String otherNames = patient.getString("other_names");
                        otherNames = scrambler.unscrambleCharacters(otherNames);
                        String gender = patient.getString("gender");
                        Date dateBirth = patient.getString("date_birth").isEmpty() ? null : DateUtil.parseStringToDate(patient.getString("date_birth"), "yyyy-MM-dd");
                        String address = patient.getString("address");
                        address = scrambler.unscrambleCharacters(address);
                        String phone = patient.getString("phone");
                        phone = scrambler.unscrambleNumbers(phone);
                        Date dateStarted = DateUtil.parseStringToDate(patient.getString("date_started"), "yyyy-MM-dd");
                        String regimentype = patient.getString("regimentype");
                        String regimen = patient.getString("regimen");
                        String lastClinicStage = patient.getString("last_clinic_stage");
                        Double lastViralLoad = patient.get("last_viral_load").toString().isEmpty() ? 0.0 : patient.getDouble("last_viral_load");
                        Date dateLastViralLoad = patient.getString("date_last_viral_load").isEmpty() ? null : DateUtil.parseStringToDate(patient.getString("date_last_viral_load"), "yyyy-MM-dd");
                        Date viralLoadDueDate = patient.getString("viral_load_due_date").isEmpty() ? null : DateUtil.parseStringToDate(patient.getString("viral_load_due_date"), "yyyy-MM-dd");
                        String viralLoadType = patient.getString("viral_load_type");
                        Double lastCd4 = patient.get("last_cd4").toString().isEmpty() ? 0.0 : patient.getDouble("last_cd4");
                        Date dateLastCd4 = patient.getString("date_last_cd4").isEmpty() ? null : DateUtil.parseStringToDate(patient.getString("date_last_cd4"), "yyyy-MM-dd");
                        Date dateLastRefill = patient.getString("date_last_refill").isEmpty() ? null : DateUtil.parseStringToDate(patient.getString("date_last_refill"), "yyyy-MM-dd");
                        Date dateNextRefill = patient.getString("date_next_refill").isEmpty() ? null : DateUtil.parseStringToDate(patient.getString("date_next_refill"), "yyyy-MM-dd");
                        Date dateLastClinic = patient.getString("date_last_clinic").isEmpty() ? null : DateUtil.parseStringToDate(patient.getString("date_last_clinic"), "yyyy-MM-dd");
                        Date dateNextClinic = patient.getString("date_next_clinic").isEmpty() ? null : DateUtil.parseStringToDate(patient.getString("date_next_clinic"), "yyyy-MM-dd");
                        String lastRefillSetting = patient.getString("last_refill_setting");

                        Patient id = DDDDb.getInstance(context).patientRepository().findByPatient(patientId);
                        if (id != null) {
                            Patient patient1 = new Patient();
                            patient1.setId(id.getId());
                            patient1.setFacilityName(DDDDb.getInstance(context).facilityRepository().findOne(facilityId).getName());
                            patient1.setPatientId(patientId);
                            patient1.setHospitalNum(hospitalNum);
                            patient1.setUniqueId(uniqueId);
                            patient1.setSurname(surname);
                            patient1.setOtherNames(otherNames);
                            patient1.setGender(gender);
                            patient1.setDateBirth(DateUtil.parseDateToString(dateBirth, "yyyy-MM-dd"));
                            patient1.setAddress(address);
                            patient1.setPhone(phone);
                            patient1.setDateStarted(DateUtil.parseDateToString(dateStarted, "dd-MM-yyyy"));
                            patient1.setLastClinicStage(lastClinicStage);
                            patient1.setLastViralLoad(lastViralLoad);
                            patient1.setDateLastViralLoad(DateUtil.parseDateToString(dateLastViralLoad, "dd-MM-yyyy"));

                            patient1.setViralLoadDueDate(DateUtil.parseDateToString(viralLoadDueDate, "dd-MM-yyyy"));

                            patient1.setViralLoadType(viralLoadType);
                            patient1.setDateLastClinic(DateUtil.parseDateToString(dateLastClinic, "dd-MM-yyyy"));
                            patient1.setDateNextClinic(DateUtil.parseDateToString(dateNextClinic, "dd-MM-yyyy"));
                            patient1.setDateLastRefill(DateUtil.parseDateToString(dateLastRefill, "dd-MM-yyyy"));
                            patient1.setDateNextRefill(DateUtil.parseDateToString(dateNextRefill, "dd-MM-yyyy"));
                            patient1.setRegimen("regimen");
                            patient1.setRegimenType("regimentype");
                            patient1.setStatus(1);
                            DDDDb.getInstance(context).patientRepository().update(patient1);
                        } else {
                            Patient patient1 = new Patient();
                            patient1.setFacilityName(DDDDb.getInstance(context).facilityRepository().findOne(facilityId).getName());
                            patient1.setPatientId(patientId);
                            patient1.setHospitalNum(hospitalNum);
                            patient1.setUniqueId(uniqueId);
                            patient1.setSurname(surname);
                            patient1.setOtherNames(otherNames);
                            patient1.setGender(gender);
                            patient1.setDateBirth(DateUtil.parseDateToString(dateBirth, "yyyy-MM-dd"));
                            patient1.setAddress(address);
                            patient1.setPhone(phone);
                            patient1.setDateStarted(DateUtil.parseDateToString(dateStarted, "dd-MM-yyyy"));
                            patient1.setLastClinicStage(lastClinicStage);
                            patient1.setLastViralLoad(lastViralLoad);
                            patient1.setDateLastViralLoad(DateUtil.parseDateToString(dateLastViralLoad, "dd-MM-yyyy"));
                            patient1.setViralLoadDueDate(DateUtil.parseDateToString(viralLoadDueDate, "dd-MM-yyyy"));
                            patient1.setViralLoadType(viralLoadType);
                            patient1.setDateLastClinic(DateUtil.parseDateToString(dateLastClinic, "dd-MM-yyyy"));
                            patient1.setDateNextClinic(DateUtil.parseDateToString(dateNextClinic, "dd-MM-yyyy"));
                            patient1.setDateLastRefill(DateUtil.parseDateToString(dateLastRefill, "dd-MM-yyyy"));
                            patient1.setDateNextRefill(DateUtil.parseDateToString(dateNextRefill, "dd-MM-yyyy"));
                            patient1.setRegimen("regimen");
                            patient1.setRegimenType("regimentype");
                            patient1.setStatus(1);
                            DDDDb.getInstance(context).patientRepository().save(patient1);
                        }
                    }
                }

//                DevolveDAO devolveDAO = new DevolveDAO(context);
//                JSONArray devolves = response.optJSONArray("devolves");
//                if(devolves != null) {
//                    for (int i = 0; i < devolves.length(); i++) {
//                        JSONObject devolve = devolves.optJSONObject(i);
//                        int facilityId = devolve.getInt("facility_id");
//                        int patientId = devolve.getInt("patient_id");
//                        Date dateDevolved = DateUtil.parseStringToDate(devolve.getString("date_devolved"), "yyyy-MM-dd");
//                        String viralLoadAssessed = devolve.getString("viral_load_assessed");
//                        Double lastViralLoad = devolve.get("last_viral_load").toString().isEmpty()? 0.0 : devolve.getDouble("last_viral_load");
//                        Date dateLastViralLoad = devolve.getString("date_last_viral_load").isEmpty()? null : DateUtil.parseStringToDate(devolve.getString("date_last_viral_load"), "yyyy-MM-dd");
//                        String cd4Assessed = devolve.getString("cd4_assessed");
//                        Double lastCd4 = devolve.get("last_cd4").toString().isEmpty()? 0.0 : devolve.getDouble("last_cd4");
//                        Date dateLastCd4 = devolve.getString("date_last_cd4").isEmpty()? null : DateUtil.parseStringToDate(devolve.getString("date_last_cd4"), "yyyy-MM-dd");
//                        String lastClinicStage = devolve.getString("last_clinic_stage");
//                        Date dateLastClinicStage = devolve.getString("date_last_clinic_stage").isEmpty()? null : DateUtil.parseStringToDate(devolve.getString("date_last_clinic_stage"), "yyyy-MM-dd");
//                        String arvDispensed = devolve.getString("arv_dispensed");
//                        String regimentype = devolve.getString("regimentype");
//                        String regimen = devolve.getString("regimen");
//                        Date dateLastRefill = devolve.getString("date_last_refill").isEmpty()? null : DateUtil.parseStringToDate(devolve.getString("date_last_refill"), "yyyy-MM-dd");
//                        Date dateNextRefill = devolve.getString("date_next_refill").isEmpty()? null : DateUtil.parseStringToDate(devolve.getString("date_next_refill"), "yyyy-MM-dd");
//                        Date dateLastClinic = devolve.getString("date_last_clinic").isEmpty()? null : DateUtil.parseStringToDate(devolve.getString("date_last_clinic"), "yyyy-MM-dd");
//                        Date dateNextClinic = devolve.getString("date_next_clinic").isEmpty()? null : DateUtil.parseStringToDate(devolve.getString("date_next_clinic"), "yyyy-MM-dd");
//                        String notes = devolve.getString("notes");;
//                        Date dateDiscontinued = devolve.getString("date_discontinued").isEmpty()? null : DateUtil.parseStringToDate(devolve.getString("date_discontinued"), "yyyy-MM-dd");
//                        String reasonDiscontinued = devolve.getString("reason_discontinued");;
//
//                        int id = devolveDAO.getId(facilityId, patientId, dateDevolved);
//                        if(id != 0) {
//                            devolveDAO.update(id, facilityId, patientId, dateDevolved, viralLoadAssessed, lastViralLoad, dateLastViralLoad, cd4Assessed, lastCd4, dateLastCd4, lastClinicStage, dateLastClinicStage,
//                                    arvDispensed, regimentype, regimen, dateLastRefill, dateNextRefill, dateLastClinic, dateNextClinic, notes, dateDiscontinued, reasonDiscontinued);
//                        }
//                        else {
//                            devolveDAO.save(facilityId, patientId, dateDevolved, viralLoadAssessed, lastViralLoad, dateLastViralLoad, cd4Assessed, lastCd4, dateLastCd4, lastClinicStage, dateLastClinicStage,
//                                    arvDispensed, regimentype, regimen, dateLastRefill, dateNextRefill, dateLastClinic, dateNextClinic, notes, dateDiscontinued, reasonDiscontinued);
//                        }
//                    }
//                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
