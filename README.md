# Simple Loan

mvn clean install
java -jar target/simpleloan-0.0.1.jar Market_Data.csv 1000


## Assumptions
* Total Capital =  Initial capital * ( 1 + interest)^Time
* Lender rates is monthly
* To get the best rates I select the highest rate from the lowest selected lenders