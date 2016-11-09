/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.h2020.symbiote.rap.data.model;

import java.util.List;

/**
 * Created by jawora on 22.09.16.
 */
public class Sensor {

    private String id;
    private String name;
    private String owner;
    private String description;
    private Location location;
    private List<String> observedProperties;
    private Platform platform;

    public Sensor() {
    }

    public Sensor(String name, String owner, String description, Location location, List<String> observedProperties, Platform platform) {
        this.name = name;
        this.owner = owner;
        this.description = description;
        this.location = location;
        this.observedProperties = observedProperties;
        this.platform = platform;
    }

    public Sensor(String id, String name, String owner, String description, Location location, List<String> observedProperties, Platform platform) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.description = description;
        this.location = location;
        this.observedProperties = observedProperties;
        this.platform = platform;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<String> getObservedProperties() {
        return observedProperties;
    }

    public void setObservedProperties(List<String> observedProperties) {
        this.observedProperties = observedProperties;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }
    
    public void addObservedProperty(String observedProperty) {
        this.observedProperties.add(observedProperty);
    }
}