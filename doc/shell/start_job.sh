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
## start flink session
if [ -z "$2" ];then
   echo "please input session name"
   exit -1
fi
session_name="$2"

session_status="`yarn application -list | grep "$session_name" | awk -F " " '{ print $1}' | awk 'NR==1'`"
if [ -z "$session_status" ];then
   write_log "$session_name is not running, start it"
   start_session.sh $session_name
fi

## start job
flink run -yd -yid $session_status -c com.rookie.submit.main.SqlSubmit original-sqlSubmit-0.1.jar $@
#write_log "start session $session_name"

