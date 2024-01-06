# Test Automation Framework - UI & API
- Sample complete test automation framework for UI and API end 2 end testing

`This sample Software Test Automation Framework is built as a Maven project using Behavior Driven Development principles with Cucumber and Junit in Java.
and also utilized Page Object Module structure to maximize framework reusability and maitainability. 
And the scenario is written in Gherkin language which is a plain English, that could benefit connecting the team.`

- And framework is also structured to support API testing using RestAssured with Database integration capabilities with JDBC.


## Project structure:

src/test/java
 - api_tests - (API tests written using RestAssured library in TestNG test structure).
 - pages - (Page objects are created within this folder)
 - runners - (One or more cucumber runner classes are within this folder)
 - step_definitions - (Test steps implementation for the scenario steps defined in feature files)
 - utilities - (Any utilities and support classes created within this folder)
src/test/resources
 - features - (Cucumber feature files are created within this folder to define scenarios for the feature)
 - testdata - (Test data / properties files are stored within this folder)
 - testfiles - (Test json or any dummy files are stored here )
pom.xml - (Project configurations and content/libraries/dependencies management and build/run configurations)
testng.xml - (TestNG test suite configuration)

## Tools used
  UI - Tools used for UI test automation:
  
- Maven - (Project configurations and content/libraries/dependencies management and build/run configurations)
- Cucumber - (Used to define feature scenarios in gherkin, and also to create test suites and execution flow with tagging and reporting)
- Selenium - (Used to automate the web application by implementing the step definitions, managing the page objects)
- WebDriverManager - (Used to manage the browser driver binary (executable file (chromedriver.exe)) and auto downloading the browser drivers)
- Junit - (Used to run the cucumber scenarios with cucumber options)
- JDBC - (Used to connect to database for testing activities (get info from database and verify in the UI))
- MySQL Driver - (Used to allow connection to MySQL database that the application uses) 

  API - Tools used for API test automation
 
- TestNG - (Used to manage API test suites, test execution flow and assertion)
- RestAssured - (Used to define API tests)
- JDBC - (Used to connect to database for testing activities (get info from database and verify in the UI))
- MySQL Driver - (Used to allow connection to MySQL database that the application uses) 

  Other tools used for end 2 end activities

- Git - (Source code management / version control)
- Github - (Remote source code management / version control platform)
- Jenkins - (CI/CD tool which we have used to run the test suites) 
- Eclipse - (IDE for project development)


## Creating tests

 Creating UI Tests: [Cucumber Docs] ()
 
 1. Create a feature file in `features`folder under `src/test/resource` folder with extension .feature
 2. Define scenario with cucumber keywords Given, When, and Then structure
 3. Generate step definition snippets (use `dryrun`)
 4. Create a steps class under `step_definition` folder and put the generated steps snippets
 5. Create page classes under `pages` folder and define the page objects (elements)
 6. Implement the step definitions based on the scenario behaviors
 
 Running the UI test
 
 1. Create a runner class under `runners` folder
 2. Define the RunWith cucumber options with necessary options (features, plugins, glue, tags)
 3. Pass a scenario tag to execute tests using cucumber tags, and run as Junit test
 4. To run the tests remotely via a tool CI/CD tool like Jenkins, create a build in pom.xml 
 and point to the runner class in build configuration (refer to existing pom.xml for examples).
 5. On Jenkins job under build workflow, select top level maven target and pass the following command: 
 ```
 `clean test -Dcucumber.filter.tags="@smoketest"`
 ```
 
 
 Creating API tests: [Rest Assured Docs](https://rest-assured.io/)
 
 1. Create a class in `api_tests` under `src/tests/java` folder 
 2. Create a TestNG test and define the API test flow with RestAssured 
 
 Running the API test
 
 1. Run the particular API test class as a TestNG test
 2. To run the API tests remotely via a CI/CD tool like Jenkins, create a TestNG xml file,
 and define a TestNG suite that includes certain API test classes to execute.
 3. Create a profile in pom.xml that includes the TestNG xml as a test suite
 4. On Jenkins job under build workflow, select top level maven target and pass the following command: 
 ```
 `clean test -P<putProfileIdHere>`
 ```
 
 Profile example:
 
 ```
 `	<profiles>
		<profile>
			<id>api_tests</id>
			<build>
				<plugins>
					<plugin>
						<groupId>org.apache.maven.plugins</groupId>
						<artifactId>maven-surefire-plugin</artifactId>
						<version>3.0.0-M5</version>
						<configuration>
							<suiteXmlFiles>
								<suiteXmlFile>testng.xml</suiteXmlFile>
							</suiteXmlFiles>
						</configuration>
					</plugin>
				</plugins>
			</build>
		</profile>
	</profiles>`
 ```