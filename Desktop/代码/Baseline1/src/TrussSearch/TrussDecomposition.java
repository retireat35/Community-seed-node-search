package TrussSearch;

import java.io.*;
import java.util.*;

/**
 * TrussSearch
 * wuyue
 * 2021/5/17
 **/
public class TrussDecomposition {
    public static void main(String[] args) throws IOException {
        BuildGraph myGraph = new BuildGraph();
        myGraph.FileInput("test2.txt");
//        ArrayList source = new ArrayList(Arrays.asList(2982,1831));
        ArrayList source = new ArrayList(Arrays.asList(751,775));
//        ArrayList source = new ArrayList(Arrays.asList(6,7));
//        System.out.println(myGraph.getGraph());
        int nodenum = myGraph.getNodenum();
        int edgenum = myGraph.getEdgenum();
        int maxsup = myGraph.supCalculation();
        Map<Integer, HashSet<Integer>> nowgraph;
        Map<Map.Entry<Integer, Integer>, Integer> nowedges;

        Map<Integer,Map.Entry<Integer,Integer>> nowedges2;
        Map<Integer,Integer> nowsup;

        int firstk = 0;
        nowgraph = myClone(myGraph.getGraph());
        nowedges = myClone(myGraph.getEdges());
        nowedges2 = myClone(myGraph.getEdges2());
        nowsup = myClone(myGraph.getSup());
        //public Truss(int nodenum, int edgenum, int maxsup, Map<Integer, ArrayList<Integer>> graph, Map<Map.Entry<Integer, Integer>, Integer> edges, ArrayList<Map.Entry<Integer, Integer>> edges2, ArrayList<Integer> sup, Vector<Integer> source)
        Truss mytruss = new Truss(nodenum,edgenum,maxsup,myGraph.getGraph(),myGraph.getEdges(),myGraph.getEdges2(),myGraph.getSup());
        int i = 3;
        while (mytruss.trussDecomposition(i,source)){
            nodenum = mytruss.getNodenum();
            edgenum = mytruss.getEdgenum();
            nowgraph.clear();
            nowgraph = myClone(mytruss.getGraph());
            nowedges.clear();
            nowedges = myClone(mytruss.getEdges());
            nowedges2.clear();
            nowedges2 = myClone(mytruss.getEdges2());
            nowsup.clear();
            nowsup = myClone(mytruss.getSup());
            firstk = i;
            i++;
//            System.out.println(nowgraph);
//            System.out.println(nowedges);
//            System.out.println(nowedges2);
            }
        nowgraph = loadGraph(nowedges2);
        System.out.println(nodenum);
        System.out.println(edgenum);
        System.out.println(maxsup);
        System.out.println(nowgraph);
        System.out.println(nowedges);
        System.out.println(nowedges2);


        System.out.println("-------------------------");
        Truss firsttruss = new Truss(nodenum,edgenum,maxsup,nowgraph,nowedges,nowedges2,nowsup);
        for (int j = firstk; j <= maxsup+2; j++) {
//            System.out.println("edges:" + firsttruss.getEdges());
//            System.out.println("nodenum:" + nodenum);
            firsttruss.getTruss(j);
            if (firsttruss.getNodenum() == 0) break;
            nodenum = firsttruss.getNodenum();
            edgenum = firsttruss.getEdgenum();
            nowgraph = firsttruss.getGraph();
            nowedges = firsttruss.getEdges();
            nowedges2 = firsttruss.getEdges2();
            nowsup = firsttruss.getSup();
            System.out.println(j);
//            System.out.println(nodenum);
            System.out.println(nowgraph);
//            System.out.println(nowgraph);
//            System.out.println(firsttruss.getGraph());
        }
    }

    public static <T extends Object,Q extends Object>Map<T,Q> myClone(Map<T,Q> obj){
        Map cloneObj = new HashMap();
        for (Map.Entry<T,Q> entry : obj.entrySet()){
            cloneObj.put(entry.getKey(),entry.getValue());
        }
        return cloneObj;
    }

    public static Map<Integer, HashSet<Integer>> loadGraph(Map<Integer,Map.Entry<Integer,Integer>> edges2){
        Map<Integer, HashSet<Integer>> graph = new HashMap<>();
        HashSet<Integer> set =null;
        for (int key : edges2.keySet()){
            int a = edges2.get(key).getKey();
            int b = edges2.get(key).getValue();
            Object obj1 = graph.get(a);
            if(obj1 == null){
                set = new HashSet<Integer>();
                set.add(b);
                graph.put(a,(HashSet<Integer>) set);
            }else{
                set = (HashSet<Integer>) obj1;
                if(!set.contains(b)){
                    set.add(b);
                    graph.put(a, (HashSet<Integer>) set);
                }
            }
            Object obj2 = graph.get(b);
            if(obj2 == null){
                set = new HashSet<Integer>();
                set.add(a);
                graph.put(b,(HashSet<Integer>) set);
            }else{
                set = (HashSet<Integer>) obj2;
                set.add(a);
                graph.put(b, (HashSet<Integer>) set);
            }
        }
        return graph;
    }


//    public static <T extends Serializable> T myClone(T obj) {
//        T clonedObj = null;
//        try {
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            ObjectOutputStream oos = new ObjectOutputStream(baos);
//            oos.writeObject(obj);
//            oos.close();
//
//            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
//            ObjectInputStream ois = new ObjectInputStream(bais);
//            clonedObj = (T) ois.readObject();
//            ois.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return clonedObj;
//    }
}
