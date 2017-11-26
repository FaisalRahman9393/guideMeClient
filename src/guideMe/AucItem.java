package guideMe;

public class AucItem implements java.io.Serializable{
    public int 		itemID 	   	= 0;
    public float 	itemStartPrice 	= 0;
    public float 	itemReserve  	= 0;
    public float 	itemCurrentBid = 0;
    public String 	itemShortDescription = null;
    public String highestItemBidder = null;
    public String highestItemBidderEmail = null;
    public String client = null;

    public AucItem(int xItemID, String xItemShortDescription, float xStartPrice, float xItemReserve, String xClient) {
        itemID = xItemID;
        itemShortDescription = xItemShortDescription;
        itemStartPrice = xStartPrice;
        itemReserve = xItemReserve;
        this.client = xClient;
    }

}

