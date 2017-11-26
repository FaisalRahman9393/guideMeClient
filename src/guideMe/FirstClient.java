package guideMe;

/**
 * Seller client by Faisal Rahman
 */

import java.rmi.Naming;
import java.security.*;
import java.util.Random;
import java.util.Scanner;

public class FirstClient {
    private auctioningServerInt auctionServer;
    private Scanner userInput = new Scanner(System.in); //To read user input
    //Temp variables where input will be stored
    private float      priceOfItem = 0;
    private float      ReserveOfItem = 0;
    private String     descriptionItem = null;
    private KeyChain keyChain = new KeyChain();
    private String currentClient = null;
    private int challengeSame;
    public String x = "Hello";

    public FirstClient(){
        System.out.println("\nWelcome to the Basic Auctioning System...");
        System.out.println("Note: This program is used for starting  and managing auctions only\n");
        try {auctionServer = (auctioningServerInt) Naming.lookup("rmi://localhost/auctionServer");}
        catch(Exception e){System.out.println("Error: Couldn't get an RMI connection"+e);}
    }

    /**
     * Authenticates the client by getting a number from the server, signing it and sending it back.
     */
    public void challengeForClient(){
        System.out.println("Authenticating Client....\n");
        try{
            int x = auctionServer.receiveChallengeForClient();
            auctionServer.clientChallengeResult(signThisObject(x,keyChain.getPriKey(currentClient))); //send back the challenge sealed
            System.out.println(">>>>>>Client Authenticated successfully<<<<<<\n");
        }
        catch(Exception e){
            System.out.println("Error: A problem occurred when trying to authenticate the client"+e);
        }
    }

    /**
     * Authenticate the server by sending it a challenge, receiving SignedObject, verifying SignedObject
     */
    public void challengeForServer(){
        System.out.println("Authenticating server.....\n");
        SignedObject gottenSignedObject; //We store the received object here
        //Create a random number to send...
        Random randGen = new Random();
        int x = randGen.nextInt(999999) + 1;
        challengeSame = x; //Update the challenge so we can verify it on return
        try{
            //Send challenge to server AND get the SignedObject
            gottenSignedObject = auctionServer.challengeForServer(x,currentClient);
            //Verify the SignedObject to see if it actually came from the server
            verifyObject(gottenSignedObject, keyChain.getPubKey("Server"));
            System.out.println(">>>>>>Server Authenticated successfully<<<<<<\n");
        }catch(Exception e){
            System.out.println("Error: A problem occurred when trying to authenticate the server"+e);
        }
    }

    /**
     * @param challenge int that will be signed
     * @param sender PrivateKey from the sender that will be used to sign the challenge
     * @return A signed object will be returned
     */
    SignedObject signThisObject(int challenge, PrivateKey sender){
        try {
            //Choosing the correct digital signature instance so that we can sign the incoming message
            Signature signature = Signature.getInstance(sender.getAlgorithm(),"SUN");
            //Time to sign the challenge using the private key, and the type of algorithm used to sign it
            SignedObject signedObject = new SignedObject(challenge, sender, signature);
            return signedObject;
        }
        catch(Exception e){
            System.out.println("Error: There was a problem while signing the challenge with the sender's key"+e);
        }
        return null;
    }

    /**
     * Verifies the object using a public key.
     *
     * @param sx - The object that needs verifying
     * @param receiver - We get the public key of the sender from a keychain
     * @return Boolean - True if verified, false otherwise
     */
    Boolean verifyObject(SignedObject sx, PublicKey receiver){
        try {
            Signature s = Signature.getInstance(receiver.getAlgorithm(), "SUN");
            //Now get the public key of the receiver for verification
            sx.verify(receiver,s);
            int i = (Integer) sx.getObject();
            if (challengeSame == i)return sx.verify(receiver,s); //Returns true
        }
        catch(Exception e){
            System.out.println("Error: There was a problem while veryfying "+e);
        }
        return false;
    }

    /**
     * This is the login message.
     * The seller can use this interface to add new auctions
     */
    public void menu(){
        //Login screen here
        System.out.println(">>>>>>>> Seller login <<<<<<<<");
        System.out.println("You have the following options: \n");
        System.out.println("Entering 1: This will log you in as Jack");
        System.out.println("Entering 2: This will log you in as Jeff");
        System.out.println("Entering 3: This will log you in as Sam");
        System.out.println("Entering 4: This will log you in as Tom");

        //Gets the user input
        System.out.println("\nEnter one of the above commands: ");
        int option = userInput.nextInt(); //Scan the user's option input as an int


        switch(option){
            case 1: {
                this.currentClient = "Jack"; initiateAuthenticationProtocol();
                System.out.println("\nWelcome " +currentClient+"!");
                giveMeOptions(); break;
            }
            case 2:{
                this.currentClient = "Jeff"; initiateAuthenticationProtocol();
                System.out.println("\nWelcome " +currentClient+"!");
                giveMeOptions(); break;
            }
            case 3: {
                this.currentClient = "Sam"; initiateAuthenticationProtocol();
                System.out.println("\nWelcome " +currentClient+" !");
                giveMeOptions(); break;
            }
            case 4:{
                this.currentClient = "Tom"; initiateAuthenticationProtocol();
                System.out.println("\nWelcome " +currentClient+"!");
                giveMeOptions(); break;
            }
            default:{System.out.println("Login failed! Try again...\n");menu();break;}
        }
    }

    private void initiateAuthenticationProtocol(){
        challengeForServer();
        challengeForClient();
    }
    /**
     * Once logged in and authenticated, these options are provided
     *
     * The seller can use this interface to add and remove auctions
     */
    private void giveMeOptions(){
        int option;
        System.out.println("You have the following options: \n");
        System.out.println("Entering 1: This will add a new item for auctioning");
        System.out.println("Entering 2: This will allow you you close an auction");
        System.out.println("Entering 3: This will sign you out");
        System.out.println("Entering 4: This will close this program");

        //Gets the user input
        System.out.println("\nEnter one of the above commands: ");
        option = userInput.nextInt(); //Scan the user's option input as an int

        //Navigate
        if (option == 1) {addItemForAuction();}
        if (option == 2) {closeItemForAuction();}
        if (option == 3) {menu();}
        if (option == 4) {System.exit(1);}
        else {System.out.println("Wrong input! Try again...\n");menu();}
    }

    /**
     * Creates a new auction by collecting inputs for starting price, minimum price and item description.
     * Then sends the result to the server
     */
    private void addItemForAuction(){

        try{
            System.out.println("Price of the item: ");
            priceOfItem = userInput.nextFloat();

            System.out.println("Reserve price for the item: ");
            ReserveOfItem = userInput.nextFloat();

            System.out.println("Short description of the item: ");
            descriptionItem = userInput.nextLine();
        } catch (Exception e){
            System.out.println(e);
        }

        //Send the user input to the server
        try {
            int idGotten = auctionServer.addItemForAuction(priceOfItem, descriptionItem, ReserveOfItem, currentClient);
            System.out.println("\nCongratulations "+currentClient+ " Your item (ID: " + idGotten + ") is now up for auction.\n" );
            menu();
        } catch (Exception e){
            System.out.println("Sorry, something went wrong while uploading your auction to the server: " + e);
        }
        giveMeOptions();
    }
    /**
     * On calling, an auction for an item will be closed.
     * A winner for an auction will be returned of there is one.
     * If there is no winner, a default message will be returned.
     */
    public void closeItemForAuction(){
        System.out.println("\n Please enter the ID associated with the item: \n");
        try{
            int itemID = userInput.nextInt(); //Scan the ID and store as an Int
            String message = auctionServer.closeItemForAuction(itemID, currentClient); //Communicate with server and get message
            System.out.println(message);
        } catch(Exception e){
            System.out.println(e);
        }
        giveMeOptions();
    }
}
