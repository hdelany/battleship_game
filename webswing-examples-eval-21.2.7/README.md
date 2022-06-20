### Project Deployment Information

This folder contains files used when deploying the project with [Webswing](https://www.webswing.org/).

These files are compatible for a Windows remote server.

This project is currently being hosted on [Oracle Cloud Infrastructure](https://www.oracle.com/cloud/), with a Windows Server 2019 Standard Image and VM.Standard.A1.Flex shape.

### Notes/ Instructions

###### 1. Export project to runnable jar file.

- When java project is finished, export the project as a runnable jar file.
- Check that it works by clicking on the file or running in in the command line `java -jar battleship.jar`.

###### 2. Run project with webswing.

- [Webswing](https://www.webswing.org/) is a web server that allows you to run any Java Swing application inside your web browser, using only pure HTML5.
- Before deploying your project on a remote server with webswing, I reccomend running your project with webswing on your local machine to make sure it runs correctly.
- It has been noted that Java Swing can have rendering differences (and issues) on different OS. In this project specifically, battleship.jar rendered perfectly on Mac OS and Windows, but not on Ubuntu Linux.
- For Windows, I recommend using webswing version 21.2.7, for other OS, I recommend using the latest version.
- [Video Tutorial](https://www.youtube.com/watch?v=DsjJn8ueGqQ)
- [Webswing Guide](https://www.webswing.org/docs/22.1/start)

###### 3. Create an Oracle Cloud Infrastructure account and create and instance

- I chose to use a Windows Server for performance and rendering, however Oracle has other options for an always free website.
- Add ingress rule to allow HTTP connections for destination port 80
- (Windows) Add ingress rule for destination port 3389
- [Video Tutorial](https://www.youtube.com/watch?v=yWVD6qmQrb8&t=62s)
- [Tutorial](https://tonyteaches.tech/oracle-always-free-website-tutorial/)

###### 4A (Linux) SSH into the Virtual Machine

- Navigate to local folder with ssh key file, then type into command line:
  `ssh -i ssh_key_file_name.key username@Public_IP_address`

###### 4B (Windows) Log into Virtual Machine

- Windows: Download [Microsoft Remote Desktop](https://apps.apple.com/us/app/microsoft-remote-desktop-10/id1295203466?mt=12)
- Add computer to Microsoft Remote Desktop and login using OCI instance information
- [Oracle Guide](https://docs.oracle.com/en-us/iaas/Content/Compute/Tasks/accessinginstance.htm)

###### 5A (Linux) Transfer files to Virtual Machine

- Use scp (secure copy protocol) to transfer webswing.zip and project files to your VM:
  `scp -i ssh_key_file_name -r path_of_folder_to_copy username@Public_IP_address`
  `scp -i ssh_key_file_name path_of_file_to_copy username@Public_IP_address`

###### 5B (Windows) Transfer files to Virtual Machine

- You can copy/paste between local machine and remote desktop

###### 6A (Linux) Add required packages to Virtual Machine

- [Recommended Step-by-step Youtube Tutorial](https://www.youtube.com/watch?v=YFNc5jwdaJA&list=PLrmIS7zMAffte2_tnxoBw1fMQ6mjC1g1v&index=139&t=432s_)
- Update VM:
  `sudo apt update`
  `sudo apt upgrade`
- Install Java 17 (only specific java versions supported by webswing):
  `sudo apt install -y openjdk-17-jdk`
- Install required packages:
  `sudo apt install xvfb libxi6 libxtst6 libxrender1`
- Set up iptables
  `sudo iptables -I INPUT 1 -p tcp --dport 8080 -j ACCEPT`
- Add fonts:
  `sudo apt install ttf-mscorefonts-installer`

###### 6B (Windows) Add required packages to Virtual Machine

- Download file for [Java 17 for Windows](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) (only specific java versions supported by webswing), copy and paste file into VM, then click on file to install
- Go to Start --> Search Menu. Open Powershell and/or Command Shell (run as Administrator)
- Navigate to unzipped webswing folder then type in:
  `java -jar webswing-server.war -j jetty.properties`
- [Webswing Guide](https://www.webswing.org/docs/20.1/start/install.html#starting-on-windows)
- [Enable Websocket](https://docs.microsoft.com/en-us/iis/configuration/system.webserver/websocket)

###### 7A (Linux) Update Webswing config files with Vim

- **webswing.config**: change password for swing login, change localhost to Public IP address of VM
- **admin/webswing-admin.properties**: change localhost to Public IP address of VM

###### 7B (Windows) Update Webswing config files with text editor

- **webswing.config**: change password for swing login, change localhost to Public IP address of VM
- **admin/webswing-admin.properties**: change localhost to Public IP address of VM
- **webswing.config**: I was unable to use management console when running webswing on Windows. To work around this, manually edit battleship project details in **webswing.config** with text editor. Example can be found in this repository.
- **jetty.properties** and **admin/jetty.properties**: change host = 0.0.0.0

###### 8A (Linux) Start Webswing Server

- `sudo chmod +x websing+admin.sh`
- `sudo ./websing+admin.sh start`

###### 8B (Windows) Start Webswing Server

- `java -jar webswing-server.war -j jetty.properties`
- **webswing.bat**
- **webswing.sh**
