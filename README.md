# 🛡️ Insurance Automation Project (Parabank - Selenium TestNG)

## 📌 Overview
This project automates **Insurance Policy Management workflows** using:
- **Selenium WebDriver** for UI automation
- **TestNG** for test execution & reporting
- **Page Object Model (POM)** design for maintainability
- **Data-driven testing** using Excel for premium calculations
- **ExtentReports / TestNG Reports** for test result visualization

It validates critical flows like:
✅ User Login  
✅ Policy Creation  
✅ Premium Calculation  
✅ Policy Renewal  
✅ Claims Submission  
✅ Payment Gateway  

---

## 📂 Project Structure

insurance-automation-project/
│── src/
│ ├── main/java/com/parabank/
│ │ ├── core/ # WebDriver factory, config
│ │ ├── pages/ # Page Object Model classes (Login, BillPay, etc.)
│ │ ├── utils/ # Excel reader, Premium calculator
│ ├── test/java/com/parabank/
│ │ ├── tests/ # TestNG test classes
│ │ ├── listeners/ # TestNG listeners for reporting
│
│── testng.xml # TestNG suite configuration
│── pom.xml # Maven dependencies
│── README.md # Project documentation
│── .gitignore # Ignore unnecessary files

yaml
Copy code

---

## ⚙️ Setup Instructions

1. **Clone Repository**
   ```bash
   git clone https://github.com/<your-username>/insurance-automation-project.git
   cd insurance-automation-project
Install Dependencies

bash
Copy code
mvn clean install
Run Tests

bash
Copy code
mvn test
Or using TestNG:

bash
Copy code
mvn test -DsuiteXmlFile=testng.xml
🧪 Test Scenarios Automated
🔑 Login Tests
Valid login with correct credentials

Invalid login with wrong credentials

📜 Policy Creation Tests
Create new policy (Savings account)

Create new policy (Checking account)

💰 Premium Calculation Tests
Validate premium calculation using Excel data

Scenarios: Age 22, 30, 50, 35 with different plans

🔄 Renewal Tests
Renew existing policy via account linking

📂 Claims Tests
Submit a claim using Bill Pay

Validate claim acknowledgement

💳 Payment Gateway Tests
Validate failed transaction when mismatched accounts are used

📊 Reporting
After test execution:

TestNG Report → test-output/index.html

Extent Report (if configured) → reports/extent-report.html

🚀 Tech Stack
Language: Java 11+

Build Tool: Maven

Automation: Selenium WebDriver

Framework: TestNG

Design Pattern: Page Object Model (POM)

Data Handling: Apache POI (Excel)

👨‍💻 Author
Aravind Pragada
