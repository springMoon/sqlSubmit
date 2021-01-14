#!/bin/bash

export FLINK_HOME=/opt/flink-1.12.0
export PATH=$PATH:$FLINK_HOME/bin
export HADOOP_CLASSPATH=`hadoop classpath`

current_path=$(cd "$(dirname $0)";pwd)
logFile=$current_path/start.log
write_log(){
  date_str=`date -d now +"$F %T"`
  if [ -n "$1" ];then
     message="$1"
  else
     message="no input"
  fi
  echo "[ $date_str ] $@" | tee -a $logFile
}

#set -x
write_log "start : $current_path"

write_log "start parameter : $@"

## start job
flink run -m yarn-cluster -ynm $2 -c com.rookie.submit.main.SqlSubmit original-sqlSubmit-0.1.jar $@

