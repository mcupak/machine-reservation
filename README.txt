Machine Reservation

Description of the project
This is a web reservation system for machines in a computer lab. The application offers complete reservation, machine, machine category and user management functionality.

Getting the source code
This project is hosted on GitHub, you can obtain the sources by running the following command:
git clone https://github.com/mcupak/machine-reservation.git

Building the project from sources
This is a Maven-based project, run the following commmand to build it:
mvn install

Deploying the project
The project was tested on JBoss AS 7.1.1.Final and JBoss AS 7.1.0.Final. To deploy the project, build it and copy the file $PROJECT_HOME/target/machine-reservation-jbas71.war to $JBOSS_HOME/standalone/deployments/. The application will be available at http://[hostname]:8080/machine-reservation.

Authors
Papoušek Jan, Kišš Oliver, Hatlapatka Radim, Cupák Miroslav
