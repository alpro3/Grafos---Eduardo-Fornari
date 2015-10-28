import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class GRAPHDIRLISTADJ <N> implements TAD<N>{
	
public class Node<N>{
		
		private List<Node<N>> listNodesAdjacentes;
	
		private N item;
		
		boolean marcado;
		
		public Node(N item){
			this.item = item;
			listNodesAdjacentes = new ArrayList<Node<N>>();
		}
		
		public boolean isMarcado() {
			return marcado;
		}
		
		public void setMarcado(boolean marcado) {
			this.marcado = marcado;
		}
		
		public void addAdj(Node<N> node){
			if(!listNodesAdjacentes.contains(node)){
				listNodesAdjacentes.add(node);
			}
		}
		
		public List<Node<N>> getNodesAdjacentes() {
			return listNodesAdjacentes;
		}
		
		public N getItem() {
			return item;
		}
		
		public void setItem(N item) {
			this.item = item;
		}
		
		public void remove(int index){
			if(listNodesAdjacentes.size()>index && index>=0){
				listNodesAdjacentes.remove(index);
			}
		}
		
		public String printListaAdjacente() {
			String lista = item.toString() + " = {";
			for (Node<N> aux : listNodesAdjacentes) {
				lista+=" ";
				lista+=aux.toString();
			}
			lista+=" }";
			return lista;
		}
		
		public String toString (){
			return item.toString();
		}
	}
	
	private List<Node<N>> listNodes;

	public GRAPHDIRLISTADJ() {
		listNodes = new ArrayList<Node<N>>();
	}

	public boolean addNode(N item) {
		for(Node<N> aux : listNodes){
			if(aux.getItem().equals(item)){
				return false;
			}
		}
		listNodes.add(new Node<N>(item));
		return true;
	}

	public void desmarcaNodos(){
		for(Node<N> aux : listNodes){
			aux.setMarcado(false);
		}
	}

	public List<N> caminhamentoLargura(N item) throws IllegalAccessException{
		List<N> res = new ArrayList<N>();
		Queue<Node<N>> fAux = new LinkedList<Node<N>>();
		Node<N> nAUX;
		int index = this.indexItem(item);
		if(index==-1){
			throw new IllegalAccessException("Elemento invalido");
		}
		else{
			this.desmarcaNodos();
			res.add(listNodes.get(index).getItem());
			fAux.offer(listNodes.get(index));
			listNodes.get(index).setMarcado(true);
			while(!fAux.isEmpty()){
				nAUX = fAux.poll();
				ArrayList<Node<N>> l = (ArrayList<Node<N>>)getAdjacents(nAUX.getItem());
				for(Node<N> v : l){
					if(!v.isMarcado()){
						res.add(v.getItem());
						v.setMarcado(true);
						fAux.offer(v);
					}
				}
			}
		}
		return res;
	}

	public List<N> caminhamentoProfundidade(N item) throws IllegalAccessException{
		int index = this.indexItem(item);
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

	public void caminhamentoProfundidade(Node<N> node, List<N> list){
		list.add(node.getItem());
		node.setMarcado(true);
		for (Node<N> aux : node.getNodesAdjacentes()) {
			if(aux.isMarcado()==false)caminhamentoProfundidade(aux, list);
		}
	}

	private int indexItem(N item) {
		for(Node<N> aux : listNodes){
			if(aux.getItem().equals(item))return listNodes.indexOf(aux);
		}
		return -1;
	}

	public boolean addEdge(N origem, N destino) {
		Node<N> aux = null, auxB = null;
		int indexA = -1;
		for(int i=0; i<listNodes.size();i++)
		{
			if(auxB!=null && indexA!=-1)break;
			aux = listNodes.get(i);
			if(aux.getItem().equals(origem))indexA = i;
			if(aux.getItem().equals(destino)) auxB = aux;
		}
		if(indexA!=-1 && auxB!=null){
			listNodes.get(indexA).addAdj(auxB);
			return true;
		}
		return false;
	}

	public boolean removeEdge(N origem, N destino) {
		int indexA = indexItem(origem), indexB = indexItem(destino);
		if(indexA!=-1 && indexB!=-1){
			ArrayList<Node<N>> listAdj = (ArrayList<Node<N>>) listNodes.get(indexA).getNodesAdjacentes();
			int index = -1;
			for (int j = 0; j < listAdj.size(); j++) {
				if(listAdj.get(j).getItem().equals(destino)){
					index = j;
					break;
				}
			}
			listNodes.get(indexA).remove(index);
			return true;
		}
		return false;
	}

	public boolean existeAresta(N origem, N destino) {
		int indexA = indexItem(origem);
		if(indexA!=-1){
			ArrayList<Node<N>> listElem = (ArrayList<Node<N>>) listNodes.get(indexA).getNodesAdjacentes();
			for (Node<N> aux : listElem) {
				if(aux.getItem().equals(destino)){
					return true;
				}
			}
		}
		return false;
	}

	public boolean removeItem(N item){
		ArrayList<Node<N>> lAUX;
		for(Node<N> aux : listNodes){
			if(aux.getItem().equals(item)){
				listNodes.remove(aux);
				break;
			}
			else{
				lAUX = (ArrayList<Node<N>>)aux.getNodesAdjacentes();
				for(int i = 0; i<lAUX.size();){
					if(lAUX.get(i).getItem().equals(item)){
						aux.remove(i);
						break;
					}
				}
			}
		}
		return false;
	}

	public void printLista() {
		for(Node<N> aux : listNodes){
			System.out.print(aux.getItem().toString()+" ");
		}
		System.out.println();
	}

	public int degree(N item) {
		int cont = 0;
		int index = -1;
		Node<N> aux1 = null;
		for(int i=0;i<listNodes.size();i++)
		{
			aux1 = listNodes.get(i);
			if(aux1.getItem().equals(item))
			{
				index = i;
				break;
			}
		}
		if(index>=0)
		{
			ArrayList<Node<N>> listAUX;
			for(Node<N> aux : listNodes)
			{
				if(aux.getItem().equals(item))
				{
					cont+=aux.getNodesAdjacentes().size();
				}
				else{
					listAUX = (ArrayList<Node<N>>)aux.getNodesAdjacentes();
					for(Node<N> n : listAUX){
						if(n.getItem().equals(item))cont++;
					}
				}
			}
		}
		else
		{
			throw new IllegalArgumentException("Item não está no grafo!");
		}
		return cont;
	}

	public boolean isReachable(N item) {
		ArrayList<N> listaAUX;
		for (Node<N> aux : listNodes) {
			listaAUX = (ArrayList<N>)aux.getNodesAdjacentes();
			if(listaAUX.contains(item))return true;
		}
		return false;
	}

	public List getAdjacents(N item) {
		for(Node<N> aux : listNodes){
			if(aux.getItem().equals(item)){
				return aux.getNodesAdjacentes();
			}
		}
		return null;
	}
	public List<Node<N>> getList() {
		return listNodes;
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
			listAdj = origem.getNodesAdjacentes();
			
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
	
	public void printGrafo(){
		for (Node<N> node : listNodes) {
			System.out.println(node.printListaAdjacente());
		}
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