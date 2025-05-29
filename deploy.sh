#!/bin/bash

# Application name
APP_NAME="hiring"
JAR_FILE="hiring-0.0.1-SNAPSHOT.jar"
JAVA_OPTS="-Xmx512m -Xms256m"

# Function to start the application
start() {
    echo "Starting $APP_NAME..."
    nohup java $JAVA_OPTS -jar $JAR_FILE > app.log 2>&1 &
    echo $! > app.pid
    echo "$APP_NAME started with PID $(cat app.pid)"
}

# Function to stop the application
stop() {
    if [ -f app.pid ]; then
        echo "Stopping $APP_NAME..."
        kill $(cat app.pid)
        rm app.pid
        echo "$APP_NAME stopped"
    else
        echo "$APP_NAME is not running"
    fi
}

# Function to check status
status() {
    if [ -f app.pid ]; then
        PID=$(cat app.pid)
        if ps -p $PID > /dev/null; then
            echo "$APP_NAME is running with PID $PID"
        else
            echo "$APP_NAME is not running"
            rm app.pid
        fi
    else
        echo "$APP_NAME is not running"
    fi
}

# Function to restart the application
restart() {
    stop
    sleep 2
    start
}

# Main script logic
case "$1" in
    start)
        start
        ;;
    stop)
        stop
        ;;
    restart)
        restart
        ;;
    status)
        status
        ;;
    *)
        echo "Usage: $0 {start|stop|restart|status}"
        exit 1
        ;;
esac

exit 0 