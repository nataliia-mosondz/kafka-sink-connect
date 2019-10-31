#[[
#!/usr/bin/env bash

export JAVA_HOME=$(/usr/libexec/java_home -v 1.8)

: ${SUSPEND:='n'}

set -e

mvn clean package
export KAFKA_JMX_OPTS="-Xdebug -agentlib:jdwp=transport=dt_socket,server=y,suspend=${SUSPEND},address=5005"

export CLASSPATH="$(find arget/kafka-connect-target/share/java -type f -name '*.jar' | tr '\n' ':')"

kafka_2.12-2.3.0/bin/connect-standalone.sh config/connect-standalone.properties config/MySinkConnector.properties
]]#