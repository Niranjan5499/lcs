# How to test the code?

#### Navigate to your project directory
` cd ~/lcs `

#### Build the project with Maven
` mvn clean install `

#### Run the application in the background
` mvn spring-boot:run `

#### Wait for the server to start

#### Navigate to the directory where lcs_test.sh is located

#### Make the script executable
` chmod +x lcs_test.sh `

#### Run the test script
` ./lcs_test.sh `

#### Sample request:
` { "setOfStrings": [ {"value": "comcast"}, {"value": "comcastic"}, {"value": "broadcaster"} ] } `
