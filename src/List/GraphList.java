package List;

import java.util.LinkedList;
import System.in.Scanner;
public class GraphList {
    private int vertex;
    private LinkedList<Integer> list[];

    public GraphList(int vertex) {
        this.vertex = vertex;
        list = new LinkedList[vertex];
        for (int i = 0; i <vertex ; i++) {
            list[i] = new LinkedList<>();
        }
    }
    public GraphList(String filename){
        Scanner sc = new Scanner(new File(filename));
        ArrayList<Integer> vertices = new ArrayList<Integer>();
        while(sc.hasNext()){
            Integer i1 = sc.nextInteger();
            vertices.add(i1);
            sc.nextLine();
        }
        sc.close();

        for(Integer vertice:vertices){
            list[vertice] = new LinkedList<>();
        }
    }
    // add edge oriented with weight
    public void addEdgeOriented(int source, int destination){
        list[source].addFirst(destination);
    }

    // degree of graph
    public int degree(int vertex){
        return list[vertex].size();
    }
    // add edge non-oriented
    public void addEdgeNonOriented(int source, int destination){
        list[source].addFirst(destination);
        list[destination].addFirst(source);
    }
    // print graph

    public void printGraph(){
        for (int i = 0; i <vertex ; i++) {
            if(list[i].size()>0){
                System.out.print("Vertex ["+i+"] is connected to: ");
                System.out.print("[ ");
                for (int j = 0; j <list[i].size() ; j++) {

                    System.out.print(list[i].get(j)+" ");
                }
                System.out.print("]");

                System.out.println();
            }
        }
    }
    // verify adjacency
    public boolean verifyAdjacency(int source, int destination){
        for (int i = 0; i <list[source].size() ; i++) {
            if(list[source].get(i)==destination){
                return true;
            }
        }
        return false;
    }
    // dfs
    public void dfs(int source){
        boolean visited[] = new boolean[vertex];
        dfsUtil(source, visited);
    }
    public void dfsUtil(int source, boolean visited[]){
        visited[source] = true;
        System.out.print(source+" ");
        for (int i = 0; i <list[source].size() ; i++) {
            if(!visited[list[source].get(i)]){
                dfsUtil(list[source].get(i), visited);
            }
        }
    }
    // bfs
    public void bfs(int source){
        boolean visited[] = new boolean[vertex];
        LinkedList<Integer> queue = new LinkedList<>();
        visited[source] = true;
        queue.add(source);
        while (queue.size()!=0){
            source = queue.poll();
            System.out.print(source+" ");
            for (int i = 0; i <list[source].size() ; i++) {
                if(!visited[list[source].get(i)]){
                    visited[list[source].get(i)] = true;
                    queue.add(list[source].get(i));
                }
            }
        }
    }
    // has cycle
    public boolean hasCycle(){
        boolean visited[] = new boolean[vertex];
        for (int i = 0; i <vertex ; i++) {
            if(!visited[i]){
                if(hasCycleUtil(i, visited, -1)){
                    return true;
                }
            }
        }
        return false;
    }
    public boolean hasCycleUtil(int source, boolean visited[], int parent){
        visited[source] = true;
        for (int i = 0; i <list[source].size() ; i++) {
            if(!visited[list[source].get(i)]){
                if(hasCycleUtil(list[source].get(i), visited, source)){
                    return true;
                }
            }else if(list[source].get(i)!=parent){
                return true;
            }
        }
        return false;
    }
    // has cycle oriented
    public boolean hasCycleOriented(){
        boolean visited[] = new boolean[vertex];
        boolean recStack[] = new boolean[vertex];
        for (int i = 0; i <vertex ; i++) {
            if(hasCycleOrientedUtil(i, visited, recStack)){
                return true;
            }
        }
        return false;
    }
    public boolean hasCycleOrientedUtil(int source, boolean visited[], boolean recStack[]){
        if(recStack[source]){
            return true;
        }
        if(visited[source]){
            return false;
        }
        visited[source] = true;
        recStack[source] = true;
        for (int i = 0; i <list[source].size() ; i++) {
            if(hasCycleOrientedUtil(list[source].get(i), visited, recStack)){
                return true;
            }
        }
        recStack[source] = false;
        return false;
    }
    // density
    public double density(){
        int edges = 0;
        for (int i = 0; i <vertex ; i++) {
            edges+=list[i].size();
        }
        return (2.0*edges)/(vertex*(vertex-1));
    }
    // is connected
    public boolean isConnected(){
        boolean visited[] = new boolean[vertex];
        isConnectedUtil(0, visited);
        for (int i = 0; i <vertex ; i++) {
            if(!visited[i]){
                return false;
            }
        }
        return true;
    }
    public void isConnectedUtil(int source, boolean visited[]){
        visited[source] = true;
        for (int i = 0; i <list[source].size() ; i++) {
            if(!visited[list[source].get(i)]){
                isConnectedUtil(list[source].get(i), visited);
            }
        }
    }
    


}
