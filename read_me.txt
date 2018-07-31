To run this project follow these steps.

1. Set an environment system variable named as "root_path_server" to the location of the project folder.
for example if project folder is in Desktop, then set it till Desktop.

2. The project requires jar files. java-json.jar and json-simple-1.1.jar which are given in the folder
"jar files" present in project folder "mLifeUploads". Set environment system variable "classpath" to the folder "jar files".

for example somepath/mLifeUploads/jar files/*;

3. Project is developed in Java and uses Curl for data transfer. 

4. Compile and run the file "PostsUpload.java".


There are two files named "QuartzTrigger.java" and "QuartzJob.java" which can be used to run a Cron Scheduler
that runs "PostsUpload.java" every 15 minutes. 

To run this cron scheduler, the required jar files are given in folder "jar files".
Compile and run the file "QuartzTrigger.java" to run the scheduler. We need to run the file once again  in case server restarts.

If program terminates while running and before it could write the postIds in the file postIds.txt then it will add duplicates else it will omit those posts.



