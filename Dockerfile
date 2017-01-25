FROM njmittet/alpine-wildfly:10.0.0.Final
COPY target/bm-rest-api.war /opt/jboss/wildfly/standalone/deployments/bm-rest-api.war

#
# Important note: If volumes are blocked by a firewall, a new
# container must be initialized when an update has been made
#
# For servers: To add a volume:
# docker run -d -p 80:8080 -v <path to /target>/target/bm-rest-api.war:/opt/jboss/wildfly/standalone/deployments/bm-rest-api.war --name <name of container> <image>
#