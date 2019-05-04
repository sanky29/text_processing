//define double linked list
public class dll<E> {

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
