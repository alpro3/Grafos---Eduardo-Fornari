import java.util.List;


public interface TAD <E>{
	public boolean addNode(E a);
	public boolean addEdge(E a, E b);
	public int degree(E node);// - retorno o grau de um determinado nodo. o Grau de um nodo é dado pelo número de arestas que incidem de e para o nodo ( ou seja, que saem e que chegam no nodo) 
	public boolean isReachable(E node);// - retorna true se o nodo fornecido for alcançavel a partir de um nodo qualquer, false caso contrário.
	public List<E> getAdjacents(E node);// - retorna uma lista com todos os nodos adjacentes ao nodo fornecido.
}
