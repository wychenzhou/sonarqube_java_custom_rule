package com.rule.test.enumtest;
import java.util.Collection;
import java.util.List;
import java.util.Set;
public class CollectionToArrayDemo {
	
}


class A<T> {
  <E extends T> Object[] foo(List<String> list, Set rawSet, Collection<E> col) {
    String[] a1 = (String[]) list.toArray(); // Noncompliant 
    Number[] a2 = (Number[]) rawSet.toArray(); // Noncompliant 
    Object[] a3 = list.toArray(); // Compliant
    String[] a4 = list.toArray(new String[0]); // Compliant
    Object[] a5 = (Object[]) list.toArray(); // Compliant
    E[] a6 = (E[]) col.toArray(); // Compliant
    Object o = (Object) list.toArray(); // Compliant
    return list.toArray(); // Compliant
  }
}
