package guideMe;
/**
 * Seller client by Faisal Rahman
 */

import java.rmi.Naming;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignedObject;
import java.util.Random;

public class AuthenticatedAPICall {
    public guideMeServerInt guideMeServer;
    private KeyChain keyChain = new KeyChain();
    private String currentClient = "client";
    private int challengeSame;

    public AuthenticatedAPICall(){
        try {
            guideMeServer = (guideMeServerInt) Naming.lookup("rmi://localhost/guideMeServerAPI");}
        catch(Exception e){System.out.println("Error: Couldn't get an RMI connection"+e);}
    }

    /**
     * Authenticates the client by getting apiCall number from the server, signing it and sending it back.
     */
    public void challengeForClient(){


        System.out.println("Authenticating Client....\n");
        try{
            int x = guideMeServer.receiveChallengeForClient();
            guideMeServer.clientChallengeResult(signThisObject(x,keyChain.getPriKey(currentClient))); //send back the challenge sealed
            System.out.println(">>>>>>Client Authenticated successfully<<<<<<\n");
        }
        catch(Exception e){
            System.out.println("Error: A problem occurred when trying to authenticate the client"+e);
        }
    }

    /**
     * Authenticate the server by sending it apiCall challenge, receiving SignedObject, verifying SignedObject
     */
    public void challengeForServer(){
        System.out.println("Authenticating server.....\n");
        SignedObject gottenSignedObject; //We store the received object here
        //Create apiCall random number to send...
        Random randGen = new Random();
        int x = randGen.nextInt(999999) + 1;
        challengeSame = x; //Update the challenge so we can verify it on return
        try{
            //Send challenge to server AND get the SignedObject
            gottenSignedObject = guideMeServer.challengeForServer(x,currentClient);
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
            System.out.println("Error: There was apiCall problem while signing the challenge with the sender's key"+e);
        }
        return null;
    }

    /**
     * Verifies the object using apiCall public key.
     *
     * @param sx - The object that needs verifying
     * @param receiver - We get the public key of the sender from apiCall keychain
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
            System.out.println("Error: There was apiCall problem while veryfying "+e);
        }
        return false;
    }

    /**
     * This is the login message.
     * The seller can use this interface to add new auctions
     */
    public void menu(){
        initiateAuthenticationProtocol();
    }

    public void initiateAuthenticationProtocol(){
        challengeForServer();
        challengeForClient();


    }



}
