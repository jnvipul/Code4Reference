package com.atc.accontroller;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


import com.atc.AC;
import com.atc.ACManager;
import com.atc.ACUtility;
import com.atc.AcSize;
import com.atc.AcType;


public class AirTrafficControlerConsole {

	BufferedReader 		mBr; 				/*For console input*/
	Boolean 			isBootupComplete;	/*Bootup complete flag*/
	ACManager 			acManager;			/*Aircraf Manager*/
	
	/**
	 * This is without argument constructor which initializes the member variables.
	 */
	public AirTrafficControlerConsole(){
		mBr					= new BufferedReader(new InputStreamReader(System.in));
		isBootupComplete 	= false;
		acManager 			= null;
	}

	/**
	 * Creates AC object which will be further saved in the Priority Queue.
	 * @return	returns the AC object
	 * @throws IOException	if there is any problem in reading from the console
	 * @throws NumberFormatException	if user doesn't provide numeric value for any option.
	 */

	public AC makeAC()  throws IOException, NumberFormatException {
		String 	strACName;
		AcType 	acType;
		AcSize 	acSize;
		AC 		ac;

		System.out.print("Enter Aircraft name : ");
		strACName = mBr.readLine();	
		System.out.print("Type of Aircraft"+ACUtility.newLine()+" Passenger ->1 "+ACUtility.newLine()+" Cargo -> 2 "+ACUtility.newLine()+" : ");
		acType = AcType.intToEnum(Integer.parseInt(mBr.readLine()));
		System.out.print("Type of Aircraft"+ACUtility.newLine()+" Big ->1 "+ACUtility.newLine()+" Small -> 2 "+ACUtility.newLine()+" : ");
		acSize = AcSize.intToEnum(Integer.parseInt(mBr.readLine()));

		ac= new AC(strACName,acType,acSize);

		return ac;
	}

	/**
	 * This method displays the list of option for Air Traffic Controller.
	 */
	public void printCommand(){
		System.out.print("1. Takeoff Request" + ACUtility.newLine()
				+"2. Proceed to takeoff" + ACUtility.newLine()
				+"3. Display AC Queue" + ACUtility.newLine()
				+"4. Bootup " + ACUtility.newLine()
				+"5. Shutdown" + ACUtility.newLine()
				+" : ");
	}

	/**
	 * Method which will handle the commands and execute the proper command
	 * 
	 */

	public void Execute(){
		Integer option;
		AC ac;

		while(true){
			printCommand();
			try {
				option = Integer.parseInt(mBr.readLine());

				switch(option){

				case 1://Takeoff request	
					if(isBootupComplete){
						acManager.takeoffRequest(makeAC());
					}else{
						System.out.println("Please boot the system first"+ACUtility.newLine());
					}
					break;

				case 2://Proceed to takeoff
					if(isBootupComplete){
						ac = acManager.acGoingtoTakeOff();
						if(null!=ac){
							System.out.println(ac);
						}else{
							System.out.println("Aircraft Queue is empty");
						}
					}else{
						System.out.println("Please boot the system first"+ACUtility.newLine());
					}
					break;

				case 3:	//Display 
					if(isBootupComplete){
						acManager.displayPriorityQueue();
					}else{
						System.out.println("Please boot the system first"+ACUtility.newLine());
					}
					break;

				case 4: //Bootup
					System.out.println(ACUtility.newLine()+"System is Booting up...");

					acManager = ACManager.getInstance();
					//Setting the isBootupComplete flag
					isBootupComplete = true;


					System.out.println("Bootup complete..."+ACUtility.newLine());
					break;

				case 5: //Shutdown
					//Save the priority queue 
					if(isBootupComplete){
						acManager.saveQueue();
					}
					//exit now
					System.out.println("Shuting down the system...");
					System.exit(0);						
					break;

				default:
					System.out.println("Please enter valid option");
					break;
				}

			} catch (NumberFormatException e) {

				System.out.println("Please enter valid number");

			} catch (IOException e) {

				System.err.println("There was problem in reading from console");
			}
		}
	}

	/**
	 * Main method for entry point of this application.
	 * @param args
	 */
	public static void main(String[] args) {
		AirTrafficControlerConsole atcConsole = new AirTrafficControlerConsole();
		atcConsole.Execute();
	}


}