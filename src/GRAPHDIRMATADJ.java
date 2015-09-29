import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GRAPHDIRMATADJ <N> implements TAD{
	int limit;
	private List<N> lNodes;
	private boolean matAdj [][];
	public GRAPHDIRMATADJ(int max) {
		limit = max;
		lNodes = new ArrayList<N>();
		matAdj = new boolean[max][max];
	}

	public boolean addNode(Object a) {
		if(lNodes.size()<limit){
			for(N aux : lNodes){
				if(aux.equals((N)a)){
					return false;
				}
			}
			lNodes.add((N)a);
			return true;
		}
		return false;
	}
	
	public boolean addEdge(Object a, Object b) {
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
		if(existeA && existeB){
			matAdj[indexA][indexB] = true;
			return true;
		}
		return false;
	}
	
	public boolean removeEdge(Object a, Object b) {
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
		if(existeA && existeB){
			matAdj[indexA][indexB] = false;
			return true;
		}
		return false;
	}
	
	public boolean removeElem(Object a){
		for(N aux : lNodes){
			if(aux.equals((N)a)){
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
		for(N aux : lNodes){
			System.out.print(aux.toString()+" ");
		}
		System.out.println();
	}
	
	public List<N> getlNodes() {
		return lNodes;
	}

	public void setlNodes(List<N> lNodes) {
		this.lNodes = lNodes;
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
		for(N aux : lNodes){
			if(aux.equals((N)node)){
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
		for(N aux : lNodes){
			if(aux.equals((N)node)){
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
		ArrayList<N> lista = new ArrayList<N>();
		for(N aux : lNodes){
			if(aux.equals((N)node)){
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
	
	public static void main(String[] args) {
		GRAPHDIRMATADJ<Integer> Grafo = new GRAPHDIRMATADJ<Integer>(10){};
		Grafo.addNode(1);
		Grafo.addNode(2);
		Grafo.addNode(3);
		System.out.println("Adicionando aresta do nodo \'1\' para o nodo \'3\': "+Grafo.addEdge(1, 2));
		System.out.println("Adicionando aresta do nodo \'1\' para o nodo \'3\': "+Grafo.addEdge(1, 3));
		System.out.println("Adicionando aresta do nodo \'1\' para o nodo \'3\': "+Grafo.addEdge(2, 3));
		System.out.print("Lista: ");
		Grafo.printLista();
		System.out.println("MatAdj:");
		Grafo.printMatADJ();
		System.out.println("Grau do elemento 1: "+Grafo.degree(1));
		System.out.println("Elemento 1 é alcançavel? "+Grafo.isReachable(1));
		System.out.println("Lista de adjacentes do elemento 1: "+Grafo.getAdjacents(1));
		Grafo.removeElem(3);
		System.out.print("Lista: ");
		Grafo.printLista();
		System.out.println("MatAdj:");
		Grafo.printMatADJ();
	}
}