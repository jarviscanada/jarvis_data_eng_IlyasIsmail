# Retrieve variables, check if amount of variables inputted is valid
psql_host=$1
psql_port=$2
db_name=$3
psql_user=$4
psql_password=$5

if [ "$#" -ne 5 ]; then
    echo "Illegal number of parameters"
    exit 1
fi

vmstat_mb=$(vmstat --unit M)
hostname=$(hostname -f)

# Retrieve hardware specification variables
memory_free=$(echo "$vmstat_mb" | tail -1 | awk -v col="4" '{print $col}')
cpu_idle=$(echo "$vmstat_mb" | tail -1 | awk -v col="15" '{print $col}')
cpu_kernel=$(echo "$vmstat_mb"| tail -1 | awk -v col="14" '{print $col}')
disk_io=$(vmstat --unit M -d | tail -1 | awk -v col="10" '{print $col}')
disk_available=$(df -m /root | tail -1 | awk -v col="4" '{print $col}')

# Current time in `2019-11-26 14:40:19` UTC format
timestamp=$(date '+%Y-%m-%d %H:%M:%S')

# find ID based on hostname
host_id="(SELECT id FROM host_info WHERE hostname='$hostname')";

#Insert usage data into host_usage table
insert_stmt="INSERT INTO host_usage(timestamp,host_id,memory_free,cpu_idle,cpu_kernel,disk_io,disk_available) VALUES('$timestamp',$host_id,'$memory_free','$cpu_idle','$cpu_kernel','$disk_io','$disk_available')"
export PGPASSWORD=$psql_password
psql -h $psql_host -p $psql_port -d $db_name -U $psql_user -c "$insert_stmt"
exit $?