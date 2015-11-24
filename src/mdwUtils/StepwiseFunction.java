package mdwUtils;
/*
 * 
 * Licensed under the Creative Commons Zero (CC0) license (https://creativecommons.org/publicdomain/zero/1.0/)
 * 
 */

/**
 * Implements a stepwise function.
 * Domain of function = real numbers.
 * Range of function = objects of type <T>.
 * I.e. we define a set of intervals on the reals. Within each interval, the function returns the same object.
 * 
 * Uses linear scan for evaluations, so not efficient for a large number of intervals.
 * 
 * @author woodhams
 *
 * @param <T>
 */

public class StepwiseFunction<T> {
	private T[] values;
	private double[] steps;

	public static final String LENGTH_MISMATCH_ERROR = "Lengths of steps and values do not agree";
	
	private static final double[] EMPTY_DOUBLE_ARRAY = new double[]{};
	public StepwiseFunction(double[] steps, T[] values) {
		this.values = values;
		// special case: if values[] has only one value, create a constant function
		if (values.length==1) {
			this.steps = EMPTY_DOUBLE_ARRAY;
		} else {
			this.steps = steps;
			// and some sanity checking for this more complex case 
			if (values.length!=steps.length+1) throw new IllegalArgumentException(LENGTH_MISMATCH_ERROR);
			double last = Double.NEGATIVE_INFINITY;
			for (double x : steps) {
				if (x <= last) throw new IllegalArgumentException("Steps must be in strictly increasing order");
				last = x;
			}
		}
	}

	/*
	 * TODO: could add a constructor which is passed a List<T>
	 */
	
	/**
	 * Takes a list of doubles (value, step, value, step, ..., value) and returns
	 * a StepwiseFunction<Double>.
	 */
	public static StepwiseFunction<Double> realStepwiseFunction(double[] valueStepValue) {
		if (valueStepValue.length%2 == 0) 
			throw new IllegalArgumentException("Step/value array must have odd number of elements");
		int nSteps = valueStepValue.length/2;
		Double[] values = new Double[nSteps+1];
		double[] steps = new double[nSteps];
		for (int i=0; i<nSteps; i++) {
			values[i] = valueStepValue[2*i];
			steps[i] = valueStepValue[2*i+1];
		}
		values[nSteps] = valueStepValue[2*nSteps];
		return new StepwiseFunction<Double>(steps, values);
	}
	/**
	 * Takes two lists of doubles (step,step) and (value,value,value) and returns
	 * a StepwiseFunction<Double>
	 */
	public static StepwiseFunction<Double> realStepwiseFunction(double[] step, double[] value) {
		Double[] valueD = new Double[value.length];
		for (int i=0; i<value.length; i++) valueD[i]=new Double(value[i]);
		return new StepwiseFunction<Double>(step, valueD);
	}
	
	public T f(double x) {
		int i;
		int n = steps.length;
		for (i=0; i<n && x>=steps[i]; i++);
		return values[i];
	}
	public double nextStep(double x) {
		int i;
		int n = steps.length;
		for (i=0; i<n && x>=steps[i]; i++);
		return (i==n) ? Double.POSITIVE_INFINITY : steps[i];
	}
	public double lastStep(double x) {
		int i;
		int n = steps.length;
		for (i=0; i<n && x>steps[i]; i++);
		return (i==0) ? Double.NEGATIVE_INFINITY : steps[i-1];
	}
	public int numSteps() {
		return steps.length;
	}
	public double getStep(int n) {
		return steps[n];
	}
	public T getValue(int n) {
		return values[n];
	}
	public String getValuesAsString() {
		int n = values.length;
		StringBuffer buf = new StringBuffer("(");
		for (int i=0; i<n-1; i++) {
			buf.append(values[i].toString()).append(',');
		}
		buf.append(values[n-1].toString()).append(')');
		return buf.toString(); 	
	}
	
	@SuppressWarnings("unchecked")
	public boolean valuesInRange(T min, T max) {
		Comparable<Object> cMin, cMax;
		try {
			cMin = (Comparable<Object>)min;
			cMax = (Comparable<Object>)max;
		} catch (ClassCastException e) {
			throw new IllegalArgumentException("Can't test range of values on non-Comparable type");
		}
		boolean outOfRange = false;
		for (T value : values) {
			outOfRange = outOfRange || (cMin.compareTo(value)>0) || (cMax.compareTo(value)<0);
		}
		return !outOfRange;
	}

	/**
	 * returns string in (value,step,value,step,...,value) format
	 */
	public String toString() {
		int n = steps.length;
		StringBuffer buf = new StringBuffer("(");
		for (int i=0; i<n; i++) {
			buf.append(values[i].toString()).append(',').append(steps[i]).append(',');
		}
		buf.append(values[n].toString()).append(')');
		return buf.toString(); 	
	}
	
	// This test will claim floor(-2.25)=-2. This is a feature, not a bug.
	public static void test() {
		StepwiseFunction<Double> testFunction = realStepwiseFunction(new double[]{-2,-1,-1,0,0,1,1,2,2});
		System.out.printf("Evaluating function %s:\n", testFunction.toString());
		System.out.println("  x   floor(x)\n");
		for (int i=-9; i<10; i++) {
			double x = (double)i / 4.0;
			System.out.printf("%5.2f  %4.1f\n",x,testFunction.f(x));
		}
	}
}
