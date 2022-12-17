import java.io.*;
import java.time.*;
import java.util.*;
/*
* Ahmed Mohamed Sallam 20210614
* Mohamed Gamal Abdelaziz 20201146
* Omar Walid Ahmed 20201126
* Ahmed Adel Ali 20201009
*/
public class PC {

    // Create a list shared by producer and consumer
    // Size of list is 2.
    Queue<Long> list = new LinkedList<>();
    public static long capacity;
    public static long numOfPrimes = 1;
    public static long bigPrime = 0;
    public static boolean isAlive = true;
    public static long size ;
    public static long i = 3;
    public static boolean limiter = false;
    public String outputFile;
    public static FileWriter myWriter;
    public File file;
    public static LocalTime runningTimeStart;
    public static LocalTime runningTimeEnd;


    public PC(long size, long capacity,String outputFile){
        this.size= size;
        this.capacity = capacity;
        this.outputFile = outputFile;
        try {
            runningTimeStart = LocalTime.now();
            if (!outputFile.substring(outputFile.length() - 3, outputFile.length()).equals("txt")) {
                this.file = new File(outputFile + ".txt");
                this.file.createNewFile();
                this.myWriter = new FileWriter(outputFile + ".txt");
            } else {
                this.file = new File(outputFile);
                this.file.createNewFile();
                this.myWriter = new FileWriter(outputFile);
            }
            if (size >= 2) {
                String s = "\"2\" ,";
                this.myWriter.write(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Function called by producer thread
    synchronized public void produce() throws InterruptedException, IOException {


        for (; i <= size; i+=2) {
            long num = 0;
            for (long l = 2; l * l <= i; l++) {
                if (i % l == 0) {
                    num = 1;
                    break;
                }
            }
            if(num == 0) {
                numOfPrimes++;
                list.add(i);
//                System.out.println("Producer produced-" + i);

            }

            if (i == size-1 && size%2 == 0 ) { // 100
                limiter = true;
            }
            else if (i==size && size%2 != 0){//99
                limiter = true;
            }

            // producer thread waits while list
            // is full


            while (list.size() == capacity || limiter){
               System.out.println("producer waited");
                wait();
            }
            //-------------------------------------------


//            System.out.println("numbers of prime" + numOfPrimes);

            // to insert the jobs in the list
            // notifies the consumer thread that
            // now it can start consuming
            notify();

            // makes the working of program easier
            // to  understand
          //  Thread.sleep(100);

        }
    }

    // Function called by consumer thread
    synchronized public void consume() throws InterruptedException, IOException {
        //end of consumer
        // end of code !!!
        while (i < size) {
            // consumer thread waits while list
            // is empty
            while (list.size() == 0) {
                System.out.println("waitConsume");
                if(limiter){

                    myWriter.close();
                    isAlive = false;
                    runningTimeEnd = LocalTime.now();
                    long time = Math.abs(runningTimeEnd.getSecond()-runningTimeStart.getSecond())*1000;
                    GUI.setTime(String.valueOf(time));
                    ProcessBuilder pb = new ProcessBuilder("Notepad.exe", file.getName());
                    pb.start();
                }
                wait();
            }

            // to retrieve the first job in the list
            long val = list.poll();
            bigPrime = val;
            String big = String.valueOf(bigPrime);
            String prime = String.valueOf(numOfPrimes);
            GUI.setBigRamy(prime);
            GUI.setPrimeNums(big);
            String s=String.format("\"%d\" ,",val);
            try {
                myWriter.write(s);
            } catch (IOException e) {
                e.printStackTrace();
            }
//            System.out.println("Consumer consumed-" + val);
            // Wake up producer thread
            notify();
//            System.out.println("numbers of prime" + numOfPrimes);
            // and sleep
          // Thread.sleep(100);
//            System.out.println(i);
        }

    }
}
