class DLLNode {
    value: any;
    next: DLLNode | null;
    prev: DLLNode | null;

    constructor(value: any) {
        this.value = value;
        this.next = null;
        this.prev = null;
    }
}

class DoublyLinkedList {
    private head: DLLNode | null;
    private tail: DLLNode | null;

    constructor() {
        this.head = null;
        this.tail = null;
    }

    push(value: any) {
        const node: DLLNode = new DLLNode(value);
        if(this.tail) {
            this.tail.next = node;
            node.prev = this.tail;
            this.tail = node;
        } else {
            this.head = node;
            this.tail = node;
        }
    }

    unshift(value: any) {
        var node = new DLLNode(value);
        if(this.head) {
            this.head.prev = node;
            node.next = this.head;
            this.head = node;
        } else {
            this.head = node;
            this.tail = node;
        }
    }

    pop(): any {
        let removedTail = this.tail!.value;
        if (this.tail?.prev) {
            this.tail.prev = this.tail;
            this.tail.next = null;
            return removedTail;
        } else {
            this.head = null;
            this.tail = null;
            return removedTail;
        }
    }

    shift(): any {
        let removedHead = this.head!.value;
        if (this.head?.next) {
            this.head = this.head.next;
            this.head.prev = null;
            return removedHead;
        } else {
        this.head = null;
        this.tail = null;
        return removedHead;
        }
    }

    public atIndex(index: number): any
   {
       let node = this.head;
       let i = 0;
       while(i < index)
       {
           node = node!.next;
           i++;

       }
       return node!.value;
   } 
}

let node= new DLLNode("testing");

let dll = new DoublyLinkedList();
dll.push(node.value);
dll.push("abc");
console.clear();
console.log(dll.pop());
console.log(dll.atIndex(0))
console.log(dll.shift());
console.log(dll.atIndex(0));
dll.unshift("I am first");
console.log(dll.atIndex(0));