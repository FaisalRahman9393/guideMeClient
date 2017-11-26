package guideMe;


import java.security.SignedObject;

public interface auctioningServerInt extends java.rmi.Remote{

    /**
     * On calling, a new auction will be created.
     *
     * @param itemPrice - Creates a new starting price for the auction
     * @param itemDescription - Creates a description about the auction
     * @param itemReservePrice - Creates a reserve price for them auction
     * @return - returns the item ID
     * @throws java.rmi.RemoteException
     */
    public int addItemForAuction(float itemPrice, String itemDescription, float itemReservePrice, String client) throws java.rmi.RemoteException;


    /**
     * On calling, an auction for an item will be closed.
     * A winner for an auction will be returned of there is one.
     * If there is no winner, a default message will be returned.
     *
     * @param itemID - The ID of the auction that needs closing.
     * @return - Returns a message indicating the status of the auction.
     * @throws java.rmi.RemoteException
     */
    public String closeItemForAuction(int itemID, String client) throws java.rmi.RemoteException;

    /**
     *
     * @param chal - Sends the server a numerical challenge to authenticate the server
     * @return returns a signed object which can then be verfied
     * @throws java.rmi.RemoteException
     */
    public SignedObject challengeForServer (int chal, String client) throws java.rmi.RemoteException;

    /**
     *
     * @return Client Gets a numerical challenge from the server so it can authenticate itself
     * @throws java.rmi.RemoteException
     */
    public int receiveChallengeForClient() throws java.rmi.RemoteException;

    /**
     *
     * @return Sends the server the signed challenge so the server can authenticate the client
     * @throws java.rmi.RemoteException
     */
    public void clientChallengeResult(SignedObject sO) throws java.rmi.RemoteException;

}
