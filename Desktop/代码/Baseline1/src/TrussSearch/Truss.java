package TrussSearch;

import javax.imageio.metadata.IIOMetadataFormatImpl;
import java.io.PrintStream;
import java.util.*;

/**
 * TrussSearch
 * wuyue
 * 2021/5/13
 **/
public class Truss {
    private Queue<Integer> q =  new LinkedList<>();
    private int nodenum,edgenum,maxsup;
    private Map<Integer, HashSet<Integer>> graph;
    private Map<Map.Entry<Integer,Integer>, Integer> edges;
    private Map<Integer,Map.Entry<Integer,Integer>> edges2;
    private Map<Integer,Integer> sup;
//    private Map<Integer,Boolean> del = new HashMap<Integer, Boolean>();
    private ArrayList<Integer> res;

    public Truss(int nodenum, int edgenum, int maxsup, Map<Integer, HashSet<Integer>> graph, Map<Map.Entry<Integer, Integer>, Integer> edges, Map<Integer,Map.Entry<Integer,Integer>> edges2, Map<Integer,Integer> sup) {
        this.nodenum = nodenum;
        this.edgenum = edgenum;
        this.maxsup = maxsup;
        this.graph = graph;
        this.edges = edges;
        this.edges2 = edges2;
        this.sup = sup;
    }

    public ArrayList getTruss(int k){
        Map<Integer,Boolean> del = new HashMap<Integer, Boolean>();
        //获取余下的边
        if (res == null){
            res = new ArrayList<>();
            for(int i : sup.keySet())
                res.add(i);
        }
        for(int i = 0; i < res.size(); i++){
            del.put(res.get(i),false);
        }
        for(int i = 0; i < res.size(); i++){
            if (sup.get(res.get(i)) < k-2){
                q.add(res.get(i));
            }
        }

        res.clear();
        while (!q.isEmpty()){
            int eid = q.poll();
            if(del.get(eid) == true) continue;
            del.replace(eid,true);
//            System.out.println(edges2);
            int x = edges2.get(eid).getKey();
            int y = edges2.get(eid).getValue();
            if(graph.get(x).size() < graph.get(y).size()){
                int t = x;
                x = y;
                y = t;
            }
            for(int j : graph.get(y)){
//                System.out.println(y);
//                System.out.println(j);
//                System.out.println(graph.get(y));
//                System.out.println(edges);
                int e = edges.get(Map.entry(y,j));
                if(del.get(e) == true) continue;
                if(edges.get(Map.entry(x,j)) != null){
                    e = edges.get(Map.entry(x,j));
                    if (del.get(e) == true) continue;
//                    System.out.println(sup);
                    sup.replace(e,sup.get(e)-1);
                    if (sup.get(e) < k-2) {
                        q.add(e);
                    }
                    e = edges.get(Map.entry(y,j));
                    sup.replace(e,sup.get(e)-1);
                    if (sup.get(e) < k-2){
                        q.add(e);
                    }
                }
            }
            delete(eid);
        }

        for (int i : del.keySet()){
            if (del.get(i) == false){
                res.add(i);
            }
        }
        return res;
    }

    //删除sup值小于k-2的边的相关信息
    public void delete(int e){
        edgenum--;
        sup.remove(e);
        int x = edges2.get(e).getKey();
        int y = edges2.get(e).getValue();
        edges.remove(Map.entry(x,y));
        edges.remove(Map.entry(y,x));
        edges2.remove(e);

        if (graph.get(x).contains(y)){
            graph.get(x).remove(y);
        }
        if (graph.get(y).contains(x)){
            graph.get(y).remove(x);
        }

        if (graph.get(x).size() == 0){
            graph.remove(x);
            nodenum--;
        }
        if (graph.get(y).size() == 0){
            graph.remove(y);
            nodenum--;
        }

    }

    public Map<Integer, HashSet<Integer>> CC(){

        return null;
    }

    public int getNodenum() {
        return nodenum;
    }

    public int getEdgenum() {
        return edgenum;
    }

    public int getMaxsup() {
        maxsup = 0;
        for(int i : sup.keySet()){
            if (sup.get(i) > maxsup){
                maxsup = sup.get(i);
            }
        }
        return maxsup;
    }

    public ArrayList getEachNode() {
        ArrayList<Integer> nodeList = new ArrayList<>();
        for (int i : graph.keySet()){
            nodeList.add(i);
        }
        return nodeList;
    }

    public ArrayList getEachEdge() {
        ArrayList<String> edgeList = new ArrayList();
        for (int i : edges2.keySet()) {
                int x = edges2.get(i).getKey();
                int y = edges2.get(i).getValue();
                edgeList.add(x + "----" + y);
        }
        return edgeList;
    }

    public Map<Integer, HashSet<Integer>> getGraph() {
        return graph;
    }

    public Map<Map.Entry<Integer, Integer>, Integer> getEdges() {
        return edges;
    }

    public Map<Integer,Map.Entry<Integer,Integer>> getEdges2() {
        return edges2;
    }

    public Map<Integer,Integer> getSup() {
        return sup;
    }

    public ArrayList<Integer> getRes() {
        return res;
    }

    public boolean trussDecomposition(int k, ArrayList<Integer> source){

        res = getTruss(k);
        ArrayList nodes = getEachNode();
        System.out.println(graph);
        if(ccDelete(graph, source) == true){
            System.out.println("查找到" + k + "-truss包含所有输入节点");
            return true;
        }else{
            System.out.println("未找到包含所有输入节点的truss");
            return false;
        }

    }

    private boolean ccDelete(Map<Integer, HashSet<Integer>> graph, ArrayList<Integer> source){
        boolean exist = false;
        int ccnum = 0;
        CC cc = new CC(graph);
        Map<Integer, ArrayList<Integer>> cclist = cc.getCC();
        int count = cc.getCount();
        for (int i = 0; i < count; i++){
            if (cclist.get(i).containsAll(source)){
                exist = true;
                ccnum = i;
            }
        }
        if(exist){
            HashSet<Integer> set = new HashSet<>();
            for (int j = 0; j< count;j++){
                if (j != ccnum){
                    ArrayList dellist = cclist.get(j);
                    for (Object w : dellist){
                        if (graph.keySet().contains(w)){
                            for (int x : graph.get(w)){
                                int eid = edges.get(Map.entry(x,w));
                                set.add(eid);
                            }
                        }
                    }
                }
            }
            for (int n : set){
                delete(n);
                res.remove(res.indexOf(n));
            }
            return true;
        }else{
            graph = null;
        }
        return false;
    }


}
