import java.util.ArrayList;
import java.util.List;

public class Node<N>{
		private List<N> lNodesAdj;
		private N elem;
		public Node(N node){
			elem = node;
			lNodesAdj = new ArrayList<N>();
		}
		public void add(N node){
			if(!lNodesAdj.contains(node)){
				lNodesAdj.add(node);
			}
		}
		public List<N> getlNodesAdj() {
			return lNodesAdj;
		}
		public void setlNodesAdj(List<N> lNodesAdj) {
			this.lNodesAdj = lNodesAdj;
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
			for (N aux : lNodesAdj) {
				lista+=" ";
				lista+=aux.toString();
			}
			lista+=" }";
			return lista;
		}
	}