import java.util.ArrayList;
import java.util.List;

public class Node<N>{
	
		private List<Node<N>> lNodesAdj;
	
		private N elem;
		
		boolean marcado;
		
		public Node(N node){
			elem = node;
			lNodesAdj = new ArrayList<Node<N>>();
		}
		
		public boolean isMarcado() {
			return marcado;
		}
		
		public void setMarcado(boolean marcado) {
			this.marcado = marcado;
		}
		
		public void addAdj(Node<N> node){
			if(!lNodesAdj.contains(node)){
				lNodesAdj.add(node);
			}
		}
		
		public List<Node<N>> getlNodesAdj() {
			return lNodesAdj;
		}
		
		public N getElem() {
			return elem;
		}
		
		public void setElem(N elem) {
			this.elem = elem;
		}
		
		public void remove(int i){
			if(lNodesAdj.size()>i && i>=0){
				lNodesAdj.remove(i);
			}
		}
		
		public String printListAdj() {
			String lista = elem.toString() + " = {";
			for (Node<N> aux : lNodesAdj) {
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