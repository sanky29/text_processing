//this will represent node of trie
public class node {
	
	//define depth as
	public int depth;
	
	//define parent as
	public node parent;
	
	//the children
	public node[] children = new node[255];
	
	//also store number of character here
	public int len;
	
	//the occurrence of node
	public dll<int[]> occur;
	
	//define constructor as
	public node( int start, int end) {
		len = end - start + 1;
		occur = new dll<int[]>();
		occur.add(new dnode<int[]>(new int[]{start,end}));
		depth = 0;
	}
	
	//need to make root condition
	public node() {
	
		len = 0;
		occur = new dll<int[]>();
		parent = null;
		depth = 0;
	}
}
