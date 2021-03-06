package com.example.aptiv.Model.Class;

import java.io.Serializable;

import static java.lang.Float.parseFloat;

public class Profile implements Serializable {
    private String ProfileName;
    private String temperature;
    private String humidity;
    private String gain;
    private String luminosity;
    private String full;
    private String ir;
    private String pressure;
    private String sound;
    private String altitude;
    private String light;
    private String lux;


    public Profile(String ProfileName, String temperature, String humidity, String gain, String luminosity, String full, String ir,
                   String pressure, String sound, String altitude, String light, String lux) {
        this.ProfileName = ProfileName;
        this.temperature = temperature;
        this.humidity = humidity;
        this.gain = gain;
        this.luminosity = luminosity;
        this.full = full;
        this.ir = ir;
        this.pressure = pressure;
        this.sound = sound;
        this.altitude = altitude;
        this.light = light;
        this.lux = lux;
    }

    public String getProfileName() {
        return this.ProfileName;
    }

    public void setProfileName(String profileName) {
        ProfileName = profileName;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getHumidity() {
        if (humidity == null) {
            return null;
        }
        String hum = String.format("%.2f", parseFloat(humidity));
        return hum;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getGain() {
        return gain;
    }

    public void setGain(String gain) {
        this.gain = gain;
    }

    public String getLuminosity() {
        return luminosity;
    }

    public void setLuminosity(String luminosity) {
        this.luminosity = luminosity;
    }

    public String getFull() {
        return full;
    }

    public void setFull(String full) {
        this.full = full;
    }

    public String getIr() {
        if (lux == null) {
            return null;
        }
        String infra = String.format("%.2f", parseFloat(lux));
        return infra;
    }

    public void setIr(String ir) {
        this.lux = ir;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getSound() {
        return sound;
    }

    public void setSound(String sound) {
        this.sound = sound;
    }

    public String getAltitude() {
        return altitude;
    }

    public void setAltitude(String altitude) {
        this.altitude = altitude;
    }

    public String getLight() {
        return light;
    }

    public void setLight(String light) {
        this.light = light;
    }

    public String getLux() {
        return lux;
    }

    public void setLux(String lux) {
        this.lux = lux;
    }

    public void setFromZone(Zone zone) {
        this.temperature = zone.getTemperature();
        this.humidity = zone.getHumidity();
        this.gain = zone.getGain();
        this.luminosity = zone.getLuminosity();
        this.full = zone.getFull();
        this.ir = zone.getIr();
        this.pressure = zone.getPressure();
        this.sound = zone.getSound();
        this.altitude = zone.getAltitude();
        this.light = zone.getLight();
        this.lux = zone.getLux();
    }
}
