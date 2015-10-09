import java.util.ArrayList;
import java.util.List;

public class Node<E>{
		
		private List<Node<E>> lNodesAdj;
	
		private E elem;
		
		boolean marcado;
		
		public Node(E node){
			elem = node;
			lNodesAdj = new ArrayList<Node<E>>();
		}
		
		public boolean isMarcado() {
			return marcado;
		}
		
		public void setMarcado(boolean marcado) {
			this.marcado = marcado;
		}
		
		public void addAdj(Node<E> node){
			if(!lNodesAdj.contains(node)){
				lNodesAdj.add(node);
			}
		}
		
		public List<Node<E>> getlNodesAdj() {
			return lNodesAdj;
		}
		
		public E getElem() {
			return elem;
		}
		
		public void setElem(E elem) {
			this.elem = elem;
		}
		
		public void remove(int i){
			if(lNodesAdj.size()>i && i>=0){
				lNodesAdj.remove(i);
			}
		}
		
		public String printListAdj() {
			String lista = elem.toString() + " = {";
			for (Node<E> aux : lNodesAdj) {
				lista+=" ";
				lista+=aux.toString();
			}
			lista+=" }";
			return lista;
		}
		
		public String toString (){
			return elem.toString();
		}
	}