import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class GRAPHDIRMATADJ <N> implements TAD{
	public class Nodo<E>{
		E elem;
		boolean marcado;
		public Nodo(E elem){
			this.elem = elem;
		}
		public E getElem() {
			return elem;
		}
		public void setElem(E elem) {
			this.elem = elem;
		}
		public boolean isMarcado() {
			return marcado;
		}
		public void setMarcado(boolean marcado) {
			this.marcado = marcado;
		}
		public String toString(){
			return elem.toString();
		}
	}
	int limit;
	private List<Nodo<N>> lNodes;
	private boolean matAdj [][];
	public GRAPHDIRMATADJ(int max) {
		limit = max;
		lNodes = new ArrayList<Nodo<N>>();
		matAdj = new boolean[max][max];
	}

	public boolean addNode(Object a) {
		if(lNodes.size()<limit){
			for(Nodo<N> aux : lNodes){
				if(aux.getElem().equals((N)a)){
					return false;
				}
			}
			lNodes.add(new Nodo((N)a));
			return true;
		}
		return false;
	}
	
	private int indexItem(N elem) {
		for(Nodo<N> aux : lNodes){
			if(aux.getElem().equals(elem))return lNodes.indexOf(aux);
		}
		return -1;
	}
	
	public boolean addEdge(Object a, Object b) {
		int indexA = indexItem((N)a), indexB = indexItem((N)b);
		if(indexA!=-1 && indexB!=-1){
			matAdj[indexA][indexB] = true;
			return true;
		}
		return false;
	}
	
	public boolean removeEdge(Object a, Object b) {
		int indexA = indexItem((N)a), indexB = indexItem((N)a);
		if(indexA!=-1 && indexB!=-1){
			matAdj[indexA][indexB] = false;
			return true;
		}
		return false;
	}
	
	public boolean removeElem(Object a){
		for(Nodo<N> aux : lNodes){
			if(aux.getElem().equals((N)a)){
				int index = lNodes.indexOf(aux);
				for (int i = 0; i < matAdj.length; i++) {
					matAdj[i][index] = false;
				}
				for (int i = index-1; i < matAdj.length-1; i++) {
					matAdj[i] = matAdj[i+1];
				}
				lNodes.remove(aux);
				break;
			}
		}
		return false;
	}
	
	public boolean existeAresta(Object a, Object b) {
		int i = 0;
		boolean existeA = false, existeB = false;
		int indexA = 0, indexB = 0;
		while(i<lNodes.size() && (existeA==false || existeB==false)){
			if(lNodes.get(i).equals((N)a)){
				existeA = true;
				indexA = i;
			}
			if(lNodes.get(i).equals((N)b)){
				existeB = true;
				indexB = i;
			}
			i++;
		}
		if((existeA && existeB) && matAdj[indexA][indexB]==true){
			return true;
		}
		return false;
	}
	
	public void printLista() {
		for(Nodo<N> aux : lNodes){
			System.out.print(aux.getElem().toString()+" ");
		}
		System.out.println();
	}
	
	public List<Nodo<N>> getlNodes() {
		return lNodes;
	}

	public boolean[][] getMatAdj() {
		return matAdj;
	}

	public void setMatAdj(boolean[][] matAdj) {
		this.matAdj = matAdj;
	}

	public void printMatADJ() {
		for (int i = 0; i < matAdj.length; i++) {
			String linha = "";
			for (int j = 0; j < matAdj[0].length; j++) {
				if(matAdj[i][j]==true){
					linha+=1+" ";
				}
				else{
					linha+=0+" ";
				}
			}
			System.out.println(linha);
		}
	}
	
	public int degree(Object node){
		int cont = 0;
		int index = -1;
		for(Nodo<N> aux : lNodes){
			if(aux.getElem().equals((N)node)){
				index = lNodes.indexOf(aux);
				for(int i = 0; i<matAdj.length; i++){
					if(matAdj[index][i]){
						cont++;
					}
				}
			}
		}
		if(index>=0){
			for(int i=0;i<lNodes.size();i++){
				if(matAdj[i][index])cont++;
			}
		}
		return cont;
	}
	
	public boolean isReachable(Object node) {
		for(Nodo<N> aux : lNodes){
			if(aux.getElem().equals((N)node)){
				int index = lNodes.indexOf(aux);
				for (int i = 0; i < lNodes.size(); i++) {
					if(matAdj[i][index]){
						return true;
					}
				}
				return false;
			}
		}
		return false;
	}

	public List getAdjacents(Object node) {
		ArrayList<Nodo<N>> lista = new ArrayList<Nodo<N>>();
		for(Nodo<N> aux : lNodes){
			if(aux.getElem().equals((N)node)){
				int index = lNodes.indexOf(aux);
				for(int i = 0; i<lNodes.size();i++){
					if(matAdj[index][i]){
						lista.add(lNodes.get(i));
					}
				}
				return lista;
			}
		}
		return null;
	}
	
	public void desmarcaNodos(){
		for(Nodo<N> aux : lNodes){
			aux.setMarcado(false);
		}
	}
	
	public List<N> caminhamentoLargura(N elem) throws IllegalAccessException{
		List<N> result = new ArrayList<N>();
		Queue<Nodo<N>> fAux = new LinkedList<Nodo<N>>();
		Nodo<N> nAUX;
		int index = this.indexItem(elem);
		if(index==-1){
			throw new IllegalAccessException("Elemento invalido");
		}
		else{
			this.desmarcaNodos();
			result.add(lNodes.get(index).getElem());
			fAux.offer(lNodes.get(index));
			lNodes.get(index).setMarcado(true);
			while(!fAux.isEmpty()){
				nAUX = fAux.poll();
				ArrayList<Nodo<N>> l = (ArrayList<Nodo<N>>)getAdjacents(nAUX.getElem());
				for(Nodo<N> v : l){
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
			caminhamentoProfundidade(lNodes.get(index),result);
			return result;
		}
	}
	
	public void caminhamentoProfundidade(Nodo<N> nodo, List<N> list){
		list.add(nodo.getElem());
		nodo.setMarcado(true);
		int index = indexItem(nodo.getElem());
		for(int i=0;i<lNodes.size();i++){
			if(matAdj[index][i] && lNodes.get(i).isMarcado()==false)caminhamentoProfundidade(lNodes.get(i), list);
		}
	}	
	
	public static void main(String[] args) throws IllegalAccessException {
		GRAPHDIRMATADJ<Integer> Grafo = new GRAPHDIRMATADJ<Integer>(5){};
		Grafo.addNode(1);
		Grafo.addNode(2);
		Grafo.addNode(3);
		System.out.println("Adicionando aresta do nodo \'1\' para o nodo \'2\': "+Grafo.addEdge(1, 2));
		System.out.println("Adicionando aresta do nodo \'1\' para o nodo \'3\': "+Grafo.addEdge(1, 3));
		System.out.println("Adicionando aresta do nodo \'2\' para o nodo \'3\': "+Grafo.addEdge(2, 3));
		System.out.print("Lista: ");
		Grafo.printLista();
		System.out.println("MatAdj:");
		Grafo.printMatADJ();
		System.out.println("Grau do elemento 1: "+Grafo.degree(1));
		System.out.println("Elemento 1 é alcançavel? "+Grafo.isReachable(1));
		System.out.println("Lista de adjacentes do elemento 1: "+Grafo.getAdjacents(1));
		System.out.println("Removento o elemento \'3\'");
		Grafo.removeElem(3);
		System.out.print("Lista: ");
		Grafo.printLista();
		System.out.println("MatAdj:");
		Grafo.printMatADJ();
		Grafo.addNode(3);
		Grafo.addNode(4);
		Grafo.addNode(5);
		Grafo.addEdge(1, 3);
		Grafo.addEdge(3, 4);
		Grafo.addEdge(4, 5);
		
		GRAPHDIRMATADJ<String> grafo2 = new GRAPHDIRMATADJ<String>(12);
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
		
		System.out.println("Caminhamento em largura partindo do elemento \'1\': "+grafo2.caminhamentoLargura("A").toString());
		System.out.println("Caminhamento em profundidade partindo do elemento \'1\': "+grafo2.caminhamentoProfundidade("A").toString());
		System.out.print("Lista: ");
		Grafo.printLista();
		System.out.println("MatAdj:");
		Grafo.printMatADJ();
	}
}