# Insurance Automation — ParaBank (Capstone)

Maps insurance flows onto ParaBank demo:
- Login/Logout
- Policy Creation/Renewal: Open New Account (SAVINGS/CHECKING)
- Claims & Payment Gateway: Bill Pay (success & failure)
- Premium Calculation: data-driven validation via `PremiumCalculator` and `policies.xlsx`

## Run
```bash
mvn clean test
# Reports: target/extent-report.html
# Screenshots on failure: target/screenshots/
```
Use `testng.xml` to run cross-browser (Chrome + Firefox) in parallel.
