package TrussSearch;

import javax.sound.midi.Soundbank;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

/**
 * TrussSearch
 * wuyue
 * 2021/5/12
 **/
public class BuildGraph{

    private int nodenum,edgenum;
    private ArrayList<Integer> nodes = new ArrayList();
    private Map<Integer, HashSet<Integer>> graph = new HashMap<>();
    private Map<Map.Entry<Integer,Integer>, Integer> edges = new HashMap<>();
    private Map<Integer,Map.Entry<Integer,Integer>> edges2 = new HashMap<>();
    private Map<Integer,Integer> sup = new HashMap<Integer, Integer>(nodenum);

    public BuildGraph() {
    }

    public void FileInput(String filepath) throws IOException {
        HashSet<Integer> set =null;

        BufferedReader br = new BufferedReader(new FileReader(filepath));

        String contentLine;
        while ((contentLine = br.readLine()) != null){
            String[] str = contentLine.split(" ");
            int a = Integer.valueOf(str[0]);
            int b = Integer.valueOf(str[1]);
//            System.out.println("成功");
            //n为图中节点个数
            if (!nodes.contains(a))
                nodes.add(a);
            if (!nodes.contains(b))
                nodes.add(b);
            nodenum = nodes.size();
            //忽略指向自己的边
            if(a == b) continue;
            if(a < b){
                int t = a;
                a = b;
                b = t;
            }
            if (!(edges.containsKey(Map.entry(a,b))||edges.containsKey(Map.entry(b,a)))) {
                edges.put(Map.entry(a, b), ++edgenum);
                edges.put(Map.entry(b, a), edgenum);
                edges2.put(edgenum, Map.entry(a, b));
//            System.out.println(edges2);
                //用graph存储整张图的信息(邻接表)
                Object obj1 = graph.get(a);
                if (obj1 == null) {
                    set = new HashSet<Integer>();
                    set.add(b);
                    graph.put(a, (HashSet<Integer>) set);
                } else {
                    set = (HashSet<Integer>) obj1;
                    if (!set.contains(b)) {
                        set.add(b);
                        graph.put(a, (HashSet<Integer>) set);
                    }
                }
                Object obj2 = graph.get(b);
                if (obj2 == null) {
                    set = new HashSet<Integer>();
                    set.add(a);
                    graph.put(b, (HashSet<Integer>) set);
                } else {
                    set = (HashSet<Integer>) obj2;
                    set.add(a);
                    graph.put(b, (HashSet<Integer>) set);
                }
            }
        }
        br.close();
    }

    public int getNodenum(){
        return nodenum;
    }

    public int getEdgenum(){
        return edgenum;
    }

    public int supCalculation(){
        int maxsup = 0;
        for (int i : graph.keySet()) {
            HashSet<Integer> set = graph.get(i);
            for(int j : set){
                if (i > j){
                    int eid = edges.get(Map.entry(i,j));
                    sup.put(eid,0);
                    //degree(u) > degree(v)
                    int u = graph.get(i).size() > graph.get(j).size() ? i : j;
                    int v = graph.get(i).size() > graph.get(j).size() ? j : i;
                    int comnei = 0;
                    //判断共同邻居数
                    for(int k : graph.get(v)){
                        if(graph.get(u).contains(k)){
                            comnei++;
                        }
                    }
                    sup.replace(eid,comnei);
                    if((int)sup.get(eid) > maxsup){
                        maxsup = (int)sup.get(eid);
                    }
                }
            }
        }
        return maxsup;
    }

    public Map<Integer, HashSet<Integer>> getGraph() {
        return graph;
    }

    public Map<Map.Entry<Integer, Integer>, Integer> getEdges() {
        return edges;
    }

    public Map<Integer,Integer> getSup() {
        return sup;
    }

    public Map<Integer,Map.Entry<Integer,Integer>> getEdges2() {
        return edges2;
    }
}
