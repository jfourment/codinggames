package org.julien.skynet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

class Player {

    private static class Node {
        
        Integer nodeNumber;
        Set<Node> linkedNodes;
        
        public Node(Integer pNodeNumber) {
            nodeNumber = pNodeNumber;
            linkedNodes = new HashSet<Node>();
        }
        
        public void addLinkedNodes(Node pNode) {
            linkedNodes.add(pNode);
            pNode.getLinkedNodes().add(this);
        }
        
        public void removeLink(Node pNode) {
            linkedNodes.remove(pNode);
            pNode.getLinkedNodes().remove(this);
        }
        
        public int hashCode() { return nodeNumber.hashCode(); }
        
        public boolean equals(Object obj) { 
            return this.nodeNumber.equals(((Node) obj).getNodeNumber());
        }
        
        public Integer getNodeNumber() {
            return nodeNumber;
        }
        
        public Set<Node> getLinkedNodes() {
            return this.linkedNodes;
        }
    }


    public static int findShortestPath(Node currentNode, Node targetNode, int pathLength, Set<Node> alreadyVisited) {
        
        alreadyVisited.add(currentNode);
        
        if (currentNode.getLinkedNodes().size() == 0){
            return -1;
        }
        Iterator<Node> it = currentNode.getLinkedNodes().iterator();
        Node currentShortestPathNode = null;
        int currentShortestPathLength = -1;
        while (it.hasNext()) {
            Node nextNode = it.next();
            if (nextNode.equals(targetNode)) {
                // found a connection to the target node
                return pathLength;
            }
            if (alreadyVisited.contains(nextNode)) {
                continue;
            }
            // else look deeper
            int result = findShortestPath(nextNode, targetNode, pathLength +1, alreadyVisited);
            if (result != -1) {
                if (result < currentShortestPathLength) {
                    currentShortestPathLength = result;
                }
            }
        }
        
        return currentShortestPathLength;
    }

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);

        // Read init information from standard input, if any
        int numberOfNodes = in.nextInt();
        int numberOfLinks = in.nextInt();
        int numberOfExits = in.nextInt();
        
        Node[] nodeArray = new Node[numberOfNodes+1];
        
        List<Node> exitNodeArray = new LinkedList<Node>();
        
        for(int i=0; i<numberOfNodes; i++) {
            nodeArray[i] = new Node(i);
            System.err.println("Build node "+i);
        }
        
        in.nextLine();
        for (int i=0; i<numberOfLinks; i++) {
            
            int n1 = in.nextInt();
            int n2 = in.nextInt();
            nodeArray[n1].addLinkedNodes(nodeArray[n2]);
            System.err.println("Build link "+n1 + "-" + n2);
            in.nextLine();
        }
        
        for (int i=0; i<numberOfExits; i++) {
            int e = in.nextInt();
            exitNodeArray.add(nodeArray[e]);
             System.err.println("Set exit node "+e);
        }
        
        in.nextLine();
        Set<Node> alreadyVisitedNodes = new HashSet<Node>();
        alreadyVisitedNodes.add(nodeArray[0]);
        int result = findShortestPath(nodeArray[0], exitNodeArray.get(0), 0, alreadyVisitedNodes);
        System.err.println("SHORTEST PATH TO EXIT " + result);
        
        while (true) {
            // Read information from standard input
            int n = in.nextInt();

            // Compute logic here

            // System.err.println("Debug messages...");

            // Write action to standard output
            //System.out.println("0 1");
        }
    }
}