Meta:
@author Marcio Gualtieri

Narrative:
In order to check that the batch file booking request processor fullfil the requirements
As a developer
I want to execute the processor and verify that the output is correct for different inputs

Scenario: Input from requirements document
Given an input file with the following lines:
|lines                     |
|0900 1730                 |
|2011-03-17 10:17:06 EMP001|
|2011-03-21 09:00 2        |
|2011-03-16 12:34:56 EMP002|
|2011-03-21 09:00 2        |
|2011-03-16 09:28:23 EMP003|
|2011-03-22 14:00 2        |
|2011-03-17 11:23:45 EMP004|
|2011-03-22 16:00 1        |
|2011-03-15 17:29:12 EMP005|
|2011-03-21 16:00 3        |
When I process the booking requests in the file
Then the output file has the following lines:
|lines             |
|2011-03-21        |
|09:00 11:00 EMP002|
|2011-03-22        |
|14:00 16:00 EMP003|
16:00 17:00 EMP004 |

Scenario: Two overlapping booking requests
Given an input file with the following lines:
|lines                     |
|0900 1730                 |
|2011-03-17 10:17:06 EMP005|
|2011-03-21 09:00 2        |
|2011-03-16 12:34:56 EMP006|
|2011-03-21 10:00 2        |
When I process the booking requests in the file
Then the output file has the following lines:
|lines             |
|2011-03-21        |
|10:00 12:00 EMP006|

Scenario: Two requests, one of the booking requests is outside office hours (too early)
Given an input file with the following lines:
|lines                     |
|0900 1730                 |
|2011-03-17 10:17:06 EMP007|
|2011-03-21 13:00 2        |
|2011-03-16 12:34:56 EMP008|
|2011-03-21 08:00 2        |
When I process the booking requests in the file
Then the output file has the following lines:
|lines             |
|2011-03-21        |
|13:00 15:00 EMP007|

Scenario: Two requests, one of the booking requests is outside office hours (too late)
Given an input file with the following lines:
|lines                     |
|0900 1730                 |
|2011-03-17 10:17:06 EMP009|
|2011-03-21 18:00 2        |
|2011-03-16 12:34:56 EMP010|
|2011-03-21 09:00 2        |
When I process the booking requests in the file
Then the output file has the following lines:
|lines             |
|2011-03-21        |
|09:00 11:00 EMP010|
