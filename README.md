taskswitching
=================

Web application for running task switching experiments with participants. Requires
a Java compiant server for running (e.g. Apache Tomcat). 

*Configuration*

To use production mode (incl a real database, MySQL assumed), modify datasource 
configuration at "src/main/webapp/WEB-INF/spring/database.xml" to match your own 
database configuration. In addition, set the production server environment
variable "SPRING_PROFILES_ACTIVE" to production;

export SPRING_PROFILES_ACTIVE=production

Now when the application starts, it knows to utilize the production db. Otherwise,
you'll have an in-memory database, which is good for development, but bad for 
production as it will lose its data when the server is restarted or the application
is redeployed.

*Packaging*

To package the source code to a runnable archive (war), you need Apache Maven, 
which takes care of the project dependencies and other everyday stuff. Once you 
have maven on your development machine, run the command

mvn clean package

in the root project folder that also contains the pom.xml. After running, you'll 
have the new war-file in the newly created target-folder.

*Deployment*

Copy the created war-file to the server. For example in tomcat, you need to copy
the war-file to the webapps-folder of tomcat. The application will start under
the path that corresponds to the name of the war-file.

*Have fun*