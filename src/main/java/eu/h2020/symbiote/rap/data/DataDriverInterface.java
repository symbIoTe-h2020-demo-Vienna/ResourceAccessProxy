/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.h2020.symbiote.rap.data;

import java.util.List;
import eu.h2020.symbiote.rap.data.model.Observation;
import eu.h2020.symbiote.rap.data.model.Sensor;

/**
 *
 * @author Aleksandar
 */
public interface DataDriverInterface {
    
    public List<Observation> getObservations(String sensorId);
    public List<Sensor> getSensors(String sensorId);
}
