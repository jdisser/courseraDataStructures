class MyLinkedList {

    private class ListNode{
        int val;
        ListNode next;
        ListNode(int val){
            this.val = val;
            this.next = null;
        }
        
    }
    
    private ListNode head;
    private ListNode tail;
    private int length;
    
    /** Initialize your data structure here. */
    public MyLinkedList() {

        this.tail = null;
        this.head = null;
        this.length = 0;
    }
    
    
    /**
    * Get the node at index return null if index invalid
    */
    
    private ListNode getNode(int index){
        //System.out.println("In getNode");
        //System.out.println("Index: " + index);
        
        int i = 0;
        ListNode cur = this.head;
        
        if ((index >= this.length)||(index < 0))
            return null;
        
        while (i < this.length){
            if (i == index)
                break;
            else {
                //System.out.print("i: " + i);
                //System.out.println(cur.next == null ? " null!" : " OK");
                cur = cur.next;
                ++i;
            }
        }
        return cur;
    }
    
    
    /** Get the value of the index-th node in the linked list. If the index is invalid, return -1. */
    public int get(int index) {
        if ((index >= this.length)||(index < 0))
            return -1;
        int i = 0;
        ListNode cur = this.head;
        while (i < this.length){
            if (i == index){
                break;
            } else {

                cur = cur.next;
                ++i;
            }
        } 
        return cur.val;
    }
    
    /** Add a node of value val before the first element of the linked list. After the insertion, the new node will be the first node of the linked list. */
    public void addAtHead(int val) {
        
        //System.out.print("addAtHead [" + val + "] ");
        
        ListNode cur = null;
        
        if (this.head == null){
            this.head = new ListNode(val);
            ++this.length;
            //printList();
            return;
        } else {
            cur = new ListNode(val);
            cur.next = this.head;
            this.head = cur;
            ++this.length;
            //printList();
            return;
        }
        
    }
    
    /** Append a node of value val to the last element of the linked list. */
    public void addAtTail(int val) {
        
        //System.out.print("addAtTail [" + val + "] ");
        
        ListNode cur = null;
        ListNode prv = null;
        
        if(this.length == 0){
            return;
        } else if (this.tail == null){
            this.tail = getNode(this.length -1);
        } 
        cur = new ListNode(val);
        this.tail.next = cur;
        this.tail = cur;
        ++this.length;
        //printList();
        return;
        
        
    }
    
    /** Add a node of value val before the index-th node in the linked list. If index equals to the length of linked list, the node will be appended to the end of linked list. If index is greater than the length, the node will not be inserted. */
    public void addAtIndex(int index, int val) {
        
        //System.out.print("addAtIndex [" + index +"," + val + "] ");
        
        if (index > this.length)
           return;
        if (index == 0 && this.length == 0){
            this.addAtHead(val);
            return;
        }
        if (index == this.length){
            this.addAtTail(val);
            return;
        }
        
        ListNode cur = new ListNode(val);
        ListNode prv = getNode(index-1);
        ListNode nxt = prv.next;
        cur.next = nxt;
        prv.next = cur;
        ++this.length;
        //printList();
        return;
    }
    
    /** Delete the index-th node in the linked list, if the index is valid. */
    public void deleteAtIndex(int index) {

        //System.out.println("in deleteAtIndex");
        //System.out.println("Length: " + this.length);
        //System.out.println("Index: " + index);
        
        
        if(index >= this.length)
            return;
        
        if(index == 0 && this.length == 1){
            this.head = null;
            this.tail = null;
            --this.length;
            return;
        }
        if(index == 0 && this.length > 1){
            this.head = getNode(1);
            --this.length;
            return;
        }
        if(index == this.length - 1){
            this.tail = getNode(index - 1);
            --this.length;
            return;
        }

        ListNode prv = getNode(index - 1);
        ListNode cur = getNode(index);
        ListNode nxt = getNode(index + 1);
        //ListNode cur = prv.next;
        //ListNode nxt = cur.next;
        prv.next = nxt;
        --this.length;
        return;
        
    }
    
    public void printList(){
        ListNode cur = this.head;
        int i = 0;
//        System.out.println("printList length: " + this.length);
        if (this.length != 0){
            while (i < this.length){
                System.out.print(cur.val + "->");
                cur = cur.next;
                ++i;
            }
            System.out.println("Length: " + this.length);
        }
    }

/*
    public static void main (String[] args){
        MyLinkedList ml = new MyLinkedList();
        System.out.println("get(0): " + ml.get(0));
        ml.addAtIndex(1,2);
        ml.printList();
        System.out.println("get(0): " + ml.get(0));       
        System.out.println("get(1): " + ml.get(1));
        ml.addAtIndex(0,1);
        ml.printList();
        System.out.println("get(0): " + ml.get(0));       
        System.out.println("get(1): " + ml.get(1));
//        ml.printList();
//        ml.addAtHead(1);
//        ml.printList();

    }
*/
}
