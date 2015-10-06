import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class GRAPHDIRLISTADJ <N> implements TAD{
	private List<Node<N>> list;
	
	public GRAPHDIRLISTADJ() {
		list = new ArrayList<Node<N>>();
	}

	public boolean addNode(Object a) {
		for(Node<N> aux : list){
			if(aux.getElem().equals((N)a)){
				return false;
			}
		}
		list.add(new Node<N>((N)a));
		return true;
	}
	
	public void desmarcaNodos(){
		for(Node<N> aux : list){
			aux.setMarcado(false);
		}
	}
	
	public List<N> caminhamentoLargura(N elem) throws IllegalAccessException{
		List<N> result = new ArrayList<N>();
		Queue<Node<N>> fAux = new LinkedList<Node<N>>();
		Node<N> nAUX;
		int index = this.indexItem(elem);
		if(index==-1){
			throw new IllegalAccessException("Elemento invalido");
		}
		else{
			this.desmarcaNodos();
			result.add(list.get(index).getElem());
			fAux.offer(list.get(index));
			list.get(index).setMarcado(true);
			while(!fAux.isEmpty()){
				nAUX = fAux.poll();
				ArrayList<Node<N>> l = (ArrayList<Node<N>>)getAdjacents(nAUX.getElem());
				for(Node<N> v : l){
					if(!v.isMarcado()){
						result.add(v.getElem());
						v.setMarcado(true);
						fAux.offer(v);
					}
				}
			}
		}
		return result;
	}
	
	public List<N> caminhamentoProfundidade(N elem) throws IllegalAccessException{
		int index = this.indexItem(elem);
		if(index==-1){
			throw new IllegalAccessException("Elemento invalido");
		}
		else{
			desmarcaNodos();
			ArrayList<N> result = new ArrayList<N>();
			caminhamentoProfundidade(list.get(index),result);
			return result;
		}
	}
	
	public void caminhamentoProfundidade(Node<N> nodo, List<N> list){
		list.add(nodo.getElem());
		nodo.setMarcado(true);
		for (Node<N> aux : nodo.getlNodesAdj()) {
			if(aux.isMarcado()==false)caminhamentoProfundidade(aux, list);
		}
	}
	
	private int indexItem(N elem) {
		for(Node<N> aux : list){
			if(aux.getElem().equals(elem))return list.indexOf(aux);
		}
		return -1;
	}

	public boolean addEdge(Object a, Object b) {
		int i = 0;
		int indexA = indexItem((N)a), indexB = indexItem((N)b);
		if(indexA!=-1 && indexB!=-1){
			list.get(indexA).addAdj(list.get(indexB));
			return true;
		}
		return false;
	}
	
	public boolean removeEdge(Object a, Object b) {
		int indexA = indexItem((N)a), indexB = indexItem((N)b);
		if(indexA!=-1 && indexB!=-1){
			ArrayList<Node<N>> listAdj = (ArrayList<Node<N>>) list.get(indexA).getlNodesAdj();
			int index = -1;
			for (int j = 0; j < listAdj.size(); j++) {
				if(listAdj.get(j).getElem().equals((N)b)){
					index = j;
					break;
				}
			}
			list.get(indexA).remove(index);
			return true;
		}
		return false;
	}
	
	public boolean existEdge(Object a, Object b) {
		int indexA = indexItem((N)a);
		if(indexA!=-1){
			ArrayList<Node<N>> listElem = (ArrayList<Node<N>>) list.get(indexA).getlNodesAdj();
			for (Node<N> aux : listElem) {
				if(aux.getElem().equals((N)b)){
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean removeElem(Object a){
		ArrayList<Node<N>> lAUX;
		for(Node<N> aux : list){
			if(aux.getElem().equals((N)a)){
				list.remove(aux);
				break;
			}
			else{
				lAUX = (ArrayList<Node<N>>)aux.getlNodesAdj();
				for(int i = 0; i<lAUX.size();){
					if(lAUX.get(i).getElem().equals((N)a)){
						aux.remove(i);
						break;
					}
				}
			}
		}
		return false;
	}
	
	public void printLista() {
		for(Node<N> aux : list){
			System.out.print(aux.getElem().toString()+" ");
		}
		System.out.println();
	}
	
	public int degree(Object node) {
		int cont = 0;
		int index = -1;
		for(Node<N> aux : list){
			if(aux.getElem().equals((N)node)){
				index = list.indexOf(aux);
				cont+=list.get(index).getlNodesAdj().size();
			}
		}
		if(index>=0){
			ArrayList<N> listaAUX;
			for (Node<N> aux : list) {
				listaAUX = (ArrayList<N>)aux.getlNodesAdj();
				if(listaAUX.contains((N)node))cont++;
			}
		}
		return cont;
	}

	public boolean isReachable(Object node) {
		ArrayList<N> listaAUX;
		for (Node<N> aux : list) {
			listaAUX = (ArrayList<N>)aux.getlNodesAdj();
			if(listaAUX.contains((N)node))return true;
		}
		return false;
	}

	public List getAdjacents(Object node) {
		for(Node<N> aux : list){
			if(aux.getElem().equals((N)node)){
				return aux.getlNodesAdj();
			}
		}
		return null;
	}
	public List<Node<N>> getList() {
		return list;
	}
	public void setList(List<Node<N>> list) {
		this.list = list;
	}
	public static void main(String[] args) throws IllegalAccessException {
		GRAPHDIRLISTADJ<Integer> Grafo = new GRAPHDIRLISTADJ<Integer>();
		Grafo.addNode(1);
		Grafo.addNode(2);
		Grafo.addNode(3);
		Grafo.addEdge(1, 2);
		Grafo.addEdge(1, 3);
		Grafo.addEdge(2, 3);
		System.out.println("Adicionando aresta do nodo \'1\' para o nodo \'3\': "+Grafo.addEdge(1, 3));
		System.out.println("Adicionando aresta do nodo \'1\' para o nodo \'4\': "+Grafo.addEdge(1, 4));
		System.out.print("Lista: ");
		Grafo.printLista();
		System.out.println("ListAdj:");
		ArrayList<Node<Integer>> lista = (ArrayList<Node<Integer>>)Grafo.getList();
		for (Node<Integer> node : lista) {
			System.out.println(node.printListAdj());
		}
		System.out.println("Grau do elemento 1: "+Grafo.degree(1));
		System.out.println("Elemento 1 é alcançavel? "+Grafo.isReachable(1));
		System.out.println("Lista de adjacentes do elemento 1: "+Grafo.getAdjacents(1));
		System.out.println("Existe aresta 1 ->  2 ? "+Grafo.existEdge(1, 2));
		Grafo.removeElem(2);
		System.out.print("Lista: ");
		Grafo.printLista();
		System.out.println("ListAdj:");
		Grafo.addNode(4);
		Grafo.addNode(5);
		Grafo.addEdge(1, 3);
		Grafo.addEdge(3, 4);
		Grafo.addEdge(4, 5);
		Grafo.addEdge(3, 5);
		
		GRAPHDIRLISTADJ<String> grafo2 = new GRAPHDIRLISTADJ<String>();
		grafo2.addNode("A");
		grafo2.addNode("B");
		grafo2.addNode("C");
		grafo2.addNode("D");
		grafo2.addNode("E");
		grafo2.addNode("F");
		grafo2.addNode("G");
		grafo2.addNode("J");
		grafo2.addEdge("A", "B");
		grafo2.addEdge("A", "C");
		grafo2.addEdge("A", "D");
		grafo2.addEdge("B", "F");
		grafo2.addEdge("F", "G");
		grafo2.addEdge("B", "C");
		grafo2.addEdge("C", "E");
		grafo2.addEdge("C", "A");
		grafo2.addEdge("G", "E");
		grafo2.addEdge("D", "E");
		grafo2.addEdge("D", "A");
		grafo2.addEdge("J", "D");
		
		
		System.out.println("Existe aresta 1 ->  2 ? "+Grafo.existEdge(1, 2));
		System.out.println("Caminhamento em largura partindo do 1: "+grafo2.caminhamentoLargura("A").toString());
		System.out.println("Caminhamento em profundidade partindo do 1: "+grafo2.caminhamentoProfundidade("A").toString());
		ArrayList<Node<String>> lista2 = (ArrayList<Node<String>>)grafo2.getList();
		for (Node<String> node : lista2) {
			System.out.println(node.printListAdj());
		}
	}
}