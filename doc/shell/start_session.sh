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
  fi
  echo "[ $date_str ] $message" | tee -a $logFile
}
echo "$current_path"  | tee -a $logFile

#write_log "current : $current_path"

## start flink session
if [ -z "$1" ];then
   echo "please input session name"
   exit -1
fi
session_name="$1"

session_status="`yarn application -list | grep "$session_name" | awk -F " " '{ print $1}' | awk 'NR==1'`"
if [ -n "$session_status" ];then
   write_log "$session_name is alread running"
   read -r -t 30 -p "Are You Sure? [Y/n] " input
   case $input in
       [yY][eE][sS]|[yY])
   		echo "Yes"
                write_log "stop session $session_status"
                yarn application -kill $session_status
   		;;
   
       [nN][oO]|[nN])
   		echo "No"
                exit 1
          	;;
   esac
fi

## start session
yarn-session.sh -d -nm "$session_name" -s 4
write_log "start session $session_name"

