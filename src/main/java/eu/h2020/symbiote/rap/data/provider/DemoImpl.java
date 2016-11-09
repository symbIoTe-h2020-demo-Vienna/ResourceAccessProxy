/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.h2020.symbiote.rap.data.provider;

import eu.h2020.symbiote.rap.data.DataDriverInterface;
import eu.h2020.symbiote.rap.data.model.Location;
import eu.h2020.symbiote.rap.data.model.Observation;
import eu.h2020.symbiote.rap.data.model.Sensor;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Aleksandar
 */
public class DemoImpl implements DataDriverInterface{

    @Override
    public List<Observation> getObservations(String sensorId) {
        List<Observation> allObservations = new LinkedList<>();
        
        //get data from data storage
        if (sensorId == null) {
            //get measurements made by sensorId
        } else {
            //get measurements
        }
        
        //create Observation objects
        Random r = new Random();
        //setting a random value observation 
        Observation o = new Observation("1", System.currentTimeMillis(), System.currentTimeMillis(), r.nextInt(100), "FetureOfInterest-Name", "FOI-Description", "ObservedProperty-Name", "ObservedProperty-Description", "UnitOfMeasurement-Name", "UOM-Description");
        o.setLocation(new Location("Location-Name", "Location-Description", 16d, 48d, 158d));
        allObservations.add(o);
        
        o = new Observation("2", System.currentTimeMillis(), System.currentTimeMillis(), new Location("Location2-Name", "Location2-Description", 16d, 48d, 158d), r.nextInt(100), "FetureOfInterest2-Name", "FOI2-Description", "ObservedProperty2-Name", "ObservedProperty2-Description", "UnitOfMeasurement2-Name", "UOM2-Description");
        allObservations.add(o);
        
        return allObservations;
    }

    @Override
    public List<Sensor> getSensors(String sensorId) {
        List<Sensor> allSensors = new LinkedList<>();
        
        //get sensor information from storage
        if (sensorId == null) {
            //get sensor info of sensorId
        } else {
            //get info about all sensors
        }
        
        //create Sensor objects
        Sensor s = new Sensor("Id1", "Name1", "Owner1", "Description1", new Location("Location1-Name", "Location1-Description", 16d, 48d, 158d), new LinkedList<String>(Arrays.asList("ObservedProperty-Name", "ObservedProperty2-Name")), null);
        allSensors.add(s);
        s = new Sensor("Id2", "Name2", "Owner2", "Description2", new Location("Location2-Name", "Location2-Description", 16d, 48d, 158d), new LinkedList<String>(), null);
        s.addObservedProperty("ObservedProperty-Name");
        s.addObservedProperty("ObservedProperty2-Name");
        allSensors.add(s);
        
        return allSensors;
    }
    
}
