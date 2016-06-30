package j.u;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import static j.u.HashMap.Node;

class P {

    private P () {}

    static void printNextItem(LinkedList.Node n) {
        if (n == null) {
            System.out.print("?");
        } else {
            System.out.print(n.item + "|");
            printNextItem(n.next);
        }
    }

    static void printNodes(LinkedList l) {
        printFromFirst(l.first);
        print("");
    }

    static void printFromFirst(LinkedList.Node n) {
        if (n != null) {
            printNode(n);
            printFromFirst(n.next);
        }
    }

    static void printNode(LinkedList.Node n) {
        if (n != null) {
            LinkedList.Node v;
            System.out.print("(");
            System.out.print((v = n.prev) == null ? "?" : v.item);
            System.out.print("|" + n.item + "|");
            System.out.print((v = n.next) == null ? "?)" : v.item + ")->");
        }
    }

    static void printTreeSet(TreeSet t) {
        print(t);
        TreeMap m = (TreeMap) t.m;
        TreeMap.Entry root = m.root;
        printTreeEntries(root);
        print("");
    }

    static final TreeMap.Entry EMPTY = new TreeMap.Entry(0, 0, null);

    static void printTree(TreeMap tm) {
        print(tm);
        LinkedList<TreeMap.Entry> entries = new LinkedList<>();
        entries.add(tm.root);
        final int rootLevel = 1;
        TreeMap<Integer, LinkedList<TreeMap.Entry>> m = new TreeMap<>();
        m.put(rootLevel, entries);
        collectTreeNodes(m);
        printTreeNodes(m);
        print("");
    }

    static void printTree(TreeSet ts) {
//      printTree(((TreeMap) ts.m));
        print(ts);
        TreeMap<Integer, LinkedList<TreeMap.Entry>> tm = new TreeMap<>();
        LinkedList<TreeMap.Entry> entries = new LinkedList<>();
        entries.add(((TreeMap) ts.m).root);
        final int rootLevel = 1;
        tm.put(rootLevel, entries);
        collectTreeNodes(tm);
        printTreeNodes(tm);
        print("");
    }


    static void collectTreeNodes(TreeMap<Integer, LinkedList<TreeMap.Entry>> tm) {
        Integer lastLevel = tm.lastKey();
        LinkedList<TreeMap.Entry> lastLevelentries = tm.get(lastLevel);
        Integer nextLevel = lastLevel + 1;
        LinkedList<TreeMap.Entry> nextLevelentries = new LinkedList<>();
        boolean allNull = true;
        for(TreeMap.Entry e : lastLevelentries) {
            if (e != null && !EMPTY.equals(e)) {
                nextLevelentries.add(e.left);
                nextLevelentries.add(e.right);
                allNull = false;
            } else {
                nextLevelentries.add(EMPTY);
                nextLevelentries.add(EMPTY);
                allNull = true;
            }
        }
        if (allNull) return; // end recursion
        tm.put(nextLevel, nextLevelentries);
        collectTreeNodes(tm);
    }

    static void printTreeNodes(TreeMap<Integer, LinkedList<TreeMap.Entry>> tm) {
        Set<Integer> levels = tm.keySet();
        int maxLevel = levels.size();
        final String indent = "   "; // 3 chars
        for (int level = maxLevel; level>=1; level--) { // root down
            LinkedList<TreeMap.Entry> ll = tm.get(level);
            boolean first = true;
            for (TreeMap.Entry e : ll) {
                indent(level, maxLevel, first, indent);
                first = false;
                if (EMPTY.equals(e)) {
                    System.out.print(indent);
                } else {
                    System.out.print( e == null ? "nul" : String.format((e.color ? "B" : "R") + "%2s", getKey(e))); // 3 chars
                }
            }
            print("");
        }
    }

    static void indent(int level, int maxLevel, boolean first, String indent) {
        StringBuilder sb = new StringBuilder();
        int n = first ? (2 << (maxLevel - level - 1)) - 1 : (2 << (maxLevel - level)) - 1;
        for (int i = 0; i < n; i++) {
            sb.append(indent);
        }
        System.out.print(sb.toString());
    }

    static void printTreeEntries(TreeMap.Entry e) {
        if (e != null) {
            printTreeEntry(e);
            printTreeEntries(e.left);
            printTreeEntries(e.right);
        }
    }

    static void printTreeEntry(TreeMap.Entry e) {
        if (e != null) {
            System.out.print(e.color ? "B " : "R ");
            printLeft(e);
            printThis(e);
            printParent(e);
            printRight(e);
        }
        print("");
    }

    static void printLeft(TreeMap.Entry e) {
        TreeMap.Entry v = e.left;
        System.out.print(getKey(v) + "<");
    }

    static void printThis(TreeMap.Entry e) {
        System.out.print(getKey(e));
    }

    static void printParent(TreeMap.Entry e) {
        TreeMap.Entry v = e.parent;
        System.out.print("^" + getKey(v));
    }

    static void printRight(TreeMap.Entry e) {
        TreeMap.Entry v = e.right;
        System.out.print(">" + getKey(v));
    }

    static String getKey(TreeMap.Entry e) {
        return e == null ? "?" : "" + e.getKey();
    }

    static void printKey(TreeMap.Entry e, String s) {
        if (e != null) {
            if (s.equals(">")) {
                System.out.print(s + e.getKey());
            } else {
                System.out.print(e.getKey() + s);
            }
        } else {
            if (s.equals(">")) {
                System.out.print(s + "?");
            } else {
                System.out.print("?" + s);
            }
        }
    }

    static void printSet(HashSet s) {
        printMap(s.map);
    }

    static void printMap(HashMap m) {
        print("size       = " + m.size);
        print("loadFactor = " + m.loadFactor);
        print("threshold  = " + m.threshold);
        print("modCount   = " + m.modCount);
        printNotNull("keySet     = ", m.keySet);
        printNotNull("entrySet   = ", m.entrySet);
        printNotNull("values     = ", m.values);
        printTable(m);
        print("");
    }

    static void printTable(HashMap m) {
        printTable(m.table);
    }

    static void printTable(Node[] t) {
        if (t == null) {
            print("Table is null");
        } else {
            print("Table length = " + t.length);
            int i = 0;
            for (Node n : t) {
                System.out.print(String.format("%2s", i) + " ");
                printNode(n);
                printLinkedNodes(n);
                print("");
                i++;
            }
            print("");
        }
    }

    static void printLinkedNodes(Node n) {
        if (n != null) {
            System.out.print("->");
            printNode(n.next);
            printLinkedNodes(n.next);
        } else {
            System.out.print("?");
        }
    }

    static void printNode(Node n) {
        if (n != null) {
            System.out.print(n);
            if (n instanceof LinkedHashMap.Entry) {
                LinkedHashMap.Entry e = (LinkedHashMap.Entry) n;
                LinkedHashMap.Entry a = e.after;
                LinkedHashMap.Entry b = e.before;
                System.out.print("(");
                System.out.print(b == null ? "?" : b.getKey());
                System.out.print(",");
                System.out.print(a == null ? "?" : a.getKey());
                System.out.print(")");
            }
        }
    }

    static void printNotNull(String s, Object p) {
        if (p != null) {
            System.out.println(s + p);
        }
    }

    static int hash(Object p) {
        return System.identityHashCode(p);
    }

    static void printList(ArrayList l) {
        print("length   = " + l.elementData.length);
        print("size     = " + l.size);
        print("modCount = " + l.modCount);
        printElementData(l);
        print("");
    }

    static void printElementData(ArrayList l) {
        for (int i = 0; i < l.elementData.length; i++) {
            print(String.format("%2s", i) + " " + l.elementData[i]);
        }
    }

    static void print(ArrayDeque p) {
        System.out.print(String.format("%2s", p.head) + "/" + p.tail + "[");
        for (Object o : p.elements) {
            System.out.print((o != null ? o : "."));
        }
        Object f = p.peekFirst(), l = p.peekLast();
        print("]" + (f != null ? f : "?") + "/" + (l != null ? l : "?") );
    }

    static void print(PriorityQueue p) {
        System.out.print("[");
        for (Object o : p.queue) {
            System.out.print((o != null ? o : "."));
        }
        print("]");
    }

    static int tableLength() {return tableLength(-1);}

    static int tableLength(int initialCapacity) {
        HashMap<Integer, Integer> m;
        m = initialCapacity < 0 ? new HashMap<>() : new HashMap<>(initialCapacity);
        m.put(0, 1);
        return m.table.length;
    }

    static void contains(Collection c1, Collection c2) {
        print(c1 + " contains " + c2 + " -> " + String.format("%5s", c1.containsAll(c2)) + "; " +
                c2 + " contains " + c1 + " -> " + String.format("%5s", c2.containsAll(c1)));
    }

    static void print(List l, ListIterator it) {
        print("p=" + it.previousIndex() + " n=" + it.nextIndex() + " " + l);
    }

    static void print(ListIterator it) {
        print("p=" + it.previousIndex() + " n=" + it.nextIndex());
    }


    static void print(int n, EnumSet s) {
        RegularEnumSet r = (RegularEnumSet) s;
        print(" elements=" + String.format("%" + n + "s", Long.toBinaryString(r.elements)).replace(' ', '0') + " " + r);
    }


    static void print(Object[] p) {
        print(Arrays.toString(p));
    }

    static void print(Object p) {
        System.out.println(p);
    }
}