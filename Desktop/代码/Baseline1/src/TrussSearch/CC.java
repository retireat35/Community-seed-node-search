package TrussSearch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * TrussSearch
 * wuyue
 * 2021/5/22
 **/
public class CC {
    private Map<Integer,Boolean> marked = new HashMap<Integer, Boolean>();
    private Map<Integer,Integer> id = new HashMap<>();
    private int count;
    private Map<Integer, HashSet<Integer>> graph;
    private Map<Integer, ArrayList<Integer>> cc = new HashMap<Integer, ArrayList<Integer>>();

    public CC(Map<Integer, HashSet<Integer>> graph){
        this.graph = graph;
    }

    public void comCC(){
        for (int i : graph.keySet()){
            marked.put(i,false);
        }
        for (int i : graph.keySet()){
            id.put(i,0);
        }
        for (int s : graph.keySet()){
            if (!marked.get(s)){
                dfs(graph,s);
                count++;
            }
        }
    }

    private void dfs(Map<Integer, HashSet<Integer>> graph, int v){
        marked.replace(v,true);
        id.replace(v,count);
        for (int w : graph.get(v)){
            if (!marked.get(w)){
                dfs(graph,w);
            }
        }
    }

    public Map<Integer, ArrayList<Integer>> getCC(){
        comCC();
        ArrayList arr = new ArrayList();
        for (int i : id.keySet()){
            int c = id.get(i);
            Object obj = cc.get(c);
            if (obj == null){
                arr = new ArrayList<>();
                arr.add(i);
                cc.put(c,arr);
            }else{
                arr = (ArrayList) obj;
                arr.add(i);
                cc.put(c,arr);
            }
        }
        return cc;
    }

    public int getCount() {
        return count;
    }
}
