import java.util.Scanner;

public class CDSR {

    static double balance = 0.0;
    static boolean svngs = false;
    static int save =0;
    

    public static void credit(){
        boolean truth = true;
        System.out.println("==Credit==");
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter amount : ");
        double cre = scan.nextDouble();
        scan.nextLine();

        

        while(truth == true){
            System.out.print("Description : ");
            String desc = scan.nextLine();

            if( desc.length() > 100){
                System.out.print("\nDescription is too long. Please write it again. \n");
            }
            else{
                truth=false;
            }

        }
        
       
        if(cre>= 0 && cre<= 50000){
            balance+=cre;
            System.out.println("Credit successfully recorded !! \n");
        }
        else{
            System.out.println("Transaction failed \n");
        }

        }
        
            
        
    public static void debit(){
        double extra=0;
        boolean truth = true;
        System.out.println("==Debit==");
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter amount : ");
        double deb = scan.nextDouble();
        scan.nextLine();
        if(svngs==true){
            extra = (save/100.0)*deb;
        }

        while(truth == true){
            System.out.print("Description : ");
            String desc = scan.nextLine();

            if( desc.length() > 100){
                System.out.print("\nDescription is too long. Please write it again. \n");
            }
            else{
                truth=false;
            }

        }
        
       
        if(deb>= 0 && deb<= balance){
            balance-=deb;
            balance+=extra;
            System.out.println("Debit successfully recorded !! \n");
        }
        else{
            System.out.println("Transaction failed \n");
        }
                 
        

    }

    public static void Savings(){
        String resp;
        Scanner scan = new Scanner(System.in);

        while(svngs==false){
        System.out.println("==Savings==\n");
        System.out.print("Are you sure you want to activate it? (Y/N) : ");
        resp=scan.nextLine();

        if(resp.trim().equalsIgnoreCase("Y")){
            svngs=true;
            continue;
        }
        else{
            return;
        }
        
        }
        

       
        System.out.print("Please enter the percentage you wish to deduct from the next debit: ");
        save = scan.nextInt();



    }


    public static void main(String[] args) {
        
        Scanner scan = new Scanner(System.in);
        int choice;
        boolean truth = true;

        while(truth== true){

        System.out.println("==Transactions==");
        System.out.println("1.Credit \n2.Debit\n3.Savings\n4.Quit\n");

        choice = scan.nextInt();

        switch(choice){
            case 1:
            credit();
            break;
            case 2:
            debit();
            break;
            case 3:
            Savings();
            break;
            case 4:
            truth=false;
            break;
            default:
            System.out.println("Invalid input \n");
            break;
        }

        }

        System.out.println(balance);
             

    }
}
