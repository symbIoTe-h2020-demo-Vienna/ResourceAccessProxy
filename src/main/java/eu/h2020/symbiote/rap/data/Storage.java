/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package eu.h2020.symbiote.rap.data;

import java.util.List;
import eu.h2020.symbiote.rap.data.model.Observation;
import eu.h2020.symbiote.rap.data.model.Sensor;

import eu.h2020.symbiote.rap.service.DemoEdmProvider;
import eu.h2020.symbiote.rap.util.Util;
import org.apache.olingo.commons.api.data.ComplexValue;

import org.apache.olingo.commons.api.data.Entity;
import org.apache.olingo.commons.api.data.EntityCollection;
import org.apache.olingo.commons.api.data.Property;
import org.apache.olingo.commons.api.data.ValueType;
import org.apache.olingo.commons.api.edm.EdmEntitySet;
import org.apache.olingo.commons.api.edm.EdmEntityType;
import org.apache.olingo.commons.api.edm.FullQualifiedName;
import org.apache.olingo.server.api.uri.UriParameter;

public class Storage {

    DataDriverInterface driver;
    
    public Storage(DataDriverInterface implementation) {
        this.driver = implementation;
    }

    /* PUBLIC FACADE */
    public EntityCollection readEntitySetData(EdmEntitySet edmEntitySet) {
        EntityCollection entitySet = null;

        if (edmEntitySet.getName().equals(DemoEdmProvider.ES_OBSERVATIONS_NAME)) {
            //entitySet = getProducts();
            entitySet = getObservations(null);
        } else if (edmEntitySet.getName().equals(DemoEdmProvider.ES_SENSORS_NAME)) {
            //entitySet = getCategories();
            entitySet = getSensors();

        }

        return entitySet;
    }

    public Entity readEntityData(EdmEntitySet edmEntitySet, List<UriParameter> keyParams) {
        Entity entity = null;

        EdmEntityType edmEntityType = edmEntitySet.getEntityType();

        if (edmEntityType.getName().equals(DemoEdmProvider.ET_OBSERVATION_NAME)) {
            //entity = getProduct(edmEntityType, keyParams);
            entity = getObservation(edmEntityType, keyParams);
        } else if (edmEntityType.getName().equals(DemoEdmProvider.ET_SENSOR_NAME)) {
            //entity = getCategory(edmEntityType, keyParams);
            entity = getSensor(edmEntityType, keyParams);
        }

        return entity;
    }

    // Navigation
    public Entity getRelatedEntity(Entity entity, EdmEntityType relatedEntityType) {
        EntityCollection collection = getRelatedEntityCollection(entity, relatedEntityType);
        if (collection.getEntities().isEmpty()) {
            return null;
        }
        return collection.getEntities().get(0);
    }

    public Entity getRelatedEntity(Entity entity, EdmEntityType relatedEntityType, List<UriParameter> keyPredicates) {

        EntityCollection relatedEntities = getRelatedEntityCollection(entity, relatedEntityType);
        return Util.findEntity(relatedEntityType, relatedEntities, keyPredicates);
    }

    public EntityCollection getRelatedEntityCollection(Entity sourceEntity, EdmEntityType targetEntityType) {
        EntityCollection navigationTargetEntityCollection = new EntityCollection();

        FullQualifiedName relatedEntityFqn = targetEntityType.getFullQualifiedName();
        String sourceEntityFqn = sourceEntity.getType();

        if (sourceEntityFqn.equals(DemoEdmProvider.ET_OBSERVATION_FQN.getFullQualifiedNameAsString())
                && relatedEntityFqn.equals(DemoEdmProvider.ET_SENSOR_FQN)) {
            // relation Observations->Sensor (not possible at the moment)
            int observationID = (Integer) sourceEntity.getProperty("ID").getValue();

        } else if (sourceEntityFqn.equals(DemoEdmProvider.ET_SENSOR_FQN.getFullQualifiedNameAsString())
                && relatedEntityFqn.equals(DemoEdmProvider.ET_OBSERVATION_FQN)) {
            // relation Sensor->Observations (result all observations by this sensor)
            String sensorId = (String) sourceEntity.getProperty("ID").getValue();

            navigationTargetEntityCollection = createObservationEntities(driver.getObservations(sensorId));
        }

        if (navigationTargetEntityCollection.getEntities().isEmpty()) {
            return null;
        }

        return navigationTargetEntityCollection;
    }

    /* INTERNAL */
    private EntityCollection getObservations(String sensorId) {
        return createObservationEntities(driver.getObservations(sensorId));
    }

    private EntityCollection getSensors() {
        return createSensorEntities(driver.getSensors(null));
    }

    private Entity getObservation(EdmEntityType edmEntityType, List<UriParameter> keyParams) {

        // the list of entities at runtime
        EntityCollection entitySet = createObservationEntities(driver.getObservations(null));

        /* generic approach to find the requested entity */
        return Util.findEntity(edmEntityType, entitySet, keyParams);
    }

    private Entity getSensor(EdmEntityType edmEntityType, List<UriParameter> keyParams) {

        // the list of entities at runtime
        EntityCollection entitySet = createSensorEntities(driver.getSensors(null));

        /* generic approach to find the requested entity */
        return Util.findEntity(edmEntityType, entitySet, keyParams);
    }

    private EntityCollection createObservationEntities(List<Observation> observations) {
        EntityCollection ec = new EntityCollection();
        for (Observation o : observations) {
            Entity entity = new Entity();

            entity.addProperty(new Property(null, "ID", ValueType.PRIMITIVE, o.getId()));
            //entity.addProperty(new Property(null, "SamplingTime", ValueType.PRIMITIVE, o.samplingTime/1000*1000));
            entity.addProperty(new Property(null, "ResultTime", ValueType.PRIMITIVE, o.getResultTime()/1000*1000));

            ComplexValue complexValue = new ComplexValue();
            List<Property> complexproperties = complexValue.getValue();
            //complexproperties.add(new Property(null, "Name", ValueType.PRIMITIVE, o.FOI));
            //entity.addProperty(new Property(null, "FeatureOfInterest", ValueType.COMPLEX, complexValue));
            entity.addProperty(new Property(null, "FeatureOfInterest", ValueType.PRIMITIVE, o.getFOIName()));

            complexValue = new ComplexValue();
            complexproperties = complexValue.getValue();
            complexproperties.add(new Property(null, "lon", ValueType.PRIMITIVE, o.getLocation().getLongitude()));
            complexproperties.add(new Property(null, "lat", ValueType.PRIMITIVE, o.getLocation().getLatitude()));
            complexproperties.add(new Property(null, "alt", ValueType.PRIMITIVE, o.getLocation().getAltitude()));
            entity.addProperty(new Property(null, "Location", ValueType.COMPLEX, complexValue));

            //complexValue = new ComplexValue();
            //complexproperties = complexValue.getValue();
            //complexproperties.add(new Property(null, "Name", ValueType.PRIMITIVE, o.observationProp));
            //complexproperties.add(new Property(null, "Description", ValueType.PRIMITIVE, "Ambiental temperature"));
            //entity.addProperty(new Property(null, "ObservedProperty", ValueType.COMPLEX, complexValue));
            //ComplexValue UOMValue = new ComplexValue();
            //List<Property> UOMproperties = UOMValue.getValue();
            //UOMproperties.add(new Property(null, "Name", ValueType.PRIMITIVE, o.UOMName));
            //UOMproperties.add(new Property(null, "Symbol", ValueType.PRIMITIVE, o.UOMSymbol));
            complexValue = new ComplexValue();
            complexproperties = complexValue.getValue();
            complexproperties.add(new Property(null, "Value", ValueType.PRIMITIVE, o.getObservationValue()));
            //complexproperties.add(new Property(null, "UnitOfMeasurement", ValueType.COMPLEX, UOMValue));

            complexproperties.add(new Property(null, "UnitOfMeasurement", ValueType.PRIMITIVE, o.getUOMSymbol()));
            complexproperties.add(new Property(null, "ObservedProperty", ValueType.PRIMITIVE, o.getObservedPropertyName()));

            entity.addProperty(new Property(null, "ObservationValue", ValueType.COMPLEX, complexValue));
            entity.setType(DemoEdmProvider.ET_OBSERVATION_FQN.getFullQualifiedNameAsString());

            ec.getEntities().add(entity);
        }

        return ec;
    }
    
    private EntityCollection createSensorEntities(List<Sensor> sensors) {
        EntityCollection ec = new EntityCollection();
        for (Sensor s : sensors) {
            Entity entity = new Entity();
            entity.addProperty(new Property(null, "ID", ValueType.PRIMITIVE, s.getId()));
            entity.addProperty(new Property(null, "Name", ValueType.PRIMITIVE, s.getName()));
            entity.addProperty(new Property(null, "ObservationProperties", ValueType.COLLECTION_PRIMITIVE, s.getObservedProperties()));
            entity.setType(DemoEdmProvider.ET_SENSOR_FQN.getFullQualifiedNameAsString());
            ec.getEntities().add(entity);
        }

        return ec;
    }

}
