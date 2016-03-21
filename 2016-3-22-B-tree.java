/*   This is The API of a B- tree
	public class	 Page<key>
			 Page(boolean bottom)
		void 	close()
       		void 	add(Key key)
		void	add(Page p)
		
	     boolean 	isExternal()
	     boolean 	contains(Key key)
	        Page    next(key key)
    	     boolean 	isFull()
		Page	split()
   	Iterable<key>	keys()

*/

//Now I will write some functions
//like add(Key key); add(Page p)


public class BTreeSET<key extends Comparable <key>>
{	
	private Page root = new Page(ture);

	public BTreeSET(key sentinel)
	{	add(sentinel);	}

	public boolean contains(Key key)
	{	return contains(root, key);	}

	private boolean contains(Page h , Key key)
	{
		if (h.isExternal())	return h.contains(key);
		return contains(h.next(key), key);
	}

	public void add(Key key)
	{
		add(root, key)
		if(root.isFull())
		{
			Page lethalf = root;


