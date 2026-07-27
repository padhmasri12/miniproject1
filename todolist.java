import java.util.Scanner;
public class todolist{
    public static void main(String [] args){
        Scanner sc=new Scanner(System.in);
        String[] tasks = new String[10];
        int count=0;
        int choice;

        do{
            System.out.println("\n ==== TO DO LIST ====");
            System.out.println("1.Add Task");
            System.out.println("2.Update Tasks");
            System.out.println("3. Delete Tasks");
            System.out.println("4. View Tasks");
            System.out.println("5. Exit ");

            System.out.println("Enter the choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch(choice){
                case 1:
                    System.out.println("enter task");
                    tasks[count]=sc.nextLine();
                    count++;
                    System.out.println("Task added Successfully");
                    break;

                case 2:
                    System.out.println("enter task number:");
                    int update= sc.nextInt();
                    sc.nextLine();
                    System.out.println(" Enter the new tasks");

                    tasks[update -1 ]=sc.nextLine();
                    System.out.println("Task updated successfully");
                    break;

                case 3:
                    System.out.println("enter the task number:");
                    int delete = sc.nextInt();
                    for(int i = delete -1 ; i < count -1 ; i++){
                        tasks[i]=tasks[i+1];

                    }
                    count --;
                    System.out.println("tasks deleted successfully");
                    break;
                case 4:
                    System.out.println("\n tasks list:");
                    for (int i =0; i < count;i++){
                    System.out.println((i+1)+"."+tasks[i]);
                    }
                    break;
                case 5:
                    System.out.println("Thank You");
                    break;
                    default:
                       System.out.println("invalid choice");

                       
            }
        } while(choice !=5);
            sc.close();

    }   

}
        
    



        

        
