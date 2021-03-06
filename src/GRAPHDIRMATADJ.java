import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class GRAPHDIRMATADJ <N> implements TAD<N>{
	public class Node<N>{
		N item;
		boolean marcado;
		public Node(N item){
			this.item = item;
		}
		public N getItem() {
			return item;
		}
		public void setItem(N item) {
			this.item = item;
		}
		public boolean isMarcado() {
			return marcado;
		}
		public void setMarcado(boolean marcado) {
			this.marcado = marcado;
		}
		public String toString(){
			return item.toString();
		}
	}
	int limit;
	private List<Node<N>> listNodes;
	private boolean matrizAdjacente [][];
	public GRAPHDIRMATADJ(int max) {
		limit = max;
		listNodes = new ArrayList<Node<N>>();
		matrizAdjacente = new boolean[max][max];
	}

	public boolean addNode(N item) {
		if(listNodes.size()<limit){
			for(Node<N> aux : listNodes){
				if(aux.getItem().equals(item)){
					return false;
				}
			}
			listNodes.add(new Node(item));
			return true;
		}
		return false;
	}
	
	private int indexItem(N item) {
		for(Node<N> aux : listNodes){
			if(aux.getItem().equals(item))return listNodes.indexOf(aux);
		}
		return -1;
	}
	
	public boolean addEdge(N origem, N destino) {
		int indexA = indexItem(origem), indexB = indexItem(destino);
		if(indexA!=-1 && indexB!=-1){
			matrizAdjacente[indexA][indexB] = true;
			return true;
		}
		return false;
	}
	
	public boolean removeEdge(N origem, N destino) {
		int indexA = indexItem(origem), indexB = indexItem(origem);
		if(indexA!=-1 && indexB!=-1){
			matrizAdjacente[indexA][indexB] = false;
			return true;
		}
		return false;
	}
	
	public boolean removeElem(N item){
		for(Node<N> aux : listNodes){
			if(aux.getItem().equals(item)){
				int index = listNodes.indexOf(aux);
				for (int i = 0; i < matrizAdjacente.length; i++) {
					matrizAdjacente[i][index] = false;
				}
				for (int i = index-1; i < matrizAdjacente.length-1; i++) {
					matrizAdjacente[i] = matrizAdjacente[i+1];
				}
				listNodes.remove(aux);
				break;
			}
		}
		return false;
	}
	
	public boolean existeAresta(N origem, N destino) {
		int i = 0;
		boolean existeA = false, existeB = false;
		int indexA = 0, indexB = 0;
		while(i<listNodes.size() && (existeA==false || existeB==false)){
			if(listNodes.get(i).equals(origem)){
				existeA = true;
				indexA = i;
			}
			if(listNodes.get(i).equals(destino)){
				existeB = true;
				indexB = i;
			}
			i++;
		}
		if((existeA && existeB) && matrizAdjacente[indexA][indexB]==true){
			return true;
		}
		return false;
	}
	
	public void printLista() {
		for(Node<N> aux : listNodes){
			System.out.print(aux.getItem().toString()+" ");
		}
		System.out.println();
	}
	
	public List<Node<N>> getNodes() {
		return listNodes;
	}

	public void printMatrizAdjacente() {
		for (int i = 0; i < matrizAdjacente.length; i++) {
			String linha = "";
			for (int j = 0; j < matrizAdjacente[0].length; j++) {
				if(matrizAdjacente[i][j]==true){
					linha+=1+" ";
				}
				else{
					linha+=0+" ";
				}
			}
			System.out.println(linha);
		}
	}
	
	public int degree(N item) {
		int cont = 0;
		int index = -1;
		Node<N> aux = null;
		for(int i=0;i<listNodes.size();i++)
		{
			aux = listNodes.get(i);
			if(aux.getItem().equals(item))
			{
				index = i;
				break;
			}
		}
		if(index>=0)
		{
			for(int i=0;i<listNodes.size();i++)
			{
				if(i==index)
				{
					for(int j=0;j<listNodes.size();j++)
					{
						if(matrizAdjacente[index][j]==true)cont++;
					}
				}
			}
			for(int i=0;i<listNodes.size();i++)
			{
				if(i!=index)
				{
					if(matrizAdjacente[i][index]==true)cont++;
				}
			}
		}
		else
		{
			throw new IllegalArgumentException("Item n�o est� no grafo!");
		}
		return cont;
	}
	
	public boolean isReachable(N item) {
		for(Node<N> aux : listNodes){
			if(aux.getItem().equals(item)){
				int index = listNodes.indexOf(aux);
				for (int i = 0; i < listNodes.size(); i++) {
					if(matrizAdjacente[i][index]){
						return true;
					}
				}
				return false;
			}
		}
		return false;
	}

	public List getAdjacents(N item) {
		ArrayList<Node<N>> lista = new ArrayList<Node<N>>();
		for(Node<N> aux : listNodes){
			if(aux.getItem().equals(item)){
				int index = listNodes.indexOf(aux);
				for(int i = 0; i<listNodes.size();i++){
					if(matrizAdjacente[index][i]){
						lista.add(listNodes.get(i));
					}
				}
				return lista;
			}
		}
		return null;
	}
	
	public void desmarcaNodos(){
		for(Node<N> aux : listNodes){
			aux.setMarcado(false);
		}
	}
	
	public List<N> caminhamentoLargura(N item) throws IllegalAccessException{
		List<N> result = new ArrayList<N>();
		Queue<Node<N>> fAux = new LinkedList<Node<N>>();
		Node<N> nAUX;
		int index = this.indexItem(item);
		if(index==-1){
			throw new IllegalAccessException("Elemento invalido");
		}
		else{
			this.desmarcaNodos();
			result.add(listNodes.get(index).getItem());
			fAux.offer(listNodes.get(index));
			listNodes.get(index).setMarcado(true);
			while(!fAux.isEmpty()){
				nAUX = fAux.poll();
				ArrayList<Node<N>> l = (ArrayList<Node<N>>)getAdjacents(nAUX.getItem());
				for(Node<N> v : l){
					if(!v.isMarcado()){
						result.add(v.getItem());
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
			caminhamentoProfundidade(listNodes.get(index),result);
			return result;
		}
	}
	
	public void caminhamentoProfundidade(Node<N> nodo, List<N> list){
		list.add(nodo.getItem());
		nodo.setMarcado(true);
		int index = indexItem(nodo.getItem());
		for(int i=0;i<listNodes.size();i++){
			if(matrizAdjacente[index][i] && listNodes.get(i).isMarcado()==false)caminhamentoProfundidade(listNodes.get(i), list);
		}
	}
	
	public boolean existeCaminho(N origem, N destino){
		int indexA = indexItem(origem), indexB = indexItem(destino);
		if(indexA>=0 && indexB>=0){
			desmarcaNodos();
			return existeCaminho(listNodes.get(indexA), destino);
		}
		return false;
	}

	private boolean existeCaminho(Node<N> origem, N destino) {
		List<Node<N>> listAdj;
		
		if(origem.getItem().equals(destino)){
			return true;
		}
		else
		{
			origem.setMarcado(true);
			listAdj = getAdjacents(origem.getItem());
			
			for(Node<N> n : listAdj){
				if(!n.isMarcado()){
					if(existeCaminho(n,destino)){
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public List<N> pathFrom(N origem, N destino) throws IllegalAccessException
	{
		desmarcaNodos();
		int indexA = indexItem(origem);
		int indexB = indexItem(destino);
		if(indexA<0 || indexB<0)
		{
			throw new IllegalAccessException("IvalidNode");
		}
		else{
			return pathFrom(listNodes.get(indexA), destino);
		}	
	}
	
	public List<N> pathFrom(Node<N> origem, N destino)
	{
		List<N> res = null;
		if(origem.getItem().equals(destino))
		{
			res = new ArrayList<N>();
			res.add(origem.getItem());
			return res;
		}
		origem.setMarcado(true);
		ArrayList<Node<N>> adj = (ArrayList<Node<N>>)getAdjacents(origem.getItem());
		for(Node<N> aux : adj)
		{
			if(!aux.isMarcado())
			{
				res = pathFrom(aux, destino);
				if(res!=null)
				{
					res.add(0,origem.getItem());
					return res;
				}			}
		}
		return res;
	}
	
	public List<N> bestPathFrom(N origem, N destino) throws IllegalAccessException
	{
		desmarcaNodos();
		int indexA = indexItem(origem);
		int indexB = indexItem(destino);
		if(indexA<0 || indexB<0)
		{
			throw new IllegalAccessException("IvalidNode");
		}
		else{
			return bestPathFrom(listNodes.get(indexA), destino);
		}	
	}
	
	public List<N> bestPathFrom(Node<N> origem, N destino)
	{
		List<N> res = null;
		List<N> resMelhor = null;
		if(origem.getItem().equals(destino))
		{
			res = new ArrayList<N>();
			res.add(origem.getItem());
			return res;
		}
		origem.setMarcado(true);
		ArrayList<Node<N>> adj = (ArrayList<Node<N>>)getAdjacents(origem.getItem());
		for(Node<N> aux : adj)
		{
			if(!aux.isMarcado())
			{
				res = bestPathFrom(aux, destino);
				if(res!=null && (resMelhor==null ||(resMelhor!=null && res.size()<resMelhor.size())))
				{
					res.add(0,origem.getItem());
					resMelhor = res;
				}		
			}
		}
		return resMelhor;
	}
	
	public static void main(String[] args) throws IllegalAccessException {
		GRAPHDIRLISTADJ<String> grafo = new GRAPHDIRLISTADJ<String>();
		System.out.println("Iniciando novo grafo.");
		grafo.addNode("A");
		grafo.addNode("B");
		grafo.addNode("C");
		grafo.addNode("D");
		grafo.addNode("E");
		grafo.addNode("F");
		grafo.addNode("G");
		grafo.addNode("J");
		grafo.addEdge("A", "B");
		grafo.addEdge("A", "C");
		grafo.addEdge("A", "D");
		grafo.addEdge("B", "F");
		grafo.addEdge("F", "G");
		grafo.addEdge("B", "C");
		grafo.addEdge("C", "E");
		grafo.addEdge("C", "A");
		grafo.addEdge("G", "E");
		grafo.addEdge("D", "E");
		grafo.addEdge("D", "A");
		grafo.addEdge("J", "D");

		System.out.println("\nCaminhamento em largura partindo do 1: "+grafo.caminhamentoLargura("A").toString());
		System.out.println("Caminhamento em profundidade partindo do 1: "+grafo.caminhamentoProfundidade("A").toString());
		grafo.printGrafo();
		System.out.println("Existe caminho do A para o A? "+grafo.existeCaminho("A", "A"));
		System.out.println("Qual o caminho? "+grafo.bestPathFrom("A", "A"));
		System.out.println("Existe caminho do A para o B? "+grafo.existeCaminho("A", "B"));
		System.out.println("Qual o caminho? "+grafo.bestPathFrom("A", "B"));
		System.out.println("Existe caminho do A para o C? "+grafo.existeCaminho("A", "C"));
		System.out.println("Qual o caminho? "+grafo.bestPathFrom("A", "C"));
		System.out.println("Existe caminho do A para o D? "+grafo.existeCaminho("A", "D"));
		System.out.println("Qual o caminho? "+grafo.bestPathFrom("A", "D"));
		System.out.println("Existe caminho do A para o E? "+grafo.existeCaminho("A", "E"));
		System.out.println("Qual o caminho? "+grafo.bestPathFrom("A", "E"));
		System.out.println("Existe caminho do A para o F? "+grafo.existeCaminho("A", "F"));
		System.out.println("Qual o caminho? "+grafo.bestPathFrom("A", "F"));
		System.out.println("Existe caminho do A para o G? "+grafo.existeCaminho("A", "G"));
		System.out.println("Qual o caminho? "+grafo.bestPathFrom("A", "G"));
		System.out.println("Existe caminho do A para o J? "+grafo.existeCaminho("A", "J"));
		System.out.println("Qual o caminho? "+grafo.bestPathFrom("A", "J"));
		System.out.println("\nGrau do A: "+grafo.degree("A"));
		System.out.print("\nLista: ");
		grafo.printLista();
		System.out.println("Grafo:");
		grafo.printGrafo();	
	}
}