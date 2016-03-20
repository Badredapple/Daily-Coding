public class KMP
{
	private String pat;
	private int[][] dfa;
	public KMP(String pat)
	{
		this.pat = pat;
		int M = pat.length();
		int R = 250;
		dfa = new int[R][M];
		dfa[pat.charAt(0)][0] = 1;
		for( int X = 0 ; j = 1; j < M ; j++)
		{
			for(int c = 0;c < R; c++)
				dfa[c][j] = dfa[c][X];
			dfa[pat.charAt(j)][j] = j+1;
			X = dfa[pat.charAt(j)][X];
		}
	}
	public int search(String txt)
	{
		int i,j, N = txt.length(), M = pat.length();
		for( i = 0, j = 0; i < N && j<M; i++)
			j = dfa[txt.charAt(i)][j];
		if( j == M )  return i-N;
		else	      return N;
	}
	public static void main(String[] args)
	{
		String pat = args[0];
		String txt = args[1];
		KMP kmp = new KMP(pat);
		StdOut.println("text:      " + text);
		int offset = kmp.search(text);
		StdOut.print("pattern: ");
		for(int i = 0; i < offset; i++)
			StdOut.print(" ");
		StdOut.println(pat);
	}
}

// This is how to creat a DFA 
	dfa[pat.charAt(0)][0] = 1;
		for( int X = 0 ; j = 1; j < M ; j++)
		{
			for(int c = 0;c < R; c++)
				dfa[c][j] = dfa[c][X];
			dfa[pat.charAt(j)][j] = j+1;
			X = dfa[pat.charAt(j)][X];
		}


//now We will discuess another algs:
//This one called  Boyer-Moore algorithms:

public class BoyerMoore
{
	private int [] right;
	private String pat;
	BoyerMoore(String pat)
	{	//计算跳跃表
		this.pat =pat;
		int M = pat.length();
		int R = 256;
		right = new int[R];
		for(int c = 0; c<R ;c++)
			right[c]=-1;
		for(int j = 0; j < M;j++)
			right[pat.charAt(j)] = j;
	}

	public int search(String txt)
	{	//在TXT中查找字符串和文本匹配的模式字符串
		int N = txt.length();
		int M =pat.length();
		int skip;
		for( int j = M-1; j >=N-M; i += skip)
		{	//模式字符串和文本在i位置能够匹配么？
			skip = 0;
			for(int j = M-1; j >= 0; j--)
				if(pat.charAt(j) != txt.charAt(i+j))
				{
					skip = j - right[txt.charAt(i+j)];
					if(skip < 1) skip =1;
					break;
				}
			if( skip == 0 ) return i ;
		}
		return N;
	}
	public static void main(String[] args)
	{
		String pat = args[0];
		String txt = args[1];
		KMP kmp = new KMP(pat);
		StdOut.println("text:      " + text);
		int offset = kmp.search(text);
		StdOut.print("pattern: ");
		for(int i = 0; i < offset; i++)
			StdOut.print(" ");
		StdOut.println(pat);
	}
}
