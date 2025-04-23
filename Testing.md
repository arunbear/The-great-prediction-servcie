Tests are included especially [API tests](src/test/java/com/example/GreatPredictionServiceApplicationTests.java)

An example test run

```
% ./mvnw -B clean test
[INFO] Scanning for projects...
[INFO] 
[INFO] ----------------< org.example:great-prediction-service >----------------
[INFO] Building great-prediction-service 0.0.1-SNAPSHOT
[INFO]   from pom.xml
[INFO] --------------------------------[ jar ]---------------------------------
[INFO] 
[INFO] --- clean:3.4.1:clean (default-clean) @ great-prediction-service ---
[INFO] Deleting /home/arunp/My/code/great-prediction-service/target
[INFO] 
[INFO] --- resources:3.3.1:resources (default-resources) @ great-prediction-service ---
[INFO] Copying 1 resource from src/main/resources to target/classes
[INFO] Copying 1 resource from src/main/resources to target/classes
[INFO] 
[INFO] --- compiler:3.13.0:compile (default-compile) @ great-prediction-service ---
[INFO] Recompiling the module because of changed source code.
[INFO] Compiling 12 source files with javac [debug parameters release 21] to target/classes
[INFO] 
[INFO] --- resources:3.3.1:testResources (default-testResources) @ great-prediction-service ---
[INFO] skip non existing resourceDirectory /home/arunp/My/code/great-prediction-service/src/test/resources
[INFO] 
[INFO] --- compiler:3.13.0:testCompile (default-testCompile) @ great-prediction-service ---
[INFO] Recompiling the module because of changed dependency.
[INFO] Compiling 4 source files with javac [debug parameters release 21] to target/test-classes
[INFO] 
[INFO] --- surefire:3.3.1:test (default-test) @ great-prediction-service ---
[INFO] Using auto detected provider org.apache.maven.surefire.junitplatform.JUnitPlatformProvider
[INFO] 
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
Java HotSpot(TM) 64-Bit Server VM warning: Sharing is only supported for boot loader classes because bootstrap classpath has been appended
[INFO] +--com.example.GreatPredictionServiceApplicationTests - 7.134 s
[INFO] |  +-- [OK] accepts a prediction creation message - 1.720 s
[INFO] |  +-- [OK] a prediction can be updated - 0.204 s
[INFO] |  +-- [OK] a closed prediction cannot be updated - 0.078 s
[INFO] |  +-- [OK] can create a prediction - 0.053 s
[INFO] |  '-- [OK] can find all predictions for a given user - 0.101 s
[INFO] +--com.example.service.PredictionServiceTest - 0.546 s
[INFO] |  +-- [OK] uses a repository to find user predictions - 0.517 s
[INFO] |  +-- [OK] uses a repository to find a prediction - 0.004 s
[INFO] |  +-- [OK] uses a repository to update a prediction - 0.014 s
[INFO] |  '-- [OK] uses repositories to save a prediction - 0.005 s
[INFO] +--com.example.controller.PredictionControllerTest - 0.869 s
[INFO] |  +-- [OK] uses predictionService to find a prediction - 0.053 s
[INFO] |  +-- [OK] uses predictionService to update a prediction - 0.030 s
[INFO] |  +-- [OK] uses predictionService to create a prediction - 0.048 s
[INFO] |  '-- [OK] uses predictionService to find user predictions - 0.009 s
[INFO] +--com.example.entity.PredictionTest - 0.006 s
[INFO] |  +-- [OK] a prediction is open if its match has not started - 0.001 s
[INFO] |  '-- [OK] a prediction is closed if its match has started - 0.001 s
[INFO] 
[INFO] Results:
[INFO] 
[INFO] Tests run: 15, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  13.691 s
[INFO] Finished at: 2025-04-23T18:44:21+01:00
[INFO] ------------------------------------------------------------------------
% 

```
