package test;

import org.junit.Test;

import java.util.HashMap;

public class HashTableTest {

    @Test
    public void test() {
//        HashMap
    }

    class HashTable<K, V> {

        private final int DEFAULT_SIZE = 8;
        private final float RATIO = 0.75f;

        private Entry<K, V>[] table = new Entry[DEFAULT_SIZE];
        // 实际元素
        private int size = 0;
        // 散列表使用索引个数
        private int use = 0;

        public void put(K k, V v) {
            int hash = hash(k);
            Entry newEntry = new Entry(k, v);
            Entry head = table[hash];
            if (head == null) {
                head = new Entry(null, null);
                table[hash] = head;
                head.next = newEntry;
                use++;
                size++;
                if (use >= table.length * RATIO) {
                    reSize();
                }
            } else {
                // conflict，散列冲突
                Entry tmp = head.next;
                do {
                    if (tmp.k == newEntry.k) {   // If key is equals, then replace
                        tmp.v = newEntry.v;
                        return;
                    }
                    tmp = tmp.next;
                } while (tmp != null);

                tmp = head.next;
                head.next = newEntry;
                newEntry.next = tmp;

                size++;
            }
        }

        public Entry remove(K k) {
            int index = hash(k);
            Entry node = table[index];
            if (node == null || node.next == null) {
                return null;
            } else {
                Entry parent = table[index];
                node = node.next;
                while (node.k != k) {
                    parent = node;
                    node = node.next;
                }
                Entry next = node.next;
                parent.next = next;
                if (parent.next == null) {
                    use--;
                }
                size--;
                return node;
            }
        }

        public Entry get(K k) {
            int index = hash(k);
            Entry node = table[index];
            if (node == null || node.next == null) {
                return null;
            } else {
                node = node.next;
                while (node.k != k) {
                    node = node.next;
                }
                return node;
            }
        }

        private void reSize() {
            Entry[] oldTable = table;
            table = new Entry[oldTable.length * 2];
            use = 0;
            for (int i = 0; i < oldTable.length; i++) {
                Entry tmp = oldTable[i];
                if (tmp == null || tmp.next == null) {
                    continue;
                }
                tmp = tmp.next;
                while (tmp != null) {
                    int index = hash(tmp.k);
                    if (table[index] == null) {
                        Entry head = new Entry(null, null);
                        table[index] = head;
                        use++;
                    }
                    Entry newNode = new Entry(tmp.k, tmp.v);
                    Entry next = table[index].next;
                    table[index].next = newNode;
                    newNode.next = next;
                }
            }
        }

        private int hash(Object key) {
            int h;
            return ((key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16)) % table.length;
        }

        class Entry<K, V> {
            K k;
            V v;
            Entry next;

            public Entry(K k, V v) {
                this.k = k;
                this.v = v;
            }
        } // end of Entry
    }
}
