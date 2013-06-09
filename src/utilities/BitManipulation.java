package utilities;

import hypeerweb.node.Node;

/**
 * The functions included are made for convenient bit manipulation of webId's in the 
 * context of the HyPeerWeb.
 * @author Craig Jacobson
 *
 */
public class BitManipulation{

	/**
	 * Finds the surrogate for a given webId. A surrogate is defined here as the number 
	 * who's bit representation has a zero where the webId's leading 1 bit is, for example:
	 * if the webId's three the bit representation is 11 then the surrogate is 01.
	 * @param webId The id to find a surrogate of.
	 * @return The webId's surrogate.
	 * @pre An webId greater than or equal to one (zero does not have a surrogate).
	 * @post result = webId's surrogate
	 */
	public static int calculateSurrogateWebId(int webId){
		assert (webId >= 1);
		
		return (webId^calculateHighestOneBitMask(webId));
	}
	
	/**
	 * Finds the child's webId from the parent's webId. Note that the height of the parent must
	 * be one less than that of the child. This method is intended to be called before the 
	 * <code>addChild</code> method is called.
	 * @param parentWebId The webId of the parent node.
	 * @param parentHeight The height of the parent node (one less than that of the child node).
	 * @return The webId of the child node.
	 * @pre parentWebId >= 0, parentHeight >= 0, parentWebId <= 0x3FFFFFFF, parentHeight <= 30
	 * @post result = child's webId
	 */
	public static int calculateChildWebId(int parentWebId, int parentHeight){
		assert (parentWebId >= 0 && parentWebId <= (0x3FFFFFFF) 
				&& parentHeight >= 0 && parentHeight <=30);
		int highestOneBitMask = 1;
		for(; parentHeight > 0; parentHeight--){
			highestOneBitMask <<= 1;
		}
		
		return (parentWebId^highestOneBitMask);
	}
	
	/**
	 * Convenience method, see calculateChildWebId(int,int).
	 * @param parent The parent node.
	 * @pre parent != null AND the preconditions for calculateChildWebId(int,int).
	 * @post See postconditions for calculateChildWebId(int,int).
	 */
	public static int calculateChildWebId(Node parent){
		assert (parent != null);
		return calculateChildWebId(parent.getWebIdValue(), parent.getHeight());
	}
	
	public static int calculateParentWebId(int childWebId, int childHeight){
		return calculateChildWebId(childWebId, childHeight - 1);
	}
	
	/**
	 * Calculates the WebId of the insertion point from the WebId of the last node in the
	 * HyPeerWeb, also known as the deletion point.
	 * @param lastNodeWebId The WebId of the last node in the HyPeerWeb.
	 * @return The WebId of the insertion point.
	 * @pre lastNodeWebId >= 0
	 * @post result = insertionPointWebId
	 */
	public static int calculateInsertionPointWebId(int lastNodeWebId){
		assert (lastNodeWebId >= 0);
		if(lastNodeWebId == 0 || lastNodeWebId == 1 || isCapNodeWebId(lastNodeWebId)){
			return 0;
		}
		else{
			return (calculateSurrogateWebId(lastNodeWebId) + 1);
		}
	}
	
	/**
	 * Tests a webId to see if it is a cap node or not. Note that the given webId is assumed to be 
	 * the largest node in the web.
	 * @param webId The webId to test.
	 * @return True if the webId is a cap node; false otherwise.
	 * @pre The webId is assumed to be the highest webId in the HyPeerWeb; webId >= 0.
	 * @post result = (webId is cap node? true: false)
	 */
	public static boolean isCapNodeWebId(int webId){
		assert (webId >= 0);
		
		if(webId == 0){
			return true;
		}
		else{
			int target = (calculateHighestOneBitMask(webId) << 1) - 1;
			return (target == webId? true: false);
		}
	}
	
	/**
	 * Calculates the height of the given webId. This is the number of bits from the 
	 * leading "1" bit to the last bit on the right. Example: 001001100 has a height of 7.
	 * @param webId The webId to find the height of.
	 * @return The height of the given webId.
	 * @pre webId >= 0
	 * @post result = webId height
	 */
	public static int calculateWebIdHeight(int webId){
		assert (webId >= 0);
		
		int digitsToLeadingOneBit = 0;
		while(webId > 0){
			digitsToLeadingOneBit++;
			webId >>>= 1;
		}
		
		return digitsToLeadingOneBit;
	}
	
	/**
	 * Calculates a bit mask for the highest "1" bit in the number given.
	 * @param number The number to create a bit mask with.
	 * @return The highest one bit mask.
	 * @pre number >= 0
	 * @post result = highestOneBitMask
	 */
	public static int calculateHighestOneBitMask(int number){
		assert (number >= 0);
		
		if(number == 0){
			return number;
		}
		
		int digitsToLeadingOneBit = calculateWebIdHeight(number);
		
		int highestOneBitMask = 1;
		for(; digitsToLeadingOneBit > 1; digitsToLeadingOneBit--){
			highestOneBitMask <<= 1;
		}
		
		return highestOneBitMask;
	}
	
	/**
	 * Calculates a bit mask for the lowest "1" bit in the number given.
	 * @param number The number to create a bit mask with.
	 * @return The lowest one bit mask.
	 * @pre number >= 0
	 * @post result = lowestOneBitMask
	 */
	public static int calculateLowestOneBitMask(int number){
		assert (number >= 0);
		
		if(number == 0){
			return number;
		}
		
		int lowestOneBitMask = 1;
		int difference = number^lowestOneBitMask;
		while(difference > number){
			lowestOneBitMask <<= 1;
			difference = number^lowestOneBitMask;
		}
		
		return lowestOneBitMask;
	}
	
	/**
	 * Calculates the next jump to make in the HyPeerWeb.
	 * @param currentWebId The webId of the node you are currently at.
	 * @param destinationWebId The webId of the node you want to get to.
	 * @return The next jump in the web that needs to be made.
	 * @pre Both inputs are non-negative integers.
	 * @post The webId of the next node to jump to.
	 */
	public static int calculateNextJumpWebId(int currentWebId, int destinationWebId){
		assert (currentWebId >=0 && destinationWebId >= 0);
		
		int bitMask;
		int bitwiseDifference = currentWebId^destinationWebId;
		if(currentWebId > destinationWebId){
			bitMask = calculateHighestOneBitMask(bitwiseDifference);
		}
		else{
			bitMask = calculateLowestOneBitMask(bitwiseDifference);
		}
		return (currentWebId^bitMask);
	}
	
	/**
	 * Calculates all of the neighbors you should broadcast to.
	 * @param webId The webId of the node to broadcast from.
	 * @param height The height of the node to broadcast from.
	 * @pre Valid webId and height >= 0; also, height < 32. 
	 * Note that height is only necessary if webId == 0.
	 * Also, the broadcast algorithm assumes that the broadcast started at node zero, otherwise
	 * not all nodes will be broadcasted to.
	 * @post An integer array of all of the nodes' webIds to broadcast to. 
	 * Arranged in ascending order.
	 */
	public static int[] calculateBroadcastWebIds(int webId, int height){
		assert (webId >= 0 && height >= 0 && height < 32);
		
		int[] result = null;
		
		//calculate number of webIds there will be
		if(webId == 0){
			result = new int[height];
		}
		else{
			result = new int[Integer.numberOfTrailingZeros(webId)];
		}
		
		//calculate the webIds
		int bitMask = 1;
		for(int i = 0; i < result.length; i++){
			result[i] = webId^bitMask;
			bitMask <<= 1;
		}
		
		return result;
	}
}
