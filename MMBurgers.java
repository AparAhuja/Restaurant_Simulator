import java.util.*;

class customer{
    int id;
    int arrTime;
    int numbs;

    int numbsRec;
    int numbsGrid;
    int timeInQue;
    int queueNo;
    int state;
    int deparTime;
    int waitTime;
}
class AVL {

    private Node root;
  
    private class Node {
      private int key;
      customer c;
      private int balance;
      private int height;
      private Node left, right, parent;
  
      Node(int k, Node p, customer x) {
        key = k;
        parent = p;
        c = x;
      }
    }
  
    public boolean insert(customer c) {
      int key = c.id;
  
      if (root == null) root = new Node(key, null, c);
      else {
        Node n = root;
        Node parent;
        while (true) {
          if (n.key == key) return false;
  
          parent = n;
  
          boolean goLeft = n.key > key;
          n = goLeft ? n.left : n.right;
  
          if (n == null) {
            if (goLeft) {
              parent.left = new Node(key, parent,c);
            } else {
              parent.right = new Node(key, parent,c);
            }
            rebalance(parent);
            break;
          }
        }
      }
      return true;
    }
  
    private void delete(Node node) {
      if (node.left == null && node.right == null) {
        if (node.parent == null) root = null;
        else {
          Node parent = node.parent;
          if (parent.left == node) {
            parent.left = null;
          } else parent.right = null;
          rebalance(parent);
        }
        return;
      }
      if (node.left != null) {
        Node child = node.left;
        while (child.right != null) child = child.right;
        node.key = child.key;
        delete(child);
      } else {
        Node child = node.right;
        while (child.left != null) child = child.left;
        node.key = child.key;
        delete(child);
      }
    }
  
    public void delete(int delKey) {
      if (root == null) return;
      Node node = root;
      Node child = root;
  
      while (child != null) {
        node = child;
        child = delKey >= node.key ? node.right : node.left;
        if (delKey == node.key) {
          delete(node);
          return;
        }
      }
    }
  
    private void rebalance(Node n) {
      setBalance(n);
  
      if (n.balance == -2) {
        if (height(n.left.left) >= height(n.left.right)) n = rotateRight(n);
        else n = rotateLeftThenRight(n);
  
      } else if (n.balance == 2) {
        if (height(n.right.right) >= height(n.right.left)) n = rotateLeft(n);
        else n = rotateRightThenLeft(n);
      }
  
      if (n.parent != null) {
        rebalance(n.parent);
      } else {
        root = n;
      }
    }
  
    private Node rotateLeft(Node a) {
  
      Node b = a.right;
      b.parent = a.parent;
  
      a.right = b.left;
  
      if (a.right != null) a.right.parent = a;
  
      b.left = a;
      a.parent = b;
  
      if (b.parent != null) {
        if (b.parent.right == a) {
          b.parent.right = b;
        } else {
          b.parent.left = b;
        }
      }
  
      setBalance(a, b);
  
      return b;
    }
  
    private Node rotateRight(Node a) {
  
      Node b = a.left;
      b.parent = a.parent;
  
      a.left = b.right;
  
      if (a.left != null) a.left.parent = a;
  
      b.right = a;
      a.parent = b;
  
      if (b.parent != null) {
        if (b.parent.right == a) {
          b.parent.right = b;
        } else {
          b.parent.left = b;
        }
      }
  
      setBalance(a, b);
  
      return b;
    }
  
    private Node rotateLeftThenRight(Node n) {
      n.left = rotateLeft(n.left);
      return rotateRight(n);
    }
  
    private Node rotateRightThenLeft(Node n) {
      n.right = rotateRight(n.right);
      return rotateLeft(n);
    }
  
    private int height(Node n) {
      if (n == null) return -1;
      return n.height;
    }
  
    private void setBalance(Node... nodes) {
      for (Node n : nodes) {
        reheight(n);
        n.balance = height(n.right) - height(n.left);
      }
    }
  
    public int printBalance() {
      return printBalance(root);
    }
  
    private int printBalance(Node n) {
      if(n == null){return 0;}
      return printBalance(n.left) + printBalance(n.right) + n.c.waitTime;
    }
  
    public int size() {
      return size(root);
    }
  
    private int size(Node n) {
      if(n == null){return 0;}
      return size(n.left) + size(n.right) + 1;
    }
  
    private void reheight(Node node) {
      if (node != null) {
        node.height = 1 + Math.max(height(node.left), height(node.right));
      }
    }
  
    public customer search(int key) {
      Node result = searchHelper(this.root, key);
      if (result != null) return result.c;
      return null;
    }
  
    private Node searchHelper(Node root, int key) {
      // root is null or key is present at root
      if (root == null || root.key == key) return root;
  
      // key is greater than root's key
      if (root.key > key)
        return searchHelper(root.left, key); // call the function on the node's left child
  
      // key is less than root's key then
      // call the function on the node's right child as it is greater
      return searchHelper(root.right, key);
    }
    public static void main(String[] args) {
    }
  }

  
class Burger{
    int custId;
    int griddleTime;
}

//implement an AVL of <customer> with ID as key.

public class MMBurgers implements MMBurgersInterface {
    int time = 0;
    int noOfCounters, griddleSize, minLenQ;

    Queue<customer>[] counters;
    Burger[] griddle;

    HashMap<Integer, Integer> queLength = new HashMap<>();
    Queue<customer> griddleWait = new LinkedList<customer>();
    Queue<Burger> readyToSend = new LinkedList<Burger>();

    AVL allCustomers = new AVL();
    public boolean isEmpty(){
        for(int i = 0; i < griddleSize; i++){
            if(griddle[i] != null){
                System.out.println("False");
                return false;
            }
        }
        for(int i = 0; i < noOfCounters; i++){
            if(counters[i].peek() != null){
                System.out.println("False");
                return false;
            }
        }
        System.out.println("True");
        return true;
    }

    public void setK(int k) throws IllegalNumberException{
        counters = new Queue[k];
        for(int i = 0; i < k; i++){
            counters[i] = new LinkedList<customer>();
            queLength.put(i+1, 0);
        }
        minLenQ = 1;
        noOfCounters = k;
    }

    public void setM(int m) throws IllegalNumberException{
        griddle = new Burger[m];
        griddleSize = m;
    }

    public void advanceTime(int t) throws IllegalNumberException{
        if(time == t) {//System.out.println("no advancement"); 
          return;}
        while(time<t){
            for(int i = noOfCounters-1; i > -1; i--){
                if(counters[i].peek() == null){continue;}
                customer temp = counters[i].peek();
                temp.timeInQue++;
                if(temp.timeInQue == temp.queueNo){
                    //System.out.println("remove id:" + temp.id+ " from counter:" + i + " at time:" + time);
                    counters[i].remove();
                    griddleWait.add(temp);
                    temp.state = noOfCounters + 1;
                    int val = queLength.get(temp.queueNo) - 1;
                    queLength.put(temp.queueNo, val);
                    if(val < queLength.get(minLenQ) ){
                        minLenQ = temp.queueNo;
                    }
                    if(val == queLength.get(minLenQ) && temp.queueNo < minLenQ){
                        minLenQ = temp.queueNo;
                    }
                }
            }
            Queue<Burger> cooked = new LinkedList<Burger>();
            for(int i = 0; i < griddleSize; i++){
                if(griddle[i] == null){continue;}
                griddle[i].griddleTime++;
                if(griddle[i].griddleTime == 10){cooked.add(griddle[i]); griddle[i] = null;
                  //System.out.println("remove burger from griddle at" + i + " for id:" + griddle[i].custId + " at time:" + time);
                }
            }
            for(int i = 0; i < griddleSize; i++){
                if(griddleWait.size() == 0){break;}
                if(griddle[i] == null){
                    customer temp = griddleWait.peek();
                    temp.numbsGrid++;
                    Burger b = new Burger();
                    b.custId = temp.id;
                    b.griddleTime = 0;
                    griddle[i] = b;
                    //System.out.println("add burger to griddle at:" + i + " for id:" + griddle[i].custId + " at time:" + time);
                    if(temp.numbs == temp.numbsGrid){//System.out.println("remove " + temp.id + " from griddleQ");
                    griddleWait.remove();}
                }
            }
            while(!readyToSend.isEmpty()){
                Burger b = readyToSend.poll();
                customer x = allCustomers.search(b.custId);
                x.numbsRec++;
                if(x.numbs == x.numbsRec){
                    //ystem.out.println("all burgers recieved by id:" + x.id);
                    x.deparTime = time;
                    x.waitTime = x.deparTime - x.arrTime + 1;
                    x.state = noOfCounters+2;
                }
            }
            readyToSend = cooked;
        time++;}
    }

    public void arriveCustomer(int id, int t, int numb) throws IllegalNumberException{
        advanceTime(t);
        customer cust = new customer();
        cust.id = id;
        cust.numbs = numb;
        cust.arrTime = time;
        cust.queueNo = minLenQ;
        cust.state = minLenQ;
        cust.timeInQue = 0;
        counters[minLenQ-1].add(cust);
        allCustomers.insert(cust);
        int y = queLength.get(minLenQ);
        queLength.put(minLenQ,y+1);
        for(int i = noOfCounters-1; i >-1; i--){
            int val = queLength.get(i+1);
            if(val < queLength.get(minLenQ) ){
                minLenQ = i+1;
            }
            if(val == queLength.get(minLenQ) && i+1 < minLenQ){
                minLenQ = i+1;
            }
        }
        //System.out.println("_________time:" + time + "________");
    }

    public int customerState(int id, int t) throws IllegalNumberException{
        advanceTime(t);
        customer x = allCustomers.search(id);
        if(x==null){System.out.println(0); return 0;}
        System.out.println(x.state);
        return x.state;
    }

    public int griddleState(int t) throws IllegalNumberException{
        advanceTime(t);
        int ans = 0;
        for(int i = 0; i < griddleSize; i++){
            if(griddle[i]!=null){ans++;}
        }
        System.out.println(ans);
        return ans;
    }

    public int griddleWait(int t) throws IllegalNumberException{
        advanceTime(t);
        int ans = 0;
        for (customer item: griddleWait) {
            ans += (item.numbs - item.numbsGrid - item.numbsRec);
            //System.out.println("gw: id"+item.id+" burg"+(item.numbs - item.numbsGrid - item.numbsRec));
        }
        System.out.println(ans);
        return ans;
    }

    public int customerWaitTime(int id) throws IllegalNumberException{
        customer ans = allCustomers.search(id);
        System.out.println(ans.waitTime);
        return ans.waitTime;
    }

	public float avgWaitTime(){
        float ans = (float)allCustomers.printBalance()/allCustomers.size();
        System.out.println(ans);
        return ans;
    }
}
