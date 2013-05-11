/**
 * 
 */
package utilities;

/**
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
	 * @pre An integer greater than or equal to one.
	 * @post result = webId's surrogate
	 */
	public static int calculateSurrogateWebId(int webId){
		int temp = webId;
		int digitsToLeadingOneBit = 0;
		while(temp > 0){
			digitsToLeadingOneBit++;
			temp >>>= 1;
		}
		
		int highestOneBitMask = 1;
		for(; digitsToLeadingOneBit > 1; digitsToLeadingOneBit--){
			highestOneBitMask <<= 1;
		}
		
		return (webId^highestOneBitMask);
	}
	
	/**
	 * Finds the child's webId from the parent's webId. Note that the height of the parent must
	 * be one less than that of the child. This method is intended to be called before the 
	 * <code>addChild</code> method is called.
	 * @param parentWebId The webId of the parent node.
	 * @param parentHeight The height of the parent node (one less than that of the child node).
	 * @return The webId of the child node.
	 * @pre parentWebId >= 0, parentHeight >= 0
	 * @post result = child's webId
	 */
	public static int calculateChildWebId(int parentWebId, int parentHeight){
		int highestOneBitMask = 1;
		for(; parentHeight > 0; parentHeight--){
			parentHeight <<= 1;
		}
		
		return (parentWebId^highestOneBitMask);
	}
}
