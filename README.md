# ResourceAccessProxy
This project is part of the Interworking API for SymbIoTe H2020 project. It exposes data from symbIoTe-compliant platform in accordance with the symbIoTe Information Model

## Background
The RAP component is used by Application and/or Enabler to gather data from a symbIoTe-specific platform. The interface is implemented according to OData standard (version 4) and uses the symbIoTe Information Model to represent semantics of data. The OData standard is implemented using the Apache Olingo project which is Java library for OData.  

## Interface to a platform
The RAP component to be able to expose data from a platform needs to have a platform-specific data provider which will implement all necessary method used by the rest of the RAP component. The Data Driver Interface (in the eu.h2020.symbiote.rap.data package) exposes two methods that need to be implemented:
-	`List<Observation> getObservations(String sensorId)`: Gets data from a platform storage that is created by the sensorId resource. To get data from all sensors, the sensorId is set to null.   
- `List<Sensor> getSensors(String sensorId)`: Returns list of sensors with their details that are exposed through the RAP component. If the sensorId value is set the method returns only the corresponding sensor description.

Return values of the Data Driver Interface are POJOs object available in the eu.h2020.symbiote.rap.data package.

Dummy implementation of the Data Driver Interface is given in the DemoImpl class which implements both methods and gives examples how to create appropriate POJOs.

An implemented provider needs to be registered in the Demo Servlet (in the eu.h2020.symbiote.rap.web package), through providing it as the parameter of the Storage constructor.

## Interface to symbIoTe Application/Enabler
Interface to Applications and/or Enabler of the symbIoTe ecosystem is defined by the OData standard and symbIoTe core model. The RAP component exposes an interface that returns a collection of objects (Observations or Sensor information), single object or single property of an object. Also support for the top, skip and count operators is provided. All of them can be tested by using following URLs:
-	`http://localhost:8080/rap/Sensors`: Returns all available sensors
-	`http://localhost:8080/rap/Observations`: Returns all observations
-	`http://localhost:8080/rap/Observations('1')`: Returns observation with ID=1
-	`http://localhost:8080/rap/Sensors('Id1')`: Returns sensor with ID=Id1
-	`http://localhost:8080/rap/Sensors('Id1')/Observations`: Returns all observations made by the sensor “Id1”
-	`http://localhost:8080/rap/Sensors('Id1')/Observations`: Returns all observations made by the sensor “Id1”
-	`http://localhost:8080/rap/Sensors('Id1')/Observations?$count=true`: Returns all observations made by the sensor “Id1” and their count
-	`http://localhost:8080/rap/Sensors('Id1')/Observations?$top=1`: Returns first observation from the collection of observations made by the sensor “Id1”
-	`http://localhost:8080/rap/Sensors('Id1')/Observations?$skip=1`: Returns all observations made by the sensor “Id1” except first one which is skipped

## Packaging and running
The component is packaged using Gradle, so it should be installed in the machine that wants to create a war. Instructions to do so are at https://gradle.org/gradle-download/.

Once Gradle is installed and running, a war ready to be deployed on an application server is created by executing the following command: `gradle war`. 

The war file will be created in the build/libs subfolder.

