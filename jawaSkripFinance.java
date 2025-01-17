import java.util.Map;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

public class jawaSkripFinance{
    public static double calculateTotalRepayment(double principalAmount, double annualInterestRate, double repaymentPeriod) {
        // Convert annual interest rate from percentage to decimal
        double monthlyInterestRate = (annualInterestRate / 100) / 12;

        // Convert repayment period to months
        double totalMonths = repaymentPeriod * 12;

        // Calculate monthly payment using the formula for an amortizing loan
        double monthlyPayment = 
            (principalAmount * monthlyInterestRate) / 
            (1 - Math.pow(1 + monthlyInterestRate, -totalMonths));

        // Calculate total repayment amount
        double totalRepayment = monthlyPayment * totalMonths;
        return totalRepayment;
    }

    public static Map<String, Double> predictMonthlyInterest(double deposit) {
        // Bank interest rates based on the table
        Map<String, Double> bankInterestRates = new HashMap<>();
        bankInterestRates.put("RHB", 2.6);
        bankInterestRates.put("Maybank", 2.5);
        bankInterestRates.put("Hong Leong", 2.3);
        bankInterestRates.put("Alliance", 2.85);
        bankInterestRates.put("AmBank", 2.55);
        bankInterestRates.put("Standard Chartered", 2.65);

        // Map to store monthly interest earned by each bank
        Map<String, Double> monthlyInterestMap = new HashMap<>();

        for (Map.Entry<String, Double> entry : bankInterestRates.entrySet()) {
            String bankName = entry.getKey();
            double interestRate = entry.getValue();
            // Calculate monthly interest
            double monthlyInterest = (deposit * interestRate) / 12 / 100; // Convert % to decimal
            monthlyInterestMap.put(bankName, monthlyInterest);
        }

        return monthlyInterestMap;
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int x = 0, Cl = 0, schedule_pay = 0, exit = 1;
        double principal_amount, interest_rate, repayment_period, total_repayment=0, payment_per_period=0;
        do{
            System.out.println("Click 1 for credit loan, 2 for Deposit Interest Predictor");
            x = input.nextInt();
            switch(x){
            
                case 1:
                    System.out.println("Click 1 to apply, 2 to repay");
                    Cl = input.nextInt();

                    switch(Cl){
                        case 1:
                            System.out.println("Enter principal amount");
                            principal_amount = input.nextDouble();
                            System.out.println("Enter interest rate");
                            interest_rate = input.nextDouble();
                            System.out.println("Enter repayment period");
                            repayment_period = input.nextDouble();
                            total_repayment = calculateTotalRepayment(principal_amount, interest_rate, repayment_period);
                            System.out.println("The total repayment value is RM" + total_repayment);

                            System.out.println("Do you want to schedule payment monthly (1) or quarternerly (2) ?");
                            schedule_pay = input.nextInt();

                            switch(schedule_pay){
                                case 1:
                                    payment_per_period = total_repayment / (repayment_period * 12);
                                    System.out.println("You need to pay RM"+ payment_per_period + " per month ");
                                    break;
                                case 2: 
                                    payment_per_period = total_repayment / (repayment_period * 4);
                                    System.out.println("You need to pay RM"+ payment_per_period + " per quarter ");
                                    break;
                                default:
                                    System.out.println("Error");
                            }
                            break;

                        case 2 :
                            System.out.printf("Please pay the following amount: RM%.2f", payment_per_period);
                            break;
                        default : 
                            System.out.println("Error");
                    break;
                }

                case 2:
                    Map<String, Double> monthlyInterest = predictMonthlyInterest(total_repayment);

                    System.out.println("Monthly Interest Prediction:");
                    for (Map.Entry<String, Double> entry : monthlyInterest.entrySet()) {
                    System.out.printf("%s: RM %.2f%n", entry.getKey(), entry.getValue());
                    }
                    break;
                default:
                    System.out.println("Error");
            }
            System.out.println("Press 0 to exit system, press other keys to continue");
            exit = input.nextInt();
        }while(exit!=0);
    input.close();
}
}

