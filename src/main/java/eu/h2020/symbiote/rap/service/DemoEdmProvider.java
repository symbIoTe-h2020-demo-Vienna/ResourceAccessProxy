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
package eu.h2020.symbiote.rap.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.olingo.commons.api.edm.EdmPrimitiveTypeKind;
import org.apache.olingo.commons.api.edm.FullQualifiedName;
import org.apache.olingo.commons.api.edm.provider.CsdlAbstractEdmProvider;
import org.apache.olingo.commons.api.edm.provider.CsdlEntityContainer;
import org.apache.olingo.commons.api.edm.provider.CsdlEntityContainerInfo;
import org.apache.olingo.commons.api.edm.provider.CsdlEntitySet;
import org.apache.olingo.commons.api.edm.provider.CsdlEntityType;
import org.apache.olingo.commons.api.edm.provider.CsdlNavigationProperty;
import org.apache.olingo.commons.api.edm.provider.CsdlNavigationPropertyBinding;
import org.apache.olingo.commons.api.edm.provider.CsdlProperty;
import org.apache.olingo.commons.api.edm.provider.CsdlPropertyRef;
import org.apache.olingo.commons.api.edm.provider.CsdlSchema;
import org.apache.olingo.commons.api.edm.provider.CsdlComplexType;
import org.apache.olingo.commons.api.ex.ODataException;

public class DemoEdmProvider extends CsdlAbstractEdmProvider {

    // Service Namespace
    public static final String NAMESPACE = "OData.Demo";

    // EDM Container
    public static final String CONTAINER_NAME = "Container";
    public static final FullQualifiedName CONTAINER = new FullQualifiedName(NAMESPACE, CONTAINER_NAME);

    // Entity Types Names
    public static final String ET_OBSERVATION_NAME = "Observation";
    public static final FullQualifiedName ET_OBSERVATION_FQN = new FullQualifiedName(NAMESPACE, ET_OBSERVATION_NAME);

    public static final String ET_SENSOR_NAME = "Sensor";
    public static final FullQualifiedName ET_SENSOR_FQN = new FullQualifiedName(NAMESPACE, ET_SENSOR_NAME);

    public static final String CT_LOCATION_NAME = "Location";
    public static final FullQualifiedName CT_LOCATION_FQN = new FullQualifiedName(NAMESPACE, CT_LOCATION_NAME);

    public static final String CT_FOI_NAME = "FeatureOfInterest";
    public static final FullQualifiedName CT_FOI_FQN = new FullQualifiedName(NAMESPACE, CT_FOI_NAME);

    public static final String CT_OBSPROP_NAME = "ObservedProperty";
    public static final FullQualifiedName CT_OBSPROP_FQN = new FullQualifiedName(NAMESPACE, CT_OBSPROP_NAME);

    public static final String CT_OBSVAL_NAME = "ObservationValue";
    public static final FullQualifiedName CT_OBSVAL_FQN = new FullQualifiedName(NAMESPACE, CT_OBSVAL_NAME);

    public static final String CT_UOM_NAME = "UnitOfMeasurement";
    public static final FullQualifiedName CT_UOM_FQN = new FullQualifiedName(NAMESPACE, CT_UOM_NAME);

    // Entity Set Names
    public static final String ES_OBSERVATIONS_NAME = "Observations";
    public static final String ES_SENSORS_NAME = "Sensors";

    @Override
    public CsdlEntityType getEntityType(FullQualifiedName entityTypeName) {

        // this method is called for each EntityType that are configured in the Schema
        CsdlEntityType entityType = null;

        if (entityTypeName.equals(ET_OBSERVATION_FQN)) {
            // create EntityType properties
            CsdlProperty id = new CsdlProperty().setName("ID").setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());
            //CsdlProperty samplingTime = new CsdlProperty().setName("SamplingTime").setType(EdmPrimitiveTypeKind.DateDateTimeOffset.getFullQualifiedName());
            CsdlProperty resultTime = new CsdlProperty().setName("ResultTime").setType(EdmPrimitiveTypeKind.DateTimeOffset.getFullQualifiedName());
            CsdlProperty location = new CsdlProperty().setName("Location").setType(CT_LOCATION_FQN);
            //CsdlProperty foi = new CsdlProperty().setName("FeatureOfInterest").setType(CT_FOI_FQN);
            CsdlProperty foi = new CsdlProperty().setName("FeatureOfInterest").setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());
            //CsdlProperty prop = new CsdlProperty().setName("ObservedProperty").setType(CT_OBSPROP_FQN);
            CsdlProperty value = new CsdlProperty().setName("ObservationValue").setType(CT_OBSVAL_FQN);

            // create PropertyRef for Key element
            CsdlPropertyRef propertyRef = new CsdlPropertyRef();
            propertyRef.setName("ID");

            // navigation property: many-to-one, null not allowed (product must have a category)
            CsdlNavigationProperty navProp = new CsdlNavigationProperty().setName("Sensor")
                    .setType(ET_SENSOR_FQN).setNullable(false).setPartner("Observations");
            List<CsdlNavigationProperty> navPropList = new ArrayList<CsdlNavigationProperty>();
            navPropList.add(navProp);

            // configure EntityType
            entityType = new CsdlEntityType();
            entityType.setName(ET_OBSERVATION_NAME);
            //entityType.setProperties(Arrays.asList(id, samplingTime, resultTime, location, foi, prop, value));
            entityType.setProperties(Arrays.asList(id, resultTime, location, foi, value));
            entityType.setKey(Arrays.asList(propertyRef));
            entityType.setNavigationProperties(navPropList);

        } else if (entityTypeName.equals(ET_SENSOR_FQN)) {
            // create EntityType properties
            CsdlProperty id = new CsdlProperty().setName("ID").setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());
            CsdlProperty name = new CsdlProperty().setName("Name").setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());
            CsdlProperty prop = new CsdlProperty().setName("ObservationProperties").setType(EdmPrimitiveTypeKind.String.getFullQualifiedName()).setCollection(true);

            // create PropertyRef for Key element
            CsdlPropertyRef propertyRef = new CsdlPropertyRef();
            propertyRef.setName("ID");

            // navigation property: one-to-many
            CsdlNavigationProperty navProp = new CsdlNavigationProperty().setName("Observations")
                    .setType(ET_OBSERVATION_FQN).setCollection(true).setPartner("Sensor");
            List<CsdlNavigationProperty> navPropList = new ArrayList<CsdlNavigationProperty>();
            navPropList.add(navProp);

            // configure EntityType
            entityType = new CsdlEntityType();
            entityType.setName(ET_SENSOR_NAME);
            entityType.setProperties(Arrays.asList(id, name, prop));
            entityType.setKey(Arrays.asList(propertyRef));
            entityType.setNavigationProperties(navPropList);
//    } else if (entityTypeName.equals(ET_CAT_FQN)) {
//        CsdlProperty id = new CsdlProperty().setName("ID")
//          .setType(EdmPrimitiveTypeKind.Int32.getFullQualifiedName());
//        
//        entityType = new CsdlEntityType();
//      entityType.setName(ET_CAT_NAME);
//      entityType.setProperties(Arrays.asList(id));
        }

        return entityType;

    }

    @Override
    public CsdlComplexType getComplexType(final FullQualifiedName complexTypeName) throws ODataException {
        CsdlComplexType complexType = null;

        if (complexTypeName.equals(CT_LOCATION_FQN)) {
            //CsdlProperty name = new CsdlProperty().setName("Name").setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());
            //CsdlProperty description = new CsdlProperty().setName("Description").setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());
            //CsdlProperty longitude = new CsdlProperty().setName("Longitude").setType(EdmPrimitiveTypeKind.Double.getFullQualifiedName());
            //CsdlProperty latitude = new CsdlProperty().setName("Latitude").setType(EdmPrimitiveTypeKind.Double.getFullQualifiedName());
            //CsdlProperty altitude = new CsdlProperty().setName("Altitude").setType(EdmPrimitiveTypeKind.Double.getFullQualifiedName());
            CsdlProperty name = new CsdlProperty().setName("name").setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());
            CsdlProperty longitude = new CsdlProperty().setName("lon").setType(EdmPrimitiveTypeKind.Double.getFullQualifiedName());
            CsdlProperty latitude = new CsdlProperty().setName("lat").setType(EdmPrimitiveTypeKind.Double.getFullQualifiedName());
            CsdlProperty altitude = new CsdlProperty().setName("alt").setType(EdmPrimitiveTypeKind.Double.getFullQualifiedName());
            complexType = new CsdlComplexType();
            complexType.setName(CT_LOCATION_NAME);
            complexType.setProperties(Arrays.asList(name, longitude, latitude, altitude));
        } else if (complexTypeName.equals(CT_FOI_FQN)) {
            CsdlProperty name = new CsdlProperty().setName("Name").setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());
            complexType = new CsdlComplexType();
            complexType.setName(CT_FOI_NAME);
            complexType.setProperties(Arrays.asList(name));
        } else if (complexTypeName.equals(CT_OBSPROP_FQN)) {
            CsdlProperty name = new CsdlProperty().setName("Name").setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());
            complexType = new CsdlComplexType();
            complexType.setName(CT_OBSPROP_NAME);
            complexType.setProperties(Arrays.asList(name));
        } else if (complexTypeName.equals(CT_OBSVAL_FQN)) {
            CsdlProperty value = new CsdlProperty().setName("Value").setType(EdmPrimitiveTypeKind.Double.getFullQualifiedName());
            //CsdlProperty uom = new CsdlProperty().setName("UnitOfMeasurement").setType(CT_UOM_FQN);
            CsdlProperty uom = new CsdlProperty().setName("UnitOfMeasurement").setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());
            CsdlProperty obsProp = new CsdlProperty().setName("ObservedProperty").setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());
            complexType = new CsdlComplexType();
            complexType.setName(CT_OBSVAL_NAME);
            //complexType.setProperties(Arrays.asList(value, uom));
            complexType.setProperties(Arrays.asList(value, uom, obsProp));
        } else if (complexTypeName.equals(CT_UOM_FQN)) {
            CsdlProperty name = new CsdlProperty().setName("Name").setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());
            CsdlProperty symbol = new CsdlProperty().setName("Symbol").setType(EdmPrimitiveTypeKind.String.getFullQualifiedName());
            complexType = new CsdlComplexType();
            complexType.setName(CT_UOM_NAME);
            complexType.setProperties(Arrays.asList(name, symbol));
        }

//    if (CT_CAT_FQN.equals(complexTypeName)) {
//      return new CsdlComplexType().setName(CT_CAT_FQN.getName()).setProperties(Arrays.asList(
//          new CsdlProperty().setName("Id").setType(EdmPrimitiveTypeKind.Int32.getFullQualifiedName()),
//          new CsdlProperty().setName("Text").setType(EdmPrimitiveTypeKind.String.getFullQualifiedName())
//          ));
//    }
        return complexType;
    }

    @Override
    public CsdlEntitySet getEntitySet(FullQualifiedName entityContainer, String entitySetName) {

        CsdlEntitySet entitySet = null;

        if (entityContainer.equals(CONTAINER)) {

            if (entitySetName.equals(ES_OBSERVATIONS_NAME)) {

                entitySet = new CsdlEntitySet();
                entitySet.setName(ES_OBSERVATIONS_NAME);
                entitySet.setType(ET_OBSERVATION_FQN);

                // navigation
                CsdlNavigationPropertyBinding navPropBinding = new CsdlNavigationPropertyBinding();
                navPropBinding.setTarget("Sensors"); // the target entity set, where the navigation property points to
                navPropBinding.setPath("Sensor"); // the path from entity type to navigation property
                List<CsdlNavigationPropertyBinding> navPropBindingList = new ArrayList<CsdlNavigationPropertyBinding>();
                navPropBindingList.add(navPropBinding);
                entitySet.setNavigationPropertyBindings(navPropBindingList);

            } else if (entitySetName.equals(ES_SENSORS_NAME)) {

                entitySet = new CsdlEntitySet();
                entitySet.setName(ES_SENSORS_NAME);
                entitySet.setType(ET_SENSOR_FQN);

                // navigation
                CsdlNavigationPropertyBinding navPropBinding = new CsdlNavigationPropertyBinding();
                navPropBinding.setTarget("Observations"); // the target entity set, where the navigation property points to
                navPropBinding.setPath("Observation"); // the path from entity type to navigation property
                List<CsdlNavigationPropertyBinding> navPropBindingList = new ArrayList<CsdlNavigationPropertyBinding>();
                navPropBindingList.add(navPropBinding);
                entitySet.setNavigationPropertyBindings(navPropBindingList);
            }
        }

        return entitySet;
    }

    @Override
    public CsdlEntityContainerInfo getEntityContainerInfo(FullQualifiedName entityContainerName) {

        // This method is invoked when displaying the service document at
        // e.g. http://localhost:8080/DemoService/DemoService.svc
        if (entityContainerName == null || entityContainerName.equals(CONTAINER)) {
            CsdlEntityContainerInfo entityContainerInfo = new CsdlEntityContainerInfo();
            entityContainerInfo.setContainerName(CONTAINER);
            return entityContainerInfo;
        }

        return null;
    }

    @Override
    public List<CsdlSchema> getSchemas() throws ODataException {
        // create Schema
        CsdlSchema schema = new CsdlSchema();
        schema.setNamespace(NAMESPACE);

        // add EntityTypes
        List<CsdlEntityType> entityTypes = new ArrayList<CsdlEntityType>();
        entityTypes.add(getEntityType(ET_OBSERVATION_FQN));
        entityTypes.add(getEntityType(ET_SENSOR_FQN));
        //entityTypes.add(getEntityType(ET_CAT_FQN));
        schema.setEntityTypes(entityTypes);

        List<CsdlComplexType> complexTypes = new ArrayList<CsdlComplexType>();
        complexTypes.add(getComplexType(CT_LOCATION_FQN));
        complexTypes.add(getComplexType(CT_FOI_FQN));
        complexTypes.add(getComplexType(CT_OBSPROP_FQN));
        complexTypes.add(getComplexType(CT_OBSVAL_FQN));
        complexTypes.add(getComplexType(CT_UOM_FQN));
        schema.setComplexTypes(complexTypes);

        // add EntityContainer
        schema.setEntityContainer(getEntityContainer());

        // finally
        List<CsdlSchema> schemas = new ArrayList<CsdlSchema>();
        schemas.add(schema);

        return schemas;
    }

    @Override
    public CsdlEntityContainer getEntityContainer() {

        // create EntitySets
        List<CsdlEntitySet> entitySets = new ArrayList<CsdlEntitySet>();
        entitySets.add(getEntitySet(CONTAINER, ES_OBSERVATIONS_NAME));
        entitySets.add(getEntitySet(CONTAINER, ES_SENSORS_NAME));

        // create EntityContainer
        CsdlEntityContainer entityContainer = new CsdlEntityContainer();
        entityContainer.setName(CONTAINER_NAME);
        entityContainer.setEntitySets(entitySets);

        return entityContainer;
    }
}
