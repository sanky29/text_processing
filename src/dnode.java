//define double linked list node
public class dnode<E> {

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
