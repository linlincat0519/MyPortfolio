#!/bin/bash
#PBS -N rclone
#PBS -l Select=1:ncpus=1
#PBS -l place=pack
#PBS -q ngs4G
#PBS -o log/
#PBS -e log/

##section 2:description: 
#= version: 2.0.0, date: 20210119, modifier: hsujc
#= version: 2.0.1, date: 20210120, modifier: Lincat
#= version: 2.0.3, date: 20210719, modifier: Lincat
#=新增功能:
#=新增確認資料夾檔案功能並輸出成uploadfilelist.txt
#= Document : 
#= demo code : bash modules/projectupload.2.X.Y.sh -p $(pwd) -g "google_d49202006:TeamSharing/Case/Y210115_02_scRNA_YangLab_Mouse" 
#= demo code : qsub -v 'projDir='$(pwd)'/,'goolDir="google_d49202006:/TeamSharing/Case/Y210115_02_scRNA_YangLab_Mouse"' modules/projectupload.2.X.Y.sh
#= demo code  : qsub -m e -M useremail@gmail.com -v 'projDir='$(pwd)'/,goolDir="google_d49202006:TeamSharing/Case/Y210115_02_scRNA_YangLab_Mouse"' modules/projectupload.2.X.Y.sh 

##  pre-request
## [projDir] project
##   +-- [rawDir] raw
##   +-- [prcDir] processed 
##   +-- [anaDir] analyzed
##   +-- [rptDir] report
##   +-- [tmpDir] temp
##   +-- [logDir] log
##   +-- [qcDir] QC
while getopts "p:g:" argv
do
case $argv in
 p) projDir=$OPTARG
  ;;
 g) goolDir=$OPTARG
  ;;
 *) echo -e "******Module description******"
    echo -e "Detect unrecognized argument"
    echo -e "-p \$projDirSet absolut path of project folder. You can get it by using \$(pwd) in runtime"
    echo -e "-g \$goolDirSet  of output google drive file"    
    echo -e "******Demo code******"
    echo -e "===Run on linux consol:===\n"
    echo -e 'bash modules/projectupload.2.X.Y.sh -p $(pwd) -g "google_d49202006:TeamSharing/Case/Y210115_02_scRNA_YangLab_Mouse"'
    echo -e "\n===Run onStandard PBS Pro:===\n"
    echo -e "qsub -v 'projDir='$(pwd)'/,'goolDir='google_d49202006:/TeamSharing/Case/Y210115_02_scRNA_YangLab_Mouse' modules/projectupload.2.X.Y.sh "
    echo -e "\n====Run on Taiwania I (please get Project ID fromServiced provider)===\n"
    echo -e "qsub -P projectID -W group_list=projectID -m e -M user@gmail.com \
-v 'projDir='$(pwd)'/,'goolDir='google_d49202006:/TeamSharing/Case/Y210115_02_scRNA_YangLab_Mouse' modules/projectupload.2.X.Y.sh "
    exit -1
  ;;    
esac
done
if [ ! $projDir ]; then
  echo -e 'set project folder'
  exit -1
fi
if [ ! $goolDir ]; then
  echo -e 'set google project folder'
  echo -e 'ex: google_d49202006:TeamSharing/Case/Y210115_02_scRNA_YangLab_Mouse'
  exit -1
fi
start_time_all=`date +%s`
source ~/miniconda3/etc/profile.d/conda.sh
conda activate GApp
ls processed/* -al >>log/uploadfilelist.txt
cd ${projDir}
#Step1:push data to goolDir /processed
echo ${projDir}
echo ${goolDir}
echo "projDir:"${projDir} >> log/log.txt
echo "goolDir:"${goolDir} >> log/log.txt
echo -e ${projDir}'processed to '${goolDir}'/processed\tStart' >> log/Summary.log
start_time=`date +%s`
echo -e 'start_time processed:'$(date) >> log/log.txt

rclone sync -vv --transfers=10 --fast-list ./processed ${goolDir}/processed

if [ $? -eq 0 ]; then
  echo -e ${projDir}'processed to '${goolDir}'/processed\tDone' >> log/Summary.log
        else
            echo -e ${projDir}'processed to '${goolDir}'/processed\tStop' >> log/Summary.log
            exit -1
fi

end_time=`date +%s`
echo -e 'end_time processed:'$(date) >> log/log.txt
time_elapsed=$((($end_time-$start_time)/60))
echo 'time_elapsed processed:' $time_elapsed 'mins'>> log/log.txt

#Step2:push data to goolDir /report
ls report/* -al >>log/uploadfilelist.txt
start_time=`date +%s`
echo -e 'start_time report:' $(date) >> log/log.txt
echo -e ${projDir}'report to '${goolDir}'/report\tStart' >> log/Summary.log
rclone sync -vv --transfers=10 --fast-list ./report ${goolDir}/report
if [ $? -eq 0 ]; then
  echo -e ${projDir}'report to '${goolDir}'/report\tDone' >> log/Summary.log
        else
            echo -e ${projDir}'report to '${goolDir}'/report\tStop' >> log/Summary.log
            exit -1
fi

end_time=`date +%s`
echo -e 'end_time report:' $(date) >> log/log.txt
time_elapsed=$((($end_time-$start_time)/60))
echo 'time_elapsed report:' $time_elapsed >> log/log.txt

#Step3:push data to goolDir /raw
ls raw/* -al >>log/uploadfilelist.txt
echo -e ${projDir}'raw to '${goolDir}'/raw\tStart' >> log/Summary.log
start_time=`date +%s`
echo -e 'start_time raw:' $(date) >> log/log.txt
rclone sync -vv --transfers=10 --fast-list ./raw ${goolDir}/raw

if [ $? -eq 0 ]; then
        echo -e ${projDir}'raw to '${goolDir}'/raw\tDone' >> log/Summary.log
        else
            echo -e ${projDir}'raw to '${goolDir}'/raw\tStop' >> log/Summary.log
            exit -1
fi
end_time=`date +%s`
echo -e 'end_time raw:'$(date) >> log/log.txt
time_elapsed=$((($end_time-$start_time)/60))
echo 'time_elapsed raw:' $time_elapsed 'mins'>> log/log.txt

#Step4:push data to goolDir /QC
ls QC/* -al >>log/uploadfilelist.txt
start_time=`date +%s`
echo -e 'start_time QC:' $(date) >> log/log.txt
echo -e ${projDir}'QC to '${goolDir}'/QC\tStart' >> log/Summary.log
rclone sync -vv --transfers=10 --fast-list ./QC ${goolDir}/QC

if [ $? -eq 0 ]; then
        echo -e ${projDir}'QC to '${goolDir}'/QC\tDone' >> log/Summary.log
        else
            echo -e ${projDir}'QC to '${goolDir}'/QC\tStop' >> log/Summary.log
            exit -1
fi
end_time=`date +%s`
echo -e 'end_time QC:' $(date) >> log/log.txt
time_elapsed=$((($end_time-$start_time)/60))
echo 'time_elapsed QC:' $time_elapsed >> log/log.txt
#Step5:push data to goolDir /temp
ls temp/* -al >>log/uploadfilelist.txt
start_time=`date +%s`
echo -e 'start_time temp:' $(date) >> log/log.txt
echo -e ${projDir}'temp to '${goolDir}'/temp\tStart' >> log/Summary.log
rclone sync -vv --transfers=10 --fast-list ./temp ${goolDir}/temp

if [ $? -eq 0 ]; then
        echo -e ${projDir}'temp to '${goolDir}'/temp\tDone' >> log/Summary.log
        else
            echo -e ${projDir}'temp to '${goolDir}'/temp\tStop' >> log/Summary.log
            exit -1
fi
end_time=`date +%s`
echo -e 'end_time temp:' $(date) >> log/log.txt
time_elapsed=$((($end_time-$start_time)/60))
echo 'time_elapsed temp:' $time_elapsed >> log/log.txt

#Step6:push data to goolDir /analyzed
ls analyzed/* -al >>log/uploadfilelist.txt
start_time=`date +%s`
echo -e 'start_time analyzed:' $(date) >> log/log.txt
echo -e ${projDir}'analyzed to '${goolDir}'/analyzed\tStart' >> log/Summary.log
rclone sync -vv --transfers=10 --fast-list ./analyzed ${goolDir}/analyzed
if [ $? -eq 0 ]; then
        echo -e ${projDir}'analyzed to '${goolDir}'/analyzed\tDone' >> log/Summary.log
        else
            echo -e ${projDir}'analyzed to '${goolDir}'/analyzed\tStop' >> log/Summary.log
            exit -1
fi
end_time=`date +%s`
echo -e 'end_time analyzed:' $(date) >> log/log.txt
time_elapsed=$((($end_time-$start_time)/60))
echo 'time_elapsed analyzed:' $time_elapsed >> log/log.txt

#Step7:push data to goolDir /log
ls log/* -al >>log/uploadfilelist.txt
start_time=`date +%s`
echo -e 'start_time log:' $(date) >> log/log.txt
echo -e ${projDir}'log to '${goolDir}'/log\tStart' >> log/Summary.log
rclone sync -vv --transfers=10 --fast-list ./log ${goolDir}/log
if [ $? -eq 0 ]; then
        echo -e ${projDir}'log to '${goolDir}'/log\tDone' >> log/Summary.log
        else
            echo -e ${projDir}'log to '${goolDir}'/log\tStop' >> log/Summary.log
            exit -1
fi
end_time=`date +%s`
echo -e 'end_time log:' $(date) >> log/log.txt
time_elapsed=$((($end_time-$start_time)/60))
echo 'time_elapsed log:' $time_elapsed >> log/log.txt

end_time_all=`date +%s`

#push Done
time_elapsed_all=$((($end_time_all-$start_time_all)/60))
echo -e "rclone sync took ${time_elapsed_all} minutes.">>log/log.txt
rclone sync -vv --transfers=10 --fast-list ./log ${goolDir}/log