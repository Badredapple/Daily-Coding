//单词查找树
public class TrieST<Value>
{
	private static int R = 256;
	private Node root;

	private static class Node
	{	
		private Object val;
		private Node[] next = new Node[R];
	}

	public Value get(String key)
	{
		Node x = get(root, key, 0);
		if( x == null)	return null;
		return (Value) x.val;
	}
	
	private Node get(Node x , String key, int d)
	{	//返回以x作为根节点的子单词查找树种与key相关的项
		if(x == null ) 	return null;
		if(d == key.length())	return x;
		char c = key.charAt(d);	
		return get(x.next[c], key, d+1);
	}

	public void put(String key, Value val)
	{	root = put(root, key, val, 0);	}

	private Node put(Node x, String key, Value val, int d)
	{
		if(x == null) x =new Node();
		if(x == key.length()) 	{	x.val = val; return x;   }
		char c = key.charAt(d);
		x.next[c] = put(x.next[c], key, val, d+1);
		return x;
	}
}
//	通配符匹配
public Iterable<String> keysThatMatch(String pat)
{
	Queue<String> q = new Queue<String>();
	collect(root, " ", pat,q);
	return q;
}

private void collect(Node x, String pre, String pat, Queue<String> q)
{	
	int d = pre.length();
	if(x == null)	 return null;
	if(d == pat.length() && x.val != null) q.enqueue(pre);
	
	char next = pat.charAt(d);
	for(char c=0; c<R; c++)
		if(next == '.'|| next == c)
			collect(x.next[c], pre + c, pat,q);
}

//最长前缀匹配
public String longestPrefixOf(String s)
{	
	int length search(root, s, 0, 0)
	return s.substring(0,length);
}

private int search(Node x, String xs, int d, int length)
{

