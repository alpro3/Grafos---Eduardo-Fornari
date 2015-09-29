import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class GRAPHDIRLISTADJ <N> implements TAD{
	private List<Node<N>> list;
	public GRAPHDIRLISTADJ(int max) {
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
	
	public boolean addEdge(Object a, Object b) {
		int i = 0;
		boolean existeA = false, existeB = false;
		int indexA = 0, indexB = 0;
		while(i<list.size() && (existeA==false || existeB==false)){
			if(list.get(i).getElem().equals((N)a)){
				existeA = true;
				indexA = i;
			}
			if(list.get(i).getElem().equals((N)b)){
				existeB = true;
				indexB = i;
			}
			i++;
		}
		if(existeA && existeB){
			list.get(indexA).add(list.get(indexB).getElem());
			return true;
		}
		return false;
	}
	
	public boolean removeEdge(Object a, Object b) {
		int i = 0;
		boolean existeA = false, existeB = false;
		int indexA = 0;
		while(i<list.size() && existeA==false){
			if(list.get(i).getElem().equals((N)a)){
				existeA = true;
				indexA = i;
			}
			i++;
		}
		if(existeA){
			ArrayList<N> listElem = (ArrayList<N>) list.get(indexA).getlNodesAdj();
			int index = -1;
			for (int j = 0; j < listElem.size(); j++) {
				if(listElem.get(j).equals((N)b)){
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
		int i = 0;
		boolean existeA = false, existeB = false;
		int indexA = 0;
		while(i<list.size() && existeA==false){
			if(list.get(i).getElem().equals((N)a)){
				existeA = true;
				indexA = i;
			}
			i++;
		}
		if(existeA){
			ArrayList<N> listElem = (ArrayList<N>) list.get(indexA).getlNodesAdj();
			for (int j = 0; j < listElem.size(); j++) {
				if(listElem.get(j).equals((N)b)){
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean removeElem(Object a){
		ArrayList<N> lAUX;
		for(Node<N> aux : list){
			if(aux.getElem().equals((N)a)){
				list.remove(aux);
				break;
			}
			else{
				lAUX = (ArrayList<N>)aux.getlNodesAdj();
				for(int i = 0; i<lAUX.size();){
					if(lAUX.get(i).equals((N)a)){
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
	
	public int existe(Object a){
		for (int i = 0; i<list.size(); i++) {
			if(list.get(i).getElem().equals((N)a)){
				return i;
			}
		}
		return -1;
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
	
	public static void main(String[] args) {
		GRAPHDIRLISTADJ<Integer> Grafo = new GRAPHDIRLISTADJ<Integer>(10){};
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
		lista = (ArrayList<Node<Integer>>)Grafo.getList();
		for (Node<Integer> node : lista) {
			System.out.println(node.printListAdj());
		}
		System.out.println("Existe aresta 1 ->  2 ? "+Grafo.existEdge(1, 2));
	}

	public List<Node<N>> getList() {
		return list;
	}

	public void setList(List<Node<N>> list) {
		this.list = list;
	}
}