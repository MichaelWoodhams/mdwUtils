package mdwUtils;

import java.util.Vector;
/*
 * Very simple class to package two lists of equal length together.
 * 
 * Arguably would be better as a list of tuples. http://www.javatuples.org/
 * This is Apache licensed, and I might need to release CC0 licensed version of code.
 */

public class DoubleList<A,B> {
	private Vector<A> listA;
	private Vector<B> listB;
	
	public DoubleList() {
		listA = new Vector<A>();
		listB = new Vector<B>();
	}
	
	public void add(A a, B b) {
		listA.add(a);
		listB.add(b);
	}
	
	public A getA(int i) {
		return listA.get(i);
	}
	@SuppressWarnings("unchecked")
	public Vector<A> getA() {
		return (Vector<A>) listA.clone();
	}
	
	public B getB(int i) {
		return listB.get(i);
	}
	@SuppressWarnings("unchecked")
	public Vector<B> getB() {
		return (Vector<B>) listB.clone();
	}

	
	public int size() {
		return listA.size();
	}
	
	public String toString() {
		StringBuffer buf = new StringBuffer("[");
		for (int i=0; i<listA.size(); i++) {
			buf.append("(").append(listA.get(i).toString()).append(",").append(listB.get(i).toString()).append(")");
		}
		return buf.toString();
	}
}
