**Setup staging server:**

	Crontab-script:

	sudo crontab -e 
	note: crontab is user specific, remember to switch to the right user.

	- 0 * * * * /home/cityzer/fetch-data.sh >/dev/null 2>&1
	Runs fetch-data.sh every hour

	Other:
 
	Install Apache2 and Tomcat on server.
	Make sure they are running and that they STAY running.

	It is recommended to raise the default ulimit (limit on open files):
		http://posidev.com/blog/2009/06/04/set-ulimit-parameters-on-ubuntu/
		

**Setup development environment:**

	Install IntelliJ IDEA and GitBash:
	
	 - https://www.jetbrains.com/idea/
	 - https://git-scm.com/downloads
	
	Import project from git:
		
 	- Choose filepath in GitBash
	 - git clone https://github.com/tpolvinen/cityzer.git
	
	In IntelliJ, check if "Import Maven projects automatically" is active.
	
	 - File > Settings > Build, Execution, Deployment > Build Tools > Maven > Importing
	
	If there are errors, click your imports and choose "add to classpath"

**Testing:**

	Run the shellscript manually.
	Send request to endpoint
	http://localhost:8080/api/getWeather?userLat={double}&userLon={double}
	
