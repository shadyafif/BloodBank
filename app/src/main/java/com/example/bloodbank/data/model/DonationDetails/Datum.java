
package com.example.bloodbank.data.model.DonationDetails;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Datum implements Parcelable {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("client_id")
    @Expose
    private String clientId;
    @SerializedName("patient_name")
    @Expose
    private String patientName;
    @SerializedName("patient_age")
    @Expose
    private String patientAge;
    @SerializedName("blood_type_id")
    @Expose
    private String bloodTypeId;
    @SerializedName("bags_num")
    @Expose
    private String bagsNum;
    @SerializedName("hospital_name")
    @Expose
    private String hospitalName;
    @SerializedName("hospital_address")
    @Expose
    private String hospitalAddress;
    @SerializedName("city_id")
    @Expose
    private String cityId;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("notes")
    @Expose
    private String notes;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("city")
    @Expose
    private City city;
    @SerializedName("client")
    @Expose
    private Client client;
    @SerializedName("blood_type")
    @Expose
    private BloodType bloodType;

    protected Datum(Parcel in) {
        id = in.readInt();
        createdAt = in.readString();
        updatedAt = in.readString();
        clientId = in.readString();
        patientName = in.readString();
        patientAge = in.readString();
        bloodTypeId = in.readString();
        bagsNum = in.readString();
        hospitalName = in.readString();
        hospitalAddress = in.readString();
        cityId = in.readString();
        phone = in.readString();
        notes = in.readString();
        latitude = in.readString();
        longitude = in.readString();
    }

    public static final Creator<Datum> CREATOR = new Creator<Datum>() {
        @Override
        public Datum createFromParcel(Parcel in) {
            return new Datum(in);
        }

        @Override
        public Datum[] newArray(int size) {
            return new Datum[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientAge() {
        return patientAge;
    }

    public void setPatientAge(String patientAge) {
        this.patientAge = patientAge;
    }

    public String getBloodTypeId() {
        return bloodTypeId;
    }

    public void setBloodTypeId(String bloodTypeId) {
        this.bloodTypeId = bloodTypeId;
    }

    public String getBagsNum() {
        return bagsNum;
    }

    public void setBagsNum(String bagsNum) {
        this.bagsNum = bagsNum;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getHospitalAddress() {
        return hospitalAddress;
    }

    public void setHospitalAddress(String hospitalAddress) {
        this.hospitalAddress = hospitalAddress;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public BloodType getBloodType() {
        return bloodType;
    }

    public void setBloodType(BloodType bloodType) {
        this.bloodType = bloodType;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(createdAt);
        dest.writeString(updatedAt);
        dest.writeString(clientId);
        dest.writeString(patientName);
        dest.writeString(patientAge);
        dest.writeString(bloodTypeId);
        dest.writeString(bagsNum);
        dest.writeString(hospitalName);
        dest.writeString(hospitalAddress);
        dest.writeString(cityId);
        dest.writeString(phone);
        dest.writeString(notes);
        dest.writeString(latitude);
        dest.writeString(longitude);
    }
}
