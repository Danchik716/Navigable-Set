import java.util.*;

public class NaviSet<T> extends AbstractSet<T> implements NavigableSet<T> {

    private Comparator<? super T> comp;
    private ArrayList<T> arr;

    public NaviSet(Collection<? extends T> col, Comparator<? super T> comp) {
        this.comp = comp;
        this.arr = (ArrayList<T>) col;
        this.arr.sort(comp);
    }

    public NaviSet(Comparator<? super T> comp) {
        this.arr = new ArrayList<>();
        this.comp = comp;
    }

    @Override
    public boolean add(T s) {
        if (arr.size() == 0) {
            arr.add(s);
            return true;
        }
        if (contains(s)) {
            return false;
        }
        for (T el : arr) {
            if (comp.compare(el, s) > 0) {
                arr.add(arr.indexOf(el), s);
                return true;
            }
        }
        arr.add(s);
        return true;
    }


    @Override
    public T lower(T s) {
        int k = -1;
        for (T el : arr) {
            if (comp.compare(el, s) < 0) {
                k++;
            }
        }
        if (k != -1) {
            return arr.get(k);
        }
        return null;
    }

    @Override
    public T higher(T s) {
        for (T el : arr) {
            if (comp.compare(el, s) > 0) {
                return el;
            }
        }
        return null;
    }

    @Override
    public T floor(T s) {
        int k = -1;
        for (T el : arr) {
            if (comp.compare(el, s) <= 0) {
                k++;
            }
        }
        if (k != -1) {
            return arr.get(k);
        }
        return null;
    }

    @Override
    public T ceiling(T s) {
        for (T el : arr) {
            if (comp.compare(el, s) >= 0) {
                return el;
            }
        }
        return null;
    }


    @Override
    public T pollFirst() {
        if (arr.size() == 0) return null;
        T temp = first();
        arr.remove(0);
        return temp;
    }

    @Override
    public T pollLast() {
        if (arr.size() == 0) return null;
        T temp = last();
        arr.remove(arr.size() - 1);
        return temp;
    }

    @Override
    public Iterator<T> iterator() {
        return arr.iterator();
    }

    @Override
    public NavigableSet<T> descendingSet() {
        ArrayList<T> newArr = new ArrayList<>();
        for (int i = 0; i < size(); i++){
            newArr.add(arr.get(size() - 1 - i));
        }
        return new NaviSet<T>(newArr, new Comparator<T>() {
                    @Override
                    public int compare(T s1, T s2) {
                        return comp.compare(s2, s1);
                    }
                });
    }

    @Override
    public Iterator<T> descendingIterator() {
        return descendingSet().iterator();
    }

    @Override
    public NavigableSet<T> subSet(T s1, boolean bool1, T s2, boolean bool2) {
        NaviSet<T> newSet = new NaviSet<T>(tailSet(s1 ,bool1), comp);
        return newSet.headSet(s2, bool2);
    }

    @Override
    public NavigableSet<T> headSet(T s, boolean bool) {
        NaviSet<T> k= (NaviSet<T>) headSet(s);
        if (!bool) return k;
        k.add(k.ceiling(s));
        return k;
    }

    @Override
    public NavigableSet<T> tailSet(T s, boolean bool) {
        NaviSet<T> k = (NaviSet<T>) tailSet(s);
        if (!bool) return k;
        k.add(floor(s));
        return k;
    }

    @Override
    public Comparator<? super T> comparator() {
        return comp;
    }

    @Override
    public SortedSet<T> subSet(T s1, T s2) {
        return subSet(s1, true, s2, false);
    }

    @Override
    public SortedSet<T> headSet(T s) {
        return headSet(s, false);
    }

    @Override
    public SortedSet<T> tailSet(T s) {
        return tailSet(s, true);
    }

    @Override
    public T first() {
        return arr.get(0);
    }

    @Override
    public T last() {
        return arr.get(arr.size() - 1);
    }

    @Override
    public int size() {
        return arr.size();
    }

    @Override
    public String toString() {
        return "NaviSet{" + arr.toString() + "}";
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}
