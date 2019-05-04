//will implement suffix trie here
import java.util.Scanner;
import java.util.Queue;
import java.util.LinkedList;
import java.io.*;
public class trie {

	//define double linked list node
	public static class dnode<E> {

		//define content
		public E element;
		
		//define previous node
		public dnode<E> prev;
		
		//define next node
		public dnode<E> next;
		
		//define constructor
		public dnode (E nelement){
			
			//just set element as
			element = nelement;
		}
		
	}
	//define double linked list
	public static class dll<E> {

		public int l;
		//define header and trailer
		public dnode<E> header = new dnode<>(null);
		
		//define trailer and trailer
		public dnode<E> trailer = new dnode<>(null);
		
		//define function to add
		public void add(dnode<E> new_element) {
			
			//just add to header
			//define temp as node next to header
			dnode<E> temp = header.next;
			
			//just change prev of temp
			temp.prev = new_element;
			
			//just change next of new_element
			new_element.next = temp;
			
			//change prev of new_element
			new_element.prev = header;
			
			//change next of header
			header.next = new_element; 
			l++;
		}
		
		//delete element
		public void delete(E thing) {
			
			//search for it
			dnode<E> temp = header;
			
			//run wile loop
			while(temp.element != thing && temp != trailer) {
				
				//just go for next
				temp = temp.next;
			}
			//if temp is not trailer do following
			if (temp != trailer) {
				
				//just change the things as
				temp.next.prev = temp.prev;
				
				//and
				temp.prev.next = temp.next; 
				l--;
			}
			//else throw exception
			else {
				
				//throw
				throw new NullPointerException("element is not there");
			}
		}
		
		//define constructor
		public dll() {
			header.next = trailer;
			trailer.prev = header;
			l = 0;
		}
		//define constructor
		public dll(dnode<E> z) {
			header.next = z;
			z.prev = header;
			z.next = trailer;
			trailer.prev = z;
			l = 1;
		}

	}
	//this will represent node of trie
	public static class node {
		
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

	//just define array of data
	public char[] data;
	
	//define root array
	public node root;
	
	//define add function as
	public void addn(int start , int end) {
		
		
		//define current pointer as
		int pointer = start;
		
		//define current node as 
		node curr = root;
		//go for while loop
		while(pointer <= end && curr!= null) {
			//define tchild as
			node tchild = curr.children[(int) data[pointer]];
			
			//check child of curr as
			if (tchild == null) {
				
				//define z as
				
				//define this dll as
				node temp = new node( pointer,end);
				
				//add start parent to parent node
				temp.parent = curr;
				
				temp.depth = curr.depth + curr.len;
				//add child as
				 curr.children[(int) data[pointer]] = temp;
				 
				//turncate while loop
				pointer = end + 1;
				
			}
			//else get cases of matches
			else {
			
				//now else start scanning like
				//define new pointer as
				int i = tchild.occur.header.next.element[0];
			
				//define integer as
				int counter = 0;
				
				//scan one one character
				while(i <= tchild.occur.header.next.element[1] && pointer <= end) {
					//match the pointer and i
					if (data[i] == data[pointer]) {
						
						pointer++;
						i++;
						counter++;
					}
					else {
						 //turnicate loop
						i = i + 1 + tchild.len;
					}
				}
				
				//now we got counter
				//if counter is end then
				if (counter == tchild.len) {
					
					//check two cases
					if (pointer > end) {
						
						//just add children
						//just add node to child
						tchild.occur.add(new dnode<int[]>(new int[] {end - tchild.len + 1 , end}));
						
						//change pointer as
						pointer = end + 1;
					}
					//pointer can less than end
					else  {
						
						//just add the sequence to tchild
						tchild.occur.add(new dnode<int[]>(new int[] {pointer - tchild.len , pointer - 1}));
						curr = tchild;
					}
				}
							//this case is which matches all pointer
				else {
				
					
						//just change end as
						dnode<int[]> temp = tchild.occur.header.next;
				
						//define int as
						int tempi = counter; 
						//define new int as
						dll<int[]> u = new dll<>();
						//change element of temp
						while(temp != tchild.occur.trailer) {
							//make changes in dll
							
							u.add(new dnode<int[]>(new int[] {temp.element[0] ,temp.element[0] + tempi - 1 }));
							//do as follows
							temp.element[0] = temp.element[0] + tempi ;	
							temp = temp.next;
						}
						//make new node as
						node p = new node();
						p.occur = u;
						//change len as
						p.children[(int) data[tchild.occur.header.next.element[0]]] = tchild;
						p.parent = tchild.parent;
						tchild.parent.children[(int) data[p.occur.header.next.element[0]]] = p;
						tchild.parent = p;
						//change lenght
						p.len = counter;
				
						tchild.len = tchild.len - counter;
						p.occur.add(new dnode<int[]>(new int[] {pointer - counter , pointer - 1}) );
						temp = p.occur.header.next;
						if (pointer > end) {
						pointer = end + 1;}
						else {
							curr = p;
							
						}
					}
				}
				
		}
	}

		
	public static void adds (dll<int[]> list, int[] element) {
		dnode<int[]> temp = list.header.next;
		while(temp != list.trailer && (element[0] > temp.element[0] || (element[0] == temp.element[0] && element[1] > temp.element[1]))) {
			temp = temp.next;
		}
		dnode<int[]> x = new dnode<>(element);
		x.prev = temp.prev;
		x.next = temp;
		temp.prev.next = x;
		temp.prev = x;
	
		list.l++;
		
	}
	public void search(char[] query, PrintStream o) {
		//first find *
	
				int divison = 0;
				int number_of_last_element = 1;
				Queue<node> back = new LinkedList<>();
				Queue<node> front = new LinkedList<>();
				Queue<Integer> backi = new LinkedList<>();
				Queue<Integer> fronti = new LinkedList<>();
				node temp2;
				int i2 = 1;
				back.add(root);
				front.add(root);
				backi.add(0);
				fronti.add(0);
	
				while (divison <= query.length - 1 && query[divison] != '*' ) {
					
					//see the matching thing
					divison ++;
				}
				int a = divison -1 ;
				int b = divison + 1;

				if (a >= 0) {
				for (int i = 0 ; i <= a ; i ++) {
					if (query[i] == '?') {	
						for(int j = 1 ; j <= number_of_last_element ; j ++) {
						
							temp2 = back.poll();
							i2 = backi.poll();
			
							if (i2 < temp2.len) {
				
								back.add(temp2);
								backi.add(i2 + 1);
							}
							else {
								for (int k = 0; k<= 254; k++) {
									if (temp2.children[k] != null) {
										
										back.add(temp2.children[k]);
										backi.add(1);
									}
								}
							}
						}
						number_of_last_element = back.size();
					}
					else {
						
						for(int j = 1 ; j <= number_of_last_element ; j ++) {
							temp2 = back.poll();
							i2 = backi.poll();
							if (i2 < temp2.len) {
								if (data[temp2.occur.header.next.element[0] + i2] == query[i]) {
								back.add(temp2);
								backi.add(i2 + 1);
							}}
							else {
								if (temp2.children[(int) query[i]] != null) {
									back.add(temp2.children[(int) query[i]]);
									backi.add(1);
								}
							}
						}
						number_of_last_element = back.size(); 
						
					}
				
				
					}
				}
				if (b <= query.length - 1) {
					
					number_of_last_element = 1;
					for (int i = b ; i <= query.length - 1; i ++) {
						
						if (query[i] == '?') {
							
							for(int j = 1 ; j <= number_of_last_element ; j ++) {
								temp2 = front.poll();
								i2 = fronti.poll();
							
								if (i2 < temp2.len) {
									front.add(temp2);
									fronti.add(i2 + 1);
								}
								else {
									
									for (int k = 0; k<= 254; k++) {
										if (temp2.children[k] != null) {
											front.add(temp2.children[k]);
											fronti.add(1);
										}
									}
								}
							}
							number_of_last_element = front.size(); 
				
						}
						else {
							
							for(int j = 1 ; j <= number_of_last_element ; j ++) {
								temp2 = front.poll();
								i2 = fronti.poll();
								if (i2 < temp2.len) {
									
									if (data[temp2.occur.header.next.element[0] + i2] == query[i]) {
									front.add(temp2);
									fronti.add(i2 + 1);
								}}
								else {
								
									if (temp2.children[(int) query[i]] != null) {
										front.add(temp2.children[(int) query[i]]);
										fronti.add(1);
									}
								}
							}
							number_of_last_element = front.size(); 
						}
						}
				
					}
		
				int y;
				
				dnode<int[]> temp3 ;
				node temp4;
				//make two array as
				dll<int[]> ba = new dll<>();
				dll<int[]> fr = new dll<>();
				while (back.isEmpty() != true) {
				
					y = backi.poll();
					temp4 =back.poll();
					
					temp3 = temp4.occur.trailer.prev;
					
					if (query[query.length - 1] != '*') {

					while (temp3 != temp4.occur.header) {
					
						adds(ba,(new int[] {temp3.element[0] + y - a - 1 , temp3.element[0] + y - 1}));
						temp3 = temp3.prev;
					}}
					else {
					
						while (temp3 != temp4.occur.header) {
							for (int i = data.length - 1 ; i >= temp3.element[0] + y - 1 ; i --) {
							adds(ba,(new int[] {temp3.element[0] + y - a - 1 , i}));
						
						}
							temp3 = temp3.prev;}
					
					}
					
				}
				while (front.isEmpty() != true) {
					
					y = fronti.poll();
					temp4 =front.poll();
					temp3 = temp4.occur.trailer.prev;
				
					if (query[0] != '*') {
					while (temp3 != temp4.occur.header) {
			
						adds(fr,(new int[] {temp3.element[0] + y + b - query.length , temp3.element[0] + y - 1}));
						temp3 = temp3.prev;
					}}
					else {
						while (temp3 != temp4.occur.header) {
							for (int i = temp3.element[0] + y + b - query.length ; i >= 0 ; i --) {
							adds(fr,(new int[] {i , temp3.element[0] + y - 1}));
							
						}
							temp3 = temp3.prev;}
					}
				}
				temp3 = ba.header.next;
				dnode<int[]> temp5 = fr.header.next;
				if (ba.l != 0 && fr.l != 0) {
				while(temp3 != ba.trailer) {	
				while(temp5 != fr.trailer) {
				
					if (temp5.element[0] > temp3.element[1] ) {
						o.println(temp3.element[0] + " " + temp5.element[1]);
					}
					temp5 = temp5.next;
				}
				temp3 = temp3.next;
				temp5 = fr.header.next;
				}}
				else if (ba.l != 0) {
					while(temp3 != ba.trailer) {
						o.println(temp3.element[0] + " " + temp3.element[1]);
						temp3 = temp3.next;
					}
				}
				else if (fr.l != 0) {
					while(temp5 != fr.trailer) {
						o.println(temp5.element[0] + " " + temp5.element[1]);
						temp5 = temp5.next;
					}
				}
				if (query.length == 1 && query[0] == '*') {
					for (int i = 0 ; i <= data.length - 1; i ++) {
						for (int j = i ; j <= data.length - 1; j ++) {
							o.println(i + " " + j);
						}
					}
				}
				}

	
		//make constructor as
	public trie (String s) {
		data = s.toCharArray();
		root = new node();
	}
	public static void print(dll<int[]> x) {
		dnode<int[]> y = x.header.next;
		while (y != x.trailer) {
			
			System.out.println(y.element[0] + " " + y.element[1]);
			y = y.next;
		}
	}
	public static void printt(dll<int[]> x , char z , int yo) {
		dnode<int[]> y = x.header.next;
		while (y != x.trailer) {
			
			System.out.println((y.element[0] + yo - 2 )+ " " + (y.element[0] + yo - 1) + " " +z+ " " +yo);
			y = y.next;
		}
	}
	public static void main(String[] args) {
		try {
			FileInputStream input = new FileInputStream(args[0]);
			Scanner s = new Scanner(input);
			FileOutputStream output = new FileOutputStream(args[1]);
			PrintStream write = new PrintStream(output);
			String y = s.nextLine();
			int j = s.nextInt();
			trie t = new trie (y);
			y = s.nextLine();
			for (int i = 0 ; i <= t.data.length - 1; i++) {
				t.addn(i, t.data.length - 1);
			}
			for (int i = 0 ; i <= j -1; i++) {
				y= s.nextLine();
				System.out.println(y);
				t.search(y.toCharArray(),write);
			}
	
			s.close();
			write.close();
		}
		catch (FileNotFoundException e) {
			System.out.println("file not found");
		}
		
}
}
