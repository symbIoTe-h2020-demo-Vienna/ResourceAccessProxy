/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.h2020.symbiote.rap.data.model;

/**
 *
 * @author Aleksandar
 */
public class Observation {

    private String Id;//platform-specific Id
    private long samplingTime;
    private long resultTime;
    private Location location;
    private Object observationValue;
    private String FOIName;
    private String FOIDescription;
    private String observedPropertyName;
    private String observedPropertyDescription;
    private String UOMName;
    private String UOMSymbol;

    public Observation(String Id, long samplingTime, long resultTime, Location location, Object observationValue, String FOIName, String FOIDescription, String observedPropertyName, String observedPropertyDescription, String UOMName, String UOMSymbol) {
        this.Id = Id;
        this.samplingTime = samplingTime;
        this.resultTime = resultTime;
        this.location = location;
        this.observationValue = observationValue;
        this.FOIName = FOIName;
        this.FOIDescription = FOIDescription;
        this.observedPropertyName = observedPropertyName;
        this.observedPropertyDescription = observedPropertyDescription;
        this.UOMName = UOMName;
        this.UOMSymbol = UOMSymbol;
    }

    public Observation(String Id, long samplingTime, long resultTime, Object observationValue, String FOIName, String FOIDescription, String observedPropertyName, String observedPropertyDescription, String UOMName, String UOMSymbol) {
        this.Id = Id;
        this.samplingTime = samplingTime;
        this.resultTime = resultTime;
        this.observationValue = observationValue;
        this.FOIName = FOIName;
        this.FOIDescription = FOIDescription;
        this.observedPropertyName = observedPropertyName;
        this.observedPropertyDescription = observedPropertyDescription;
        this.UOMName = UOMName;
        this.UOMSymbol = UOMSymbol;
    }

    public String getId() {
        return Id;
    }

    public long getSamplingTime() {
        return samplingTime;
    }

    public long getResultTime() {
        return resultTime;
    }

    public Location getLocation() {
        return location;
    }

    public Object getObservationValue() {
        return observationValue;
    }

    public String getFOIName() {
        return FOIName;
    }

    public String getFOIDescription() {
        return FOIDescription;
    }

    public String getObservedPropertyName() {
        return observedPropertyName;
    }

    public String getObservedPropertyDescription() {
        return observedPropertyDescription;
    }

    public String getUOMName() {
        return UOMName;
    }

    public String getUOMSymbol() {
        return UOMSymbol;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Observation{" + "Id=" + Id + ", samplingTime=" + samplingTime + ", resultTime=" + resultTime + ", location=" + location + ", observationValue=" + observationValue + ", FOIName=" + FOIName + ", FOIDescription=" + FOIDescription + ", observedPropertyName=" + observedPropertyName + ", observedPropertyDescription=" + observedPropertyDescription + ", UOMName=" + UOMName + ", UOMSymbol=" + UOMSymbol + '}';
    }
}
